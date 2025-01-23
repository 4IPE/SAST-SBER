package ru.SberTex.SastManager.service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmailService {

    private final JavaMailSender mailSender;

    public void sendEmail(String to, String url) throws MessagingException {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);
        String emailContent = "<!DOCTYPE html>\n" +
                "<html lang=\"ru\">\n" +
                "<head>\n" +
                "  <meta charset=\"UTF-8\">\n" +
                "  <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\n" +
                "  <title>Восстановление доступа</title>\n" +
                "</head>\n" +
                "<body style=\"font-family: 'Space Grotesk', Arial, sans-serif; background-color: #0a0a0f; color: #ffffff; margin: 0; padding: 0; display: flex; justify-content: center; align-items: center; min-height: 100vh;\">\n" +
                "  <div style=\"max-width: 600px; width: 90%; background-color: #151520; padding: 40px; border-radius: 12px; box-shadow: 0 4px 20px rgba(0, 229, 255, 0.1);\">\n" +
                "    <div style=\"text-align: center; margin-bottom: 30px;\">\n" +
                "      <h1 style=\"font-size: 32px; background: linear-gradient(to right, #21a038, #4c38dd); -webkit-background-clip: text; background-clip: text; color: transparent; margin: 0 0 10px;\">SAST</h1>\n" +
                "      <p style=\"font-size: 18px; color: #e0e0e0; margin: 0;\">Восстановление доступа к платформе</p>\n" +
                "    </div>\n" +
                "    <div style=\"text-align: center; margin: 40px 0;\">\n" +
                "      <a href=\"" + url + "\" style=\"display: inline-block; background: linear-gradient(to right, #21a038, #4c38dd); color: #ffffff; text-decoration: none; padding: 14px 28px; font-size: 18px; font-weight: bold; border-radius: 50px; transition: all 0.3s ease; box-shadow: 0 4px 15px rgba(33, 160, 56, 0.3); animation: pulse 2s infinite;\">Восстановить доступ</a>\n" +
                "      <p style=\"font-size: 16px; color: #a0a0a0; margin-top: 15px;\">Данная ссылка активна 10 минут</p>\n" +
                "    </div>\n" +
                "    <div style=\"text-align: center; font-size: 14px; color: #a0a0a0; margin-top: 30px; padding-top: 20px; border-top: 1px solid #ffffff1a;\">\n" +
                "      <p>Если вы не запрашивали это письмо, пожалуйста, проигнорируйте его.</p>\n" +
                "    </div>\n" +
                "  </div>\n" +
                "</body>\n" +
                "</html>";
        helper.setFrom("email");
        helper.setTo(to);
        helper.setSubject("Востановление пароля для сервиса SAST");
        helper.setText(emailContent, true);
        mailSender.send(message);
    }
}
