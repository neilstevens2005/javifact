package org.javifact.buildsrc.generator.segment;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

/**
 * Created by neil on 23/12/14.
 */
public class SegmentFileReader {

    public SegmentDefinition readSegmentFile(File file) throws IOException {
        SegmentDefinition segmentDefinition = new SegmentDefinition();
        try (BufferedReader br =
                     new BufferedReader(new FileReader(file))) {
            String lineRead;
            List<DataElementDefinition> dataElements = segmentDefinition.getDataElements();
            List<String> currentDataElementComponents = null;

            boolean finishedReadingheader = false;
            boolean startedOfComponentsFound = false;
            StringBuilder titleBuilder = new StringBuilder();
            StringBuilder functionBuilder = new StringBuilder();

            while ((lineRead = br.readLine()) != null) {
                if (finishedReadingheader) {
                    if (!startedOfComponentsFound && lineRead.startsWith("010")) {
                        startedOfComponentsFound = true;
                    }
                    if (startedOfComponentsFound){
                        SegmentLineParser segmentLineParser = new SegmentLineParser(lineRead);
                        if (segmentLineParser.isDataElement()) {
                            String name = segmentLineParser.getName();
                            DataElementDefinition currentDataElementDefinition = new DataElementDefinition(name);
                            dataElements.add(currentDataElementDefinition);
                            currentDataElementComponents = currentDataElementDefinition.getComponentNames();
                        } else if (segmentLineParser.isComponentDataElement()) {
                            String name = segmentLineParser.getName();
                            currentDataElementComponents.add(name);
                        }
                    }
                } else {
                    SegmentHeaderLineParser segmentHeaderLineParser = new SegmentHeaderLineParser(lineRead);
                    if (segmentHeaderLineParser.isTitleLine()) {
                        if (segmentDefinition.getName() == null) {
                            String text = segmentHeaderLineParser.getText();
                            if (!text.isEmpty()) {
                                String segmentName;
                                int titleStartIndex;
                                if (text.startsWith("X ") || text.startsWith("| ") || text.startsWith("+ ") || text.startsWith("* ")) {
                                    // remove the specical charater from the start
                                    text = text.substring(2).trim();
                                }
                                segmentName = text.substring(0, 3);
                                titleStartIndex = 5;
                                segmentDefinition.setName(segmentName);
                                titleBuilder.append(text.substring(titleStartIndex));
                            }
                        } else {
                            titleBuilder.append(' ');
                            titleBuilder.append(segmentHeaderLineParser.getText());
                        }

                    } else if (segmentHeaderLineParser.isFunctionLine()) {
                        if (functionBuilder.length() > 0) {
                            functionBuilder.append(' ');
                        }
                        functionBuilder.append(segmentHeaderLineParser.getText());
                    } else if (functionBuilder.length() > 0){
                        segmentDefinition.setTitle(titleBuilder.toString());
                        segmentDefinition.setFunction(functionBuilder.toString());
                        finishedReadingheader = true;
                    }
                }
            }
        }

        return segmentDefinition;
    }


}
