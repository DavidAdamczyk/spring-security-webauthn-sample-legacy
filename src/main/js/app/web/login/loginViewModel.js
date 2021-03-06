require('static/css/login/login.css');
const base64url = require('base64url');

$ = require('jquery');
jQuery = $;

Config = require('lib/config.js');

// loginViewModel constructor
let LoginViewModel = function(){
    let _this = this;
    $(document).ready(function() {
        //対応するViewがDOMに存在する場合
        if($('#login-login-view').length){
            let searchParams = new URLSearchParams(location.search);
            if(searchParams.has("error")) {
                //nop
            }
            _this.setupEventListeners();
        }
    });

};

LoginViewModel.prototype.setupEventListeners = function () {
    let _this = this;

    $('#webauthn-login').click(function (event) {
        _this.tryLoginWithPublicKeyCredential();
        event.preventDefault();
    });
};

LoginViewModel.prototype.tryLoginWithPublicKeyCredential = function (){
    let _this = this;

    return _this.getPublicKeyCredential()
        .then(function (credential) { //succeeded to retrieve credential
            if (credential.type === "public-key") {
                return _this.loginWithPublicKeyCredential(credential);
            }
            else {
                console.error("Unexpected credential type is returned.");
            }
        })
        .catch(function (error) { // fall back to login form
            $("#gesture-request-modal").modal('hide');
            console.error(error);
        });
};

LoginViewModel.prototype.getPublicKeyCredential = function (){
    let _this = this;

    if(typeof navigator.credentials === "undefined"){
        return Promise.reject("Credential Management API is not supported.");
    }

    $("#gesture-request-modal").modal('show');

    let challenge = this.loadChallenge();

    let publicKeyCredentialRequestOptions = {
        challenge: challenge,
        //timeout: null,
        //rpId: null,
        //allowCredentials: null,
        userVerification: "required",
        extensions: {}
    };

    return navigator.credentials.get({
        mediation: "required",
        publicKey: publicKeyCredentialRequestOptions
    }).then(function(credential){
        if(typeof credential === "undefined"){
            return Promise.reject("No credential is chosen.");
        }
        else{
            return Promise.resolve(credential);
        }
    });

};

LoginViewModel.prototype.loginWithPublicKeyCredential = function (credential) {
    let _this = this;

    let credentialId = credential.id;
    let clientData = credential.response.clientDataJSON;
    let authenticatorData = credential.response.authenticatorData;
    let signature = credential.response.signature;
    // let clientExtensions = credential.getClientExtensionResults(); //Edge preview throws exception as of build 180603-1447
    let clientExtensions = {};

    _this.loginByFormSubmission(null, null, credentialId, clientData, authenticatorData, signature, clientExtensions);
};

LoginViewModel.prototype.loginByFormSubmission = function (username, password, credentialId, clientData, authenticatorData, signature, clientExtensions) {
    let loginForm = $("#login-form");
    loginForm.find("input[name='username']").val(username);
    loginForm.find("input[name='rawPassword']").val(password);
    loginForm.find("input[name='credentialId']").val(credentialId);
    loginForm.find("input[name='clientData']").val(base64url.encode(clientData));
    loginForm.find("input[name='authenticatorData']").val(base64url.encode(authenticatorData));
    loginForm.find("input[name='signature']").val(base64url.encode(signature));
    loginForm.find("input[name='clientExtensionsJSON']").val(JSON.stringify(clientExtensions));
    loginForm.submit();
};

LoginViewModel.prototype.loadChallenge = function () {
    let challengeBase64 = $("meta[name='_challenge']").attr("content");
    return base64url.toBuffer(challengeBase64);
};

module.exports = new LoginViewModel();