package kr.hs.dgsw.fastwash.fastwashserver.global.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public abstract class BusinessException extends RuntimeException {
    private int code;
    private String message;
}
