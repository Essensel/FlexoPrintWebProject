package ru.vishnyakov.flexoPrint.controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import ru.vishnyakov.flexoPrint.controllers.services.UserService;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    protected UserService userService;

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }


    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .authorizeRequests()
                   .antMatchers("/check/").authenticated()
                   .antMatchers("/newsform/*").hasRole("EDITOR")
                   .antMatchers("/**").permitAll()
                .and()
                  .formLogin()
                  .loginPage("/login/")
                  .defaultSuccessUrl("/main")
                  .permitAll()
                .and()
                .logout()
                .logoutUrl("/logout/")
                .logoutSuccessUrl("/login/?logout")
                .permitAll()
                .and()
                .exceptionHandling().accessDeniedPage("/login/?access");
    }
    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(this.userService).passwordEncoder(this.bCryptPasswordEncoder());
    }
}
