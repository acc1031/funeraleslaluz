package funeraleslaluz.com.modelo;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "User")
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String nombre;
    @Column(name = "lastName")
    private String apellido;
    @Column(name = "email")
    private String correo;
    @Column(name = "password")
    private String contrasena;

    @Column(name = "rol") // <-- AGREGA ESTE CAMPO
    private String rol;

    @Column(name = "dateRegister")
    @Temporal(TemporalType.DATE)
    private Date fechaRegistro;

    @Column(name = "dateUpdate")
    @Temporal(TemporalType.DATE)
    private Date fechaActualizacion;






}
