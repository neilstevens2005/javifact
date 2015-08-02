package org.javifact.segment;

/**
 * Created by neil on 02/08/15.
 */
public abstract class AbstractSegment {

    //public abstract String getSegmentType();

    public abstract RawSegment toRawSegment();

    public String toEdifactString(EdifactSeparators edifactSeparators) {
        RawSegment rawSegment = toRawSegment();
        return rawSegment.toEdifactString(edifactSeparators);
    }
}
