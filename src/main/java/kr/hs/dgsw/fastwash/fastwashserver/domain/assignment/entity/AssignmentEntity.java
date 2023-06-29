package kr.hs.dgsw.fastwash.fastwashserver.domain.assignment.entity;

import kr.hs.dgsw.fastwash.fastwashserver.domain.user.entity.UserEntity;
import kr.hs.dgsw.fastwash.fastwashserver.domain.washer.entity.WasherEntity;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class AssignmentEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;

    @JoinColumn(name = "user", referencedColumnName = "id")
    @ManyToOne
    private UserEntity user;

    @JoinColumn(name = "washer", referencedColumnName = "id")
    @ManyToOne
    private WasherEntity washer;

    private LocalDateTime assignedAt;

    @Setter
    private LocalTime estimatedAt;

    private Integer quantity;

    @Setter
    private Boolean granted;

    @Setter
    @OneToOne(mappedBy = "assignment")
    private AgreementCode otp;

    @Setter
    @OneToOne(mappedBy = "assignment")
    private AgreementBLE ble;
}
