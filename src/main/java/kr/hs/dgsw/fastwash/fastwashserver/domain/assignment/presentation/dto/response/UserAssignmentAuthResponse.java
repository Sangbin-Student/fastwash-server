package kr.hs.dgsw.fastwash.fastwashserver.domain.assignment.presentation.dto.response;

import kr.hs.dgsw.fastwash.fastwashserver.domain.user.presentation.dto.response.UserResponse;
import lombok.Getter;
import lombok.experimental.SuperBuilder;

@Getter
@SuperBuilder
public class UserAssignmentAuthResponse extends UserResponse {
    private String bluetoothDeviceName;
    private Integer webOtpSeed;
}
