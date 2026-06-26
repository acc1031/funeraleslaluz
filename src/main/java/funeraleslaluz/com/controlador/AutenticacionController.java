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
     * Ruta: GET /obituarios/usuarios/logout
     * Cierra la sesión del operador de forma segura
     */
    @RequestMapping("/logout")
    public String cerrarSesion(HttpSession session) {
        session.invalidate(); // Destruye la sesión actual
        return "redirect:/obituarios/usuarios/login?logout=true";
    }
}