package funeraleslaluz.com.controlador;

import funeraleslaluz.com.modelo.EmailContacto;
import funeraleslaluz.com.repositorio.EmailRepository;
import funeraleslaluz.com.servicio.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class ContactoController {

    @Autowired
    private EmailService emailService;

    @PostMapping("/contacto/enviar")
    public String enviarFormulario(@ModelAttribute EmailContacto contacto, RedirectAttributes redirectAttrs) {
        try {
            // Delegamos todo al servicio: Guardar en BD y Enviar Email
            emailService.procesarNuevaSolicitud(
                    contacto.getNombre(),
                    contacto.getTelefono(),
                    contacto.getEmail(),
                    contacto.getMensaje()
            );

            redirectAttrs.addFlashAttribute("mensajeExito", "Tu solicitud ha sido enviada. Un asesor se comunicará contigo pronto.");

        } catch (Exception e) {
            // Imprime el error en consola para que sepas qué falló (importante en desarrollo)
            e.printStackTrace();
            redirectAttrs.addFlashAttribute("mensajeError", "Lo sentimos, hubo un error. Por favor comunícate a nuestras líneas 24h.");
        }

        return "redirect:/contacto";
    }
}
