package co.com.grupoasd.fae.util;

import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class EnvioCorreo implements Runnable {

    private final String destinatario;
    private String asunto;
    private String cuerpo;
    private final String user;
    private final String pass;

    private EnvioCorreo(String destinatario, String asunto, String cuerpo, 
            String user, String pass) {
        this.destinatario = destinatario;
        this.asunto = asunto;
        this.cuerpo = cuerpo;
        this.user = user;
        this.pass = pass;
    }

//	//Main para realizar pruebas
//	public static void main(String[] args) {
//	    try {
//                    EnvioCorreo envioCorreo = new EnvioCorreo("josem.moreno@grupoasd.com.co", "Correo de prueba enviado desde Java", "Esta es una prueba de correo...");
//                    envioCorreo.run();
//	    	
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//	}
    /**
     * Envia un correo al destinatario indicado
     *
     * @param destinatario destinatario
     * @param asunto asunto
     * @param cuerpo mensaje a enviar
     * @param user
     * @param pass
     */
    public static void enviar(String destinatario, String asunto, String cuerpo, 
            String user, String pass) {
        EnvioCorreo envioCorreo = new EnvioCorreo(destinatario, asunto, cuerpo, user, pass);
        envioCorreo.run();
    }

    @Override
    public void run() {

        try {
            // Esto es lo que va delante de @gmail.com en tu cuenta de correo. Es el remitente tambien.
            
            Properties props = System.getProperties();
            props.put("mail.smtp.host", "smtp.gmail.com");  //El servidor SMTP de Google
            props.put("mail.smtp.user", user);
            props.put("mail.smtp.clave", pass);    //La clave de la cuenta
            props.put("mail.smtp.auth", "true");    //Usar autenticacion mediante usuario y clave
            props.put("mail.smtp.starttls.enable", "true"); //Para conectar de manera segura al servidor SMTP
            props.put("mail.smtp.port", "587"); //El puerto SMTP seguro de Google

            Session session = Session.getDefaultInstance(props);
            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress(user));
            message.addRecipients(Message.RecipientType.TO, destinatario);   //Se podrian añadir varios de la misma manera
            message.setSubject(asunto);
            message.setText(cuerpo);
            Transport transport = session.getTransport("smtp");
            transport.connect("smtp.gmail.com", user, pass);
            transport.sendMessage(message, message.getAllRecipients());
            transport.close();
        } catch (MessagingException me) {
            System.err.println("NO SE COMPLETO EL ENVIO DE CORREO");
            System.err.println("Se recomienda verificar: Accesos del correo (usuario/contrasena), conectividad con gmail (mail.google.com)"
                    + " y control de accesos/seguridad desde las configuraciones de seguridad google (tener en cuenta la ubicacion del servidor)");
            me.printStackTrace();   //Si se produce un error
        }
    }

}
