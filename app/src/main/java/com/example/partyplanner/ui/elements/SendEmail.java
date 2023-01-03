package com.example.partyplanner.ui.elements;

import com.sendgrid.*;
import com.sendgrid.helpers.mail.Mail;
import com.sendgrid.helpers.mail.objects.Content;
import com.sendgrid.helpers.mail.objects.Email;

import java.io.IOException;

public class SendEmail {
    public void sendAnEmail() throws IOException {
        Email from = new Email("s215708@dtu.dk");
        String subject = "Sending with SendGrid is Fun";
        Email to = new Email("s215708@dtu.dk");
        Content content = new Content("text/plain", "and easy to do anywhere, even with Java");
        Mail mail = new Mail(from, subject, to, content);

        SendGrid sg = new SendGrid("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");
        Request request = new Request();

        try {
            request.setMethod(Method.POST);
            request.setEndpoint("mail/send");
            request.setBody(mail.build());
            Response response = sg.api(request);
            System.out.println(response.getStatusCode());
            System.out.println(response.getBody());
            System.out.println(response.getHeaders());
        } catch (IOException ex) {
            throw ex;
        }
    }
}