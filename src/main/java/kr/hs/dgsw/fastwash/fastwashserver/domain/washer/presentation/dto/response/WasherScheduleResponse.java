package kr.hs.dgsw.fastwash.fastwashserver.domain.washer.presentation.dto.response;

import kr.hs.dgsw.fastwash.fastwashserver.domain.assignment.presentation.dto.response.UserAssignmentAuthResponse;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class WasherScheduleResponse {
    private String time;
    private List<UserAssignmentAuthResponse> users;
}
