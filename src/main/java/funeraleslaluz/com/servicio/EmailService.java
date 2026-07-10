package funeraleslaluz.com.servicio;

import funeraleslaluz.com.modelo.EmailContacto;
import funeraleslaluz.com.repositorio.EmailRepository;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class EmailService {

    @Autowired
    private EmailRepository emailRepository;

    @Autowired
    private JavaMailSender mailSender; // Inyectamos el motor de envío de correos

    public void procesarNuevaSolicitud(String nombre, String telefono, String correo, String mensaje) {

        // 1. Persistencia: Creamos y guardamos el objeto en la Base de Datos
        EmailContacto solicitud = new EmailContacto();
        solicitud.setNombre(nombre);
        solicitud.setTelefono(telefono);
        solicitud.setEmail(correo);
        solicitud.setMensaje(mensaje);
        solicitud.setFechaRegistro(LocalDateTime.now());

        emailRepository.save(solicitud);
        System.out.println("Lead guardado en BD exitosamente: " + nombre);

        // 2. Notificación: Enviamos la información a tu correo personal
        enviarNotificacionEmail(nombre, telefono, correo, mensaje);
    }

    private void enviarNotificacionEmail(String nombre, String telefono, String correo, String mensaje) {
        try {
            MimeMessage mimeMessage = mailSender.createMimeMessage();
            // El parámetro 'true' indica que es un mensaje multipart (necesario para el logo)
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true, "utf-8");

            String htmlMsg =
                    "<div style='background-color: #f4f4f4; padding: 20px; font-family: \"Segoe UI\", Tahoma, Geneva, Verdana, sans-serif;'>" +
                            "<div style='max-width: 600px; margin: 0 auto; background-color: #ffffff; border-radius: 12px; overflow: hidden; shadow: 0 4px 10px rgba(0,0,0,0.1);'>" +

                            // Encabezado con Logo y letras blancas
                            "<div style='background-color: #3d2b1f; padding: 30px; text-align: center;'>" +
                            "<img src='cid:logoLaLuz' style='max-width: 180px; margin-bottom: 15px;' alt='Funerales La Luz'>" +
                            // Mantenemos la línea dorada para el contraste de marca
                            "<div style='height: 2px; background: linear-gradient(to right, transparent, #c5a059, transparent); width: 80%; margin: 0 auto;'></div>" +
                            // Color cambiado a #ffffff (blanco)
                            "<p style='color: #ffffff; margin-top: 15px; font-size: 14px; letter-spacing: 2px; text-transform: uppercase;'>Solicitud de Acompañamiento</p>" +
                            "</div>" +

                            // Cuerpo del mensaje
                            "<div style='padding: 40px;'>" +
                            "<h2 style='color: #3d2b1f; font-size: 20px; margin-bottom: 20px;'>Nuevo Lead Registrado</h2>" +
                            "<p style='color: #666; line-height: 1.6;'>Se ha recibido una nueva consulta a través del portal web. Aquí están los detalles para el seguimiento:</p>" +

                            "<div style='background-color: #fafafa; border-radius: 8px; padding: 20px; margin: 25px 0; border: 1px solid #eee;'>" +
                            "<p style='margin: 10px 0;'><strong>👤 Nombre:</strong> <span style='color: #333;'>" + nombre + "</span></p>" +
                            "<p style='margin: 10px 0;'><strong>📞 Teléfono:</strong> <a href='tel:" + telefono + "' style='color: #c5a059; text-decoration: none; font-weight: bold;'>" + telefono + "</a></p>" +
                            "<p style='margin: 10px 0;'><strong>📧 Email:</strong> <a href='mailto:" + correo + "' style='color: #c5a059; text-decoration: none;'>" + correo + "</a></p>" +
                            "</div>" +

                            // Cuadro del mensaje estilizado
                            "<div style='border-left: 4px solid #c5a059; padding-left: 20px; margin-top: 30px;'>" +
                            "<p style='color: #3d2b1f; font-weight: bold; margin-bottom: 10px;'>Consulta del cliente:</p>" +
                            "<p style='color: #555; font-style: italic; line-height: 1.6;'>" + mensaje + "</p>" +
                            "</div>" +
                            "</div>" +

                            // Footer
                            "<div style='background-color: #3d2b1f; padding: 20px; text-align: center;'>" +
                            "<p style='color: #888; font-size: 12px; margin: 0;'>© 2026 Funerales La Luz. Gestión de Infraestructura Telecommunications.</p>" +
                            "</div>" +

                            "</div>" +
                            "</div>";

            helper.setText(htmlMsg, true);
            helper.setTo("laluzasistenciaintegral@gmail.com");
            helper.setSubject("⚠️ LEAD WEB: " + nombre);

            // Aquí vinculamos la imagen local al 'cid:logoLaLuz' del HTML
            ClassPathResource logo = new ClassPathResource("static/img/logoluzblanco.png");
            helper.addInline("logoLaLuz", logo);

            mailSender.send(mimeMessage);
            System.out.println("Notificación premium enviada con éxito.");

        } catch (Exception e) {
            System.err.println("Error al enviar el correo premium: " + e.getMessage());
            e.printStackTrace();
        }
    }
}