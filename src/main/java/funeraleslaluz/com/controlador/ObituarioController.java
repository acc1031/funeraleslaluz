package funeraleslaluz.com.controlador;

import funeraleslaluz.com.modelo.Obituario;
import funeraleslaluz.com.repositorio.ObituarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDateTime;

@Controller
@RequestMapping("/obituarios")
public class ObituarioController {

    @Autowired
    private ObituarioRepository obituarioRepository;

    // Lista de frases predefinidas para los homenajes
    private static final String[] FRASES_HOMENAJE = {
            "Su luz no se apaga; se transforma en un recuerdo eterno que nos acompañará siempre.",
            "Su memoria vivirá con especial cariño en el corazón de quienes compartieron su camino.",
            "Un homenaje sincero a una vida compartida, cuyo recuerdo permanecerá siempre con nosotros.",
            "Su legado de amor, bondad y enseñanzas continuará siendo una guía en nuestro caminar.",
            "Agradecemos profundamente cada momento compartido y la huella imborrable que deja en nuestras vidas.",
            "Su recuerdo es un tesoro invaluable que guardaremos con el más profundo afecto.",
            "Recordamos con inmensa gratitud su paso por nuestras vidas y el afecto que siempre nos brindó.",
            "Un ser maravilloso cuyo ejemplo y calidez vivirán por siempre en nuestra memoria. Descanse en paz."
    };

    // Método auxiliar para obtener una frase al azar
    private String obtenerFraseAleatoria() {
        int index = (int) (Math.random() * FRASES_HOMENAJE.length);
        return FRASES_HOMENAJE[index];
    }

    @GetMapping
    public String verPanelPublico(Model model) {
        model.addAttribute("obituarios", obituarioRepository.findAllByOrderByFechaFallecimientoDesc());
        model.addAttribute("title", "Obiturarios - La Luz Asistencia Integral");
        return "obituarios/panel-publico";
    }

    @GetMapping("/crear")
    public String mostrarFormularioCrear(Model model) {
        // 1. Creamos la instancia del obituario
        Obituario nuevoObituario = new Obituario();

        // 2. Le asignamos la frase aleatoria usando tu método auxiliar
        nuevoObituario.setMensajeHomenaje(obtenerFraseAleatoria());

        // 3. Pasamos el objeto ya preparado al modelo
        model.addAttribute("obituario", nuevoObituario);
        model.addAttribute("obituariosCreados", obituarioRepository.findAllByOrderByFechaFallecimientoDesc());

        return "obituarios/formulario-crear";
    }

    @PostMapping("/guardar")
    public String guardarObituario(@ModelAttribute Obituario obituario) {
        // 1. Si es una CREACIÓN (id es null)
        if (obituario.getId() == null) {
            // Asignamos automáticamente la fecha y hora actual como fecha de fallecimiento
            obituario.setFechaFallecimiento(LocalDateTime.now());
        } else {
            // 2. Si es una EDICIÓN (id ya existe)
            // Buscamos el registro anterior en la base de datos para no perder su fecha de fallecimiento original
            obituarioRepository.findById(obituario.getId()).ifPresent(original -> {
                obituario.setFechaFallecimiento(original.getFechaFallecimiento());

                // Conservamos también el campo "cementerio" histórico por si venía de un registro viejo
                if (obituario.getCementerio() == null) {
                    obituario.setCementerio(original.getCementerio());
                }
            });
        }

        obituarioRepository.save(obituario);
        return "redirect:/obituarios/crear";
    }

    @GetMapping("/editar/{id}")
    public String mostrarFormularioEditar(@PathVariable("id") Long id, Model model) {
        Obituario obituarioExistente = obituarioRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("ID de Homenaje inválido: " + id));
        model.addAttribute("obituario", obituarioExistente);
        model.addAttribute("obituariosCreados", obituarioRepository.findAllByOrderByFechaFallecimientoDesc());
        return "obituarios/formulario-crear";
    }

    @GetMapping("/eliminar/{id}")
    public String eliminarObituario(@PathVariable("id") Long id, RedirectAttributes redirectAttributes) {
        try {
            obituarioRepository.deleteById(id);
            redirectAttributes.addFlashAttribute("success", "El homenaje ha sido removido correctamente.");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "No se pudo eliminar el registro seleccionado.");
        }
        return "redirect:/obituarios/crear";
    }

}