package kr.hs.dgsw.fastwash.fastwashserver.domain.assignment.repository;

import kr.hs.dgsw.fastwash.fastwashserver.domain.assignment.entity.AgreementCode;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AgreementCodeRepository extends CrudRepository<AgreementCode, Long> {

}
