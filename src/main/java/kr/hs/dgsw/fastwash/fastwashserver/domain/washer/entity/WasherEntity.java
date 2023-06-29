package kr.hs.dgsw.fastwash.fastwashserver.domain.washer.entity;

import kr.hs.dgsw.fastwash.fastwashserver.domain.assignment.entity.AssignmentEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class WasherEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;

    private String name;

    @OneToMany(mappedBy = "washer")
    List<AssignmentEntity> assignments;
}
