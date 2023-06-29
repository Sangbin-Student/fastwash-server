package kr.hs.dgsw.fastwash.fastwashserver.domain.assignment.presentation.dto.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.Pattern;

@Getter
@NoArgsConstructor
public class AssignmentRequest {
    @ApiModelProperty("세탁기의 고유 아이디")
    private Long washerId;

    @ApiModelProperty(value = "세탁기를 배정할 시간 (hh:mm 형식)")
    @Pattern(regexp = "^[1-2]{1}[0-9]{1}:[0-6]{1}[0-9]{1}$")
    private String time;

    @ApiModelProperty("1~100까지의 정수")
    @Range(min = 1, max = 100)
    private Integer quantity;
}
