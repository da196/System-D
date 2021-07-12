package com.Hrms.Employee.Controller;

import java.util.List;
import java.util.Optional;

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

import com.Hrms.Employee.DTO.UserRolesResponse;
import com.Hrms.Employee.Entity.HrmsUserRoles;
import com.Hrms.Employee.Service.HrmsUserRolesService;

@RestController
@RequestMapping("/v1/userRoles")
public class HrmsUserRolesController {
	@Autowired
	private HrmsUserRolesService hrmsUserRolesService;

	@PostMapping(value = "/addUserRoles")
	public ResponseEntity<HrmsUserRoles> addUserRoles(@RequestBody HrmsUserRoles hrmsUserRoles) {

		return hrmsUserRolesService.save(hrmsUserRoles);
	}

	@PostMapping(value = "/addUserRoles/{roles}")
	public ResponseEntity<List<HrmsUserRoles>> savelist(@RequestBody HrmsUserRoles hrmsUserRoles,
			@PathVariable("roles") List<Integer> roles) {

		return hrmsUserRolesService.savelist(hrmsUserRoles, roles);

	}

	@GetMapping(value = "/getUserRolesById/{id}")
	public ResponseEntity<UserRolesResponse> getUserRolesById(@PathVariable("id") int id) {
		return hrmsUserRolesService.getUserRolesById(id);

	}

	@GetMapping(value = "/getAllUserRoles")
	public ResponseEntity<List<UserRolesResponse>> getAllUserRoles() {
		return hrmsUserRolesService.getAllUserRoles();

	}

	@GetMapping(value = "/getUserRolesByEmpId/{empid}")
	public ResponseEntity<List<UserRolesResponse>> getUserRolesByEmpId(@PathVariable("empid") int empid) {

		return hrmsUserRolesService.getUserRolesByEmpId(empid);
	}

	@GetMapping(value = "/getUserRolesByUserId/{userid}")
	public ResponseEntity<List<UserRolesResponse>> getUserRolesByUserId(@PathVariable("userid") int userid) {
		return hrmsUserRolesService.getUserRolesByUserId(userid);
	}

	@GetMapping(value = "/getUserRoles/{id}")
	public ResponseEntity<Optional<HrmsUserRoles>> getUserRoles(@PathVariable("id") int id) {

		return hrmsUserRolesService.viewHrmsUserRoles(id);

	}

	@PutMapping(value = "/updateUserRoles/{id}")
	public ResponseEntity<HrmsUserRoles> updateUserRoles(@RequestBody HrmsUserRoles hrmsUserRoles,
			@PathVariable("id") int id) {

		return hrmsUserRolesService.update(hrmsUserRoles, id);

	}

	@DeleteMapping(value = "/deleteUserRoles/{id}")
	public ResponseEntity<?> deleteUserRoles(@PathVariable("id") int id) {

		return hrmsUserRolesService.deleteHrmsUserRoles(id);

	}

	@GetMapping(value = "/listUserRoles")
	public ResponseEntity<List<HrmsUserRoles>> listUserRoles() {

		return hrmsUserRolesService.listHrmsUserRoles();

	}

}
