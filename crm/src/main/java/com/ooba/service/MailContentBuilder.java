package com.ooba.service;

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

    public String build(String message, String subject) {
        Context context = new Context();
        context.setVariable("subject", subject);
        context.setVariable("message", message);
        return templateEngine.process("mailTemplate", context);
    }

}
