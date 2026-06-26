package funeraleslaluz.com.controlador;

import funeraleslaluz.com.modelo.EmailContacto;
import funeraleslaluz.com.servicio.AsistenciaService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;


@Controller
public class WebController {

    @Autowired
    private AsistenciaService asistenciaService;

    @GetMapping("/")
    public String index() {
        return "index";
    }

    @GetMapping("/planes")
    public String planes(Model model, HttpServletRequest request) {
        model.addAttribute("currentUri", request.getRequestURI());
        return "planes";
    }

    @GetMapping("/prevision-empresarial")
    public String verPrevisionEmpresarial(Model model) {
        model.addAttribute("prevision-empresarial", asistenciaService.obtenerTodosLosPlanes());
        return "prevision-empresarial";
    }


    @GetMapping("/mascotas")
    public String verMascotas() {
        return "mascotas";
    }



    @GetMapping("/sedes")
    public String mostrarSedes() {
        return "sedes";
    }

    @GetMapping("/contacto")
    public String mostrarContacto(Model model, HttpServletRequest request) {
        // 1. Forzamos la creación de la sesión HTTP usando el parámetro inyectado 'request'
        request.getSession(true);

        // 2. Pasamos el objeto con el mismo nombre que espera tu HTML: "emailContacto"
        model.addAttribute("emailContacto", new EmailContacto());

        return "contacto";
    }

    // ==========================================
    // RUTAS DEL MENÚ DE SERVICIOS
    // ==========================================

    @GetMapping("/servicios/atencion-inmediata")
    public String atencionInmediata() {
        // Busca el archivo en src/main/resources/templates/servicios/atencion-inmediata.html
        return "servicios/atencion-inmediata";
    }

    @GetMapping("/servicios/tradicionales")
    public String serviciosTradicionales() {
        return "servicios/tradicionales";
    }

    @GetMapping("/servicios/personalizados")
    public String serviciosPersonalizados() {
        return "servicios/personalizados";
    }

    @GetMapping("/servicios/internacionales")
    public String serviciosInternacionales() {
        return "servicios/internacionales";
    }

    @GetMapping("/servicios/conmemoraciones")
    public String conmemoraciones() {
        return "servicios/conmemoraciones";
    }

    @GetMapping("/servicios/repatriacion")
    public String repatriacion() {
        return "servicios/repatriacion";
    }



    // ==========================================
    // RUTAS DEL FOOTER
    // ==========================================

    @GetMapping("/proteccion-datos")
    public String proteccionDatos() {
        return "proteccion-datos";
    }
}