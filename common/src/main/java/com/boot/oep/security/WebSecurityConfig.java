package com.boot.oep.security;

import com.boot.oep.result.ApiResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.annotation.Resource;

/**
 * @author ccjr
 * @date 2021/3/18 9:16 下午
 */
@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Resource
    private UserDetailsServiceImpl userDetailsService;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        System.err.println(passwordEncoder().encode("admin"));
        //交由自定义的UserDetailsService做认证
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.cors().and().csrf().disable()
                .authorizeRequests()
                .antMatchers("/teacher/**").hasAnyRole("TEACHER", "ADMIN")
                .antMatchers("/student/**").hasAnyRole("STUDENT", "ADMIN")
                .antMatchers("/admin/**").hasRole("ADMIN")
                .antMatchers("/common/**").hasAnyRole("ADMIN", "TEACHER", "STUDENT")
                .anyRequest().authenticated()
                .and()
                .formLogin()
                .loginProcessingUrl("/login")
                .successHandler((httpServletRequest, httpServletResponse, authentication) -> {
                    httpServletResponse.setHeader("Content-Type", "application/json;charset=utf-8");
                    httpServletResponse.getWriter().print(new ObjectMapper().writeValueAsString(ApiResponse.ok(SecurityUserUtils.getCurUser())));
                    httpServletResponse.getWriter().flush();
                })
                .failureHandler((httpServletRequest, httpServletResponse, e) -> {
                    httpServletResponse.setHeader("Content-Type", "application/json;charset=utf-8");
                    httpServletResponse.getWriter().print(new ObjectMapper().writeValueAsString(ApiResponse.fail("登录失败:" + e.getMessage())));
                    httpServletResponse.getWriter().flush();
                })
                .permitAll()
                .and()
                .logout().logoutUrl("/logout")
                .logoutSuccessHandler((httpServletRequest, httpServletResponse, authentication) -> {
                    httpServletResponse.setHeader("Content-Type", "application/json;charset=utf-8");
                    httpServletResponse.getWriter().print(new ObjectMapper().writeValueAsString(ApiResponse.ok("注销成功")));
                    httpServletResponse.getWriter().flush();
                })
                .and()
                .exceptionHandling().authenticationEntryPoint((httpServletRequest, httpServletResponse, e) -> {
            httpServletResponse.setHeader("Content-Type", "application/json;charset=utf-8");
            httpServletResponse.getWriter().print(new ObjectMapper().writeValueAsString(ApiResponse.fail(233,"匿名用户!")));
            httpServletResponse.getWriter().flush();
        })
                .accessDeniedHandler((httpServletRequest, httpServletResponse, e) -> {
                    httpServletResponse.setHeader("Content-Type", "application/json;charset=utf-8");
                    httpServletResponse.getWriter().print(new ObjectMapper().writeValueAsString(ApiResponse.fail("你没有足够的权限!")));
                    httpServletResponse.getWriter().flush();
                });
    }
}
