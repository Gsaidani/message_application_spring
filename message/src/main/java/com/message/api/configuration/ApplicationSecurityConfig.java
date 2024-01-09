package com.message.api.configuration;

import com.nimbusds.jose.jwk.JWK;
import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jose.jwk.source.ImmutableJWKSet;
import com.nimbusds.jose.jwk.source.JWKSource;
import com.nimbusds.jose.proc.SecurityContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.FormLoginConfigurer;
import org.springframework.security.config.annotation.web.configurers.LogoutConfigurer;
import org.springframework.security.config.annotation.web.configurers.oauth2.server.resource.OAuth2ResourceServerConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.core.OAuth2AccessToken;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;


@Component
@EnableWebSecurity
@EnableMethodSecurity(prePostEnabled = true)
public class ApplicationSecurityConfig {
    @Autowired
    private DataSource dataSource;
    private RsakeysConfig rsakeysConfig;

    private PasswordEncoder passwordEncoder;

    public ApplicationSecurityConfig(RsakeysConfig rsakeysConfig, PasswordEncoder passwordEncoder) {
        this.rsakeysConfig = rsakeysConfig;
        this.passwordEncoder = passwordEncoder;
    }

    @Bean
    public AuthenticationManager authenticationManager(UserDetailsService userDetailsService){
        var authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setPasswordEncoder(passwordEncoder);
        authenticationProvider.setUserDetailsService(userDetailsService);
        return new ProviderManager(authenticationProvider);
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
          return http
                  .csrf(AbstractHttpConfigurer::disable)
                  .authorizeHttpRequests(
                           auth -> auth.requestMatchers(new AntPathRequestMatcher("/login/**")).permitAll())
                  .authorizeHttpRequests(auth->auth.anyRequest().authenticated())
//                .authorizeHttpRequests((authorize) -> authorize
//                          .requestMatchers(HttpMethod.PUT).hasAuthority("MANAGER")
//                          .requestMatchers(HttpMethod.GET).hasAuthority("MANAGER")
//                          .anyRequest().denyAll()
//                  )
                  .sessionManagement(session->session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                  .oauth2ResourceServer(OAuth2ResourceServerConfigurer::jwt)
                  .httpBasic(Customizer.withDefaults())
                  .build();

    }

    @Bean
    public JwtEncoder jwtEncoder(){
//        System.out.println("rsakeysConfig.publicKey() : "+rsakeysConfig.publicKey());
//        System.exit(0);
        JWK jwk= new RSAKey.Builder(rsakeysConfig.getPublicKey()).privateKey(rsakeysConfig.getPrivateKey()).build();
        JWKSource<SecurityContext> jwkSource=new ImmutableJWKSet<>(new JWKSet(jwk));
        return new NimbusJwtEncoder(jwkSource);
    }

    @Bean
    public JwtDecoder jwtDecoder(){
        return NimbusJwtDecoder.withPublicKey(rsakeysConfig.getPublicKey()).build();
    }

    @Bean
    public UserDetailsService inMemoryUserDetailsManager() {
        UserDetails user = User.withUsername("user")
                .password(passwordEncoder.encode("password_user"))
                .authorities("USER")
                .build();
        UserDetails manager =User.withUsername("manager")
                .password(passwordEncoder.encode("password_manager"))
                .authorities("MANAGER", "USER")
                .build();
        UserDetails admin = User.withUsername("admin")
                .password(passwordEncoder.encode("password_admin"))
                .authorities("ADMIN","MANAGER","USER")
                .build();
        return new InMemoryUserDetailsManager(user,manager,admin);
    }

//deprecated
//public class ApplicationSecurityConfig extends WebSecurityConfigurerAdapter {



//    @Override
//    protected void configure(final AuthenticationManagerBuilder auth) throws Exception {
//        auth.inMemoryAuthentication()
//                .withUser("admin1").password(passwordEncoder().encode("admin1Pass")).roles("ADMIN")
//                .and()
//                .withUser("admin2").password(passwordEncoder().encode("admin2Pass")).roles("ADMIN");
//    }

//    @Bean
//    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
//        auth.jdbcAuthentication()
//                .dataSource(dataSource);
// //             .usersByUsernameQuery("select name as username, password, true"
////                        + " from user where name=?")
////              .authoritiesByUsernameQuery("select name as username, upper(concat('role_', role))"
////                        + " from user where name=?");
//
////                .withDefaultSchema()
////                .withUser(User.withUsername("user")
////                        .password(passwordEncoder.encode("pass"))
////                        .roles("USER"));
//    }

//    @Bean
//    public UserDetailsManager findUserByUsername(String username) {
//        JdbcUserDetailsManager users = new JdbcUserDetailsManager(dataSource);
//        return (UserDetailsManager) users.loadUserByUsername(username);
//    }

}
