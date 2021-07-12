package com.Hrms.Employee.Service;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.Hrms.Employee.Entity.HrmsOrginisationUnit;

@Service
public interface HrmsOrginisationUnitService {

	public ResponseEntity<List<HrmsOrginisationUnit>> listHrmsOrginisationUnit();

	public ResponseEntity<List<HrmsOrginisationUnit>> listDepartment();

	public ResponseEntity<List<HrmsOrginisationUnit>> listSections();

	public ResponseEntity<List<HrmsOrginisationUnit>> listUnits();

	public ResponseEntity<List<HrmsOrginisationUnit>> listDirectorgeneral();

	public ResponseEntity<List<HrmsOrginisationUnit>> listChildUnitByParentId(int parentId);

	public ResponseEntity<List<HrmsOrginisationUnit>> listDepartmentsAndUnits();
}
