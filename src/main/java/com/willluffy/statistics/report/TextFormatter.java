package com.willluffy.statistics.report;

import com.willluffy.statistics.Formatter;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by dongshuwang on 14-7-5.
 */
@Service
public class TextFormatter implements Formatter {

    private static final String RULE = "\n==================================================================================================================\n";

    @SuppressWarnings("unchecked")
    @Override
    public <T> T format(Results results) {

        StringBuilder sb = new StringBuilder(dateFormat());
        sb.append(RULE);

        Set<Map.Entry<String, List<Results.ErrorResult>>> entries = results.getRawResults().entrySet();
        for (Map.Entry<String, List<Results.ErrorResult>> entry : entries) {
            appendFileName(sb, entry.getKey());
            appendErrors(sb, entry.getValue());
        }

        return (T) sb.toString();
    }

    private String dateFormat() {

        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        return df.format(Calendar.getInstance().getTime());
    }

    private void appendFileName(StringBuilder sb, String fileName) {
        sb.append("File:  ");
        sb.append(fileName);
        sb.append("\n");
    }

    private void appendErrors(StringBuilder sb, List<Results.ErrorResult> errorResults) {

        for (Results.ErrorResult errorResult : errorResults) {
            appendErrorResult(sb, errorResult);
        }

    }

    private void appendErrorResult(StringBuilder sb, Results.ErrorResult errorResult) {
        addLineNumber(sb, errorResult.getLineNumber());
        addDetails(sb, errorResult.getLines());
        sb.append(RULE);
    }

    private void addLineNumber(StringBuilder sb, int lineNumber) {
        sb.append("Error found at line: ");
        sb.append(lineNumber);
        sb.append("\n");
    }

    private void addDetails(StringBuilder sb, List<String> lines) {

        for (String line : lines) {
            sb.append(line);
            sb.append("\n");
        }
    }
}