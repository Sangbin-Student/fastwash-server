package kr.hs.dgsw.fastwash.fastwashserver.domain.assignment.service;

import kr.hs.dgsw.fastwash.fastwashserver.domain.assignment.entity.AgreementBLE;
import kr.hs.dgsw.fastwash.fastwashserver.domain.assignment.entity.AgreementCode;
import kr.hs.dgsw.fastwash.fastwashserver.domain.assignment.entity.AssignmentEntity;
import kr.hs.dgsw.fastwash.fastwashserver.domain.assignment.exception.AssignmentNotFoundException;
import kr.hs.dgsw.fastwash.fastwashserver.domain.assignment.presentation.dto.request.AssignmentBleIdRequest;
import kr.hs.dgsw.fastwash.fastwashserver.domain.assignment.presentation.dto.request.AssignmentRequest;
import kr.hs.dgsw.fastwash.fastwashserver.domain.assignment.presentation.dto.response.AssignmentResponse;
import kr.hs.dgsw.fastwash.fastwashserver.domain.assignment.repository.AgreementCodeRepository;
import kr.hs.dgsw.fastwash.fastwashserver.domain.assignment.repository.AssignmentRepository;
import kr.hs.dgsw.fastwash.fastwashserver.domain.user.entity.UserEntity;
import kr.hs.dgsw.fastwash.fastwashserver.domain.user.exception.UserUnauthorizedException;
import kr.hs.dgsw.fastwash.fastwashserver.domain.user.facade.UserFacade;
import kr.hs.dgsw.fastwash.fastwashserver.domain.user.presentation.dto.response.UserResponse;
import kr.hs.dgsw.fastwash.fastwashserver.domain.washer.entity.WasherEntity;
import kr.hs.dgsw.fastwash.fastwashserver.domain.washer.presentation.dto.response.WasherResponse;
import kr.hs.dgsw.fastwash.fastwashserver.domain.washer.repository.WasherRepository;
import kr.hs.dgsw.fastwash.fastwashserver.global.utils.DateUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Time;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class AssignmentService {
    private final UserFacade userFacade;
    private final AssignmentRepository assignmentRepository;
    private final AgreementCodeRepository agreementCodeRepository;
    private final WasherRepository washerRepository;

    @Transactional
    public void requestAssignment(AssignmentRequest request) {
        UserEntity user = userFacade.queryUser()
                .orElseThrow(UserUnauthorizedException::new);

        WasherEntity washer = washerRepository.findById(request.getWasherId())
                .orElseThrow();

        AssignmentEntity assignment = AssignmentEntity.builder()
                .washer(washer)
                .user(user)
                .assignedAt(LocalDateTime.now())
                .estimatedAt(DateUtil.timeFromString(request.getTime()))
                .quantity(request.getQuantity())
                .granted(false)
                .build();

        assignmentRepository.save(assignment);
    }

    public AssignmentResponse getMyAssignment() {
        UserEntity user = userFacade.queryUser()
                .orElseThrow(UserUnauthorizedException::new);

        AssignmentEntity assignment = assignmentRepository.findByUserAndDate(user, Date.from(Instant.now()))
                .orElseThrow(AssignmentNotFoundException::new);

        return AssignmentResponse.builder()
                .time(assignment.getAssignedAt().toLocalTime().toString())
                .users(assignment.getWasher().getAssignments().stream()
                        .filter(it -> it.getAssignedAt().toLocalDate().isEqual(LocalDate.now()))
                        .map(it -> UserResponse.builder()
                                .id(it.getUser().getId())
                                .name(it.getUser().getName())
                                .build())
                        .collect(Collectors.toList()))
                .washer(WasherResponse.builder()
                        .id(assignment.getWasher().getId())
                        .name(assignment.getWasher().getName())
                        .build())
                .seed(assignment.getOtp().getSeed())
                .build();
    }

    private double assessAssignment(int quantity, int elapsed) {
        double quantityScore = -0.01 * Math.pow(quantity, 2) + 100;
        double elapsedScore = 0.2 * Math.pow(Math.min(elapsed, 5) - 2, 3) + 2;

        return quantityScore + elapsedScore + 1;
    }

    @Transactional
    public void assign(Long washerId, String time) {
        WasherEntity washer = washerRepository.findById(washerId)
                .orElseThrow();

        List<AssignmentEntity> requests = assignmentRepository.findByWasherAndTime(washer, DateUtil.timeFromString(time))
                .stream().sorted(Comparator.comparingDouble(request ->
                        assessAssignment(request.getQuantity(), assignmentRepository.findAllByUserWithTimeOrder(
                                request.getUser()).stream()
                                .map(it -> LocalDate.now().until(it.getAssignedAt().toLocalDate()).getDays())
                                .max(Comparator.comparingInt(it -> it))
                                .orElse(3))
                )).collect(Collectors.toList());

        List<AssignmentEntity> result = new ArrayList<>();
        int quantity = 0;
        for (AssignmentEntity assignment : requests) {
            if(quantity + assignment.getQuantity() > 100)
                break;

            quantity += assignment.getQuantity();
            result.add(assignment);
        }

        result.forEach(assignment -> {
            assignment.setGranted(true);

            AgreementCode code = agreementCodeRepository.save(AgreementCode.builder()
                    .assignment(assignment)
                    .seed(Math.round((float)Math.random() * 100000))
                    .build());
            assignment.setOtp(code);
        });
    }

    @Transactional
    public void setAssignmentBleId(AssignmentBleIdRequest request) {
        UserEntity user = userFacade.queryUser()
                .orElseThrow(UserUnauthorizedException::new);

        AssignmentEntity assignment = assignmentRepository.findByUserAndDate(user, Date.from(Instant.now()))
                .orElseThrow(AssignmentNotFoundException::new);

        assignment.setBle(new AgreementBLE(null, assignment, request.getIdentifier()));
    }
}
