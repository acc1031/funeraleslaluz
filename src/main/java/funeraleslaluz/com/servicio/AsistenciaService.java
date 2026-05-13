package funeraleslaluz.com.servicio;

import funeraleslaluz.com.modelo.Obituario;
import funeraleslaluz.com.modelo.PlanExequial;
import funeraleslaluz.com.repositorio.ObituarioRepository;
import funeraleslaluz.com.repositorio.PlanRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class AsistenciaService {

    @Autowired
    private PlanRepository planRepository;

    @Autowired
    private ObituarioRepository obituarioRepository;

    public List<PlanExequial> obtenerTodosLosPlanes() {
        return planRepository.findAll();
    }

    public List<Obituario> obtenerObituariosRecientes() {
        return obituarioRepository.findAllByOrderByFechaFallecimientoDesc();
    }

    public void guardarObituario(Obituario obituario) {
        obituarioRepository.save(obituario);
    }
}