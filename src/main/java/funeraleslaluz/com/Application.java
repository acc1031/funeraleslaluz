package funeraleslaluz.com;

import funeraleslaluz.com.modelo.Usuario;
import funeraleslaluz.com.repositorio.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

	@Bean
	CommandLineRunner initDatabase(UserRepository userRepository, PasswordEncoder passwordEncoder) {
		return args -> {
			// Verificamos si la tabla de usuarios en PostgreSQL está vacía
			if (userRepository.count() == 0) {
				Usuario admin = new Usuario();

				// Mapeo exacto con los nuevos atributos de tu entidad Usuario
				admin.setNombre("admin");
					admin.setContrasena(passwordEncoder.encode("L4.luZ_20!25*")); // Hasheo BCrypt automático
				admin.setRol("ROLE_ADMIN");
				admin.setNombre("Administrador");
				admin.setApellido("Inicial");
				admin.setCorreo("admin@funeraleslaluz.com");

				userRepository.save(admin);
				System.out.println(">> [La Luz] Base de datos vacía detectada. Se creó el usuario por defecto: admin / admin123");
			}

			System.out.println(">> [La Luz] Verificación de credenciales por defecto completada con éxito.");
		};
	}
}