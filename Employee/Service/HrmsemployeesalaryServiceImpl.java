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

import com.Hrms.Employee.DTO.employeesalaryResponse;
import com.Hrms.Employee.Entity.HrmsEmployee;
import com.Hrms.Employee.Entity.Hrmsemployeesalary;
import com.Hrms.Employee.Repository.HrmsCurrencyRepository;
import com.Hrms.Employee.Repository.HrmsEmployeeRepository;
import com.Hrms.Employee.Repository.HrmsSalaryScaleRepository;
import com.Hrms.Employee.Repository.HrmsSalaryscalenotchRepository;
import com.Hrms.Employee.Repository.HrmsSalarystructureRepository;
import com.Hrms.Employee.Repository.HrmsemployeesalaryRepository;

@Service
public class HrmsemployeesalaryServiceImpl implements HrmsemployeesalaryService {

	@Autowired
	private HrmsemployeesalaryRepository hrmsemployeesalaryRepository;

	@Autowired
	private HrmsEmployeeRepository hrmsEmployeeRepository;

	@Autowired
	private HrmsSalarystructureRepository hrmsSalarystructureRepository;

	@Autowired
	private HrmsSalaryScaleRepository hrmsSalaryScaleRepository;

	@Autowired
	private HrmsSalaryscalenotchRepository hrmsSalaryscalenotchRepository;

	@Autowired
	private HrmsCurrencyRepository hrmsCurrencyRepository;

	@Override
	public ResponseEntity<Hrmsemployeesalary> addEmployeesalary(Hrmsemployeesalary hrmsemployeesalary) {
		if (hrmsemployeesalaryRepository.existsByEmployeeidAndActive(hrmsemployeesalary.getEmployeeid(), 1)) {
			return ResponseEntity.status(HttpStatus.ALREADY_REPORTED).body(hrmsemployeesalary);

		} else {

			if (hrmsEmployeeRepository.existsByIdAndActive(hrmsemployeesalary.getEmployeeid(), 1)
					&& hrmsSalarystructureRepository.existsByIdAndActive(hrmsemployeesalary.getSalarystructureId(),
							1)) {

				UUID uuid = UUID.randomUUID();
				hrmsemployeesalary.setUnique_id(uuid);
				LocalDateTime LocalTime = LocalDateTime.now();
				hrmsemployeesalary.setDateAssigned(LocalTime);
				hrmsemployeesalary.setApproved(0);
				hrmsemployeesalary.setActive(1);
				return ResponseEntity.status(HttpStatus.OK).body(hrmsemployeesalaryRepository.save(hrmsemployeesalary));
			} else {
				return ResponseEntity.status(HttpStatus.PRECONDITION_FAILED).body(hrmsemployeesalary);
			}
		}
	}

	@Override
	public ResponseEntity<Optional<Hrmsemployeesalary>> viewEmployeesalary(int id) {

		if (hrmsemployeesalaryRepository.existsById(id)) {

			return ResponseEntity.status(HttpStatus.OK).body(hrmsemployeesalaryRepository.findById(id));
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
		}
	}

	@Override
	public ResponseEntity<Hrmsemployeesalary> updateEmployeesalary(Hrmsemployeesalary hrmsemployeesalary, int id) {

		hrmsemployeesalary.setDate_created(LocalDateTime.now());
		hrmsemployeesalary.setApproved(0);
		hrmsemployeesalary.setId(0);

		if (hrmsemployeesalaryRepository.existsByIdAndActive(id, 1)) {
			if (hrmsEmployeeRepository.existsByIdAndActive(hrmsemployeesalary.getEmployeeid(), 1)
					&& hrmsSalarystructureRepository.existsByIdAndActive(hrmsemployeesalary.getSalarystructureId(),
							1)) {

				Hrmsemployeesalary hrmssalary = hrmsemployeesalaryRepository.findByIdAndActive(id, 1);
				hrmssalary.setActive(2);
				hrmssalary.setDate_updated(LocalDateTime.now());// deactivate previous active salary for employee with
																// this
																// id

				hrmsemployeesalary.setActive(1);
				hrmsemployeesalary.setId(0);
				hrmsemployeesalary.setUnique_id(UUID.randomUUID());
				hrmsemployeesalary.setApproved(0);

				hrmsemployeesalaryRepository.save(hrmssalary);// save changes of status to 0 ,inactive

				return ResponseEntity.status(HttpStatus.OK).body(hrmsemployeesalaryRepository.save(hrmsemployeesalary));
			} else {
				return ResponseEntity.status(HttpStatus.PRECONDITION_FAILED).body(hrmsemployeesalary);
			}
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(hrmsemployeesalary);
		}
	}

	@Override
	public ResponseEntity<?> deleteHrmsemployeesalary(int id) {

		if (hrmsemployeesalaryRepository.existsById(id)) {
			Hrmsemployeesalary hrmsemployeesalary = hrmsemployeesalaryRepository.findById(id).get();
			hrmsemployeesalary.setActive(0);
			hrmsemployeesalary.setDate_updated(LocalDateTime.now());

			return ResponseEntity.status(HttpStatus.OK).body(hrmsemployeesalaryRepository.save(hrmsemployeesalary));
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
		}
	}

	@Override
	public ResponseEntity<List<Hrmsemployeesalary>> listHrmsemployeesalary() {

		return ResponseEntity.status(HttpStatus.OK).body(hrmsemployeesalaryRepository.findAll());
	}

	@Override
	public ResponseEntity<Hrmsemployeesalary> approveEmployeesalary(Hrmsemployeesalary hrmsemployeesalary, int id) {

		LocalDateTime LocalTime = LocalDateTime.now();
		hrmsemployeesalary.setDateApproved(LocalTime);

		if (hrmsemployeesalaryRepository.existsById(id)) {
			return ResponseEntity.status(HttpStatus.OK).body(hrmsemployeesalaryRepository.save(hrmsemployeesalary));
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(hrmsemployeesalary);
		}
	}

	@Override
	public ResponseEntity<employeesalaryResponse> getEmployeesalaryById(int id) {
		employeesalaryResponse employeesalaryResponse = new employeesalaryResponse();
		if (hrmsemployeesalaryRepository.existsByIdAndActive(id, 1)) {

			Hrmsemployeesalary dbm = hrmsemployeesalaryRepository.findById(id).get();

			employeesalaryResponse.setActive(dbm.getActive());
			employeesalaryResponse.setApproved(dbm.getApproved());
			if (dbm.getApprovedbyId() != 0 && hrmsEmployeeRepository.existsById(dbm.getApprovedbyId())) {
				StringBuilder approvedby = new StringBuilder();

				HrmsEmployee hrmsEmployee = hrmsEmployeeRepository.findById(dbm.getApprovedbyId()).get();

				approvedby.append(hrmsEmployee.getFirstName().trim());
				if (hrmsEmployee.getMiddleName() != null) {
					approvedby.append(" " + hrmsEmployee.getMiddleName().trim());
				}
				approvedby.append(" " + hrmsEmployee.getLastName().trim());
				employeesalaryResponse.setApprovedby(approvedby.toString());
			}

			employeesalaryResponse.setApprovedbyId(dbm.getApprovedbyId());

			if (dbm.getAssignedbyId() != 0 && hrmsEmployeeRepository.existsById(dbm.getAssignedbyId())) {
				StringBuilder setAssignedby = new StringBuilder();

				HrmsEmployee hrmsEmployee = hrmsEmployeeRepository.findById(dbm.getAssignedbyId()).get();

				setAssignedby.append(hrmsEmployee.getFirstName().trim());
				if (hrmsEmployee.getMiddleName() != null) {
					setAssignedby.append(" " + hrmsEmployee.getMiddleName().trim());
				}
				setAssignedby.append(" " + hrmsEmployee.getLastName().trim());
				employeesalaryResponse.setAssignedby(setAssignedby.toString());
			}

			employeesalaryResponse.setAssignedbyId(dbm.getAssignedbyId());
			if (dbm.getCurrencyId() != 0 && hrmsCurrencyRepository.existsById(dbm.getCurrencyId())) {
				employeesalaryResponse
						.setCurrency(hrmsCurrencyRepository.findById(dbm.getCurrencyId()).get().getName());
			}
			employeesalaryResponse.setCurrencyId(dbm.getCurrencyId());
			if (dbm.getDescriptionApproved() != null) {
				employeesalaryResponse.setDescriptionApproved(dbm.getDescriptionApproved());
			}
			if (dbm.getDescriptionAssigned() != null) {
				employeesalaryResponse.setDescriptionAssigned(dbm.getDescriptionAssigned());
			}
			employeesalaryResponse.setEmployeeid(dbm.getEmployeeid());
			if (dbm.getEmployeeid() != 0 && hrmsEmployeeRepository.existsById(dbm.getEmployeeid())) {
				StringBuilder fullName = new StringBuilder();

				HrmsEmployee hrmsEmployee = hrmsEmployeeRepository.findById(dbm.getEmployeeid()).get();

				fullName.append(hrmsEmployee.getFirstName().trim());
				if (hrmsEmployee.getMiddleName() != null) {
					fullName.append(" " + hrmsEmployee.getMiddleName().trim());
				}
				fullName.append(" " + hrmsEmployee.getLastName().trim());
				employeesalaryResponse.setEmployeefullname(fullName.toString());
			}

			employeesalaryResponse.setId(dbm.getId());
			if (dbm.getSalarystructureId() != 0
					&& hrmsSalarystructureRepository.existsById(dbm.getSalarystructureId())) {
				String SalaryScale = hrmsSalaryScaleRepository
						.findById(hrmsSalarystructureRepository.findById(dbm.getSalarystructureId()).get().getScaleId())
						.get().getName()
						+ "-"
						+ hrmsSalaryscalenotchRepository.findById(
								hrmsSalarystructureRepository.findById(dbm.getSalarystructureId()).get().getNotchId())
								.get().getName();
				employeesalaryResponse.setSalaryScale(SalaryScale);

			}

			employeesalaryResponse.setSalarystructureId(dbm.getSalarystructureId());

		}

		return ResponseEntity.status(HttpStatus.OK).body(employeesalaryResponse);
	}

	@Override
	public ResponseEntity<List<employeesalaryResponse>> getAllemployeesalary() {
		List<employeesalaryResponse> employeesalaryResponselist = new ArrayList<>();

		List<Hrmsemployeesalary> dbms = hrmsemployeesalaryRepository.findByActive(1);

		for (Hrmsemployeesalary dbm : dbms) {

			employeesalaryResponse employeesalaryResponse = new employeesalaryResponse();

			employeesalaryResponse.setActive(dbm.getActive());
			employeesalaryResponse.setApproved(dbm.getApproved());
			if (dbm.getApprovedbyId() != 0 && hrmsEmployeeRepository.existsById(dbm.getApprovedbyId())) {
				StringBuilder approvedby = new StringBuilder();

				HrmsEmployee hrmsEmployee = hrmsEmployeeRepository.findById(dbm.getApprovedbyId()).get();

				approvedby.append(hrmsEmployee.getFirstName().trim());
				if (hrmsEmployee.getMiddleName() != null) {
					approvedby.append(" " + hrmsEmployee.getMiddleName().trim());
				}
				approvedby.append(" " + hrmsEmployee.getLastName().trim());
				employeesalaryResponse.setApprovedby(approvedby.toString());
			}

			employeesalaryResponse.setApprovedbyId(dbm.getApprovedbyId());

			if (dbm.getAssignedbyId() != 0 && hrmsEmployeeRepository.existsById(dbm.getAssignedbyId())) {
				StringBuilder setAssignedby = new StringBuilder();

				HrmsEmployee hrmsEmployee = hrmsEmployeeRepository.findById(dbm.getAssignedbyId()).get();

				setAssignedby.append(hrmsEmployee.getFirstName().trim());
				if (hrmsEmployee.getMiddleName() != null) {
					setAssignedby.append(" " + hrmsEmployee.getMiddleName().trim());
				}
				setAssignedby.append(" " + hrmsEmployee.getLastName().trim());
				employeesalaryResponse.setAssignedby(setAssignedby.toString());
			}

			employeesalaryResponse.setAssignedbyId(dbm.getAssignedbyId());
			if (dbm.getCurrencyId() != 0 && hrmsCurrencyRepository.existsById(dbm.getCurrencyId())) {
				employeesalaryResponse
						.setCurrency(hrmsCurrencyRepository.findById(dbm.getCurrencyId()).get().getName());
			}
			employeesalaryResponse.setCurrencyId(dbm.getCurrencyId());
			if (dbm.getDescriptionApproved() != null) {
				employeesalaryResponse.setDescriptionApproved(dbm.getDescriptionApproved());
			}
			if (dbm.getDescriptionAssigned() != null) {
				employeesalaryResponse.setDescriptionAssigned(dbm.getDescriptionAssigned());
			}
			employeesalaryResponse.setEmployeeid(dbm.getEmployeeid());
			if (dbm.getEmployeeid() != 0 && hrmsEmployeeRepository.existsById(dbm.getEmployeeid())) {
				StringBuilder fullName = new StringBuilder();

				HrmsEmployee hrmsEmployee = hrmsEmployeeRepository.findById(dbm.getEmployeeid()).get();

				fullName.append(hrmsEmployee.getFirstName().trim());
				if (hrmsEmployee.getMiddleName() != null) {
					fullName.append(" " + hrmsEmployee.getMiddleName().trim());
				}
				fullName.append(" " + hrmsEmployee.getLastName().trim());
				employeesalaryResponse.setEmployeefullname(fullName.toString());
			}

			employeesalaryResponse.setId(dbm.getId());
			if (dbm.getSalarystructureId() != 0
					&& hrmsSalarystructureRepository.existsById(dbm.getSalarystructureId())) {
				String SalaryScale = hrmsSalaryScaleRepository
						.findById(hrmsSalarystructureRepository.findById(dbm.getSalarystructureId()).get().getScaleId())
						.get().getName()
						+ "-"
						+ hrmsSalaryscalenotchRepository.findById(
								hrmsSalarystructureRepository.findById(dbm.getSalarystructureId()).get().getNotchId())
								.get().getName();
				employeesalaryResponse.setSalaryScale(SalaryScale);

			}

			employeesalaryResponse.setSalarystructureId(dbm.getSalarystructureId());

			employeesalaryResponselist.add(employeesalaryResponse);

		}

		return ResponseEntity.status(HttpStatus.OK).body(employeesalaryResponselist);
	}

	@Override
	public ResponseEntity<employeesalaryResponse> getEmployeesalaryByEmpId(int empid) {
		employeesalaryResponse employeesalaryResponse = new employeesalaryResponse();
		if (hrmsemployeesalaryRepository.existsByEmployeeidAndActive(empid, 1)) {

			Hrmsemployeesalary dbm = hrmsemployeesalaryRepository.findByEmployeeidAndActive(empid, 1);

			employeesalaryResponse.setActive(dbm.getActive());
			employeesalaryResponse.setApproved(dbm.getApproved());
			if (dbm.getApprovedbyId() != 0 && hrmsEmployeeRepository.existsById(dbm.getApprovedbyId())) {
				StringBuilder approvedby = new StringBuilder();

				HrmsEmployee hrmsEmployee = hrmsEmployeeRepository.findById(dbm.getApprovedbyId()).get();

				approvedby.append(hrmsEmployee.getFirstName().trim());
				if (hrmsEmployee.getMiddleName() != null) {
					approvedby.append(" " + hrmsEmployee.getMiddleName().trim());
				}
				approvedby.append(" " + hrmsEmployee.getLastName().trim());
				employeesalaryResponse.setApprovedby(approvedby.toString());
			}

			employeesalaryResponse.setApprovedbyId(dbm.getApprovedbyId());

			if (dbm.getAssignedbyId() != 0 && hrmsEmployeeRepository.existsById(dbm.getAssignedbyId())) {
				StringBuilder setAssignedby = new StringBuilder();

				HrmsEmployee hrmsEmployee = hrmsEmployeeRepository.findById(dbm.getAssignedbyId()).get();

				setAssignedby.append(hrmsEmployee.getFirstName().trim());
				if (hrmsEmployee.getMiddleName() != null) {
					setAssignedby.append(" " + hrmsEmployee.getMiddleName().trim());
				}
				setAssignedby.append(" " + hrmsEmployee.getLastName().trim());
				employeesalaryResponse.setAssignedby(setAssignedby.toString());
			}

			employeesalaryResponse.setAssignedbyId(dbm.getAssignedbyId());
			if (dbm.getCurrencyId() != 0 && hrmsCurrencyRepository.existsById(dbm.getCurrencyId())) {
				employeesalaryResponse
						.setCurrency(hrmsCurrencyRepository.findById(dbm.getCurrencyId()).get().getName());
			}
			employeesalaryResponse.setCurrencyId(dbm.getCurrencyId());
			if (dbm.getDescriptionApproved() != null) {
				employeesalaryResponse.setDescriptionApproved(dbm.getDescriptionApproved());
			}
			if (dbm.getDescriptionAssigned() != null) {
				employeesalaryResponse.setDescriptionAssigned(dbm.getDescriptionAssigned());
			}
			employeesalaryResponse.setEmployeeid(dbm.getEmployeeid());
			if (dbm.getEmployeeid() != 0 && hrmsEmployeeRepository.existsById(dbm.getEmployeeid())) {
				StringBuilder fullName = new StringBuilder();

				HrmsEmployee hrmsEmployee = hrmsEmployeeRepository.findById(dbm.getEmployeeid()).get();

				fullName.append(hrmsEmployee.getFirstName().trim());
				if (hrmsEmployee.getMiddleName() != null) {
					fullName.append(" " + hrmsEmployee.getMiddleName().trim());
				}
				fullName.append(" " + hrmsEmployee.getLastName().trim());
				employeesalaryResponse.setEmployeefullname(fullName.toString());
			}

			employeesalaryResponse.setId(dbm.getId());
			if (dbm.getSalarystructureId() != 0
					&& hrmsSalarystructureRepository.existsById(dbm.getSalarystructureId())) {
				String SalaryScale = hrmsSalaryScaleRepository
						.findById(hrmsSalarystructureRepository.findById(dbm.getSalarystructureId()).get().getScaleId())
						.get().getName()
						+ "-"
						+ hrmsSalaryscalenotchRepository.findById(
								hrmsSalarystructureRepository.findById(dbm.getSalarystructureId()).get().getNotchId())
								.get().getName();
				employeesalaryResponse.setSalaryScale(SalaryScale);

			}

			employeesalaryResponse.setSalarystructureId(dbm.getSalarystructureId());

		}

		return ResponseEntity.status(HttpStatus.OK).body(employeesalaryResponse);
	}

}
