package kr.hs.dgsw.fastwash.fastwashserver.domain.washer.presentation.dto.response;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class WasherResponse {
    private Long id;
    private String name;
}
