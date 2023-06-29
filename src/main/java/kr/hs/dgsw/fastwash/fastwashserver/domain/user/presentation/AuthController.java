package kr.hs.dgsw.fastwash.fastwashserver.domain.user.presentation;

import kr.hs.dgsw.fastwash.fastwashserver.domain.user.presentation.dto.response.SignInResponse;
import kr.hs.dgsw.fastwash.fastwashserver.domain.user.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RequestMapping("/auth")
@RestController
public class AuthController {
    private final AuthService authService;

    @PostMapping("/sign-in")
    public SignInResponse signIn(@RequestParam("code") String code) {
        return authService.signIn(code);
    }

    @PostMapping("/sign-in-token")
    public SignInResponse signInWithToken(@RequestParam("accessToken") String token) {
        return authService.signInWithAccessToken(token);
    }
}
