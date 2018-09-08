package net.sharplab.springframework.security.webauthn.sample.app.config;

import net.sharplab.springframework.security.webauthn.converter.Base64StringToCollectedClientDataConverter;
import net.sharplab.springframework.security.webauthn.converter.Base64StringToAttestationObjectConverter;
import net.sharplab.springframework.security.webauthn.sample.app.formatter.AttestationObjectFormFormatter;
import net.sharplab.springframework.security.webauthn.sample.app.formatter.CollectedClientDataFormFormatter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Spring Conversion Service Configuration
 */
@Configuration
public class ConverterConfig {

    @Bean
    public Base64StringToCollectedClientDataConverter base64StringToCollectedClientDataConverter(){
        return new Base64StringToCollectedClientDataConverter();
    }

    @Bean
    public Base64StringToAttestationObjectConverter base64StringToWebAuthnAttestationObjectConverter(){
        return new Base64StringToAttestationObjectConverter();
    }

    @Bean
    public CollectedClientDataFormFormatter collectedClientDataFromToBase64StringConverter(
            Base64StringToCollectedClientDataConverter base64StringToCollectedClientDataConverter){
        return new CollectedClientDataFormFormatter(base64StringToCollectedClientDataConverter);
    }

    @Bean
    public AttestationObjectFormFormatter attestationObjectFormFormatter(
            Base64StringToAttestationObjectConverter base64StringToAttestationObjectConverter) {
        return new AttestationObjectFormFormatter(base64StringToAttestationObjectConverter);
    }

}
