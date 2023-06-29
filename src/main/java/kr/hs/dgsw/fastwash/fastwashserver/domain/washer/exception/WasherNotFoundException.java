package kr.hs.dgsw.fastwash.fastwashserver.domain.washer.exception;

import kr.hs.dgsw.fastwash.fastwashserver.global.exception.BusinessException;

public class WasherNotFoundException extends BusinessException {
    public WasherNotFoundException() {
        super(404, "No such washer");
    }
}
