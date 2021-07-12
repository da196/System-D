package com.Hrms.Configuration;

import java.time.LocalDateTime;
import java.util.UUID;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.Hrms.Communication.SendEmail;
import com.Hrms.Employee.DTO.AuthenticationRequest;
import com.Hrms.Employee.DTO.AuthenticationResponseTemp;
import com.Hrms.Employee.DTO.LoginResponse;
import com.Hrms.Employee.Entity.HrmsEmployee;
import com.Hrms.Employee.Entity.HrmsUser;
import com.Hrms.Employee.Entity.HrmsUserLogins;
import com.Hrms.Employee.Repository.HrmsDesignationRepository;
import com.Hrms.Employee.Repository.HrmsEmployeeRepository;
import com.Hrms.Employee.Repository.HrmsUserLoginsRepository;
import com.Hrms.Employee.Repository.HrmsUserRepository;
import com.Hrms.Employee.Service.HrmsUserLoginsService;
import com.Hrms.Employee.Service.MyUserDetailsService;
import com.Hrms.Leave.Service.HrmsLeaveApplicationsService;
import com.Hrms.Leave.Service.HrmsLeaveRecallService;
import com.Hrms.Leave.Service.HrmsLeaveSoldService;
import com.Hrms.Training.Service.HrmsTrainingService;

@RestController
@RequestMapping("/v1/authController")
public class AuthController {
	@Autowired
	CustomAuthenticationProvider othenticationProvider;

	@Autowired
	AuthenticationManager authenticationManager;

	@Autowired
	MyUserDetailsService userDetailsService;
	@Autowired
	PasswordEncoder encoder;

	@Autowired
	private JwtUtill jwtTokenUtil;

	@Autowired
	private HrmsTrainingService hrmsTrainingService;

	@Autowired
	private HrmsLeaveSoldService hrmsLeaveSoldService;

	@Autowired
	private HrmsLeaveApplicationsService hrmsLeaveApplicationsService;

	@Autowired
	private HrmsLeaveRecallService hrmsLeaveRecallService;

	@Autowired
	private HrmsEmployeeRepository emprepository;
	@Autowired
	private HrmsUserRepository hrmsUserRepository;

	@Autowired
	private HrmsUserLoginsService hrmsUserLoginsService;

	@Autowired
	private HrmsUserLoginsRepository hrmsUserLoginsRepository;

	@Autowired
	private HrmsDesignationRepository hrmsDesignationRepository;

	@Autowired
	private SendEmail sendnotificationviaemail;

	@PostMapping(value = "/authenticate")
	public ResponseEntity<LoginResponse> loginAndTokenProvider(
			@Valid @RequestBody AuthenticationRequest authenticationRequest) throws Exception {

		int leaveApproval = 0;

		int leaveCommutationApproval = 0;

		int leaveRecallApproval = 0;
		int trainingApproval = 0;

		int totalApproval = 0;

		// userlogin check if we have more than or equalled five failure
		if (hrmsUserRepository.existsByEmail(authenticationRequest.getUsername() + "@tcra.go.tz")) {

			if (hrmsUserRepository.existsByEmailAndLocked(authenticationRequest.getUsername() + "@tcra.go.tz", 1)) {
				return ResponseEntity.status(HttpStatus.LOCKED).body(new LoginResponse());

			} else {

				int userid = hrmsUserRepository.findByEmail(authenticationRequest.getUsername() + "@tcra.go.tz").get()
						.getId();

				if (hrmsUserLoginsRepository.countByUserIdAndDatcreatedAfterAndApproved(userid,
						LocalDateTime.now().minusMinutes(5), 0) >= 5) {
					sendnotificationviaemail.sendmaillogin(
							emprepository.findByEmail(authenticationRequest.getUsername() + "@tcra.go.tz")
									.getFirstName(),
							authenticationRequest.getUsername() + "@tcra.go.tz",
							"Your account has been locded temporary due to five consecutive failed login attempt , kindly wait for another five minutes to be able to login again");

					return ResponseEntity.status(HttpStatus.NON_AUTHORITATIVE_INFORMATION).body(new LoginResponse());

				}
			}
		}

		// normal userlogin procedure
		if (// othenticationProvider.isLdapRegistred(authenticationRequest.getUsername(),
			// authenticationRequest.getPassword())

		2 != 3) {

			createNewUser(authenticationRequest.getUsername() + "@tcra.go.tz");

			final UserDetails userDetails;
			try {

				// add if to check if username does exist in userrole
				userDetails = userDetailsService
						.loadUserByUsername(authenticationRequest.getUsername() + "@tcra.go.tz");
			} catch (Exception e) {
				LoginResponse response = new LoginResponse();
				response.setEmail(authenticationRequest.getUsername() + "@tcra.go.tz");

				System.out.println("Trace problem here" + e);
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new LoginResponse());

			}

			final String jwt = jwtTokenUtil.generateToken(userDetails);

			HttpHeaders responseHeaders = new HttpHeaders();
			responseHeaders.set("Authorization", "Bearer " + jwt);

			final String authorities = userDetails.getAuthorities().stream().map(GrantedAuthority::getAuthority)
					.collect(Collectors.joining(","));
			HrmsEmployee emp = null;
			if (emprepository.existsByEmail(authenticationRequest.getUsername() + "@tcra.go.tz")) {
				try {
					emp = emprepository.findByEmail(authenticationRequest.getUsername() + "@tcra.go.tz");

					System.out.println("employee from db" + emp);
				} catch (Exception e) {
					System.out.println("error from employee db" + e.getMessage());
				}
				String fullname = null;
				int id = hrmsUserRepository.findByEmail(authenticationRequest.getUsername() + "@tcra.go.tz").get()
						.getId();

				String designation = hrmsDesignationRepository.findById(emp.getDesignationId()).get().getName();

				// addlogs
				HrmsUserLogins hrmsUserLogins = new HrmsUserLogins();
				hrmsUserLogins.setActive(1);
				hrmsUserLogins.setApproved(1);
				hrmsUserLogins.setUserId(id);

				try {

					hrmsUserLoginsService.updateuserlogin(hrmsUserLogins, id);
				} catch (Exception e) {

				}
				System.out.println("hapa shida" + emp);

				StringBuilder builderSupervisor = new StringBuilder();
				builderSupervisor.append(emp.getFirstName().trim());
				builderSupervisor.append(" " + emp.getMiddleName().trim());
				builderSupervisor.append(" " + emp.getLastName().trim());
				fullname = builderSupervisor.toString();

				// sendnotificationviaemail.sendmaillogin(fullname,
				// authenticationRequest.getUsername() + "@tcra.go.tz"); // emeil
				// notification
				// on
				// successfull
				// login
				if (emp != null && emp.getId() != 0 && emp.getIssupervisor() == 1) {
					trainingApproval = hrmsTrainingService.getHrmsTrainingNotApprovedBySupervisorNext(emp.getId())
							.getBody().size();

					leaveRecallApproval = hrmsLeaveRecallService
							.getHrmsLeaveRecallNotApprovedBySupervisorNext(emp.getId()).getBody().size();

					leaveCommutationApproval = hrmsLeaveSoldService
							.getHrmsLeaveCommutationNotApprovedBySupervisorNext(emp.getId()).getBody().size();

					leaveApproval = hrmsLeaveApplicationsService.getHrmsLeaveNotApprovedBySupervisorNext(emp.getId())
							.getBody().size();

					totalApproval = trainingApproval + leaveRecallApproval + leaveCommutationApproval + leaveApproval;

				}

				return ResponseEntity.ok().headers(responseHeaders)
						.body(new LoginResponse("Bearer " + jwt, authenticationRequest.getUsername() + "@tcra.go.tz",
								authorities, emp.getPicture(), fullname, id, emp.getId(), emp.getFileNumber(),
								designation, leaveApproval, leaveCommutationApproval, leaveRecallApproval,
								trainingApproval, totalApproval));
			} else {
				if (authenticationRequest.getUsername().equals("edms1")) {
					return ResponseEntity.ok().headers(responseHeaders)
							.body(new LoginResponse("Bearer " + jwt,
									authenticationRequest.getUsername() + "@tcra.go.tz", authorities, null, null, 0, 0,
									null, null, leaveApproval, leaveCommutationApproval, leaveRecallApproval,
									trainingApproval, totalApproval));
				} else {
					return ResponseEntity.ok().headers(responseHeaders)
							.body(new LoginResponse("Bearer " + null,
									authenticationRequest.getUsername() + "@tcra.go.tz", authorities, null, null, 0, 0,
									null, null, leaveApproval, leaveCommutationApproval, leaveRecallApproval,
									trainingApproval, totalApproval));
				}
			}
		} else {
			if (hrmsUserRepository.existsByEmail(authenticationRequest.getUsername() + "@tcra.go.tz")) {
				int id = hrmsUserRepository.findByEmail(authenticationRequest.getUsername() + "@tcra.go.tz").get()
						.getId();
				HrmsUserLogins hrmsUserLogins = new HrmsUserLogins();

				hrmsUserLogins.setActive(0);
				hrmsUserLogins.setApproved(0);
				hrmsUserLogins.setUserId(id);

				try {

					hrmsUserLoginsService.adduserlogin(hrmsUserLogins);

					System.out.println("logger for loggin in db");

				} catch (Exception e) {
					System.out.println(e.getMessage());

				}

				LoginResponse response = new LoginResponse();
				response.setEmail("no email");
				response.setFilenumber("no file number");
				response.setId(0);
				response.setPhoto("no photo");
				response.setRoles("no roles");
				response.setToken("no token");
				return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new LoginResponse());

			} else {
				HrmsUserLogins hrmsUserLogins = new HrmsUserLogins();

				hrmsUserLogins.setActive(0);
				hrmsUserLogins.setApproved(0);

				try {

					// hrmsUserLoginsService.adduserlogin(hrmsUserLogins);

					System.out.println("logger for loggin in db wrong username and password ");

				} catch (Exception e) {
					System.out.println(e.getMessage());

				}
				LoginResponse response = new LoginResponse();
				response.setEmail("no email");
				response.setFilenumber("no file number");
				response.setId(0);
				response.setPhoto("no photo");
				response.setRoles("no roles");
				response.setToken("no token");
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new LoginResponse());
			}

		}

	}

	@GetMapping(value = "/freeToken/{id}")
	public ResponseEntity<AuthenticationResponseTemp> freeToken(@PathVariable("id") int id) {
		AuthenticationResponseTemp authenticationResponseTemp = new AuthenticationResponseTemp();

		if (emprepository.existsById(id)) {
			String email = emprepository.findById(id).get().getEmail();
			HrmsEmployee emp = emprepository.findById(id).get();

			final UserDetails userDetails;

			try {

				// add if to check if username does exist in userrole
				userDetails = userDetailsService.loadUserByUsername(email);
			} catch (Exception e) {

				System.out.println("Trace problem here" + e);
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body(authenticationResponseTemp);

			}

			final String jwt = jwtTokenUtil.generateToken(userDetails);

			HttpHeaders responseHeaders = new HttpHeaders();
			responseHeaders.set("Authorization", "Bearer " + jwt);
			authenticationResponseTemp.setEmail(email);
			authenticationResponseTemp.setJwt("Bearer " + jwt);
			if (emp.getFirstName() != null) {
				authenticationResponseTemp.setFirstname(emp.getFirstName());
			}
			if (emp.getMiddleName() != null) {
				authenticationResponseTemp.setMiddlename(emp.getMiddleName());
			}
			if (emp.getLastName() != null) {
				authenticationResponseTemp.setLastname(emp.getLastName());
			}

			return ResponseEntity.ok().headers(responseHeaders).body(authenticationResponseTemp);
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
		}

	}

	// @Secured("ROLE_ADMIN")
	// @PreAuthorize("hasRole('ADMIN') AND hasRole('USER')")
	@PreAuthorize("hasAnyRole('ROLE_USER', 'ROLE_ADMINISTRATOR')")
	@GetMapping(value = "/test")
	public String test(HttpServletRequest request) {
		System.out.println(request.getRemoteAddr());
		String authorizationHeader = request.getHeader("Authorization");

		String username = null;
		String jwt = null;

		if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
			jwt = authorizationHeader.substring(7);
			username = jwtTokenUtil.extractUsername(jwt);
		}

		return username;
	}

	public Boolean createNewUser(String email) {
		if (!hrmsUserRepository.existsByEmail(email)) {
			HrmsUser hrmsUser = new HrmsUser();
			hrmsUser.setActive(1);
			hrmsUser.setApproved(1);
			hrmsUser.setUnique_id(UUID.randomUUID());
			hrmsUser.setEmail(email);
			hrmsUser.setUser_hash(email.concat("hhfgkdfguvnsd"));

			hrmsUserRepository.saveAndFlush(hrmsUser);
			return true;
		} else {
			return false;
		}

	}

	public Boolean failureCount(String Email) {

		return true;
	}

}
