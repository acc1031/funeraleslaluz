package funeraleslaluz.com.repositorio; // Ajusta según tu estructura

import funeraleslaluz.com.modelo.Condolencia;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CondolenciaRepository extends JpaRepository<Condolencia, Long> {
    // Hereda automáticamente save(), findById(), etc.
}