package com.training.core.security;

import javax.servlet.http.HttpServletRequest;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

/**
 * @author haowei
 */
public class RockUsernamePasswordAuthenticationToken extends UsernamePasswordAuthenticationToken {

    private static final long serialVersionUID = 1615472466024090676L;

    public RockUsernamePasswordAuthenticationToken(String principal, String credentials, String vcode, HttpServletRequest request) {
        super(principal, credentials);
        this.vcode = vcode;
        this.request = request;
    }

    private String vcode;

    private HttpServletRequest request;

    public String getVcode() {
        return vcode;
    }

    public HttpServletRequest getHttpServletRequest() {
        return request;
    }
}
