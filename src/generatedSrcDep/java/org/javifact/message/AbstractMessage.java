package org.javifact.message;

import org.javifact.segment.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by neil on 15/08/15.
 */
public abstract class AbstractMessage implements Message {

    protected abstract RawMessage toRawMessage();

    @Override
    public String toEdifactString() {
        return toEdifactString(new EdifactSeparators.Builder().build(), false);
    }

    @Override
    public String toEdifactString(EdifactSeparators edifactSeparators) {
        return toEdifactString(edifactSeparators, false);
    }

    @Override
    public String toEdifactString(boolean appendNewLineAfterEachSegment) {
        return toEdifactString(new EdifactSeparators.Builder().build(), appendNewLineAfterEachSegment);
    }

    @Override
    public String toEdifactString(EdifactSeparators edifactSeparators, boolean appendNewLineAfterEachSegment) {
        RawMessage rawMessage = toRawMessage();
        StringBuilder edifactStringBuilder = new StringBuilder();
        UNH unh = rawMessage.getUnh();
        edifactStringBuilder.append(unh.toEdifactString(edifactSeparators));
        List<Segment> messageSegments = rawMessage.getUserDataSegmentsSegments();
        for (Segment segment : messageSegments) {
            edifactStringBuilder.append(segment.toEdifactString(edifactSeparators));
            if (appendNewLineAfterEachSegment) {
                edifactStringBuilder.append('\n');
            }
        }
        UNT unt = rawMessage.getUnt();
        edifactStringBuilder.append(unt.toEdifactString(edifactSeparators));
        return edifactStringBuilder.toString();
    }

    //@Override
    public List<Segment> getSegments() {
        RawMessage rawMessage = toRawMessage();
        UNH unh = rawMessage.getUnh();
        List<Segment> userDataSegments = rawMessage.getUserDataSegmentsSegments();
        UNT unt = rawMessage.getUnt();
        List<Segment> segments = new ArrayList<>(userDataSegments.size() + 2);
        segments.add(unh);
        segments.addAll(userDataSegments);
        segments.add(unt);
        return segments;
    }
}
