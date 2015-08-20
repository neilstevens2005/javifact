package org.javifact.segment;

//import com.google.common.collect.Lists;

import java.io.Reader;
import java.lang.String;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by neil on 28/04/15.
 */
public class RawSegment extends AbstractSegment implements Segment {

    static private class DataElementComponentValueAndTerminator {
        private enum Terminator {
            DATA_SEPARATOR,
            COMPONENT_SEPARATOR,
            SEGMENT_SEPARATOR,
            END_OF_STRING;
        }

        public DataElementComponentValueAndTerminator(String value, Terminator terminator, int endIndex) {
            this.value = value;
            this.terminator = terminator;
            this.endIndex = endIndex;
        }

        private final String value;
        private final Terminator terminator;
        private final int endIndex;

        public String getValue() {
            return value;
        }

        public Terminator getTerminator() {
            return terminator;
        }

        public int getEndIndex() {
            return endIndex;
        }
    }

    private String segmentType;
    //private final static List<String> SINGLE_EMPTY_STRING_LIST = Lists.newArrayList("");
    private final List<List<String>> componentDataElements = new ArrayList<>();

    private String unaEdifactData;

    public RawSegment(){

    }

    public RawSegment(EdifactSeparators edifactSeparators, String edifactData) {
        segmentType = edifactData.substring(0, 3);

        if (segmentType.equals("UNA")) {
            unaEdifactData = edifactData;
        } else {
            DataElementComponentValueAndTerminator.Terminator terminator;
            int dataIndex = 0;
            int componentIndex = 0;
            int offset = 4;
            do {
                DataElementComponentValueAndTerminator dataElementComponentValueAndTerminator = readNextDataElement(edifactData, offset, edifactSeparators);
                String value = toUnescapedValue(dataElementComponentValueAndTerminator.getValue(), edifactSeparators);
                setComponentDataElement(dataIndex, componentIndex, value);
                terminator = dataElementComponentValueAndTerminator.getTerminator();
                if (terminator == DataElementComponentValueAndTerminator.Terminator.DATA_SEPARATOR) {
                    dataIndex++;
                    componentIndex = 0;
                } else if (terminator == DataElementComponentValueAndTerminator.Terminator.COMPONENT_SEPARATOR) {
                    componentIndex++;
                }
                offset = dataElementComponentValueAndTerminator.getEndIndex() + 1; // +1 to move past separator
            } while (terminator != DataElementComponentValueAndTerminator.Terminator.SEGMENT_SEPARATOR
                    && terminator != DataElementComponentValueAndTerminator.Terminator.END_OF_STRING);
        }
    }

    public String getUnaEdifactData() {
        return unaEdifactData;
    }

    /*public RawSegment(EdifactSeparators edifactSeparators, Reader edifactData) {
        segmentCode = edifactData.substring(0, 3);

        DataElementComponentValueAndTerminator.Terminator terminator;
        int dataIndex = 0;
        int componentIndex = 0;
        int offset = 4;
        do {
            DataElementComponentValueAndTerminator dataElementComponentValueAndTerminator = readNextDataElement(edifactData, offset, edifactSeparators);
            String value = toUnescapedValue(dataElementComponentValueAndTerminator.getValue(), edifactSeparators);
            setComponentDataElement(dataIndex, componentIndex, value);
            terminator = dataElementComponentValueAndTerminator.getTerminator();
            if (terminator == DataElementComponentValueAndTerminator.Terminator.DATA_SEPARATOR) {
                dataIndex++;
                componentIndex = 0;
            } else if (terminator == DataElementComponentValueAndTerminator.Terminator.COMPONENT_SEPARATOR) {
                componentIndex++;
            }
            offset = dataElementComponentValueAndTerminator.getEndIndex() + 1; // +1 to move past separator
        } while (terminator != DataElementComponentValueAndTerminator.Terminator.SEGMENT_SEPARATOR
                && terminator != DataElementComponentValueAndTerminator.Terminator.END_OF_STRING);
    }*/

    private DataElementComponentValueAndTerminator readNextDataElement(String edifactData, int offset, EdifactSeparators edifactSeparators) {
        //StringBuilder dataBuilder = new StringBuilder();
        boolean terminatorFound = false;
        boolean escaped = false;
        int currentIndex = offset;
        DataElementComponentValueAndTerminator.Terminator terminator = null;
        while (currentIndex < edifactData.length() && terminator == null) {
            char chatAtIndex = edifactData.charAt(currentIndex);
            if (escaped) {
                escaped = false;
                currentIndex++;
            } else if (chatAtIndex == edifactSeparators.getReleaseCharacter()) {
                escaped = true;
                currentIndex++;
            } else if (chatAtIndex == edifactSeparators.getDataElementSeparator()) {
                terminator = DataElementComponentValueAndTerminator.Terminator.DATA_SEPARATOR;
            } else if (chatAtIndex == edifactSeparators.getComponentDataElementSeparator()) {
                terminator = DataElementComponentValueAndTerminator.Terminator.COMPONENT_SEPARATOR;
            } else if (chatAtIndex == edifactSeparators.getSegmentTerminator()) {
                terminator = DataElementComponentValueAndTerminator.Terminator.SEGMENT_SEPARATOR;
            } else {
                currentIndex++;
            }
        }

        if (terminator == null) {
            terminator = DataElementComponentValueAndTerminator.Terminator.END_OF_STRING;
        }

        String value = edifactData.substring(offset, currentIndex);
        return new DataElementComponentValueAndTerminator(value, terminator, currentIndex);

    }

    @Override
    public String getSegmentType() {
        return segmentType;
    }

    public void setSegmentType(String segmentType) {
        this.segmentType = segmentType;
    }

    public String getComponentDataElement(int dataElementIndex, int componentIndex) {
        String value;
        if (componentDataElements.size() > dataElementIndex
                && componentDataElements.get(dataElementIndex).size() > componentIndex) {
            value = componentDataElements.get(dataElementIndex).get(componentIndex);
        } else {
            value = null;
        }
        return value;
    }

    public void setComponentDataElement(int dataElementIndex, int componentIndex, String value) {
        while (dataElementIndex >= componentDataElements.size()) {
            componentDataElements.add(new ArrayList<>());
        }

        List<String> dataElementsComponentsForTheDataElement = componentDataElements.get(dataElementIndex);
        while (componentIndex > dataElementsComponentsForTheDataElement.size()) {
            dataElementsComponentsForTheDataElement.add(null);
        }

        if (value == null || value.equals("")) {
            dataElementsComponentsForTheDataElement.add(null);
        } else {
            dataElementsComponentsForTheDataElement.add(value);
        }
    }


    @Override
    protected RawSegment toRawSegment() {
        return this;
    }

    @Override
    public String toEdifactString(EdifactSeparators edifactSeparators) {
        simplyfyComponentDataElements();
        StringBuilder edifactStringBuilder = new StringBuilder();
        edifactStringBuilder.append(segmentType);
        for (List<String> currentDataElement : componentDataElements) {
            char dataElementSeparator = edifactSeparators.getDataElementSeparator();
            edifactStringBuilder.append(dataElementSeparator);
            for (int componentIndex = 0; componentIndex < currentDataElement.size(); componentIndex++) {
                if (componentIndex != 0) {
                    char componentDataElementSeparator = edifactSeparators.getComponentDataElementSeparator();
                    edifactStringBuilder.append(componentDataElementSeparator);
                }
                String value = currentDataElement.get(componentIndex);
                if (value != null) {
                    String escapedValue = toEscappedValue(value, edifactSeparators);
                    edifactStringBuilder.append(escapedValue);
                }
            }
        }
        char segmentTerminator = edifactSeparators.getSegmentTerminator();
        edifactStringBuilder.append(segmentTerminator);
        return edifactStringBuilder.toString();
    }

    private String toUnescapedValue(String escappedValue, EdifactSeparators edifactSeparators) {
        StringBuilder unescappedValueBuilder = new StringBuilder();
        boolean escaped = false;
        for (int index = 0; index < escappedValue.length(); index++) {
            char charAtIndex = escappedValue.charAt(index);
            if (escaped) {
                // TODO: log warning if not separator
                //unescappedValueBuilder.append(charAtIndex);
                escaped = false;
            } else if (charAtIndex == edifactSeparators.getReleaseCharacter()) {
                escaped = true;
            } else {
                unescappedValueBuilder.append(charAtIndex);
            }
        }
        return unescappedValueBuilder.toString();


    }

    private String toEscappedValue(String unescappedValue, EdifactSeparators edifactSeparators) {
        StringBuilder escappedValueBuilder = new StringBuilder();
        for (int index = 0; index < unescappedValue.length(); index++) {
            char charAtIndex = unescappedValue.charAt(index);
            if (charAtIndex == edifactSeparators.getComponentDataElementSeparator()
                    || charAtIndex == edifactSeparators.getDataElementSeparator()
                    || charAtIndex == edifactSeparators.getSegmentTerminator()
                    || charAtIndex == edifactSeparators.getReleaseCharacter()) {
                escappedValueBuilder.append(edifactSeparators.getReleaseCharacter());
            }
            escappedValueBuilder.append(charAtIndex);
        }

        return escappedValueBuilder.toString();
    }


    public static void main(String[] args) {
        EdifactSeparators edifactSeparators = new EdifactSeparators.Builder().build();
        RawSegment rawSegment = new RawSegment(edifactSeparators, "FTX+AFM+1++X?'Path 2.0 Programmer?'s Reference'");
        System.out.println("parsed");
        System.out.println(rawSegment.toEdifactString(edifactSeparators));
    }

    private void simplyfyComponentDataElements() {
        componentDataElements.stream().forEach(RawSegment::removeTrailingNullElements);
        removeTrailingEmptyCollections(componentDataElements);
    }

    // TODO: Move to utility class
    private static void removeTrailingNullElements(List<?> list) {
        boolean elementRemoved;
        do {
            if (!list.isEmpty() && list.get(list.size() - 1) == null) {
                list.remove(list.size() - 1);
                elementRemoved = true;
            } else {
                elementRemoved = false;
            }
        } while (elementRemoved);
    }

    // TODO: move to utility class
    private static void removeTrailingEmptyCollections(List<? extends Collection<?>> list) {
        boolean elementRemoved;
        do {
            if (!list.isEmpty() && list.get(list.size() - 1).isEmpty()) {
                list.remove(list.size() - 1);
                elementRemoved = true;
            } else {
                elementRemoved = false;
            }
        } while (elementRemoved);
    }



}
