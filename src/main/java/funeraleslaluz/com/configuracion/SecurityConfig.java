package funeraleslaluz.com.configuracion;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        // Retornamos la implementación estándar de BCrypt
        return new BCryptPasswordEncoder();
    }



    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                // 1. REGLAS DE ACCESO SELECTIVAS
                .authorizeHttpRequests(auth -> auth
                        // 🔒 PRIMERO: Las únicas rutas que requieren estar logueado (Panel de control)
                        .requestMatchers("/obituarios/crear").authenticated()
                        .requestMatchers("/obituarios/crear/**").authenticated()
                        .requestMatchers("/obituarios/usuarios/**").authenticated()
                        .requestMatchers("/obituarios/guardar/**").authenticated()
                        .requestMatchers("/obituarios/eliminar/**").authenticated()
                        .requestMatchers("/css/**", "/js/**", "/img/**", "/webjars/**").permitAll()
                        .requestMatchers("/", "/obituarios", "/obituarios/usuarios/login", "/ws-chat/**").permitAll() // <-- AGREGAR /ws-chat/** AQUÍ

                        // 🔓 SEGUNDO: ¡Todo lo demás en la web es de acceso público!
                        // Esto incluye la raíz (/), ver obituarios públicos, estilos, imágenes, etc.
                        .anyRequest().permitAll()
                )

                // 2. CONFIGURACIÓN DEL FORMULARIO DE ACCESO
                .formLogin(form -> form
                        .loginPage("/obituarios/usuarios/login") // Tu vista de login (signup.html)
                        .loginProcessingUrl("/obituarios/usuarios/login") // URL interna donde Spring procesa el POST
                        .defaultSuccessUrl("/obituarios/crear", true) // Al loguearse con éxito, entra directo al creador
                        .permitAll()
                )

                // 3. CONTROL DE SALIDA (LOGOUT)
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