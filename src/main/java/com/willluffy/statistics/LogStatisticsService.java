package com.willluffy.statistics;

import com.willluffy.statistics.file.FileLocator;
import com.willluffy.statistics.report.Results;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

/**
 * Created by dongshuwang on 14-7-5.
 */
@Service
public class LogStatisticsService {

    @Autowired
    private FileLocator fileLocator;

    @Autowired
    private Results results;

    @Autowired
    @Qualifier("textFormatter")
    private Formatter formatter;

    @Autowired
    @Qualifier("emailPublisher")
    private Publisher publisher;

    public void statistics() {

        fileLocator.findFile();
        results.generate(formatter, publisher);
    }
}
