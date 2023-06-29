package kr.hs.dgsw.fastwash.fastwashserver.domain.washer.exception;

import kr.hs.dgsw.fastwash.fastwashserver.global.exception.BusinessException;

public class WasherScheduleNotFoundException extends BusinessException {
    public WasherScheduleNotFoundException() {
        super(404, "Schedule is not exists yet");
    }
}
