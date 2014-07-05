package com.willluffy.statistics.validator;

import com.google.common.annotations.VisibleForTesting;

import com.willluffy.statistics.report.Results;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by dongshuwang on 14-7-5.
 */

@Service
public class FileValidator implements Validator {

    private static final Logger logger = LoggerFactory.getLogger(FileValidator.class);

    @Value("${following.lines}")
    private Integer extraLineCount;

    @Autowired
    @Qualifier("scan-for")
    private RegexValidator scanForValidator;

    @Autowired(required = false)
    @Qualifier("exclude")
    private RegexValidator excludeValidator;

    @Autowired
    private FileAgeValidator fileAgeValidator;

    @Autowired
    private Results results;

    @Override
    public <T> boolean validate(T obj) {

        boolean retVal = false;
        File file = (File) obj;
        if (fileAgeValidator.validate(file)) {
            results.addFile(file.getPath());
            checkFile(file);
            retVal = true;
        }
        return retVal;
    }

    private void checkFile(File file) {

        try {
            BufferedReader in = createBufferedReader(file);
            readLines(in, file);
            in.close();
        } catch (Exception e) {
            logger.error("Error whilst processing file: " + file.getPath() + " Message: " + e.getMessage(), e);
        }
    }

    @VisibleForTesting
    BufferedReader createBufferedReader(File file) throws FileNotFoundException {
        BufferedReader in = new BufferedReader(new FileReader(file));
        return in;
    }

    private void readLines(BufferedReader in, File file) throws IOException {
        int lineNumber = 0;
        String line;
        do {
            line = in.readLine();
            if (isNotNull(line)) {
                processLine(line, file.getPath(), ++lineNumber, in);
            }
        } while (isNotNull(line));
    }

    private boolean isNotNull(Object obj) {
        return obj != null;
    }

    private int processLine(String line, String filePath, int lineNumber, BufferedReader in) throws IOException {

        if (canValidateLine(line) && scanForValidator.validate(line)) {
            List<String> lines = new ArrayList<String>();
            lines.add(line);
            addExtraDetailLines(in, lines);
            results.addResult(filePath, lineNumber, lines);
            lineNumber += extraLineCount;
        }

        return lineNumber;
    }

    private boolean canValidateLine(String line) {
        boolean retVal = true;
        if (isNotNull(excludeValidator)) {
            retVal = !excludeValidator.validate(line);
        }
        return retVal;
    }

    private void addExtraDetailLines(BufferedReader in, List<String> lines) throws IOException {

        for (int i = 0; i < extraLineCount; i++) {
            String line = in.readLine();
            if (isNotNull(line)) {
                lines.add(line);
            } else {
                break;
            }
        }
    }

}
