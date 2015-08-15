package org.javifact.io;

import org.javifact.message.InvalidMessageException;
import org.javifact.message.RawMessage;
import org.javifact.segment.*;

import java.io.*;
import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by neil on 10/08/15.
 * TODO: this should be all the reader classes merged together
 */
public class EdifactReader extends Reader {

    private final static String SEGMENT_PACKAGE = "org.javifact.segment";

    private final Reader reader;
    private final EdifactSeparators edifactSeparators;

    public EdifactReader(Reader reader) {
        this(reader, new EdifactSeparators.Builder().build());
    }

    public EdifactReader(Reader reader, EdifactSeparators edifactSeparators) {
        super(reader);
        this.reader = reader;
        this.edifactSeparators = edifactSeparators;
    }

    @Override
    public int read(char[] cbuf, int off, int len) throws IOException {
        return reader.read(cbuf, off, len);
    }

    @Override
    public void close() throws IOException {
        reader.close();
    }

    public RawSegment readRawSegment() throws IOException {
        StringBuilder segmentDataBuilder = new StringBuilder();
        int dataRead;
        boolean escaped;
        do {
            dataRead = read();
            if (dataRead == -1) {
                throw new IOException("Stream closed");
            }
            char charData = (char) dataRead;
            if (charData == edifactSeparators.getReleaseCharacter()) {
                escaped = true;
            } else {
                escaped = false;
            }
            segmentDataBuilder.append(charData);
        } while (dataRead != edifactSeparators.getSegmentTerminator() && !escaped);
        String segmentData = segmentDataBuilder.toString().trim();
        return new RawSegment(edifactSeparators, segmentData);
    }

    public Segment readSegment() throws IOException {
        RawSegment rawSegment = readRawSegment();
        String segmentName = rawSegment.getSegmentType();
        String qualifiedSegmentClassName = SEGMENT_PACKAGE + "." + segmentName.toUpperCase();
        Segment segment;
        try {
            Class<? extends Segment> segmentClass = (Class<? extends Segment>)(Class.forName(qualifiedSegmentClassName));
            Constructor<? extends Segment> constructor = segmentClass.getConstructor(RawSegment.class);
            segment = constructor.newInstance(rawSegment);
        } catch (ClassNotFoundException c) {
            segment = rawSegment;
        } catch (Exception e) {
            throw new AssertionError(e);
        }
        return segment;
    }

    public RawMessage readRawMessage() throws IOException, InvalidMessageException {
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

    public static void main(String[] args) throws IOException, InvalidMessageException {
        String data = //"UNB+UNOA:1+005435656:1+006415160:1+060515:1434+00000000000778'\n" +
                "UNH+00000000000117+INVOIC:D:97B:UN'\n" +
                "BGM+380+342459+9'\n" +
                "DTM+3:20060515:102'\n" +
                "RFF+ON:521052'\n" +
                "NAD+BY+792820524::16++CUMMINS MID-RANGE ENGINE PLANT'\n" +
                "NAD+SE+005435656::16++GENERAL WIDGET COMPANY'\n" +
                "CUX+1:USD'\n" +
                "LIN+1++157870:IN'\n" +
                "IMD+F++:::WIDGET'\n" +
                "QTY+47:1020:EA'\n" +
                "ALI+US'\n" +
                "MOA+203:1202.58'\n" +
                "PRI+INV:1.179'\n" +
                "LIN+2++157871:IN'\n" +
                "IMD+F++:::DIFFERENT WIDGET'\n" +
                "QTY+47:20:EA'\n" +
                "ALI+JP'\n" +
                "MOA+203:410'\n" +
                "PRI+INV:20.5'\n" +
                "UNS+S'\n" +
                "MOA+39:2137.58'\n" +
                "ALC+C+ABG'\n" +
                "MOA+8:525'\n" +
                "UNT+23+00000000000117'\n";// +
                //"UNZ+1+00000000000778'";
        InputStream inputStream = new ByteArrayInputStream(data.getBytes());
        Reader reader = new InputStreamReader(inputStream);
        EdifactReader edifactReader = new EdifactReader(reader);
        RawMessage rawMessage = edifactReader.readRawMessage();
        System.out.println(rawMessage.toEdifactString());
    }
}
