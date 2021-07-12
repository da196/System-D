package com.Hrms.Configuration;

import java.util.ArrayList;
import java.util.Hashtable;

import javax.naming.Context;
import javax.naming.directory.DirContext;
import javax.naming.directory.InitialDirContext;
import javax.validation.constraints.NotEmpty;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ldap.NamingException;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

@Component
public class CustomAuthenticationProvider implements AuthenticationProvider {
	Logger log = LoggerFactory.getLogger(CustomAuthenticationProvider.class);

	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		String email = authentication.getName();
		String password = authentication.getCredentials().toString();

		try {
			if (isLdapRegistred(email, password)) {

				// use the credentials
				// and authenticate against the third-party system
				return new UsernamePasswordAuthenticationToken(email, password, new ArrayList<>());

			} else {
				return null;
			}
		} catch (NamingException ex) {

		}
		return null;
	}

	public boolean isLdapRegistred(String username, @NotEmpty String password) {
		boolean result = false;
		try {

			Hashtable<String, String> env = new Hashtable<String, String>();
			env.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.ldap.LdapCtxFactory");
			env.put(Context.PROVIDER_URL, "ldap://10.200.222.11:389");
			env.put(Context.SECURITY_AUTHENTICATION, "simple");
			env.put(Context.SECURITY_PRINCIPAL, username + "@" + "tcra.go.tz");
			env.put(Context.SECURITY_CREDENTIALS, password);

			// Create the initial context
			DirContext ctx = new InitialDirContext(env);
			result = ctx != null;
			if (ctx != null)
				ctx.close();
			System.out.println(result);
			return result;
		} catch (Exception e) {
			System.out.println("oops");
			return result;
		}

	}

	@Override
	public boolean supports(Class<?> authentication) {
		return authentication.equals(UsernamePasswordAuthenticationToken.class);
	}

}
