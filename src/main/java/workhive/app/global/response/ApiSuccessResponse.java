package workhive.app.global.response;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import workhive.app.exception.enums.ErrorCode;

@Getter
@ToString
@EqualsAndHashCode(callSuper = true)
public class ApiSuccessResponse<T> extends ApiErrorResponse {

    private final T data;

    public ApiSuccessResponse() {
        super(true, ErrorCode.OK.getCode(), ErrorCode.OK.getMessage());
        this.data = null;
    }

    public ApiSuccessResponse(T data) {
        super(true, ErrorCode.OK.getCode(), ErrorCode.OK.getMessage());
        this.data = data;
    }
}
