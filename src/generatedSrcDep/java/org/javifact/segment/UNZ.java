package org.javifact.segment;

/**
 * Created by neil on 10/08/15.
 *
 * TODO: Add all the other UNZ fields
 */
public class UNZ extends AbstractSegment implements Segment {

    @Override
    public String getSegmentType() {
        return "UNZ";
    }

    @Override
    protected RawSegment toRawSegment() {
        RawSegment rawSegment = new RawSegment();
        rawSegment.setSegmentType("UNZ");
        return rawSegment;
    }
}
