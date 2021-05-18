package web.service.forum.config.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
public class CustomWebSecurityConfigurerAdapter extends WebSecurityConfigurerAdapter {

    // TODO check why the constructor give cycle --'
    @Autowired private PasswordEncoder passwordEncoder;

    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication()
                .withUser("user").password(this.passwordEncoder.encode("user")).roles("USER")
                .and()
                .withUser("admin").password(this.passwordEncoder.encode("admin")).roles("ADMIN", "USER");
               // .withUser("admin").password(this.passwordEncoder.encode("admin")).authorities("ROLE_ADMIN", "ROLE_USER");
    }

    @Override
    public void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .formLogin().and()
                .logout().deleteCookies("JSESSIONID").invalidateHttpSession(true).and()
                .authorizeRequests()
                .antMatchers("/login").permitAll()
               // .antMatchers(HttpMethod.PUT).hasAnyAuthority("ROLE_ADMIN")
                .antMatchers(HttpMethod.PUT).hasRole("ADMIN")
                .antMatchers(HttpMethod.POST).hasRole("ADMIN")
                .antMatchers(HttpMethod.DELETE).hasRole("ADMIN")
                .antMatchers(HttpMethod.GET).hasRole("USER")
                .anyRequest().authenticated();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // 3. protéger les API de modification pour que seule les admins puissent modifier/supprimer/créer les ressources


    // 1. 2 utilisateurs OK
    //  - USER => ROLES => USER OK
    //  - ADMIN => ROLES => USER et ADMIN OK

    // 2. protéger toutes les resources pour que seul les users authentifiés puissent utiliser l'API rest OK
    // 3. protéger les API de modification pour que seule les admins puissent modifier/supprimer/créer les ressources
    // 4. Ajout d'un endpoint pour récuperer le login du user connecté

}
