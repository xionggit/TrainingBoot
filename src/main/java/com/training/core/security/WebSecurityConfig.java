package com.training.core.security;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.core.annotation.Order;
import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.access.AccessDecisionVoter;
import org.springframework.security.access.vote.AuthenticatedVoter;
import org.springframework.security.access.vote.RoleVoter;
import org.springframework.security.authentication.AnonymousAuthenticationProvider;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.DefaultAuthenticationEventPublisher;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.event.LoggerListener;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.access.AccessDeniedHandlerImpl;
import org.springframework.security.web.access.expression.DefaultWebSecurityExpressionHandler;
import org.springframework.security.web.access.expression.WebExpressionVoter;
import org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import com.mysql.jdbc.Messages;
import com.training.sysmanager.service.AclResourcesService;
import com.training.sysmanager.service.impl.AclResourcesServiceImpl;

/**
 * Created by Athos on 2016-10-16.
 */
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

	@Resource
	private UserDetailsService userDetailsService;
	
	@Resource
	private MySecurityMetadataSource mySecurityMetadataSource;

	@Override
	protected void configure(HttpSecurity http) throws Exception {

		http.addFilterAfter(MyUsernamePasswordAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);
		// 开启默认登录页面
		http/*.authorizeRequests().anyRequest().authenticated().withObjectPostProcessor(new ObjectPostProcessor<FilterSecurityInterceptor>() {
			public <O extends FilterSecurityInterceptor> O postProcess(O fsi) {
				fsi.setSecurityMetadataSource(mySecurityMetadataSource);
				fsi.setAccessDecisionManager(accessDecisionManager());
				fsi.setAuthenticationManager(authenticationManagerBean());
				return fsi;
			}
		}).and()*/.exceptionHandling().authenticationEntryPoint(
		        /*默认登录页的url*/
		        new LoginUrlAuthenticationEntryPoint("/login.html")).
		and().logout().logoutSuccessUrl("/index.html").permitAll();
		// 自定义accessDecisionManager访问控制器,并开启表达式语言
		http.exceptionHandling().accessDeniedHandler(accessDeniedHandler()).and().authorizeRequests().
		anyRequest().authenticated().expressionHandler(webSecurityExpressionHandler());

		// 自定义登录页面
		http.csrf().disable();

		// 自定义注销
		http.logout().logoutUrl("/logout").logoutSuccessUrl("/login.html")
		.invalidateHttpSession(true);

		// session管理
		http.sessionManagement().maximumSessions(1);

		// RemeberMe
		// http.rememberMe().key("webmvc#FD637E6D9C0F1A5A67082AF56CE32485");

	}

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
	    
	    
		// 自定义UserDetailsService
//	    auth.authenticationProvider(authenticationProvider());
//		auth.userDetailsService(userDetailsService);
		
	}
	
	@Bean
	UsernamePasswordAuthenticationFilter MyUsernamePasswordAuthenticationFilter() {
	    RockUsernamePasswordAuthenticationFilter myUsernamePasswordAuthenticationFilter = new RockUsernamePasswordAuthenticationFilter();
		myUsernamePasswordAuthenticationFilter.setPostOnly(true);
		myUsernamePasswordAuthenticationFilter.setAuthenticationManager(authenticationManagerBean());
		myUsernamePasswordAuthenticationFilter.setUsernameParameter("username");
		myUsernamePasswordAuthenticationFilter.setPasswordParameter("password");
		myUsernamePasswordAuthenticationFilter.setMessageSource(messageSource());
		myUsernamePasswordAuthenticationFilter.setRequiresAuthenticationRequestMatcher(new AntPathRequestMatcher("/login", "POST"));
		myUsernamePasswordAuthenticationFilter.setAuthenticationSuccessHandler(simpleUrlAuthenticationSuccessHandler());
		myUsernamePasswordAuthenticationFilter.setAuthenticationFailureHandler(simpleUrlAuthenticationFailureHandler());
		return myUsernamePasswordAuthenticationFilter;
	}
	
	/**
	 * <!-- 消息本地化 -->
    <beans:bean id="messageSource"
                class="org.springframework.context.support.ReloadableResourceBundleMessageSource">
        <beans:property name="basenames">
            <beans:array>
                <!-- 自定义消息在前 -->
                <beans:value>classpath:locale/securityMessages</beans:value>
                <beans:value>
                    classpath:org/springframework/security/messages
                </beans:value>
            </beans:array>
        </beans:property>
    </beans:bean>
	 * @return
	 */
	ReloadableResourceBundleMessageSource messageSource(){
	    ReloadableResourceBundleMessageSource messages = new ReloadableResourceBundleMessageSource();
	    messages.addBasenames("classpath:locale/securityMessages");
	    messages.addBasenames("classpath:org/springframework/security/messages");
	    
	    return messages;
	}

	@Bean
	AccessDeniedHandler accessDeniedHandler() {
		AccessDeniedHandlerImpl accessDeniedHandler = new AccessDeniedHandlerImpl();
		accessDeniedHandler.setErrorPage("/securityException/accessDenied");
		return accessDeniedHandler;
	}

	@Bean
	public LoggerListener loggerListener() {
		System.out.println("-*-*-*-*-*-*-org.springframework.security.authentication.event.LoggerListener");
		return new LoggerListener();
	}

	@Bean
	public org.springframework.security.access.event.LoggerListener eventLoggerListener() {
		System.out.println("-*-*-*-*-*-*-org.springframework.security.access.event.LoggerListener");
		return new org.springframework.security.access.event.LoggerListener();
	}

	/*
	 * 
	 * 这里可以增加自定义的投票器
	 */
	@Bean(name = "accessDecisionManager")
	public AccessDecisionManager accessDecisionManager() {
		List<AccessDecisionVoter<? extends Object>> decisionVoters = new ArrayList();
		decisionVoters.add(new RoleVoter());
		decisionVoters.add(new AuthenticatedVoter());
		decisionVoters.add(webExpressionVoter());// 启用表达式投票器
		MyAccessDecisionManager accessDecisionManager = new MyAccessDecisionManager(decisionVoters);
		return accessDecisionManager;
	}

	public AuthenticationManager authenticationManagerBean() {
	    /*
	     * 参考链接 http://dead-knight.iteye.com/blog/1512779
	     * */
		List<AuthenticationProvider> providers = new ArrayList<AuthenticationProvider>();
		RockUserDetailsAuthenticationProvider provider = new RockUserDetailsAuthenticationProvider();
		//自定义userDetailsService
		provider.setUserDetailsService(userDetailsService);
		//自定义provider
		providers.add(provider);
		
		//猜测为匿名用户用的
		providers.add(new AnonymousAuthenticationProvider("work"));
		
		ProviderManager authenticationManager = new ProviderManager(providers );
		
		authenticationManager.setAuthenticationEventPublisher(new DefaultAuthenticationEventPublisher());
		
		return authenticationManager;
	}

	@Bean(name = "successHandler")
	public SimpleUrlAuthenticationSuccessHandler simpleUrlAuthenticationSuccessHandler(){
	    SimpleUrlAuthenticationSuccessHandler simpleUrlAuthenticationSuccessHandler = new SimpleUrlAuthenticationSuccessHandler();
	    //default_after_login 默认登录后的跳转页面
	    simpleUrlAuthenticationSuccessHandler.setDefaultTargetUrl("/food/hello");
	    //redirect_param 登陆后页面。 如果setAlwaysUseDefaultTargetUrl=true，则该参数设置也无效
	    simpleUrlAuthenticationSuccessHandler.setTargetUrlParameter("next");
	    // setAlwaysUseDefaultTargetUrl 默认为false
//	    simpleUrlAuthenticationSuccessHandler.setAlwaysUseDefaultTargetUrl(false);
	    return simpleUrlAuthenticationSuccessHandler;
	}
	
	@Bean(name = "failureHandler")
	public SimpleUrlAuthenticationFailureHandler simpleUrlAuthenticationFailureHandler() {
		return new SimpleUrlAuthenticationFailureHandler("/getLoginError");
	}

	@Bean(name = "aclResourcesService")
	@ConditionalOnMissingBean
	public AclResourcesService aclResourcesService() {
		return new AclResourcesServiceImpl();
	}

	/*
	 * 表达式控制器
	 */
	@Bean(name = "expressionHandler")
	public DefaultWebSecurityExpressionHandler webSecurityExpressionHandler() {
		DefaultWebSecurityExpressionHandler webSecurityExpressionHandler = new DefaultWebSecurityExpressionHandler();
		return webSecurityExpressionHandler;
	}

	/*
	 * 表达式投票器
	 */
	@Bean(name = "expressionVoter")
	public WebExpressionVoter webExpressionVoter() {
		WebExpressionVoter webExpressionVoter = new WebExpressionVoter();
		webExpressionVoter.setExpressionHandler(webSecurityExpressionHandler());
		return webExpressionVoter;
	}

}
