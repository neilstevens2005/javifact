package org.javifact.message;

import org.javifact.segment.EdifactSeparators;
import org.javifact.segment.Segment;

import java.util.List;

/**
 * Created by neil on 10/08/15.
 */
public interface Message {

    String getMessageType();

    String toEdifactString();

    String toEdifactString(EdifactSeparators edifactSeparators);

    String toEdifactString(boolean appendNewLineAfterEachSegment);

    String toEdifactString(EdifactSeparators edifactSeparators, boolean appendNewLineAfterEachSegment);

    void setUnt();

}
