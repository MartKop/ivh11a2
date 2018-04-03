package avans.ivh11.mart.demo.Config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    public void configure(WebSecurity web) throws Exception {
        web
                .ignoring()
                .antMatchers("/resources/**")
                .antMatchers("/publics/**")
                .antMatchers("/")
        ;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()

                .authorizeRequests()
                .antMatchers("/admin/**").hasRole("ADMIN")
                .antMatchers("/user/**").hasRole("USER")
                .antMatchers("/profile/**").hasRole("USER")
                .antMatchers("/order/**").hasRole("USER")
                .anyRequest().permitAll()
//            .and()
//                .formLogin()
//                .loginPage("/login")
//                .loginProcessingUrl("/login").permitAll()
            .and().logout()
                .logoutUrl("/logout")
                .logoutSuccessUrl("/welcome")
        ;
    }
}

