package org.javifact.buildsrc.generator.segment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by neil on 23/12/14.
 */
public class SegmentDefinition {

    private String name;
    private String title;
    private String function;
    private final List<DataElementDefinition> dataElements = new ArrayList<>();



    public String getName() {
        return name;
    }

    public String getTitle() {
        return title;
    }

    public String getFunction() {
        return function;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setFunction(String function) {
        this.function = function;
    }

    public List<DataElementDefinition> getDataElements() {
        return dataElements;
    }
}
