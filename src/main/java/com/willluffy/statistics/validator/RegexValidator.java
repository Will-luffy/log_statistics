package com.willluffy.statistics.validator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by dongshuwang on 14-7-5.
 */

public class RegexValidator implements Validator {

    private static final Logger logger = LoggerFactory.getLogger(RegexValidator.class);

    private final Pattern pattern;

    public RegexValidator(String regex) {
        pattern = Pattern.compile(regex);
        logger.info("loaded regex : {}", regex);
    }

    @Override
    public <T> boolean validate(T obj) {

        boolean retVal = false;
        Matcher matcher = pattern.matcher((String)obj);
        retVal = matcher.matches();
        return retVal;
    }
}
