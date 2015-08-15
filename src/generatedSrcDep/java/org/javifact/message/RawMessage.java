package org.javifact.message;

import org.javifact.segment.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by neil on 10/08/15.
 */
public class RawMessage extends AbstractMessage implements Message {

    private UNH unh;
    private List<Segment> messageSegments;
    private UNT unt;

    public RawMessage() {

    }

    public RawMessage(UNH unh, List<Segment> messageSegments, UNT unt) {
        this.unh = unh;
        this.messageSegments = new ArrayList<>(messageSegments);
        this.unt = unt;
    }

    public UNH getUnh() {
        return unh;
    }

    public void setUnh(UNH unh) {
        this.unh = unh;
    }

    public List<Segment> getMessageSegments() {
        return messageSegments;
    }

    public void setMessageSegments(List<Segment> messageSegments) {
        this.messageSegments = messageSegments;
    }

    public UNT getUnt() {
        return unt;
    }

    public void setUnt(UNT unt) {
        this.unt = unt;
    }

    @Override
    public String getMessageType() {
        // TODO: get from UNH
        return null;
    }


    @Override
    protected RawMessage toRawMessage() {
        return this;
    }
}
