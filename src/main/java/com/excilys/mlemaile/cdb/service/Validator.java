package com.excilys.mlemaile.cdb.service;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;

public enum Validator {
    INSTANCE;

    /**
     * check that id is a long greater than 0.
     * @param id the id to check
     */
    public void checkId(String id) {
        if (id != null) {
            if (id.trim().isEmpty()) {
                throw new ServiceException("The given parameter is not an id, it's empty !");
            }
            try {
                Long.parseLong(id);
            } catch (NumberFormatException e) {
                throw new ServiceException("The given parameter is not an id.", e);
            }
        }
    }

    /**
     * check that the given String is a date.
     * @param date the String to check
     */
    public void checkDate(String date) {
        if (date != null && !date.trim().isEmpty()) {
            try {
                LocalDate.parse(date);
            } catch (DateTimeParseException e) {
                throw new ServiceException("The given parameter is not a date.", e);
            }
        }
    }

    /**
     * Check that String are date, and that introduced is at the same moment or before discontinued date.
     * @param discontinuedDate the discontinued date
     * @param introducedDate the introduced date
     */
    public void checkDateNotBeforeDate(String discontinuedDate, String introducedDate) {
        if (introducedDate != null && discontinuedDate != null && !introducedDate.trim().isEmpty()
                && !discontinuedDate.trim().isEmpty()) {
            this.checkDate(introducedDate);
            this.checkDate(discontinuedDate);
            LocalDate intro = LocalDate.parse(introducedDate);
            LocalDate disco = LocalDate.parse(discontinuedDate);
            if (intro.isAfter(disco)) {
                throw new ServiceException("The introduced date is after the discontinued date.");
            }
        }
    }
}
