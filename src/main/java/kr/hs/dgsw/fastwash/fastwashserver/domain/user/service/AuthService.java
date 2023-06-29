package kr.hs.dgsw.fastwash.fastwashserver.domain.user.service;

import kr.hs.dgsw.fastwash.fastwashserver.domain.user.entity.UserEntity;
import kr.hs.dgsw.fastwash.fastwashserver.domain.user.presentation.dto.response.SignInResponse;
import kr.hs.dgsw.fastwash.fastwashserver.domain.user.repository.UserRepository;
import kr.hs.dgsw.fastwash.fastwashserver.global.infra.dodam.DAuthApi;
import kr.hs.dgsw.fastwash.fastwashserver.global.infra.dodam.DodamApi;
import kr.hs.dgsw.fastwash.fastwashserver.global.security.JwtUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOError;
import java.io.IOException;
import java.util.Objects;

@Log4j2
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AuthService {
    private final UserRepository userRepository;
    private final DAuthApi dAuthApi;
    private final DodamApi dodamApi;
    private final JwtUtil jwtUtil;

    @Value("${infra.dodam.client-id}")
    private String clientId;

    @Value("${infra.dodam.client-secret}")
    private String clientSecret;

    @Transactional
    public SignInResponse signIn(String code) {
        try {
            String accessToken = dAuthApi.issueAccessToken(DAuthApi.IssueAccessTokenRequest.builder()
                            .code(code)
                            .clientId(clientId)
                            .clientSecret(clientSecret)
                            .build())
                    .execute().body().getAccessToken();

            log.info(accessToken);

            return signInWithAccessToken(accessToken);
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
    }

    @Transactional
    public SignInResponse signInWithAccessToken(String accessToken) {
        try {
            DodamApi.UserInfoResponse userInfo = Objects.requireNonNull(dodamApi.getUserInfo(String.format("Bearer %s", accessToken))
                    .execute().body());

            String schoolNumber = String.format("%d%d%02d", userInfo.getData().getGrade(), userInfo.getData().getRoom(), userInfo.getData().getNumber());
            UserEntity user = userRepository.findByDodamId(userInfo.getData().getUniqueId())
                    .orElseGet(() -> userRepository.save(UserEntity.builder()
                            .name(userInfo.getData().getName())
                            .dodamId(userInfo.getData().getUniqueId())
                            .schoolNumber(schoolNumber)
                            .build()));

            user.setSchoolNumber(schoolNumber);

            return SignInResponse.builder()
                    .accessToken(jwtUtil.issueToken(user.getId()))
                    .build();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
    }
}
