package com.Hrms.Employee.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.Hrms.Employee.Entity.HrmsOrginisationUnit;
import com.Hrms.Employee.Repository.HrmsOrginisationUnitRepository;

@Service
public class HrmsOrginisationUnitServiceImpl implements HrmsOrginisationUnitService {

	@Autowired
	private HrmsOrginisationUnitRepository HrmsOrginisationUnitRepository;

	@Override
	public ResponseEntity<List<HrmsOrginisationUnit>> listHrmsOrginisationUnit() {
		Integer[] unittypeid = { 1, 10, 14, 33 };

		return ResponseEntity.status(HttpStatus.OK).body(HrmsOrginisationUnitRepository.findByActive(1));
	}

	@Override
	public ResponseEntity<List<HrmsOrginisationUnit>> listDepartment() {
		List<Integer> department = new ArrayList<>(Arrays.asList(14));

		return ResponseEntity.status(HttpStatus.OK).body(HrmsOrginisationUnitRepository.findAllDepartment(department));
	}

	@Override
	public ResponseEntity<List<HrmsOrginisationUnit>> listSections() {
		List<Integer> section = new ArrayList<>(Arrays.asList(32));

		return ResponseEntity.status(HttpStatus.OK).body(HrmsOrginisationUnitRepository.findAllDepartment(section));
	}

	@Override
	public ResponseEntity<List<HrmsOrginisationUnit>> listUnits() {
		List<Integer> department = new ArrayList<>(Arrays.asList(33));

		return ResponseEntity.status(HttpStatus.OK).body(HrmsOrginisationUnitRepository.findAllDepartment(department));
	}

	@Override
	public ResponseEntity<List<HrmsOrginisationUnit>> listDirectorgeneral() {
		List<Integer> department = new ArrayList<>(Arrays.asList(10));

		return ResponseEntity.status(HttpStatus.OK).body(HrmsOrginisationUnitRepository.findAllDepartment(department));
	}

	@Override
	public ResponseEntity<List<HrmsOrginisationUnit>> listChildUnitByParentId(int parentId) {

		List<HrmsOrginisationUnit> hrmsOrginisationUnitlist = new ArrayList<>();
		HrmsOrginisationUnit hrmsOrginisationUnit1 = new HrmsOrginisationUnit();
		hrmsOrginisationUnit1.setActive(1);
		hrmsOrginisationUnit1.setId(0);
		hrmsOrginisationUnit1.setName("None");
		hrmsOrginisationUnitlist.add(hrmsOrginisationUnit1);

		HrmsOrginisationUnitRepository.findByparentId(parentId).forEach(dbm -> {
			hrmsOrginisationUnitlist.add(dbm);

		});

		return ResponseEntity.status(HttpStatus.OK).body(hrmsOrginisationUnitlist);
	}

	@Override
	public ResponseEntity<List<HrmsOrginisationUnit>> listDepartmentsAndUnits() {
		List<Integer> department = new ArrayList<>(Arrays.asList(14));
		department.add(33); // units

		return ResponseEntity.status(HttpStatus.OK).body(HrmsOrginisationUnitRepository.findAllDepartment(department));
	}

}
