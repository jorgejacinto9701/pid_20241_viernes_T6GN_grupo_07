package com.prestamos.demo.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import com.prestamos.demo.service.UsuariosService;
import com.prestamos.demo.service.UsuariosServiceImpl;

@Configuration
@EnableWebSecurity
public class WebSecurityConfiguration {
	
	@Autowired
	private UsuariosService service;
	
	@Bean 
	public PasswordEncoder passEncoder() {
		return NoOpPasswordEncoder.getInstance();	}

    @Bean
       public AuthenticationProvider authenticationProvider(UsuariosServiceImpl imp) {
		DaoAuthenticationProvider auth = new DaoAuthenticationProvider();
		auth.setUserDetailsService(imp);
		auth.setPasswordEncoder(passEncoder());
		return auth;
	}
	
	/*protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.authenticationProvider(authenticationProvider());
	}*/
	
	protected SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		return http
                .csrf(csrf -> csrf.disable())
                .httpBasic(basic -> basic.disable())
                .formLogin(formLogin -> formLogin
                		.loginPage("/login") // Aquí especificas la ruta de tu formulario personalizado
                        .permitAll())
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .build();
	}
	

}