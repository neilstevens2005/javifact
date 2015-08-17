package org.javifact.message;

import org.javifact.segment.*;

import java.util.List;

/**
 * Created by neil on 15/08/15.
 */
public abstract class AbstractMessage implements Message {

    protected abstract RawMessage toRawMessage();

    @Override
    public String toEdifactString() {
        return toEdifactString(new EdifactSeparators.Builder().build());
    }

    @Override
    public String toEdifactString(EdifactSeparators edifactSeparators) {
        RawMessage rawMessage = toRawMessage();
        StringBuilder edifactStringBuilder = new StringBuilder();
        UNH unh = rawMessage.getUnh();
        edifactStringBuilder.append(unh.toEdifactString(edifactSeparators));
        List<Segment> messageSegments = rawMessage.getUserDataSegmentsSegments();
        for (Segment segment : messageSegments) {
            edifactStringBuilder.append(segment.toEdifactString(edifactSeparators));
        }
        UNT unt = rawMessage.getUnt();
        edifactStringBuilder.append(unt.toEdifactString(edifactSeparators));
        return edifactStringBuilder.toString();
    }
}
