package com.Hrms.Employee.Service;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.Hrms.Employee.DTO.EmployeeSocialSecuritySchemeResponse;
import com.Hrms.Employee.Entity.HrmsEmployee;
import com.Hrms.Employee.Entity.HrmsEmployeeSocialSecurityScheme;
import com.Hrms.Employee.Repository.HrmsEmployeeRepository;
import com.Hrms.Employee.Repository.HrmsEmployeeSocialSecuritySchemeRepositoty;
import com.Hrms.Payroll.Repository.HrmsSocialSecuritySchemeServiceProviderRepository;

@Service
public class HrmsEmployeeSocialSecuritySchemeServiceImpl implements HrmsEmployeeSocialSecuritySchemeService {

	@Autowired
	private HrmsEmployeeSocialSecuritySchemeRepositoty hrmsEmployeeSocialSecuritySchemeRepositoty;
	@Autowired
	private HrmsEmployeeRepository hrmsEmployeeRepository;

	@Autowired
	private HrmsSocialSecuritySchemeServiceProviderRepository hrmsSocialSecuritySchemeServiceProviderRepository;

	@Override
	public ResponseEntity<HrmsEmployeeSocialSecurityScheme> addEmployeeSocialSecurityScheme(
			HrmsEmployeeSocialSecurityScheme hrmsEmployeeSocialSecurityScheme) {

		if (// !hrmsEmployeeSocialSecuritySchemeRepositoty.existsByEmployeeidAndServiceprovideridAndActive(
			// hrmsEmployeeSocialSecurityScheme.getEmployeeid(),
			// hrmsEmployeeSocialSecurityScheme.getServiceproviderid(), 1)
			// &&
		!hrmsEmployeeSocialSecuritySchemeRepositoty
				.existsByEmployeeidAndActive(hrmsEmployeeSocialSecurityScheme.getEmployeeid(), 1)) {
			if (hrmsSocialSecuritySchemeServiceProviderRepository
					.existsByIdAndActive(hrmsEmployeeSocialSecurityScheme.getServiceproviderid(), 1)) {
				hrmsEmployeeSocialSecurityScheme.setUnique_id(UUID.randomUUID());
				hrmsEmployeeSocialSecurityScheme.setActive(1);
				hrmsEmployeeSocialSecurityScheme.setApproved(0);

				return ResponseEntity.status(HttpStatus.OK)
						.body(hrmsEmployeeSocialSecuritySchemeRepositoty.save(hrmsEmployeeSocialSecurityScheme));
			} else {
				return ResponseEntity.status(HttpStatus.PRECONDITION_FAILED).body(hrmsEmployeeSocialSecurityScheme);
			}
		} else {

			return ResponseEntity.status(HttpStatus.ALREADY_REPORTED).body(hrmsEmployeeSocialSecurityScheme);
		}
	}

	@Override
	public ResponseEntity<EmployeeSocialSecuritySchemeResponse> getEmployeeSocialSecuritySchemeById(int id) {
		if (hrmsEmployeeSocialSecuritySchemeRepositoty.existsByIdAndActive(id, 1)) {
			String pattern = "yyyy-MM-dd";
			SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);

			HrmsEmployeeSocialSecurityScheme dbm = hrmsEmployeeSocialSecuritySchemeRepositoty.findById(id).get();
			EmployeeSocialSecuritySchemeResponse employeeSocialSecuritySchemeResponse = new EmployeeSocialSecuritySchemeResponse();
			employeeSocialSecuritySchemeResponse.setActive(dbm.getActive());
			employeeSocialSecuritySchemeResponse.setApproved(dbm.getApproved());
			if (dbm.getDatend() != null) {
				employeeSocialSecuritySchemeResponse.setDatend(simpleDateFormat.format(dbm.getDatend()));
			}
			if (dbm.getDatestart() != null) {
				employeeSocialSecuritySchemeResponse.setDatestart(simpleDateFormat.format(dbm.getDatestart()));
			}
			employeeSocialSecuritySchemeResponse.setDescriptionend(dbm.getDescriptionend());
			employeeSocialSecuritySchemeResponse.setDescriptionstart(dbm.getDescriptionstart());
			employeeSocialSecuritySchemeResponse.setEmployeeid(dbm.getEmployeeid());

			StringBuilder fullName = new StringBuilder();

			HrmsEmployee hrmsEmployee = hrmsEmployeeRepository.findById(dbm.getEmployeeid()).get();

			fullName.append(hrmsEmployee.getFirstName().trim());
			if (hrmsEmployee.getMiddleName() != null) {
				fullName.append(" " + hrmsEmployee.getMiddleName().trim());
			}
			fullName.append(" " + hrmsEmployee.getLastName().trim());

			employeeSocialSecuritySchemeResponse.setFullName(fullName.toString());
			employeeSocialSecuritySchemeResponse.setId(dbm.getId());
			if (dbm.getServiceproviderid() != 0
					&& hrmsSocialSecuritySchemeServiceProviderRepository.existsById(dbm.getServiceproviderid())) {
				employeeSocialSecuritySchemeResponse
						.setServiceprovider(hrmsSocialSecuritySchemeServiceProviderRepository
								.findById(dbm.getServiceproviderid()).get().getName());
			}
			employeeSocialSecuritySchemeResponse.setServiceproviderid(dbm.getServiceproviderid());
			employeeSocialSecuritySchemeResponse.setSocialsecurityschemenumber(dbm.getSocialsecurityschemenumber());

			return ResponseEntity.status(HttpStatus.OK).body(employeeSocialSecuritySchemeResponse);
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
		}
	}

	@Override
	public ResponseEntity<HrmsEmployeeSocialSecurityScheme> updateEmployeeSocialSecurityScheme(
			HrmsEmployeeSocialSecurityScheme hrmsEmployeeSocialSecurityScheme, int id) {
		LocalDateTime LocalTime = LocalDateTime.now();
		hrmsEmployeeSocialSecurityScheme.setDate_updated(LocalTime);
		hrmsEmployeeSocialSecurityScheme.setApproved(0);
		if (hrmsEmployeeSocialSecuritySchemeRepositoty.existsById(id)) {
			if (hrmsSocialSecuritySchemeServiceProviderRepository
					.existsByIdAndActive(hrmsEmployeeSocialSecurityScheme.getServiceproviderid(), 1)) {

				HrmsEmployeeSocialSecurityScheme hrmsEmployeeSocialSecurityScheme1 = hrmsEmployeeSocialSecuritySchemeRepositoty
						.findById(id).get();
				if (hrmsEmployeeSocialSecurityScheme1.getDate_created() != null) {
					hrmsEmployeeSocialSecurityScheme
							.setDate_created(hrmsEmployeeSocialSecurityScheme1.getDate_created());
				}
				if (hrmsEmployeeSocialSecurityScheme1.getUnique_id() != null) {
					hrmsEmployeeSocialSecurityScheme.setUnique_id(hrmsEmployeeSocialSecurityScheme1.getUnique_id());
				}
				return ResponseEntity.status(HttpStatus.OK)
						.body(hrmsEmployeeSocialSecuritySchemeRepositoty.save(hrmsEmployeeSocialSecurityScheme));

			} else {
				return ResponseEntity.status(HttpStatus.PRECONDITION_FAILED).body(hrmsEmployeeSocialSecurityScheme);
			}

		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(hrmsEmployeeSocialSecurityScheme);
		}
	}

	@Override
	public ResponseEntity<?> deleteEmployeeSocialSecurityScheme(int id) {
		if (hrmsEmployeeSocialSecuritySchemeRepositoty.existsById(id)) {
			HrmsEmployeeSocialSecurityScheme hrmsEmployeeSocialSecurityScheme = hrmsEmployeeSocialSecuritySchemeRepositoty
					.findById(id).get();
			hrmsEmployeeSocialSecurityScheme.setActive(0);
			hrmsEmployeeSocialSecurityScheme.setDate_updated(LocalDateTime.now());
			return ResponseEntity.status(HttpStatus.OK)
					.body(hrmsEmployeeSocialSecuritySchemeRepositoty.save(hrmsEmployeeSocialSecurityScheme));
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
		}
	}

	@Override
	public ResponseEntity<List<EmployeeSocialSecuritySchemeResponse>> listEmployeeSocialSecurityScheme() {
		String pattern = "yyyy-MM-dd";
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);

		List<EmployeeSocialSecuritySchemeResponse> employeeSocialSecuritySchemeResponselist = new ArrayList<>();
		List<HrmsEmployeeSocialSecurityScheme> dbms = hrmsEmployeeSocialSecuritySchemeRepositoty
				.findByActiveOrderByIdDesc(1);

		dbms.forEach(dbm -> {

			EmployeeSocialSecuritySchemeResponse employeeSocialSecuritySchemeResponse = new EmployeeSocialSecuritySchemeResponse();
			employeeSocialSecuritySchemeResponse.setActive(dbm.getActive());
			employeeSocialSecuritySchemeResponse.setApproved(dbm.getApproved());
			if (dbm.getDatend() != null) {
				employeeSocialSecuritySchemeResponse.setDatend(simpleDateFormat.format(dbm.getDatend()));
			}
			if (dbm.getDatestart() != null) {
				employeeSocialSecuritySchemeResponse.setDatestart(simpleDateFormat.format(dbm.getDatestart()));
			}
			employeeSocialSecuritySchemeResponse.setDescriptionend(dbm.getDescriptionend());
			employeeSocialSecuritySchemeResponse.setDescriptionstart(dbm.getDescriptionstart());
			employeeSocialSecuritySchemeResponse.setEmployeeid(dbm.getEmployeeid());

			StringBuilder fullName = new StringBuilder();

			HrmsEmployee hrmsEmployee = hrmsEmployeeRepository.findById(dbm.getEmployeeid()).get();

			fullName.append(hrmsEmployee.getFirstName().trim());
			if (hrmsEmployee.getMiddleName() != null) {
				fullName.append(" " + hrmsEmployee.getMiddleName().trim());
			}
			fullName.append(" " + hrmsEmployee.getLastName().trim());

			employeeSocialSecuritySchemeResponse.setFullName(fullName.toString());
			employeeSocialSecuritySchemeResponse.setId(dbm.getId());
			if (dbm.getServiceproviderid() != 0
					&& hrmsSocialSecuritySchemeServiceProviderRepository.existsById(dbm.getServiceproviderid())) {
				employeeSocialSecuritySchemeResponse
						.setServiceprovider(hrmsSocialSecuritySchemeServiceProviderRepository
								.findById(dbm.getServiceproviderid()).get().getName());
			}
			employeeSocialSecuritySchemeResponse.setServiceproviderid(dbm.getServiceproviderid());
			employeeSocialSecuritySchemeResponse.setSocialsecurityschemenumber(dbm.getSocialsecurityschemenumber());
			employeeSocialSecuritySchemeResponselist.add(employeeSocialSecuritySchemeResponse);

		});

		return ResponseEntity.status(HttpStatus.OK).body(employeeSocialSecuritySchemeResponselist);
	}

	@Override
	public ResponseEntity<List<EmployeeSocialSecuritySchemeResponse>> getEmployeeSocialSecuritySchemeByEmpId(
			int EmpId) {
		if (hrmsEmployeeSocialSecuritySchemeRepositoty.existsByIdAndActive(EmpId, 1)) {
			String pattern = "yyyy-MM-dd";
			SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);

			List<EmployeeSocialSecuritySchemeResponse> employeeSocialSecuritySchemeResponselist = new ArrayList<>();
			List<HrmsEmployeeSocialSecurityScheme> dbms = hrmsEmployeeSocialSecuritySchemeRepositoty
					.findByEmployeeidAndActiveOrderByIdDesc(EmpId, 1);

			dbms.forEach(dbm -> {

				EmployeeSocialSecuritySchemeResponse employeeSocialSecuritySchemeResponse = new EmployeeSocialSecuritySchemeResponse();
				employeeSocialSecuritySchemeResponse.setActive(dbm.getActive());
				employeeSocialSecuritySchemeResponse.setApproved(dbm.getApproved());
				if (dbm.getDatend() != null) {
					employeeSocialSecuritySchemeResponse.setDatend(simpleDateFormat.format(dbm.getDatend()));
				}
				if (dbm.getDatestart() != null) {
					employeeSocialSecuritySchemeResponse.setDatestart(simpleDateFormat.format(dbm.getDatestart()));
				}
				employeeSocialSecuritySchemeResponse.setDescriptionend(dbm.getDescriptionend());
				employeeSocialSecuritySchemeResponse.setDescriptionstart(dbm.getDescriptionstart());
				employeeSocialSecuritySchemeResponse.setEmployeeid(dbm.getEmployeeid());

				StringBuilder fullName = new StringBuilder();

				HrmsEmployee hrmsEmployee = hrmsEmployeeRepository.findById(dbm.getEmployeeid()).get();

				fullName.append(hrmsEmployee.getFirstName().trim());
				fullName.append(" " + hrmsEmployee.getMiddleName().trim());
				fullName.append(" " + hrmsEmployee.getLastName().trim());

				employeeSocialSecuritySchemeResponse.setFullName(fullName.toString());
				employeeSocialSecuritySchemeResponse.setId(dbm.getId());
				if (dbm.getServiceproviderid() != 0
						&& hrmsSocialSecuritySchemeServiceProviderRepository.existsById(dbm.getServiceproviderid())) {
					employeeSocialSecuritySchemeResponse
							.setServiceprovider(hrmsSocialSecuritySchemeServiceProviderRepository
									.findById(dbm.getServiceproviderid()).get().getName());
				}
				employeeSocialSecuritySchemeResponse.setServiceproviderid(dbm.getServiceproviderid());
				employeeSocialSecuritySchemeResponse.setSocialsecurityschemenumber(dbm.getSocialsecurityschemenumber());
				employeeSocialSecuritySchemeResponselist.add(employeeSocialSecuritySchemeResponse);

			});

			return ResponseEntity.status(HttpStatus.OK).body(employeeSocialSecuritySchemeResponselist);
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
		}
	}

	@Override
	public ResponseEntity<?> verifyIfEmployeeHasPension(int EmpId) {
		if (hrmsEmployeeSocialSecuritySchemeRepositoty.existsByEmployeeidAndActive(EmpId, 1)) {

			return ResponseEntity.status(HttpStatus.ALREADY_REPORTED).body(0);
		} else {
			return ResponseEntity.status(HttpStatus.OK).body(1);
		}
	}

}
