package org.javifact;

import org.javifact.message.RawMessage;
import org.javifact.segment.UNE;
import org.javifact.segment.UNZ;

/**
 * Created by neil on 17/08/15.
 */
public class RawMessageOrUneOrUnz {

    private final RawMessage rawMessage;
    private final UNE une;
    private final UNZ unz;

    public RawMessageOrUneOrUnz(RawMessage rawMessage) {
        this.rawMessage = rawMessage;
        une = null;
        unz = null;
    }

    public RawMessageOrUneOrUnz(UNE une) {
        rawMessage = null;
        this.une = une;
        unz = null;
    }

    public RawMessageOrUneOrUnz(UNZ unz) {
        rawMessage = null;
        une = null;
        this.unz = unz;
    }

    public RawMessage getRawMessage() {
        return rawMessage;
    }

    public UNE getUne() {
        return une;
    }

    public UNZ getUnz() {
        return unz;
    }

    public boolean isRawMessage() {
        return rawMessage != null;
    }

    public boolean isUne() {
        return une != null;
    }

    public boolean isUnz() {
        return unz != null;
    }

}
