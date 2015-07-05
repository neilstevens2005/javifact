package org.javifact.segment;

/**
 * Created by neil on 28/04/15.
 */
public class EdifactSeparators {

    private final char segmentTerminator;
    private final char dataElementSeparator;
    private final char componentDataElementSeparator;
    private final char decimalMark;
    private final char releaseCharacter;

    private EdifactSeparators(char segmentTerminator, char dataElementSeparator, char componentDataElementSeparator, char decimalMark, char releaseCharacter) {
        this.segmentTerminator = segmentTerminator;
        this.dataElementSeparator = dataElementSeparator;
        this.componentDataElementSeparator = componentDataElementSeparator;
        this.decimalMark = decimalMark;
        this.releaseCharacter = releaseCharacter;
    }

    public char getSegmentTerminator() {
        return segmentTerminator;
    }

    public char getDataElementSeparator() {
        return dataElementSeparator;
    }

    public char getComponentDataElementSeparator() {
        return componentDataElementSeparator;
    }

    public char getDecimalMark() {
        return decimalMark;
    }

    public char getReleaseCharacter() {
        return releaseCharacter;
    }

    public static class Builder {

        private char segmentTerminator = '\'';
        private char dataElementSeparator = '+';
        private char componentDataElementSeparator = ':';
        private char decimalMark = '.';
        private char releaseCharacter = '?';

        public void segmentTerminator(char segmentTerminator) {
            this.segmentTerminator = segmentTerminator;
        }

        public void dataElementSeparator(char dataElementSeparator) {
            this.dataElementSeparator = dataElementSeparator;
        }

        public void componentDataElementSeparator(char componentDataElementSeparator) {
            this.componentDataElementSeparator = componentDataElementSeparator;
        }

        public void decimalMark(char decimalMark) {
            this.decimalMark = decimalMark;
        }

        public void releaseCharacter(char releaseCharacter) {
            this.releaseCharacter = releaseCharacter;
        }

        public EdifactSeparators build() {
            return new EdifactSeparators(segmentTerminator, dataElementSeparator, componentDataElementSeparator, decimalMark, releaseCharacter);
        }
    }
}
