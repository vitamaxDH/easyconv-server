package com.easyconv.easyconvserver.web.oauth.provider;

import lombok.Data;

import java.util.Map;

@Data
public class GoogleUserInfo implements OAuth2UserInfo {

    private Map<String, Object> attributes; // getAttributes();

    public GoogleUserInfo(Map<String, Object> attributes) {
        this.attributes = attributes;
    }

    @Override
    public String getProviderId() {
        return (String) attributes.get("sub");
    }

    @Override
    public String getProvider() {
        return "Google";
    }

    @Override
    public String getEmail() {
        return (String) attributes.get("email");
    }

    @Override
    public String getName() {
        return (String) attributes.get("name");
    }
}
