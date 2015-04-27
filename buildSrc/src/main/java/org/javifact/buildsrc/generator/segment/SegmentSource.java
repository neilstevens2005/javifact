package org.javifact.buildsrc.generator.segment;

import java.util.List;

/**
 * Created by neil on 30/12/14.
 */
public class SegmentSource {

    private final String segmentSource;
    private final List<String> compositeDataElementSources;

    public SegmentSource(String segmentSource, List<String> compositeDataElementSources) {
        this.segmentSource = segmentSource;
        this.compositeDataElementSources = compositeDataElementSources;
    }
}
