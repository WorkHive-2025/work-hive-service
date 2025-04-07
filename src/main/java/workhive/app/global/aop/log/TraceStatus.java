package workhive.app.global.aop.log;

public record TraceStatus(TraceId traceId,
                          Long startTimeMs,
                          String message) {

}
