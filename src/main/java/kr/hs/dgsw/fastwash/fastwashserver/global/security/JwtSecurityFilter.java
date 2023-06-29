package kr.hs.dgsw.fastwash.fastwashserver.global.security;

import kr.hs.dgsw.fastwash.fastwashserver.domain.user.entity.UserEntity;
import kr.hs.dgsw.fastwash.fastwashserver.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@RequiredArgsConstructor
public class JwtSecurityFilter extends OncePerRequestFilter {
    private final JwtUtil jwtUtil;
    private final UserRepository userRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String authorization = request.getHeader("Authorization");

            if(authorization != null) {
                try {
                    String token = authorization.replace("Bearer ", "");
                    UserEntity user = userRepository.findById(jwtUtil.getUserIdByToken(token))
                        .orElseThrow();

                    SecurityContextHolder.getContext()
                        .setAuthentication(new UsernamePasswordAuthenticationToken(user, null, List.of()));
                } catch (Exception exception) {
                    System.out.println(exception);
                }
            }

        filterChain.doFilter(request, response);
    }
}
