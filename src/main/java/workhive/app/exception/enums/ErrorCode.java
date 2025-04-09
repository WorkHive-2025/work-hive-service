package workhive.app.exception.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import workhive.app.exception.GeneralException;

import java.util.Arrays;
import java.util.Optional;
import java.util.function.Predicate;

@Slf4j
@Getter
@RequiredArgsConstructor
public enum ErrorCode {

    OK(200, HttpStatus.OK, "OK"),

    //Validation Error
    INVALID_PARAMETER_ERROR(10000, HttpStatus.BAD_REQUEST, "Invalid parameter error"),
    DUPLICATE_PARAMETER_ERROR(10001, HttpStatus.BAD_REQUEST, "Duplicate parameter error"),

    //Not Found Error
    NOT_FOUND_ERROR(40000, HttpStatus.BAD_REQUEST, "Data not found error"),

    INTERNAL_SERVER_ERROR(99999, HttpStatus.INTERNAL_SERVER_ERROR, "Internal server error");


    private final Integer code;
    private final HttpStatus status;
    private final String message;

    public static ErrorCode valueOf(Integer code) {
        if (code == null) throw new GeneralException(ErrorCode.INVALID_PARAMETER_ERROR, "Error code is null");

        return Arrays.stream(values())
                .filter(errorCode -> errorCode.getCode().equals(code))
                .findFirst()
                .orElseThrow(() -> {
                    log.error("[ErrorCode.valueOf] Error code not found: {}", code);
                    return new GeneralException(ErrorCode.INVALID_PARAMETER_ERROR, "Error code not found: " + code);
                });
    }

    public String getMessage(Throwable e) {
        return this.getMessage(this.getMessage() + " - " + e.getMessage());
    }

    public String getMessage(String message) {
        return Optional.ofNullable(message)
                .filter(Predicate.not(String::isBlank))
                .orElse(this.getMessage());
    }
}
