package com.cn.iboot.diy.api.security;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.session.SessionRegistryImpl;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.access.AccessDeniedHandlerImpl;
import org.springframework.security.web.access.expression.DefaultWebSecurityExpressionHandler;
import org.springframework.security.web.access.intercept.FilterSecurityInterceptor;
import org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.session.ConcurrentSessionControlAuthenticationStrategy;
import org.springframework.security.web.session.SessionInformationExpiredStrategy;
import org.springframework.security.web.session.SimpleRedirectSessionInformationExpiredStrategy;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

	private Logger logger = LoggerFactory.getLogger(getClass());
	
	@Autowired
	private MyUserDetailServiceImpl userDetailsService;

	@Autowired
	private MySecurityMetadataSource securityMetadataSource;

	@Autowired
	private MyAccessDecisionManager accessDecisionManager;

	@Autowired
	private SessionRegistry sessionRegistry;

	/**
	 * 注册UserDetailsService 的bean
	 * 
	 */
	@Bean
	protected UserDetailsService userDetailsService() {
		return userDetailsService;
	}

	/**
	 * user Details Service验证
	 * 
	 */
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userDetailsService());
	}

	/**
	 * 认证过滤器
	 * 
	 * @return
	 * @throws Exception
	 */
	@Bean
	public MyFilterSecurityInterceptor filterSecurityInterceptor() throws Exception {
		MyFilterSecurityInterceptor filterSecurityInterceptor = new MyFilterSecurityInterceptor();
		filterSecurityInterceptor.setAccessDecisionManager(accessDecisionManager);
		filterSecurityInterceptor.setSecurityMetadataSource(securityMetadataSource);
		filterSecurityInterceptor.setAuthenticationManager(authenticationManagerBean());
		return filterSecurityInterceptor;
	}

	/**
	 * 登录验证
	 * 
	 * @return
	 * @throws Exception
	 */
	@Bean
	public MyUsernamePasswordAuthenticationFilter myUsernamePasswordAuthenticationFilter() throws Exception {
		MyUsernamePasswordAuthenticationFilter usernamePasswordAuthenticationFilter = new MyUsernamePasswordAuthenticationFilter();
		usernamePasswordAuthenticationFilter.setPostOnly(true);
		usernamePasswordAuthenticationFilter.setRequiresAuthenticationRequestMatcher(new AntPathRequestMatcher("/doLogin", "POST"));
		// 登录失败之后返回的
		usernamePasswordAuthenticationFilter.setAuthenticationFailureHandler(simpleUrlAuthenticationFailureHandler());
		// 登录成功之后返回的路径
		usernamePasswordAuthenticationFilter.setAuthenticationSuccessHandler(loginLogAuthenticationSuccessHandler());
		usernamePasswordAuthenticationFilter.setAuthenticationManager(authenticationManagerBean());
		// session并发控制,因为默认的并发控制方法是空方法.这里必须自己配置一个
		usernamePasswordAuthenticationFilter.setSessionAuthenticationStrategy(new ConcurrentSessionControlAuthenticationStrategy(sessionRegistry));
		return usernamePasswordAuthenticationFilter;
	}

	@Bean
	public AccessDeniedHandler accessDeniedHandler() {
		AccessDeniedHandlerImpl accessDeniedHandler = new AccessDeniedHandlerImpl();
		accessDeniedHandler.setErrorPage("/error");
		return accessDeniedHandler;
	}

	/**
	 * 表达式控制器
	 * 
	 * @return
	 */
	@Bean(name = "expressionHandler")
	public DefaultWebSecurityExpressionHandler webSecurityExpressionHandler() {
		DefaultWebSecurityExpressionHandler webSecurityExpressionHandler = new DefaultWebSecurityExpressionHandler();
		return webSecurityExpressionHandler;
	}

	/**
	 * 登录成功后跳转 如果需要根据不同的角色做不同的跳转处理,那么继承AuthenticationSuccessHandler重写方法
	 * 
	 * @return
	 */
	public SavedRequestAwareAuthenticationSuccessHandler loginLogAuthenticationSuccessHandler() {
		SavedRequestAwareAuthenticationSuccessHandler loginLogAuthenticationSuccessHandler = new SavedRequestAwareAuthenticationSuccessHandler();
		loginLogAuthenticationSuccessHandler.setDefaultTargetUrl("/main");
		return loginLogAuthenticationSuccessHandler;
	}

	/**
	 * 验证异常处理器
	 * 
	 * @return
	 */
	public SimpleUrlAuthenticationFailureHandler simpleUrlAuthenticationFailureHandler() {
		// 请求跳转到登录页面
		return new SimpleUrlAuthenticationFailureHandler("/login");
	}

	@Bean(name = "authenticationManager")
	@Override
	public AuthenticationManager authenticationManagerBean() {
		AuthenticationManager authenticationManager = null;
		try {
			authenticationManager = super.authenticationManagerBean();
		} catch (Exception e) {
			logger.error("## authenticationManager error", e);
		}
		return authenticationManager;
	}

	/**
	 * session失效跳转
	 * 
	 * @return
	 */
	private SessionInformationExpiredStrategy sessionInformationExpiredStrategy() {
		return new SimpleRedirectSessionInformationExpiredStrategy("/login");
	}

	@Bean
	public SessionRegistry sessionRegistry() {
		return new SessionRegistryImpl();
	}

	/**
	 * 未登录切入点
	 * 
	 * @return
	 */
	private LoginUrlAuthenticationEntryPoint myAuthenticationEntryPoint() {
		// 未登录用户指定登录路径
		LoginUrlAuthenticationEntryPoint myAuthenticationEntryPoint = new LoginUrlAuthenticationEntryPoint("/login");
		return myAuthenticationEntryPoint;
	}

	/**
	 * 静态资源权限配置
	 */
	@Override
	public void configure(WebSecurity web) throws Exception {
		logger.info("静态资源权限配置方法");
		// 解决静态资源被拦截的问题
		web.ignoring().antMatchers("/diy/**", "/images/**", "/icon/**", "/jquery/**", "/favicon.ico");
		// web.securityInterceptor((FilterSecurityInterceptor)filterSecurityInterceptor(),FilterSecurityInterceptor.class);
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		// 解决不允许显问题
		http.headers().frameOptions().disable();
		// 访问这些路径,无需登录认证权限 "/authcode/**"：验证码通过路径
		http.authorizeRequests().antMatchers("/authcode/**", "/login", "/logout", "/error").permitAll();
		http.exceptionHandling().accessDeniedPage("/error");
		// 登录页面的访问地址，failureUrl("/login")：登录失败后转向的页面
		//defaultSuccessUrl("/main")：登录成功后转向的页面，invalidateHttpSession(true)： 注销行为任意访问
		http.authorizeRequests().anyRequest().authenticated().and().formLogin().loginPage("/login").failureUrl("/login")
				.permitAll().defaultSuccessUrl("/main").and().logout().permitAll().invalidateHttpSession(true);
		// 自定义登录
		http.addFilterAfter(myUsernamePasswordAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);
		// //自定义过滤器
		http.addFilterBefore(filterSecurityInterceptor(), FilterSecurityInterceptor.class);
		// session并发控制过滤器
//		http.addFilterAfter(new ConcurrentSessionFilter(sessionRegistry, sessionInformationExpiredStrategy()), ConcurrentSessionFilter.class);
		http.exceptionHandling().authenticationEntryPoint(new LoginUrlAuthenticationEntryPoint("/login")).and().logout()
				.logoutUrl("/logout").logoutSuccessUrl("/login").and().exceptionHandling().accessDeniedPage("/error");
		//重置密码与退出登录时进行处理
		http.logout().logoutUrl("/logout").logoutSuccessUrl("/login").invalidateHttpSession(true);
		// session管理
		// session失效后跳转
		http.sessionManagement().invalidSessionUrl("/login");
		http.sessionManagement().maximumSessions(1).expiredUrl("/login");
		// 关闭csrf
		http.csrf().disable();
	}
 
}