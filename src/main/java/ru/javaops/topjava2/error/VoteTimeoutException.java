package ru.javaops.topjava2.error;

import java.time.LocalTime;

public class VoteTimeoutException extends AppException {

    private static final String VOTING_IS_OVER = "This voting finished at ";
    public VoteTimeoutException(LocalTime deadline) {
        super(VOTING_IS_OVER + deadline);
    }
}
