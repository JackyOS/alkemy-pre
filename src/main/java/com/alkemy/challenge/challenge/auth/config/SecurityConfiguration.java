package com.alkemy.challenge.challenge.auth.config;

import com.alkemy.challenge.challenge.auth.filter.JwtRequestFilter;
import com.alkemy.challenge.challenge.auth.service.UserDetailsCustomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@EnableWebSecurity //Habilitamos la seguridad a traves de web, de los endpoint...
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Autowired
    private UserDetailsCustomService userDetailsCustomService;
    @Autowired
    private JwtRequestFilter jwtRequestFilter;

    //configuramos quien va a ser el user details (le cambiamos el que viene por default
    //en spring security -el user y el pass que se genera automaticamente-)
    @Override //sobreescribimos el metodo que ya trae spring security
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsCustomService);
    }

    @Bean //generamos un bean (un componente en nuestro contexto de spring) para poder encodear la password
    public PasswordEncoder passwordEncoder(){
        return NoOpPasswordEncoder.getInstance(); //le decimos que no vamos a encodear/codificar la password
    } //esta tachado porque ya no se usa (deprecated). NO es buena practica el NO codificar la password que guardamos en la base de datos

    @Override
    @Bean //definimos el manejador de la autentificacion
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean(); //vamos a usar el que nos provee web security
    }

    @Override //configuramos como se comporta nuestro http security
    protected void configure(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.csrf().disable()
                //configuramos a que endpoint le damos seguridad y a cual no.
                //autorizamos request, a cualquier persona en /auth/* => para que cualquier persona pueda registrarse o loguearse
                .authorizeRequests().antMatchers("/auth/*").permitAll()

                //los demas endpoint ( /peliculas ) si requieren de autentificacion
                .anyRequest().authenticated()
                .and().exceptionHandling()
                .and().sessionManagement()

                //Por defecto, spring security es statefull => es decir, se guarda el estado donde se ejecutó
                //Por ende, una vez que nos logueamos, podemos entrar a todos los endpoint
                //Pero no queremos eso. Queromos que por cada endpoint, por cada peticion, se haga una autentificacion nueva => por eso se pone stateless
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        //configuramos un filtro (genera tokens que quedan en el headers)
        //el filtro se genera antes(before que el de UsernamePassword.....)
        httpSecurity.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);
        /*Se pasa el filter al header para validarlo*/
    }
}


