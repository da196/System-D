package com.Hrms.Employee.Service;

import java.util.List;
import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.Hrms.Employee.Entity.HrmsRefereeCategory;

@Service
public interface HrmsRefereeCategoryService {

	public ResponseEntity<HrmsRefereeCategory> addRefereeCategory(HrmsRefereeCategory hrmsRefereeCategory);

	public ResponseEntity<Optional<HrmsRefereeCategory>> getRefereeCategory(int id);

	public ResponseEntity<HrmsRefereeCategory> updateRefereeCategory(HrmsRefereeCategory hrmsRefereeCategory, int id);

	public ResponseEntity<?> deleteRefereeCategory(int id);

	public ResponseEntity<List<HrmsRefereeCategory>> listRefereeCategory();

}
