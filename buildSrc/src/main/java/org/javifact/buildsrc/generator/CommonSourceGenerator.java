package org.javifact.buildsrc.generator;

import org.apache.commons.lang3.text.WordUtils;
import org.javifact.buildsrc.GeneralUtils;
import org.jboss.forge.roaster.model.JavaType;
import org.jboss.forge.roaster.model.Property;
import org.jboss.forge.roaster.model.source.JavaClassSource;
import org.jboss.forge.roaster.model.source.MethodSource;

/**
 * Created by neil on 30/12/14.
 */
public class CommonSourceGenerator {


    public String toClassName(String name) {
        StringBuilder propertyNameBuilder = new StringBuilder();
        boolean capitaliseNext = true;
        for (int charIndex = 0; charIndex < name.length(); charIndex++) {
            char nameChar = name.charAt(charIndex);
            if (nameChar == ' ' || nameChar == '/') {
                capitaliseNext = true;
            } else if (Character.isLetterOrDigit(nameChar)) {
                if (capitaliseNext) {
                    propertyNameBuilder.append(Character.toUpperCase(nameChar));
                    capitaliseNext = false;
                } else {
                    propertyNameBuilder.append(Character.toLowerCase(nameChar));
                }
            }
        }

        return propertyNameBuilder.toString();
    }

    public String toPropertyName(String name) {
        StringBuilder propertyNameBuilder = new StringBuilder();
        boolean capitaliseNext = false;
        for (int charIndex = 0; charIndex < name.length(); charIndex++) {
            char nameChar = name.charAt(charIndex);
            if (nameChar == ' ' || nameChar == '/') {
                capitaliseNext = true;
            } else if (Character.isLetterOrDigit(nameChar)) {
                if (capitaliseNext) {
                    propertyNameBuilder.append(Character.toUpperCase(nameChar));
                    capitaliseNext = false;
                } else {
                    propertyNameBuilder.append(Character.toLowerCase(nameChar));
                }
            }
        }

        return propertyNameBuilder.toString();
    }

    public String toPropertyName(String name, int index) {
        StringBuilder propertyNameBuilder = new StringBuilder();
        propertyNameBuilder.append(toPropertyName(name));
        propertyNameBuilder.append(index + 1);
        return propertyNameBuilder.toString();
    }

    public String toFormattedName(String name) {
        StringBuilder formattedNameBuilder = new StringBuilder();
        formattedNameBuilder.append('\"');
        formattedNameBuilder.append(WordUtils.capitalizeFully(name));
        formattedNameBuilder.append('\"');
        return formattedNameBuilder.toString();
    }

    public String toFormattedName(String name, int index) {
        StringBuilder formattedNameBuilder = new StringBuilder();
        formattedNameBuilder.append(GeneralUtils.ordinal(index + 1));
        formattedNameBuilder.append(' ');
        formattedNameBuilder.append(toFormattedName(name));
        return formattedNameBuilder.toString();
    }

    public void addStringPropertyWithJavaDoc(JavaClassSource javaClassSource, String propertyName, String formattedName) {
        System.out.println("Adding new property " + formattedName + " to " + javaClassSource.getName());
        javaClassSource.addProperty("String", propertyName);
        addJavaDocForProperty(javaClassSource, propertyName, formattedName);
    }

    public void addCustomTypePropertyWithJavaDoc(JavaClassSource javaClassSource, String propertyName, String formattedName, JavaType<?> javaType) {
        javaClassSource.addProperty(javaType, propertyName);
        addJavaDocForProperty(javaClassSource, propertyName, formattedName);
    }

    private void addJavaDocForProperty(JavaClassSource javaClassSource, String propertyName, String formattedName) {
        // Add Javadoc for get method
        Property<JavaClassSource> property = javaClassSource.getProperty(propertyName);
        MethodSource<JavaClassSource> getMethod = (MethodSource<JavaClassSource>)property.getAccessor();
        getMethod.getJavaDoc().setText(buildGetMethodJavaDocText(formattedName));
        getMethod.getJavaDoc().addTagValue("@return", buildGetMethodReturnJavaDocText(formattedName));

        // Add Javadoc for set method
        MethodSource<JavaClassSource> setMethod = (MethodSource<JavaClassSource>)property.getMutator();
        setMethod.getJavaDoc().setText(buildSetMethodJavaDocText(formattedName));
        setMethod.getJavaDoc().addTagValue("@param", buildSetMethodReturnJavaDocText(propertyName, formattedName));
    }

    private String buildGetMethodJavaDocText(String formattedName) {
        StringBuilder javaDocBuilder = new StringBuilder();
        javaDocBuilder.append("Retrieves the ");
        javaDocBuilder.append(formattedName);
        return javaDocBuilder.toString();
    }

    private String buildGetMethodReturnJavaDocText(String formattedName) {
        StringBuilder javaDocBuilder = new StringBuilder();
        javaDocBuilder.append("The ");
        javaDocBuilder.append(formattedName);
        javaDocBuilder.append(" if it has been defined, otherwise null");
        return javaDocBuilder.toString();
    }

    private String buildSetMethodJavaDocText(String formattedName) {
        StringBuilder javaDocBuilder = new StringBuilder();
        javaDocBuilder.append("Sets the ");
        javaDocBuilder.append(formattedName);
        return javaDocBuilder.toString();
    }

    private String buildSetMethodReturnJavaDocText(String propertyName, String formattedName) {
        StringBuilder javaDocBuilder = new StringBuilder();
        javaDocBuilder.append(propertyName);
        javaDocBuilder.append(" The new ");
        javaDocBuilder.append(formattedName);
        return javaDocBuilder.toString();
    }

}