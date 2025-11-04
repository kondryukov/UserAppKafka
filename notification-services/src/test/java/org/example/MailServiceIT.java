package org.example;

import com.icegreen.greenmail.util.GreenMail;
import com.icegreen.greenmail.util.ServerSetup;
import jakarta.mail.internet.MimeMessage;
import org.example.dto.CreateNotificationRequest;
import org.example.dto.NotificationResponse;
import org.example.service.NotificationService;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@TestPropertySource(properties = {
        "spring.mail.host=localhost",
        "spring.mail.port=3025",
        "spring.mail.username=",
        "spring.mail.password=",
        "spring.mail.properties.mail.smtp.auth=false",
        "spring.mail.properties.mail.smtp.starttls.enable=false"
})
class MailServiceIT {

    static GreenMail greenMail;

    @BeforeEach
    void startSmtp() {
        greenMail = new GreenMail(new ServerSetup(3025, null, "smtp"));
        greenMail.start();
    }

    @AfterEach
    void stopSmtp() {
        greenMail.stop();
    }

    @Autowired
    NotificationService notificationService;

    @Test
    void sendSimpleEmail() throws Exception {
        var createNotificationRequest = new CreateNotificationRequest("name@mail.ru", "create");
        NotificationResponse notificationResponse = notificationService.sendEmail(createNotificationRequest);

        greenMail.waitForIncomingEmail(1);
        MimeMessage[] receivedMessages = greenMail.getReceivedMessages();
        assertThat(receivedMessages).hasSize(1);
        assertThat(receivedMessages[0].getAllRecipients()[0].toString()).contains("name@mail.ru");
        assertThat(receivedMessages[0].getSubject()).isEqualTo("create");
        assertThat(notificationResponse.email()).isEqualTo("name@mail.ru");
        assertThat(notificationResponse.operation()).isEqualTo("create");
    }

    @Test
    void sendEmailFromKafkaDto() throws Exception {
        var event = new org.example.events.UserEvent();
        event.setEmail("name@mail.ru");
        event.setOperation("deleted");

        notificationService.sendEmail(event);

        greenMail.waitForIncomingEmail(1);
        MimeMessage[] receivedMessages = greenMail.getReceivedMessages();
        assertThat(receivedMessages).hasSize(1);
        assertThat(receivedMessages[0].getAllRecipients()[0].toString()).contains("name@mail.ru");
        assertThat(receivedMessages[0].getSubject()).isEqualTo("deleted");
    }
}