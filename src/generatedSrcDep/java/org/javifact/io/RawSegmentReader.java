package org.javifact.io;

import org.javifact.segment.EdifactSeparators;
import org.javifact.segment.RawSegment;

import java.io.*;

/**
 * Created by neil on 06/07/15.
 */
public class RawSegmentReader extends Reader {

    private final Reader reader;
    private final EdifactSeparators edifactSeparators;

    public RawSegmentReader(Reader reader) {
        this(reader, new EdifactSeparators.Builder().build());
    }

    public RawSegmentReader(Reader reader, EdifactSeparators edifactSeparators) {
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

    public static void main (String[] args) throws IOException {
        String data = "UNB+IATB:1+6XPPC+LHPPC+940101:0950+1'UNH+1+PAORES:93:1:IA'MSG+1:45'IFT+3+XYZCOMPANY AVAILABILITY'ERC+A7V:1:AMD'";
        InputStream inputStream = new ByteArrayInputStream(data.getBytes());
        Reader reader = new InputStreamReader(inputStream);
        RawSegmentReader rawSegmentReader = new RawSegmentReader(reader);
        RawSegment rs1 = rawSegmentReader.readRawSegment();
        RawSegment rs2 = rawSegmentReader.readRawSegment();
        RawSegment rs3 = rawSegmentReader.readRawSegment();
        RawSegment rs4 = rawSegmentReader.readRawSegment();
        RawSegment rs5 = rawSegmentReader.readRawSegment();
        RawSegment rs6 = rawSegmentReader.readRawSegment();
        System.out.println("done");
    }
}
