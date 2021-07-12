package com.Hrms.Employee.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.Hrms.Employee.DTO.DesignationResponse;
import com.Hrms.Employee.Entity.HrmsDesignation;
import com.Hrms.Employee.Repository.HrmsDesignationRepository;
import com.Hrms.Employee.Repository.HrmsEmployeeRepository;
import com.Hrms.Employee.Repository.HrmsOrganisationOfficeRepository;
import com.Hrms.Employee.Repository.HrmsOrginisationUnitRepository;

@Service
public class HrmsDesignationServiceImpl implements HrmsDesignationService {

	@Autowired
	private HrmsDesignationRepository hrmsDesignationRepository;

	@Autowired
	private HrmsEmployeeRepository hrmsEmployeeRepository;

	@Autowired
	private HrmsOrganisationOfficeRepository hrmsOrganisationOfficeRepository;

	@Autowired
	private HrmsOrginisationUnitRepository hrmsOrginisationUnitRepository;

	@Override
	public ResponseEntity<HrmsDesignation> save(HrmsDesignation hrmsDesignation) {
		UUID uuid = UUID.randomUUID();
		hrmsDesignation.setUnique_id(uuid);
		hrmsDesignation.setApproved(0);
		hrmsDesignation.setActive(1);

		if (hrmsDesignationRepository.existsByName(hrmsDesignation.getName())) {
			return ResponseEntity.status(HttpStatus.ALREADY_REPORTED).body(null);

		} else {

			return ResponseEntity.status(HttpStatus.OK).body(hrmsDesignationRepository.save(hrmsDesignation));
		}
	}

	@Override
	public ResponseEntity<Optional<HrmsDesignation>> viewHrmsDesignation(int id) {
		if (hrmsDesignationRepository.existsByIdAndActive(id, 1)) {

			return ResponseEntity.status(HttpStatus.OK).body(hrmsDesignationRepository.findById(id));
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
		}
	}

	@Override
	public ResponseEntity<HrmsDesignation> update(HrmsDesignation hrmsDesignation, int id) {
		LocalDateTime LocalTime = LocalDateTime.now();
		hrmsDesignation.setDate_updated(LocalTime);
		if (hrmsDesignationRepository.existsByIdAndActive(id, 1)) {
			return ResponseEntity.status(HttpStatus.OK).body(hrmsDesignationRepository.save(hrmsDesignation));
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(hrmsDesignation);
		}
	}

	@Override
	public ResponseEntity<?> deleteHrmsDesignation(int id) {
		if (hrmsDesignationRepository.existsByIdAndActive(id, 1)) {
			HrmsDesignation hrmsDesignation = hrmsDesignationRepository.findById(id).get();

			hrmsDesignation.setActive(0);
			hrmsDesignation.setDate_updated(LocalDateTime.now());
			return ResponseEntity.status(HttpStatus.OK).body(hrmsDesignationRepository.save(hrmsDesignation));
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
		}
	}

	@Override
	public ResponseEntity<List<HrmsDesignation>> listHrmsDesignation() {

		return ResponseEntity.status(HttpStatus.OK).body(hrmsDesignationRepository.findByActive(1));
	}

	@Override
	public ResponseEntity<List<HrmsDesignation>> listHrmsDesignationApprover() {
		List<HrmsDesignation> hrmsDesignationlist = new ArrayList<>();
		hrmsDesignationRepository.findByActive(1).forEach(dbm -> {

			if (hrmsEmployeeRepository.existsByDesignationIdAndIssupervisorAndEmploymentstatusid(dbm.getId(), 1, 2))
				hrmsDesignationlist.add(dbm);

		});
		return ResponseEntity.status(HttpStatus.OK).body(hrmsDesignationlist);
	}

	@Override
	public ResponseEntity<List<DesignationResponse>> listDesignationResponse() {
		List<DesignationResponse> designationResponselist = new ArrayList<>();

		hrmsDesignationRepository.findByActive(1).forEach(dbm -> {
			DesignationResponse designationResponse = new DesignationResponse();
			designationResponse.setAbbreviation(dbm.getAbbreviation());
			designationResponse.setActive(dbm.getActive());
			designationResponse.setApproved(dbm.getApproved());
			designationResponse.setCode(dbm.getCode());
			designationResponse.setDescription(dbm.getDescription());
			designationResponse.setId(dbm.getId());
			if (hrmsEmployeeRepository.existsByDesignationIdAndIssupervisorAndEmploymentstatusid(dbm.getId(), 1, 2)) {
				designationResponse.setIssupervisor(1);
			}
			designationResponse.setName(dbm.getName());

			designationResponselist.add(designationResponse);

		});
		return ResponseEntity.status(HttpStatus.OK).body(designationResponselist);
	}

	@Override
	public ResponseEntity<List<HrmsDesignation>> listHrmsDesignationSupervisorByDepartmentId(int departmentId) {
		if (hrmsOrginisationUnitRepository.existsByIdAndActive(departmentId, 1)) {
			List<HrmsDesignation> hrmsDesignationlist = new ArrayList<>();
			hrmsDesignationRepository.findByActive(1).forEach(dbm -> {

				if (hrmsEmployeeRepository.existsByDesignationIdAndIssupervisorAndEmploymentstatusidAndUnitId(
						dbm.getId(), 1, 2, departmentId))
					hrmsDesignationlist.add(dbm);

			});
			return ResponseEntity.status(HttpStatus.OK).body(hrmsDesignationlist);
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
		}
	}

}
