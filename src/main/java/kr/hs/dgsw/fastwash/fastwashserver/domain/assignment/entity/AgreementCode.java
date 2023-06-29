package kr.hs.dgsw.fastwash.fastwashserver.domain.assignment.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class AgreementCode {
    @Id
    private Long id;

    @MapsId
    @JoinColumn(referencedColumnName = "id")
    @OneToOne(orphanRemoval = true, cascade = CascadeType.ALL)
    private AssignmentEntity assignment;

    private Integer seed;
}
