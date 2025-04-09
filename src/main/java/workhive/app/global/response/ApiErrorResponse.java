package workhive.app.global.response;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import workhive.app.exception.enums.ErrorCode;

@Getter
@ToString
@EqualsAndHashCode
public class ApiErrorResponse {

    private final Boolean success;
    private final Integer code;
    private final String message;

    public ApiErrorResponse(Boolean success, Integer errorCode, String message) {
        this.success = success;
        this.code = errorCode;
        this.message = message;
    }

    public ApiErrorResponse(ErrorCode code) {
        this.success = false;
        this.code = code.getCode();
        this.message = code.getMessage();
    }

    public ApiErrorResponse(Boolean success, ErrorCode code) {
        this.success = success;
        this.code = code.getCode();
        this.message = code.getMessage();
    }

    public ApiErrorResponse(Boolean success, ErrorCode code, String message) {
        this.success = success;
        this.code = code.getCode();
        this.message = message;
    }


}
