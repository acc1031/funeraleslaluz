package funeraleslaluz.com.repositorio;

import funeraleslaluz.com.modelo.Obituario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ObituarioRepository extends JpaRepository<Obituario, Long> {
    // Para mostrar los más recientes arriba
    List<Obituario> findAllByOrderByFechaFallecimientoDesc();
}