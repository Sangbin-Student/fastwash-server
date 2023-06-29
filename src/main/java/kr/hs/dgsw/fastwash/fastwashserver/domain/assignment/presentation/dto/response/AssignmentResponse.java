package kr.hs.dgsw.fastwash.fastwashserver.domain.assignment.presentation.dto.response;

import kr.hs.dgsw.fastwash.fastwashserver.domain.user.presentation.dto.response.UserResponse;
import kr.hs.dgsw.fastwash.fastwashserver.domain.washer.presentation.dto.response.WasherResponse;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class AssignmentResponse {
    private WasherResponse washer;
    private String time;
    private List<UserResponse> users;
    private Integer seed;
}
