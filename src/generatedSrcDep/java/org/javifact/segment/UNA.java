package org.javifact.segment;

/**
 * Created by neil on 10/08/15.
 *
 * TODO: actually implement
 */
public class UNA implements Segment {

    private final static int EXPECTED_UNA_EDIFACT_STRING_LENGTH = 9;

    private EdifactSeparators edifactSeparators;

    public UNA() {
        edifactSeparators = new EdifactSeparators.Builder().build();
    }

    public UNA(String edifactString) throws InvalidSegmentException {
        if (edifactString.length() < EXPECTED_UNA_EDIFACT_STRING_LENGTH) {
            throw new InvalidSegmentException("Invalid UNA Edifact data: " + edifactString);
        }
        char componentDataElementSeparator = edifactString.charAt(3);
        char dataElementSeparator = edifactString.charAt(4);
        char decimalNotation = edifactString.charAt(5);
        char releaseCharacter = edifactString.charAt(6);
        char segmentTerminator = edifactString.charAt(8);

        edifactSeparators = new EdifactSeparators.Builder()
                .componentDataElementSeparator(componentDataElementSeparator)
                .dataElementSeparator(dataElementSeparator)
                .decimalMark(decimalNotation)
                .releaseCharacter(releaseCharacter)
                .segmentTerminator(segmentTerminator)
                .build();
    }

    @Override
    public String getSegmentType() {
        return "UNA";
    }

    @Override
    public String toEdifactString(EdifactSeparators ignored) {
        StringBuilder unaBuilder = new StringBuilder("UNA");
        unaBuilder.append(edifactSeparators.getComponentDataElementSeparator());
        unaBuilder.append(edifactSeparators.getDataElementSeparator());
        unaBuilder.append(edifactSeparators.getDecimalMark());
        unaBuilder.append(edifactSeparators.getReleaseCharacter());
        unaBuilder.append(' ');
        unaBuilder.append(edifactSeparators.getSegmentTerminator());
        return unaBuilder.toString();
    }

    public EdifactSeparators getEdifactSeparators() {
        return edifactSeparators;
    }

    public void setEdifactSeparators(EdifactSeparators edifactSeparators) {
        this.edifactSeparators = edifactSeparators;
    }
}
