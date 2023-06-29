package kr.hs.dgsw.fastwash.fastwashserver.global.infra.dodam;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Getter;
import lombok.ToString;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;

public interface DodamApi {
    @GET("/api/user")
    Call<UserInfoResponse> getUserInfo(@Header("authorization") String headerString);

    @Getter
    @ToString
    @JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
    @JsonIgnoreProperties(ignoreUnknown = true)
    class UserInfoResponse {
        private UserInfoResponseInner data;
    }

    @Getter
    @ToString
    @JsonIgnoreProperties(ignoreUnknown = true)
    class UserInfoResponseInner {
        private String uniqueId;
        private Integer grade;
        private Integer room;
        private Integer number;
        private String name;
    }
}
