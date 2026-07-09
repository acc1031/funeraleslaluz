package funeraleslaluz.com.controlador; // Ajusta según tu estructura

import funeraleslaluz.com.modelo.Condolencia;
import funeraleslaluz.com.modelo.Obituario;
import funeraleslaluz.com.repositorio.CondolenciaRepository;
import funeraleslaluz.com.repositorio.ObituarioRepository; // El repositorio de tu obituario
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/obituarios")
public class CondolenciaController {

    @Autowired
    private CondolenciaRepository condolenciaRepository;

    @Autowired
    private ObituarioRepository obituarioRepository;

    @PostMapping("/condolencia/guardar")
    public String guardarCondolencia(@RequestParam("obituarioId") Long obituarioId,
                                     @RequestParam("remitente") String remitente,
                                     @RequestParam("mensaje") String mensaje) {

        // 1. Buscar el obituario por ID usando tu repositorio
        Obituario obituario = obituarioRepository.findById(obituarioId)
                .orElseThrow(() -> new IllegalArgumentException("No se encontró el obituario con ID: " + obituarioId));

        // 2. Construir la condolencia usando el @Builder de Lombok
        Condolencia nuevaCondolencia = Condolencia.builder()
                .remitente(remitente)
                .mensaje(mensaje)
                .obituario(obituario)
                .build();

        // 3. Guardar en la base de datos mediante JPA
        condolenciaRepository.save(nuevaCondolencia);

        // 4. Redireccionar al panel principal (Cambia "/obituarios" por tu ruta base si es diferente)
        return "redirect:/obituarios";
    }
}