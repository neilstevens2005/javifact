package org.javifact.buildsrc.generator;

import org.apache.commons.lang3.text.WordUtils;
import org.apache.tools.ant.taskdefs.Java;
import org.javifact.buildsrc.GeneralUtils;
import org.jboss.forge.roaster.model.JavaType;
import org.jboss.forge.roaster.model.Property;
import org.jboss.forge.roaster.model.source.FieldSource;
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
        return toPropertyName(name, false);
    }

    public String toSetterMethodText(String name) {
        StringBuilder setterNameBuilder = new StringBuilder("set");
        setterNameBuilder.append(toPropertyName(name, true));
        return setterNameBuilder.toString();
    }

    public String toSetterMethodText(String name, int index) {
        StringBuilder setterNameBuilder = new StringBuilder("set");
        setterNameBuilder.append(toPropertyName(name, true));
        setterNameBuilder.append(index + 1);
        return setterNameBuilder.toString();
    }

    public String toGetterMethodText(String name) {
        StringBuilder setterNameBuilder = new StringBuilder("get");
        setterNameBuilder.append(toPropertyName(name, true));
        return setterNameBuilder.toString();
    }

    public String toGetterMethodText(String name, int index) {
        StringBuilder setterNameBuilder = new StringBuilder("get");
        setterNameBuilder.append(toPropertyName(name, true));
        setterNameBuilder.append(index + 1);
        return setterNameBuilder.toString();
    }

    private String toPropertyName(String name, boolean capitaliseFirstLetter) {
        StringBuilder propertyNameBuilder = new StringBuilder();
        boolean capitaliseNext = capitaliseFirstLetter;
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

    public void addFinalFieldWithGetter(JavaClassSource javaClassSource, String fieldName, String formattedName, JavaType<?> javaType) {
        // add the final field
        StringBuilder fieldTextBuilder = new StringBuilder();
        fieldTextBuilder.append("private final " );
        fieldTextBuilder.append(javaType.getCanonicalName());
        fieldTextBuilder.append(' ');
        fieldTextBuilder.append(fieldName);
        fieldTextBuilder.append(" = new ");
        fieldTextBuilder.append(javaType.getCanonicalName());
        fieldTextBuilder.append("();");
        String fieldText = fieldTextBuilder.toString();
        javaClassSource.addField(fieldText);

        String getMethodName = toGetterMethodText(fieldName);
        MethodSource<JavaClassSource> getMethod = javaClassSource.addMethod();
        getMethod.setPublic();
        getMethod.setReturnType(javaType);
        getMethod.setName(getMethodName);

        StringBuilder getMethodBodyBuilder = new StringBuilder();
        getMethodBodyBuilder.append("return ");
        getMethodBodyBuilder.append(fieldName);
        getMethodBodyBuilder.append(";\n");
        String getMethodBody = getMethodBodyBuilder.toString();
        getMethod.setBody(getMethodBody);

        getMethod.getJavaDoc().setText(buildGetMethodJavaDocText(formattedName));
        getMethod.getJavaDoc().addTagValue("@return", buildGetMethodReturnJavaDocText(formattedName));
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
