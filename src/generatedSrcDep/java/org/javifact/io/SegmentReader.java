package org.javifact.io;

import org.javifact.segment.EdifactSeparators;
import org.javifact.segment.RawSegment;

import java.io.*;
import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.List;

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
        //String data = "UNB+IATB:1+6XPPC+LHPPC+940101:0950+1'UNH+1+PAORES:93:1:IA'MSG+1:45'IFT+3+XYZCOMPANY AVAILABILITY'ERC+A7V:1:AMD'";
        //String data = "MSG+1:45'IFT+3+XYZCOMPANY AVAILABILITY'ERC+A7V:1:AMD'";
        String data = "UNB+UNOA:1+005435656:1+006415160:1+060515:1434+00000000000778'\n" +
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
                "UNT+23+00000000000117'\n" +
                "UNZ+1+00000000000778'";
        InputStream inputStream = new ByteArrayInputStream(data.getBytes());
        Reader reader = new InputStreamReader(inputStream);
        SegmentReader rawSegmentReader = new SegmentReader(reader);
        List<Object> segments = new ArrayList<Object>();
        try {
            while (true) {
                segments.add(rawSegmentReader.readSegment());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("done");
    }
}
