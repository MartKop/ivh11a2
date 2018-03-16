package avans.ivh11.mart.demo.Config;

import avans.ivh11.mart.demo.Domain.Role;
import avans.ivh11.mart.demo.Repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Iterator;


//@Configuration
//@EnableWebSecurity
public class SecurityConfig { //extends WebSecurityConfigurerAdapter {

//    @Bean
//    public BCryptPasswordEncoder bCryptPasswordEncoder() {
//        return new BCryptPasswordEncoder();
//    }

//    @Override
//    protected void configure(HttpSecurity http) throws Exception {
//        http
//            .authorizeRequests()
//                .antMatchers("/resources/**", "/registration").permitAll()
//                .anyRequest().authenticated()
//                .and()
//            .formLogin()
//                .loginPage("/login")
//                .permitAll()
//                .and()
//            .logout()
//                .permitAll();
//    }

//    @Autowired
//    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
//        auth.userDetailsService(userDetailsService()).passwordEncoder(bCryptPasswordEncoder());
//    }

    @Autowired
    public void addRoles(RoleRepository roleRepository) throws Exception {
        Iterable<Role> roles = roleRepository.findAll();
        if(roles.spliterator().getExactSizeIfKnown() == 0) {
            Role superAdmin = new Role("SUPER_ADMIN");
            Role admin = new Role("ADMIN");
            Role user = new Role("USER");
            roleRepository.save(superAdmin);
            roleRepository.save(admin);
            roleRepository.save(user);
        }
    }

}

