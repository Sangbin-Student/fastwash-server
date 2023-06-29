package kr.hs.dgsw.fastwash.fastwashserver.domain.washer.repository;

import kr.hs.dgsw.fastwash.fastwashserver.domain.washer.entity.WasherEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WasherRepository extends JpaRepository<WasherEntity, Long> {
    Page<WasherEntity> findAll(Pageable pageable);
}
