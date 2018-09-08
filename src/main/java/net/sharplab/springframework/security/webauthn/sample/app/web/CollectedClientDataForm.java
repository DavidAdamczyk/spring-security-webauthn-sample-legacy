package net.sharplab.springframework.security.webauthn.sample.app.web;

import com.webauthn4j.client.CollectedClientData;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

/**
 * Form for CollectedClientData
 */
public class CollectedClientDataForm {

    @NotNull
    @Valid
    private CollectedClientData collectedClientData;

    @NotNull
    private String clientDataBase64;

    public CollectedClientData getCollectedClientData() {
        return collectedClientData;
    }

    public void setCollectedClientData(CollectedClientData collectedClientData) {
        this.collectedClientData = collectedClientData;
    }

    public String getClientDataBase64() {
        return clientDataBase64;
    }

    public void setClientDataBase64(String clientDataBase64) {
        this.clientDataBase64 = clientDataBase64;
    }
}
