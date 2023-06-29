package kr.hs.dgsw.fastwash.fastwashserver.domain.user.repository;

import kr.hs.dgsw.fastwash.fastwashserver.domain.user.entity.UserEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface UserRepository extends CrudRepository<UserEntity, Long> {
    Optional<UserEntity> findByDodamId(String dodamId);
}
