package com.security.spring_security.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandler;

@EnableWebSecurity
@Configuration
@Slf4j
@EnableMethodSecurity(prePostEnabled = true)

public class SecurityConfig {
    @Autowired
    private AccessDeniedHandler accessDeniedHandler;

    @Bean   // 메소드의 반환객체를 ioc컨테이너에 등록해주는 것
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.csrf(csrf -> csrf.disable()); //시큐리티 로그인 화면 가리기
        httpSecurity.formLogin(
                formLogin -> formLogin
                        .loginPage("/member/login")
                        .loginProcessingUrl("/member/login")
                        .defaultSuccessUrl("/")
                        .failureUrl("/member/login/error")
        );
        httpSecurity.logout(
                logout -> logout
                        .logoutUrl("/member/logout")
                        .logoutSuccessUrl("/")
        );
        httpSecurity.exceptionHandling(
                handler -> handler
                        .accessDeniedHandler(accessDeniedHandler)
        );
        return httpSecurity.build();//이 설정이 빌드임. 설정한 루트로 가겠다.
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
