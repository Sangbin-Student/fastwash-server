package kr.hs.dgsw.fastwash.fastwashserver.domain.washer.service;

import kr.hs.dgsw.fastwash.fastwashserver.domain.assignment.entity.AssignmentEntity;
import kr.hs.dgsw.fastwash.fastwashserver.domain.assignment.presentation.dto.response.UserAssignmentAuthResponse;
import kr.hs.dgsw.fastwash.fastwashserver.domain.assignment.repository.AssignmentRepository;
import kr.hs.dgsw.fastwash.fastwashserver.domain.washer.entity.WasherEntity;
import kr.hs.dgsw.fastwash.fastwashserver.domain.washer.exception.WasherNotFoundException;
import kr.hs.dgsw.fastwash.fastwashserver.domain.washer.exception.WasherScheduleNotFoundException;
import kr.hs.dgsw.fastwash.fastwashserver.domain.washer.presentation.dto.response.WasherPageResponse;
import kr.hs.dgsw.fastwash.fastwashserver.domain.washer.presentation.dto.response.WasherResponse;
import kr.hs.dgsw.fastwash.fastwashserver.domain.washer.presentation.dto.response.WasherScheduleResponse;
import kr.hs.dgsw.fastwash.fastwashserver.domain.washer.repository.WasherRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Date;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class WasherService {
    private final WasherRepository washerRepository;
    private final AssignmentRepository assignmentRepository;

    public WasherPageResponse getAllWasher(int page) {
        Page<WasherResponse> response = washerRepository
                .findAll(PageRequest.of(page, 10))
                .map(it -> WasherResponse.builder()
                        .id(it.getId())
                        .name(it.getName())
                        .build());

        return WasherPageResponse.builder()
                .washers(response.getContent())
                .pages(response.getTotalPages())
                .build();
    }

    public WasherScheduleResponse getWasherSchedule(Long washerId) {
        WasherEntity washer = washerRepository.findById(washerId)
                .orElseThrow(WasherNotFoundException::new);

        Map.Entry<LocalTime, List<AssignmentEntity>> group = assignmentRepository
                .findAllByWasherWithTimeOrder(washer, Date.from(Instant.now()))
                .stream().collect(Collectors.groupingBy(AssignmentEntity::getEstimatedAt))
                .entrySet().stream().min(Comparator.comparingInt(it -> it.getKey().getHour()))
                .orElseThrow(WasherScheduleNotFoundException::new);

        return WasherScheduleResponse.builder()
                .time(group.getKey().toString())
                .users(group.getValue().stream()
                        .filter(it -> it.getAssignedAt().toLocalDate().isEqual(LocalDate.now()))
                        .map(it -> UserAssignmentAuthResponse.builder()
                                .id(it.getUser().getId())
                                .name(it.getUser().getName())
                                .bluetoothDeviceName(it.getBle() == null ? null : it.getBle().getDeviceName())
                                .webOtpSeed(it.getOtp().getSeed())
                                .build())
                        .collect(Collectors.toList()))
                .build();
    }
}
