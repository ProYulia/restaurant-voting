package ru.javaops.topjava2.error;

public class VoteTimeoutException extends AppException {

    private static final String VOTING_IS_OVER = "This voting finished at ";

    public VoteTimeoutException(String deadline) {
        super(VOTING_IS_OVER + deadline);
    }
}
