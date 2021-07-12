package com.Hrms.Employee.Service;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.Hrms.Employee.Entity.HrmsRoles;
import com.Hrms.Employee.Entity.HrmsUser;
import com.Hrms.Employee.Entity.HrmsUserRead;
import com.Hrms.Employee.Repository.HrmsRolesRepository;
import com.Hrms.Employee.Repository.HrmsUserReadRepository;
import com.Hrms.Employee.Repository.HrmsUserRepository;
import com.Hrms.Employee.Repository.HrmsUserRolesRepository;

@Service
public class MyUserDetailsService implements UserDetailsService {
	@Autowired
	HrmsUserRepository userrepository;

	@Autowired
	HrmsUserRolesRepository userrolerepository;

	@Autowired
	HrmsRolesRepository rolerepository;

	@Autowired
	HrmsUserReadRepository hrmsUserReadRepository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		// if (hrmsUserReadRepository.existsByEmail(username))
		// HrmsUserRead hrmsUserRead = hrmsUserReadRepository.findByEmail(username);

		// com.Tcra.Hrms.entity.
		// if (hrmsUserRead != null) {
		if (hrmsUserReadRepository.existsByEmail(username)) {
			HrmsUserRead hrmsUserRead = hrmsUserReadRepository.findByEmail(username);
			// HrmsUser user = userrepository.findByEmail(username).get();
			return new org.springframework.security.core.userdetails.User(hrmsUserRead.getEmail(),
					hrmsUserRead.getUser_hash(), getAuthority(hrmsUserRead.getRoles()));
		} else {
			Set<SimpleGrantedAuthority> authorities = new HashSet<>();
			HrmsUser user = userrepository.findByEmail(username)
					.orElseThrow(() -> new UsernameNotFoundException("User Not Found with username: " + username));
			return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getUser_hash(),
					authorities);
		}

	}

	public Set<SimpleGrantedAuthority> getAuthority(Set<HrmsRoles> roles) {
		Set<SimpleGrantedAuthority> authorities = new HashSet<>();
		if (roles == null || roles.isEmpty()) {
			authorities.add(new SimpleGrantedAuthority("ROLE_NORMAL"));
		} else {

			roles.forEach(role -> {

				authorities.add(new SimpleGrantedAuthority(role.getName()));

			});
		}

		System.out.println(" ROLES WILL BE PRINTED HERE  " + authorities);
		return authorities;
		// return Arrays.asList(new SimpleGrantedAuthority("ROLE_ADMIN"));
	}

}
