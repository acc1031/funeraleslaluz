package funeraleslaluz.com.controlador;

import funeraleslaluz.com.modelo.Usuario;
import funeraleslaluz.com.repositorio.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository usuarioRepository;

    @Override
    public UserDetails loadUserByUsername(String emailInput) throws UsernameNotFoundException {
        // Buscamos al operador por el correo ingresado en el formulario
        Usuario usuario = usuarioRepository.findByCorreoIgnoreCase(emailInput)
                .orElseThrow(() -> new UsernameNotFoundException("Operador no encontrado con el correo: " + emailInput));

        // Retornamos el objeto User nativo de Spring Security.
        // Spring Security comparará internamente la contraseña ingresada contra el hash de la BD.
        return new User(usuario.getCorreo(), usuario.getContrasena(), new ArrayList<>());
    }
}