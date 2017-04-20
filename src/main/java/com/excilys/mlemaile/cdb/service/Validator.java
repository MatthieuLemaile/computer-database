package com.excilys.mlemaile.cdb.service;

import java.time.LocalDate;

import org.apache.commons.lang3.StringUtils;

public class Validator {
    private static final String REGEX_DATE = "^(((((1[26]|2[048])00)|[12]\\d([2468][048]|[13579][26]|0[48]))-((((0[13578]|1[02])-(0[1-9]|[12]\\d|3[01]))|((0[469]|11)-(0[1-9]|[12]\\d|30)))|(02-(0[1-9]|[12]\\d))))|((([12]\\d([02468][1235679]|[13579][01345789]))|((1[1345789]|2[1235679])00))-((((0[13578]|1[02])-(0[1-9]|[12]\\d|3[01]))|((0[469]|11)-(0[1-9]|[12]\\d|30)))|(02-(0[1-9]|1\\d|2[0-8])))))$";

    /**
     * check that id is a long greater than 0.
     * @param id the id to check
     * @return a String with the error or null
     */
    public static String checkId(String id) {
        if (id != null && !id.matches("^\\d+$")) {
            return "The id supplied is not a good one.";
        }
        return null;
    }

    /**
     * check that the given String is a date.
     * @param date the String to check
     * @return a String with the error or null
     */
    public static String checkDate(String date) {
        if (date != null && !date.matches(REGEX_DATE)) {
            return "The date supplied is not well formatted.";
        }
        return null;
    }

    /**
     * Check that String are date, and that introduced is at the same moment or before discontinued
     * date.
     * @param discontinuedDate the discontinued date
     * @param introducedDate the introduced date
     * @return a String with the error or null.
     */
    public static String checkDateNotBeforeDate(String discontinuedDate, String introducedDate) {
        if (introducedDate != null && discontinuedDate != null && !introducedDate.trim().isEmpty()
                && !discontinuedDate.trim().isEmpty()) {
            String introValid = checkDate(introducedDate);
            String discoValid = checkDate(discontinuedDate);
            if (introValid != null) {
                return introValid;
            }
            if (discoValid != null) {
                return discoValid;
            }
            LocalDate intro = LocalDate.parse(introducedDate);
            LocalDate disco = LocalDate.parse(discontinuedDate);
            if (intro.isAfter(disco)) {
                return "The introduced date is after the discontinued date.";
            }
        }
        return null;
    }

    /**
     * check if the name of the computer is not empty.
     * @param name the name to check
     * @return a String with the error or null
     */
    public static String checkNameNotEmpty(String name) {
        if (StringUtils.isNotEmpty(name)) {
            return null;
        }
        return "You must suply a name.";
    }

    /**
     * check if the numero of the page is a positive integer.
     * @param number the number to check.
     * @return the error or null.
     */
    public static String checkPageNumberPositiveOrNull(String number) {
        if (number != null && !number.matches("^\\d+$")) {
            return "The page number must be a positive integer.";
        }
        return null;
    }

    /**
     * Check if number is a positive integer.
     * @param number to check.
     * @return the error or null
     */
    public static String checkPageLimitPositiveOrNull(String number) {
        if (number != null && !number.matches("^\\d+$")) {
            return "The page limit must be a positive integer.";
        }
        return null;
    }
}
