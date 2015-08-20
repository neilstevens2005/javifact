package org.javifact.io;

import org.javifact.message.Message;
import org.javifact.segment.*;

import java.io.IOException;
import java.io.Writer;

/**
 * Created by neil on 17/08/15.
 */
public class EdifactWriter extends Writer {

    private final Writer writer;
    private EdifactSeparators edifactSeparators;
    private final boolean overrideUna;
    private final boolean appendNewLineAfterEachSegment;

    private String currentMessagereferenceNumber;
    private int currentNumberOfSegmentsInMessage;

    private String currentGroupReferenceNumber;
    private int currentGroupControlCount;

    private String currentInterchangeControlReference;
    private int currentInterchangeControlCount;

    public EdifactWriter(Writer writer) {
        this(writer, new EdifactSeparators.Builder().build(), false, false);
    }

    public EdifactWriter(Writer writer, boolean appendNewLineAfterEachSegment) {
        this(writer, new EdifactSeparators.Builder().build(), false, appendNewLineAfterEachSegment);
    }

    public EdifactWriter(Writer writer, EdifactSeparators edifactSeparators, boolean overrideUna, boolean appendNewLineAfterEachSegment) {
        this.writer = writer;
        this.edifactSeparators = edifactSeparators;
        this.overrideUna = overrideUna;
        this.appendNewLineAfterEachSegment = appendNewLineAfterEachSegment;
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
        // handle UNA specially
        if (segment instanceof UNA && !overrideUna) {
            UNA una = (UNA)segment;
            edifactSeparators = una.getEdifactSeparators();
        }

        // handle UNB specially
        if (segment instanceof UNB) {
            UNB unb = (UNB)segment;
            currentInterchangeControlReference = unb.getInterchangeControlAndReference();
            currentInterchangeControlCount = 0;
        }

        // handle UNG specially
        if (segment instanceof UNG) {
            UNG ung = (UNG)segment;
            currentGroupReferenceNumber = ung.getGroupReferenceNumber();
            currentGroupControlCount = 0;
        }

        // handle UNH specially
        if (segment instanceof UNH) {
            UNH unh = (UNH)segment;
            currentMessagereferenceNumber = unh.getMessageReferenceNumber();
            currentNumberOfSegmentsInMessage = 0;

            // if not using group the intechange control count will be the number of messages
            if (currentGroupReferenceNumber == null) {
                currentInterchangeControlCount++;
            } else {
                currentGroupControlCount++;
            }
        }

        String edifactString = segment.toEdifactString(edifactSeparators);
        writer.write(edifactString);
        if (appendNewLineAfterEachSegment) {
            writer.append('\n');
        }
        currentNumberOfSegmentsInMessage++;
    }

    public void writeMessage(Message message) throws IOException {
        String edifactString = message.toEdifactString(edifactSeparators, appendNewLineAfterEachSegment);
        writer.write(edifactString);

        // if not using group the intechange control count will be the number of messages
        if (currentGroupReferenceNumber == null) {
            currentInterchangeControlCount++;
        } else {
            currentGroupControlCount++;
        }
    }

    public void writeUna() throws IOException {
        UNA una = new UNA();
        una.setEdifactSeparators(edifactSeparators);
        writeSegment(una);
    }

    public void writeUnt() throws IOException {
        UNT unt = new UNT();
        unt.setMessageReferenceNumber(currentMessagereferenceNumber);
        // + 1 to include this segment in the segment count
        unt.setNumberOfSegmentsInTheMessage(Integer.toString(currentNumberOfSegmentsInMessage + 1));
        writeSegment(unt);
    }

    public void writeUne() throws IOException {
        UNE une = new UNE();
        une.setFunctionalGroupReferenceNumber(currentGroupReferenceNumber);
        une.setNumberOfMessages(Integer.toString(currentGroupControlCount));
        writeSegment(une);
    }

    public void writeUnz() throws IOException {
        UNZ unz = new UNZ();
        unz.setInterchangeControlReference(currentInterchangeControlReference);
        unz.setInterchangeControlCount(Integer.toString(currentInterchangeControlCount));
        writeSegment(unz);
    }


}
