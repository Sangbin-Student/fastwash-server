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
public class AgreementBLE {
    @Id
    private Long id;

    @MapsId
    @JoinColumn(referencedColumnName = "id")
    @OneToOne
    private AssignmentEntity assignment;

    @Column(unique = true)
    private String deviceName;
}
