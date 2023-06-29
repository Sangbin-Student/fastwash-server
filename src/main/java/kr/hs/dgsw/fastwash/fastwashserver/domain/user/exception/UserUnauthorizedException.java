package kr.hs.dgsw.fastwash.fastwashserver.domain.user.exception;

import kr.hs.dgsw.fastwash.fastwashserver.global.exception.BusinessException;

public class UserUnauthorizedException extends BusinessException {
    public UserUnauthorizedException() {
        super(401, "User is not logined");
    }
}
