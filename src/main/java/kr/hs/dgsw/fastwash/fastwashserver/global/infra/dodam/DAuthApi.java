package kr.hs.dgsw.fastwash.fastwashserver.global.infra.dodam;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface DAuthApi {
    @POST("/api/token")
    Call<IssueAccessTokenResponse> issueAccessToken(@Body IssueAccessTokenRequest body);

    @Getter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    @JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
    class IssueAccessTokenRequest {
        private String code;
        private String clientId;
        private String clientSecret;
    }

    @Getter
    @NoArgsConstructor
    @JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
    @JsonIgnoreProperties(ignoreUnknown = true)
    class IssueAccessTokenResponse {
        private String accessToken;
    }
}
