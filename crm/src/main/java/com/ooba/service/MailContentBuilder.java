package com.ooba.service;

import com.ooba.model.NotificationMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

/**
 * Created by cleophas on 2018/10/26.
 */

@Service
public class MailContentBuilder {

    private TemplateEngine templateEngine;

    @Autowired
    public MailContentBuilder(TemplateEngine templateEngine) {
        this.templateEngine = templateEngine;
    }

    public String build(NotificationMessage notificationMessage) {
        Context context = new Context();
        context.setVariable("subject", notificationMessage.getSubject());
        context.setVariable("message", notificationMessage.getBody());
        context.setVariable("action", notificationMessage.getAction());
        context.setVariable("actionDescription", notificationMessage.getActionDescription());
        context.setVariable("leadId", notificationMessage.getLeadId());
        String html = templateEngine.process("mailTemplate", context);
        return html;
    }

}
