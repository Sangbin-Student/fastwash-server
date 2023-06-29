package kr.hs.dgsw.fastwash.fastwashserver.global;

import kr.hs.dgsw.fastwash.fastwashserver.domain.assignment.repository.AssignmentRepository;
import kr.hs.dgsw.fastwash.fastwashserver.domain.assignment.service.AssignmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.HashSet;
import java.util.Set;

@RequiredArgsConstructor
@Component
public class AssignmentScheduler {
    private final Set<LocalTime> finished = new HashSet<>();
    private final AssignmentRepository assignmentRepository;
    private final AssignmentService assignmentService;

    @Scheduled(fixedDelay = 300000)
    public void assign() {
        assignmentRepository.getTimesOfWashers().stream()
                .filter(assignment -> !finished.contains(assignment.getTime()))
                .filter(assignment -> LocalTime.now().isAfter(assignment.getTime().minusHours(3)) )
                .forEach(assignment -> {
                    finished.add(assignment.getTime());
                    String formattedTime = DateTimeFormatter.ofPattern("H:mm").format(assignment.getTime());
                    assignmentService.assign(assignment.getWasherId(), formattedTime);
        });
    }
}
