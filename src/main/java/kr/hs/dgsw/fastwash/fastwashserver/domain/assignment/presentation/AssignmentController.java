package kr.hs.dgsw.fastwash.fastwashserver.domain.assignment.presentation;

import io.swagger.annotations.ApiOperation;
import kr.hs.dgsw.fastwash.fastwashserver.domain.assignment.presentation.dto.request.AssignmentBleIdRequest;
import kr.hs.dgsw.fastwash.fastwashserver.domain.assignment.presentation.dto.request.AssignmentRequest;
import kr.hs.dgsw.fastwash.fastwashserver.domain.assignment.presentation.dto.response.AssignmentResponse;
import kr.hs.dgsw.fastwash.fastwashserver.domain.assignment.service.AssignmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RequestMapping("/assignments")
@RestController
public class AssignmentController {
    private final AssignmentService assignmentService;

    @ApiOperation("세탁기 배정을 요청합니다")
    @PostMapping("/me")
    public void requestMyAssignment(@RequestBody @Validated AssignmentRequest request) {
        assignmentService.requestAssignment(request);
    }

    @ApiOperation("세탁기 배정에 대한 블루투스 아이디를 지정합니다")
    @PostMapping("/me/bluetooth-id")
    public void setAssignmentBleId(@RequestBody AssignmentBleIdRequest request) {
        assignmentService.setAssignmentBleId(request);
    }

    @ApiOperation("사용자에게 배정된 세탁기를 조회합니다")
    @GetMapping("/me")
    public AssignmentResponse getMyAssignment() {
        return assignmentService.getMyAssignment();
    }
}
