package org.bohdan.springboot.booktracking2boot.models.enums;

public enum Role {
    ADMIN("Admin"),
    LIBRARIAN("Librarian"),
    READER("Reader");

    private final String displayValue;

    private Role(String displayValue) {
        this.displayValue = displayValue;
    }

    public String getDisplayValue() {
        return displayValue;
    }
}
