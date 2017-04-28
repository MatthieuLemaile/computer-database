package com.excilys.mlemaile.cdb.persistence;

public enum FieldSort {
    NAME("name"), INTRODUCED("introduced"), DISCONTINUED("discontinued"), COMPANY_NAME(
            "company.name");
    private final String text;

    /**
     * The constructor.
     * @param text the text representing the field in the database
     */
    FieldSort(final String text) {
        this.text = text;
    }

    @Override
    public String toString() {
        return text;
    }
}
