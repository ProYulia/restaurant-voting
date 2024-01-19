package com.github.proyulia.error;

public class DeletionNotAllowedException extends AppException {
    private static final String DELETION_IS_NOT_ALLOWED = "Deletion of past " +
            "entities is not allowed";

    public DeletionNotAllowedException() {
        super(DELETION_IS_NOT_ALLOWED);
    }
}
