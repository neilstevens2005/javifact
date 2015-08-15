package org.javifact.segment;

/**
 * Created by neil on 10/08/15.
 */
public class UNH extends AbstractSegment implements Segment{

    public UNH() {

    }

    public UNH(RawSegment rawSegment) {

    }

    @Override
    public String getSegmentType() {
        return "UNH";
    }

    @Override
    protected RawSegment toRawSegment() {
        RawSegment rawSegment = new RawSegment();
        rawSegment.setSegmentType("UNH");
        return rawSegment;
    }
}
