package com.Hrms.Payroll.Service;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.Hrms.Employee.Entity.HrmsEmployee;
import com.Hrms.Employee.Repository.HrmsCurrencyRepository;
import com.Hrms.Employee.Repository.HrmsEmployeeRepository;
import com.Hrms.Payroll.DTO.PayrollContributionVoluntaryResponse;
import com.Hrms.Payroll.Entity.HrmsPayrollContributionVoluntary;
import com.Hrms.Payroll.Repository.HrmsPayrollContributionTypeVoluntaryRepository;
import com.Hrms.Payroll.Repository.HrmsPayrollContributionVoluntaryRepository;
import com.Hrms.Payroll.Repository.HrmsPayrollContributionVoluntaryServiceProviderRepository;

@Service
public class HrmsPayrollContributionVoluntaryServiceImpl implements HrmsPayrollContributionVoluntaryService {

	@Autowired

	private HrmsPayrollContributionVoluntaryRepository hrmsPayrollContributionVoluntaryRepository;

	@Autowired
	private HrmsPayrollContributionVoluntaryServiceProviderRepository hrmsPayrollContributionVoluntaryServiceProviderRepository;

	@Autowired
	private HrmsPayrollContributionTypeVoluntaryRepository hrmsPayrollContributionTypeVoluntaryRepository;

	@Autowired
	private HrmsEmployeeRepository hrmsEmployeeRepository;

	@Autowired
	private HrmsCurrencyRepository hrmsCurrencyRepository;

	@Override
	public ResponseEntity<HrmsPayrollContributionVoluntary> addPayrollContributionVoluntary(
			HrmsPayrollContributionVoluntary hrmsPayrollContributionVoluntary) {
		if (hrmsPayrollContributionVoluntaryRepository
				.existsByEmployeeidAndContributiontypeidAndServiceprovideridAndActive(
						hrmsPayrollContributionVoluntary.getEmployeeid(),
						hrmsPayrollContributionVoluntary.getContributiontypeid(),
						hrmsPayrollContributionVoluntary.getServiceproviderid(), 1)) {
			return ResponseEntity.status(HttpStatus.ALREADY_REPORTED).body(hrmsPayrollContributionVoluntary);
		} else {
			if (hrmsPayrollContributionVoluntaryServiceProviderRepository
					.existsByIdAndActive(hrmsPayrollContributionVoluntary.getServiceproviderid(), 1)
					&& hrmsPayrollContributionTypeVoluntaryRepository
							.existsByIdAndActive(hrmsPayrollContributionVoluntary.getContributiontypeid(), 1)
					&& hrmsEmployeeRepository.existsByIdAndActive(hrmsPayrollContributionVoluntary.getEmployeeid(), 1)
					&& hrmsCurrencyRepository.existsByIdAndActive(hrmsPayrollContributionVoluntary.getCurrencyid(),
							1)) {
				hrmsPayrollContributionVoluntary.setActive(1);
				hrmsPayrollContributionVoluntary.setApproved(0);
				hrmsPayrollContributionVoluntary.setUnique_id(UUID.randomUUID());

				return ResponseEntity.status(HttpStatus.OK).body(
						hrmsPayrollContributionVoluntaryRepository.saveAndFlush(hrmsPayrollContributionVoluntary));
			} else {
				return ResponseEntity.status(HttpStatus.PRECONDITION_FAILED).body(hrmsPayrollContributionVoluntary);
			}

		}
	}

	@Override
	public ResponseEntity<PayrollContributionVoluntaryResponse> getPayrollContributionVoluntaryById(int id) {
		if (hrmsPayrollContributionVoluntaryRepository.existsByIdAndActive(id, 1)) {

			String pattern = "yyyy-MM-dd";
			SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);

			HrmsPayrollContributionVoluntary dbm = hrmsPayrollContributionVoluntaryRepository.findByIdAndActive(id, 1)
					.get();

			PayrollContributionVoluntaryResponse payrollContributionVoluntaryResponse = new PayrollContributionVoluntaryResponse();

			payrollContributionVoluntaryResponse.setActive(dbm.getActive());
			payrollContributionVoluntaryResponse.setApproved(dbm.getApproved());
			payrollContributionVoluntaryResponse.setAmount(dbm.getAmount());
			payrollContributionVoluntaryResponse.setLocked(dbm.getLocked());
			payrollContributionVoluntaryResponse.setMembershipnumber(dbm.getMembershipnumber());
			if (dbm.getJoiningdate() != null) {
				payrollContributionVoluntaryResponse.setJoiningdate(simpleDateFormat.format(dbm.getJoiningdate()));
			}
			payrollContributionVoluntaryResponse.setContributionmode(dbm.getContributionmode());

			if (dbm.getContributiontypeid() != 0
					&& hrmsPayrollContributionTypeVoluntaryRepository.existsById(dbm.getContributiontypeid())) {
				payrollContributionVoluntaryResponse.setContributiontype(hrmsPayrollContributionTypeVoluntaryRepository
						.findById(dbm.getContributiontypeid()).get().getName());
			}

			payrollContributionVoluntaryResponse.setContributiontypeid(dbm.getContributiontypeid());
			if (dbm.getCurrencyid() != 0 && hrmsCurrencyRepository.existsById(dbm.getCurrencyid())) {
				payrollContributionVoluntaryResponse
						.setCurrency(hrmsCurrencyRepository.findById(dbm.getCurrencyid()).get().getName());
			}

			payrollContributionVoluntaryResponse.setCurrencyid(dbm.getCurrencyid());

			payrollContributionVoluntaryResponse.setDescription(dbm.getDescription());
			payrollContributionVoluntaryResponse.setEmployeeid(dbm.getEmployeeid());

			if (dbm.getEmployeeid() != 0 && hrmsEmployeeRepository.existsById(dbm.getEmployeeid())) {
				StringBuilder fullName = new StringBuilder();

				HrmsEmployee hrmsEmployee = hrmsEmployeeRepository.findById(dbm.getEmployeeid()).get();

				fullName.append(hrmsEmployee.getFirstName().trim());
				if (hrmsEmployee.getMiddleName() != null) {
					fullName.append(" " + hrmsEmployee.getMiddleName().trim());
				}
				fullName.append(" " + hrmsEmployee.getLastName().trim());
				payrollContributionVoluntaryResponse.setFullName(fullName.toString());
			}

			payrollContributionVoluntaryResponse.setId(dbm.getId());
			payrollContributionVoluntaryResponse.setIsformularcomputed(dbm.getIsformularcomputed());

			payrollContributionVoluntaryResponse.setRate(dbm.getRate());

			if (dbm.getServiceproviderid() != 0 && hrmsPayrollContributionVoluntaryServiceProviderRepository
					.existsById(dbm.getServiceproviderid())) {
				payrollContributionVoluntaryResponse
						.setServiceprovider(hrmsPayrollContributionVoluntaryServiceProviderRepository
								.findById(dbm.getServiceproviderid()).get().getName());
			}
			payrollContributionVoluntaryResponse.setServiceproviderid(dbm.getServiceproviderid());

			return ResponseEntity.status(HttpStatus.OK).body(payrollContributionVoluntaryResponse);
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
		}
	}

	@Override
	public ResponseEntity<HrmsPayrollContributionVoluntary> updatePayrollContributionVoluntary(
			HrmsPayrollContributionVoluntary hrmsPayrollContributionVoluntary, int id) {
		if (hrmsPayrollContributionVoluntaryRepository.existsByIdAndActive(id, 1)) {
			if (hrmsPayrollContributionVoluntaryServiceProviderRepository
					.existsByIdAndActive(hrmsPayrollContributionVoluntary.getServiceproviderid(), 1)
					&& hrmsPayrollContributionTypeVoluntaryRepository
							.existsByIdAndActive(hrmsPayrollContributionVoluntary.getContributiontypeid(), 1)
					&& hrmsEmployeeRepository.existsByIdAndActive(hrmsPayrollContributionVoluntary.getEmployeeid(), 1)
					&& hrmsCurrencyRepository.existsByIdAndActive(hrmsPayrollContributionVoluntary.getCurrencyid(),
							1)) {
				hrmsPayrollContributionVoluntary.setActive(1);
				hrmsPayrollContributionVoluntary.setApproved(0);
				hrmsPayrollContributionVoluntary.setDate_updated(LocalDateTime.now());

				HrmsPayrollContributionVoluntary hrmsPayrollContributionVoluntary1 = hrmsPayrollContributionVoluntaryRepository
						.findById(id).get();
				if (hrmsPayrollContributionVoluntary1.getDate_created() != null) {
					hrmsPayrollContributionVoluntary
							.setDate_created(hrmsPayrollContributionVoluntary1.getDate_created());
				}
				if (hrmsPayrollContributionVoluntary1.getUnique_id() != null) {
					hrmsPayrollContributionVoluntary.setUnique_id(hrmsPayrollContributionVoluntary1.getUnique_id());
				}

				return ResponseEntity.status(HttpStatus.OK).body(
						hrmsPayrollContributionVoluntaryRepository.saveAndFlush(hrmsPayrollContributionVoluntary));
			} else {
				return ResponseEntity.status(HttpStatus.PRECONDITION_FAILED).body(hrmsPayrollContributionVoluntary);
			}

		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(hrmsPayrollContributionVoluntary);
		}
	}

	@Override
	public ResponseEntity<?> deletePayrollContributionVoluntary(int id) {
		if (hrmsPayrollContributionVoluntaryRepository.existsByIdAndActive(id, 1)) {
			HrmsPayrollContributionVoluntary hrmsPayrollContributionVoluntary = hrmsPayrollContributionVoluntaryRepository
					.findByIdAndActive(id, 1).get();
			hrmsPayrollContributionVoluntary.setActive(0);
			hrmsPayrollContributionVoluntary.setDate_updated(LocalDateTime.now());

			return ResponseEntity.status(HttpStatus.OK)
					.body(hrmsPayrollContributionVoluntaryRepository.saveAndFlush(hrmsPayrollContributionVoluntary));
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
		}
	}

	@Override
	public ResponseEntity<List<PayrollContributionVoluntaryResponse>> getAllPayrollContributionVoluntary() {
		List<PayrollContributionVoluntaryResponse> payrollContributionVoluntaryResponselist = new ArrayList<>();
		List<HrmsPayrollContributionVoluntary> dbms = hrmsPayrollContributionVoluntaryRepository
				.findByActiveOrderByIdDesc(1);

		String pattern = "yyyy-MM-dd";
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);

		dbms.forEach(dbm -> {
			PayrollContributionVoluntaryResponse payrollContributionVoluntaryResponse = new PayrollContributionVoluntaryResponse();

			payrollContributionVoluntaryResponse.setActive(dbm.getActive());
			payrollContributionVoluntaryResponse.setApproved(dbm.getApproved());
			payrollContributionVoluntaryResponse.setAmount(dbm.getAmount());

			payrollContributionVoluntaryResponse.setLocked(dbm.getLocked());
			payrollContributionVoluntaryResponse.setMembershipnumber(dbm.getMembershipnumber());
			if (dbm.getJoiningdate() != null) {
				payrollContributionVoluntaryResponse.setJoiningdate(simpleDateFormat.format(dbm.getJoiningdate()));
			}
			payrollContributionVoluntaryResponse.setContributionmode(dbm.getContributionmode());

			if (dbm.getContributiontypeid() != 0
					&& hrmsPayrollContributionTypeVoluntaryRepository.existsById(dbm.getContributiontypeid())) {
				payrollContributionVoluntaryResponse.setContributiontype(hrmsPayrollContributionTypeVoluntaryRepository
						.findById(dbm.getContributiontypeid()).get().getName());
			}

			payrollContributionVoluntaryResponse.setContributiontypeid(dbm.getContributiontypeid());
			if (dbm.getCurrencyid() != 0 && hrmsCurrencyRepository.existsById(dbm.getCurrencyid())) {
				payrollContributionVoluntaryResponse
						.setCurrency(hrmsCurrencyRepository.findById(dbm.getCurrencyid()).get().getName());
			}

			payrollContributionVoluntaryResponse.setCurrencyid(dbm.getCurrencyid());

			payrollContributionVoluntaryResponse.setDescription(dbm.getDescription());
			payrollContributionVoluntaryResponse.setEmployeeid(dbm.getEmployeeid());

			if (dbm.getEmployeeid() != 0 && hrmsEmployeeRepository.existsById(dbm.getEmployeeid())) {
				StringBuilder fullName = new StringBuilder();

				HrmsEmployee hrmsEmployee = hrmsEmployeeRepository.findById(dbm.getEmployeeid()).get();

				fullName.append(hrmsEmployee.getFirstName().trim());
				if (hrmsEmployee.getMiddleName() != null) {
					fullName.append(" " + hrmsEmployee.getMiddleName().trim());
				}
				fullName.append(" " + hrmsEmployee.getLastName().trim());

				payrollContributionVoluntaryResponse.setFullName(fullName.toString());
			}

			payrollContributionVoluntaryResponse.setId(dbm.getId());
			payrollContributionVoluntaryResponse.setIsformularcomputed(dbm.getIsformularcomputed());

			payrollContributionVoluntaryResponse.setRate(dbm.getRate());

			if (dbm.getServiceproviderid() != 0 && hrmsPayrollContributionVoluntaryServiceProviderRepository
					.existsById(dbm.getServiceproviderid())) {
				payrollContributionVoluntaryResponse
						.setServiceprovider(hrmsPayrollContributionVoluntaryServiceProviderRepository
								.findById(dbm.getServiceproviderid()).get().getName());
			}
			payrollContributionVoluntaryResponse.setServiceproviderid(dbm.getServiceproviderid());
			payrollContributionVoluntaryResponselist.add(payrollContributionVoluntaryResponse);
		});

		return ResponseEntity.status(HttpStatus.OK).body(payrollContributionVoluntaryResponselist);
	}

	@Override
	public ResponseEntity<List<PayrollContributionVoluntaryResponse>> getPayrollContributionVoluntaryByEmpId(
			int EmpId) {
		if (hrmsPayrollContributionVoluntaryRepository.existsByIdAndActive(EmpId, 1)) {

			List<PayrollContributionVoluntaryResponse> payrollContributionVoluntaryResponselist = new ArrayList<>();
			List<HrmsPayrollContributionVoluntary> dbms = hrmsPayrollContributionVoluntaryRepository
					.findByEmployeeidAndActiveOrderByIdDesc(EmpId, 1);

			String pattern = "yyyy-MM-dd";
			SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);

			dbms.forEach(dbm -> {
				PayrollContributionVoluntaryResponse payrollContributionVoluntaryResponse = new PayrollContributionVoluntaryResponse();

				payrollContributionVoluntaryResponse.setActive(dbm.getActive());
				payrollContributionVoluntaryResponse.setApproved(dbm.getApproved());
				payrollContributionVoluntaryResponse.setAmount(dbm.getAmount());

				payrollContributionVoluntaryResponse.setLocked(dbm.getLocked());
				payrollContributionVoluntaryResponse.setMembershipnumber(dbm.getMembershipnumber());
				if (dbm.getJoiningdate() != null) {
					payrollContributionVoluntaryResponse.setJoiningdate(simpleDateFormat.format(dbm.getJoiningdate()));
				}
				payrollContributionVoluntaryResponse.setContributionmode(dbm.getContributionmode());

				if (dbm.getContributiontypeid() != 0
						&& hrmsPayrollContributionTypeVoluntaryRepository.existsById(dbm.getContributiontypeid())) {
					payrollContributionVoluntaryResponse
							.setContributiontype(hrmsPayrollContributionTypeVoluntaryRepository
									.findById(dbm.getContributiontypeid()).get().getName());
				}

				payrollContributionVoluntaryResponse.setContributiontypeid(dbm.getContributiontypeid());
				if (dbm.getCurrencyid() != 0 && hrmsCurrencyRepository.existsById(dbm.getCurrencyid())) {
					payrollContributionVoluntaryResponse
							.setCurrency(hrmsCurrencyRepository.findById(dbm.getCurrencyid()).get().getName());
				}

				payrollContributionVoluntaryResponse.setCurrencyid(dbm.getCurrencyid());

				payrollContributionVoluntaryResponse.setDescription(dbm.getDescription());
				payrollContributionVoluntaryResponse.setEmployeeid(dbm.getEmployeeid());

				if (dbm.getEmployeeid() != 0 && hrmsEmployeeRepository.existsById(dbm.getEmployeeid())) {
					StringBuilder fullName = new StringBuilder();

					HrmsEmployee hrmsEmployee = hrmsEmployeeRepository.findById(dbm.getEmployeeid()).get();

					fullName.append(hrmsEmployee.getFirstName().trim());
					if (hrmsEmployee.getMiddleName() != null) {
						fullName.append(" " + hrmsEmployee.getMiddleName().trim());
					}
					fullName.append(" " + hrmsEmployee.getLastName().trim());

					payrollContributionVoluntaryResponse.setFullName(fullName.toString());
				}

				payrollContributionVoluntaryResponse.setId(dbm.getId());
				payrollContributionVoluntaryResponse.setIsformularcomputed(dbm.getIsformularcomputed());

				payrollContributionVoluntaryResponse.setRate(dbm.getRate());

				if (dbm.getServiceproviderid() != 0 && hrmsPayrollContributionVoluntaryServiceProviderRepository
						.existsById(dbm.getServiceproviderid())) {
					payrollContributionVoluntaryResponse
							.setServiceprovider(hrmsPayrollContributionVoluntaryServiceProviderRepository
									.findById(dbm.getServiceproviderid()).get().getName());
				}
				payrollContributionVoluntaryResponse.setServiceproviderid(dbm.getServiceproviderid());
				payrollContributionVoluntaryResponselist.add(payrollContributionVoluntaryResponse);
			});

			return ResponseEntity.status(HttpStatus.OK).body(payrollContributionVoluntaryResponselist);
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
		}
	}

}
