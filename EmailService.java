package com.example.demo;


import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;

    public void sendEmailWithImage(String to, String subject, String text, ArrayList<String> imagePaths) throws MessagingException {
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true); {
        	 helper.setTo(to);
             helper.setSubject(subject);
             helper.setFrom("lalydev24@gmail.com");

             // Costruisci l'HTML con i CID per le immagini
             StringBuilder htmlContent = new StringBuilder("<html><body>");
             htmlContent.append("<p>").append(text).append("</p>");
             
             // Aggiungi un tag <img> per ogni immagine
             for (int i = 0; i < imagePaths.size(); i++) {
                 htmlContent.append("<img src='cid:image").append(i).append("'/>");
             }
             
             htmlContent.append("</body></html>");
             helper.setText(htmlContent.toString(), true); // 'true' per indicare che il testo è in HTML

             // Aggiungi ogni immagine come allegato inline con un Content ID unico
             for (int i = 0; i < imagePaths.size(); i++) {
                 // Costruisci il percorso relativo per l'immagine
                 ClassPathResource image = new ClassPathResource("static/" + imagePaths.get(i));
                 // Aggiungi l'immagine come allegato inline con un Content ID unico
                 helper.addInline("image" + i, image);
             }

             mailSender.send(mimeMessage);
         }
     }
}