package com.repostats.ghbackend.oauth.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Map;

@Data
@AllArgsConstructor
public abstract class OAuth2UserInfo {
    protected Map<String, Object> attributes;
    public abstract String getId();
    public abstract String getUsername();
    public abstract String getEmail();
}
