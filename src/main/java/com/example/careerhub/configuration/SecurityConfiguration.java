package com.example.careerhub.configuration;

import com.example.careerhub.service.CustomerDetailsService.CustomerAdminDetailsService;
import com.example.careerhub.service.CustomerDetailsService.CustomerSeekerDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {
   
    @Autowired
    @Qualifier("customerUserDetailsService")
    private UserDetailsService userDetailsService;

    @Autowired
    @Qualifier("customerSeekerDetailsService")
    private CustomerSeekerDetailsService seekerDetailsService;

    @Autowired
    @Qualifier("customerAdminDetailsService")
    private CustomerAdminDetailsService adminDetailsService;

    @Bean
    public static PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf(csrf -> csrf.disable())
                .authorizeHttpRequests((authorize) ->
                        authorize

                                .requestMatchers("/", "/index","login", "/js/**", "/register",  "register/**", "/static/**").permitAll()
                                .requestMatchers("/admin_home", "/admin/**").hasAuthority("ADMIN")
                                .requestMatchers("employer", "/employer/**", "employer-home").hasAuthority("USER")
                                .requestMatchers("seeker", "/seeker/**").hasAuthority("SEEKER")
                                                                .anyRequest().permitAll()
                ).formLogin(
                        form -> form
                                .loginPage("/login")
                                .loginProcessingUrl("/login")
                                .successHandler(successHandler())
                                .permitAll()
                ).logout(
                        logout -> logout
                                .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                                 .permitAll()
                );
        return http.build();
    }

    @Bean
    public AuthenticationSuccessHandler successHandler() {
        return new CustomAuthenticationSuccessHandler();
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth
                .userDetailsService(userDetailsService)
                .passwordEncoder(passwordEncoder());
        auth
                .userDetailsService(seekerDetailsService)
                .passwordEncoder(passwordEncoder());
        auth
                .userDetailsService(adminDetailsService)
                .passwordEncoder(passwordEncoder());
    }
}
