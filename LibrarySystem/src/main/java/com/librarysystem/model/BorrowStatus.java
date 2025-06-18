package com.librarysystem.model;

public enum BorrowStatus {
    PENDING("Pending"),
    APPROVED("Disetujui"),
    REJECTED("Ditolak"),
    RETURNED("Dikembalikan");

    private final String displayName;

    BorrowStatus(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }

    public static BorrowStatus fromString(String text) {
        for (BorrowStatus status : BorrowStatus.values()) {
            if (status.displayName.equalsIgnoreCase(text)) {
                return status;
            }
        }
        throw new IllegalArgumentException("Unknown status: " + text);
    }
}
