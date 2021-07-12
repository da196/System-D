package com.Hrms.Employee.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.Hrms.Employee.DTO.Userloginresponse;
import com.Hrms.Employee.Entity.HrmsUserLogins;
import com.Hrms.Employee.Entity.HrmsUserRoles;
import com.Hrms.Employee.Repository.HrmsEmployeeRepository;
import com.Hrms.Employee.Repository.HrmsOrginisationUnitRepository;
import com.Hrms.Employee.Repository.HrmsRolesRepository;
import com.Hrms.Employee.Repository.HrmsUserLoginsRepository;
import com.Hrms.Employee.Repository.HrmsUserRepository;
import com.Hrms.Employee.Repository.HrmsUserRolesRepository;

@Service
public class HrmsUserLoginsServiceImpl implements HrmsUserLoginsService {
	@Autowired
	private HrmsUserLoginsRepository hrmsUserLoginsRepository;

	@Autowired
	private HrmsUserRepository hrmsUserRepository;

	@Autowired
	private HrmsEmployeeRepository hrmsEmployeeRepository;

	@Autowired
	private HrmsOrginisationUnitRepository hrmsOrginisationUnitRepository;

	@Autowired
	private HrmsUserRolesRepository hrmsUserRolesRepository;

	@Autowired
	private HrmsRolesRepository hrmsRolesRepository;

	@Override
	public ResponseEntity<HrmsUserLogins> adduserlogin(HrmsUserLogins hrmsUserLogins) {

		UUID uuid = UUID.randomUUID();
		hrmsUserLogins.setUnique_id(uuid);
		return ResponseEntity.status(HttpStatus.OK).body(hrmsUserLoginsRepository.save(hrmsUserLogins));

	}

	@Override
	public ResponseEntity<HrmsUserLogins> updateuserlogin(HrmsUserLogins hrmsUserLogins, int userid) {// not finished

		LocalDateTime LocalTime = LocalDateTime.now();
		// hrmsUserLogins.setDate_updated(LocalTime);
		if (hrmsUserLoginsRepository.existsByuserIdAndApproved(userid, 1)) {
			HrmsUserLogins oldusr = hrmsUserLoginsRepository.findByuserIdAndActive(userid, 1).get();
			oldusr.setActive(0);
			oldusr.setDate_updated(LocalTime);

			hrmsUserLoginsRepository.save(oldusr);

			return ResponseEntity.status(HttpStatus.OK).body(hrmsUserLoginsRepository.save(hrmsUserLogins));
		} else {
			UUID uuid = UUID.randomUUID();
			hrmsUserLogins.setUnique_id(uuid);

			return ResponseEntity.status(HttpStatus.OK).body(hrmsUserLoginsRepository.save(hrmsUserLogins));
		}
	}

	@Override
	public ResponseEntity<List<Userloginresponse>> viewlog(int id) {

		if (hrmsUserLoginsRepository.existsByuserId(
				hrmsUserRepository.findByEmail(hrmsEmployeeRepository.findById(id).get().getEmail()).get().getId())) {
			List<Userloginresponse> userloginlist = new ArrayList<>();

			List<HrmsUserLogins> hrmsUserLogins = hrmsUserLoginsRepository.findByuserId(
					hrmsUserRepository.findByEmail(hrmsEmployeeRepository.findById(id).get().getEmail()).get().getId());

			hrmsUserLogins.forEach(dbemp -> {
				Userloginresponse usrlogn = new Userloginresponse();
				usrlogn.setId(dbemp.getId());
				if (dbemp.getActive() == 1 && dbemp.getApproved() == 1) {
					usrlogn.setStatus("Active");
					usrlogn.setAuthenticated("successfully logged in");

				} else {
					if (dbemp.getApproved() == 1 && dbemp.getActive() == 0) {
						usrlogn.setStatus("logged off");
						usrlogn.setAuthenticated("successfully logged in");

					} else {
						usrlogn.setStatus("neverlogged");
						usrlogn.setAuthenticated("unsuccessfully logged in");
					}
				}

				String mail = hrmsUserRepository.findById(dbemp.getUserId()).get().getEmail();
				int empid = hrmsEmployeeRepository.findByEmail(mail).getId();

				Set<HrmsUserRoles> UserRoles = hrmsUserRolesRepository.findByUserIdAndActive(empid, 1);
				if (!UserRoles.isEmpty()) {
					Set<String> role = getroles(UserRoles);
					usrlogn.setRole(role);
				}

				StringBuilder fullname = new StringBuilder();
				fullname.append(hrmsEmployeeRepository.findByEmail(mail).getFirstName().trim());
				fullname.append("  " + hrmsEmployeeRepository.findByEmail(mail).getMiddleName().trim());
				fullname.append(" " + hrmsEmployeeRepository.findByEmail(mail).getLastName().trim());
				usrlogn.setFullname(fullname.toString());
				usrlogn.setTimein(dbemp.getDatcreated());
				usrlogn.setUnit(hrmsOrginisationUnitRepository
						.findById(hrmsEmployeeRepository.findByEmail(mail).getUnitId()).get().getName());

				userloginlist.add(usrlogn);

			});

			return ResponseEntity.status(HttpStatus.OK).body(userloginlist);
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
		}
	}

	@Override
	public ResponseEntity<List<Userloginresponse>> listAlllogs() {

		List<Userloginresponse> userloginlist = new ArrayList<>();

		List<HrmsUserLogins> hrmsUserLogins = hrmsUserLoginsRepository.findAll();

		hrmsUserLogins.forEach(dbemp -> {
			Userloginresponse usrlogn = new Userloginresponse();
			usrlogn.setId(dbemp.getId());
			if (dbemp.getActive() == 1 && dbemp.getApproved() == 1) {
				usrlogn.setStatus("Active");
				usrlogn.setAuthenticated("successfully logged in");

			} else {
				if (dbemp.getApproved() == 1 && dbemp.getActive() == 0) {
					usrlogn.setStatus("logged off");
					usrlogn.setAuthenticated("successfully logged in");

				} else {
					usrlogn.setStatus("neverlogged");
					usrlogn.setAuthenticated("unsuccessfully logged in");
				}
			}

			String mail = hrmsUserRepository.findById(dbemp.getUserId()).get().getEmail();
			int empid = hrmsEmployeeRepository.findByEmail(mail).getId();

			Set<HrmsUserRoles> UserRoles = hrmsUserRolesRepository.findByUserIdAndActive(empid, 1);
			if (!UserRoles.isEmpty()) {
				Set<String> role = getroles(UserRoles);
				usrlogn.setRole(role);
			}

			StringBuilder fullname = new StringBuilder();
			fullname.append(hrmsEmployeeRepository.findByEmail(mail).getFirstName().trim());
			fullname.append("  " + hrmsEmployeeRepository.findByEmail(mail).getMiddleName().trim());
			fullname.append(" " + hrmsEmployeeRepository.findByEmail(mail).getLastName().trim());
			usrlogn.setFullname(fullname.toString());
			usrlogn.setTimein(dbemp.getDatcreated());
			usrlogn.setUnit(hrmsOrginisationUnitRepository
					.findById(hrmsEmployeeRepository.findByEmail(mail).getUnitId()).get().getName());

			userloginlist.add(usrlogn);

		});

		return ResponseEntity.status(HttpStatus.OK).body(userloginlist);
	}

	public Set<String> getroles(Set<HrmsUserRoles> useroles) {
		Set<String> roles = new HashSet<>();
		useroles.forEach(dbemp -> {
			roles.add(hrmsRolesRepository.findById(dbemp.getRoleId()).get().getName());

		});

		return roles;
	}

	@Override
	public ResponseEntity<?> logout(int empid) {
		if (hrmsEmployeeRepository.existsById(empid)) {

			String email = hrmsEmployeeRepository.findById(empid).get().getEmail();
			int userid = hrmsUserRepository.findByEmail(email).get().getId();
			if (hrmsUserLoginsRepository.existsByuserIdAndActive(userid, 1)) {
				HrmsUserLogins userlog = hrmsUserLoginsRepository.findByuserIdAndActive(userid, 1).get();
				userlog.setActive(0);
				hrmsUserLoginsRepository.save(userlog);
				return ResponseEntity.status(HttpStatus.OK).body(null);
			} else {
				return ResponseEntity.status(HttpStatus.ALREADY_REPORTED).body(null);
			}
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
		}
	}

}
