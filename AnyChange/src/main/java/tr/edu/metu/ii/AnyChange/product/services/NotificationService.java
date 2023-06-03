package tr.edu.metu.ii.AnyChange.product.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tr.edu.metu.ii.AnyChange.user.repositories.UserRepository;

@Service
@Transactional
public class NotificationService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private JavaMailSender javaMailSender;
    
    //@Scheduled(fixedRate = 3600000)
    public void sendNotifications() {
        userRepository.findAll().forEach(user -> {
            user.getNotificationProducts().forEach(product -> {
                final SimpleMailMessage mailMessage = new SimpleMailMessage();
                mailMessage.setTo(user.getEmail());
                mailMessage.setSubject("Product notification!");
                mailMessage.setFrom("<MAIL>");
                StringBuilder builder = new StringBuilder();
                builder.append("Latest ").append(product.getName()).append(" prices are: \n");
                product.getProductPrices().forEach((priceSource, priceInformation) -> {
                    builder.append(priceSource.getName()).append(" = ").append(priceInformation.getCurrentPrice().getPrice()).append("\n");
                });
                mailMessage.setText(builder.toString());
                sendEmail(mailMessage);
            });
        });
    }

    @Async
    public void sendEmail(SimpleMailMessage email) {
        javaMailSender.send(email);
    }
}
