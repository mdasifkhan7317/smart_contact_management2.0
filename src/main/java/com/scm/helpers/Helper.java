package com.scm.helpers;

import java.security.Principal;

import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.OAuth2AuthenticatedPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;

public class Helper {

	public static String getEmailOfLoggedInUser(Authentication authentication) {

		// agar email, password se login kiya hai to : email kaise nikale

		if (authentication instanceof OAuth2AuthenticationToken) {

			var aOAuth2AuthenticationToken = (OAuth2AuthenticationToken) authentication;
			var clientId = aOAuth2AuthenticationToken.getAuthorizedClientRegistrationId();

			var oauth2User = (OAuth2User) authentication.getPrincipal();
			String username = "";

			if (clientId.equalsIgnoreCase("google")) {

				// sign in with google se : email kaise nikale
				username = oauth2User.getAttribute("email").toString();
				System.out.println("Getting email from google client");

			} else if (clientId.equalsIgnoreCase("github")) {

				// github se : email kaise nikale

				System.out.println("Getting email from github client");
				username = oauth2User.getAttribute("email") != null ? oauth2User.getAttribute("email").toString()
						: oauth2User.getAttribute("login").toString() + "@gmail.com";

			}

			// facebook se email kaise nikale

			return username;

		} else {
			System.out.println("Getting data from local database");
			return authentication.getName();
		}

	}

}
