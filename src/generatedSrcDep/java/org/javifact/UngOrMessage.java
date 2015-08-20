package org.javifact;

import org.javifact.message.Message;
import org.javifact.segment.UNG;

/**
 * Created by neil on 17/08/15.
 */
public class UngOrMessage {

    private final UNG ung;
    private final Message message;

    public UngOrMessage(UNG ung) {
        this.ung = ung;
        message = null;
    }

    public UngOrMessage(Message message) {
        ung = null;
        this.message = message;
    }

    public UNG getUng() {
        return ung;
    }

    public Message getMessage() {
        return message;
    }

    public boolean isUng() {
        return ung != null;
    }

    public boolean isMessage() {
        return message != null;
    }

}
