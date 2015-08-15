package org.javifact.segment;

/**
 * Created by neil on 10/08/15.
 *
 * TODO: actually implement
 */
public class UNA implements Segment {
    @Override
    public String getSegmentType() {
        return "UNA";
    }

    @Override
    public String toEdifactString(EdifactSeparators edifactSeparators) {
        return "";
    }
}
