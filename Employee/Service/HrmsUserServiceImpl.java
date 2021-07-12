package com.Hrms.Employee.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.Hrms.Employee.DTO.UserResponse;
import com.Hrms.Employee.Entity.HrmsEmployee;
import com.Hrms.Employee.Entity.HrmsUser;
import com.Hrms.Employee.Repository.HrmsEmployeeRepository;
import com.Hrms.Employee.Repository.HrmsUserRepository;

@Service
public class HrmsUserServiceImpl implements HrmsUserService {

	@Autowired
	HrmsUserRepository hrmsUserRepository;

	@Autowired
	private HrmsEmployeeRepository hrmsEmployeeRepository;

	@Override
	public ResponseEntity<HrmsUser> save(HrmsUser hrmsUser) {
		UUID uuid = UUID.randomUUID();
		hrmsUser.setUnique_id(uuid);
		hrmsUser.setActive(1);
		hrmsUser.setLocked(1);
		hrmsUser.setUser_hash(hrmsUser.getEmail().concat("UsErHaSh"));
		hrmsUser.setApproved(0);

		return ResponseEntity.status(HttpStatus.OK).body(hrmsUserRepository.save(hrmsUser));
	}

	@Override
	public ResponseEntity<UserResponse> getUserById(int id) {

		UserResponse userResponse = new UserResponse();
		if (hrmsUserRepository.existsByIdAndActive(id, 1)) {
			HrmsUser dbm = hrmsUserRepository.findById(id).get();

			userResponse.setActive(dbm.getActive());
			userResponse.setApproved(dbm.getApproved());
			userResponse.setEmail(dbm.getEmail());
			userResponse.setId(dbm.getId());
			userResponse.setUser_hash(dbm.getUser_hash());

			userResponse.setLocked(dbm.getLocked());

			if (dbm.getEmail() != null && hrmsEmployeeRepository.existsByEmail(dbm.getEmail())) {
				StringBuilder userfullname = new StringBuilder();

				HrmsEmployee hrmsEmployee = hrmsEmployeeRepository.findByEmail(dbm.getEmail());

				userfullname.append(hrmsEmployee.getFirstName().trim());
				if (hrmsEmployee.getMiddleName() != null) {
					userfullname.append(" " + hrmsEmployee.getMiddleName().trim());
				}
				userfullname.append(" " + hrmsEmployee.getLastName().trim());
				userResponse.setUserfullname(userfullname.toString());
			}

		}

		return ResponseEntity.status(HttpStatus.OK).body(userResponse);

	}

	@Override
	public ResponseEntity<HrmsUser> update(HrmsUser hrmsUser, int id) {
		if (hrmsUserRepository.existsByIdAndActive(id, 1)) {
			HrmsUser hrmsUser1 = hrmsUserRepository.findById(id).get();
			LocalDateTime LocalTime = LocalDateTime.now();
			hrmsUser.setActive(1);
			hrmsUser.setApproved(0);
			hrmsUser.setLocked(1);
			hrmsUser.setDate_updated(LocalTime);

			if (hrmsUser1.getUnique_id() != null) {
				hrmsUser.setUnique_id(hrmsUser1.getUnique_id());
			}

			if (hrmsUser1.getDate_created() != null) {
				hrmsUser.setDate_created(hrmsUser1.getDate_created());
			}

			return ResponseEntity.status(HttpStatus.OK).body(hrmsUserRepository.save(hrmsUser));
		} else {

			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(hrmsUser);
		}
	}

	@Override
	public ResponseEntity<?> deleteHrmsUser(int id) {
		if (hrmsUserRepository.existsByIdAndActive(id, 1)) {
			HrmsUser hrmsUser = hrmsUserRepository.findById(id).get();

			hrmsUser.setActive(0);
			hrmsUser.setDate_updated(LocalDateTime.now());

			return ResponseEntity.status(HttpStatus.OK).body(hrmsUserRepository.save(hrmsUser));
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
		}

	}

	@Override
	public ResponseEntity<List<UserResponse>> getAllUsers() {
		List<UserResponse> userResponselist = new ArrayList<>();

		List<HrmsUser> dbms = hrmsUserRepository.findByActive(1);
		for (HrmsUser dbm : dbms) {
			UserResponse userResponse = new UserResponse();
			userResponse.setActive(dbm.getActive());
			userResponse.setApproved(dbm.getApproved());
			userResponse.setEmail(dbm.getEmail());
			userResponse.setId(dbm.getId());
			userResponse.setUser_hash(dbm.getUser_hash());

			userResponse.setLocked(dbm.getLocked());

			if (dbm.getEmail() != null && hrmsEmployeeRepository.existsByEmail(dbm.getEmail())) {
				StringBuilder userfullname = new StringBuilder();

				HrmsEmployee hrmsEmployee = hrmsEmployeeRepository.findByEmail(dbm.getEmail());

				userfullname.append(hrmsEmployee.getFirstName().trim());
				if (hrmsEmployee.getMiddleName() != null) {
					userfullname.append(" " + hrmsEmployee.getMiddleName().trim());
				}
				userfullname.append(" " + hrmsEmployee.getLastName().trim());
				userResponse.setUserfullname(userfullname.toString());
			}

			userResponselist.add(userResponse);
		}

		return ResponseEntity.status(HttpStatus.OK).body(userResponselist);
	}

}
