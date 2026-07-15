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
    public String index(Model model, HttpServletRequest request) {
        request.getSession(true);
        model.addAttribute("currentUri", request.getRequestURI()); // <--- Agrega esto
        return "index";
    }

    @GetMapping("/planes")
    public String planes(Model model, HttpServletRequest request) {
        request.getSession(true);
        model.addAttribute("currentUri", request.getRequestURI());
        return "planes";
    }

    @GetMapping("/prevision-empresarial")
    public String verPrevisionEmpresarial(Model model, HttpServletRequest request) {
        request.getSession(true);
        model.addAttribute("currentUri", request.getRequestURI());
        model.addAttribute("prevision-empresarial", asistenciaService.obtenerTodosLosPlanes());
        return "prevision-empresarial";
    }

    @GetMapping("/mascotas")
    public String verMascotas(Model model, HttpServletRequest request) {
        request.getSession(true);
        model.addAttribute("currentUri", request.getRequestURI());
        return "mascotas";
    }

    @GetMapping("/sedes")
    public String mostrarSedes(Model model, HttpServletRequest request) {
        request.getSession(true);
        model.addAttribute("currentUri", request.getRequestURI());
        return "sedes";
    }

    @GetMapping("/contacto")
    public String mostrarContacto(Model model, HttpServletRequest request) {
        // 1. Forzamos la creación de la sesión HTTP usando el parámetro inyectado 'request'
        request.getSession(true);
        model.addAttribute("currentUri", request.getRequestURI());

        // 2. Pasamos el objeto con el mismo nombre que espera tu HTML: "emailContacto"
        model.addAttribute("emailContacto", new EmailContacto());

        return "contacto";
    }

    // ==========================================
    // RUTAS DEL MENÚ DE SERVICIOS
    // ==========================================

    @GetMapping("/servicios/atencion-inmediata")
    public String atencionInmediata(Model model, HttpServletRequest request) {
        request.getSession(true);
        model.addAttribute("currentUri", request.getRequestURI());
        return "servicios/atencion-inmediata";
    }

    @GetMapping("/servicios/tradicionales")
    public String serviciosTradicionales(Model model, HttpServletRequest request) {
        request.getSession(true);
        model.addAttribute("currentUri", request.getRequestURI());
        return "servicios/tradicionales";
    }

    @GetMapping("/servicios/personalizados")
    public String serviciosPersonalizados(Model model, HttpServletRequest request) {
        request.getSession(true);
        model.addAttribute("currentUri", request.getRequestURI());
        return "servicios/personalizados";
    }

    @GetMapping("/servicios/internacionales")
    public String serviciosInternacionales(Model model, HttpServletRequest request) {
        request.getSession(true);
        model.addAttribute("currentUri", request.getRequestURI());
        return "servicios/internacionales";
    }

    @GetMapping("/servicios/conmemoraciones")
    public String conmemoraciones(Model model, HttpServletRequest request) {
        // Forzamos la creación de la sesión antes de que Thymeleaf empiece a renderizar
        request.getSession(true);
        model.addAttribute("currentUri", request.getRequestURI());
        return "servicios/conmemoraciones";
    }

    @GetMapping("/servicios/repatriacion")
    public String repatriacion(Model model, HttpServletRequest request) {
        request.getSession(true);
        model.addAttribute("currentUri", request.getRequestURI());
        return "servicios/repatriacion";
    }

    // ==========================================
    // RUTAS DEL FOOTER
    // ==========================================

    @GetMapping("/proteccion-datos")
    public String proteccionDatos(Model model, HttpServletRequest request) {
        request.getSession(true);
        model.addAttribute("currentUri", request.getRequestURI());
        return "proteccion-datos";
    }
}