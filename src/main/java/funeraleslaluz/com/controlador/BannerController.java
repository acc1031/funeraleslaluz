package funeraleslaluz.com.controlador;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

@Controller
@RequestMapping("/obituarios/banner")
public class BannerController {

    // Cambiado para apuntar a la imagen de previsión exequial
    private final String RUTA_ALMACENAMIENTO = "src/main/resources/static/img/";

    @GetMapping
    public String mostrarPanelBanner(Model model) {
        return "obituarios/banner";
    }

    @PostMapping("/guardar")
    public String guardarBanner(@RequestParam("file") MultipartFile archivo,
                                @RequestParam(value = "titulo", required = false) String titulo,
                                @RequestParam(value = "subtitulo", required = false) String subtitulo,
                                RedirectAttributes redirectAttributes) {

        if (archivo.isEmpty()) {
            redirectAttributes.addFlashAttribute("error", "Por favor seleccione una imagen válida para continuar.");
            return "redirect:/obituarios/banner";
        }

        try {
            Path directorioDestino = Paths.get(RUTA_ALMACENAMIENTO);

            if (!Files.exists(directorioDestino)) {
                Files.createDirectories(directorioDestino);
            }

            // AHORA REEMPLAZA DIRECTAMENTE LA IMAGEN DE PREVISIÓN
            Path rutaCompletaArchivo = directorioDestino.resolve("prevision-exequial-banner.jpg");

            Files.copy(archivo.getInputStream(), rutaCompletaArchivo, StandardCopyOption.REPLACE_EXISTING);

            redirectAttributes.addFlashAttribute("success", "El banner de previsión exequial ha sido actualizado con éxito.");

        } catch (IOException e) {
            redirectAttributes.addFlashAttribute("error", "Error al escribir el archivo en el disco: " + e.getMessage());
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Ocurrió un error inesperado: " + e.getMessage());
        }

        return "redirect:/obituarios/banner";
    }
}