package funeraleslaluz.com.controlador;

import funeraleslaluz.com.modelo.Usuario;
import funeraleslaluz.com.repositorio.UserRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/obituarios/usuarios")
public class AutenticacionController {

    @Autowired
    private UserRepository usuarioRepository;

    /**
     * Ruta: POST /obituarios/usuarios/login
     * Procesa las credenciales del formulario (signup.html / login.html)
     */
    @PostMapping("/login")
    public String validarCredenciales(
            @RequestParam("username") String correoInput,
            @RequestParam("password") String contrasenaInput,
            HttpSession session,
            RedirectAttributes redirectAttributes) {

        // 1. Buscar al operador en la base de datos por su correo/login
        Usuario usuarioEncontrado = usuarioRepository.findAll().stream()
                .filter(u -> u.getCorreo().equalsIgnoreCase(correoInput))
                .findFirst()
                .orElse(null);

        // 2. Validar si el usuario existe y si la contraseña coincide
        if (usuarioEncontrado != null && usuarioEncontrado.getContrasena().equals(contrasenaInput)) {

            // Guardamos al usuario en la sesión HTTP del navegador para saber quién entró
            session.setAttribute("usuarioLogueado", usuarioEncontrado);

            // Mensaje de bienvenida de éxito
            redirectAttributes.addFlashAttribute("success", "¡Bienvenido de vuelta, " + usuarioEncontrado.getNombre() + "!");

            // Redirección exitosa al formulario de creación de obituarios
            return "redirect:/obituarios/crear";
        }

        // 3. Si las credenciales fallan, regresa al login con un parámetro de error
        redirectAttributes.addFlashAttribute("error", "Nombre de usuario o contraseña incorrectos.");
        return "redirect:/obituarios/usuarios/login?error=true";
    }

    /**
     * Ruta: GET /obituarios/usuarios/logout
     * Cierra la sesión del operador de forma segura
     */
    @RequestMapping("/logout")
    public String cerrarSesion(HttpSession session) {
        session.invalidate(); // Destruye la sesión actual
        return "redirect:/obituarios/usuarios/login?logout=true";
    }
}