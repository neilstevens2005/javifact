package org.javifact.message;

import org.javifact.segment.EdifactSeparators;

/**
 * Created by neil on 10/08/15.
 */
public interface Message {

    String getMessageType();

    String toEdifactString();

    String toEdifactString(EdifactSeparators edifactSeparators);

}
