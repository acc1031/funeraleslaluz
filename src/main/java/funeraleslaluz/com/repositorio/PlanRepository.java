package funeraleslaluz.com.repositorio;

import funeraleslaluz.com.modelo.PlanExequial;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PlanRepository extends JpaRepository<PlanExequial, Long> { }

