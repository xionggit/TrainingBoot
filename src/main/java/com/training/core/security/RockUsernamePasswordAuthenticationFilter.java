/**
 *
 */
package com.training.core.security;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.training.core.util.ValidationCodeUtil.ValidationCode;

/**
 * @author haowei
 */
public class RockUsernamePasswordAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    @Value("${spring.dev_model}")
    private String devModel;
    
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
            throws AuthenticationException {
        String username = obtainUsername(request);
        String password = obtainPassword(request);
        String vcode = request.getParameter("vcode");
        UsernamePasswordAuthenticationToken authRequest = new RockUsernamePasswordAuthenticationToken(username, password, vcode, request);
        
        if (!"true".equalsIgnoreCase(devModel)) {
            
            RockUsernamePasswordAuthenticationToken rockAuthentication =
                    (RockUsernamePasswordAuthenticationToken) authRequest;

            ValidationCode sessionVcode = (ValidationCode) rockAuthentication
                    .getHttpServletRequest().getSession()
                    .getAttribute("_validationCode");

            if (sessionVcode == null
                    || !sessionVcode.validate(rockAuthentication.getVcode())) {
                throw new InternalAuthenticationServiceException(
                        messages.getMessage("WrongValidationCode",
                                "验证码输入有误或者验证码过期!"));
            }
        }
        
        setDetails(request, authRequest);
        return this.getAuthenticationManager().authenticate(authRequest);
    }
}
