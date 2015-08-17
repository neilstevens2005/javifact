package org.javifact.io;

import org.javifact.message.Message;
import org.javifact.segment.EdifactSeparators;
import org.javifact.segment.Segment;
import org.javifact.segment.UNA;

import java.io.IOException;
import java.io.Writer;

/**
 * Created by neil on 17/08/15.
 */
public class EdifactWriter extends Writer {

    private final Writer writer;
    private EdifactSeparators edifactSeparators;
    private final boolean overrideUna;

    // TODO: keep track of number of messages and stuff so can easily implement writeUnt, writeUne and writeUnz

    public EdifactWriter(Writer writer) {
        this(writer, new EdifactSeparators.Builder().build(), false);
    }

    public EdifactWriter(Writer writer, EdifactSeparators edifactSeparators, boolean overrideUna) {
        this.writer = writer;
        this.edifactSeparators = edifactSeparators;
        this.overrideUna = overrideUna;
    }

    @Override
    public void write(char[] cbuf, int off, int len) throws IOException {
        writer.write(cbuf, off, len);
    }

    @Override
    public void flush() throws IOException {
        writer.flush();
    }

    @Override
    public void close() throws IOException {
        writer.close();
    }

    public void writeSegment(Segment segment) throws IOException {
        if (segment instanceof UNA && !overrideUna) {
            UNA una = (UNA)segment;
            edifactSeparators = una.getEdifactSeparators();
        }

        String edifactString = segment.toEdifactString(edifactSeparators);
        writer.write(edifactString);
    }

    public void writeMessage(Message message) throws IOException {
        String edifactString = message.toEdifactString(edifactSeparators);
        writer.write(edifactString);
    }

    public void writeUna() throws IOException {
        UNA una = new UNA();
        una.setEdifactSeparators(edifactSeparators);
        writeSegment(una);
    }

    public void writeUne() {

    }

    public void writeUnz() {

    }
}
