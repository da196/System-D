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

import com.Hrms.Employee.Entity.HrmsCertificationCategory;
import com.Hrms.Employee.Service.HrmsCertificationCategoryService;

@RestController
@RequestMapping("v1/certificationCategory")
public class HrmsCertificationCategoryController {

	@Autowired
	private HrmsCertificationCategoryService hrmsCertificationCategoryService;

	@GetMapping(value = "/getAllCertificationCategory")
	public ResponseEntity<List<HrmsCertificationCategory>> listCertificationCategory() {
		return hrmsCertificationCategoryService.listCertificationCategory();

	}

	@PostMapping(value = "/addCertificationCategory")
	public ResponseEntity<HrmsCertificationCategory> addCertificationCategory(
			@RequestBody HrmsCertificationCategory hrmsCertificationCategory) {
		return hrmsCertificationCategoryService.addCertificationCategory(hrmsCertificationCategory);

	}

	@PutMapping(value = "/updateCertificationCategory/{id}")
	public ResponseEntity<HrmsCertificationCategory> updateCertificationCategory(
			@RequestBody HrmsCertificationCategory hrmsCertificationCategory, @PathVariable("id") int id) {
		return hrmsCertificationCategoryService.updateCertificationCategory(hrmsCertificationCategory, id);

	}

	@DeleteMapping(value = "/deleteCertificationCategory/{id}")
	public ResponseEntity<?> deleteCertificationCategory(@PathVariable("id") int id) {

		return hrmsCertificationCategoryService.deleteCertificationCategory(id);

	}

	@GetMapping(value = "/getCertificationCategoryById/{id}")
	public ResponseEntity<HrmsCertificationCategory> getCertificationCategoryById(@PathVariable("id") int id) {
		return hrmsCertificationCategoryService.getCertificationCategoryById(id);
	}

}
