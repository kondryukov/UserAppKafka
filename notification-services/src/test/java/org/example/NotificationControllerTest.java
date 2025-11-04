package org.example;

import com.icegreen.greenmail.util.GreenMail;
import com.icegreen.greenmail.util.ServerSetup;
import jakarta.mail.internet.MimeMessage;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.util.TestPropertyValues;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.http.*;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.test.context.TestPropertySource;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource(properties = {
        "spring.mail.host=localhost",
        "spring.mail.port=3025",
        "spring.mail.username=",
        "spring.mail.password=",
        "spring.mail.properties.mail.smtp.auth=false",
        "spring.mail.properties.mail.smtp.starttls.enable=false"
})
//@ContextConfiguration(initializers = NotificationControllerTest.Initializer.class)
class NotificationControllerTest {

    static GreenMail greenMail;

    @BeforeAll
    static void startSmtp() {
        greenMail = new GreenMail(new ServerSetup(3026, null, "smtp"));
        greenMail.start();
    }

    @AfterAll
    static void stopSmtp() {
        greenMail.stop();
    }

//    static class Initializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {
//        @Override
//        public void initialize(ConfigurableApplicationContext configurableApplicationContext) {
//            TestPropertyValues.of(
//                    "spring.mail.host=localhost",
//                    "spring.mail.port=3026",
//                    "spring.mail.username=",
//                    "spring.mail.password=",
//                    "spring.mail.properties.mail.smtp.auth=false",
//                    "spring.mail.properties.mail.smtp.starttls.enable=false"
//            ).applyTo(configurableApplicationContext.getEnvironment());
//        }
//    }

    @Autowired
    TestRestTemplate restTemplate;

    @Test
    void postCreate_sendsEmail() throws Exception {
        Map<String, Object> body = Map.of(
                "email", "name@mail.ru",
                "operation", "create"
        );

        ResponseEntity<String> response = restTemplate.postForEntity("/notification/create", body, String.class);

        assertThat(response.getStatusCode().is2xxSuccessful()).isTrue();

        greenMail.waitForIncomingEmail(1);
        MimeMessage[] receivedMessages = greenMail.getReceivedMessages();
        assertThat(receivedMessages).hasSize(1);
        assertThat(receivedMessages[0].getAllRecipients()[0].toString()).contains("name@mail.ru");
        assertThat(receivedMessages[0].getSubject()).isEqualTo("create");
    }
}