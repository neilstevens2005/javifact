package org.javifact.buildsrc.generator.segment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by neil on 23/12/14.
 */
public class DataElementDefinition {

    private String name;
    private final List<String> componentNames = new ArrayList<>();

    public DataElementDefinition(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<String> getComponentNames() {
        return componentNames;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        DataElementDefinition that = (DataElementDefinition) o;

        if (componentNames != null ? !componentNames.equals(that.componentNames) : that.componentNames != null)
            return false;
        if (name != null ? !name.equals(that.name) : that.name != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = name != null ? name.hashCode() : 0;
        result = 31 * result + (componentNames != null ? componentNames.hashCode() : 0);
        return result;
    }
}
