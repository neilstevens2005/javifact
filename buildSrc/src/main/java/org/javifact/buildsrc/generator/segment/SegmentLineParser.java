package org.javifact.buildsrc.generator.segment;

import org.apache.commons.lang3.StringUtils;

/**
 * Created by neil on 23/12/14.
 */
public class SegmentLineParser {

    private final boolean dataElement;
    private final boolean componentDataElement;
    private final String name;

    public SegmentLineParser(String line) {
        //System.out.println(line);
        if (StringUtils.isBlank(line)) {
            dataElement = false;
            componentDataElement = false;
            name = null;
        } else {
            if (line.startsWith("   ")) {
                componentDataElement = true;
                dataElement = false;
            } else {
                componentDataElement = false;
                dataElement = true;
            }
            if (line.length() > 66) {
                name = line.substring(12, 66).trim();
            } else {
                name = line.substring(12).trim();
            }
        }
    }

    public boolean isDataElement(){
        return dataElement;
    }

    public boolean isComponentDataElement() {
        return componentDataElement;
    }

    public String getName() {
        return name;
    }
}
