package org.javifact.message;

import org.javifact.segment.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by neil on 10/08/15.
 */
public class RawMessage extends AbstractMessage implements Message {

    private UNH unh;
    private List<Segment> userDataSegmentsSegments;
    private UNT unt;

    public RawMessage() {

    }

    public RawMessage(UNH unh, List<Segment> messageSegments, UNT unt) {
        this.unh = unh;
        this.userDataSegmentsSegments = new ArrayList<>(messageSegments);
        this.unt = unt;
    }

    public UNH getUnh() {
        return unh;
    }

    public void setUnh(UNH unh) {
        this.unh = unh;
    }

    public List<Segment> getUserDataSegmentsSegments() {
        return userDataSegmentsSegments;
    }

    public void setUserDataSegmentsSegments(List<Segment> userDataSegmentsSegments) {
        this.userDataSegmentsSegments = userDataSegmentsSegments;
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

    @Override
    public void setUnt() {
        UNT unt = new UNT();
        unt.setNumberOfSegmentsInTheMessage(Integer.toString(userDataSegmentsSegments.size()));
        unt.setMessageReferenceNumber(unh.getMessageReferenceNumber());
    }
}
