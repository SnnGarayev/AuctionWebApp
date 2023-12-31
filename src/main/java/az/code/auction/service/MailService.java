package az.code.auction.service;

import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MailService {
    private final JavaMailSender mailSender;

    public String sendMail(
                           String toEmail,
                           String subject,
                           String body){
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("jamaladdinkk@code.edu.az");
        message.setTo(toEmail);
        message.setText(body);
        message.setSubject(subject);
        mailSender.send(message);
        return "Success";
    }
}
