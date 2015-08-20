package org.javifact;

import org.javifact.message.Message;
import org.javifact.message.RawMessage;
import org.javifact.segment.UNG;

/**
 * Created by neil on 17/08/15.
 */
public class UngOrRawMessage {

    private final UNG ung;
    private final RawMessage rawMessage;

    public UngOrRawMessage(UNG ung) {
        this.ung = ung;
        rawMessage = null;
    }

    public UngOrRawMessage(RawMessage rawMessage) {
        ung = null;
        this.rawMessage = rawMessage;
    }

    public UNG getUng() {
        return ung;
    }

    public Message getRawMessage() {
        return rawMessage;
    }

    public boolean isUng() {
        return ung != null;
    }

    public boolean isRawMessage() {
        return rawMessage != null;
    }

}
