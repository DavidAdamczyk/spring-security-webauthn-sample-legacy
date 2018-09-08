package net.sharplab.springframework.security.webauthn.sample.app.formatter;

import com.webauthn4j.attestation.AttestationObject;
import net.sharplab.springframework.security.webauthn.converter.Base64StringToAttestationObjectConverter;
import net.sharplab.springframework.security.webauthn.sample.app.web.AttestationObjectForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.Formatter;

import java.text.ParseException;
import java.util.Locale;

/**
 * Converter which converts from {@link AttestationObjectForm} to {@link String}
 */
public class AttestationObjectFormFormatter implements Formatter<AttestationObjectForm> {

    @Autowired
    private Base64StringToAttestationObjectConverter base64StringToAttestationObjectConverter;

    public AttestationObjectFormFormatter(Base64StringToAttestationObjectConverter base64StringToAttestationObjectConverter) {
        this.base64StringToAttestationObjectConverter = base64StringToAttestationObjectConverter;
    }

    @Override
    public AttestationObjectForm parse(String text, Locale locale) throws ParseException {
        AttestationObject attestationObject = base64StringToAttestationObjectConverter.convert(text);
        AttestationObjectForm attestationObjectForm = new AttestationObjectForm();
        attestationObjectForm.setAttestationObject(attestationObject);
        attestationObjectForm.setAttestationObjectBase64(text);
        return attestationObjectForm;
    }

    @Override
    public String print(AttestationObjectForm object, Locale locale) {
        return object.getAttestationObjectBase64();
    }
}
