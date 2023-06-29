package kr.hs.dgsw.fastwash.fastwashserver.domain.user.presentation.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.experimental.SuperBuilder;

@Getter
@SuperBuilder
public class UserResponse {
    private Long id;
    private String name;
}
