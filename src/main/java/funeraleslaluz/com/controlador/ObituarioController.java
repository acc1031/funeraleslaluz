package funeraleslaluz.com.controlador;

import funeraleslaluz.com.modelo.Obituario;
import funeraleslaluz.com.modelo.Usuario;
import funeraleslaluz.com.repositorio.ObituarioRepository;
import funeraleslaluz.com.repositorio.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/obituarios")
public class ObituarioController {

    @Autowired
    private ObituarioRepository obituarioRepository;

    @GetMapping
    public String verPanelPublico(Model model) {
        model.addAttribute("obituarios", obituarioRepository.findAllByOrderByFechaFallecimientoDesc());
        return "obituarios/panel-publico";
    }

    @GetMapping("/crear")
    public String mostrarFormularioCrear(Model model) {
        model.addAttribute("obituario", new Obituario());
        model.addAttribute("obituariosCreados", obituarioRepository.findAllByOrderByFechaFallecimientoDesc());
        return "obituarios/formulario-crear";
    }

    @PostMapping("/guardar")
    public String guardarObituario(@ModelAttribute Obituario obituario) {
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