package kr.hs.dgsw.fastwash.fastwashserver.global.infra.dodam;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

@Configuration
public class DodamInfraConfiguration {
    @Bean
    public OkHttpClient client() {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        return new OkHttpClient.Builder()
                .addInterceptor(interceptor)
                .build();
    }

    @Bean
    public DAuthApi dAuthApi() {
        Retrofit retrofit = new Retrofit.Builder()
                .addConverterFactory(JacksonConverterFactory.create())
                .baseUrl("http://dauth.b1nd.com")
                .client(client())
                .build();

        return retrofit.create(DAuthApi.class);
    }

    @Bean
    public DodamApi dodamApi() {
        Retrofit retrofit = new Retrofit.Builder()
                .addConverterFactory(JacksonConverterFactory.create())
                .client(client())
                .baseUrl("http://open.dodam.b1nd.com/")
                .build();

        return retrofit.create(DodamApi.class);
    }
}
