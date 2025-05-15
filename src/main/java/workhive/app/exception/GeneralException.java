package workhive.app.exception;

import lombok.Getter;
import workhive.app.exception.enums.ErrorCode;

@Getter
public class GeneralException extends RuntimeException{

    private final ErrorCode code;

    public GeneralException() {
        this.code = ErrorCode.INTERNAL_SERVER_ERROR;
    }

    public GeneralException(ErrorCode code) {
        this.code = code;
    }

    public GeneralException(ErrorCode code, String message) {
        super(message);
        this.code = code;
    }

    public GeneralException(ErrorCode code, String message, Throwable cause) {
        super(message, cause);
        this.code = code;
    }

    public GeneralException(ErrorCode code, Throwable cause) {
        super(cause);
        this.code = code;
    }
}
