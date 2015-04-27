package org.javifact.buildsrc.generator.segment;


import org.apache.commons.lang3.text.WordUtils;
import org.javifact.buildsrc.GeneralUtils;
import org.javifact.buildsrc.generator.CommonSourceGenerator;
import org.jboss.forge.roaster.Roaster;
import org.jboss.forge.roaster.model.source.JavaClassSource;
import org.jboss.forge.roaster.model.source.JavaDocSource;

import java.util.*;

/**
 * Created by neil on 24/12/14.
 *
 * Problem: This does not support segments which contain the same composite element twice!
 *
 * I need to create a new version which makes classes for external classes for composite elements
 */
public class SegmentSourceGenerator {

    public final static String PACKAGE = "org.javifact.segment";

    public final CommonSourceGenerator commonSourceGenerator;

    public SegmentSourceGenerator(CommonSourceGenerator commonSourceGenerator) {
        this.commonSourceGenerator = commonSourceGenerator;
    }

    public String generateSource(SegmentDefinition segmentDefinition) throws InvalidSegmentException {
        validateSegment(segmentDefinition);

        // create class
        JavaClassSource segmentClass = Roaster.create(JavaClassSource.class);
        segmentClass.setPackage(PACKAGE);
        String segmentClassName = segmentDefinition.getName().toUpperCase();
        segmentClass.setName(segmentClassName);

        // Add JavaDoc
        JavaDocSource<JavaClassSource> classJavaDoc = segmentClass.getJavaDoc();
        String classJavaDocText = buildClassJavaDocText(segmentDefinition);
        classJavaDoc.setFullText(classJavaDocText);

        List<DataElementDefinition> dataElementDefinitions = segmentDefinition.getDataElements();
        List<String> dataElementNames = getDataElementNames(dataElementDefinitions);
        Map<DataElementDefinition, JavaClassSource> nonUniqueCompositeDataElementsMaps = new HashMap<>();

        for (int dataElementIndex = 0; dataElementIndex < dataElementDefinitions.size(); dataElementIndex++) {
            DataElementDefinition dataElementDefinition = dataElementDefinitions.get(dataElementIndex);
            if (dataElementDefinition.getComponentNames().isEmpty()) {
                String dataElementName = dataElementDefinition.getName();
                String propertyName;
                String formattedName;
                if (Collections.frequency(dataElementNames, dataElementName) == 1) {
                    propertyName = commonSourceGenerator.toPropertyName(dataElementName);
                    formattedName = commonSourceGenerator.toFormattedName(dataElementName);
                } else {
                    List<String> dataElementNamesAlreadyAdded = dataElementNames.subList(0, dataElementIndex);
                    int propertyIndex = Collections.frequency(dataElementNamesAlreadyAdded, dataElementName);
                    propertyName = commonSourceGenerator.toPropertyName(dataElementName, propertyIndex);
                     formattedName = commonSourceGenerator.toFormattedName(dataElementName, propertyIndex);
                }
                commonSourceGenerator.addStringPropertyWithJavaDoc(segmentClass, propertyName, formattedName);
            } else {
                String dataElementName = dataElementDefinition.getName();
                if (Collections.frequency(dataElementNames, dataElementName) == 1) {
                    JavaClassSource dataElementClass = buildCompositeDataElementClass(dataElementDefinition);
                    segmentClass.addNestedType(dataElementClass).setStatic(true);

                    String propertyName = commonSourceGenerator.toPropertyName(dataElementName);
                    String formattedName = commonSourceGenerator.toFormattedName(dataElementName);
                    commonSourceGenerator.addCustomTypePropertyWithJavaDoc(segmentClass, propertyName, formattedName, dataElementClass);
                    //segmentClass.addProperty(dataElementClass, propertyName);
                } else {
                    List<String> dataElementNamesAlreadyAdded = dataElementNames.subList(0, dataElementIndex);
                    int propertyIndex = Collections.frequency(dataElementNamesAlreadyAdded, dataElementName);
                    JavaClassSource dataElementClass;
                    if (propertyIndex == 0) {
                        dataElementClass = buildCompositeDataElementClass(dataElementDefinition);
                        segmentClass.addNestedType(dataElementClass).setStatic(true);
                        nonUniqueCompositeDataElementsMaps.put(dataElementDefinition, dataElementClass);
                    } else {
                        dataElementClass = nonUniqueCompositeDataElementsMaps.get(dataElementDefinition);
                    }
                    String propertyName = commonSourceGenerator.toPropertyName(dataElementName, propertyIndex);
                    String formattedName = commonSourceGenerator.toFormattedName(dataElementName, propertyIndex);
                    commonSourceGenerator.addCustomTypePropertyWithJavaDoc(segmentClass, propertyName, formattedName, dataElementClass);
                }
            }
        }

        return segmentClass.toString();
    }

    private void validateSegment(SegmentDefinition segmentDefinition) throws InvalidSegmentException {
        // Create map of data element definitions with key being the name and the value being a list of all data elements with that name
        Map<String, List<DataElementDefinition>> dataElementDefinitionsMap = new HashMap<>();
        for (DataElementDefinition dataElementDefinition : segmentDefinition.getDataElements()) {
            String dataElementDefinitionName = dataElementDefinition.getName();
            List<DataElementDefinition> dataElementDefinitions = dataElementDefinitionsMap.get(dataElementDefinitionName);
            if (dataElementDefinitions == null) {
                dataElementDefinitions = new ArrayList<>();
                dataElementDefinitionsMap.put(dataElementDefinitionName, dataElementDefinitions);
            }
            dataElementDefinitions.add(dataElementDefinition);
        }

        // verify all data elements with the same name have the same definition
        for (List<DataElementDefinition> dataElementDefinitions : dataElementDefinitionsMap.values()) {
            if (!GeneralUtils.allEqual(dataElementDefinitions)) {
                String message = "Segment " + segmentDefinition.getName() + " contains mutiple definitions for data element " + dataElementDefinitions.get(0).getName();
                throw new InvalidSegmentException(message);
            }
        }
    }

    private JavaClassSource buildCompositeDataElementClass(DataElementDefinition dataElementDefinition) {
        JavaClassSource dataElementClass = Roaster.create(JavaClassSource.class);
        String dataElementName = dataElementDefinition.getName();
        String dataElementClassName = commonSourceGenerator.toClassName(dataElementName);
        dataElementClass.setName(dataElementClassName);

        List<String> dataElementComponentNames = dataElementDefinition.getComponentNames();
        for (int dataElementComponentIndex = 0; dataElementComponentIndex < dataElementComponentNames.size(); dataElementComponentIndex++) {
            String dataElementComponentName = dataElementComponentNames.get(dataElementComponentIndex);
            String propertyName;
            String formattedName;
            if (Collections.frequency(dataElementComponentNames, dataElementComponentName) == 1) {
                propertyName = commonSourceGenerator.toPropertyName(dataElementComponentName);
                formattedName = commonSourceGenerator.toFormattedName(dataElementComponentName);
            } else {
                List<String> dataElementComponentNamesAlreadyAdded = dataElementComponentNames.subList(0, dataElementComponentIndex);
                int propertyIndex = Collections.frequency(dataElementComponentNamesAlreadyAdded, dataElementComponentName);
                propertyName = commonSourceGenerator.toPropertyName(dataElementComponentName, propertyIndex);
                formattedName = commonSourceGenerator.toFormattedName(dataElementComponentName, propertyIndex);
            }
            commonSourceGenerator.addStringPropertyWithJavaDoc(dataElementClass, propertyName, formattedName);
        }
        JavaDocSource<JavaClassSource> dataElementclassJavaDoc = dataElementClass.getJavaDoc();
        String formattedName = commonSourceGenerator.toFormattedName(dataElementName);
        String dataElementClassJavaDocText = buildClassJavaDocText(formattedName);
        dataElementclassJavaDoc.setFullText(dataElementClassJavaDocText);

        return dataElementClass;
    }

    private String buildClassJavaDocText(SegmentDefinition segmentDefinition) {
        StringBuilder javaDocTextBuilder = new StringBuilder();
        String segmentName = segmentDefinition.getName().toUpperCase();
        if (GeneralUtils.isVowel(segmentName.charAt(0))) {
            javaDocTextBuilder.append("This represents an ");
        } else {
            javaDocTextBuilder.append("This represents a ");
        }
        javaDocTextBuilder.append(segmentName);
        javaDocTextBuilder.append(" (");
        javaDocTextBuilder.append(WordUtils.capitalizeFully(segmentDefinition.getTitle()));
        javaDocTextBuilder.append(") segment.  The purpose of this segment is: ");
        javaDocTextBuilder.append(segmentDefinition.getFunction());
        return javaDocTextBuilder.toString();
    }

    private String buildClassJavaDocText(String formattedName) {
        StringBuilder javaDocTextBuilder = new StringBuilder();
        if (GeneralUtils.isVowel(formattedName.charAt(1))) {
            javaDocTextBuilder.append("This represents an ");
        } else {
            javaDocTextBuilder.append("This represents a ");
        }
        javaDocTextBuilder.append(formattedName);
        javaDocTextBuilder.append(" data element");
        return javaDocTextBuilder.toString();
    }

    private List<String> getDataElementNames(List<DataElementDefinition> dataElementDefinitions) {
        List<String> names = new ArrayList<>();

        for (DataElementDefinition dataElementDefinition : dataElementDefinitions) {
            names.add(dataElementDefinition.getName());
        }

        return names;
    }
}