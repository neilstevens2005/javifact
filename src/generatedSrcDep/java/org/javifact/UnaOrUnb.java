package org.javifact;

import org.javifact.segment.UNA;
import org.javifact.segment.UNB;

/**
 * Created by neil on 17/08/15.
 */
public class UnaOrUnb {

    private final UNA una;
    private final UNB unb;

    public UnaOrUnb(UNA una) {
        this.una = una;
        unb = null;
    }

    public UnaOrUnb(UNB unb) {
        una = null;
        this.unb = unb;
    }

    public UNA getUna() {
        return una;
    }

    public UNB getUnb() {
        return unb;
    }

    public boolean isUna() {
        return una != null;
    }

    public boolean isUnb() {
        return unb != null;
    }

}


