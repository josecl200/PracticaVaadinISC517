package edu.pucmm.josecl200.vadimblyat.visual;

import com.sendgrid.*;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.spring.annotation.UIScope;

import java.io.IOException;

@SpringComponent
@UIScope
public class PantallaEmail extends VerticalLayout {
    TextField from = new TextField("Para:");
    TextField subject = new TextField("Asunto:");
    TextArea body = new TextArea("Cuerpo:");

    public PantallaEmail() {
        FormLayout formLayout = new FormLayout();

        H3 header = new H3("Enviar Correo");

        Button sendBtn = new Button("Enviar");
        sendBtn.getElement().setAttribute("theme", "primary");

        Button cancelBtn = new Button("Cancelar");
        cancelBtn.getElement().setAttribute("theme", "error");

        HorizontalLayout botones = new HorizontalLayout(sendBtn, cancelBtn);

        botones.setSpacing(true);

        formLayout.add(from, subject, body);
        setAlignItems(Alignment.CENTER);

        add(header, formLayout, botones);

        sendBtn.addClickListener((evento) -> {
            Email desdeEmail = new Email("20160138@ce.pucmm.edu.do");
            String asuntoEmail = subject.getValue();
            Email paraEmail = new Email(from.getValue());
            Content cuerpoEmail = new Content("text/plain", body.getValue());
            Mail email = new Mail(desdeEmail, asuntoEmail, paraEmail, cuerpoEmail);

            //Why is this null, tha heck
            System.out.println("REEEEEEEEEE"+System.getenv("SENDGRID_API_KEY"));
            String apiKey = "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";
            SendGrid sg = new SendGrid(apiKey);
            Request request = new Request();

            try {
                request.setMethod(Method.POST);
                request.setEndpoint("mail/send");
                request.setBody(email.build());
                Response response = sg.api(request);

                System.out.println(response.getStatusCode());
                System.out.println(response.getBody());
                System.out.println(response.getHeaders());

                from.setValue("");
                subject.setValue("");
                body.setValue("");
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });

        cancelBtn.addClickListener((evento) -> {
            from.setValue("");
            subject.setValue("");
            body.setValue("");
        });
    }
}
