package org.javifact.io;

import org.javifact.segment.EdifactSeparators;
import org.javifact.segment.RawSegment;

import java.io.*;
import java.lang.reflect.Constructor;

/**
 * Created by neil on 02/08/15.
 */
public class SegmentReader extends Reader {

    private final static String SEGMENT_PACKAGE = "org.javifact.segment";

    private final RawSegmentReader rawSegmentReader;

    public SegmentReader(Reader reader) {
        this(reader, new EdifactSeparators.Builder().build());
    }

    public SegmentReader(RawSegmentReader rawSegmentReader) {
        super(rawSegmentReader);
        this.rawSegmentReader = rawSegmentReader;
    }

    public SegmentReader(Reader reader, EdifactSeparators edifactSeparators) {
        super(reader);
        rawSegmentReader = new RawSegmentReader(reader, edifactSeparators);
    }

    @Override
    public int read(char[] cbuf, int off, int len) throws IOException {
        return rawSegmentReader.read(cbuf, off, len);
    }

    @Override
    public void close() throws IOException {
        rawSegmentReader.close();
    }

    public Object readSegment() throws IOException {
        RawSegment rawSegment = rawSegmentReader.readRawSegment();
        String segmentName = rawSegment.getSegmentCode();
        String qualifiedSegmentClassName = SEGMENT_PACKAGE + "." + segmentName.toUpperCase();
        Object segment;
        try {
            Class<?> segmentClass = Class.forName(qualifiedSegmentClassName);
            Constructor<?> constructor = segmentClass.getConstructor(RawSegment.class);
            segment = constructor.newInstance(rawSegment);
        } catch (ClassNotFoundException c) {
            segment = rawSegment;
        } catch (Exception e) {
            throw new AssertionError(e);
        }
        return segment;
    }

    public static void main (String[] args) throws IOException, ClassNotFoundException {
        String data = "UNB+IATB:1+6XPPC+LHPPC+940101:0950+1'UNH+1+PAORES:93:1:IA'MSG+1:45'IFT+3+XYZCOMPANY AVAILABILITY'ERC+A7V:1:AMD'";
        //String data = "MSG+1:45'IFT+3+XYZCOMPANY AVAILABILITY'ERC+A7V:1:AMD'";
        InputStream inputStream = new ByteArrayInputStream(data.getBytes());
        Reader reader = new InputStreamReader(inputStream);
        SegmentReader rawSegmentReader = new SegmentReader(reader);
        Object rs1 = rawSegmentReader.readSegment();
        Object rs2 = rawSegmentReader.readSegment();
        Object rs3 = rawSegmentReader.readSegment();
        Object rs4 = rawSegmentReader.readSegment();
        Object rs5 = rawSegmentReader.readSegment();
        Object rs6 = rawSegmentReader.readSegment();
        System.out.println("done");
    }
}
