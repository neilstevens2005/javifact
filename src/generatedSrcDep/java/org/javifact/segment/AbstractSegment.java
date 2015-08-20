package org.javifact.segment;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by neil on 02/08/15.
 */
public abstract class AbstractSegment implements Segment {

    /**
     * Get the segment type
     * @return The segment type
     */
    @Override
    public abstract String getSegmentType();

    /**
     * Create an edifact string representing this segment
     * @param edifactSeparators The separators to use when constructing the edifact string
     * @return The edifact string created
     */
    @Override
    public String toEdifactString(EdifactSeparators edifactSeparators) {
        RawSegment rawSegment = toRawSegment();
        return rawSegment.toEdifactString(edifactSeparators);
    }

    /**
     * Create a raw segment containing the data modelled by this segment
     * @return The raw segment created.
     */
    protected abstract RawSegment toRawSegment();


}
