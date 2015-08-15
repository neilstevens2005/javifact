package org.javifact.io;

import org.javifact.message.InvalidMessageException;
import org.javifact.message.RawMessage;
import org.javifact.segment.EdifactSeparators;
import org.javifact.segment.Segment;
import org.javifact.segment.UNH;
import org.javifact.segment.UNT;

import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by neil on 10/08/15.
 */
public class RawMessageReader extends SegmentReader {
    public RawMessageReader(Reader reader) {
        super(reader);
    }

    public RawMessageReader(RawSegmentReader rawSegmentReader) {
        super(rawSegmentReader);
    }

    public RawMessageReader(Reader reader, EdifactSeparators edifactSeparators) {
        super(reader, edifactSeparators);
    }

    public RawMessage readRawMessage() throws IOException, InvalidMessageException{
        Segment firstSegment = readSegment();
        if (!(firstSegment instanceof UNH)) {
            throw new InvalidMessageException("Message does not begin with UNH");
        }
        UNH unh = (UNH)firstSegment;

        UNT unt = null;
        List<Segment> messageSegments = new ArrayList<>();
        do {
            Segment segment = readSegment();
            if (segment instanceof UNT) {
                unt = (UNT) segment;
            } else {
                messageSegments.add(segment);
            }
        } while (unt == null);

        return new RawMessage(unh, messageSegments, unt);
    }
}
