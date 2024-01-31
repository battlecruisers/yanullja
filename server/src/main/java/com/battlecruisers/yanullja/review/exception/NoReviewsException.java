package com.battlecruisers.yanullja.review.exception;


public class NoReviewsException extends RuntimeException {

    public NoReviewsException() {
        super("등록된 후기가 없습니다.");
    }

    public NoReviewsException(String message) {
        super(message);
    }

    public NoReviewsException(String message, Throwable cause) {
        super(message, cause);
    }

    public NoReviewsException(Throwable cause) {
        super(cause);
    }
}
