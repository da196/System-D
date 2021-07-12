package com.Hrms.Leave.Service;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.Hrms.Employee.Entity.HrmsEmployee;
import com.Hrms.Employee.Entity.HrmsSalarystructure;
import com.Hrms.Employee.Entity.Hrmsemployeesalary;
import com.Hrms.Employee.Repository.HrmsDesignationRepository;
import com.Hrms.Employee.Repository.HrmsEmployeeRepository;
import com.Hrms.Employee.Repository.HrmsOrganisationUnitTypeRepository;
import com.Hrms.Employee.Repository.HrmsOrginisationUnitRepository;
import com.Hrms.Employee.Repository.HrmsSalarystructureRepository;
import com.Hrms.Employee.Repository.HrmsemployeesalaryRepository;
import com.Hrms.Leave.DTO.LeaveApprovalStatus;
import com.Hrms.Leave.DTO.LeaveSellResponse;
import com.Hrms.Leave.Entity.HrmsLeaveBalance;
import com.Hrms.Leave.Entity.HrmsLeaveCommutationApproval;
import com.Hrms.Leave.Entity.HrmsLeaveCommutationApprovalWorkflow;
import com.Hrms.Leave.Entity.HrmsLeaveCommutationApprovalWorkflowStep;
import com.Hrms.Leave.Entity.HrmsLeaveSold;
import com.Hrms.Leave.Repository.HrmsLeaveBalanceRepository;
import com.Hrms.Leave.Repository.HrmsLeaveCommutationApprovalRepository;
import com.Hrms.Leave.Repository.HrmsLeaveCommutationApprovalWorkflowRepository;
import com.Hrms.Leave.Repository.HrmsLeaveCommutationApprovalWorkflowStepRepository;
import com.Hrms.Leave.Repository.HrmsLeaveSoldRepository;
import com.Hrms.Leave.Repository.HrmsLeaveTypeRepository;

@Service
public class HrmsLeaveSoldServiceImpl implements HrmsLeaveSoldService {
	@Autowired
	private HrmsLeaveSoldRepository hrmsLeaveSoldRepository;
	@Autowired
	private HrmsLeaveTypeRepository hrmsLeaveTypeRepository;

	@Autowired
	private HrmsOrginisationUnitRepository hrmsOrginisationUnitRepository;

	@Autowired
	private HrmsOrganisationUnitTypeRepository hrmsOrganisationUnitTypeRepository;

	@Autowired
	private HrmsLeaveBalanceRepository hrmsLeaveBalanceRepository;

	@Autowired
	private HrmsEmployeeRepository hrmsEmployeeRepository;

	@Autowired
	private HrmsemployeesalaryRepository hrmsemployeesalaryRepository;

	@Autowired
	private HrmsSalarystructureRepository hrmsSalarystructureRepository;

	@Autowired
	private HrmsLeaveCommutationApprovalWorkflowRepository hrmsLeaveCommutationApprovalWorkflowRepository;

	@Autowired
	private HrmsLeaveCommutationApprovalWorkflowStepRepository hrmsLeaveCommutationApprovalWorkflowStepRepository;

	@Autowired
	private HrmsLeaveCommutationApprovalRepository hrmsLeaveCommutationApprovalRepository;

	@Autowired
	private HrmsDesignationRepository hrmsDesignationRepository;

	@Override
	public ResponseEntity<HrmsLeaveSold> sellLeave(HrmsLeaveSold hrmsLeaveSold, int requesterid) {

		hrmsLeaveSold.setActive(1);
		hrmsLeaveSold.setApproved(0);
		hrmsLeaveSold.setUnique_id(UUID.randomUUID());
		hrmsLeaveSold.setSolddate(new Date());

		hrmsLeaveSold.setRequesterid(requesterid);
		int year = LocalDateTime.now().getYear();

		int month = LocalDateTime.now().getMonthValue();

		hrmsLeaveSold.setYear(year);
		hrmsLeaveSold.setMonth(month);

		Double amount = 0.00;

		// verify if leave type is annual leave
		if (hrmsLeaveTypeRepository.existsByIdAndActive(hrmsLeaveSold.getLeavetypeid(), 1)
				&& hrmsLeaveSold.getLeavetypeid() == 1) {
			if (hrmsLeaveSold.getEmployeeid() != requesterid
					&& hrmsEmployeeRepository.existsByIdAndActive(requesterid, 1)
					&& hrmsEmployeeRepository.existsByIdAndActive(hrmsLeaveSold.getEmployeeid(), 1)

			) {
				// verify if the request is the supervisor of the Employee
				if (hrmsEmployeeRepository.findById(requesterid).get().getDesignationId() == hrmsEmployeeRepository
						.findById(hrmsLeaveSold.getEmployeeid()).get().getSupervisordesignationid()) {

					// verify the employee has the balance required to be sold
					if (hrmsLeaveBalanceRepository.existsByEmployeeidAndLeavetypeidAndActive(
							hrmsLeaveSold.getEmployeeid(), hrmsLeaveSold.getLeavetypeid(), 1)

							&& hrmsLeaveBalanceRepository
									.findByEmployeeidAndLeavetypeidAndActive(hrmsLeaveSold.getEmployeeid(),
											hrmsLeaveSold.getLeavetypeid(), 1)
									.getDays() >= hrmsLeaveSold.getNumberofdaysSold()) {
						if (hrmsemployeesalaryRepository.existsByEmployeeidAndActive(hrmsLeaveSold.getEmployeeid(),
								1)) {

							Hrmsemployeesalary hrmsemployeesalary = hrmsemployeesalaryRepository
									.findByEmployeeidAndActive(hrmsLeaveSold.getEmployeeid(), 1);

							if (hrmsSalarystructureRepository
									.existsByIdAndActive(hrmsemployeesalary.getSalarystructureId(), 1)) {
								HrmsSalarystructure hrmsSalarystructure = hrmsSalarystructureRepository
										.findById(hrmsemployeesalary.getSalarystructureId()).get();
								if (hrmsSalarystructure.getBasicSalary() != null) {
									amount = ((double) hrmsLeaveSold.getNumberofdaysSold() / 30)
											* hrmsSalarystructure.getBasicSalary();

									hrmsLeaveSold.setAmount(amount);

								}

								HrmsLeaveBalance hrmsLeaveBalance = hrmsLeaveBalanceRepository
										.findByEmployeeidAndLeavetypeidAndActive(hrmsLeaveSold.getEmployeeid(),
												hrmsLeaveSold.getLeavetypeid(), 1);
								hrmsLeaveBalance.setDate_updated(LocalDateTime.now());
								hrmsLeaveBalance
										.setDays(hrmsLeaveBalance.getDays() - hrmsLeaveSold.getNumberofdaysSold());
								hrmsLeaveBalanceRepository.saveAndFlush(hrmsLeaveBalance);// reduce leave balance
								return ResponseEntity.status(HttpStatus.OK)
										.body(hrmsLeaveSoldRepository.saveAndFlush(hrmsLeaveSold));
							} else {
								return ResponseEntity.status(HttpStatus.NO_CONTENT).body(hrmsLeaveSold);
							}
						} else {
							return ResponseEntity.status(HttpStatus.NON_AUTHORITATIVE_INFORMATION).body(hrmsLeaveSold);
						}
					} else {
						return ResponseEntity.status(HttpStatus.LOOP_DETECTED).body(hrmsLeaveSold);
					}
				} else {
					return ResponseEntity.status(HttpStatus.UNAVAILABLE_FOR_LEGAL_REASONS).body(hrmsLeaveSold);
				}
			} else {
				return ResponseEntity.status(HttpStatus.FAILED_DEPENDENCY).body(hrmsLeaveSold);

			}
		} else {
			return ResponseEntity.status(HttpStatus.PRECONDITION_FAILED).body(hrmsLeaveSold);
		}
	}

	@Override
	public ResponseEntity<LeaveSellResponse> getLeaveSellById(int id) {
		String pattern = "yyyy-MM-dd";
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
		if (hrmsLeaveSoldRepository.existsByIdAndActive(id, 1)) {
			HrmsLeaveSold dbm = hrmsLeaveSoldRepository.findByIdAndActive(id, 1);
			LeaveSellResponse leaveSellResponse = new LeaveSellResponse();
			leaveSellResponse.setActive(dbm.getActive());
			leaveSellResponse.setAmount(dbm.getAmount());
			leaveSellResponse.setApproved(dbm.getApproved());
			if (dbm.getApproved() == 0 && dbm.getEmpknowledge() == 0) {
				leaveSellResponse.setCommutationStatus("Submitted");

			}

			if (dbm.getApproved() == 0 && dbm.getEmpknowledge() == 1) {
				leaveSellResponse.setCommutationStatus("On Progress");

			}

			if (dbm.getApproved() == 1 && dbm.getEmpknowledge() == 1) {
				leaveSellResponse.setCommutationStatus("Approved");

			}

			if (dbm.getApproved() == -1 && dbm.getEmpknowledge() == 1) {
				leaveSellResponse.setCommutationStatus("Rejected");

			}

			leaveSellResponse.setEmpknowledge(dbm.getEmpknowledge());

			if (dbm.getEmpknowledge() == 0) {
				leaveSellResponse.setEmpknowledgement("Not Acknowledged");
			}

			if (dbm.getEmpknowledge() == 1) {
				leaveSellResponse.setEmpknowledgement("Acknowledged");
			}

			leaveSellResponse.setYear(dbm.getYear());
			leaveSellResponse.setMonth(dbm.getMonth());
			leaveSellResponse.setReason(dbm.getReason());
			if (dbm.getEmployeeid() != 0 && hrmsEmployeeRepository.existsById(dbm.getEmployeeid())) {
				HrmsEmployee hrmsEmployee2 = hrmsEmployeeRepository.findById(dbm.getEmployeeid()).get();

				StringBuilder fullname = new StringBuilder();
				fullname.append(hrmsEmployee2.getFirstName().trim());
				if (hrmsEmployee2.getMiddleName() != null) {
					fullname.append(" " + hrmsEmployee2.getMiddleName().trim());
				}
				fullname.append(" " + hrmsEmployee2.getLastName().trim());
				leaveSellResponse.setEmployeeFullName(fullname.toString());
				leaveSellResponse.setEmployeeid(dbm.getEmployeeid());
			}

			if (dbm.getRequesterid() != 0 && hrmsEmployeeRepository.existsById(dbm.getRequesterid())) {
				HrmsEmployee hrmsEmployee2 = hrmsEmployeeRepository.findById(dbm.getRequesterid()).get();

				StringBuilder Requester = new StringBuilder();
				Requester.append(hrmsEmployee2.getFirstName().trim());
				if (hrmsEmployee2.getMiddleName() != null) {
					Requester.append(" " + hrmsEmployee2.getMiddleName().trim());
				}
				Requester.append(" " + hrmsEmployee2.getLastName().trim());
				leaveSellResponse.setRequester(Requester.toString());
				leaveSellResponse.setRequesterid(dbm.getRequesterid());
			}

			leaveSellResponse.setId(dbm.getId());
			if (dbm.getLeavetypeid() != 0 && hrmsLeaveTypeRepository.existsById(dbm.getLeavetypeid())) {
				leaveSellResponse.setLeavetypeid(dbm.getLeavetypeid());
				leaveSellResponse
						.setLeavetypeName(hrmsLeaveTypeRepository.findById(dbm.getLeavetypeid()).get().getName());
			}
			leaveSellResponse.setNumberofdaysSold(dbm.getNumberofdaysSold());
			if (dbm.getSolddate() != null) {
				leaveSellResponse.setSolddate(simpleDateFormat.format(dbm.getSolddate()));
			}

			return ResponseEntity.status(HttpStatus.OK).body(leaveSellResponse);

		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
		}
	}

	@Override
	public ResponseEntity<HrmsLeaveSold> updateLeaveSell(HrmsLeaveSold hrmsLeaveSold, int id, int requesterid) {
		if (hrmsLeaveSoldRepository.existsByIdAndActive(id, 1)) {

			hrmsLeaveSold.setActive(1);
			hrmsLeaveSold.setApproved(0);
			hrmsLeaveSold.setDate_updated(LocalDateTime.now());
			hrmsLeaveSold.setRequesterid(requesterid);

			Double amount = 0.00;

			// return all leave balance before updating

			HrmsLeaveBalance hrmsLeaveBalance = hrmsLeaveBalanceRepository.findByEmployeeidAndLeavetypeidAndActive(
					hrmsLeaveSold.getEmployeeid(), hrmsLeaveSold.getLeavetypeid(), 1);

			hrmsLeaveBalance.setDate_updated(LocalDateTime.now());

			if (hrmsLeaveTypeRepository.existsByIdAndActive(hrmsLeaveSold.getLeavetypeid(), 1)
					&& hrmsEmployeeRepository.existsByIdAndActive(hrmsLeaveSold.getEmployeeid(), 1)

					&& hrmsEmployeeRepository.existsByIdAndActive(requesterid, 1)
					&& hrmsLeaveSoldRepository.existsByIdAndActive(id, 1)) {

				HrmsLeaveSold hrmsLeaveSold1 = hrmsLeaveSoldRepository.findById(id).get();

				// update leave balance

				hrmsLeaveBalance.setDays(hrmsLeaveSold1.getNumberofdaysSold() + hrmsLeaveBalance.getDays());
				hrmsLeaveBalanceRepository.saveAndFlush(hrmsLeaveBalance);

				if (hrmsLeaveSoldRepository.findById(id).get().getDate_created() != null) {
					hrmsLeaveSold.setDate_created(hrmsLeaveSoldRepository.findById(id).get().getDate_created());
				}

				if (hrmsLeaveSoldRepository.findById(id).get().getUnique_id() != null) {
					hrmsLeaveSold.setUnique_id(hrmsLeaveSoldRepository.findById(id).get().getUnique_id());
				}

				if (hrmsLeaveSold1.getMonth() != 0 && hrmsLeaveSold1.getYear() != 0) {
					hrmsLeaveSold.setMonth(hrmsLeaveSold1.getMonth());
					hrmsLeaveSold.setYear(hrmsLeaveSold1.getYear());

				}

				// verify if the request is the supervisor of the Employee
				if (hrmsEmployeeRepository.findById(requesterid).get().getDesignationId() == hrmsEmployeeRepository
						.findById(hrmsLeaveSold.getEmployeeid()).get().getSupervisordesignationid()) {

					// verify the employee has the balance required to be sold
					if (hrmsLeaveBalanceRepository.existsByEmployeeidAndLeavetypeidAndActive(
							hrmsLeaveSold.getEmployeeid(), hrmsLeaveSold.getLeavetypeid(), 1)

							&& hrmsLeaveBalanceRepository
									.findByEmployeeidAndLeavetypeidAndActive(hrmsLeaveSold.getEmployeeid(),
											hrmsLeaveSold.getLeavetypeid(), 1)
									.getDays() >= hrmsLeaveSold.getNumberofdaysSold()) {
						if (hrmsemployeesalaryRepository.existsByEmployeeidAndActive(hrmsLeaveSold.getEmployeeid(),
								1)) {

							Hrmsemployeesalary hrmsemployeesalary = hrmsemployeesalaryRepository
									.findByEmployeeidAndActive(hrmsLeaveSold.getEmployeeid(), 1);

							if (hrmsSalarystructureRepository
									.existsByIdAndActive(hrmsemployeesalary.getSalarystructureId(), 1)) {
								HrmsSalarystructure hrmsSalarystructure = hrmsSalarystructureRepository
										.findById(hrmsemployeesalary.getSalarystructureId()).get();
								if (hrmsSalarystructure.getBasicSalary() != null) {
									amount = ((double) hrmsLeaveSold.getNumberofdaysSold() / 30)
											* hrmsSalarystructure.getBasicSalary();

									hrmsLeaveSold.setAmount(amount);

								}

								HrmsLeaveBalance hrmsLeaveBalance1 = hrmsLeaveBalanceRepository
										.findByEmployeeidAndLeavetypeidAndActive(hrmsLeaveSold.getEmployeeid(),
												hrmsLeaveSold.getLeavetypeid(), 1);
								hrmsLeaveBalance1.setDate_updated(LocalDateTime.now());
								hrmsLeaveBalance1
										.setDays(hrmsLeaveBalance1.getDays() - hrmsLeaveSold.getNumberofdaysSold());
								hrmsLeaveBalanceRepository.saveAndFlush(hrmsLeaveBalance1);// reduce leave balance
								return ResponseEntity.status(HttpStatus.OK)
										.body(hrmsLeaveSoldRepository.saveAndFlush(hrmsLeaveSold));
							} else {
								return ResponseEntity.status(HttpStatus.NO_CONTENT).body(hrmsLeaveSold);
							}
						} else {
							return ResponseEntity.status(HttpStatus.NON_AUTHORITATIVE_INFORMATION).body(hrmsLeaveSold);
						}
					} else {
						return ResponseEntity.status(HttpStatus.LOOP_DETECTED).body(hrmsLeaveSold);
					}
				} else {
					return ResponseEntity.status(HttpStatus.UNAVAILABLE_FOR_LEGAL_REASONS).body(hrmsLeaveSold);
				}

				// return
				// ResponseEntity.status(HttpStatus.OK).body(hrmsLeaveSoldRepository.saveAndFlush(hrmsLeaveSold));
			} else {
				return ResponseEntity.status(HttpStatus.PRECONDITION_FAILED).body(hrmsLeaveSold);
			}
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
		}
	}

	@Override
	public ResponseEntity<?> deleteLeaveSell(int id) {
		if (hrmsLeaveSoldRepository.existsByIdAndActiveAndEmpknowledge(id, 1, 0)) {
			HrmsLeaveSold hrmsLeaveSold = hrmsLeaveSoldRepository.findByIdAndActive(id, 1);
			hrmsLeaveSold.setActive(0);
			hrmsLeaveSold.setDate_updated(LocalDateTime.now());

			// return leave which were sold

			HrmsLeaveBalance hrmsLeaveBalance = hrmsLeaveBalanceRepository.findByEmployeeidAndLeavetypeidAndActive(
					hrmsLeaveSold.getEmployeeid(), hrmsLeaveSold.getLeavetypeid(), 1);

			hrmsLeaveBalance.setDate_updated(LocalDateTime.now());

			HrmsLeaveSold hrmsLeaveSold1 = hrmsLeaveSoldRepository.findById(id).get();

			// update leave balance

			hrmsLeaveBalance.setDays(hrmsLeaveSold1.getNumberofdaysSold() + hrmsLeaveBalance.getDays());
			hrmsLeaveBalanceRepository.saveAndFlush(hrmsLeaveBalance);

			return ResponseEntity.status(HttpStatus.OK).body(hrmsLeaveSoldRepository.saveAndFlush(hrmsLeaveSold));
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
		}
	}

	@Override
	public ResponseEntity<List<LeaveSellResponse>> getAllLeaveSell() {
		String pattern = "yyyy-MM-dd";
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
		List<LeaveSellResponse> leaveSellResponselist = new ArrayList<>();
		List<HrmsLeaveSold> dbms = hrmsLeaveSoldRepository.findByActiveOrderByIdDesc(1);
		dbms.forEach(dbm -> {
			LeaveSellResponse leaveSellResponse = new LeaveSellResponse();
			leaveSellResponse.setActive(dbm.getActive());
			leaveSellResponse.setAmount(dbm.getAmount());
			leaveSellResponse.setApproved(dbm.getApproved());

			if (dbm.getApproved() == 0 && dbm.getEmpknowledge() == 0) {
				leaveSellResponse.setCommutationStatus("Submitted");

			}

			if (dbm.getApproved() == 0 && dbm.getEmpknowledge() == 1) {
				leaveSellResponse.setCommutationStatus("On Progress");

			}

			if (dbm.getApproved() == 1 && dbm.getEmpknowledge() == 1) {
				leaveSellResponse.setCommutationStatus("Approved");

			}

			if (dbm.getApproved() == -1 && dbm.getEmpknowledge() == 1) {
				leaveSellResponse.setCommutationStatus("Rejected");

			}

			leaveSellResponse.setEmpknowledge(dbm.getEmpknowledge());

			if (dbm.getEmpknowledge() == 0) {
				leaveSellResponse.setEmpknowledgement("Not Acknowledged");
			}

			if (dbm.getEmpknowledge() == 1) {
				leaveSellResponse.setEmpknowledgement("Acknowledged");
			}

			leaveSellResponse.setReason(dbm.getReason());

			leaveSellResponse.setYear(dbm.getYear());
			leaveSellResponse.setMonth(dbm.getMonth());
			if (dbm.getEmployeeid() != 0 && hrmsEmployeeRepository.existsById(dbm.getEmployeeid())) {
				HrmsEmployee hrmsEmployee2 = hrmsEmployeeRepository.findById(dbm.getEmployeeid()).get();

				StringBuilder fullname = new StringBuilder();
				fullname.append(hrmsEmployee2.getFirstName().trim());
				if (hrmsEmployee2.getMiddleName() != null) {
					fullname.append(" " + hrmsEmployee2.getMiddleName().trim());
				}
				fullname.append(" " + hrmsEmployee2.getLastName().trim());
				leaveSellResponse.setEmployeeFullName(fullname.toString());
				leaveSellResponse.setEmployeeid(dbm.getEmployeeid());
			}

			if (dbm.getRequesterid() != 0 && hrmsEmployeeRepository.existsById(dbm.getRequesterid())) {
				HrmsEmployee hrmsEmployee2 = hrmsEmployeeRepository.findById(dbm.getRequesterid()).get();

				StringBuilder Requester = new StringBuilder();
				Requester.append(hrmsEmployee2.getFirstName().trim());
				if (hrmsEmployee2.getMiddleName() != null) {
					Requester.append(" " + hrmsEmployee2.getMiddleName().trim());
				}
				Requester.append(" " + hrmsEmployee2.getLastName().trim());
				leaveSellResponse.setRequester(Requester.toString());
				leaveSellResponse.setRequesterid(dbm.getRequesterid());
			}

			leaveSellResponse.setId(dbm.getId());
			if (dbm.getLeavetypeid() != 0 && hrmsLeaveTypeRepository.existsById(dbm.getLeavetypeid())) {
				leaveSellResponse.setLeavetypeid(dbm.getLeavetypeid());
				leaveSellResponse
						.setLeavetypeName(hrmsLeaveTypeRepository.findById(dbm.getLeavetypeid()).get().getName());
			}
			leaveSellResponse.setNumberofdaysSold(dbm.getNumberofdaysSold());
			if (dbm.getSolddate() != null) {
				leaveSellResponse.setSolddate(simpleDateFormat.format(dbm.getSolddate()));
			}
			leaveSellResponselist.add(leaveSellResponse);
		});

		return ResponseEntity.status(HttpStatus.OK).body(leaveSellResponselist);
	}

	@Override
	public ResponseEntity<List<LeaveSellResponse>> getLeaveSellByEmpId(int empId) {
		String pattern = "yyyy-MM-dd";
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);

		List<LeaveSellResponse> leaveSellResponselist = new ArrayList<>();
		List<HrmsLeaveSold> dbms = hrmsLeaveSoldRepository.findByEmployeeidAndActiveOrderByIdDesc(empId, 1);
		dbms.forEach(dbm -> {
			LeaveSellResponse leaveSellResponse = new LeaveSellResponse();
			leaveSellResponse.setActive(dbm.getActive());
			leaveSellResponse.setAmount(dbm.getAmount());
			leaveSellResponse.setApproved(dbm.getApproved());

			if (dbm.getApproved() == 0 && dbm.getEmpknowledge() == 0) {
				leaveSellResponse.setCommutationStatus("Submitted");

			}

			if (dbm.getApproved() == 0 && dbm.getEmpknowledge() == 1) {
				leaveSellResponse.setCommutationStatus("On Progress");

			}

			if (dbm.getApproved() == 1 && dbm.getEmpknowledge() == 1) {
				leaveSellResponse.setCommutationStatus("Approved");

			}

			if (dbm.getApproved() == -1 && dbm.getEmpknowledge() == 1) {
				leaveSellResponse.setCommutationStatus("Rejected");

			}

			leaveSellResponse.setEmpknowledge(dbm.getEmpknowledge());

			if (dbm.getEmpknowledge() == 0) {
				leaveSellResponse.setEmpknowledgement("Not Acknowledged");
			}

			if (dbm.getEmpknowledge() == 1) {
				leaveSellResponse.setEmpknowledgement("Acknowledged");
			}

			leaveSellResponse.setReason(dbm.getReason());

			leaveSellResponse.setYear(dbm.getYear());
			leaveSellResponse.setMonth(dbm.getMonth());
			if (dbm.getEmployeeid() != 0 && hrmsEmployeeRepository.existsById(dbm.getEmployeeid())) {
				HrmsEmployee hrmsEmployee2 = hrmsEmployeeRepository.findById(dbm.getEmployeeid()).get();

				StringBuilder fullname = new StringBuilder();
				fullname.append(hrmsEmployee2.getFirstName().trim());
				if (hrmsEmployee2.getMiddleName() != null) {
					fullname.append(" " + hrmsEmployee2.getMiddleName().trim());
				}
				fullname.append(" " + hrmsEmployee2.getLastName().trim());
				leaveSellResponse.setEmployeeFullName(fullname.toString());
				leaveSellResponse.setEmployeeid(dbm.getEmployeeid());
			}

			if (dbm.getRequesterid() != 0 && hrmsEmployeeRepository.existsById(dbm.getRequesterid())) {
				HrmsEmployee hrmsEmployee2 = hrmsEmployeeRepository.findById(dbm.getRequesterid()).get();

				StringBuilder Requester = new StringBuilder();
				Requester.append(hrmsEmployee2.getFirstName().trim());
				if (hrmsEmployee2.getMiddleName() != null) {
					Requester.append(" " + hrmsEmployee2.getMiddleName().trim());
				}
				Requester.append(" " + hrmsEmployee2.getLastName().trim());
				leaveSellResponse.setRequester(Requester.toString());
				leaveSellResponse.setRequesterid(dbm.getRequesterid());
			}

			leaveSellResponse.setId(dbm.getId());
			if (dbm.getLeavetypeid() != 0 && hrmsLeaveTypeRepository.existsById(dbm.getLeavetypeid())) {
				leaveSellResponse.setLeavetypeid(dbm.getLeavetypeid());
				leaveSellResponse
						.setLeavetypeName(hrmsLeaveTypeRepository.findById(dbm.getLeavetypeid()).get().getName());
			}
			leaveSellResponse.setNumberofdaysSold(dbm.getNumberofdaysSold());
			if (dbm.getSolddate() != null) {
				leaveSellResponse.setSolddate(simpleDateFormat.format(dbm.getSolddate()));
			}
			leaveSellResponselist.add(leaveSellResponse);
		});

		return ResponseEntity.status(HttpStatus.OK).body(leaveSellResponselist);

	}

	@Override
	public ResponseEntity<Double> getCommutableAmount(int empid, int numberofdays) {

		Double amount = 0.0;
		if (hrmsemployeesalaryRepository.existsByEmployeeidAndActive(empid, 1)) {

			Hrmsemployeesalary hrmsemployeesalary = hrmsemployeesalaryRepository.findByEmployeeidAndActive(empid, 1);

			if (hrmsSalarystructureRepository.existsByIdAndActive(hrmsemployeesalary.getSalarystructureId(), 1)) {
				HrmsSalarystructure hrmsSalarystructure = hrmsSalarystructureRepository
						.findById(hrmsemployeesalary.getSalarystructureId()).get();
				if (hrmsSalarystructure.getBasicSalary() != null) {
					amount = ((double) numberofdays / 30) * hrmsSalarystructure.getBasicSalary();

				}

			}

		}
		return ResponseEntity.status(HttpStatus.OK).body(amount);
	}

	@Override
	public ResponseEntity<Double> getCommutableAmountV2(int empid, int numberofdays) {

		Double amount = 0.0;
		if (hrmsemployeesalaryRepository.existsByEmployeeidAndActive(empid, 1)) {

			Hrmsemployeesalary hrmsemployeesalary = hrmsemployeesalaryRepository.findByEmployeeidAndActive(empid, 1);

			if (hrmsSalarystructureRepository.existsByIdAndActive(hrmsemployeesalary.getSalarystructureId(), 1)) {
				HrmsSalarystructure hrmsSalarystructure = hrmsSalarystructureRepository
						.findById(hrmsemployeesalary.getSalarystructureId()).get();
				if (hrmsSalarystructure.getBasicSalary() != null) {
					amount = ((double) numberofdays / 28) * hrmsSalarystructure.getBasicSalary();

				}

			}

		}
		return ResponseEntity.status(HttpStatus.OK).body(amount);
	}

	@Override
	public ResponseEntity<?> ApproveLeaveCommutation(int leaveid, int supervisorid, int status, String comment) {
		if (hrmsLeaveSoldRepository.existsByIdAndActiveAndApprovedAndEmpknowledge(leaveid, 1, 0, 1)
				&& hrmsEmployeeRepository.existsByIdAndIssupervisorAndActive(supervisorid, 1, 1)) {

			HrmsEmployee hrmsEmployee = hrmsEmployeeRepository.findByIdAndIssupervisorAndActive(supervisorid, 1, 1);

			HrmsLeaveSold hrmsLeave = hrmsLeaveSoldRepository.findByIdAndActive(leaveid, 1);

			int employeeid = hrmsLeave.getEmployeeid();

			int firstsupervisordesignationid = 0;
			if (hrmsEmployeeRepository.existsByIdAndActive(employeeid, 1)) {
				int firstsupervisordesignationid1 = hrmsEmployeeRepository.findByIdAndActive(employeeid, 1)
						.getSupervisordesignationid();

				firstsupervisordesignationid = hrmsEmployeeRepository
						.findByDesignationIdAndEmploymentstatusidAndActive(firstsupervisordesignationid1, 2, 1)
						.getSupervisordesignationid();

			}
			List<Integer> units = new ArrayList<>();
			units.add(178); // DCRM
			if (hrmsOrginisationUnitRepository.existsByIdAndActiveAndUnitTypeid(
					hrmsEmployeeRepository.findByIdAndActive(hrmsLeave.getEmployeeid(), 1).getUnitId(), 1, 33)) {

				units.add(hrmsEmployeeRepository.findByIdAndActive(hrmsLeave.getEmployeeid(), 1).getUnitId());

			}

			if (hrmsEmployeeRepository.existsByIdAndIssupervisorAndActiveAndUnitIdIn(hrmsLeave.getEmployeeid(), 0, 1,
					units)) {

				HrmsEmployee hrmsEmployee3 = hrmsEmployeeRepository
						.findFirstByDesignationIdAndEmploymentstatusidAndActiveAndIssupervisor(360, 2, 1, 1);// DCRM
				if (hrmsEmployee3 != null) {
					firstsupervisordesignationid = 360;// DCRM Designation
				}

			}

			int workflowid = 0;
			if (hrmsLeaveCommutationApprovalWorkflowRepository
					.existsBySupervisordesignationidAndActive(firstsupervisordesignationid, 1)) {

				HrmsLeaveCommutationApprovalWorkflow hrmsLeaveCommuatationApprovalWorkflow = hrmsLeaveCommutationApprovalWorkflowRepository
						.findBySupervisordesignationidAndActive(firstsupervisordesignationid, 1);

				workflowid = hrmsLeaveCommuatationApprovalWorkflow.getId();

				if (hrmsLeaveCommutationApprovalWorkflowStepRepository
						.existsByWorkflowidAndApproverdesignationidAndActive(workflowid,
								hrmsEmployee.getDesignationId(), 1)) {

					HrmsLeaveCommutationApprovalWorkflowStep step = hrmsLeaveCommutationApprovalWorkflowStepRepository
							.findByWorkflowidAndApproverdesignationidAndActive(workflowid,
									hrmsEmployee.getDesignationId(), 1);

					int stepnext = 0;
					if (step.getApproverdesignationnextid() != 0) {
						HrmsLeaveCommutationApprovalWorkflowStep step1 = hrmsLeaveCommutationApprovalWorkflowStepRepository
								.findByWorkflowidAndApproverdesignationidAndActive(workflowid,
										step.getApproverdesignationnextid(), 1);

						stepnext = step1.getStepnumber();
					}

					HrmsLeaveCommutationApproval hrmsLeaveApproval = new HrmsLeaveCommutationApproval();

					hrmsLeaveApproval.setActive(1);
					hrmsLeaveApproval.setApproved(1);

					StringBuilder approvedby = new StringBuilder();

					if (hrmsEmployee.getFirstName() != null) {
						approvedby.append(hrmsEmployee.getFirstName().trim());
					}

					if (hrmsEmployee.getMiddleName() != null) {
						approvedby.append(" " + hrmsEmployee.getMiddleName().trim());
					}
					if (hrmsEmployee.getLastName() != null) {
						approvedby.append(" " + hrmsEmployee.getLastName().trim());
					}

					hrmsLeaveApproval.setApprovedby(approvedby.toString());

					if (hrmsEmployee.getDesignationId() != 0
							&& hrmsDesignationRepository.existsByIdAndActive(hrmsEmployee.getDesignationId(), 1)) {
						hrmsLeaveApproval.setApproverdesignationid(hrmsEmployee.getDesignationId());
					}

					hrmsLeaveApproval.setDate_created(LocalDateTime.now());

					hrmsLeaveApproval.setDescription(comment);
					hrmsLeaveApproval.setStepid(step.getId());
					hrmsLeaveApproval.setStepnumber(step.getStepnumber());
					hrmsLeaveApproval.setStepnumbernext(stepnext);
					hrmsLeaveApproval.setLeaveid(leaveid);
					hrmsLeaveApproval.setUnique_id(UUID.randomUUID());
					hrmsLeaveApproval.setWorkflowid(workflowid);
					hrmsLeaveApproval.setApproveruserid(supervisorid);

					hrmsLeaveApproval.setStatus(status);

					hrmsLeaveCommutationApprovalRepository.saveAndFlush(hrmsLeaveApproval);

					if (hrmsLeaveCommutationApprovalRepository.countByLeaveid(
							leaveid) == hrmsLeaveCommutationApprovalWorkflowStepRepository.countByWorkflowid(workflowid)
							&& status == 1) {

						hrmsLeave.setDate_updated(LocalDateTime.now());
						hrmsLeave.setApproved(1);

						hrmsLeaveSoldRepository.saveAndFlush(hrmsLeave);

						return ResponseEntity.status(HttpStatus.OK).body(hrmsLeave);

					} else {
						if (status == 0) {

							hrmsLeave.setDate_updated(LocalDateTime.now());
							hrmsLeave.setApproved(2);// rejected

//release leave

							// return leave which were sold

							HrmsLeaveBalance hrmsLeaveBalance = hrmsLeaveBalanceRepository
									.findByEmployeeidAndLeavetypeidAndActive(hrmsLeave.getEmployeeid(),
											hrmsLeave.getLeavetypeid(), 1);

							hrmsLeaveBalance.setDate_updated(LocalDateTime.now());

							HrmsLeaveSold hrmsLeaveSold1 = hrmsLeaveSoldRepository.findById(leaveid).get();

							// update leave balance

							hrmsLeaveBalance.setDays(hrmsLeaveSold1.getNumberofdaysSold() + hrmsLeaveBalance.getDays());
							hrmsLeaveBalanceRepository.saveAndFlush(hrmsLeaveBalance);

							hrmsLeaveSoldRepository.saveAndFlush(hrmsLeave);

							return ResponseEntity.status(HttpStatus.OK).body(hrmsLeave);

						} else {

							return ResponseEntity.status(HttpStatus.OK).body(hrmsLeave);
						}
					}

				} else {
					return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).body(workflowid);
				}
			} else {
				return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).body(leaveid);
			}

		} else {

			return ResponseEntity.status(HttpStatus.FAILED_DEPENDENCY).body(leaveid);
		}
	}

	@Override
	public ResponseEntity<List<LeaveApprovalStatus>> getLeaveCommutationApprovers(int id) {
		String pattern = "yyyy-MM-dd";
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);

		List<LeaveApprovalStatus> leaveApprovalStatuslist = new ArrayList<>();

		if (hrmsLeaveSoldRepository.existsByIdAndActiveAndEmpknowledge(id, 1, 1)) {

			HrmsLeaveSold hrmsLeave = hrmsLeaveSoldRepository.findByIdAndActive(id, 1);

			int supervisordesignationid = 0;
			if (hrmsLeave.getEmployeeid() != 0
					&& hrmsEmployeeRepository.existsByIdAndActive(hrmsLeave.getEmployeeid(), 1)) {

				int headdesignation = hrmsEmployeeRepository.findByIdAndActive(hrmsLeave.getEmployeeid(), 1)
						.getSupervisordesignationid();

				supervisordesignationid = hrmsEmployeeRepository
						.findByDesignationIdAndEmploymentstatusidAndActive(headdesignation, 2, 1)
						.getSupervisordesignationid();
			}

			List<Integer> units = new ArrayList<>();// DCRM employee and Units
			units.add(178); // DCRM
			if (hrmsOrginisationUnitRepository.existsByIdAndActiveAndUnitTypeid(
					hrmsEmployeeRepository.findByIdAndActive(hrmsLeave.getEmployeeid(), 1).getUnitId(), 1, 33)) {

				units.add(hrmsEmployeeRepository.findByIdAndActive(hrmsLeave.getEmployeeid(), 1).getUnitId());

			}

			if (hrmsEmployeeRepository.existsByIdAndIssupervisorAndActiveAndUnitIdIn(hrmsLeave.getEmployeeid(), 0, 1,
					units)) {

				HrmsEmployee hrmsEmployee3 = hrmsEmployeeRepository
						.findFirstByDesignationIdAndEmploymentstatusidAndActiveAndIssupervisor(360, 2, 1, 1);// DCRM
				if (hrmsEmployee3 != null) {
					supervisordesignationid = 360;// DCRM Designation
				}

			}

			if (hrmsLeaveCommutationApprovalWorkflowRepository
					.existsBySupervisordesignationidAndActive(supervisordesignationid, 1)) {
				int workflowid = hrmsLeaveCommutationApprovalWorkflowRepository
						.findBySupervisordesignationidAndActive(supervisordesignationid, 1).getId();

				if (hrmsLeaveCommutationApprovalWorkflowStepRepository.existsByWorkflowidAndActive(workflowid, 1)) {

					List<HrmsLeaveCommutationApprovalWorkflowStep> dbms = hrmsLeaveCommutationApprovalWorkflowStepRepository
							.findByWorkflowidAndActiveOrderByStepnumberAsc(workflowid, 1);

					for (HrmsLeaveCommutationApprovalWorkflowStep dbm : dbms) {

						HrmsEmployee hrmsEmployee = hrmsEmployeeRepository
								.findFirstByDesignationIdAndIssupervisorAndActive(dbm.getApproverdesignationid(), 1, 1);

						if (hrmsLeaveCommutationApprovalRepository.existsByApproverdesignationidAndLeaveidAndActive(
								dbm.getApproverdesignationid(), id, 1)) {

							HrmsLeaveCommutationApproval hrmsLeaveApproval = hrmsLeaveCommutationApprovalRepository
									.findByApproverdesignationidAndLeaveidAndActive(dbm.getApproverdesignationid(), id,
											1);

							LeaveApprovalStatus leaveApprovalStatus = new LeaveApprovalStatus();

							if (hrmsLeaveApproval != null && hrmsLeaveApproval.getStatus() == 1) {
								leaveApprovalStatus.setApprovalStatus("Approved");
							}

							if (hrmsLeaveApproval != null && hrmsLeaveApproval.getStatus() == 0) {
								leaveApprovalStatus.setApprovalStatus("Rejected");
							}

							if (hrmsLeaveApproval == null) {
								leaveApprovalStatus.setApprovalStatus("Pending");
							}

							leaveApprovalStatus.setApproved(hrmsLeave.getApproved());

							if (hrmsLeaveApproval != null && hrmsLeaveApproval.getDate_created() != null) {

								String dateapproved = hrmsLeaveApproval.getDate_created()
										.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));

								leaveApprovalStatus.setDateApproved(dateapproved);
							}
							if (hrmsLeaveApproval != null) {
								leaveApprovalStatus.setDescription(hrmsLeaveApproval.getDescription());
							}

							leaveApprovalStatus.setId(id);
							if (hrmsEmployee != null && hrmsEmployee.getFirstName() != null) {

								char initial = hrmsEmployee.getFirstName().charAt(0);
								leaveApprovalStatus.setSupervisorInitial(initial);
							}

							if (hrmsEmployee != null) {

								StringBuilder supervisorName = new StringBuilder();

								supervisorName.append(hrmsEmployee.getFirstName().trim());
								if (hrmsEmployee.getMiddleName() != null) {
									supervisorName.append(" " + hrmsEmployee.getMiddleName().trim());
								}

								supervisorName.append(" " + hrmsEmployee.getLastName().trim());

								leaveApprovalStatus.setSupervisorName(supervisorName.toString());

							}
							if (hrmsLeave.getApproved() == 1) {
								leaveApprovalStatus.setLeaveStatus("Approved");
							}

							if (hrmsLeave.getApproved() == 0) {
								leaveApprovalStatus.setLeaveStatus("On Progress");
							}

							if (hrmsLeave.getApproved() == -1) {
								leaveApprovalStatus.setLeaveStatus("Rejected");
							}

							leaveApprovalStatuslist.add(leaveApprovalStatus);

						} else {
							// approver has not yet approved so get his/her details in a way around that
							// checking on approval table

							// confirm or check if previous approver rejected this training then do not
							// return the rest approvers
							if (dbm.getApproverdesignationprevid() != 0 && hrmsLeaveCommutationApprovalRepository
									.existsByStatusAndLeaveidAndActive(0, id, 1)) {

							} else {

								LeaveApprovalStatus leaveApprovalStatus = new LeaveApprovalStatus();

								leaveApprovalStatus.setApprovalStatus("Pending");

								leaveApprovalStatus.setApproved(hrmsLeave.getApproved());

								// do not set date approved as it does not exist
								leaveApprovalStatus.setDateApproved("");

								// do not set description of approval as it does not exist
								leaveApprovalStatus.setDescription("");

								leaveApprovalStatus.setId(id);
								if (hrmsEmployee != null && hrmsEmployee.getFirstName() != null) {

									char initial = hrmsEmployee.getFirstName().charAt(0);
									leaveApprovalStatus.setSupervisorInitial(initial);
								}

								if (hrmsEmployee != null) {

									StringBuilder supervisorName = new StringBuilder();

									supervisorName.append(hrmsEmployee.getFirstName().trim());
									if (hrmsEmployee.getMiddleName() != null) {
										supervisorName.append(" " + hrmsEmployee.getMiddleName().trim());
									}

									supervisorName.append(" " + hrmsEmployee.getLastName().trim());

									leaveApprovalStatus.setSupervisorName(supervisorName.toString());

								}
								if (hrmsLeave.getApproved() == 1) {
									leaveApprovalStatus.setLeaveStatus("Approved");
								}

								if (hrmsLeave.getApproved() == 0) {
									leaveApprovalStatus.setLeaveStatus("On Progress");
								}

								if (hrmsLeave.getApproved() == -1) {
									leaveApprovalStatus.setLeaveStatus("Rejected");
								}

								if (hrmsLeave.getApproved() == 2) {
									leaveApprovalStatus.setLeaveStatus("Rejected");
								}
								if (hrmsLeave.getApproved() == 3) {
									leaveApprovalStatus.setLeaveStatus("Cancelled by System");
								}

								leaveApprovalStatuslist.add(leaveApprovalStatus);

							}

						}

					}

				}

			}

		}

		return ResponseEntity.status(HttpStatus.OK).body(leaveApprovalStatuslist);
	}

	@Override
	public ResponseEntity<List<LeaveSellResponse>> getHrmsLeaveCommutationNotApprovedBySupervisorNext(
			int supervisorid) {
		String pattern = "yyyy-MM-dd";
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);

		List<LeaveSellResponse> leaveApplicationsResponselist = new ArrayList<>();

		if (hrmsEmployeeRepository.existsByIdAndIssupervisorAndActive(supervisorid, 1, 1)) {

			List<HrmsLeaveSold> dbms = hrmsLeaveSoldRepository.findByApprovedAndActiveAndEmpknowledge(0, 1, 1);

			for (HrmsLeaveSold dbm : dbms) {
				int supervisordbm = 0;
				if (hrmsEmployeeRepository.existsByIdAndActive(dbm.getEmployeeid(), 1)) {
					List<Integer> units = new ArrayList<>();// DCRM employee and Units
					units.add(178); // DCRM
					if (hrmsOrginisationUnitRepository.existsByIdAndActiveAndUnitTypeid(
							hrmsEmployeeRepository.findByIdAndActive(dbm.getEmployeeid(), 1).getUnitId(), 1, 33)) {

						units.add(hrmsEmployeeRepository.findByIdAndActive(dbm.getEmployeeid(), 1).getUnitId());

					}

					if (hrmsEmployeeRepository.existsByIdAndIssupervisorAndActiveAndUnitIdIn(dbm.getEmployeeid(), 0, 1,
							units)) {

						HrmsEmployee hrmsEmployee3 = hrmsEmployeeRepository
								.findFirstByDesignationIdAndEmploymentstatusidAndActiveAndIssupervisor(360, 2, 1, 1);// DCRM
						if (hrmsEmployee3 != null) {
							supervisordbm = hrmsEmployee3.getId();
						}

					} else {

						int headdesignation = hrmsEmployeeRepository.findByIdAndActive(dbm.getEmployeeid(), 1)
								.getSupervisordesignationid();

						supervisordbm = hrmsEmployeeRepository
								.findByDesignationIdAndEmploymentstatusidAndActive(headdesignation, 2, 1)
								.getSupervisorId();
					}

					/*
					 * supervisordbm = hrmsEmployeeRepository
					 * .findByIdAndActiveAndEmploymentstatusid(dbm.getEmployeeid(), 1,
					 * 2).getSupervisorId();
					 */
				}

				if (supervisorid == supervisordbm) {

					if (hrmsLeaveCommutationApprovalRepository.existsByApproveruseridAndLeaveidAndActive(supervisorid,
							dbm.getId(), 1)) {
						// do nothing you already approved it
					} else {

						LeaveSellResponse leaveSellResponse = new LeaveSellResponse();
						leaveSellResponse.setActive(dbm.getActive());
						leaveSellResponse.setAmount(dbm.getAmount());
						leaveSellResponse.setApproved(dbm.getApproved());

						if (dbm.getApproved() == 0 && dbm.getEmpknowledge() == 0) {
							leaveSellResponse.setCommutationStatus("Submitted");

						}

						if (dbm.getApproved() == 0 && dbm.getEmpknowledge() == 1) {
							leaveSellResponse.setCommutationStatus("On Progress");

						}

						if (dbm.getApproved() == 1 && dbm.getEmpknowledge() == 1) {
							leaveSellResponse.setCommutationStatus("Approved");

						}

						if (dbm.getApproved() == -1 && dbm.getEmpknowledge() == 1) {
							leaveSellResponse.setCommutationStatus("Rejected");

						}

						leaveSellResponse.setEmpknowledge(dbm.getEmpknowledge());

						if (dbm.getEmpknowledge() == 0) {
							leaveSellResponse.setEmpknowledgement("Not Acknowledged");
						}

						if (dbm.getEmpknowledge() == 1) {
							leaveSellResponse.setEmpknowledgement("Acknowledged");
						}

						leaveSellResponse.setYear(dbm.getYear());
						leaveSellResponse.setMonth(dbm.getMonth());
						leaveSellResponse.setReason(dbm.getReason());
						if (dbm.getEmployeeid() != 0 && hrmsEmployeeRepository.existsById(dbm.getEmployeeid())) {
							HrmsEmployee hrmsEmployee2 = hrmsEmployeeRepository.findById(dbm.getEmployeeid()).get();

							StringBuilder fullname = new StringBuilder();
							fullname.append(hrmsEmployee2.getFirstName().trim());
							if (hrmsEmployee2.getMiddleName() != null) {
								fullname.append(" " + hrmsEmployee2.getMiddleName().trim());
							}
							fullname.append(" " + hrmsEmployee2.getLastName().trim());
							leaveSellResponse.setEmployeeFullName(fullname.toString());
							leaveSellResponse.setEmployeeid(dbm.getEmployeeid());
						}

						if (dbm.getRequesterid() != 0 && hrmsEmployeeRepository.existsById(dbm.getRequesterid())) {
							HrmsEmployee hrmsEmployee2 = hrmsEmployeeRepository.findById(dbm.getRequesterid()).get();

							StringBuilder Requester = new StringBuilder();
							Requester.append(hrmsEmployee2.getFirstName().trim());
							if (hrmsEmployee2.getMiddleName() != null) {
								Requester.append(" " + hrmsEmployee2.getMiddleName().trim());
							}
							Requester.append(" " + hrmsEmployee2.getLastName().trim());
							leaveSellResponse.setRequester(Requester.toString());
							leaveSellResponse.setRequesterid(dbm.getRequesterid());
						}

						leaveSellResponse.setId(dbm.getId());
						if (dbm.getLeavetypeid() != 0 && hrmsLeaveTypeRepository.existsById(dbm.getLeavetypeid())) {
							leaveSellResponse.setLeavetypeid(dbm.getLeavetypeid());
							leaveSellResponse.setLeavetypeName(
									hrmsLeaveTypeRepository.findById(dbm.getLeavetypeid()).get().getName());
						}
						leaveSellResponse.setNumberofdaysSold(dbm.getNumberofdaysSold());
						if (dbm.getSolddate() != null) {
							leaveSellResponse.setSolddate(simpleDateFormat.format(dbm.getSolddate()));
						}

						leaveApplicationsResponselist.add(leaveSellResponse);

					}

				} else {

					// check if first supervisor has approved and this user has not approved

					if (hrmsLeaveCommutationApprovalRepository.existsByApproveruseridAndLeaveidAndActive(supervisordbm,
							dbm.getId(), 1)

							&& !hrmsLeaveCommutationApprovalRepository
									.existsByApproveruseridAndLeaveidAndActive(supervisorid, dbm.getId(), 1)) {

						// check if this supervisor is the next to approve by verifying if the back to
						// this has approved

						int workflowid = hrmsLeaveCommutationApprovalRepository
								.findByApproveruseridAndLeaveidAndActive(supervisordbm, dbm.getId(), 1).getWorkflowid();

						// check if the step number of this supervisor on step

						// get supervisor designation

						HrmsEmployee hrmsEmployee = hrmsEmployeeRepository.findById(supervisorid).get();

						if (hrmsLeaveCommutationApprovalWorkflowStepRepository
								.existsByWorkflowidAndApproverdesignationidAndActive(workflowid,
										hrmsEmployee.getDesignationId(), 1)) {

							HrmsLeaveCommutationApprovalWorkflowStep step = hrmsLeaveCommutationApprovalWorkflowStepRepository
									.findByWorkflowidAndApproverdesignationidAndActive(workflowid,
											hrmsEmployee.getDesignationId(), 1);

							int stepnow = step.getStepnumber();

							int designationprev = step.getApproverdesignationprevid();

							// check if this user has approved already

							if (hrmsLeaveCommutationApprovalRepository
									.existsByApproverdesignationidAndLeaveidAndActiveAndStatus(designationprev,
											dbm.getId(), 1, 1)) {

								// add this leave as it deserves to be approved
								LeaveSellResponse leaveSellResponse = new LeaveSellResponse();
								leaveSellResponse.setActive(dbm.getActive());
								leaveSellResponse.setAmount(dbm.getAmount());
								leaveSellResponse.setApproved(dbm.getApproved());

								if (dbm.getApproved() == 0 && dbm.getEmpknowledge() == 0) {
									leaveSellResponse.setCommutationStatus("Submitted");

								}

								if (dbm.getApproved() == 0 && dbm.getEmpknowledge() == 1) {
									leaveSellResponse.setCommutationStatus("On Progress");

								}

								if (dbm.getApproved() == 1 && dbm.getEmpknowledge() == 1) {
									leaveSellResponse.setCommutationStatus("Approved");

								}

								if (dbm.getApproved() == -1 && dbm.getEmpknowledge() == 1) {
									leaveSellResponse.setCommutationStatus("Rejected");

								}

								leaveSellResponse.setEmpknowledge(dbm.getEmpknowledge());

								if (dbm.getEmpknowledge() == 0) {
									leaveSellResponse.setEmpknowledgement("Not Acknowledged");
								}

								if (dbm.getEmpknowledge() == 1) {
									leaveSellResponse.setEmpknowledgement("Acknowledged");
								}

								leaveSellResponse.setYear(dbm.getYear());
								leaveSellResponse.setMonth(dbm.getMonth());
								leaveSellResponse.setReason(dbm.getReason());
								if (dbm.getEmployeeid() != 0
										&& hrmsEmployeeRepository.existsById(dbm.getEmployeeid())) {
									HrmsEmployee hrmsEmployee2 = hrmsEmployeeRepository.findById(dbm.getEmployeeid())
											.get();

									StringBuilder fullname = new StringBuilder();
									fullname.append(hrmsEmployee2.getFirstName().trim());
									if (hrmsEmployee2.getMiddleName() != null) {
										fullname.append(" " + hrmsEmployee2.getMiddleName().trim());
									}
									fullname.append(" " + hrmsEmployee2.getLastName().trim());
									leaveSellResponse.setEmployeeFullName(fullname.toString());
									leaveSellResponse.setEmployeeid(dbm.getEmployeeid());
								}

								if (dbm.getRequesterid() != 0
										&& hrmsEmployeeRepository.existsById(dbm.getRequesterid())) {
									HrmsEmployee hrmsEmployee2 = hrmsEmployeeRepository.findById(dbm.getRequesterid())
											.get();

									StringBuilder Requester = new StringBuilder();
									Requester.append(hrmsEmployee2.getFirstName().trim());
									if (hrmsEmployee2.getMiddleName() != null) {
										Requester.append(" " + hrmsEmployee2.getMiddleName().trim());
									}
									Requester.append(" " + hrmsEmployee2.getLastName().trim());
									leaveSellResponse.setRequester(Requester.toString());
									leaveSellResponse.setRequesterid(dbm.getRequesterid());
								}

								leaveSellResponse.setId(dbm.getId());
								if (dbm.getLeavetypeid() != 0
										&& hrmsLeaveTypeRepository.existsById(dbm.getLeavetypeid())) {
									leaveSellResponse.setLeavetypeid(dbm.getLeavetypeid());
									leaveSellResponse.setLeavetypeName(
											hrmsLeaveTypeRepository.findById(dbm.getLeavetypeid()).get().getName());
								}
								leaveSellResponse.setNumberofdaysSold(dbm.getNumberofdaysSold());
								if (dbm.getSolddate() != null) {
									leaveSellResponse.setSolddate(simpleDateFormat.format(dbm.getSolddate()));
								}

								leaveApplicationsResponselist.add(leaveSellResponse);

							}

						}

					}

				}

			}

			return ResponseEntity.status(HttpStatus.OK).body(leaveApplicationsResponselist);

		} else {

			return ResponseEntity.status(HttpStatus.UNAVAILABLE_FOR_LEGAL_REASONS).body(null);
		}

	}

	@Override
	public ResponseEntity<?> AcknowledgeLeaveCommutation(int id, int empid, int acknowledge) {

		if (hrmsLeaveSoldRepository.existsByIdAndEmployeeidAndActive(id, empid, 1)
				&& hrmsEmployeeRepository.existsByIdAndActive(empid, 1)
				&& !hrmsLeaveCommutationApprovalRepository.existsByLeaveidAndActive(id, 1)) {

			HrmsLeaveSold hrmsLeaveSold = hrmsLeaveSoldRepository.findByIdAndActive(id, 1);

			hrmsLeaveSold.setEmpknowledge(acknowledge);
			hrmsLeaveSold.setDate_updated(LocalDateTime.now());

			// return leave which were sold
			if (acknowledge == 2) {// rejected
				HrmsLeaveBalance hrmsLeaveBalance = hrmsLeaveBalanceRepository.findByEmployeeidAndLeavetypeidAndActive(
						hrmsLeaveSold.getEmployeeid(), hrmsLeaveSold.getLeavetypeid(), 1);

				hrmsLeaveBalance.setDate_updated(LocalDateTime.now());

				HrmsLeaveSold hrmsLeaveSold1 = hrmsLeaveSoldRepository.findById(id).get();

				// update leave balance

				hrmsLeaveBalance.setDays(hrmsLeaveSold1.getNumberofdaysSold() + hrmsLeaveBalance.getDays());
				hrmsLeaveBalanceRepository.saveAndFlush(hrmsLeaveBalance);
			}

			return ResponseEntity.status(HttpStatus.OK).body(hrmsLeaveSoldRepository.saveAndFlush(hrmsLeaveSold));
		} else {

			return ResponseEntity.status(HttpStatus.UNAVAILABLE_FOR_LEGAL_REASONS).body(null);
		}
	}

	@Override
	public ResponseEntity<List<LeaveSellResponse>> getAllLeaveCommutedNotApprovedYet(int supervisorid) {
		String pattern = "yyyy-MM-dd";
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
		List<LeaveSellResponse> leaveSellResponselist = new ArrayList<>();
		List<HrmsLeaveSold> dbms = hrmsLeaveSoldRepository.findByActiveAndRequesteridAndApprovedOrderByIdDesc(1,
				supervisorid, 0);
		dbms.forEach(dbm -> {
			LeaveSellResponse leaveSellResponse = new LeaveSellResponse();
			leaveSellResponse.setActive(dbm.getActive());
			leaveSellResponse.setAmount(dbm.getAmount());
			leaveSellResponse.setApproved(dbm.getApproved());

			if (dbm.getApproved() == 0 && dbm.getEmpknowledge() == 0) {
				leaveSellResponse.setCommutationStatus("Submitted");

			}

			if (dbm.getApproved() == 0 && dbm.getEmpknowledge() == 1) {
				leaveSellResponse.setCommutationStatus("On Progress");

			}

			if (dbm.getApproved() == 1 && dbm.getEmpknowledge() == 1) {
				leaveSellResponse.setCommutationStatus("Approved");

			}

			if (dbm.getApproved() == -1 && dbm.getEmpknowledge() == 1) {
				leaveSellResponse.setCommutationStatus("Rejected");

			}

			leaveSellResponse.setEmpknowledge(dbm.getEmpknowledge());

			if (dbm.getEmpknowledge() == 0) {
				leaveSellResponse.setEmpknowledgement("Not Acknowledged");
			}

			if (dbm.getEmpknowledge() == 1) {
				leaveSellResponse.setEmpknowledgement("Acknowledged");
			}

			leaveSellResponse.setReason(dbm.getReason());

			leaveSellResponse.setYear(dbm.getYear());
			leaveSellResponse.setMonth(dbm.getMonth());
			if (dbm.getEmployeeid() != 0 && hrmsEmployeeRepository.existsById(dbm.getEmployeeid())) {
				HrmsEmployee hrmsEmployee2 = hrmsEmployeeRepository.findById(dbm.getEmployeeid()).get();

				StringBuilder fullname = new StringBuilder();
				fullname.append(hrmsEmployee2.getFirstName().trim());
				if (hrmsEmployee2.getMiddleName() != null) {
					fullname.append(" " + hrmsEmployee2.getMiddleName().trim());
				}
				fullname.append(" " + hrmsEmployee2.getLastName().trim());
				leaveSellResponse.setEmployeeFullName(fullname.toString());
				leaveSellResponse.setEmployeeid(dbm.getEmployeeid());
			}

			if (dbm.getRequesterid() != 0 && hrmsEmployeeRepository.existsById(dbm.getRequesterid())) {
				HrmsEmployee hrmsEmployee2 = hrmsEmployeeRepository.findById(dbm.getRequesterid()).get();

				StringBuilder Requester = new StringBuilder();
				Requester.append(hrmsEmployee2.getFirstName().trim());
				if (hrmsEmployee2.getMiddleName() != null) {
					Requester.append(" " + hrmsEmployee2.getMiddleName().trim());
				}
				Requester.append(" " + hrmsEmployee2.getLastName().trim());
				leaveSellResponse.setRequester(Requester.toString());
				leaveSellResponse.setRequesterid(dbm.getRequesterid());
			}

			leaveSellResponse.setId(dbm.getId());
			if (dbm.getLeavetypeid() != 0 && hrmsLeaveTypeRepository.existsById(dbm.getLeavetypeid())) {
				leaveSellResponse.setLeavetypeid(dbm.getLeavetypeid());
				leaveSellResponse
						.setLeavetypeName(hrmsLeaveTypeRepository.findById(dbm.getLeavetypeid()).get().getName());
			}
			leaveSellResponse.setNumberofdaysSold(dbm.getNumberofdaysSold());
			if (dbm.getSolddate() != null) {
				leaveSellResponse.setSolddate(simpleDateFormat.format(dbm.getSolddate()));
			}
			leaveSellResponselist.add(leaveSellResponse);
		});

		return ResponseEntity.status(HttpStatus.OK).body(leaveSellResponselist);
	}

}
