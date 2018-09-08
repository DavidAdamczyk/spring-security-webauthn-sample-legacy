package net.sharplab.springframework.security.webauthn.sample.util.modelmapper;

import com.webauthn4j.attestation.authenticator.AttestedCredentialData;
import com.webauthn4j.attestation.authenticator.CredentialPublicKey;
import com.webauthn4j.attestation.authenticator.EC2CredentialPublicKey;
import net.sharplab.springframework.security.webauthn.sample.domain.config.ModelMapperConfig;
import net.sharplab.springframework.security.webauthn.sample.domain.vo.AttestedCredentialDataVO;
import org.junit.Test;
import org.modelmapper.ModelMapper;

import static org.assertj.core.api.Assertions.assertThat;

public class AttestedCredentialDataToAttestedCredentialDataVOConverterTest {

    @Test
    public void test(){
        ModelMapper modelMapper = ModelMapperConfig.createModelMapper();

        //Given
        byte[] aaGuid = new byte[16];
        byte[] credentialId = new byte[32];
        CredentialPublicKey credentialPublicKey = new EC2CredentialPublicKey();
        AttestedCredentialData source = new AttestedCredentialData(aaGuid, credentialId, credentialPublicKey);

        AttestedCredentialDataVO destination = new AttestedCredentialDataVO();


        //When
        modelMapper.map(source, destination);

        //Then
        assertThat(destination.getAaGuid()).isNotNull();
        assertThat(destination.getCredentialId()).isNotNull();
        assertThat(destination.getCredentialPublicKey()).isNotNull();

    }
}
