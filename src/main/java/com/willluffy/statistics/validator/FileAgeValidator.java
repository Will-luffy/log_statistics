package com.willluffy.statistics.validator;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.Calendar;

/**
 * Created by dongshuwang on 14-7-5.
 */
@Service
public class FileAgeValidator implements Validator {

    @Value("${max.days}")
    private int maxDays;

    /**
     * Validate the age of the file.
     *
     */
    @Override
    public <T> boolean validate(T obj) {

        File file = (File) obj;
        Calendar fileDate = getFileDate(file);

        Calendar ageLimit = getFileAgeLimit();

        boolean retVal = false;
        if (fileDate.after(ageLimit)) {
            retVal = true;
        }

        return retVal;
    }

    private Calendar getFileAgeLimit() {

        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DAY_OF_MONTH, -1 * maxDays);
        return cal;
    }

    private Calendar getFileDate(File file) {

        long fileDate = file.lastModified();
        Calendar when = Calendar.getInstance();
        when.setTimeInMillis(fileDate);
        return when;
    }

}
