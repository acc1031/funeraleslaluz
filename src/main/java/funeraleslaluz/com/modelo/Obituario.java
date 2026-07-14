package funeraleslaluz.com.modelo;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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
public class Obituario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombreCompleto;
    private LocalDateTime fechaFallecimiento; // Se mantiene para el control de fechas internas
    private LocalDateTime fechaExequias;
    private String salaVelacion;

    // Campos de destino (Todos disponibles)
    private String cementerio; // Conservado para compatibilidad y registros previos
    private String iglesia;     // Nuevo: Campo opcional para Parque/Cementerio
    private String destino;    // Nuevo: Campo opcional para el Destino Final (ej: Cremación)

    private String mensajeHomenaje;

    // AGREGA ESTO:
    @jakarta.persistence.OneToMany(mappedBy = "obituario", cascade = jakarta.persistence.CascadeType.ALL, orphanRemoval = true)
    private java.util.List<Condolencia> condolencias = new java.util.ArrayList<>();


    public String getNombreCompleto() {
        return this.nombreCompleto;
    }
    public void setNombreCompleto(String nombreCompleto) {
        this.nombreCompleto = nombreCompleto;
    }
}