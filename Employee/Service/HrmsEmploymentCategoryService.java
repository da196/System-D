package com.Hrms.Employee.Service;

import java.util.List;
import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.Hrms.Employee.Entity.HrmsEmploymentCategory;

@Service
public interface HrmsEmploymentCategoryService {

	public ResponseEntity<HrmsEmploymentCategory> save(HrmsEmploymentCategory hrmsEmploymentCategory);

	public ResponseEntity<Optional<HrmsEmploymentCategory>> viewHrmsEmploymentCategory(int id);

	public ResponseEntity<HrmsEmploymentCategory> update(HrmsEmploymentCategory hrmsEmploymentCategory, int id);

	public ResponseEntity<?> deleteHrmsEmploymentCategory(int id);

	public ResponseEntity<List<HrmsEmploymentCategory>> listHrmsEmploymentCategory();

}
