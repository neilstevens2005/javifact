package org.javifact.buildsrc.generator.segment;

import org.apache.commons.lang3.StringUtils;

/**
 * Created by neil on 27/12/14.
 */
public class SegmentHeaderLineParser {

    private final boolean titleLine;
    private final boolean functionLine;
    private final String text;

    public SegmentHeaderLineParser(String line) {
        if (StringUtils.isEmpty(line)) {
            titleLine = false;
            functionLine = false;
            text = null;
        } else if (line.trim().startsWith("Function: ")  || line.startsWith("                ") || line.trim().startsWith("| Function: ")) {
            titleLine = false;
            functionLine = true;
            text = line.substring(16).trim();
        } else {
            titleLine = true;
            functionLine = false;
            text = line.trim();
        }
    }

    public boolean isTitleLine() {
        return titleLine;
    }

    public boolean isFunctionLine() {
        return functionLine;
    }

    public String getText() {
        return text;
    }
}
