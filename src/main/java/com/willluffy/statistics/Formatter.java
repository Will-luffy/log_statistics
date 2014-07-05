package com.willluffy.statistics;

import com.willluffy.statistics.report.Results;

/**
 * Created by dongshuwang on 14-7-5.
 */
public interface Formatter {
    public <T> T format(Results results);
}
