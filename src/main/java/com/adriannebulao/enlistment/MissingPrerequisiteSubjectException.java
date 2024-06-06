package com.adriannebulao.enlistment;

public class MissingPrerequisiteSubjectException extends RuntimeException {
    public MissingPrerequisiteSubjectException() {
    }

    public MissingPrerequisiteSubjectException(String message) {
        super(message);
    }

    public MissingPrerequisiteSubjectException(String message, Throwable cause) {
        super(message, cause);
    }

    public MissingPrerequisiteSubjectException(Throwable cause) {
        super(cause);
    }

    public MissingPrerequisiteSubjectException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
