package com.mercadolibre.bootcamp_w1_g7_mlb_frescos.config;

import com.mercadolibre.bootcamp_w1_g7_mlb_frescos.security.JWTFilter;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@EnableWebSecurity
@Configuration
public class WebSecurityConfig extends WebSecurityConfigurerAdapter{

    @Override
    protected void configure(HttpSecurity http) throws Exception{
        http.csrf().disable()
                .addFilterBefore(new JWTFilter(), UsernamePasswordAuthenticationFilter.class)
                .authorizeRequests()
                .antMatchers(HttpMethod.GET, "/ping").permitAll()
                .antMatchers(HttpMethod.POST, "/login").permitAll()
//                .mvcMatchers(HttpMethod.GET, "/swagger-ui").permitAll()
                .mvcMatchers(HttpMethod.POST, "/inboundorder").hasRole("SUPERVISOR")
                .mvcMatchers(HttpMethod.PUT, "/inboundorder").hasRole("SUPERVISOR")
                .mvcMatchers(HttpMethod.GET, "/list").hasRole("SUPERVISOR")
                .mvcMatchers(HttpMethod.GET, "/warehouse").hasRole("SUPERVISOR")
                .mvcMatchers(HttpMethod.GET, "/due-date").hasRole("SUPERVISOR")
                .anyRequest().authenticated();
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/v3/api-docs/**", "/swagger-ui.html", "/swagger-ui/**");
    }

}
