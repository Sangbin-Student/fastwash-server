package kr.hs.dgsw.fastwash.fastwashserver.domain.assignment.repository;

import kr.hs.dgsw.fastwash.fastwashserver.domain.assignment.entity.AssignmentEntity;
import kr.hs.dgsw.fastwash.fastwashserver.domain.user.entity.UserEntity;
import kr.hs.dgsw.fastwash.fastwashserver.domain.washer.entity.WasherEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalTime;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Repository
public interface AssignmentRepository extends CrudRepository<AssignmentEntity, Long> {
    @Query("SELECT e from AssignmentEntity e WHERE e.user=:user AND DATE(e.assignedAt)=:now AND e.granted=true")
    Optional<AssignmentEntity> findByUserAndDate(UserEntity user, Date now);

    @Query("SELECT e from AssignmentEntity e WHERE e.washer=:washer AND TIME(e.estimatedAt)=TIME(:time)")
    List<AssignmentEntity> findByWasherAndTime(WasherEntity washer, LocalTime time);

    @Query("SELECT e from AssignmentEntity e WHERE e.user=:user ORDER BY e.assignedAt DESC")
    List<AssignmentEntity> findAllByUserWithTimeOrder(UserEntity user);

    @Query("SELECT e from AssignmentEntity e WHERE e.washer=:washer AND DATE(e.assignedAt)=:now AND e.granted=true ORDER BY e.assignedAt")
    List<AssignmentEntity> findAllByWasherWithTimeOrder(WasherEntity washer, Date now);

    @Query("SELECT new kr.hs.dgsw.fastwash.fastwashserver.domain.assignment.repository.AssignmentTimeGroupResult(a.washer.id, a.estimatedAt) FROM AssignmentEntity a GROUP BY a.estimatedAt HAVING COUNT(a.granted) = 0")
    List<AssignmentTimeGroupResult> getTimesOfWashers();
}
