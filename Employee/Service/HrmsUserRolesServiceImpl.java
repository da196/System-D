package com.Hrms.Employee.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.Hrms.Employee.DTO.UserRolesResponse;
import com.Hrms.Employee.Entity.HrmsEmployee;
import com.Hrms.Employee.Entity.HrmsRoles;
import com.Hrms.Employee.Entity.HrmsUserRoles;
import com.Hrms.Employee.Repository.HrmsEmployeeRepository;
import com.Hrms.Employee.Repository.HrmsRolesRepository;
import com.Hrms.Employee.Repository.HrmsUserRepository;
import com.Hrms.Employee.Repository.HrmsUserRolesRepository;

@Service
public class HrmsUserRolesServiceImpl implements HrmsUserRolesService {
	@Autowired
	private HrmsUserRolesRepository hrmsUserRolesRepository;

	@Autowired
	private HrmsUserRepository hrmsUserRepository;

	@Autowired
	private HrmsRolesRepository hrmsRolesRepository;

	@Autowired
	private HrmsEmployeeRepository hrmsEmployeeRepository;

	@Override
	public ResponseEntity<HrmsUserRoles> save(HrmsUserRoles hrmsUserRoles) {
		if (hrmsUserRepository.existsById(hrmsUserRoles.getUserId())
				&& hrmsRolesRepository.existsById(hrmsUserRoles.getRoleId())) {

			if (hrmsUserRolesRepository.existsByUserIdAndRoleId(hrmsUserRoles.getUserId(), hrmsUserRoles.getRoleId())) {
				return ResponseEntity.status(HttpStatus.ALREADY_REPORTED).body(hrmsUserRoles);

			} else {
				UUID uuid = UUID.randomUUID();
				hrmsUserRoles.setUnique_id(uuid);
				hrmsUserRoles.setActive(1);
				hrmsUserRoles.setApproved(0);
				return ResponseEntity.status(HttpStatus.OK).body(hrmsUserRolesRepository.save(hrmsUserRoles));

			}
		} else {
			return ResponseEntity.status(HttpStatus.PRECONDITION_FAILED).body(hrmsUserRoles);
		}
	}

	@Override
	public ResponseEntity<Optional<HrmsUserRoles>> viewHrmsUserRoles(int id) {

		return null;
	}

	@Override
	public ResponseEntity<HrmsUserRoles> update(HrmsUserRoles hrmsUserRoles, int id) {
		hrmsUserRoles.setActive(1);
		hrmsUserRoles.setApproved(0);
		hrmsUserRoles.setDate_updated(LocalDateTime.now());
		if (hrmsUserRolesRepository.existsById(id)) {
			return ResponseEntity.status(HttpStatus.OK).body(hrmsUserRolesRepository.save(hrmsUserRoles));
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(hrmsUserRoles);
		}

	}

	@Override
	public ResponseEntity<?> deleteHrmsUserRoles(int id) {
		if (hrmsUserRolesRepository.existsById(id)) {

			HrmsUserRoles hrmsUserRoles = hrmsUserRolesRepository.findById(id).get();
			hrmsUserRoles.setActive(0);
			hrmsUserRoles.setDate_updated(LocalDateTime.now());

			return ResponseEntity.status(HttpStatus.OK).body(hrmsUserRolesRepository.save(hrmsUserRoles));

		}

		return null;
	}

	@Override
	public ResponseEntity<List<HrmsUserRoles>> listHrmsUserRoles() {

		return null;
	}

	@Override
	public ResponseEntity<UserRolesResponse> getUserRolesById(int id) {
		List<HrmsRoles> roles = new ArrayList<>();
		UserRolesResponse userRolesResponse = new UserRolesResponse();

		if (hrmsUserRolesRepository.existsByIdAndActive(id, 1)) {
			HrmsUserRoles dbm = hrmsUserRolesRepository.findByIdAndActive(id, 1);

			userRolesResponse.setActive(dbm.getActive());
			userRolesResponse.setApproved(dbm.getApproved());
			userRolesResponse.setId(dbm.getId());
			if (dbm.getRoleId() != 0 && hrmsRolesRepository.existsById(dbm.getRoleId())) {

				roles.add(hrmsRolesRepository.findById(dbm.getRoleId()).get());

				userRolesResponse.setRoles(roles);
			}
			userRolesResponse.setRoleId(dbm.getRoleId());
			if (dbm.getUserId() != 0 && hrmsUserRepository.existsById(dbm.getUserId()) && hrmsEmployeeRepository
					.existsByEmail(hrmsUserRepository.findById(dbm.getUserId()).get().getEmail())) {
				HrmsEmployee hrmsEmployee = hrmsEmployeeRepository
						.findByEmail(hrmsUserRepository.findById(dbm.getUserId()).get().getEmail());
				StringBuilder user = new StringBuilder();

				user.append(hrmsEmployee.getFirstName().trim());
				if (hrmsEmployee.getMiddleName() != null) {
					user.append(" " + hrmsEmployee.getMiddleName().trim());
				}
				user.append(" " + hrmsEmployee.getLastName().trim());

				userRolesResponse.setUser(user.toString());
			}
			userRolesResponse.setUserId(dbm.getUserId());

		}

		return ResponseEntity.status(HttpStatus.OK).body(userRolesResponse);
	}

	@Override
	public ResponseEntity<List<UserRolesResponse>> getAllUserRoles() {
		List<UserRolesResponse> userRolesResponselist = new ArrayList<>();

		List<HrmsUserRoles> dbms = hrmsUserRolesRepository.findByActive(1);

		for (HrmsUserRoles dbm : dbms) {
			List<HrmsRoles> roles = new ArrayList<>();
			UserRolesResponse userRolesResponse = new UserRolesResponse();
			userRolesResponse.setActive(dbm.getActive());
			userRolesResponse.setApproved(dbm.getApproved());
			userRolesResponse.setId(dbm.getId());
			if (dbm.getRoleId() != 0 && hrmsRolesRepository.existsById(dbm.getRoleId())) {

				roles.add(hrmsRolesRepository.findById(dbm.getRoleId()).get());

			}
			userRolesResponse.setRoleId(dbm.getRoleId());
			if (dbm.getUserId() != 0 && hrmsUserRepository.existsById(dbm.getUserId()) && hrmsEmployeeRepository
					.existsByEmail(hrmsUserRepository.findById(dbm.getUserId()).get().getEmail())) {
				HrmsEmployee hrmsEmployee = hrmsEmployeeRepository
						.findByEmail(hrmsUserRepository.findById(dbm.getUserId()).get().getEmail());
				StringBuilder user = new StringBuilder();

				user.append(hrmsEmployee.getFirstName().trim());
				if (hrmsEmployee.getMiddleName() != null) {
					user.append(" " + hrmsEmployee.getMiddleName().trim());
				}
				user.append(" " + hrmsEmployee.getLastName().trim());

				userRolesResponse.setUser(user.toString());
			}
			userRolesResponse.setUserId(dbm.getUserId());

			userRolesResponselist.add(userRolesResponse);

		}

		return ResponseEntity.status(HttpStatus.OK).body(userRolesResponselist);
	}

	@Override
	public ResponseEntity<List<UserRolesResponse>> getUserRolesByEmpId(int empid) {
		List<UserRolesResponse> userRolesResponselist = new ArrayList<>();

		if (hrmsEmployeeRepository.existsById(empid)
				&& hrmsUserRepository.existsByEmail(hrmsEmployeeRepository.findById(empid).get().getEmail())) {

			Set<HrmsUserRoles> dbms = hrmsUserRolesRepository.findByUserId(hrmsUserRepository
					.findByEmail(hrmsEmployeeRepository.findById(empid).get().getEmail()).get().getId());

			for (HrmsUserRoles dbm : dbms) {
				List<HrmsRoles> roles = new ArrayList<>();
				UserRolesResponse userRolesResponse = new UserRolesResponse();
				userRolesResponse.setActive(dbm.getActive());
				userRolesResponse.setApproved(dbm.getApproved());
				userRolesResponse.setId(dbm.getId());
				if (dbm.getRoleId() != 0 && hrmsRolesRepository.existsById(dbm.getRoleId())) {

					roles.add(hrmsRolesRepository.findById(dbm.getRoleId()).get());
				}
				userRolesResponse.setRoleId(dbm.getRoleId());
				if (dbm.getUserId() != 0 && hrmsUserRepository.existsById(dbm.getUserId()) && hrmsEmployeeRepository
						.existsByEmail(hrmsUserRepository.findById(dbm.getUserId()).get().getEmail())) {
					HrmsEmployee hrmsEmployee = hrmsEmployeeRepository
							.findByEmail(hrmsUserRepository.findById(dbm.getUserId()).get().getEmail());
					StringBuilder user = new StringBuilder();

					user.append(hrmsEmployee.getFirstName().trim());
					if (hrmsEmployee.getMiddleName() != null) {
						user.append(" " + hrmsEmployee.getMiddleName().trim());
					}
					user.append(" " + hrmsEmployee.getLastName().trim());

					userRolesResponse.setUser(user.toString());
				}
				userRolesResponse.setUserId(dbm.getUserId());

				userRolesResponselist.add(userRolesResponse);

			}
		}
		return ResponseEntity.status(HttpStatus.OK).body(userRolesResponselist);
	}

	@Override
	public ResponseEntity<List<UserRolesResponse>> getUserRolesByUserId(int userid) {
		List<UserRolesResponse> userRolesResponselist = new ArrayList<>();

		if (hrmsUserRepository.existsById(userid) && hrmsUserRolesRepository.existsByUserIdAndActive(userid, 1)) {

			Set<HrmsUserRoles> dbms = hrmsUserRolesRepository.findByUserId(userid);

			for (HrmsUserRoles dbm : dbms) {
				List<HrmsRoles> roles = new ArrayList<>();
				UserRolesResponse userRolesResponse = new UserRolesResponse();
				userRolesResponse.setActive(dbm.getActive());
				userRolesResponse.setApproved(dbm.getApproved());
				userRolesResponse.setId(dbm.getId());
				if (dbm.getRoleId() != 0 && hrmsRolesRepository.existsById(dbm.getRoleId())) {

					roles.add(hrmsRolesRepository.findById(dbm.getRoleId()).get());
				}
				userRolesResponse.setRoleId(dbm.getRoleId());
				if (dbm.getUserId() != 0 && hrmsUserRepository.existsById(dbm.getUserId()) && hrmsEmployeeRepository
						.existsByEmail(hrmsUserRepository.findById(dbm.getUserId()).get().getEmail())) {
					HrmsEmployee hrmsEmployee = hrmsEmployeeRepository
							.findByEmail(hrmsUserRepository.findById(dbm.getUserId()).get().getEmail());
					StringBuilder user = new StringBuilder();

					user.append(hrmsEmployee.getFirstName().trim());
					if (hrmsEmployee.getMiddleName() != null) {
						user.append(" " + hrmsEmployee.getMiddleName().trim());
					}
					user.append(" " + hrmsEmployee.getLastName().trim());

					userRolesResponse.setUser(user.toString());
				}
				userRolesResponse.setUserId(dbm.getUserId());

				userRolesResponselist.add(userRolesResponse);

			}
		}
		return ResponseEntity.status(HttpStatus.OK).body(userRolesResponselist);
	}

	@Override
	public ResponseEntity<List<HrmsUserRoles>> savelist(HrmsUserRoles hrmsUserRoles, List<Integer> roles) {
		if (hrmsUserRepository.existsById(hrmsUserRoles.getUserId()) && hrmsRolesRepository.existsByIdIn(roles)) {

			if (hrmsUserRolesRepository.existsByUserIdAndRoleIdInAndActive(hrmsUserRoles.getUserId(), roles, 1)) {
				return ResponseEntity.status(HttpStatus.ALREADY_REPORTED).body(null);

			} else {

				List<HrmsUserRoles> hrmsUserRoleslist = new ArrayList<>();

				for (int roleid : roles) {
					if (hrmsRolesRepository.existsByIdAndActive(roleid, 1)) {
						UUID uuid = UUID.randomUUID();
						hrmsUserRoles.setUnique_id(uuid);
						hrmsUserRoles.setActive(1);
						hrmsUserRoles.setApproved(0);
						hrmsUserRoles.setRoleId(roleid);

						hrmsUserRoleslist.add(hrmsUserRoles);
					}
				}
				return ResponseEntity.status(HttpStatus.OK).body(hrmsUserRolesRepository.saveAll(hrmsUserRoleslist));

			}
		} else {
			return ResponseEntity.status(HttpStatus.PRECONDITION_FAILED).body(null);
		}
	}

}
