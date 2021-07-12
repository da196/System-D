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

import com.Hrms.Employee.DTO.EmployeeInsuranceResponse;
import com.Hrms.Employee.Entity.HrmsEmployee;
import com.Hrms.Employee.Entity.HrmsEmployeeInsurance;
import com.Hrms.Employee.Repository.HrmsEmployeeInsuranceRepository;
import com.Hrms.Employee.Repository.HrmsEmployeeRepository;
import com.Hrms.Employee.Repository.HrmsInsuranceCategoryRepository;
import com.Hrms.Employee.Repository.HrmsInsuranceProviderRepository;
import com.Hrms.Payroll.Repository.HrmsInsuranceTypeRepository;

@Service
public class HrmsEmployeeInsuranceServiceImpl implements HrmsEmployeeInsuranceService {

	@Autowired
	private HrmsEmployeeInsuranceRepository hsrmsEmployeeInsuranceRepository;

	@Autowired
	private HrmsEmployeeRepository hrmsEmployeeRepository;

	@Autowired
	private HrmsInsuranceProviderRepository hrmsInsuranceProviderRepository;

	@Autowired
	private HrmsInsuranceCategoryRepository hrmsInsuranceCategoryRepository;

	@Autowired
	private HrmsInsuranceTypeRepository hrmsInsuranceTypeRepository;

	@Override
	public ResponseEntity<HrmsEmployeeInsurance> addEmployeeInsurance(HrmsEmployeeInsurance hrmsEmployeeInsurance) {

		UUID uuid = UUID.randomUUID();
		hrmsEmployeeInsurance.setUnique_id(uuid);
		hrmsEmployeeInsurance.setApproved(0);
		hrmsEmployeeInsurance.setActive(1);

		if (hsrmsEmployeeInsuranceRepository
				.existsByInsurancenumberAndActive(hrmsEmployeeInsurance.getInsurancenumber(), 1)
				|| (hsrmsEmployeeInsuranceRepository
						.existsByEmployeeidAndInsurancetypeidAndInsuranceprovideridAndActive(
								hrmsEmployeeInsurance.getEmployeeid(), hrmsEmployeeInsurance.getInsurancetypeid(),
								hrmsEmployeeInsurance.getInsuranceproviderid(), 1))) {
			return ResponseEntity.status(HttpStatus.ALREADY_REPORTED).body(null);

		} else {
			if (hrmsInsuranceProviderRepository.existsByIdAndActive(hrmsEmployeeInsurance.getInsuranceproviderid(), 1)
					&& hrmsInsuranceTypeRepository.existsByIdAndActive(hrmsEmployeeInsurance.getInsurancetypeid(), 1)) {

				return ResponseEntity.status(HttpStatus.OK)
						.body(hsrmsEmployeeInsuranceRepository.save(hrmsEmployeeInsurance));
			} else {
				return ResponseEntity.status(HttpStatus.PRECONDITION_FAILED).body(null);
			}
		}
	}

	@Override
	public ResponseEntity<EmployeeInsuranceResponse> getEmployeeInsuranceById(int id) {

		if (hsrmsEmployeeInsuranceRepository.existsByIdAndActive(id, 1)) {
			String pattern = "yyyy-MM-dd";
			SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);

			HrmsEmployeeInsurance dbm = hsrmsEmployeeInsuranceRepository.findById(id).get();
			EmployeeInsuranceResponse employeeInsuranceResponse = new EmployeeInsuranceResponse();

			employeeInsuranceResponse.setActive(dbm.getActive());
			employeeInsuranceResponse.setApproved(dbm.getApproved());

			if (dbm.getDateend() != null) {
				employeeInsuranceResponse.setDateend(simpleDateFormat.format(dbm.getDateend()));
			}
			if (dbm.getDatestart() != null) {
				employeeInsuranceResponse.setDatestart(simpleDateFormat.format(dbm.getDatestart()));
			}
			employeeInsuranceResponse.setEmployeeid(dbm.getEmployeeid());
			if (dbm.getEmployeeid() != 0 && hrmsEmployeeRepository.existsById(dbm.getEmployeeid())) {
				StringBuilder fullName = new StringBuilder();

				HrmsEmployee hrmsEmployee = hrmsEmployeeRepository.findById(dbm.getEmployeeid()).get();

				fullName.append(hrmsEmployee.getFirstName().trim());
				if (hrmsEmployee.getMiddleName() != null) {
					fullName.append(" " + hrmsEmployee.getMiddleName().trim());
				}
				fullName.append(" " + hrmsEmployee.getLastName().trim());
				employeeInsuranceResponse.setFullName(fullName.toString());
			}

			employeeInsuranceResponse.setDescriptionend(dbm.getDescriptionend());
			employeeInsuranceResponse.setDescriptionstart(dbm.getDescriptionstart());
			employeeInsuranceResponse.setId(dbm.getId());
			if (dbm.getInsurancetypeid() != 0 && hrmsInsuranceTypeRepository.existsById(dbm.getInsurancetypeid())) {
				employeeInsuranceResponse.setInsurancecategory(
						hrmsInsuranceTypeRepository.findById(dbm.getInsurancetypeid()).get().getName());
			}
			employeeInsuranceResponse.setInsurancecategoryid(dbm.getInsurancetypeid());
			employeeInsuranceResponse.setInsurancenumber(dbm.getInsurancenumber());
			if (dbm.getInsuranceproviderid() != 0
					&& hrmsInsuranceProviderRepository.existsById(dbm.getInsuranceproviderid())) {
				employeeInsuranceResponse.setInsuranceprovider(
						hrmsInsuranceProviderRepository.findById(dbm.getInsuranceproviderid()).get().getName());
			}
			employeeInsuranceResponse.setInsuranceproviderid(dbm.getInsuranceproviderid());

			return ResponseEntity.status(HttpStatus.OK).body(employeeInsuranceResponse);
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
		}
	}

	@Override
	public ResponseEntity<HrmsEmployeeInsurance> updateEmployeeInsurance(HrmsEmployeeInsurance hrmsEmployeeInsurance,
			int id) {

		LocalDateTime LocalTime = LocalDateTime.now();
		hrmsEmployeeInsurance.setDate_updated(LocalTime);
		hrmsEmployeeInsurance.setApproved(0);
		if (hsrmsEmployeeInsuranceRepository.existsByIdAndActive(id, 1)) {
			if (hrmsInsuranceProviderRepository.existsByIdAndActive(hrmsEmployeeInsurance.getInsuranceproviderid(), 1)
					&& hrmsInsuranceTypeRepository.existsByIdAndActive(hrmsEmployeeInsurance.getInsurancetypeid(), 1)) {

				HrmsEmployeeInsurance hsrmsEmployeeInsurance = hsrmsEmployeeInsuranceRepository.findById(id).get();
				LocalDateTime datecreated = hsrmsEmployeeInsurance.getDate_created();

				if (datecreated != null) {
					hrmsEmployeeInsurance.setDate_created(datecreated);
				}
				UUID uuid = hsrmsEmployeeInsurance.getUnique_id();
				if (uuid != null) {
					hrmsEmployeeInsurance.setUnique_id(uuid);
				}

				return ResponseEntity.status(HttpStatus.OK)
						.body(hsrmsEmployeeInsuranceRepository.save(hrmsEmployeeInsurance));
			} else {
				return ResponseEntity.status(HttpStatus.PRECONDITION_FAILED).body(null);
			}
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(hrmsEmployeeInsurance);
		}
	}

	@Override
	public ResponseEntity<?> deleteEmployeeInsurance(int id) {

		if (hsrmsEmployeeInsuranceRepository.existsById(id)) {
			HrmsEmployeeInsurance hsrmsEmployeeInsurance = hsrmsEmployeeInsuranceRepository.findById(id).get();
			hsrmsEmployeeInsurance.setActive(0);
			hsrmsEmployeeInsurance.setDate_updated(LocalDateTime.now());

			return ResponseEntity.status(HttpStatus.OK)
					.body(hsrmsEmployeeInsuranceRepository.save(hsrmsEmployeeInsurance));
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
		}
	}

	@Override
	public ResponseEntity<List<EmployeeInsuranceResponse>> listEmployeeInsurance() {

		String pattern = "yyyy-MM-dd";
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
		List<EmployeeInsuranceResponse> employeeInsuranceResponselist = new ArrayList<>();
		List<HrmsEmployeeInsurance> dbms = hsrmsEmployeeInsuranceRepository.findByActiveOrderByIdDesc(1);
		dbms.forEach(dbm -> {
			EmployeeInsuranceResponse employeeInsuranceResponse = new EmployeeInsuranceResponse();

			employeeInsuranceResponse.setActive(dbm.getActive());
			employeeInsuranceResponse.setApproved(dbm.getApproved());

			if (dbm.getDateend() != null) {
				employeeInsuranceResponse.setDateend(simpleDateFormat.format(dbm.getDateend()));
			}
			if (dbm.getDatestart() != null) {
				employeeInsuranceResponse.setDatestart(simpleDateFormat.format(dbm.getDatestart()));
			}
			employeeInsuranceResponse.setEmployeeid(dbm.getEmployeeid());
			if (dbm.getEmployeeid() != 0 && hrmsEmployeeRepository.existsById(dbm.getEmployeeid())) {
				StringBuilder fullName = new StringBuilder();

				HrmsEmployee hrmsEmployee = hrmsEmployeeRepository.findById(dbm.getEmployeeid()).get();

				fullName.append(hrmsEmployee.getFirstName().trim());
				if (hrmsEmployee.getMiddleName() != null) {
					fullName.append(" " + hrmsEmployee.getMiddleName().trim());
				}
				fullName.append(" " + hrmsEmployee.getLastName().trim());
				employeeInsuranceResponse.setFullName(fullName.toString());
			}

			employeeInsuranceResponse.setDescriptionend(dbm.getDescriptionend());
			employeeInsuranceResponse.setDescriptionstart(dbm.getDescriptionstart());
			employeeInsuranceResponse.setId(dbm.getId());
			if (dbm.getInsurancetypeid() != 0 && hrmsInsuranceTypeRepository.existsById(dbm.getInsurancetypeid())) {
				employeeInsuranceResponse.setInsurancecategory(
						hrmsInsuranceTypeRepository.findById(dbm.getInsurancetypeid()).get().getName());
			}
			employeeInsuranceResponse.setInsurancecategoryid(dbm.getInsurancetypeid());
			employeeInsuranceResponse.setInsurancenumber(dbm.getInsurancenumber());
			if (dbm.getInsuranceproviderid() != 0
					&& hrmsInsuranceProviderRepository.existsById(dbm.getInsuranceproviderid())) {
				employeeInsuranceResponse.setInsuranceprovider(
						hrmsInsuranceProviderRepository.findById(dbm.getInsuranceproviderid()).get().getName());
			}
			employeeInsuranceResponse.setInsuranceproviderid(dbm.getInsuranceproviderid());
			employeeInsuranceResponselist.add(employeeInsuranceResponse);
		});

		return ResponseEntity.status(HttpStatus.OK).body(employeeInsuranceResponselist);
	}

	@Override
	public ResponseEntity<List<EmployeeInsuranceResponse>> getEmployeeInsuranceByEmpId(int EmpId) {
		if (hsrmsEmployeeInsuranceRepository.existsByEmployeeidAndActive(EmpId, 1)) {
			String pattern = "yyyy-MM-dd";
			SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
			List<EmployeeInsuranceResponse> employeeInsuranceResponselist = new ArrayList<>();
			List<HrmsEmployeeInsurance> dbms = hsrmsEmployeeInsuranceRepository
					.findByEmployeeidAndActiveOrderByIdDesc(EmpId, 1);
			dbms.forEach(dbm -> {
				EmployeeInsuranceResponse employeeInsuranceResponse = new EmployeeInsuranceResponse();

				employeeInsuranceResponse.setActive(dbm.getActive());
				employeeInsuranceResponse.setApproved(dbm.getApproved());

				if (dbm.getDateend() != null) {
					employeeInsuranceResponse.setDateend(simpleDateFormat.format(dbm.getDateend()));
				}
				if (dbm.getDatestart() != null) {
					employeeInsuranceResponse.setDatestart(simpleDateFormat.format(dbm.getDatestart()));
				}
				employeeInsuranceResponse.setEmployeeid(dbm.getEmployeeid());
				if (dbm.getEmployeeid() != 0 && hrmsEmployeeRepository.existsById(dbm.getEmployeeid())) {
					StringBuilder fullName = new StringBuilder();

					HrmsEmployee hrmsEmployee = hrmsEmployeeRepository.findById(dbm.getEmployeeid()).get();

					fullName.append(hrmsEmployee.getFirstName().trim());
					if (hrmsEmployee.getMiddleName() != null) {
						fullName.append(" " + hrmsEmployee.getMiddleName().trim());
					}
					fullName.append(" " + hrmsEmployee.getLastName().trim());
					employeeInsuranceResponse.setFullName(fullName.toString());
				}

				employeeInsuranceResponse.setDescriptionend(dbm.getDescriptionend());
				employeeInsuranceResponse.setDescriptionstart(dbm.getDescriptionstart());
				employeeInsuranceResponse.setId(dbm.getId());
				if (dbm.getInsurancetypeid() != 0 && hrmsInsuranceTypeRepository.existsById(dbm.getInsurancetypeid())) {
					employeeInsuranceResponse.setInsurancecategory(
							hrmsInsuranceTypeRepository.findById(dbm.getInsurancetypeid()).get().getName());
				}
				employeeInsuranceResponse.setInsurancecategoryid(dbm.getInsurancetypeid());
				employeeInsuranceResponse.setInsurancenumber(dbm.getInsurancenumber());
				if (dbm.getInsuranceproviderid() != 0
						&& hrmsInsuranceProviderRepository.existsById(dbm.getInsuranceproviderid())) {
					employeeInsuranceResponse.setInsuranceprovider(
							hrmsInsuranceProviderRepository.findById(dbm.getInsuranceproviderid()).get().getName());
				}
				employeeInsuranceResponse.setInsuranceproviderid(dbm.getInsuranceproviderid());
				employeeInsuranceResponselist.add(employeeInsuranceResponse);
			});

			return ResponseEntity.status(HttpStatus.OK).body(employeeInsuranceResponselist);
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
		}
	}

	@Override
	public ResponseEntity<List<HrmsEmployeeInsurance>> addEmployeeInsuranceList(
			List<HrmsEmployeeInsurance> hrmsEmployeeInsurancelist) {

		List<HrmsEmployeeInsurance> hrmsEmployeeInsurancelistmade = new ArrayList<>();

		for (HrmsEmployeeInsurance hrmsEmployeeInsurance : hrmsEmployeeInsurancelist) {

			UUID uuid = UUID.randomUUID();
			hrmsEmployeeInsurance.setUnique_id(uuid);
			hrmsEmployeeInsurance.setApproved(0);
			hrmsEmployeeInsurance.setActive(1);

			if (hsrmsEmployeeInsuranceRepository
					.existsByInsurancenumberAndActive(hrmsEmployeeInsurance.getInsurancenumber(), 1)
					|| (hsrmsEmployeeInsuranceRepository
							.existsByEmployeeidAndInsurancetypeidAndInsuranceprovideridAndActive(
									hrmsEmployeeInsurance.getEmployeeid(), hrmsEmployeeInsurance.getInsurancetypeid(),
									hrmsEmployeeInsurance.getInsuranceproviderid(), 1))) {
				return ResponseEntity.status(HttpStatus.ALREADY_REPORTED).body(null);

			} else {
				if (hrmsInsuranceProviderRepository.existsByIdAndActive(hrmsEmployeeInsurance.getInsuranceproviderid(),
						1)
						&& hrmsInsuranceTypeRepository.existsByIdAndActive(hrmsEmployeeInsurance.getInsurancetypeid(),
								1)) {

					hrmsEmployeeInsurancelistmade.add(hrmsEmployeeInsurance);

				} else {
					return ResponseEntity.status(HttpStatus.PRECONDITION_FAILED).body(null);
				}
			}
		}

		return ResponseEntity.status(HttpStatus.OK)
				.body(hsrmsEmployeeInsuranceRepository.saveAll(hrmsEmployeeInsurancelistmade));
	}

}
