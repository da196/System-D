package com.Hrms.Employee.Service;

import java.util.List;
import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.Hrms.Employee.Entity.HrmsEducationInstCategory;

@Service
public interface HrmsEducationInstCategoryService {

	public ResponseEntity<HrmsEducationInstCategory> save(HrmsEducationInstCategory hrmsEducationInstCategory);

	public ResponseEntity<Optional<HrmsEducationInstCategory>> viewHrmsEducationInstCategory(int id);

	public ResponseEntity<HrmsEducationInstCategory> update(HrmsEducationInstCategory hrmsEducationInstCategory,
			int id);

	public ResponseEntity<?> deleteHrmsEducationInstCategory(int id);

	public ResponseEntity<List<HrmsEducationInstCategory>> listHrmsEducationInstCategory();

}
