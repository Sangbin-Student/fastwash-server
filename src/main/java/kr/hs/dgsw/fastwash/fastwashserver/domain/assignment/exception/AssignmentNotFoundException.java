package kr.hs.dgsw.fastwash.fastwashserver.domain.assignment.exception;

import kr.hs.dgsw.fastwash.fastwashserver.global.exception.BusinessException;

public class AssignmentNotFoundException extends BusinessException {
    public AssignmentNotFoundException() {
        super(404, "No such assignment");
    }
}
