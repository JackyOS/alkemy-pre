package com.alkemy.challenge.challenge.service.Impl;

import com.alkemy.challenge.challenge.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import com.sendgrid.Method;
import com.sendgrid.Request;
import com.sendgrid.Response;
import com.sendgrid.SendGrid;
import com.sendgrid.helpers.mail.Mail;
import com.sendgrid.helpers.mail.objects.Content;
import com.sendgrid.helpers.mail.objects.Email;
import org.springframework.beans.factory.annotation.Value;


import java.io.IOException;

@Service
public class EmailServiceImp implements EmailService {

    @Autowired
    private Environment env; //Aca va el api key
    //Como es api key es un dato que debe estar seguro, no tiene que estar en el codigo


    @Value("${email.sender}") //obtengo el valor de una property (application.property)
    private String emailSender;

    @Value("${email.enabled}") //creamos esta property para habilitar o deshabilitar el envio de mail
    private boolean enabled; //si es true, envia el mail y si es false, no lo envía.

    @Override
    public void sendWelcomeEmailTo(String to) {
       if (!enabled){  //si enabled no es true, no envía el mail => return; => sale del metodo
           return;
       }
        String apiKey = env.getProperty("EMAIL_API_KEY");
        //Obtenida desde Sender y lo asignamos a variable
        //La cargamos en "ChallengeApplication" => "edit configuration" => enviroment variable

        Email fromEmail = new Email(emailSender); //se guarda nuestro mail, obtenido de las property
        Email toEmail = new Email(to); //a quien se lo enviamos - el usuario nuevo
        Content content = new Content(//Cuerpo del msj, tipo texto plano y contenido
                "text/plain",
                "Bienvenido/a a Disney "
        );

        String subject = "Alkemy Disney"; //asunto del mensaje

        Mail mail = new Mail(fromEmail, subject, toEmail, content); //Creo un mail del tipo Send
        SendGrid sg = new SendGrid(apiKey);//Envio mi cuenta y los permisos
        Request request = new Request();
        //Tanto mail sendgrid y request son de la dependencia sengrid

        try {
            request.setMethod(Method.POST);
            request.setEndpoint("mail/send");
            request.setBody(mail.build());//Agarra el mail y lo construye con el build
            Response response = sg.api(request); //aca se ejecuta la respuesta

            System.out.println(response.getStatusCode());
            System.out.println(response.getBody());
            System.out.println(response.getHeaders());
        } catch (IOException e) {
            System.out.println("Error al enviar el mail");
        }

    }

}


