package kr.hs.dgsw.fastwash.fastwashserver.domain.user.entity;

import lombok.*;

import javax.persistence.*;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class UserEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;

    @Column(unique = true)
    private String dodamId;

    @Setter
    private String schoolNumber;

    private String name;
}
