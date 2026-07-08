package funeraleslaluz.com.configuracion;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.csrf.HttpSessionCsrfTokenRepository;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                // 1. CABECERAS DE SEGURIDAD (Protección contra Clickjacking y XSS)
                .headers(headers -> headers
                        .frameOptions(frame -> frame.deny()) // Evita que metan tu web en un <iframe> falso
                )

                // 2. CONFIGURACIÓN EXPLICITA DE CSRF
                // Esto le dice a Spring cómo manejar los tokens de seguridad en los formularios públicos
                .csrf(csrf -> csrf
                        .csrfTokenRepository(new HttpSessionCsrfTokenRepository())
                )

                // 3. REGLAS DE ACCESO SELECTIVAS
                .authorizeHttpRequests(auth -> auth
                        // Estáticos optimizados (¡CORREGIDO: Agregado /pdf/** para permitir el catálogo!)
                        .requestMatchers("/css/**", "/js/**", "/img/**", "/pdf/**", "/webjars/**").permitAll()

                        // Rutas públicas explícitas y Websockets
                        .requestMatchers("/", "/obituarios", "/obituarios/usuarios/login", "/ws-chat/**").permitAll()

                        // 🔒 Rutas Privadas (Panel de Control de Obituarios)
                        .requestMatchers("/obituarios/crear/**").authenticated()
                        .requestMatchers("/obituarios/usuarios/**").authenticated()
                        .requestMatchers("/obituarios/guardar/**").authenticated()
                        .requestMatchers("/obituarios/eliminar/**").authenticated()

                        // 🔓 ¡Todo lo demás en la web es público! (Servicios, Contacto, Planes, etc.)
                        .anyRequest().permitAll()
                )

                // 4. CONFIGURACIÓN DEL FORMULARIO DE ACCESO
                .formLogin(form -> form
                        .loginPage("/obituarios/usuarios/login")
                        .loginProcessingUrl("/obituarios/usuarios/login")
                        .defaultSuccessUrl("/obituarios/crear", true)
                        .permitAll()
                )

                // 5. CONTROL DE SALIDA (LOGOUT)
                .logout(logout -> logout
                        .logoutUrl("/obituarios/usuarios/logout")
                        .logoutSuccessUrl("/obituarios/usuarios/login?logout=true")
                        .invalidateHttpSession(true)
                        .clearAuthentication(true)
                        .deleteCookies("JSESSIONID")
                        .permitAll()
                );

        return http.build();
    }
}