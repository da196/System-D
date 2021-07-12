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

import com.Hrms.Employee.Entity.HrmsSocialSecurityScheme;
import com.Hrms.Employee.Service.HrmsSocialSecuritySchemeService;

@RestController
@RequestMapping("/v1/socialSecurityScheme")
public class HrmsSocialSecuritySchemeController {

	@Autowired
	private HrmsSocialSecuritySchemeService hrmsSocialSecuritySchemeService;

	@PostMapping(value = "/addsocialSecurityScheme")
	public ResponseEntity<HrmsSocialSecurityScheme> addSocialSecurityScheme(
			@RequestBody HrmsSocialSecurityScheme hrmsSocialSecurityScheme) {
		return hrmsSocialSecuritySchemeService.save(hrmsSocialSecurityScheme);

	}

	@GetMapping(value = "/getSocialSecurityScheme/{id}")
	public ResponseEntity<Optional<HrmsSocialSecurityScheme>> getSocialSecurityScheme(@PathVariable("id") int id) {
		return hrmsSocialSecuritySchemeService.viewHrmsSocialSecurityScheme(id);
	}

	@PutMapping(value = "/updateSocialSecurityScheme/{id}")
	public ResponseEntity<HrmsSocialSecurityScheme> updateSocialSecurityScheme(
			@RequestBody HrmsSocialSecurityScheme hrmsSocialSecurityScheme, @PathVariable("id") int id) {
		return hrmsSocialSecuritySchemeService.update(hrmsSocialSecurityScheme, id);

	}

	@DeleteMapping(value = "/deleteSocialSecurityScheme/{id}")
	public ResponseEntity<?> deleteSocialSecurityScheme(@PathVariable("id") int id) {
		return hrmsSocialSecuritySchemeService.deleteHrmsSocialSecurityScheme(id);
	}

	@GetMapping(value = "/listSocialSecurityScheme")
	public ResponseEntity<List<HrmsSocialSecurityScheme>> listSocialSecurityScheme() {
		return hrmsSocialSecuritySchemeService.listHrmsSocialSecurityScheme();

	}

}
