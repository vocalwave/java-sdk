package io.qrng;

public class QRNGException extends Exception {
    public QRNGException(String message) {
        super(message);
    }

    public QRNGException(String message, Throwable cause) {
        super(message, cause);
    }
}

class AuthenticationException extends QRNGException {
    public AuthenticationException(String message) {
        super(message);
    }
}

class RateLimitException extends QRNGException {
    public RateLimitException(String message) {
        super(message);
    }
}

class QuotaExceededException extends QRNGException {
    public QuotaExceededException(String message) {
        super(message);
    }
}
