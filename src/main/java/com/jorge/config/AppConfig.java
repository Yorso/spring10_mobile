/**
 * This is a configuration class
 * 
 */

package com.jorge.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.mobile.device.DeviceResolverHandlerInterceptor;
import org.springframework.mobile.device.site.SitePreferenceHandlerInterceptor;
import org.springframework.mobile.device.switcher.SiteSwitcherHandlerInterceptor;
import org.springframework.mobile.device.view.LiteDeviceDelegatingViewResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.view.InternalResourceViewResolver;


@Configuration // This declares it as a Spring configuration class
@EnableWebMvc // This enables Spring's ability to receive and process web requests. Necessary for interceptors too.
@ComponentScan(basePackages = { "com.jorge.controller" }) // This scans the com.jorge.controller package for Spring components

// @Import({ DatabaseConfig.class, SecurityConfig.class }) => //If you are using a Spring application without a 'ServletInitializer' class,
														      // you can include other configuration classes from your primary configuration class

public class AppConfig extends WebMvcConfigurerAdapter { // Extend from WebMvcConfigurerAdapter is necessary for interceptors

	 /* This method is replaced by liteDeviceAwareViewResolver() method below
	 @Bean 
	 public ViewResolver jspViewResolver(){ 
		 InternalResourceViewResolver resolver = new InternalResourceViewResolver();
		 
	 	 resolver.setViewClass(JstlView.class);
	 	 resolver.setPrefix("/WEB-INF/jsp/"); // These folders must be created on /src/main/webapp/ 
	 	 resolver.setSuffix(".jsp"); 
	 	 
	 	 return resolver; 
	 }
	 */
	 
	/**
	 * Using different JSP views for mobiles automatically
	 * 
	 * Instead of having to manually select the correct JSP in each controller method depending on the
	 * request device or site preference, use LiteDeviceDelegatingViewResolver provided by Spring Mobile.
	 * 
	 */
	@Bean
	// This method replaces any existing ViewResolver bean method
	public LiteDeviceDelegatingViewResolver liteDeviceAwareViewResolver() {
		InternalResourceViewResolver delegate = new InternalResourceViewResolver();
		
		delegate.setPrefix("/WEB-INF/jsp/"); // The /WEB-INF/user_list.jsp JSP view will be used if the site preference is normal
		delegate.setSuffix(".jsp");
		
		LiteDeviceDelegatingViewResolver resolver = new
		LiteDeviceDelegatingViewResolver(delegate);
		
		resolver.setMobilePrefix("mobile/"); // The /WEB-INF/mobile/user_list.jsp JSP view will be used if the site preference is mobile
		resolver.setTabletPrefix("tablet/"); // The /WEB-INF/tablet/user_list.jsp JSP view will be used if the site preference is tablet
		resolver.setEnableFallback(true); // This line means that if the site preference is mobile and /WEB-INF/mobile/user_list.jsp
							              //doesn't exist, /WEB-INF/user_list.jsp will be used as a fallback instead. Same thing for tablets
		
		return resolver;
	}
	

	/**
	 * INTERCEPTORS
	 * 
	 * The interceptor methods are executed at the corresponding moments of the
	 * request workflow
	 */
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(deviceResolverHandlerInterceptor()); // Registering deviceResolverHandlerInterceptor() interceptor method. We use it in Controller methods
		registry.addInterceptor(sitePreferenceHandlerInterceptor());
		registry.addInterceptor(siteSwitcherHandlerInterceptor());
	}
	
	/**
	 * Switching to the normal view on mobiles
	 * 
	 * A mobile user gets the mobile version of the website by default, but he/she may want to access some
	 * contents displayed only on the normal version. 
	 * Spring Mobile offers the SitePreference object for that purpose, which is to be used 
	 * instead of the Device object used in the previous recipe.
	 * 
	 */
	@Bean
	// Necessary to detect whether the current HTTP	request has come from a desktop computer, mobile, or tablet
	public DeviceResolverHandlerInterceptor deviceResolverHandlerInterceptor() {
		return new DeviceResolverHandlerInterceptor();
	}
	
	@Bean
	public SitePreferenceHandlerInterceptor sitePreferenceHandlerInterceptor() {
		return new SitePreferenceHandlerInterceptor();
	}
	
	/**
	 * 1. Using a .mobi domain name on mobiles
	 * 
	 * In this recipe, you'll learn how to use a top-level .mobi domain name for the mobile pages of your
	 * website. For example:
	 * 		mysite.com for the normal website
	 * 		mysite.mobi for the mobile version
	 * 
	 * The top-level domain name .mobi has been created to enable visitors of a website to ask explicitly for its mobile version
	 * 
	 * Behind the scenes, SiteSwitcherHandlerInterceptor reads the current SitePreference value
	 * (normal, tablet, or mobile) and performs a redirect to the correct domain name if necessary. For
	 * example, an HTTP request from a mobile device for mywebsite.com will be automatically
	 * redirected to mywebsite.mobi . A tablet will go to the normal website
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 2. Using an m. subdomain on mobiles
	 * 
	 * In this recipe, you'll learn how to use an m. subdomain for the mobile pages of your website. For example:
	 * 		mysite.com for the normal website
	 * 		m.mysite.com for the mobile version
	 * 
	 * Some advantages of an m. subdomain are:
	 * 		No need to purchase another domain name (and another SSL certificate if you're using HTTPS)
	 * 		It is easy to remember for the user
	 * 
	 * It works like .mobi and a tablet will go to the normal website too.
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 3. Using a different domain name on mobiles
	 * 
	 * In this recipe, you'll learn how to use a different domain name for the mobile pages of your website.For example:
	 * 		mysite.com for the normal
	 * 		mymobilesite.com for the website mobile version
	 * 
	 * It works like .mobi and a tablet will go to the normal website too.
	 * 
	 * The Set-Cookie HTTP header field contains the SitePreference value. The cookie allows us to
	 * share that value with subdomains. In this recipe, .mywebsite.com makes the SitePreference value
	 * available to www.mywebsite.com , for example.
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 4. Using a subfolder path on mobiles
	 * 
	 * In this recipe, you'll learn how to use a subfolder in the URL for the mobile pages of your website. For example:
	 * 		mysite.com for the normal website
	 * 		mysite.com/mobile for the mobile version
	 * 
	 *  It works like .mobi and a tablet will go to the normal website too.
	 *  
	 *  
	 */
	@Bean
	public SiteSwitcherHandlerInterceptor siteSwitcherHandlerInterceptor() {
		return SiteSwitcherHandlerInterceptor.dotMobi("mywebsite.com"); // 1. mysite.mobi
		//return SiteSwitcherHandlerInterceptor.mDot("mywebsite.com"); // 2. m.mysite.com
		//return SiteSwitcherHandlerInterceptor.standard("mywebsite.com", "mymobilewebsite.com", ".mywebsite.com"); // 3. Declare a SiteSwitcherHandlerInterceptor bean initialized
																												    // with the standard() method with your main domain name, mobile domain name, and the value
																												    // for the Set-Cookie HTTP header field
		//return SiteSwitcherHandlerInterceptor.urlPath("/mobile", "spring10_mobile"); // 4. You must add the controller method for the URL with the mobile subfolder '@RequestMapping("/mobile/user_list")'
																					   // Declare a SiteSwitcherHandlerInterceptor bean initialized
																					   // with the urlPath() method with the subfolder name and the web application root path ifnecessary
	}
	
}