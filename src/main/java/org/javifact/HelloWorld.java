package org.javifact;

import org.javifact.io.EdifactReader;
import org.javifact.io.EdifactWriter;
import org.javifact.message.Message;
import org.javifact.segment.*;
import org.javifact.segment.d96a.BGM;

import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by neil on 05/05/15.
 */
public class HelloWorld {

    private static final String INPUT_FILE_1 = "/home/neil/edifact.txt";
    private static final String INPUT_FILE_2 = "/home/neil/edifact.txt";

    public static void main(String[] args) throws IOException, UnexpectedSegmentException {
        //readFile();
        //generateFile();
        //splitFile();
        //combineFiles();
        changeSeparators();
    }

    private static void readFile() throws IOException, UnexpectedSegmentException {
        UNA una;
        UNB unb;
        List<Message> messages = new ArrayList<>();
        UNZ unz;
        FileReader fileReader = new FileReader(INPUT_FILE_1);
        EdifactReader edifactReader = new EdifactReader(fileReader, "d96a");

        // Get UNA and UNB
        UnaOrUnb unaOrUnb = edifactReader.getUnaOrUnb();
        if (unaOrUnb.isUna()) {
            una = unaOrUnb.getUna();
            unb = (UNB)edifactReader.readSegment();
        } else {
            unb = unaOrUnb.getUnb();
        }

        // Get messages
        MessageOrUneOrUnz messageOrUneOrUnz = edifactReader.getMessageOrUneOrUnz();
        while (messageOrUneOrUnz.isMessage()) {
            messages.add(messageOrUneOrUnz.getMessage());
            messageOrUneOrUnz = edifactReader.getMessageOrUneOrUnz();
        }

        unz = messageOrUneOrUnz.getUnz();

        System.out.println("stop with debugger here");
    }

    private static void generateFile() throws IOException {
        PrintWriter printWriter = new PrintWriter(System.out);
        EdifactWriter edifactWriter = new EdifactWriter(printWriter, true);
        edifactWriter.writeUna();
        UNB unb = new UNB();
        unb.setInterchangeControlAndReference("My interchange");
        edifactWriter.writeSegment(unb);
        UNH unh = new UNH();
        unh.setMessageReferenceNumber("My Message");
        edifactWriter.writeSegment(unh);
        BGM bgm = new BGM();
        bgm.setMessageFunctionCoded("MFC");
        edifactWriter.writeSegment(bgm);
        edifactWriter.writeUnt();
        edifactWriter.writeUnz();
        edifactWriter.close();
    }

    private static void splitFile() {

    }

    private static void combineFiles() {

    }

    private static void changeSeparators() throws IOException, UnexpectedSegmentException {
        PrintWriter printWriter = new PrintWriter(System.out);
        EdifactWriter edifactWriter = new EdifactWriter(printWriter, true);
        FileReader fileReader = new FileReader(INPUT_FILE_1);
        EdifactReader edifactReader = new EdifactReader(fileReader, "d96a");

        // create new separators and write UNA
        EdifactSeparators newSeps = new EdifactSeparators.Builder().componentDataElementSeparator('a').dataElementSeparator('b').
                segmentTerminator('c').releaseCharacter('d').build();
        UNA una = new UNA();
        una.setEdifactSeparators(newSeps);
        edifactWriter.writeSegment(una);

        // Get UNB and write it
        UnaOrUnb unaOrUnb = edifactReader.getUnaOrUnb();
        UNB unb;
        if (unaOrUnb.isUna()) {
            unb = (UNB)edifactReader.readSegment();
        } else {
            unb = unaOrUnb.getUnb();
        }
        edifactWriter.writeSegment(unb);

        // Get messages and write them
        MessageOrUneOrUnz messageOrUneOrUnz = edifactReader.getMessageOrUneOrUnz();
        while (messageOrUneOrUnz.isMessage()) {
            edifactWriter.writeMessage(messageOrUneOrUnz.getMessage());
            messageOrUneOrUnz = edifactReader.getMessageOrUneOrUnz();
        }

        // get UNZ and write it
        UNZ unz = messageOrUneOrUnz.getUnz();
        edifactWriter.writeSegment(unz);
        edifactWriter.close();
    }

}
