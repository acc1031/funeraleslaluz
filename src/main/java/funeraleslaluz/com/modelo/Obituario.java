package funeraleslaluz.com.modelo;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Data
public class Obituario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombreCompleto;
    private LocalDateTime fechaFallecimiento;
    private LocalDateTime fechaExequias;
    private String salaVelacion;
    private String cementerio;
    private String mensajeHomenaje;
}