package org.javifact.segment;

/**
 * Created by neil on 28/04/15.
 */
public interface Segment {

    String getSegmentType();

    String toEdifactString(EdifactSeparators edifactSeparators);

}
