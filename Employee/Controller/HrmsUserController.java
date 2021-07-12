package com.Hrms.Employee.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.Hrms.Employee.DTO.UserResponse;
import com.Hrms.Employee.Entity.HrmsUser;
import com.Hrms.Employee.Repository.HrmsUserRepository;
import com.Hrms.Employee.Service.HrmsUserService;

@RestController
@RequestMapping("/v1/user")
public class HrmsUserController {
	@Autowired
	private HrmsUserService hrmsUserService;

	@Autowired
	private HrmsUserRepository userrepository;

	@PostMapping(value = "/addUser")
	public ResponseEntity<HrmsUser> addUser(@RequestBody HrmsUser hrmsUser) {
		return hrmsUserService.save(hrmsUser);

	}

	@GetMapping(value = "/getUser/{id}")
	public ResponseEntity<UserResponse> getUser(@PathVariable("id") int id) {
		return hrmsUserService.getUserById(id);

	}

	@PutMapping(value = "/updateUser/{id}")
	public ResponseEntity<HrmsUser> updateUser(@RequestBody HrmsUser hrmsUser, @PathVariable("id") int id) {

		return hrmsUserService.update(hrmsUser, id);

	}

	@DeleteMapping(value = "/deleteUser/{id}")
	public ResponseEntity<?> deleteUser(@PathVariable("id") int id) {

		return hrmsUserService.deleteHrmsUser(id);

	}

	@GetMapping(value = "/getAllUser")
	public ResponseEntity<List<UserResponse>> getAllUser() {
		return hrmsUserService.getAllUsers();
	}

}
