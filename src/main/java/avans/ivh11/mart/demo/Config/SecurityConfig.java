package avans.ivh11.mart.demo.Config;

import avans.ivh11.mart.demo.Domain.Role;
import avans.ivh11.mart.demo.Repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

import java.util.Iterator;


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
                .antMatchers("/user/**").hasRole("USER") // no effect
                .anyRequest().permitAll()
        ;
//                .authorizeRequests()
//                .antMatchers("/admin/**").hasRole("ADMIN")
//                .anyRequest().anonymous()
//
//                .authenticated();
    }


//    @Autowired
//    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
//        auth.userDetailsService(userDetailsService()).passwordEncoder(bCryptPasswordEncoder());
//    }

//    @Autowired
//    public void addRoles(RoleRepository roleRepository) throws Exception {
//        Iterable<Role> roles = roleRepository.findAll();
//        if(roles.spliterator().getExactSizeIfKnown() == 0) {
//            Role superAdmin = new Role("SUPER_ADMIN");
//            Role admin = new Role("ADMIN");
//            Role user = new Role("USER");
//            roleRepository.save(superAdmin);
//            roleRepository.save(admin);
//            roleRepository.save(user);
//        }
//    }

}

