package kr.hs.dgsw.fastwash.fastwashserver.domain.assignment.presentation.dto.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.Pattern;

@Getter
@NoArgsConstructor
public class AssignmentBleIdRequest {
    @ApiModelProperty("사용자 모바일 장비의 블루투스 식별자")
    private String identifier;
}
