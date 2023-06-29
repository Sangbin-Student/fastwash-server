package kr.hs.dgsw.fastwash.fastwashserver.domain.user.facade;

import kr.hs.dgsw.fastwash.fastwashserver.domain.user.entity.UserEntity;
import kr.hs.dgsw.fastwash.fastwashserver.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@RequiredArgsConstructor
@Component
public class UserFacade {
    private final UserRepository userRepository;

    @Transactional
    public Optional<UserEntity> queryUser() {
        return queryUser(true);
    }

    public Optional<UserEntity> queryUser(boolean persistence) {
        try {
            UserEntity user = (UserEntity) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            if (!persistence)
                return Optional.of(user);

            return userRepository.findById(user.getId());
        } catch (Exception exception) {
            return Optional.empty();
        }
    }
}
