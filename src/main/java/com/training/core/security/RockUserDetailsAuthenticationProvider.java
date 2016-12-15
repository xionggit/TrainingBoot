package com.training.core.security;

import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.unitils.util.CollectionUtils;

import com.training.core.util.DateUtil;
import com.training.core.util.ValidationCodeUtil;

public class RockUserDetailsAuthenticationProvider extends DaoAuthenticationProvider {
    
    private Logger logger = Logger.getLogger(RockUserDetailsAuthenticationProvider.class);

	/*
     * (non-Javadoc)
	 *
	 * @see org.springframework.security.authentication.dao.
	 * AbstractUserDetailsAuthenticationProvider
	 * #additionalAuthenticationChecks(org
	 * .springframework.security.core.userdetails.UserDetails,
	 * org.springframework
	 * .security.authentication.UsernamePasswordAuthenticationToken)
	 */

    @Override
    protected void additionalAuthenticationChecks(UserDetails userDetails, UsernamePasswordAuthenticationToken authentication)
            throws AuthenticationException {

        super.additionalAuthenticationChecks(userDetails, authentication);
        
    }
    
//    @Override
//    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
//
//        System.out.println("------------------");
//        
//        try {
//            return super.authenticate(authentication);
//        } catch (BadCredentialsException e) {
////            String username = (authentication.getPrincipal() == null) ? "NONE_USERNAME" : authentication.getName();
////            logger.debug("-----1--------" + username);
////
////            // 锁定用户、密码检查等自定义设置
////            RuntimeException exception = e;
////            TuUser user = null;
////            @SuppressWarnings("unchecked")
////            List<TuPwdPolicy> policyList = TuPwdPolicy.objects.getAll();
////            try {
////                RockJdbcUserDetails userDetail = (RockJdbcUserDetails) getUserDetailsService().loadUserByUsername(username);
////                user = userDetail.getUser();
////                user.setPwdErrorCount(user.getPwdErrorCount() + 1);
////                if (!CollectionUtils.isEmpty(policyList)) {
////                    TuPwdPolicy policy = policyList.get(0);
////                    if (user.getLockedTime() != null
////                            && !DateUtil.between(user.getLockedTime(), policy.getLockTime(), TimeUnit.MINUTES)
////                            && user.getPwdErrorCount() > policy.getLockFailCount()) {
////                        user.setPwdErrorCount(1);
////                    }
////                    if (user.getPwdErrorCount() >= policy.getLockFailCount()) {
////                        user.setIsLocked(TuUser.LOCKED_FLAG_TRUE);
////                        user.setLockedTime(new Date());
////                        exception = new BadCredentialsException(messages.getMessage("Rock.locked", new Integer[]{policy.getLockTime()}));
////                    } else {
////                        exception = new BadCredentialsException(e.getMessage() + ", "
////                                + messages.getMessage("TimesLeft", new Object[]{policy.getLockFailCount() - user.getPwdErrorCount()}));
////                    }
////                }
////                user.update();
////            } catch (UsernameNotFoundException ex) {
////                logger.debug("---------" + ex.getMessage());
////                // 表单登录时，用户不存在时也验验证码，为了保持和用户存在时行为一致
////                if (authentication instanceof RockUsernamePasswordAuthenticationToken) {
////                    
////                    TuLoginError loginErr = (TuLoginError) TuLoginError.objects.getByProperty("username", username);
////                    if (loginErr != null) {
////                        loginErr.setPwdErrorCount(loginErr.getPwdErrorCount() + 1);
////                    } else {
////                        loginErr = new TuLoginError();
////                        loginErr.setPwdErrorCount(1);
////                        loginErr.setUsername(username);
////                    }
////
////                    logger.debug("------------" + loginErr.getUsername() + " " + loginErr.getPwdErrorCount());
////
////                    if (!CollectionUtils.isEmpty(policyList)) {
////                        TuPwdPolicy policy = policyList.get(0);
////                        logger.debug(String.valueOf(policy.getLockFailCount()));
////                        if (loginErr.getLockedTime() != null) {
////                            // 锁定期内
////                            if (DateUtil.between(loginErr.getLockedTime(), policy.getLockTime(), TimeUnit.MINUTES)) {
////                                throw new LockedException(messages.getMessage("AbstractUserDetailsAuthenticationProvider.locked"));
////                            }
////
////                            if (loginErr.getPwdErrorCount() > policy.getLockFailCount()) {
////                                loginErr.setPwdErrorCount(1);
////                            }
////                        }
////
////                        if (loginErr.getPwdErrorCount() >= policy.getLockFailCount()) {
////                            loginErr.setLockedTime(new Date());
////                            exception = new BadCredentialsException(messages.getMessage("Rock.locked", new Integer[]{policy.getLockTime()}));
////                        } else {
////                            exception = new BadCredentialsException(
////                                    messages.getMessage("AbstractUserDetailsAuthenticationProvider.badCredentials") + ", "
////                                            + messages.getMessage("TimesLeft", new Object[]{policy.getLockFailCount() - loginErr.getPwdErrorCount()}));
////                        }
////                    }
////
////                    loginErr.save();
////                }
////            }
////            logger.debug(exception.getMessage());
////            throw exception;
//        }
//        throw new RuntimeException("-runtimeexception.************");
//    }
}
