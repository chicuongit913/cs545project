package cs545_project.online_market.service;

import cs545_project.online_market.controller.response.OrderResponse;
import cs545_project.online_market.domain.Order;
import cs545_project.online_market.helper.Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;
import sun.security.ssl.KerberosClientKeyExchange;

@Service
public class EmailServiceImpl implements EmailService{

    @Autowired
    private JavaMailSender emailSender;

    @Autowired
    Util util;

    public void sendConfirmPurchaseMessage(OrderResponse orderResponse) {
        ClassLoaderTemplateResolver templateResolver = new ClassLoaderTemplateResolver();
        templateResolver.setSuffix(".html");
        templateResolver.setTemplateMode(TemplateMode.HTML);

        TemplateEngine templateEngine = new TemplateEngine();
        templateEngine.setTemplateResolver(templateResolver);

        Context context = new Context();
        context.setVariable("orderResponse", orderResponse);

        String cardNumber = orderResponse.getCardNumber();
        String lastFourDigits = cardNumber.substring(cardNumber.length() - 4);
        context.setVariable("lastFourDigitsCard", lastFourDigits);

        String body =  templateEngine.process("/templates/views/buyer/confirmPurchaseOrder", context);
        String subject = "Purchase confirm for Order #" + orderResponse.hashCode();

        String to = util.getCurrentUser().getEmail();

        this.sendMessage(to, subject,  body);
    }

    public void sendMessage(String to, String subject, String body) {


        MimeMessagePreparator messagePreparator = mimeMessage -> {
            MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage);
            messageHelper.setFrom("sample@dolszewski.com");
            messageHelper.setTo(to);
            messageHelper.setSubject(subject);
            messageHelper.setText(body, true);
        };
        try {
            emailSender.send(messagePreparator);
        } catch (MailException e) {
            // runtime exception; compiler will not force you to handle it
        }
    }
}
