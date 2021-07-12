package com.Hrms.Employee.Service;

import java.util.List;
import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.Hrms.Employee.Entity.HrmsRelativeCategory;

@Service
public interface HrmsRelativeCategoryService {

	public ResponseEntity<HrmsRelativeCategory> addRelativeCategory(HrmsRelativeCategory HrmsRelativeCategory);

	public ResponseEntity<Optional<HrmsRelativeCategory>> getHrmsRelativeCategory(int id);

	public ResponseEntity<HrmsRelativeCategory> updateRelativeCategory(HrmsRelativeCategory hrmsRelativeCategory,
			int id);

	public ResponseEntity<?> deleteRelativeCategory(int id);

	public ResponseEntity<List<HrmsRelativeCategory>> listRelativeCategory();

}
