package net.sharplab.springframework.security.webauthn.sample.infrastructure.util.jpa.converter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.webauthn4j.converter.jackson.ObjectMapperUtil;
import net.sharplab.springframework.security.webauthn.sample.domain.vo.AbstractCredentialPublicKeyVO;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.io.IOException;
import java.io.UncheckedIOException;

@Converter
public class CredentialPublicKeyVOConverter implements AttributeConverter<AbstractCredentialPublicKeyVO, String> {

    private ObjectMapper jsonMapper = ObjectMapperUtil.createWebAuthnClassesAwareJSONMapper();

    @Override
    public String convertToDatabaseColumn(AbstractCredentialPublicKeyVO attribute) {
        try {
            return jsonMapper.writeValueAsString(attribute);
        } catch (JsonProcessingException e) {
            throw new IllegalArgumentException(e);
        }
    }

    @Override
    public AbstractCredentialPublicKeyVO convertToEntityAttribute(String dbData) {
        try {
            return jsonMapper.readValue(dbData, AbstractCredentialPublicKeyVO.class);
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }
}
