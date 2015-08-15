package org.javifact.segment;

/**
 * Created by neil on 10/08/15.
 */
public class UNT extends AbstractSegment implements Segment {

    public UNT() {

    }

    public UNT(RawSegment rawSegment) {

    }

    @Override
    public String getSegmentType() {
        return "UNT";
    }

    @Override
    protected RawSegment toRawSegment() {
        RawSegment rawSegment = new RawSegment();
        rawSegment.setSegmentType("UNT");
        return rawSegment;
    }
}
