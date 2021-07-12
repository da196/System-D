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
import com.Hrms.Employee.Repository.HrmsDesignationRepository;
import com.Hrms.Employee.Repository.HrmsEmployeeRepository;
import com.Hrms.Employee.Repository.HrmsLocationCityRepository;
import com.Hrms.Payroll.DTO.EmployeeBankAccount;
import com.Hrms.Payroll.Entity.HrmsEmployeeBankAccount;
import com.Hrms.Payroll.Entity.HrmsEmployeeBankAccountAudit;
import com.Hrms.Payroll.Entity.HrmsEmployeeBankAccountHistory;
import com.Hrms.Payroll.Repository.HrmsBankBranchRepository;
import com.Hrms.Payroll.Repository.HrmsBankRepository;
import com.Hrms.Payroll.Repository.HrmsEmployeeBankAccountAuditRepository;
import com.Hrms.Payroll.Repository.HrmsEmployeeBankAccountHistoryRepository;
import com.Hrms.Payroll.Repository.HrmsEmployeeBankAccountRepository;

@Service
public class HrmsEmployeeBankAccountServiceImpl implements HrmsEmployeeBankAccountService {
	@Autowired
	private HrmsEmployeeBankAccountRepository hrmsEmployeeBankAccountRepository;

	@Autowired

	private HrmsEmployeeRepository hrmsEmployeeRepository;

	@Autowired
	private HrmsBankRepository hrmsBankRepository;

	@Autowired
	private HrmsBankBranchRepository hrmsBankBranchRepository;

	@Autowired
	private HrmsDesignationRepository hrmsDesignationRepository;

	@Autowired
	private HrmsLocationCityRepository hrmsLocationCityRepository;

	@Autowired
	private HrmsEmployeeBankAccountAuditRepository hrmsEmployeeBankAccountAuditRepository;
	@Autowired
	private HrmsEmployeeBankAccountHistoryRepository hrmsEmployeeBankAccountHistoryRepository;

	@Override
	public ResponseEntity<HrmsEmployeeBankAccount> addEmployeeBankAccount(
			HrmsEmployeeBankAccount hrmsEmployeeBankAccount) {
		if (hrmsEmployeeBankAccountRepository.existsByAccountnumberAndActive(hrmsEmployeeBankAccount.getAccountnumber(),
				1)
				|| hrmsEmployeeBankAccountRepository.existsByEmployeeidAndPriorityAndActive(
						hrmsEmployeeBankAccount.getEmployeeid(), hrmsEmployeeBankAccount.getPriority(), 1)) {
			return ResponseEntity.status(HttpStatus.ALREADY_REPORTED).body(hrmsEmployeeBankAccount);
		} else {
			if (hrmsEmployeeRepository.existsByIdAndActive(hrmsEmployeeBankAccount.getEmployeeid(), 1)
					&& hrmsEmployeeRepository.existsByIdAndActive(hrmsEmployeeBankAccount.getCreatedbyuserid(), 1)
					&& hrmsBankRepository.existsByIdAndActive(hrmsEmployeeBankAccount.getBankid(), 1)
			// &&
			// hrmsLocationCityRepository.existsByIdAndActive(hrmsEmployeeBankAccount.getBankbranchlocationid(),
			// 1)
			) {
				hrmsEmployeeBankAccount.setActive(1);
				hrmsEmployeeBankAccount.setApproved(0);
				hrmsEmployeeBankAccount.setCreatedbyuserid(hrmsEmployeeBankAccount.getCreatedbyuserid());
				hrmsEmployeeBankAccount.setCreatedbydesignationid(hrmsEmployeeRepository
						.findById(hrmsEmployeeBankAccount.getCreatedbyuserid()).get().getDesignationId());
				hrmsEmployeeBankAccount.setUnique_id(UUID.randomUUID());

				// add employee bank account
				int idcreated = hrmsEmployeeBankAccountRepository.saveAndFlush(hrmsEmployeeBankAccount).getId();
				if (idcreated > 0) {
					// write logs to bank account logs
					/*
					 * HrmsEmployeeBankAccountAudit hrmsEmployeeBankAccountAudit = new
					 * HrmsEmployeeBankAccountAudit();
					 * 
					 * hrmsEmployeeBankAccountAudit.setAction(1); hrmsEmployeeBankAccountAudit.
					 * setActiondescription("Employee bank account created");
					 * hrmsEmployeeBankAccountAudit.setActive(1);
					 * hrmsEmployeeBankAccountAudit.setApproved(1);
					 * hrmsEmployeeBankAccountAudit.setAudituserdesignationid(hrmsEmployeeRepository
					 * .findById(hrmsEmployeeBankAccount.getCreatedbyuserid()).get().
					 * getDesignationId());
					 * hrmsEmployeeBankAccountAudit.setAudituserid(hrmsEmployeeBankAccount.
					 * getCreatedbyuserid());
					 * hrmsEmployeeBankAccountAudit.setBankaccountid(idcreated);
					 * hrmsEmployeeBankAccountAudit.setUnique_id(UUID.randomUUID());
					 * 
					 * int empbankaccoundid = hrmsEmployeeBankAccountAuditRepository.saveAndFlush(
					 * hrmsEmployeeBankAccountAudit).getId();
					 */
					// add employee bank account history
					HrmsEmployeeBankAccountHistory hrmsEmployeeBankAccountHistory = new HrmsEmployeeBankAccountHistory();

					hrmsEmployeeBankAccountHistory.setAccountname(hrmsEmployeeBankAccount.getAccountname());
					hrmsEmployeeBankAccountHistory.setAccountnumber(hrmsEmployeeBankAccount.getAccountnumber());
					hrmsEmployeeBankAccountHistory.setAction(1);
					hrmsEmployeeBankAccountHistory.setActiondescription("creation of employee bank account");
					hrmsEmployeeBankAccountHistory.setActive(1);
					hrmsEmployeeBankAccountHistory.setAmount(hrmsEmployeeBankAccount.getAmount());
					hrmsEmployeeBankAccountHistory.setApproved(0);
					hrmsEmployeeBankAccountHistory.setApprovedbydesignationid(0);// not mandatory
					hrmsEmployeeBankAccountHistory.setApprovedbyuserid(hrmsEmployeeBankAccount.getApprovedbyuserid());
					hrmsEmployeeBankAccountHistory.setApproveddate(null);
					hrmsEmployeeBankAccountHistory.setAudituserdesignationid(0);// not mandatory
					hrmsEmployeeBankAccountHistory.setAudituserid(0);// not mandatory
					hrmsEmployeeBankAccountHistory.setBankaccountid(idcreated);
					hrmsEmployeeBankAccountHistory.setBankbranchid(hrmsEmployeeBankAccount.getBankbranchid());
					hrmsEmployeeBankAccountHistory
							.setBankbranchlocationid(hrmsEmployeeBankAccount.getBankbranchlocationid());
					if (hrmsEmployeeBankAccount.getBankbranchlocationid() != 0 && hrmsLocationCityRepository
							.existsById(hrmsEmployeeBankAccount.getBankbranchlocationid())) {
						hrmsEmployeeBankAccountHistory.setBankbranchlocationname(hrmsLocationCityRepository
								.findById(hrmsEmployeeBankAccount.getBankbranchlocationid()).get().getName());
					}

					if (hrmsEmployeeBankAccount.getBankbranchid() != 0
							&& hrmsBankBranchRepository.existsById(hrmsEmployeeBankAccount.getBankbranchid())) {
						hrmsEmployeeBankAccountHistory.setBankbranchname(hrmsBankBranchRepository
								.findById(hrmsEmployeeBankAccount.getBankbranchid()).get().getName());

					}

					hrmsEmployeeBankAccountHistory.setBankid(hrmsEmployeeBankAccount.getBankid());
					if (hrmsEmployeeBankAccount.getBankid() != 0
							&& hrmsBankRepository.existsById(hrmsEmployeeBankAccount.getBankid())) {
						hrmsEmployeeBankAccountHistory.setBankname(
								hrmsBankRepository.findById(hrmsEmployeeBankAccount.getBankid()).get().getName());
					}

					hrmsEmployeeBankAccountHistory.setCreatedbydesignationid(0);// not mandatory
					hrmsEmployeeBankAccountHistory.setCreatedbyuserid(hrmsEmployeeBankAccount.getCreatedbyuserid());
					hrmsEmployeeBankAccountHistory.setDateopened(hrmsEmployeeBankAccount.getDateopened());
					hrmsEmployeeBankAccountHistory.setDescription(hrmsEmployeeBankAccount.getDescription());
					hrmsEmployeeBankAccountHistory.setEmployeeid(hrmsEmployeeBankAccount.getEmployeeid());
					hrmsEmployeeBankAccountHistory.setPriority(hrmsEmployeeBankAccount.getPriority());
					hrmsEmployeeBankAccountHistory.setUnique_id(UUID.randomUUID());

					hrmsEmployeeBankAccountHistoryRepository.saveAndFlush(hrmsEmployeeBankAccountHistory);

					return ResponseEntity.status(HttpStatus.OK).body(hrmsEmployeeBankAccount);

				} else {

					return ResponseEntity.status(HttpStatus.PRECONDITION_REQUIRED).body(null);
				}
			} else {
				return ResponseEntity.status(HttpStatus.FAILED_DEPENDENCY).body(hrmsEmployeeBankAccount);
			}
		}
	}

	@Override
	public ResponseEntity<EmployeeBankAccount> getEmployeeBankAccountById(int id) {
		if ((hrmsEmployeeBankAccountRepository.existsByIdAndActive(id, 1))) {

			String pattern = "yyyy-MM-dd";
			SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);

			EmployeeBankAccount employeeBankAccount = new EmployeeBankAccount();
			HrmsEmployeeBankAccount hrmsEmployeeBankAccount = hrmsEmployeeBankAccountRepository.findById(id).get();

			employeeBankAccount.setAccountname(hrmsEmployeeBankAccount.getAccountname());
			employeeBankAccount.setAmount(hrmsEmployeeBankAccount.getAmount());
			employeeBankAccount.setAccountnumber(hrmsEmployeeBankAccount.getAccountnumber());
			employeeBankAccount.setActive(hrmsEmployeeBankAccount.getActive());
			employeeBankAccount.setApproved(hrmsEmployeeBankAccount.getApproved());
			employeeBankAccount.setApprovalcomment(hrmsEmployeeBankAccount.getApprovalcomment());

			if (hrmsEmployeeBankAccount.getApprovedbyuserid() != 0
					&& hrmsEmployeeRepository.existsById(hrmsEmployeeBankAccount.getApprovedbyuserid())) {
				StringBuilder fullname2 = new StringBuilder();

				HrmsEmployee hrmsEmployee2 = hrmsEmployeeRepository
						.findById(hrmsEmployeeBankAccount.getCreatedbyuserid()).get();

				fullname2.append(hrmsEmployee2.getFirstName().trim());
				fullname2.append(" " + hrmsEmployee2.getMiddleName().trim());
				fullname2.append(" " + hrmsEmployee2.getLastName().trim());

				employeeBankAccount.setApprovedby(fullname2.toString());
			}
			if (hrmsEmployeeBankAccount.getApprovedbydesignationid() != 0
					&& hrmsDesignationRepository.existsById(hrmsEmployeeBankAccount.getApprovedbydesignationid())) {
				employeeBankAccount.setApprovedbydesignation(hrmsDesignationRepository
						.findById(hrmsEmployeeBankAccount.getApprovedbydesignationid()).get().getName());
			}

			employeeBankAccount.setApprovedbydesignationid(hrmsEmployeeBankAccount.getApprovedbydesignationid());
			employeeBankAccount.setApprovedbyuserid(hrmsEmployeeBankAccount.getApprovedbyuserid());
			if (hrmsEmployeeBankAccount.getApproveddate() != null) {
				employeeBankAccount.setApproveddate(simpleDateFormat.format(hrmsEmployeeBankAccount.getApproveddate()));
			}

			if (hrmsEmployeeBankAccount.getBankbranchlocationid() != 0
					&& hrmsLocationCityRepository.existsById(hrmsEmployeeBankAccount.getBankbranchlocationid())) {
				employeeBankAccount.setBankbranchlocation(hrmsLocationCityRepository
						.findById(hrmsEmployeeBankAccount.getBankbranchlocationid()).get().getName());
			}
			employeeBankAccount.setBankbranchlocationid(hrmsEmployeeBankAccount.getBankbranchlocationid());
			if (hrmsEmployeeBankAccount.getBankbranchid() != 0
					&& hrmsBankBranchRepository.existsById(hrmsEmployeeBankAccount.getBankbranchid())) {
				employeeBankAccount.setBankbranchname(
						hrmsBankBranchRepository.findById(hrmsEmployeeBankAccount.getBankbranchid()).get().getName());
			}
			employeeBankAccount.setBankid(hrmsEmployeeBankAccount.getBankid());
			if (hrmsEmployeeBankAccount.getBankid() != 0
					&& hrmsBankRepository.existsById(hrmsEmployeeBankAccount.getBankid())) {
				employeeBankAccount
						.setBankName(hrmsBankRepository.findById(hrmsEmployeeBankAccount.getBankid()).get().getName());
			}

			if (hrmsEmployeeBankAccount.getCreatedbydesignationid() != 0
					&& hrmsDesignationRepository.existsById(hrmsEmployeeBankAccount.getCreatedbydesignationid())) {
				employeeBankAccount.setCreatedbydesignation(hrmsDesignationRepository
						.findById(hrmsEmployeeBankAccount.getCreatedbydesignationid()).get().getName());
			}
			employeeBankAccount.setCreatedbydesignationid(hrmsEmployeeBankAccount.getCreatedbydesignationid());
			if (hrmsEmployeeBankAccount.getCreatedbyuserid() != 0) {
				StringBuilder fullname1 = new StringBuilder();

				HrmsEmployee hrmsEmployee1 = hrmsEmployeeRepository
						.findById(hrmsEmployeeBankAccount.getCreatedbyuserid()).get();

				fullname1.append(hrmsEmployee1.getFirstName().trim());
				if (hrmsEmployee1.getMiddleName() != null) {
					fullname1.append(" " + hrmsEmployee1.getMiddleName().trim());
				}
				fullname1.append(" " + hrmsEmployee1.getLastName().trim());
				employeeBankAccount.setCreatedbyuser(fullname1.toString());
			}
			employeeBankAccount.setCreatedbyuserid(hrmsEmployeeBankAccount.getCreatedbyuserid());
			if (hrmsEmployeeBankAccount.getDateclosed() != null) {
				employeeBankAccount.setDateclosed(simpleDateFormat.format(hrmsEmployeeBankAccount.getDateclosed()));
			}
			if (hrmsEmployeeBankAccount.getDateopened() != null) {
				employeeBankAccount.setDateopened(simpleDateFormat.format(hrmsEmployeeBankAccount.getDateopened()));
			}
			employeeBankAccount.setDescription(hrmsEmployeeBankAccount.getDescription());
			StringBuilder fullname = new StringBuilder();

			HrmsEmployee hrmsEmployee = hrmsEmployeeRepository.findById(hrmsEmployeeBankAccount.getEmployeeid()).get();

			fullname.append("   " + hrmsEmployee.getFirstName().trim());
			if (hrmsEmployee.getMiddleName() != null) {
				fullname.append(" " + hrmsEmployee.getMiddleName().trim());
			}
			fullname.append(" " + hrmsEmployee.getLastName().trim());

			employeeBankAccount.setEmployeeFullName(fullname.toString());
			employeeBankAccount.setEmployeeid(hrmsEmployeeBankAccount.getEmployeeid());
			employeeBankAccount.setId(id);
			employeeBankAccount.setPriority(hrmsEmployeeBankAccount.getPriority());

			return ResponseEntity.status(HttpStatus.OK).body(employeeBankAccount);
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
		}
	}

	@Override
	public ResponseEntity<List<EmployeeBankAccount>> getEmployeeBankAccountByEmpId(int empId) {

		List<EmployeeBankAccount> EmployeeBankAccountlist = new ArrayList<>();

		// if ((hrmsEmployeeBankAccountRepository.existsByEmployeeidAndActive(empId,
		// 1))) {

		String pattern = "yyyy-MM-dd";
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);

		List<HrmsEmployeeBankAccount> hrmsEmployeeBankAccountlist = hrmsEmployeeBankAccountRepository
				.findByEmployeeid(empId);
		hrmsEmployeeBankAccountlist.forEach(hrmsEmployeeBankAccount -> {
			EmployeeBankAccount employeeBankAccount = new EmployeeBankAccount();

			employeeBankAccount.setAccountname(hrmsEmployeeBankAccount.getAccountname());
			employeeBankAccount.setAccountnumber(hrmsEmployeeBankAccount.getAccountnumber());
			employeeBankAccount.setActive(hrmsEmployeeBankAccount.getActive());
			employeeBankAccount.setApproved(hrmsEmployeeBankAccount.getApproved());
			employeeBankAccount.setApprovalcomment(hrmsEmployeeBankAccount.getApprovalcomment());
			employeeBankAccount.setAmount(hrmsEmployeeBankAccount.getAmount());

			if (hrmsEmployeeBankAccount.getApprovedbyuserid() != 0
					&& hrmsEmployeeRepository.existsById(hrmsEmployeeBankAccount.getApprovedbyuserid())) {
				StringBuilder fullname2 = new StringBuilder();

				HrmsEmployee hrmsEmployee2 = hrmsEmployeeRepository
						.findById(hrmsEmployeeBankAccount.getCreatedbyuserid()).get();

				fullname2.append(" -  " + hrmsEmployee2.getFirstName().trim());
				fullname2.append(" -  " + hrmsEmployee2.getMiddleName().trim());
				fullname2.append(" " + hrmsEmployee2.getLastName().trim());

				employeeBankAccount.setApprovedby(fullname2.toString());
			}
			if (hrmsEmployeeBankAccount.getApprovedbydesignationid() != 0
					&& hrmsDesignationRepository.existsById(hrmsEmployeeBankAccount.getApprovedbydesignationid())) {
				employeeBankAccount.setApprovedbydesignation(hrmsDesignationRepository
						.findById(hrmsEmployeeBankAccount.getApprovedbydesignationid()).get().getName());
			}

			employeeBankAccount.setApprovedbydesignationid(hrmsEmployeeBankAccount.getApprovedbydesignationid());
			employeeBankAccount.setApprovedbyuserid(hrmsEmployeeBankAccount.getApprovedbyuserid());
			if (hrmsEmployeeBankAccount.getApproveddate() != null) {
				employeeBankAccount.setApproveddate(simpleDateFormat.format(hrmsEmployeeBankAccount.getApproveddate()));
			}

			if (hrmsEmployeeBankAccount.getBankbranchlocationid() != 0
					&& hrmsLocationCityRepository.existsById(hrmsEmployeeBankAccount.getBankbranchlocationid())) {
				employeeBankAccount.setBankbranchlocation(hrmsLocationCityRepository
						.findById(hrmsEmployeeBankAccount.getBankbranchlocationid()).get().getName());
			}
			employeeBankAccount.setBankbranchlocationid(hrmsEmployeeBankAccount.getBankbranchlocationid());
			if (hrmsEmployeeBankAccount.getBankbranchid() != 0
					&& hrmsBankBranchRepository.existsById(hrmsEmployeeBankAccount.getBankbranchid())) {
				employeeBankAccount.setBankbranchname(
						hrmsBankBranchRepository.findById(hrmsEmployeeBankAccount.getBankbranchid()).get().getName());
			}

			employeeBankAccount.setBankbranchid(hrmsEmployeeBankAccount.getBankbranchid());
			employeeBankAccount.setBankid(hrmsEmployeeBankAccount.getBankid());
			if (hrmsEmployeeBankAccount.getBankid() != 0
					&& hrmsBankRepository.existsById(hrmsEmployeeBankAccount.getBankid())) {
				employeeBankAccount
						.setBankName(hrmsBankRepository.findById(hrmsEmployeeBankAccount.getBankid()).get().getName());
			}

			if (hrmsEmployeeBankAccount.getCreatedbydesignationid() != 0
					&& hrmsDesignationRepository.existsById(hrmsEmployeeBankAccount.getCreatedbydesignationid())) {
				employeeBankAccount.setCreatedbydesignation(hrmsDesignationRepository
						.findById(hrmsEmployeeBankAccount.getCreatedbydesignationid()).get().getName());
			}
			employeeBankAccount.setCreatedbydesignationid(hrmsEmployeeBankAccount.getCreatedbydesignationid());
			if (hrmsEmployeeBankAccount.getCreatedbyuserid() != 0) {
				StringBuilder fullname1 = new StringBuilder();

				HrmsEmployee hrmsEmployee1 = hrmsEmployeeRepository
						.findById(hrmsEmployeeBankAccount.getCreatedbyuserid()).get();

				fullname1.append(hrmsEmployee1.getFirstName().trim());
				if (hrmsEmployee1.getMiddleName() != null) {
					fullname1.append(" " + hrmsEmployee1.getMiddleName().trim());
				}
				fullname1.append(" " + hrmsEmployee1.getLastName().trim());
				employeeBankAccount.setCreatedbyuser(fullname1.toString());
			}
			employeeBankAccount.setCreatedbyuserid(hrmsEmployeeBankAccount.getCreatedbyuserid());
			if (hrmsEmployeeBankAccount.getDateclosed() != null) {
				employeeBankAccount.setDateclosed(simpleDateFormat.format(hrmsEmployeeBankAccount.getDateclosed()));
			}
			if (hrmsEmployeeBankAccount.getDateopened() != null) {
				employeeBankAccount.setDateopened(simpleDateFormat.format(hrmsEmployeeBankAccount.getDateopened()));
			}
			employeeBankAccount.setDescription(hrmsEmployeeBankAccount.getDescription());
			StringBuilder fullname = new StringBuilder();

			HrmsEmployee hrmsEmployee = hrmsEmployeeRepository.findById(hrmsEmployeeBankAccount.getEmployeeid()).get();

			fullname.append(hrmsEmployee.getFirstName().trim());
			if (hrmsEmployee.getMiddleName() != null) {
				fullname.append(" " + hrmsEmployee.getMiddleName().trim());
			}
			fullname.append(" " + hrmsEmployee.getLastName().trim());

			employeeBankAccount.setEmployeeFullName(fullname.toString());
			employeeBankAccount.setEmployeeid(hrmsEmployeeBankAccount.getEmployeeid());
			employeeBankAccount.setId(hrmsEmployeeBankAccount.getId());
			employeeBankAccount.setPriority(hrmsEmployeeBankAccount.getPriority());
			EmployeeBankAccountlist.add(employeeBankAccount);

		});
		return ResponseEntity.status(HttpStatus.OK).body(EmployeeBankAccountlist);
		// } else {
		// return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
		// }
	}

	@Override
	public ResponseEntity<HrmsEmployeeBankAccount> updateEmployeeBankAccount(
			HrmsEmployeeBankAccount hrmsEmployeeBankAccount, int id) {
		if (hrmsEmployeeBankAccountRepository.existsByIdAndActive(id, 1)) {

			if (hrmsEmployeeRepository.existsByIdAndActive(hrmsEmployeeBankAccount.getEmployeeid(), 1)
					&& hrmsEmployeeRepository.existsByIdAndActive(hrmsEmployeeBankAccount.getCreatedbyuserid(), 1)
					&& hrmsBankRepository.existsByIdAndActive(hrmsEmployeeBankAccount.getBankid(), 1)
			// &&
			// hrmsLocationCityRepository.existsByIdAndActive(hrmsEmployeeBankAccount.getBankbranchlocationid(),
			// 1)
			) {

				hrmsEmployeeBankAccount.setActive(1);
				hrmsEmployeeBankAccount.setApproved(0);
				hrmsEmployeeBankAccount.setCreatedbyuserid(hrmsEmployeeBankAccount.getCreatedbyuserid());
				hrmsEmployeeBankAccount.setCreatedbydesignationid(hrmsEmployeeRepository
						.findById(hrmsEmployeeBankAccount.getCreatedbyuserid()).get().getDesignationId());
				hrmsEmployeeBankAccount.setDate_updated(LocalDateTime.now());

				// add employee bank account
				hrmsEmployeeBankAccountRepository.saveAndFlush(hrmsEmployeeBankAccount).getId();

				// add employee bank account history
				HrmsEmployeeBankAccountHistory hrmsEmployeeBankAccountHistory = new HrmsEmployeeBankAccountHistory();

				hrmsEmployeeBankAccountHistory.setAccountname(hrmsEmployeeBankAccount.getAccountname());
				hrmsEmployeeBankAccountHistory.setAccountnumber(hrmsEmployeeBankAccount.getAccountnumber());
				hrmsEmployeeBankAccountHistory.setAction(2);
				hrmsEmployeeBankAccountHistory.setActiondescription("update of employee bank account");
				hrmsEmployeeBankAccountHistory.setActive(1);
				hrmsEmployeeBankAccountHistory.setAmount(hrmsEmployeeBankAccount.getAmount());
				hrmsEmployeeBankAccountHistory.setApproved(0);
				hrmsEmployeeBankAccountHistory.setApprovedbydesignationid(0);// not mandatory
				hrmsEmployeeBankAccountHistory.setApprovedbyuserid(hrmsEmployeeBankAccount.getApprovedbyuserid());
				hrmsEmployeeBankAccountHistory.setApproveddate(null);
				hrmsEmployeeBankAccountHistory.setAudituserdesignationid(0);// not mandatory
				hrmsEmployeeBankAccountHistory.setAudituserid(0);// not mandatory
				hrmsEmployeeBankAccountHistory.setBankaccountid(id);
				hrmsEmployeeBankAccountHistory.setBankbranchid(hrmsEmployeeBankAccount.getBankbranchid());
				hrmsEmployeeBankAccountHistory
						.setBankbranchlocationid(hrmsEmployeeBankAccount.getBankbranchlocationid());
				if (hrmsEmployeeBankAccount.getBankbranchlocationid() != 0
						&& hrmsLocationCityRepository.existsById(hrmsEmployeeBankAccount.getBankbranchlocationid())) {
					hrmsEmployeeBankAccountHistory.setBankbranchlocationname(hrmsLocationCityRepository
							.findById(hrmsEmployeeBankAccount.getBankbranchlocationid()).get().getName());
				}

				if (hrmsEmployeeBankAccount.getBankbranchid() != 0
						&& hrmsBankBranchRepository.existsById(hrmsEmployeeBankAccount.getBankbranchid())) {
					hrmsEmployeeBankAccountHistory.setBankbranchname(hrmsBankBranchRepository
							.findById(hrmsEmployeeBankAccount.getBankbranchid()).get().getName());

				}

				hrmsEmployeeBankAccountHistory.setBankid(hrmsEmployeeBankAccount.getBankid());
				if (hrmsEmployeeBankAccount.getBankid() != 0
						&& hrmsBankRepository.existsById(hrmsEmployeeBankAccount.getBankid())) {
					hrmsEmployeeBankAccountHistory.setBankname(
							hrmsBankRepository.findById(hrmsEmployeeBankAccount.getBankid()).get().getName());
				}

				hrmsEmployeeBankAccountHistory.setCreatedbydesignationid(0);// not mandatory
				hrmsEmployeeBankAccountHistory.setCreatedbyuserid(hrmsEmployeeBankAccount.getCreatedbyuserid());
				hrmsEmployeeBankAccountHistory.setDateopened(hrmsEmployeeBankAccount.getDateopened());
				hrmsEmployeeBankAccountHistory.setDescription(hrmsEmployeeBankAccount.getDescription());
				hrmsEmployeeBankAccountHistory.setEmployeeid(hrmsEmployeeBankAccount.getEmployeeid());
				hrmsEmployeeBankAccountHistory.setPriority(hrmsEmployeeBankAccount.getPriority());
				hrmsEmployeeBankAccountHistory.setUnique_id(UUID.randomUUID());

				hrmsEmployeeBankAccountHistoryRepository.saveAndFlush(hrmsEmployeeBankAccountHistory);

				return ResponseEntity.status(HttpStatus.OK).body(hrmsEmployeeBankAccount);

			} else {
				return ResponseEntity.status(HttpStatus.FAILED_DEPENDENCY).body(hrmsEmployeeBankAccount);
			}
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(hrmsEmployeeBankAccount);
		}
	}

	@Override
	public ResponseEntity<?> deleteEmployeeBankAccount(int id) {
		if (hrmsEmployeeBankAccountRepository.existsByIdAndActive(id, 1)) {

			HrmsEmployeeBankAccount hrmsEmployeeBankAccount = hrmsEmployeeBankAccountRepository.findById(id).get();

			hrmsEmployeeBankAccount.setActive(0);
			hrmsEmployeeBankAccount.setApproved(0);

			hrmsEmployeeBankAccount.setCreatedbyuserid(hrmsEmployeeBankAccount.getCreatedbyuserid());
			hrmsEmployeeBankAccount.setCreatedbydesignationid(hrmsEmployeeRepository
					.findById(hrmsEmployeeBankAccount.getCreatedbyuserid()).get().getDesignationId());
			hrmsEmployeeBankAccount.setDate_updated(LocalDateTime.now());

			// add employee bank account
			hrmsEmployeeBankAccountRepository.saveAndFlush(hrmsEmployeeBankAccount).getId();

			// write logs to bank account logs
			// add employee bank account history
			HrmsEmployeeBankAccountHistory hrmsEmployeeBankAccountHistory = new HrmsEmployeeBankAccountHistory();

			hrmsEmployeeBankAccountHistory.setAccountname(hrmsEmployeeBankAccount.getAccountname());
			hrmsEmployeeBankAccountHistory.setAccountnumber(hrmsEmployeeBankAccount.getAccountnumber());
			hrmsEmployeeBankAccountHistory.setAction(3);
			hrmsEmployeeBankAccountHistory.setActiondescription("delete of employee bank account");
			hrmsEmployeeBankAccountHistory.setActive(1);
			hrmsEmployeeBankAccountHistory.setAmount(hrmsEmployeeBankAccount.getAmount());
			hrmsEmployeeBankAccountHistory.setApproved(0);
			hrmsEmployeeBankAccountHistory.setApprovedbydesignationid(0);// not mandatory
			hrmsEmployeeBankAccountHistory.setApprovedbyuserid(hrmsEmployeeBankAccount.getApprovedbyuserid());
			hrmsEmployeeBankAccountHistory.setApproveddate(null);
			hrmsEmployeeBankAccountHistory.setAudituserdesignationid(0);// not mandatory
			hrmsEmployeeBankAccountHistory.setAudituserid(0);// not mandatory
			hrmsEmployeeBankAccountHistory.setBankaccountid(id);
			hrmsEmployeeBankAccountHistory.setBankbranchid(hrmsEmployeeBankAccount.getBankbranchid());
			hrmsEmployeeBankAccountHistory.setBankbranchlocationid(hrmsEmployeeBankAccount.getBankbranchlocationid());
			if (hrmsEmployeeBankAccount.getBankbranchlocationid() != 0
					&& hrmsLocationCityRepository.existsById(hrmsEmployeeBankAccount.getBankbranchlocationid())) {
				hrmsEmployeeBankAccountHistory.setBankbranchlocationname(hrmsLocationCityRepository
						.findById(hrmsEmployeeBankAccount.getBankbranchlocationid()).get().getName());
			}

			if (hrmsEmployeeBankAccount.getBankbranchid() != 0
					&& hrmsBankBranchRepository.existsById(hrmsEmployeeBankAccount.getBankbranchid())) {
				hrmsEmployeeBankAccountHistory.setBankbranchname(
						hrmsBankBranchRepository.findById(hrmsEmployeeBankAccount.getBankbranchid()).get().getName());

			}

			hrmsEmployeeBankAccountHistory.setBankid(hrmsEmployeeBankAccount.getBankid());
			if (hrmsEmployeeBankAccount.getBankid() != 0
					&& hrmsBankRepository.existsById(hrmsEmployeeBankAccount.getBankid())) {
				hrmsEmployeeBankAccountHistory
						.setBankname(hrmsBankRepository.findById(hrmsEmployeeBankAccount.getBankid()).get().getName());
			}

			hrmsEmployeeBankAccountHistory.setCreatedbydesignationid(0);// not mandatory
			hrmsEmployeeBankAccountHistory.setCreatedbyuserid(hrmsEmployeeBankAccount.getCreatedbyuserid());
			hrmsEmployeeBankAccountHistory.setDateopened(hrmsEmployeeBankAccount.getDateopened());
			hrmsEmployeeBankAccountHistory.setDescription(hrmsEmployeeBankAccount.getDescription());
			hrmsEmployeeBankAccountHistory.setEmployeeid(hrmsEmployeeBankAccount.getEmployeeid());
			hrmsEmployeeBankAccountHistory.setPriority(hrmsEmployeeBankAccount.getPriority());
			hrmsEmployeeBankAccountHistory.setUnique_id(UUID.randomUUID());

			hrmsEmployeeBankAccountHistoryRepository.saveAndFlush(hrmsEmployeeBankAccountHistory);

			return ResponseEntity.status(HttpStatus.OK).body(hrmsEmployeeBankAccount);

		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
		}
	}

	@Override
	public ResponseEntity<List<EmployeeBankAccount>> getAllEmployeeBankAccount() {

		List<EmployeeBankAccount> EmployeeBankAccountlist = new ArrayList<>();

		String pattern = "yyyy-MM-dd";
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);

		List<HrmsEmployeeBankAccount> hrmsEmployeeBankAccountlist = hrmsEmployeeBankAccountRepository.findByActive(1);
		hrmsEmployeeBankAccountlist.forEach(hrmsEmployeeBankAccount -> {
			EmployeeBankAccount employeeBankAccount = new EmployeeBankAccount();

			employeeBankAccount.setAccountname(hrmsEmployeeBankAccount.getAccountname());
			employeeBankAccount.setAccountnumber(hrmsEmployeeBankAccount.getAccountnumber());
			employeeBankAccount.setActive(hrmsEmployeeBankAccount.getActive());
			employeeBankAccount.setApproved(hrmsEmployeeBankAccount.getApproved());
			employeeBankAccount.setApprovalcomment(hrmsEmployeeBankAccount.getApprovalcomment());
			employeeBankAccount.setAmount(hrmsEmployeeBankAccount.getAmount());

			if (hrmsEmployeeBankAccount.getApprovedbyuserid() != 0
					&& hrmsEmployeeRepository.existsById(hrmsEmployeeBankAccount.getApprovedbyuserid())) {
				StringBuilder fullname2 = new StringBuilder();

				HrmsEmployee hrmsEmployee2 = hrmsEmployeeRepository
						.findById(hrmsEmployeeBankAccount.getCreatedbyuserid()).get();

				fullname2.append(hrmsEmployee2.getFirstName().trim());
				if (hrmsEmployee2.getMiddleName() != null) {
					fullname2.append(" " + hrmsEmployee2.getMiddleName().trim());
				}
				fullname2.append(" " + hrmsEmployee2.getLastName().trim());

				employeeBankAccount.setApprovedby(fullname2.toString());
			}
			if (hrmsEmployeeBankAccount.getApprovedbydesignationid() != 0
					&& hrmsDesignationRepository.existsById(hrmsEmployeeBankAccount.getApprovedbydesignationid())) {
				employeeBankAccount.setApprovedbydesignation(hrmsDesignationRepository
						.findById(hrmsEmployeeBankAccount.getApprovedbydesignationid()).get().getName());
			}

			employeeBankAccount.setApprovedbydesignationid(hrmsEmployeeBankAccount.getApprovedbydesignationid());
			employeeBankAccount.setApprovedbyuserid(hrmsEmployeeBankAccount.getApprovedbyuserid());
			if (hrmsEmployeeBankAccount.getApproveddate() != null) {
				employeeBankAccount.setApproveddate(simpleDateFormat.format(hrmsEmployeeBankAccount.getApproveddate()));
			}

			if (hrmsEmployeeBankAccount.getBankbranchlocationid() != 0
					&& hrmsLocationCityRepository.existsById(hrmsEmployeeBankAccount.getBankbranchlocationid())) {
				employeeBankAccount.setBankbranchlocation(hrmsLocationCityRepository
						.findById(hrmsEmployeeBankAccount.getBankbranchlocationid()).get().getName());
			}
			employeeBankAccount.setBankbranchlocationid(hrmsEmployeeBankAccount.getBankbranchlocationid());
			if (hrmsEmployeeBankAccount.getBankbranchid() != 0
					&& hrmsBankBranchRepository.existsById(hrmsEmployeeBankAccount.getBankbranchid())) {
				employeeBankAccount.setBankbranchname(
						hrmsBankBranchRepository.findById(hrmsEmployeeBankAccount.getBankbranchid()).get().getName());
			}
			employeeBankAccount.setBankbranchid(hrmsEmployeeBankAccount.getBankbranchid());
			employeeBankAccount.setBankid(hrmsEmployeeBankAccount.getBankid());
			if (hrmsEmployeeBankAccount.getBankid() != 0
					&& hrmsBankRepository.existsById(hrmsEmployeeBankAccount.getBankid())) {
				employeeBankAccount
						.setBankName(hrmsBankRepository.findById(hrmsEmployeeBankAccount.getBankid()).get().getName());
			}

			if (hrmsEmployeeBankAccount.getCreatedbydesignationid() != 0
					&& hrmsDesignationRepository.existsById(hrmsEmployeeBankAccount.getCreatedbydesignationid())) {
				employeeBankAccount.setCreatedbydesignation(hrmsDesignationRepository
						.findById(hrmsEmployeeBankAccount.getCreatedbydesignationid()).get().getName());
			}
			employeeBankAccount.setCreatedbydesignationid(hrmsEmployeeBankAccount.getCreatedbydesignationid());
			if (hrmsEmployeeBankAccount.getCreatedbyuserid() != 0) {
				StringBuilder fullname1 = new StringBuilder();

				HrmsEmployee hrmsEmployee1 = hrmsEmployeeRepository
						.findById(hrmsEmployeeBankAccount.getCreatedbyuserid()).get();

				fullname1.append(hrmsEmployee1.getFirstName().trim());
				if (hrmsEmployee1.getMiddleName() != null) {
					fullname1.append(" " + hrmsEmployee1.getMiddleName().trim());
				}
				fullname1.append(" " + hrmsEmployee1.getLastName().trim());
				employeeBankAccount.setCreatedbyuser(fullname1.toString());
			}
			employeeBankAccount.setCreatedbyuserid(hrmsEmployeeBankAccount.getCreatedbyuserid());
			if (hrmsEmployeeBankAccount.getDateclosed() != null) {
				employeeBankAccount.setDateclosed(simpleDateFormat.format(hrmsEmployeeBankAccount.getDateclosed()));
			}
			if (hrmsEmployeeBankAccount.getDateopened() != null) {
				employeeBankAccount.setDateopened(simpleDateFormat.format(hrmsEmployeeBankAccount.getDateopened()));
			}
			employeeBankAccount.setDescription(hrmsEmployeeBankAccount.getDescription());
			StringBuilder fullname = new StringBuilder();

			HrmsEmployee hrmsEmployee = hrmsEmployeeRepository.findById(hrmsEmployeeBankAccount.getEmployeeid()).get();

			fullname.append(hrmsEmployee.getFirstName().trim());
			if (hrmsEmployee.getMiddleName() != null) {
				fullname.append(" " + hrmsEmployee.getMiddleName().trim());
			}
			fullname.append(" " + hrmsEmployee.getLastName().trim());

			employeeBankAccount.setEmployeeFullName(fullname.toString());
			employeeBankAccount.setEmployeeid(hrmsEmployeeBankAccount.getEmployeeid());
			employeeBankAccount.setId(hrmsEmployeeBankAccount.getId());
			employeeBankAccount.setPriority(hrmsEmployeeBankAccount.getPriority());
			EmployeeBankAccountlist.add(employeeBankAccount);

		});
		return ResponseEntity.status(HttpStatus.OK).body(EmployeeBankAccountlist);

	}

	@Override
	public ResponseEntity<List<EmployeeBankAccount>> getAllEmployeeBankAccountNonApproved() {

		List<EmployeeBankAccount> EmployeeBankAccountlist = new ArrayList<>();

		String pattern = "yyyy-MM-dd";
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);

		List<HrmsEmployeeBankAccount> hrmsEmployeeBankAccountlist = hrmsEmployeeBankAccountRepository
				.findByActiveAndApproved(1, 0);
		hrmsEmployeeBankAccountlist.forEach(hrmsEmployeeBankAccount -> {
			EmployeeBankAccount employeeBankAccount = new EmployeeBankAccount();

			employeeBankAccount.setApprovalcomment(hrmsEmployeeBankAccount.getApprovalcomment());

			employeeBankAccount.setAccountname(hrmsEmployeeBankAccount.getAccountname());
			employeeBankAccount.setAccountnumber(hrmsEmployeeBankAccount.getAccountnumber());
			employeeBankAccount.setActive(hrmsEmployeeBankAccount.getActive());
			employeeBankAccount.setApproved(hrmsEmployeeBankAccount.getApproved());
			employeeBankAccount.setBankbranchid(hrmsEmployeeBankAccount.getBankbranchid());
			employeeBankAccount.setAmount(hrmsEmployeeBankAccount.getAmount());

			if (hrmsEmployeeBankAccount.getApprovedbyuserid() != 0
					&& hrmsEmployeeRepository.existsById(hrmsEmployeeBankAccount.getApprovedbyuserid())) {
				StringBuilder fullname2 = new StringBuilder();

				HrmsEmployee hrmsEmployee2 = hrmsEmployeeRepository
						.findById(hrmsEmployeeBankAccount.getCreatedbyuserid()).get();

				fullname2.append(hrmsEmployee2.getFirstName().trim());
				if (hrmsEmployee2.getMiddleName() != null) {
					fullname2.append(" " + hrmsEmployee2.getMiddleName().trim());
				}
				fullname2.append(" " + hrmsEmployee2.getLastName().trim());

				employeeBankAccount.setApprovedby(fullname2.toString());
			}
			if (hrmsEmployeeBankAccount.getApprovedbydesignationid() != 0
					&& hrmsDesignationRepository.existsById(hrmsEmployeeBankAccount.getApprovedbydesignationid())) {
				employeeBankAccount.setApprovedbydesignation(hrmsDesignationRepository
						.findById(hrmsEmployeeBankAccount.getApprovedbydesignationid()).get().getName());
			}

			employeeBankAccount.setApprovedbydesignationid(hrmsEmployeeBankAccount.getApprovedbydesignationid());
			employeeBankAccount.setApprovedbyuserid(hrmsEmployeeBankAccount.getApprovedbyuserid());
			if (hrmsEmployeeBankAccount.getApproveddate() != null) {
				employeeBankAccount.setApproveddate(simpleDateFormat.format(hrmsEmployeeBankAccount.getApproveddate()));
			}

			if (hrmsEmployeeBankAccount.getBankbranchlocationid() != 0
					&& hrmsLocationCityRepository.existsById(hrmsEmployeeBankAccount.getBankbranchlocationid())) {
				employeeBankAccount.setBankbranchlocation(hrmsLocationCityRepository
						.findById(hrmsEmployeeBankAccount.getBankbranchlocationid()).get().getName());
			}
			employeeBankAccount.setBankbranchlocationid(hrmsEmployeeBankAccount.getBankbranchlocationid());
			if (hrmsEmployeeBankAccount.getBankbranchid() != 0
					&& hrmsBankBranchRepository.existsById(hrmsEmployeeBankAccount.getBankbranchid())) {
				employeeBankAccount.setBankbranchname(
						hrmsBankBranchRepository.findById(hrmsEmployeeBankAccount.getBankbranchid()).get().getName());
			}
			employeeBankAccount.setBankid(hrmsEmployeeBankAccount.getBankid());
			if (hrmsEmployeeBankAccount.getBankid() != 0
					&& hrmsBankRepository.existsById(hrmsEmployeeBankAccount.getBankid())) {
				employeeBankAccount
						.setBankName(hrmsBankRepository.findById(hrmsEmployeeBankAccount.getBankid()).get().getName());
			}

			if (hrmsEmployeeBankAccount.getCreatedbydesignationid() != 0
					&& hrmsDesignationRepository.existsById(hrmsEmployeeBankAccount.getCreatedbydesignationid())) {
				employeeBankAccount.setCreatedbydesignation(hrmsDesignationRepository
						.findById(hrmsEmployeeBankAccount.getCreatedbydesignationid()).get().getName());
			}
			employeeBankAccount.setCreatedbydesignationid(hrmsEmployeeBankAccount.getCreatedbydesignationid());
			if (hrmsEmployeeBankAccount.getCreatedbyuserid() != 0) {
				StringBuilder fullname1 = new StringBuilder();

				HrmsEmployee hrmsEmployee1 = hrmsEmployeeRepository
						.findById(hrmsEmployeeBankAccount.getCreatedbyuserid()).get();

				fullname1.append(hrmsEmployee1.getFirstName().trim());
				if (hrmsEmployee1.getMiddleName() != null) {
					fullname1.append(" " + hrmsEmployee1.getMiddleName().trim());
				}
				fullname1.append(" " + hrmsEmployee1.getLastName().trim());
				employeeBankAccount.setCreatedbyuser(fullname1.toString());
			}
			employeeBankAccount.setCreatedbyuserid(hrmsEmployeeBankAccount.getCreatedbyuserid());
			if (hrmsEmployeeBankAccount.getDateclosed() != null) {
				employeeBankAccount.setDateclosed(simpleDateFormat.format(hrmsEmployeeBankAccount.getDateclosed()));
			}
			if (hrmsEmployeeBankAccount.getDateopened() != null) {
				employeeBankAccount.setDateopened(simpleDateFormat.format(hrmsEmployeeBankAccount.getDateopened()));
			}
			employeeBankAccount.setDescription(hrmsEmployeeBankAccount.getDescription());
			StringBuilder fullname = new StringBuilder();

			HrmsEmployee hrmsEmployee = hrmsEmployeeRepository.findById(hrmsEmployeeBankAccount.getEmployeeid()).get();

			fullname.append(hrmsEmployee.getFirstName().trim());
			if (hrmsEmployee.getMiddleName() != null) {
				fullname.append(" " + hrmsEmployee.getMiddleName().trim());
			}
			fullname.append(" " + hrmsEmployee.getLastName().trim());

			employeeBankAccount.setEmployeeFullName(fullname.toString());
			employeeBankAccount.setEmployeeid(hrmsEmployeeBankAccount.getEmployeeid());
			employeeBankAccount.setId(hrmsEmployeeBankAccount.getId());
			employeeBankAccount.setPriority(hrmsEmployeeBankAccount.getPriority());
			EmployeeBankAccountlist.add(employeeBankAccount);

		});
		return ResponseEntity.status(HttpStatus.OK).body(EmployeeBankAccountlist);
	}

	@Override
	public ResponseEntity<HrmsEmployeeBankAccount> ApproveEmployeeBankAccount(int id, int approverid, int status,
			String comment) {
		if (hrmsEmployeeBankAccountRepository.existsByIdAndActiveAndApproved(id, 1, 0)) {
			if (hrmsEmployeeRepository.existsById(approverid) && (status == 1 || status == -1)) {
				HrmsEmployeeBankAccount hrmsEmployeeBankAccount = hrmsEmployeeBankAccountRepository.findById(id).get();
				if (hrmsEmployeeBankAccount.getCreatedbyuserid() != approverid) {// check if the creator is not the same
																					// person wants to approve

					hrmsEmployeeBankAccount.setDate_updated(LocalDateTime.now());
					hrmsEmployeeBankAccount.setApproved(status);
					hrmsEmployeeBankAccount.setApproved(status);
					hrmsEmployeeBankAccount.setApprovalcomment(comment);

					hrmsEmployeeBankAccount.setApprovedbyuserid(approverid);
					hrmsEmployeeBankAccount.setApprovedbydesignationid(
							hrmsEmployeeRepository.findById(approverid).get().getDesignationId());
					hrmsEmployeeBankAccountRepository.saveAndFlush(hrmsEmployeeBankAccount);

					// keep record on audit log

					HrmsEmployeeBankAccountAudit hrmsEmployeeBankAccountAudit = new HrmsEmployeeBankAccountAudit();

					hrmsEmployeeBankAccountAudit.setAction(4);
					hrmsEmployeeBankAccountAudit.setActiondescription("Employee bank account Approved");
					hrmsEmployeeBankAccountAudit.setActive(1);
					hrmsEmployeeBankAccountAudit.setApproved(status);
					hrmsEmployeeBankAccountAudit.setAudituserdesignationid(hrmsEmployeeRepository
							.findById(hrmsEmployeeBankAccount.getCreatedbyuserid()).get().getDesignationId());
					hrmsEmployeeBankAccountAudit.setAudituserid(hrmsEmployeeBankAccount.getCreatedbyuserid());
					hrmsEmployeeBankAccountAudit.setBankaccountid(id);
					hrmsEmployeeBankAccountAudit.setUnique_id(UUID.randomUUID());

					hrmsEmployeeBankAccountAuditRepository.saveAndFlush(hrmsEmployeeBankAccountAudit);

				} else {
					return ResponseEntity.status(HttpStatus.CONFLICT).body(null);
				}
			} else {
				return ResponseEntity.status(HttpStatus.FAILED_DEPENDENCY).body(null);
			}
			return null;
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
		}
	}

}
