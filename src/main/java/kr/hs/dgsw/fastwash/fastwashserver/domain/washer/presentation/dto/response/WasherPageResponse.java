package kr.hs.dgsw.fastwash.fastwashserver.domain.washer.presentation.dto.response;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class WasherPageResponse {
    private List<WasherResponse> washers;

    private int pages;
}
