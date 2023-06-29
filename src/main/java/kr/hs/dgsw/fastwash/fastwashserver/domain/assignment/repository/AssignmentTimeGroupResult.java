package kr.hs.dgsw.fastwash.fastwashserver.domain.assignment.repository;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalTime;

@Getter
@AllArgsConstructor
public class AssignmentTimeGroupResult {
    private Long washerId;
    private LocalTime time;
}
