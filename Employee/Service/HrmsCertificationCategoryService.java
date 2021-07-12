package com.Hrms.Employee.Service;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.Hrms.Employee.Entity.HrmsCertificationCategory;

@Service
public interface HrmsCertificationCategoryService {

	public ResponseEntity<List<HrmsCertificationCategory>> listCertificationCategory();

	public ResponseEntity<HrmsCertificationCategory> addCertificationCategory(
			HrmsCertificationCategory hrmsCertificationCategory);

	public ResponseEntity<HrmsCertificationCategory> updateCertificationCategory(
			HrmsCertificationCategory hrmsCertificationCategory, int id);

	public ResponseEntity<?> deleteCertificationCategory(int id);

	public ResponseEntity<HrmsCertificationCategory> getCertificationCategoryById(int id);

}
