package com.jorge.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.mobile.device.Device;
import org.springframework.mobile.device.DeviceUtils;
import org.springframework.mobile.device.site.SitePreference;
import org.springframework.mobile.device.site.SitePreferenceUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class UserController {
	
	@RequestMapping("/user_list")
	public String userList(HttpServletRequest request) {
		String device = "";
		String version = "";
		
		Device currentDevice = DeviceUtils.getCurrentDevice(request); // This generates a Device object from HttpServletRequest
		SitePreference sitePreference =	SitePreferenceUtils.getCurrentSitePreference(request); // This is used to generate a SitePreference	object
		
		//Use the Device object to detect the type of device that sent the request:
		if(currentDevice == null)
			// detection failed
			device = "Detection failed";
		else if(currentDevice.isMobile())
			// mobile
			device = "This is a mobile";
		else if(currentDevice.isTablet())
			// tablet
			device = "This is a tablet";
		else if(currentDevice.isNormal())
			// desktop computer
			device = "This is a PC";
		
		// Use the SitePreference object to detect the version of the page to be displayed:
		if(sitePreference == null || sitePreference.isNormal())
			// normal
			version = "Displaying normal page version";
		else if(sitePreference.isMobile())
			// mobile
			version = "Displaying mobile page version";
		
		request.setAttribute("device", device);
		request.setAttribute("version", version);
		request.setAttribute("subfolder", "NO");
		
		System.out.println(this.getClass().getSimpleName() + "." + new Exception().getStackTrace()[0].getMethodName() + ": " + device);
		System.out.println(this.getClass().getSimpleName() + "." + new Exception().getStackTrace()[0].getMethodName() + ": " + version);
		
		return "user_list";
	}
	
	// Necessary to using a subfolder path on mobiles
	@RequestMapping("/mobile/user_list")
	public String userListMobile(HttpServletRequest request) {
		
		request.setAttribute("device", "This is a mobile");
		request.setAttribute("version", "Displaying mobile page version");
		request.setAttribute("subfolder", "YES");
		
		System.out.println(this.getClass().getSimpleName() + "." + new Exception().getStackTrace()[0].getMethodName() + ": This is a mobile");
		System.out.println(this.getClass().getSimpleName() + "." + new Exception().getStackTrace()[0].getMethodName() + ": Displaying mobile page version");
		
		return "user_list";
	}
	
}
