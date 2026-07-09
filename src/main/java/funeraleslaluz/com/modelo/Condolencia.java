package funeraleslaluz.com.modelo;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Condolencia {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String remitente;

    @Column(columnDefinition = "TEXT")
    private String mensaje;

    private LocalDateTime fechaCreacion;

    // Relación directa con tu entidad Obituario
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "obituario_id", nullable = false)
    private Obituario obituario;

    // Asigna la fecha y hora exacta automáticamente al guardarse
    @PrePersist
    protected void onCreate() {
        this.fechaCreacion = LocalDateTime.now();
    }
}