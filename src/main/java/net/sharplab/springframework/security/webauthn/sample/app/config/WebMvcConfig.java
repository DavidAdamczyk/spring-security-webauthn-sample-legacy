package net.sharplab.springframework.security.webauthn.sample.app.config;

import net.sharplab.springframework.security.webauthn.sample.app.formatter.AttestationObjectFormFormatter;
import net.sharplab.springframework.security.webauthn.sample.app.formatter.CollectedClientDataFormFormatter;
import net.sharplab.thymeleaf.dialect.WebAuthnDialect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.i18n.AcceptHeaderLocaleResolver;

import java.util.Locale;

/**
 * WebMVC Configuration
 */
@Import(ConverterConfig.class)
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    @Autowired
    private CollectedClientDataFormFormatter collectedClientDataFormFormatter;

    @Autowired
    private AttestationObjectFormFormatter attestationObjectFormFormatter;

    @Bean
    public LocaleResolver localeResolver() {
        AcceptHeaderLocaleResolver localeResolver = new AcceptHeaderLocaleResolver();
        localeResolver.setDefaultLocale(Locale.US);
        return localeResolver;
    }

    @Bean
    public WebAuthnDialect webAuthnDialect() {
        return new WebAuthnDialect();
    }

    @Override
    public void addFormatters(FormatterRegistry formatterRegistry) {
        formatterRegistry.addFormatter(attestationObjectFormFormatter);
        formatterRegistry.addFormatter(collectedClientDataFormFormatter);
    }
}
