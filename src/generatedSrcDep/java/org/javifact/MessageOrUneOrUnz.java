package org.javifact;

import org.javifact.message.Message;
import org.javifact.segment.UNE;
import org.javifact.segment.UNZ;

/**
 * Created by neil on 17/08/15.
 */
public class MessageOrUneOrUnz {

    private final Message message;
    private final UNE une;
    private final UNZ unz;

    public MessageOrUneOrUnz(Message message) {
        this.message = message;
        une = null;
        unz = null;
    }

    public MessageOrUneOrUnz(UNE une) {
        message = null;
        this.une = une;
        unz = null;
    }

    public MessageOrUneOrUnz(UNZ unz) {
        message = null;
        une = null;
        this.unz = unz;
    }

    public Message getMessage() {
        return message;
    }

    public UNE getUne() {
        return une;
    }

    public UNZ getUnz() {
        return unz;
    }

    public boolean isMessage() {
        return message != null;
    }

    public boolean isUne() {
        return une != null;
    }

    public boolean isUnz() {
        return unz != null;
    }
}
