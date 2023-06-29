package kr.hs.dgsw.fastwash.fastwashserver.domain.washer.presentation;

import io.swagger.annotations.ApiOperation;
import kr.hs.dgsw.fastwash.fastwashserver.domain.assignment.service.AssignmentService;
import kr.hs.dgsw.fastwash.fastwashserver.domain.washer.presentation.dto.request.WasherAssignmentRequest;
import kr.hs.dgsw.fastwash.fastwashserver.domain.washer.presentation.dto.response.WasherPageResponse;
import kr.hs.dgsw.fastwash.fastwashserver.domain.washer.presentation.dto.response.WasherScheduleResponse;
import kr.hs.dgsw.fastwash.fastwashserver.domain.washer.service.WasherService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RequestMapping("/washers")
@RestController
public class WasherController {
    private final WasherService washerService;
    private final AssignmentService assignmentService;

    @ApiOperation("모든 세탁기를 조회합니다")
    @GetMapping
    public WasherPageResponse getAllWasher(@RequestParam(value = "page", defaultValue = "1") int page) {
        return washerService.getAllWasher(page);
    }

    @ApiOperation("세탁기의 가장 가까운 스케줄을 가져옵니다")
    @GetMapping("/{washer-id}")
    public WasherScheduleResponse getWasherSchedule(@PathVariable("washer-id") Long washerId) {
        return washerService.getWasherSchedule(washerId);
    }

    @ApiOperation("세탁기 배정을 시작합니다.")
    @PostMapping("/{washer-id}/assignment")
    public void assign(@PathVariable("washer-id") Long washerId, @RequestBody WasherAssignmentRequest request) {
        assignmentService.assign(washerId, request.getLabel());
    }
}
