package funeraleslaluz.com.controlador;

import funeraleslaluz.com.modelo.Usuario;
import funeraleslaluz.com.repositorio.ObituarioRepository;
import funeraleslaluz.com.repositorio.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/obituarios/usuarios") // -> Base de todas las URLs en este controlador
public class UsuarioController {

    @Autowired
    private UserRepository usuarioRepository;

    @Autowired
    private ObituarioRepository obituarioRepository; // <- Inyéctalo aquí

    /**
     * Ruta: GET /usuarios
     * Este método se encarga de la raíz. Cuando entres a http://localhost:3587/usuarios
     * cargará la vista 'user.html' con la lista de usuarios de la base de datos.
     */
    @GetMapping
    public String listarUsuarios(Model model) {
        model.addAttribute("usuario", new Usuario());
        model.addAttribute("usuariosRegistrados", usuarioRepository.findAll());

        model.addAttribute("obituariosCreados", obituarioRepository.findAll());

        // Debe coincidir con 'user.html' en src/main/resources/templates/
        return "obituarios/user";
    }

    @GetMapping("/login")
    public String mostrarLogin() {
        // Retorna el archivo login.html ubicado directamente en templates/
        return "signup";
    }

    /**
     * Ruta: GET /usuarios/editar/{id}
     * Carga el usuario seleccionado para editarlo en la misma vista 'user.html'.
     */
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
            // 1. Validar si es una edición (el usuario ya tiene un ID)
            if (usuario.getId() != null) {
                Usuario usuarioExistente = usuarioRepository.findById(usuario.getId()).orElse(null);

                if (usuarioExistente != null) {
                    // Si la contraseña del formulario viene vacía, conservamos la que ya tenía
                    if (usuario.getContrasena() == null || usuario.getContrasena().trim().isEmpty()) {
                        usuario.setContrasena(usuarioExistente.getContrasena());
                    }

                    // Mantenemos la fecha de registro original si la tienes mapeada
                    if (usuarioExistente.getFechaRegistro() != null) {
                        usuario.setFechaRegistro(usuarioExistente.getFechaRegistro());
                    }
                }
                usuario.setFechaActualizacion(new java.util.Date());
                redirectAttributes.addFlashAttribute("success", "Usuario actualizado con éxito.");
            } else {
                // 2. Es un usuario nuevo
                usuario.setFechaRegistro(new java.util.Date());
                redirectAttributes.addFlashAttribute("success", "Usuario creado con éxito.");
            }

            // 3. Guardar en la base de datos
            usuarioRepository.save(usuario);

        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Hubo un problema al procesar el usuario: " + e.getMessage());
        }

        // Redirige a la pantalla del listado general
        return "redirect:/obituarios/usuarios";
    }
}