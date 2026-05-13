package funeraleslaluz.com.repositorio;


import funeraleslaluz.com.modelo.EmailContacto;
import funeraleslaluz.com.modelo.EmailContacto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmailRepository extends JpaRepository<EmailContacto, Long> {
}

