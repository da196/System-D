package com.Hrms.Leave.Service;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.Hrms.Employee.Entity.HrmsEmployee;
import com.Hrms.Employee.Repository.HrmsDesignationRepository;
import com.Hrms.Employee.Repository.HrmsEmployeeRepository;
import com.Hrms.Employee.Repository.HrmsOrginisationUnitRepository;
import com.Hrms.Leave.DTO.LeaveApprovalStatus;
import com.Hrms.Leave.DTO.LeaveRecallResponse;
import com.Hrms.Leave.Entity.HrmsLeaveApplications;
import com.Hrms.Leave.Entity.HrmsLeaveBalance;
import com.Hrms.Leave.Entity.HrmsLeaveRecall;
import com.Hrms.Leave.Entity.HrmsLeaveRecallApproval;
import com.Hrms.Leave.Entity.HrmsLeaveRecallApprovalWorkflow;
import com.Hrms.Leave.Entity.HrmsLeaveRecallApprovalWorkflowStep;
import com.Hrms.Leave.Repository.HrmsLeaveApplicationsRepository;
import com.Hrms.Leave.Repository.HrmsLeaveBalanceRepository;
import com.Hrms.Leave.Repository.HrmsLeaveRecallApprovalRepository;
import com.Hrms.Leave.Repository.HrmsLeaveRecallApprovalWorkflowRepository;
import com.Hrms.Leave.Repository.HrmsLeaveRecallApprovalWorkflowStepRepository;
import com.Hrms.Leave.Repository.HrmsLeaveRecallRepository;
import com.Hrms.Leave.Repository.HrmsLeaveTypeRepository;

@Service
public class HrmsLeaveRecallServiceImpl implements HrmsLeaveRecallService {

	@Autowired
	private HrmsLeaveRecallRepository hrmsLeaveRecallRepository;

	@Autowired
	private HrmsEmployeeRepository hrmsEmployeeRepository;

	@Autowired
	private HrmsLeaveTypeRepository hrmsLeaveTypeRepository;

	@Autowired
	private HrmsLeaveBalanceRepository hrmsLeaveBalanceRepository;

	@Autowired
	private HrmsLeaveApplicationsRepository hrmsLeaveApplicationsRepository;

	@Autowired
	private HrmsOrginisationUnitRepository hrmsOrginisationUnitRepository;

	@Autowired
	private HrmsLeaveRecallApprovalRepository hrmsLeaveRecallApprovalRepository;

	@Autowired
	private HrmsDesignationRepository hrmsDesignationRepository;

	@Autowired
	private HrmsLeaveRecallApprovalWorkflowRepository hrmsLeaveRecallApprovalWorkflowRepository;

	@Autowired
	private HrmsLeaveRecallApprovalWorkflowStepRepository hrmsLeaveRecallApprovalWorkflowStepRepository;

	@Override
	public ResponseEntity<HrmsLeaveRecall> requestLeaveRecall(HrmsLeaveRecall hrmsLeaveRecall, int requestedby) {

		hrmsLeaveRecall.setActive(1);
		hrmsLeaveRecall.setApproved(0);
		hrmsLeaveRecall.setUnique_id(UUID.randomUUID());
		hrmsLeaveRecall.setRequestedby(requestedby);
		hrmsLeaveRecall.setYear(LocalDateTime.now().getYear());
		hrmsLeaveRecall.setMonth(LocalDateTime.now().getMonthValue());

		String pattern = "yyyy-MM-dd";
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);

		String dateBeforeString = simpleDateFormat.format(hrmsLeaveRecall.getStartdate());

		String dateAfterString = simpleDateFormat.format(hrmsLeaveRecall.getEnddate());

		// Parsing the date
		LocalDate dateBefore = LocalDate.parse(dateBeforeString);
		LocalDate dateAfter = LocalDate.parse(dateAfterString);

		long daysnumbers = ChronoUnit.DAYS.between(dateBefore, dateAfter);
		Double numberofdays = (double) daysnumbers + 1.0;

		hrmsLeaveRecall.setNumberofdays(numberofdays);

		// verify if the employee and requester does exist as employee and are active
		if (hrmsEmployeeRepository.existsByIdAndActive(hrmsLeaveRecall.getEmployeeid(), 1)
				&& hrmsEmployeeRepository.existsByIdAndActive(requestedby, 1)) {

			// verify if the requester is the supervisor of the employee
			if (requestedby != hrmsLeaveRecall.getEmployeeid()
					&& hrmsEmployeeRepository.findById(requestedby).get().getDesignationId() == hrmsEmployeeRepository
							.findById(hrmsLeaveRecall.getEmployeeid()).get().getSupervisordesignationid()) {

				// check if the leave recall if been requested for the valid leave
				if (hrmsLeaveApplicationsRepository.existsByIdAndActiveAndApproved(hrmsLeaveRecall.getLeaveid(), 1,
						1)) {
					// verify if the employee is leave in the given date range

					HrmsLeaveApplications hrmsLeaveApplications = hrmsLeaveApplicationsRepository
							.findById(hrmsLeaveRecall.getLeaveid()).get();

					if (((hrmsLeaveRecall.getStartdate().after(hrmsLeaveApplications.getStartdate())
							|| (compareTo(hrmsLeaveApplications.getStartdate(), hrmsLeaveRecall.getStartdate())))

							&& (hrmsLeaveRecall.getStartdate().before(hrmsLeaveApplications.getEnddate())
									|| (compareTo(hrmsLeaveApplications.getEnddate(), hrmsLeaveRecall.getStartdate()))))

							&& ((hrmsLeaveRecall.getEnddate().before(hrmsLeaveApplications.getEnddate())
									|| (compareTo(hrmsLeaveApplications.getEnddate(),
											hrmsLeaveRecall.getEnddate()))))) {

						// update leave to recalled
						hrmsLeaveApplications.setApproved(4);// recalled
						hrmsLeaveApplications.setDate_updated(LocalDateTime.now());
						hrmsLeaveApplicationsRepository.saveAndFlush(hrmsLeaveApplications);

						return ResponseEntity.status(HttpStatus.OK)
								.body(hrmsLeaveRecallRepository.saveAndFlush(hrmsLeaveRecall));
					} else {

						return ResponseEntity.status(HttpStatus.REQUESTED_RANGE_NOT_SATISFIABLE).body(hrmsLeaveRecall);
					}

				} else {
					return ResponseEntity.status(HttpStatus.NOT_FOUND).body(hrmsLeaveRecall);
				}
			} else {
				return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(hrmsLeaveRecall);
			}
		} else {

			return ResponseEntity.status(HttpStatus.FAILED_DEPENDENCY).body(hrmsLeaveRecall);

		}
	}

	@Override
	public ResponseEntity<LeaveRecallResponse> getLeaveRecallById(int id) {

		String pattern = "yyyy-MM-dd";
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
		LeaveRecallResponse leaveRecallResponse = new LeaveRecallResponse();
		if (hrmsLeaveRecallRepository.existsByIdAndActive(id, 0)) {

			HrmsLeaveRecall dbm = hrmsLeaveRecallRepository.findById(id).get();

			leaveRecallResponse.setActive(dbm.getActive());
			leaveRecallResponse.setApprovalcomment(dbm.getApprovalcomment());
			leaveRecallResponse.setApproved(dbm.getApproved());
			leaveRecallResponse.setComment(dbm.getComment());
			leaveRecallResponse.setEmployeeid(dbm.getEmployeeid());
			leaveRecallResponse.setEstimatedcost(dbm.getEstimatedcost());

			if (dbm.getEmployeeid() != 0 && hrmsEmployeeRepository.existsById(dbm.getEmployeeid())) {
				StringBuilder employeename = new StringBuilder();

				HrmsEmployee hrmsEmployee = hrmsEmployeeRepository.findById(dbm.getEmployeeid()).get();

				employeename.append(hrmsEmployee.getFirstName().trim());
				if (hrmsEmployee.getMiddleName() != null) {
					employeename.append(" " + hrmsEmployee.getMiddleName().trim());
				}
				employeename.append(" " + hrmsEmployee.getLastName().trim());
				leaveRecallResponse.setEmployeename(employeename.toString());
			}

			if (dbm.getEnddate() != null) {
				leaveRecallResponse.setEnddate(simpleDateFormat.format(dbm.getEnddate()));
			}
			leaveRecallResponse.setId(dbm.getId());
			leaveRecallResponse.setLeaveid(dbm.getLeaveid());
			if (dbm.getLeavetypeid() != 0 && hrmsLeaveTypeRepository.existsById(dbm.getLeavetypeid())) {
				leaveRecallResponse
						.setLeavetype(hrmsLeaveTypeRepository.findById(dbm.getLeavetypeid()).get().getName());
			}
			leaveRecallResponse.setLeavetypeid(dbm.getLeavetypeid());
			leaveRecallResponse.setMonth(dbm.getMonth());
			leaveRecallResponse.setYear(dbm.getYear());
			leaveRecallResponse.setNumberofdays(dbm.getNumberofdays());
			leaveRecallResponse.setRequestedbyid(dbm.getRequestedby());

			if (dbm.getRequestedby() != 0 && hrmsEmployeeRepository.existsById(dbm.getRequestedby())) {
				StringBuilder requestedbyname = new StringBuilder();

				HrmsEmployee hrmsEmployee = hrmsEmployeeRepository.findById(dbm.getRequestedby()).get();

				requestedbyname.append(hrmsEmployee.getFirstName().trim());
				if (hrmsEmployee.getMiddleName() != null) {
					requestedbyname.append(" " + hrmsEmployee.getMiddleName().trim());
				}
				requestedbyname.append(" " + hrmsEmployee.getLastName().trim());
				leaveRecallResponse.setRequestedbyname(requestedbyname.toString());
			}

			if (dbm.getStartdate() != null) {
				leaveRecallResponse.setStartdate(simpleDateFormat.format(dbm.getStartdate()));
			}

		}

		return ResponseEntity.status(HttpStatus.OK).body(leaveRecallResponse);
	}

	@Override
	public ResponseEntity<HrmsLeaveRecall> updateLeaveRecall(HrmsLeaveRecall hrmsLeaveRecall, int id, int requestedby) {

		if (hrmsLeaveRecallRepository.existsByIdAndActiveAndApprovedAndEmpknowledge(id, 1, 0, 0)) {

			hrmsLeaveRecall.setActive(1);
			hrmsLeaveRecall.setApproved(0);
			hrmsLeaveRecall.setRequestedby(requestedby);
			HrmsLeaveRecall hrmsLeaveRecall1 = hrmsLeaveRecallRepository.findById(id).get();
			if (hrmsLeaveRecall1.getUnique_id() != null && hrmsLeaveRecall1.getYear() != 0
					&& hrmsLeaveRecall1.getMonth() != 0) {
				hrmsLeaveRecall.setUnique_id(hrmsLeaveRecall1.getUnique_id());

				hrmsLeaveRecall.setYear(hrmsLeaveRecall1.getYear());
				hrmsLeaveRecall.setMonth(hrmsLeaveRecall1.getMonth());
			}

			String pattern = "yyyy-MM-dd";
			SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);

			String dateBeforeString = simpleDateFormat.format(hrmsLeaveRecall.getStartdate());

			String dateAfterString = simpleDateFormat.format(hrmsLeaveRecall.getEnddate());

			// Parsing the date
			LocalDate dateBefore = LocalDate.parse(dateBeforeString);
			LocalDate dateAfter = LocalDate.parse(dateAfterString);

			long daysnumbers = ChronoUnit.DAYS.between(dateBefore, dateAfter);
			Double numberofdays = (double) daysnumbers + 1.0;

			hrmsLeaveRecall.setNumberofdays(numberofdays);

			// verify if the employee and requester does exist as employee and are active
			if (hrmsEmployeeRepository.existsByIdAndActive(hrmsLeaveRecall.getEmployeeid(), 1)
					&& hrmsEmployeeRepository.existsByIdAndActive(requestedby, 1)) {

				// verify if the requester is the supervisor of the employee
				if (requestedby != hrmsLeaveRecall.getEmployeeid() && hrmsEmployeeRepository.findById(requestedby).get()
						.getDesignationId() == hrmsEmployeeRepository.findById(requestedby).get()
								.getSupervisordesignationid()) {

					// check if the leave recall if been requested for the valid leave
					if (hrmsLeaveApplicationsRepository.existsByIdAndActiveAndApproved(hrmsLeaveRecall.getLeaveid(), 1,
							1)) {
						// verify if the employee is leave in the given date range

						HrmsLeaveApplications hrmsLeaveApplications = hrmsLeaveApplicationsRepository
								.findById(hrmsLeaveRecall.getLeaveid()).get();

						if (((hrmsLeaveRecall.getStartdate().after(hrmsLeaveApplications.getStartdate())
								|| (compareTo(hrmsLeaveApplications.getStartdate(), hrmsLeaveRecall.getStartdate())))

								&& (hrmsLeaveRecall.getStartdate().before(hrmsLeaveApplications.getEnddate())
										|| (compareTo(hrmsLeaveApplications.getEnddate(),
												hrmsLeaveRecall.getStartdate()))))

								&& ((hrmsLeaveRecall.getEnddate().before(hrmsLeaveApplications.getEnddate())
										|| (compareTo(hrmsLeaveApplications.getEnddate(),
												hrmsLeaveRecall.getEnddate()))))) {

							return ResponseEntity.status(HttpStatus.OK)
									.body(hrmsLeaveRecallRepository.saveAndFlush(hrmsLeaveRecall));
						} else {
							return ResponseEntity.status(HttpStatus.REQUESTED_RANGE_NOT_SATISFIABLE)
									.body(hrmsLeaveRecall);
						}

					} else {
						return ResponseEntity.status(HttpStatus.NOT_FOUND).body(hrmsLeaveRecall);
					}
				} else {
					return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(hrmsLeaveRecall);
				}
			} else {

				return ResponseEntity.status(HttpStatus.FAILED_DEPENDENCY).body(hrmsLeaveRecall);

			}

		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
		}
	}

	@Override
	public ResponseEntity<?> deleteLeaveRecall(int id) {
		if (hrmsLeaveRecallRepository.existsByIdAndActiveAndApproved(id, 1, 0)) {
			HrmsLeaveRecall hrmsLeaveRecall = hrmsLeaveRecallRepository.findByIdAndActiveAndApproved(id, 1, 0);

			hrmsLeaveRecall.setActive(0);
			hrmsLeaveRecall.setDate_updated(LocalDateTime.now());

			// update recalled status to approved normal on leave application
			HrmsLeaveApplications hrmsLeaveApplications = hrmsLeaveApplicationsRepository
					.findById(hrmsLeaveRecall.getLeaveid()).get();
			hrmsLeaveApplications.setApproved(1);
			hrmsLeaveApplications.setDate_updated(LocalDateTime.now());
			hrmsLeaveApplicationsRepository.saveAndFlush(hrmsLeaveApplications);

			return ResponseEntity.status(HttpStatus.OK).body(hrmsLeaveRecallRepository.saveAndFlush(hrmsLeaveRecall));

		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
		}
	}

	@Override
	public ResponseEntity<List<LeaveRecallResponse>> getAllLeaveRecall() {

		List<LeaveRecallResponse> leaveRecallResponselist = new ArrayList<>();
		String pattern = "yyyy-MM-dd";
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);

		List<HrmsLeaveRecall> dbms = hrmsLeaveRecallRepository.findByActiveOrderByIdDesc(1);
		for (HrmsLeaveRecall dbm : dbms) {
			LeaveRecallResponse leaveRecallResponse = new LeaveRecallResponse();
			leaveRecallResponse.setActive(dbm.getActive());
			leaveRecallResponse.setApprovalcomment(dbm.getApprovalcomment());
			leaveRecallResponse.setApproved(dbm.getApproved());
			leaveRecallResponse.setComment(dbm.getComment());
			leaveRecallResponse.setEmployeeid(dbm.getEmployeeid());
			leaveRecallResponse.setEstimatedcost(dbm.getEstimatedcost());

			if (dbm.getEmployeeid() != 0 && hrmsEmployeeRepository.existsById(dbm.getEmployeeid())) {
				StringBuilder employeename = new StringBuilder();

				HrmsEmployee hrmsEmployee = hrmsEmployeeRepository.findById(dbm.getEmployeeid()).get();

				employeename.append(hrmsEmployee.getFirstName().trim());
				if (hrmsEmployee.getMiddleName() != null) {
					employeename.append(" " + hrmsEmployee.getMiddleName().trim());
				}
				employeename.append(" " + hrmsEmployee.getLastName().trim());
				leaveRecallResponse.setEmployeename(employeename.toString());
			}

			if (dbm.getEnddate() != null) {
				leaveRecallResponse.setEnddate(simpleDateFormat.format(dbm.getEnddate()));
			}
			leaveRecallResponse.setId(dbm.getId());
			leaveRecallResponse.setLeaveid(dbm.getLeaveid());
			if (dbm.getLeavetypeid() != 0 && hrmsLeaveTypeRepository.existsById(dbm.getLeavetypeid())) {
				leaveRecallResponse
						.setLeavetype(hrmsLeaveTypeRepository.findById(dbm.getLeavetypeid()).get().getName());
			}
			leaveRecallResponse.setLeavetypeid(dbm.getLeavetypeid());
			leaveRecallResponse.setMonth(dbm.getMonth());
			leaveRecallResponse.setYear(dbm.getYear());
			leaveRecallResponse.setNumberofdays(dbm.getNumberofdays());
			leaveRecallResponse.setRequestedbyid(dbm.getRequestedby());

			if (dbm.getRequestedby() != 0 && hrmsEmployeeRepository.existsById(dbm.getRequestedby())) {
				StringBuilder requestedbyname = new StringBuilder();

				HrmsEmployee hrmsEmployee = hrmsEmployeeRepository.findById(dbm.getRequestedby()).get();

				requestedbyname.append(hrmsEmployee.getFirstName().trim());
				if (hrmsEmployee.getMiddleName() != null) {
					requestedbyname.append(" " + hrmsEmployee.getMiddleName().trim());
				}
				requestedbyname.append(" " + hrmsEmployee.getLastName().trim());
				leaveRecallResponse.setRequestedbyname(requestedbyname.toString());
			}

			if (dbm.getStartdate() != null) {
				leaveRecallResponse.setStartdate(simpleDateFormat.format(dbm.getStartdate()));
			}

			leaveRecallResponselist.add(leaveRecallResponse);
		}

		return ResponseEntity.status(HttpStatus.OK).body(leaveRecallResponselist);
	}

	@Override
	public ResponseEntity<List<LeaveRecallResponse>> getAllLeaveRecallBySupervisorId(int supervisorId) {
		List<LeaveRecallResponse> leaveRecallResponselist = new ArrayList<>();
		String pattern = "yyyy-MM-dd";
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);

		List<HrmsLeaveRecall> dbms = hrmsLeaveRecallRepository.findByActiveOrderByIdDesc(1);
		for (HrmsLeaveRecall dbm : dbms) {
			if (hrmsEmployeeRepository.existsById(dbm.getEmployeeid())
					&& hrmsEmployeeRepository.existsById(supervisorId)
					&& hrmsEmployeeRepository.findById(dbm.getEmployeeid()).get()
							.getSupervisordesignationid() == hrmsEmployeeRepository.findById(supervisorId).get()
									.getDesignationId()) {
				LeaveRecallResponse leaveRecallResponse = new LeaveRecallResponse();
				leaveRecallResponse.setActive(dbm.getActive());
				leaveRecallResponse.setApprovalcomment(dbm.getApprovalcomment());
				leaveRecallResponse.setApproved(dbm.getApproved());
				leaveRecallResponse.setComment(dbm.getComment());
				leaveRecallResponse.setEmployeeid(dbm.getEmployeeid());
				leaveRecallResponse.setEstimatedcost(dbm.getEstimatedcost());

				if (dbm.getEmployeeid() != 0 && hrmsEmployeeRepository.existsById(dbm.getEmployeeid())) {
					StringBuilder employeename = new StringBuilder();

					HrmsEmployee hrmsEmployee = hrmsEmployeeRepository.findById(dbm.getEmployeeid()).get();

					employeename.append(hrmsEmployee.getFirstName().trim());
					if (hrmsEmployee.getMiddleName() != null) {
						employeename.append(" " + hrmsEmployee.getMiddleName().trim());
					}
					employeename.append(" " + hrmsEmployee.getLastName().trim());
					leaveRecallResponse.setEmployeename(employeename.toString());
				}

				if (dbm.getEnddate() != null) {
					leaveRecallResponse.setEnddate(simpleDateFormat.format(dbm.getEnddate()));
				}
				leaveRecallResponse.setId(dbm.getId());
				leaveRecallResponse.setLeaveid(dbm.getLeaveid());
				if (dbm.getLeavetypeid() != 0 && hrmsLeaveTypeRepository.existsById(dbm.getLeavetypeid())) {
					leaveRecallResponse
							.setLeavetype(hrmsLeaveTypeRepository.findById(dbm.getLeavetypeid()).get().getName());
				}
				leaveRecallResponse.setLeavetypeid(dbm.getLeavetypeid());
				leaveRecallResponse.setMonth(dbm.getMonth());
				leaveRecallResponse.setYear(dbm.getYear());
				leaveRecallResponse.setNumberofdays(dbm.getNumberofdays());
				leaveRecallResponse.setRequestedbyid(dbm.getRequestedby());

				if (dbm.getRequestedby() != 0 && hrmsEmployeeRepository.existsById(dbm.getRequestedby())) {
					StringBuilder requestedbyname = new StringBuilder();

					HrmsEmployee hrmsEmployee = hrmsEmployeeRepository.findById(dbm.getRequestedby()).get();

					requestedbyname.append(hrmsEmployee.getFirstName().trim());
					if (hrmsEmployee.getMiddleName() != null) {
						requestedbyname.append(" " + hrmsEmployee.getMiddleName().trim());
					}
					requestedbyname.append(" " + hrmsEmployee.getLastName().trim());
					leaveRecallResponse.setRequestedbyname(requestedbyname.toString());
				}

				if (dbm.getStartdate() != null) {
					leaveRecallResponse.setStartdate(simpleDateFormat.format(dbm.getStartdate()));
				}

				leaveRecallResponselist.add(leaveRecallResponse);
			}
		}

		return ResponseEntity.status(HttpStatus.OK).body(leaveRecallResponselist);
	}

	public boolean compareTo(Date datedb, Date dateapp) {

		String pattern = "yyyy-MM-dd";
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);

		String dateBeforeString = simpleDateFormat.format(datedb);

		String dateAfterString = simpleDateFormat.format(dateapp);

		// Parsing the date
		LocalDate dateBefore = LocalDate.parse(dateBeforeString);
		LocalDate dateAfter = LocalDate.parse(dateAfterString);
		Boolean response = false;

		System.out.println("==================================================================");

		System.out.println("DB date =  " + dateBefore + " And App Date  = " + dateAfter);

		if ((dateBefore.getYear() == dateAfter.getYear()) && (dateBefore.getMonthValue() == dateAfter.getMonthValue())
				&& (dateBefore.getDayOfMonth() == dateAfter.getDayOfMonth())) {
			response = true;
		}

		return response;

	}

	@Override
	public ResponseEntity<?> ApproveLeaveRecall(int leaveid, int supervisorid, int status, String comment) {
		if (hrmsLeaveRecallRepository.existsByIdAndActiveAndApprovedAndEmpknowledge(leaveid, 1, 0, 1)
				&& hrmsEmployeeRepository.existsByIdAndIssupervisorAndActive(supervisorid, 1, 1)) {

			HrmsEmployee hrmsEmployee = hrmsEmployeeRepository.findByIdAndIssupervisorAndActive(supervisorid, 1, 1);

			HrmsLeaveRecall hrmsLeave = hrmsLeaveRecallRepository.findByIdAndActive(leaveid, 1);

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

			if (hrmsEmployeeRepository.existsByIdAndIssupervisorAndActive(hrmsLeave.getEmployeeid(), 1, 1)) {

				firstsupervisordesignationid = 575;// GD Designation

			}

			int workflowid = 0;
			if (hrmsLeaveRecallApprovalWorkflowRepository
					.existsBySupervisordesignationidAndActive(firstsupervisordesignationid, 1)) {

				HrmsLeaveRecallApprovalWorkflow hrmsLeaveRecallApprovalWorkflow = hrmsLeaveRecallApprovalWorkflowRepository
						.findBySupervisordesignationidAndActive(firstsupervisordesignationid, 1);

				workflowid = hrmsLeaveRecallApprovalWorkflow.getId();

				if (hrmsLeaveRecallApprovalWorkflowStepRepository.existsByWorkflowidAndApproverdesignationidAndActive(
						workflowid, hrmsEmployee.getDesignationId(), 1)) {

					HrmsLeaveRecallApprovalWorkflowStep step = hrmsLeaveRecallApprovalWorkflowStepRepository
							.findByWorkflowidAndApproverdesignationidAndActive(workflowid,
									hrmsEmployee.getDesignationId(), 1);

					int stepnext = 0;
					if (step.getApproverdesignationnextid() != 0) {
						HrmsLeaveRecallApprovalWorkflowStep step1 = hrmsLeaveRecallApprovalWorkflowStepRepository
								.findByWorkflowidAndApproverdesignationidAndActive(workflowid,
										step.getApproverdesignationnextid(), 1);

						stepnext = step1.getStepnumber();
					}

					HrmsLeaveRecallApproval hrmsLeaveApproval = new HrmsLeaveRecallApproval();

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

					hrmsLeaveRecallApprovalRepository.saveAndFlush(hrmsLeaveApproval);

					if (hrmsLeaveRecallApprovalRepository.countByLeaveid(
							leaveid) == hrmsLeaveRecallApprovalWorkflowStepRepository.countByWorkflowid(workflowid)
							&& status == 1) {

						// return leave which were recalled

						HrmsLeaveBalance hrmsLeaveBalance = hrmsLeaveBalanceRepository
								.findByEmployeeidAndLeavetypeidAndActive(hrmsLeave.getEmployeeid(),
										hrmsLeave.getLeavetypeid(), 1);

						hrmsLeaveBalance.setDate_updated(LocalDateTime.now());

						HrmsLeaveApplications hrmsLeaveApplications = hrmsLeaveApplicationsRepository
								.findById(hrmsLeave.getLeaveid()).get();

						// update leave balance

						hrmsLeaveBalance.setDays(hrmsLeaveApplications.getNumberofdays() + hrmsLeaveBalance.getDays());
						hrmsLeaveBalanceRepository.saveAndFlush(hrmsLeaveBalance);

						hrmsLeave.setDate_updated(LocalDateTime.now());
						hrmsLeave.setApproved(1);

						hrmsLeaveRecallRepository.saveAndFlush(hrmsLeave);

						return ResponseEntity.status(HttpStatus.OK).body(hrmsLeave);

					} else {
						if (status == 0) {

							hrmsLeave.setDate_updated(LocalDateTime.now());
							hrmsLeave.setApproved(2);// rejected

							// update recalled status to approved normal on leave application
							HrmsLeaveApplications hrmsLeaveApplications = hrmsLeaveApplicationsRepository
									.findById(hrmsLeave.getLeaveid()).get();
							hrmsLeaveApplications.setApproved(1);
							hrmsLeaveApplications.setDate_updated(LocalDateTime.now());
							hrmsLeaveApplicationsRepository.saveAndFlush(hrmsLeaveApplications);

							hrmsLeaveRecallRepository.saveAndFlush(hrmsLeave);

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
	public ResponseEntity<List<LeaveApprovalStatus>> getLeaveRecallApprovers(int id) {
		String pattern = "yyyy-MM-dd";
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);

		List<LeaveApprovalStatus> leaveApprovalStatuslist = new ArrayList<>();

		if (hrmsLeaveRecallRepository.existsByIdAndActiveAndEmpknowledge(id, 1, 1)) {

			HrmsLeaveRecall hrmsLeave = hrmsLeaveRecallRepository.findByIdAndActive(id, 1);

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

			if (hrmsEmployeeRepository.existsByIdAndIssupervisorAndActive(hrmsLeave.getEmployeeid(), 1, 1)) {

				supervisordesignationid = 575;// GD Designation

			}

			if (hrmsLeaveRecallApprovalWorkflowRepository
					.existsBySupervisordesignationidAndActive(supervisordesignationid, 1)) {
				int workflowid = hrmsLeaveRecallApprovalWorkflowRepository
						.findBySupervisordesignationidAndActive(supervisordesignationid, 1).getId();

				if (hrmsLeaveRecallApprovalWorkflowStepRepository.existsByWorkflowidAndActive(workflowid, 1)) {

					List<HrmsLeaveRecallApprovalWorkflowStep> dbms = hrmsLeaveRecallApprovalWorkflowStepRepository
							.findByWorkflowidAndActiveOrderByStepnumberAsc(workflowid, 1);

					for (HrmsLeaveRecallApprovalWorkflowStep dbm : dbms) {

						HrmsEmployee hrmsEmployee = hrmsEmployeeRepository
								.findFirstByDesignationIdAndIssupervisorAndActive(dbm.getApproverdesignationid(), 1, 1);

						if (hrmsLeaveRecallApprovalRepository.existsByApproverdesignationidAndLeaveidAndActive(
								dbm.getApproverdesignationid(), id, 1)) {

							HrmsLeaveRecallApproval hrmsLeaveApproval = hrmsLeaveRecallApprovalRepository
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
							if (dbm.getApproverdesignationprevid() != 0
									&& hrmsLeaveRecallApprovalRepository.existsByStatusAndLeaveidAndActive(0, id, 1)) {

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
	public ResponseEntity<List<LeaveRecallResponse>> getHrmsLeaveRecallNotApprovedBySupervisorNext(int supervisorid) {
		String pattern = "yyyy-MM-dd";
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);

		List<LeaveRecallResponse> leaveApplicationsResponselist = new ArrayList<>();

		if (hrmsEmployeeRepository.existsByIdAndIssupervisorAndActive(supervisorid, 1, 1)) {

			List<HrmsLeaveRecall> dbms = hrmsLeaveRecallRepository.findByApprovedAndActiveAndEmpknowledge(0, 1, 1);

			for (HrmsLeaveRecall dbm : dbms) {
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

					}

					if (hrmsEmployeeRepository.existsByIdAndIssupervisorAndActive(dbm.getEmployeeid(), 1, 1)) {

						// supervisordesignationid = 575;// GD Designation

						supervisordbm = hrmsEmployeeRepository
								.findFirstByDesignationIdAndEmploymentstatusidAndActive(575, 2, 1).getId();

					}

					/*
					 * supervisordbm = hrmsEmployeeRepository
					 * .findByIdAndActiveAndEmploymentstatusid(dbm.getEmployeeid(), 1,
					 * 2).getSupervisorId();
					 */
				}

				if (supervisorid == supervisordbm) {

					if (hrmsLeaveRecallApprovalRepository.existsByApproveruseridAndLeaveidAndActive(supervisorid,
							dbm.getId(), 1)) {
						// do nothing you already approved it
					} else {

						LeaveRecallResponse leaveRecallResponse = new LeaveRecallResponse();

						if (dbm.getApproved() == 0 && dbm.getEmpknowledge() == 0) {
							leaveRecallResponse.setRecallStatus("Submitted");

						}

						if (dbm.getApproved() == 0 && dbm.getEmpknowledge() == 1) {
							leaveRecallResponse.setRecallStatus("On Progress");

						}

						if (dbm.getApproved() == 1 && dbm.getEmpknowledge() == 1) {
							leaveRecallResponse.setRecallStatus("Approved");

						}

						if (dbm.getApproved() == -1 && dbm.getEmpknowledge() == 1) {
							leaveRecallResponse.setRecallStatus("Rejected");

						}

						leaveRecallResponse.setEmpknowledge(dbm.getEmpknowledge());

						if (dbm.getEmpknowledge() == 0) {
							leaveRecallResponse.setEmpknowledgement("Not Acknowledged");
						}

						if (dbm.getEmpknowledge() == 1) {
							leaveRecallResponse.setEmpknowledgement("Acknowledged");
						}

						leaveRecallResponse.setEmpknowledge(dbm.getId());
						leaveRecallResponse.setActive(dbm.getActive());
						leaveRecallResponse.setApprovalcomment(dbm.getApprovalcomment());
						leaveRecallResponse.setApproved(dbm.getApproved());
						leaveRecallResponse.setComment(dbm.getComment());
						leaveRecallResponse.setEmployeeid(dbm.getEmployeeid());
						leaveRecallResponse.setEstimatedcost(dbm.getEstimatedcost());

						if (dbm.getEmployeeid() != 0 && hrmsEmployeeRepository.existsById(dbm.getEmployeeid())) {
							StringBuilder employeename = new StringBuilder();

							HrmsEmployee hrmsEmployee = hrmsEmployeeRepository.findById(dbm.getEmployeeid()).get();

							employeename.append(hrmsEmployee.getFirstName().trim());
							if (hrmsEmployee.getMiddleName() != null) {
								employeename.append(" " + hrmsEmployee.getMiddleName().trim());
							}
							employeename.append(" " + hrmsEmployee.getLastName().trim());
							leaveRecallResponse.setEmployeename(employeename.toString());
						}

						if (dbm.getEnddate() != null) {
							leaveRecallResponse.setEnddate(simpleDateFormat.format(dbm.getEnddate()));
						}
						leaveRecallResponse.setId(dbm.getId());
						leaveRecallResponse.setLeaveid(dbm.getLeaveid());
						if (dbm.getLeavetypeid() != 0 && hrmsLeaveTypeRepository.existsById(dbm.getLeavetypeid())) {
							leaveRecallResponse.setLeavetype(
									hrmsLeaveTypeRepository.findById(dbm.getLeavetypeid()).get().getName());
						}
						leaveRecallResponse.setLeavetypeid(dbm.getLeavetypeid());
						leaveRecallResponse.setMonth(dbm.getMonth());
						leaveRecallResponse.setYear(dbm.getYear());
						leaveRecallResponse.setNumberofdays(dbm.getNumberofdays());
						leaveRecallResponse.setRequestedbyid(dbm.getRequestedby());

						if (dbm.getRequestedby() != 0 && hrmsEmployeeRepository.existsById(dbm.getRequestedby())) {
							StringBuilder requestedbyname = new StringBuilder();

							HrmsEmployee hrmsEmployee = hrmsEmployeeRepository.findById(dbm.getRequestedby()).get();

							requestedbyname.append(hrmsEmployee.getFirstName().trim());
							if (hrmsEmployee.getMiddleName() != null) {
								requestedbyname.append(" " + hrmsEmployee.getMiddleName().trim());
							}
							requestedbyname.append(" " + hrmsEmployee.getLastName().trim());
							leaveRecallResponse.setRequestedbyname(requestedbyname.toString());
						}

						if (dbm.getStartdate() != null) {
							leaveRecallResponse.setStartdate(simpleDateFormat.format(dbm.getStartdate()));
						}

						leaveApplicationsResponselist.add(leaveRecallResponse);

					}

				} else {

					// check if first supervisor has approved and this user has not approved

					if (hrmsLeaveRecallApprovalRepository.existsByApproveruseridAndLeaveidAndActive(supervisordbm,
							dbm.getId(), 1)

							&& !hrmsLeaveRecallApprovalRepository
									.existsByApproveruseridAndLeaveidAndActive(supervisorid, dbm.getId(), 1)) {

						// check if this supervisor is the next to approve by verifying if the back to
						// this has approved

						int workflowid = hrmsLeaveRecallApprovalRepository
								.findByApproveruseridAndLeaveidAndActive(supervisordbm, dbm.getId(), 1).getWorkflowid();

						// check if the step number of this supervisor on step

						// get supervisor designation

						HrmsEmployee hrmsEmployee = hrmsEmployeeRepository.findById(supervisorid).get();

						if (hrmsLeaveRecallApprovalWorkflowStepRepository
								.existsByWorkflowidAndApproverdesignationidAndActive(workflowid,
										hrmsEmployee.getDesignationId(), 1)) {

							HrmsLeaveRecallApprovalWorkflowStep step = hrmsLeaveRecallApprovalWorkflowStepRepository
									.findByWorkflowidAndApproverdesignationidAndActive(workflowid,
											hrmsEmployee.getDesignationId(), 1);

							int stepnow = step.getStepnumber();

							int designationprev = step.getApproverdesignationprevid();

							// check if this user has approved already

							if (hrmsLeaveRecallApprovalRepository
									.existsByApproverdesignationidAndLeaveidAndActiveAndStatus(designationprev,
											dbm.getId(), 1, 1)) {

								// add this leave recall as it deserves to be approved
								LeaveRecallResponse leaveRecallResponse = new LeaveRecallResponse();

								if (dbm.getApproved() == 0 && dbm.getEmpknowledge() == 0) {
									leaveRecallResponse.setRecallStatus("Submitted");

								}

								if (dbm.getApproved() == 0 && dbm.getEmpknowledge() == 1) {
									leaveRecallResponse.setRecallStatus("On Progress");

								}

								if (dbm.getApproved() == 1 && dbm.getEmpknowledge() == 1) {
									leaveRecallResponse.setRecallStatus("Approved");

								}

								if (dbm.getApproved() == -1 && dbm.getEmpknowledge() == 1) {
									leaveRecallResponse.setRecallStatus("Rejected");

								}

								leaveRecallResponse.setEmpknowledge(dbm.getEmpknowledge());

								if (dbm.getEmpknowledge() == 0) {
									leaveRecallResponse.setEmpknowledgement("Not Acknowledged");
								}

								if (dbm.getEmpknowledge() == 1) {
									leaveRecallResponse.setEmpknowledgement("Acknowledged");
								}

								leaveRecallResponse.setEmpknowledge(dbm.getId());
								leaveRecallResponse.setActive(dbm.getActive());
								leaveRecallResponse.setApprovalcomment(dbm.getApprovalcomment());
								leaveRecallResponse.setApproved(dbm.getApproved());
								leaveRecallResponse.setComment(dbm.getComment());
								leaveRecallResponse.setEmployeeid(dbm.getEmployeeid());
								leaveRecallResponse.setEstimatedcost(dbm.getEstimatedcost());

								if (dbm.getEmployeeid() != 0
										&& hrmsEmployeeRepository.existsById(dbm.getEmployeeid())) {
									StringBuilder employeename = new StringBuilder();

									HrmsEmployee hrmsEmployee2 = hrmsEmployeeRepository.findById(dbm.getEmployeeid())
											.get();

									employeename.append(hrmsEmployee2.getFirstName().trim());
									if (hrmsEmployee2.getMiddleName() != null) {
										employeename.append(" " + hrmsEmployee2.getMiddleName().trim());
									}
									employeename.append(" " + hrmsEmployee2.getLastName().trim());
									leaveRecallResponse.setEmployeename(employeename.toString());
								}

								if (dbm.getEnddate() != null) {
									leaveRecallResponse.setEnddate(simpleDateFormat.format(dbm.getEnddate()));
								}
								leaveRecallResponse.setId(dbm.getId());
								leaveRecallResponse.setLeaveid(dbm.getLeaveid());
								if (dbm.getLeavetypeid() != 0
										&& hrmsLeaveTypeRepository.existsById(dbm.getLeavetypeid())) {
									leaveRecallResponse.setLeavetype(
											hrmsLeaveTypeRepository.findById(dbm.getLeavetypeid()).get().getName());
								}
								leaveRecallResponse.setLeavetypeid(dbm.getLeavetypeid());
								leaveRecallResponse.setMonth(dbm.getMonth());
								leaveRecallResponse.setYear(dbm.getYear());
								leaveRecallResponse.setNumberofdays(dbm.getNumberofdays());
								leaveRecallResponse.setRequestedbyid(dbm.getRequestedby());

								if (dbm.getRequestedby() != 0
										&& hrmsEmployeeRepository.existsById(dbm.getRequestedby())) {
									StringBuilder requestedbyname = new StringBuilder();

									HrmsEmployee hrmsEmployee1 = hrmsEmployeeRepository.findById(dbm.getRequestedby())
											.get();

									requestedbyname.append(hrmsEmployee1.getFirstName().trim());
									if (hrmsEmployee1.getMiddleName() != null) {
										requestedbyname.append(" " + hrmsEmployee1.getMiddleName().trim());
									}
									requestedbyname.append(" " + hrmsEmployee1.getLastName().trim());
									leaveRecallResponse.setRequestedbyname(requestedbyname.toString());
								}

								if (dbm.getStartdate() != null) {
									leaveRecallResponse.setStartdate(simpleDateFormat.format(dbm.getStartdate()));
								}

								leaveApplicationsResponselist.add(leaveRecallResponse);

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
	public ResponseEntity<List<LeaveRecallResponse>> getAllLeaveRecallByEmployeeId(int employeeid) {

		List<LeaveRecallResponse> leaveRecallResponselist = new ArrayList<>();
		String pattern = "yyyy-MM-dd";
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);

		List<HrmsLeaveRecall> dbms = hrmsLeaveRecallRepository.findByActiveAndEmployeeidOrderByIdDesc(1, employeeid);
		for (HrmsLeaveRecall dbm : dbms) {
			LeaveRecallResponse leaveRecallResponse = new LeaveRecallResponse();
			leaveRecallResponse.setActive(dbm.getActive());
			leaveRecallResponse.setApprovalcomment(dbm.getApprovalcomment());
			leaveRecallResponse.setApproved(dbm.getApproved());

			if (dbm.getApproved() == 0 && dbm.getEmpknowledge() == 0) {
				leaveRecallResponse.setRecallStatus("Submitted");

			}

			if (dbm.getApproved() == 0 && dbm.getEmpknowledge() == 1) {
				leaveRecallResponse.setRecallStatus("On Progress");

			}

			if (dbm.getApproved() == 1 && dbm.getEmpknowledge() == 1) {
				leaveRecallResponse.setRecallStatus("Approved");

			}

			if (dbm.getApproved() == -1 && dbm.getEmpknowledge() == 1) {
				leaveRecallResponse.setRecallStatus("Rejected");

			}

			leaveRecallResponse.setEmpknowledge(dbm.getEmpknowledge());

			if (dbm.getEmpknowledge() == 0) {
				leaveRecallResponse.setEmpknowledgement("Not Acknowledged");
			}

			if (dbm.getEmpknowledge() == 1) {
				leaveRecallResponse.setEmpknowledgement("Acknowledged");
			}

			leaveRecallResponse.setComment(dbm.getComment());
			leaveRecallResponse.setEmployeeid(dbm.getEmployeeid());
			leaveRecallResponse.setEstimatedcost(dbm.getEstimatedcost());

			if (dbm.getEmployeeid() != 0 && hrmsEmployeeRepository.existsById(dbm.getEmployeeid())) {
				StringBuilder employeename = new StringBuilder();

				HrmsEmployee hrmsEmployee = hrmsEmployeeRepository.findById(dbm.getEmployeeid()).get();

				employeename.append(hrmsEmployee.getFirstName().trim());
				if (hrmsEmployee.getMiddleName() != null) {
					employeename.append(" " + hrmsEmployee.getMiddleName().trim());
				}
				employeename.append(" " + hrmsEmployee.getLastName().trim());
				leaveRecallResponse.setEmployeename(employeename.toString());
			}

			if (dbm.getEnddate() != null) {
				leaveRecallResponse.setEnddate(simpleDateFormat.format(dbm.getEnddate()));
			}
			leaveRecallResponse.setId(dbm.getId());
			leaveRecallResponse.setLeaveid(dbm.getLeaveid());
			if (dbm.getLeavetypeid() != 0 && hrmsLeaveTypeRepository.existsById(dbm.getLeavetypeid())) {
				leaveRecallResponse
						.setLeavetype(hrmsLeaveTypeRepository.findById(dbm.getLeavetypeid()).get().getName());
			}
			leaveRecallResponse.setLeavetypeid(dbm.getLeavetypeid());
			leaveRecallResponse.setMonth(dbm.getMonth());
			leaveRecallResponse.setYear(dbm.getYear());
			leaveRecallResponse.setNumberofdays(dbm.getNumberofdays());
			leaveRecallResponse.setRequestedbyid(dbm.getRequestedby());

			if (dbm.getRequestedby() != 0 && hrmsEmployeeRepository.existsById(dbm.getRequestedby())) {
				StringBuilder requestedbyname = new StringBuilder();

				HrmsEmployee hrmsEmployee = hrmsEmployeeRepository.findById(dbm.getRequestedby()).get();

				requestedbyname.append(hrmsEmployee.getFirstName().trim());
				if (hrmsEmployee.getMiddleName() != null) {
					requestedbyname.append(" " + hrmsEmployee.getMiddleName().trim());
				}
				requestedbyname.append(" " + hrmsEmployee.getLastName().trim());
				leaveRecallResponse.setRequestedbyname(requestedbyname.toString());
			}

			if (dbm.getStartdate() != null) {
				leaveRecallResponse.setStartdate(simpleDateFormat.format(dbm.getStartdate()));
			}

			leaveRecallResponselist.add(leaveRecallResponse);
		}

		return ResponseEntity.status(HttpStatus.OK).body(leaveRecallResponselist);
	}

	@Override
	public ResponseEntity<?> AcknowledgeLeaveRecall(int id, int empid, int acknowledge) {
		if (hrmsLeaveRecallRepository.existsByIdAndEmployeeidAndActive(id, empid, 1)
				&& hrmsEmployeeRepository.existsByIdAndActive(empid, 1)
				&& !hrmsLeaveRecallApprovalRepository.existsByLeaveidAndActive(id, 1)) {

			HrmsLeaveRecall hrmsLeaveRecall = hrmsLeaveRecallRepository.findByIdAndActive(id, 1);

			hrmsLeaveRecall.setEmpknowledge(acknowledge);
			hrmsLeaveRecall.setDate_updated(LocalDateTime.now());

			return ResponseEntity.status(HttpStatus.OK).body(hrmsLeaveRecallRepository.saveAndFlush(hrmsLeaveRecall));
		} else {

			return ResponseEntity.status(HttpStatus.UNAVAILABLE_FOR_LEGAL_REASONS).body(null);
		}
	}

}
