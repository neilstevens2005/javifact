package org.javifact.buildsrc.generator;

import org.javifact.buildsrc.generator.segment.InvalidSegmentException;
import org.javifact.buildsrc.generator.segment.SegmentDefinition;
import org.javifact.buildsrc.generator.segment.SegmentFileReader;
import org.javifact.buildsrc.generator.segment.SegmentSourceGenerator;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;

/**
 * Created by neil on 05/04/15.
 */
public class SourceGenerator {

    private static final String DATA_ELEMENT_PACKAGE = "org.javifact.dataelement";
    private static final String SEGMENT_PACAKAGE = "org.javifact.segment";
    private static final String MESSAGE_PACKAGE = "org.javifact.message";
    private final static String ENCODING = "UTF-8";

    private final Path segmentDefinitionDirectory;
    private final Path messageDefinitionDirectory;
    private final Path generatedSourceDirectory;
    private final String subPackageName;

    public SourceGenerator(Path segmentDefinitionDirectory, Path messageDefinitionDirectory, Path generatedSourceDirectory, String subPackageName) {
        this.segmentDefinitionDirectory = segmentDefinitionDirectory;
        this.messageDefinitionDirectory = messageDefinitionDirectory;
        this.generatedSourceDirectory = generatedSourceDirectory;
        this.subPackageName = subPackageName;
    }

    public void generate() throws IOException, InvalidSegmentException {
        System.out.println("generate " + segmentDefinitionDirectory);
        Path outDir = makeOutputDir();
        SegmentFileReader segmentFileReader = new SegmentFileReader();

        String packageName;
        if (subPackageName == null) {
            packageName = SEGMENT_PACAKAGE;
        } else {
            packageName = SEGMENT_PACAKAGE + "." + subPackageName;
        }

        SegmentSourceGenerator segmentSourceGenerator = new SegmentSourceGenerator(new CommonSourceGenerator(), packageName);
        for (File segmentDefinitionFile : segmentDefinitionDirectory.toFile().listFiles()) {
            //if (segmentDefinitionFile.getName().equals("cux.txt")) {
            System.out.println("Reading segment definition file:" + segmentDefinitionFile.getName());
            SegmentDefinition segmentDefinition = segmentFileReader.readSegmentFile(segmentDefinitionFile);
            System.out.println("Generating source for segment " + segmentDefinition.getName());
            String segmentSource = segmentSourceGenerator.generateSource(segmentDefinition);
            Path outputSourceFile = buildJavaSegmentFilePath(outDir, segmentDefinition);
            Files.write(outputSourceFile, segmentSource.getBytes(ENCODING));
            //System.out.println(segmentSource);
            //}
        }
    }

    private Path makeOutputDir() throws IOException {
        //Path classOutputDir = Paths.get(generatedSourceDirectory);
        System.out.println("Path = " + generatedSourceDirectory);
        String[] packageSubDirectories;
        if (subPackageName == null) {
            packageSubDirectories = SEGMENT_PACAKAGE.split("\\.");
        } else {
            packageSubDirectories = (SEGMENT_PACAKAGE + "." + subPackageName).split("\\.");
        }
        Path packageSubDir = Paths.get(packageSubDirectories[0], Arrays.copyOfRange(packageSubDirectories, 1, packageSubDirectories.length));
        Path path = generatedSourceDirectory.resolve(packageSubDir);
        if (Files.exists(path)) {
            if (!Files.isDirectory(path)) {
                Files.delete(path);
                Files.createDirectory(path);
            }
        } else {
            Files.createDirectories(path);
            System.out.println("created dir: " + path);
        }
        System.out.println("Output dir = " + path);
        return path;
    }

    private Path buildJavaSegmentFilePath(Path segmentOutputDir, SegmentDefinition segmentDefinition) {
        String segmentName = segmentDefinition.getName().toUpperCase();
        String filename = new StringBuilder(segmentName).append(".java").toString();
        System.out.println("Filename = " + segmentOutputDir.resolve(Paths.get(filename)));
        return segmentOutputDir.resolve(Paths.get(filename));
    }
}
