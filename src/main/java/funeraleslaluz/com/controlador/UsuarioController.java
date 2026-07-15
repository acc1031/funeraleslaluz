package funeraleslaluz.com.controlador;

import funeraleslaluz.com.modelo.Usuario;
import funeraleslaluz.com.repositorio.ObituarioRepository;
import funeraleslaluz.com.repositorio.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder; // <-- IMPORTANTE
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/obituarios/usuarios")
public class UsuarioController {

    @Autowired
    private UserRepository usuarioRepository;

    @Autowired
    private ObituarioRepository obituarioRepository;

    @Autowired
    private PasswordEncoder passwordEncoder; // <-- INYÉCTALO AQUÍ

    @GetMapping
    public String listarUsuarios(Model model) {
        model.addAttribute("usuario", new Usuario());
        model.addAttribute("usuariosRegistrados", usuarioRepository.findAll());
        model.addAttribute("obituariosCreados", obituarioRepository.findAll());
        return "obituarios/user";
    }

    @GetMapping("/login")
    public String mostrarLogin(Model model) {
        // Asegúrate de que este apunte a tu plantilla de login (vimos que usabas 'login' o 'signup')
        model.addAttribute("title", "Inicio de Sesión - La Luz Asistencia Integral");
        return "signup";
    }

    @GetMapping("/editar/{id}")
    public String editarUsuario(@PathVariable("id") Long id, Model model) {
        Usuario usuarioExistente = usuarioRepository.findById(id).orElse(null);
        model.addAttribute("usuario", usuarioExistente);
        model.addAttribute("usuariosRegistrados", usuarioRepository.findAll());
        model.addAttribute("obituariosCreados", obituarioRepository.findAll());
        return "obituarios/user";
    }

    @PostMapping("/guardar")
    public String guardarUsuario(@ModelAttribute("usuario") Usuario usuario, RedirectAttributes redirectAttributes) {
        try {
            // 1. Validar si es una EDICIÓN (el usuario ya tiene un ID)
            if (usuario.getId() != null) {
                Usuario usuarioExistente = usuarioRepository.findById(usuario.getId()).orElse(null);

                if (usuarioExistente != null) {
                    // Caso A: Si el administrador NO escribió una nueva contraseña en el formulario
                    if (usuario.getContrasena() == null || usuario.getContrasena().trim().isEmpty()) {
                        // Conservamos el hash seguro que ya estaba guardado en la BD
                        usuario.setContrasena(usuarioExistente.getContrasena());
                    } else {
                        // Caso B: Si escribió una nueva contraseña, ¡tenemos que encriptarla!
                        usuario.setContrasena(passwordEncoder.encode(usuario.getContrasena()));
                    }

                    if (usuarioExistente.getFechaRegistro() != null) {
                        usuario.setFechaRegistro(usuarioExistente.getFechaRegistro());
                    }
                }
                usuario.setFechaActualizacion(new java.util.Date());
                redirectAttributes.addFlashAttribute("success", "Usuario actualizado con éxito.");
            }
            // 2. Es un USUARIO NUEVO
            else {
                usuario.setFechaRegistro(new java.util.Date());

                // ¡BLINDAJE AQUÍ! Encriptamos la contraseña del nuevo operador antes de guardarlo
                String claveEncriptada = passwordEncoder.encode(usuario.getContrasena());
                usuario.setContrasena(claveEncriptada);

                redirectAttributes.addFlashAttribute("success", "Usuario creado con éxito.");
            }

            // 3. Guardar en la base de datos con la contraseña debidamente hasheada
            usuarioRepository.save(usuario);

        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Hubo un problema al procesar el usuario: " + e.getMessage());
        }

        return "redirect:/obituarios/usuarios";
    }

    /**
     * Ruta: GET /obituarios/usuarios/eliminar/{id}
     * Elimina una cuenta de usuario permanentemente
     */
    @GetMapping("/eliminar/{id}")
    public String eliminarUsuario(@PathVariable("id") Long id, RedirectAttributes redirectAttributes) {
        try {
            Usuario usuario = usuarioRepository.findById(id).orElse(null);

            if (usuario != null) {
                // Validación extra en el backend por seguridad: evitar borrar el admin principal
                if ("admin".equalsIgnoreCase(usuario.getCorreo())) {
                    redirectAttributes.addFlashAttribute("error", "No está permitido eliminar la cuenta raíz del administrador.");
                } else {
                    usuarioRepository.deleteById(id);
                    redirectAttributes.addFlashAttribute("success", "El usuario ha sido eliminado correctamente del sistema.");
                }
            } else {
                redirectAttributes.addFlashAttribute("error", "El usuario que intenta eliminar no existe.");
            }
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Error al eliminar el operador: " + e.getMessage());
        }

        // Redirige de vuelta al listado general de gestión de seguridad
        return "redirect:/obituarios/usuarios";
    }
}