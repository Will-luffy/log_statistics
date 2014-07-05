package com.willluffy.statistics.validator;

/**
 * Created by dongshuwang on 14-7-5.
 */
public interface Validator {

    public <T> boolean validate(T obj);
}
