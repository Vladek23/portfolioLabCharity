package pl.coderslab.charity.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import pl.coderslab.charity.services.impl.SpringDataUserDetailsService;

import javax.sql.DataSource;

@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final DataSource dataSource;

    public SecurityConfig(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    @Bean
    public SpringDataUserDetailsService customUserDetailsService() {
        return new SpringDataUserDetailsService();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {

        auth.jdbcAuthentication()
                .dataSource(dataSource)
                .passwordEncoder(passwordEncoder())
                .usersByUsernameQuery("SELECT email, password, active FROM users WHERE email = ?")
                .authoritiesByUsernameQuery("SELECT u.email, r.name FROM users u JOIN users_roles ur ON u.id = ur.user_id JOIN roles r ON ur.roles_id = r.id WHERE u.email = ?");
    }

    @Override
    public void configure(WebSecurity web) throws Exception {

        web.ignoring()
                .antMatchers("/resources/images/**")    // pictures
                .antMatchers("/resources/css/**")       // css
                .antMatchers("/resources/js/**")        // JavaScript
                .antMatchers("/webjars/**");            // residual from other project with webjar - BULMA
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/", "/home").permitAll()
                .antMatchers("/index").permitAll()
                .antMatchers("/index/**").permitAll()
                .antMatchers("/donation/*").permitAll()
                .antMatchers("/registration/*").permitAll()
                .antMatchers("/login").anonymous()
                .antMatchers("/logout").authenticated()
                .antMatchers("/user", "/user/**").hasRole("USER")
                .antMatchers("/admin", "/admin/**").hasRole("ADMIN")
                .anyRequest().authenticated()
                .and()
                // Redirect to login
                .formLogin()                        // Przekierowanie do logowania w przypadku wejścia nieautoryzowanego
                // na strony w/w. Logowanie wg poniższych ustawień:
                .loginPage("/login")                // Security obsługuje działanie logowania na ścieżce "/login" (w tym wypadku
                // LoginController.java + login.jsp; LoginController.java przekierowuje
                // przez Get na login.jsp)
                .loginProcessingUrl("/login")       // wysłanie/zwrócenie wypełnionego formularza (z login.jsp)
                .usernameParameter("email")         // Security sam odbiera POST i sprawdza zgodność username (email) i password
                //  z formularza - JUŻ NIE POTRZEBA obsługi post w LoginController.java
                .passwordParameter("password")      // j.w.
                .defaultSuccessUrl("/")             // jeżeli logowanie się powiodło Security przekierowuje na ścieżkę
                // "/" (de facto index.jsp)
                .and()
                // Finishing after logout
                .logout()
                .logoutUrl("/logout")               // Security sam obsługuje działanie wylogowania przez POST na ścieżce
                // "/logout" (w tym wypadku ten POST przychodzi bezpośrednio z viewera
                // tj. z header.jsp.
                // Dzięki temu nie potrzeba ani kontrolera ani viewera dla logout
                .logoutSuccessUrl("/")              // jeżeli logowanie się powiodło Security przekierowuje na ścieżkę
                // "/" (de facto index.jsp)
                .and()
                .csrf()                            // generuje identyfikacyjny numer do kożdego pola formularza (w
                // formularzu *.jsp dodajemy <set:csrfInput/> w obrębie adnotacji <form>
                .and()
                .exceptionHandling().accessDeniedPage("/403");  // przekierowania na widok 403.jsp przy niezgodnym z
        // autoryzacją wejściem na dany zasób


    }
}
