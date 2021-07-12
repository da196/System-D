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

import com.Hrms.Communication.SendEmail;
import com.Hrms.Employee.DTO.EmployeeApprovalComment;
import com.Hrms.Employee.DTO.HrmsEmployeeEmploymentHistoryRequest;
import com.Hrms.Employee.DTO.HrmsEmployeeEmploymentHistoryResponseByEmpId;
import com.Hrms.Employee.DTO.HrmsEmployeeEmploymentHistoryResponseById;
import com.Hrms.Employee.Entity.HrmsEmployee;
import com.Hrms.Employee.Entity.HrmsEmployeeEmploymentHistory;
import com.Hrms.Employee.Repository.HrmsEmployeeEmploymentHistoryRepository;
import com.Hrms.Employee.Repository.HrmsEmployeeRepository;
import com.Hrms.Employee.Repository.HrmsLocationCountryRepository;

@Service
public class HrmsEmployeeEmploymentHistoryServiceImpl implements HrmsEmployeeEmploymentHistoryService {
	@Autowired

	private HrmsEmployeeEmploymentHistoryRepository hrmsEmployeeEmploymentHistoryRepository;

	@Autowired
	private HrmsLocationCountryRepository hrmsLocationCountryRepository;

	@Autowired
	private HrmsEmployeeRepository hrmsEmployeeRepository;

	@Autowired
	private SendEmail sendEmail;

	@Override
	public ResponseEntity<HrmsEmployeeEmploymentHistoryRequest> save(
			HrmsEmployeeEmploymentHistoryRequest hrmsEmployeeEmploymentHistoryRequest) {

		if (hrmsLocationCountryRepository.existsById(hrmsEmployeeEmploymentHistoryRequest.getCountryid())
				&& hrmsEmployeeRepository.existsById(hrmsEmployeeEmploymentHistoryRequest.getEmployeeid())) {
			if (hrmsEmployeeEmploymentHistoryRepository
					.existsByEmployeeidAndCountryidAndActiveAndEmployerAndDatendAndDatestartAndPositionAndDuties(

							hrmsEmployeeEmploymentHistoryRequest.getEmployeeid(),
							hrmsEmployeeEmploymentHistoryRequest.getCountryid(), 1,
							hrmsEmployeeEmploymentHistoryRequest.getEmployer(),
							hrmsEmployeeEmploymentHistoryRequest.getDate_end(),
							hrmsEmployeeEmploymentHistoryRequest.getDatestart(),
							hrmsEmployeeEmploymentHistoryRequest.getPosition(),
							hrmsEmployeeEmploymentHistoryRequest.getDuties())) {
				return ResponseEntity.status(HttpStatus.ALREADY_REPORTED).body(hrmsEmployeeEmploymentHistoryRequest);

			} else {

				HrmsEmployeeEmploymentHistory emphistory = new HrmsEmployeeEmploymentHistory();
				emphistory.setUnique_id(UUID.randomUUID());
				emphistory.setActive(1);
				emphistory.setApproved(0);
				emphistory.setCountryid(hrmsEmployeeEmploymentHistoryRequest.getCountryid());
				emphistory.setDatend(hrmsEmployeeEmploymentHistoryRequest.getDate_end());

				emphistory.setDatestart(hrmsEmployeeEmploymentHistoryRequest.getDatestart());

				emphistory.setDuties(hrmsEmployeeEmploymentHistoryRequest.getDuties());
				emphistory.setEmployeeid(hrmsEmployeeEmploymentHistoryRequest.getEmployeeid());
				emphistory.setEmployer(hrmsEmployeeEmploymentHistoryRequest.getEmployer());
				emphistory.setEmployeraddress(hrmsEmployeeEmploymentHistoryRequest.getEmployeraddress());
				emphistory.setEmployeremail(hrmsEmployeeEmploymentHistoryRequest.getEmployeremail());
				emphistory.setEmployerphoneprimary(hrmsEmployeeEmploymentHistoryRequest.getEmployerphoneprimary());
				emphistory.setEmployerphonesecondary(hrmsEmployeeEmploymentHistoryRequest.getEmployerphonesecondary());
				emphistory.setPosition(hrmsEmployeeEmploymentHistoryRequest.getPosition());
				emphistory.setReasonend(hrmsEmployeeEmploymentHistoryRequest.getReasonend());
				emphistory.setSupervisoraddress(hrmsEmployeeEmploymentHistoryRequest.getSupervisoraddress());
				emphistory.setSupervisorname(hrmsEmployeeEmploymentHistoryRequest.getSupervisorname());
				emphistory.setSupervisortelephonenumber(
						hrmsEmployeeEmploymentHistoryRequest.getSupervisortelephonenumber());
				emphistory.setIsyourcurrentjob(hrmsEmployeeEmploymentHistoryRequest.getIsyourcurrentjob());

				hrmsEmployeeEmploymentHistoryRepository.save(emphistory);
				return ResponseEntity.status(HttpStatus.OK).body(hrmsEmployeeEmploymentHistoryRequest);
			}
		} else {
			return ResponseEntity.status(HttpStatus.PRECONDITION_FAILED).body(hrmsEmployeeEmploymentHistoryRequest);
		}
	}

	@Override
	public ResponseEntity<HrmsEmployeeEmploymentHistoryResponseById> getEmployeeEmploymentHistoryById(int id) {
		String pattern = "yyyy-MM-dd";
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
		if (hrmsEmployeeEmploymentHistoryRepository.existsByIdAndActive(id, 1)) {

			HrmsEmployeeEmploymentHistoryResponseById emphistryrespo = new HrmsEmployeeEmploymentHistoryResponseById();

			HrmsEmployeeEmploymentHistory emphistry = hrmsEmployeeEmploymentHistoryRepository.findByIdAndActive(id, 1);

			emphistryrespo.setId(emphistry.getId());
			emphistryrespo.setCountryid(emphistry.getCountryid());
			emphistryrespo
					.setCountryname(hrmsLocationCountryRepository.findById(emphistry.getCountryid()).get().getName());
			emphistryrespo.setDate_end(emphistry.getDatend());
			emphistryrespo.setDatestart(emphistry.getDatestart());
			emphistryrespo.setDuties(emphistry.getDuties());
			emphistryrespo.setEmployer(emphistry.getEmployer());
			emphistryrespo.setEmployeraddress(emphistry.getEmployeraddress());
			emphistryrespo.setEmployeremail(emphistry.getEmployeremail());
			emphistryrespo.setEmployerphoneprimary(emphistry.getEmployerphoneprimary());
			emphistryrespo.setEmployerphonesecondary(emphistry.getEmployerphonesecondary());
			emphistryrespo.setIsyourcurrentjob(emphistry.getIsyourcurrentjob());
			emphistryrespo.setReasonend(emphistry.getReasonend());
			emphistryrespo.setPosition(emphistry.getPosition());
			emphistryrespo.setSupervisoraddress(emphistry.getSupervisoraddress());
			emphistryrespo.setSupervisorname(emphistry.getSupervisorname());
			emphistryrespo.setSupervisortelephonenumber(emphistry.getSupervisortelephonenumber());
			emphistryrespo.setApproved(emphistry.getApproved());
			emphistryrespo.setApprovalComment(emphistry.getApprovalComment());

			return ResponseEntity.status(HttpStatus.OK).body(emphistryrespo);
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
		}
	}

	@Override
	public ResponseEntity<HrmsEmployeeEmploymentHistoryRequest> updateEmployeeEmploymentHistory(
			HrmsEmployeeEmploymentHistoryRequest hrmsEmployeeEmploymentHistoryRequest, int id) {

		if (hrmsEmployeeEmploymentHistoryRepository.existsById(id)) {
			if (hrmsLocationCountryRepository.existsById(hrmsEmployeeEmploymentHistoryRequest.getCountryid())
					&& hrmsEmployeeRepository.existsById(hrmsEmployeeEmploymentHistoryRequest.getEmployeeid())) {

				HrmsEmployeeEmploymentHistory hrmsEmployeeEmploymentHistory = hrmsEmployeeEmploymentHistoryRepository
						.findById(id).get();
				hrmsEmployeeEmploymentHistory.setDate_updated(LocalDateTime.now());
				hrmsEmployeeEmploymentHistory.setCountryid(hrmsEmployeeEmploymentHistoryRequest.getCountryid());
				hrmsEmployeeEmploymentHistory.setDatend(hrmsEmployeeEmploymentHistoryRequest.getDate_end());

				hrmsEmployeeEmploymentHistory.setDatestart(hrmsEmployeeEmploymentHistoryRequest.getDatestart());

				hrmsEmployeeEmploymentHistory.setDuties(hrmsEmployeeEmploymentHistoryRequest.getDuties());
				hrmsEmployeeEmploymentHistory.setEmployeeid(hrmsEmployeeEmploymentHistoryRequest.getEmployeeid());
				hrmsEmployeeEmploymentHistory.setEmployer(hrmsEmployeeEmploymentHistoryRequest.getEmployer());
				hrmsEmployeeEmploymentHistory
						.setEmployeraddress(hrmsEmployeeEmploymentHistoryRequest.getEmployeraddress());
				hrmsEmployeeEmploymentHistory.setEmployeremail(hrmsEmployeeEmploymentHistoryRequest.getEmployeremail());
				hrmsEmployeeEmploymentHistory
						.setEmployerphoneprimary(hrmsEmployeeEmploymentHistoryRequest.getEmployerphoneprimary());
				hrmsEmployeeEmploymentHistory
						.setEmployerphonesecondary(hrmsEmployeeEmploymentHistoryRequest.getEmployerphonesecondary());
				hrmsEmployeeEmploymentHistory.setPosition(hrmsEmployeeEmploymentHistoryRequest.getPosition());
				hrmsEmployeeEmploymentHistory.setReasonend(hrmsEmployeeEmploymentHistoryRequest.getReasonend());
				hrmsEmployeeEmploymentHistory
						.setSupervisoraddress(hrmsEmployeeEmploymentHistoryRequest.getSupervisoraddress());
				hrmsEmployeeEmploymentHistory
						.setSupervisorname(hrmsEmployeeEmploymentHistoryRequest.getSupervisorname());
				hrmsEmployeeEmploymentHistory.setSupervisortelephonenumber(
						hrmsEmployeeEmploymentHistoryRequest.getSupervisortelephonenumber());
				hrmsEmployeeEmploymentHistory
						.setIsyourcurrentjob(hrmsEmployeeEmploymentHistoryRequest.getIsyourcurrentjob());
				hrmsEmployeeEmploymentHistory.setApproved(0);

				hrmsEmployeeEmploymentHistoryRepository.save(hrmsEmployeeEmploymentHistory);

				return ResponseEntity.status(HttpStatus.OK).body(hrmsEmployeeEmploymentHistoryRequest);
			} else {
				return ResponseEntity.status(HttpStatus.PRECONDITION_FAILED).body(hrmsEmployeeEmploymentHistoryRequest);
			}
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(hrmsEmployeeEmploymentHistoryRequest);
		}
	}

	@Override
	public ResponseEntity<?> deleteEmployeeEmploymentHistory(int id) {

		if (hrmsEmployeeEmploymentHistoryRepository.existsById(id)) {
			HrmsEmployeeEmploymentHistory hrmsEmployeeEmploymentHistory = hrmsEmployeeEmploymentHistoryRepository
					.findById(id).get();

			hrmsEmployeeEmploymentHistory.setActive(0);
			hrmsEmployeeEmploymentHistory.setDate_updated(LocalDateTime.now());

			hrmsEmployeeEmploymentHistoryRepository.save(hrmsEmployeeEmploymentHistory);
			return ResponseEntity.status(HttpStatus.OK).body(null);
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
		}
	}

	@Override
	public ResponseEntity<List<HrmsEmployeeEmploymentHistoryResponseById>> listEmployeeEmploymentHistory() {
		String pattern = "yyyy-MM-dd";
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
		List<HrmsEmployeeEmploymentHistoryResponseById> employeeemployementhistorylist = new ArrayList<>();

		List<HrmsEmployeeEmploymentHistory> emphistrylist = hrmsEmployeeEmploymentHistoryRepository
				.findByActiveOrderByEmployeeid(1);

		emphistrylist.forEach(emphistry -> {

			HrmsEmployeeEmploymentHistoryResponseById emphistryrespo = new HrmsEmployeeEmploymentHistoryResponseById();
			emphistryrespo.setId(emphistry.getId());
			emphistryrespo.setCountryid(emphistry.getCountryid());
			emphistryrespo
					.setCountryname(hrmsLocationCountryRepository.findById(emphistry.getCountryid()).get().getName());
			emphistryrespo.setDate_end(emphistry.getDatend());
			emphistryrespo.setDatestart(emphistry.getDatestart());
			emphistryrespo.setDuties(emphistry.getDuties());
			emphistryrespo.setEmployer(emphistry.getEmployer());
			emphistryrespo.setEmployeraddress(emphistry.getEmployeraddress());
			emphistryrespo.setEmployeremail(emphistry.getEmployeremail());
			emphistryrespo.setEmployerphoneprimary(emphistry.getEmployerphoneprimary());
			emphistryrespo.setEmployerphonesecondary(emphistry.getEmployerphonesecondary());
			emphistryrespo.setIsyourcurrentjob(emphistry.getIsyourcurrentjob());
			emphistryrespo.setReasonend(emphistry.getReasonend());
			emphistryrespo.setPosition(emphistry.getPosition());
			emphistryrespo.setSupervisoraddress(emphistry.getSupervisoraddress());
			emphistryrespo.setSupervisorname(emphistry.getSupervisorname());
			emphistryrespo.setSupervisortelephonenumber(emphistry.getSupervisortelephonenumber());
			emphistryrespo.setApproved(emphistry.getApproved());

			emphistryrespo.setEmployeeid(emphistry.getEmployeeid());

			HrmsEmployee hrmsEmployee = hrmsEmployeeRepository.findById(emphistry.getEmployeeid()).get();

			StringBuilder fullname = new StringBuilder();
			fullname.append(hrmsEmployee.getFirstName().trim());
			fullname.append("  " + hrmsEmployee.getMiddleName().trim());
			fullname.append(" " + hrmsEmployee.getLastName().trim());

			emphistryrespo.setEmployeename(fullname.toString());
			emphistryrespo.setApprovalComment(emphistry.getApprovalComment());

			employeeemployementhistorylist.add(emphistryrespo);
		});

		return ResponseEntity.status(HttpStatus.OK).body(employeeemployementhistorylist);
	}

	@Override
	public ResponseEntity<List<HrmsEmployeeEmploymentHistoryResponseByEmpId>> getEmployeeEmploymentHistoryByEmpId(
			int empId) {
		String pattern = "yyyy-MM-dd";
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
		if (hrmsEmployeeEmploymentHistoryRepository.existsByEmployeeidAndActive(empId, 1)) {

			List<HrmsEmployeeEmploymentHistoryResponseByEmpId> emphstrlist = new ArrayList<>();

			List<HrmsEmployeeEmploymentHistory> emplymentlist = hrmsEmployeeEmploymentHistoryRepository
					.findByEmployeeidAndActiveOrderByDatestartDesc(empId, 1);

			emplymentlist.forEach(dbemp -> {

				HrmsEmployeeEmploymentHistoryResponseByEmpId emphstryrespo = new HrmsEmployeeEmploymentHistoryResponseByEmpId();

				emphstryrespo.setId(dbemp.getId());

				emphstryrespo.setFromDate(dbemp.getDatestart());

				emphstryrespo.setToDate(dbemp.getDatend());
				emphstryrespo.setJobTitle(dbemp.getPosition());
				emphstryrespo.setInstituteName(dbemp.getEmployer());
				emphstryrespo.setApproved(dbemp.getApproved());
				emphstryrespo.setApprovalComment(dbemp.getApprovalComment());

				emphstrlist.add(emphstryrespo);

			});

			return ResponseEntity.status(HttpStatus.OK).body(emphstrlist);
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
		}
	}

	@Override
	public ResponseEntity<List<HrmsEmployeeEmploymentHistoryResponseById>> listEmployeeEmploymentHistoryNonApproved() {

		String pattern = "yyyy-MM-dd";
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
		List<HrmsEmployeeEmploymentHistoryResponseById> employeeemployementhistorylist = new ArrayList<>();

		List<HrmsEmployeeEmploymentHistory> emphistrylist = hrmsEmployeeEmploymentHistoryRepository
				.findByActiveAndApprovedOrderByEmployeeid(1, 0);

		emphistrylist.forEach(emphistry -> {

			HrmsEmployeeEmploymentHistoryResponseById emphistryrespo = new HrmsEmployeeEmploymentHistoryResponseById();
			emphistryrespo.setId(emphistry.getId());
			emphistryrespo.setCountryid(emphistry.getCountryid());
			emphistryrespo
					.setCountryname(hrmsLocationCountryRepository.findById(emphistry.getCountryid()).get().getName());
			emphistryrespo.setDate_end(emphistry.getDatend());
			emphistryrespo.setDatestart(emphistry.getDatestart());
			emphistryrespo.setDuties(emphistry.getDuties());
			emphistryrespo.setEmployer(emphistry.getEmployer());
			emphistryrespo.setEmployeraddress(emphistry.getEmployeraddress());
			emphistryrespo.setEmployeremail(emphistry.getEmployeremail());
			emphistryrespo.setEmployerphoneprimary(emphistry.getEmployerphoneprimary());
			emphistryrespo.setEmployerphonesecondary(emphistry.getEmployerphonesecondary());
			emphistryrespo.setIsyourcurrentjob(emphistry.getIsyourcurrentjob());
			emphistryrespo.setReasonend(emphistry.getReasonend());
			emphistryrespo.setPosition(emphistry.getPosition());
			emphistryrespo.setSupervisoraddress(emphistry.getSupervisoraddress());
			emphistryrespo.setSupervisorname(emphistry.getSupervisorname());
			emphistryrespo.setSupervisortelephonenumber(emphistry.getSupervisortelephonenumber());
			emphistryrespo.setApproved(emphistry.getApproved());

			emphistryrespo.setEmployeeid(emphistry.getEmployeeid());

			HrmsEmployee hrmsEmployee = hrmsEmployeeRepository.findById(emphistry.getEmployeeid()).get();

			StringBuilder fullname = new StringBuilder();
			fullname.append(hrmsEmployee.getFirstName().trim());
			fullname.append("  " + hrmsEmployee.getMiddleName().trim());
			fullname.append(" " + hrmsEmployee.getLastName().trim());

			emphistryrespo.setEmployeename(fullname.toString());
			emphistryrespo.setApprovalComment(emphistry.getApprovalComment());

			employeeemployementhistorylist.add(emphistryrespo);
		});

		return ResponseEntity.status(HttpStatus.OK).body(employeeemployementhistorylist);
	}

	@Override
	public ResponseEntity<?> approveOrRejectEmployeeEmploymentHistory(EmployeeApprovalComment employeeApprovalComment,
			int id, int status) {
		if (hrmsEmployeeEmploymentHistoryRepository.existsByIdAndActive(id, 1) && (status == 1 || status == -1)) {

			HrmsEmployeeEmploymentHistory hrmsEmployeeEmploymentHistory = hrmsEmployeeEmploymentHistoryRepository
					.findByIdAndActive(id, 1);

			hrmsEmployeeEmploymentHistory.setApproved(status);
			hrmsEmployeeEmploymentHistory.setDate_updated(LocalDateTime.now());
			hrmsEmployeeEmploymentHistory.setApprovalComment(employeeApprovalComment.getComment());
			hrmsEmployeeEmploymentHistory.setApproverEmployeeid(employeeApprovalComment.getApproverEmployeeid());
			hrmsEmployeeEmploymentHistoryRepository.saveAndFlush(hrmsEmployeeEmploymentHistory);

			String messages = "";

			HrmsEmployee hrmsEmployee = hrmsEmployeeRepository.findById(hrmsEmployeeEmploymentHistory.getEmployeeid())
					.get();

			StringBuilder fullname = new StringBuilder();
			fullname.append(hrmsEmployee.getFirstName().trim());
			fullname.append("  " + hrmsEmployee.getMiddleName().trim());
			fullname.append(" " + hrmsEmployee.getLastName().trim());
			String username = fullname.toString();
			String usermail = hrmsEmployee.getEmail();
			if (status == -1) {
				messages = " Your Employement history entry at  " + hrmsEmployeeEmploymentHistory.getEmployer()
						+ " has been rejected,Kindly edit and re-submit again " + "  Comments for rejection is  "
						+ employeeApprovalComment.getComment();
				sendEmail.sendmailNotification(username, usermail, messages);
			}

			return ResponseEntity.status(HttpStatus.OK).body(id);
		} else {
			return ResponseEntity.status(HttpStatus.PRECONDITION_FAILED).body(id);
		}
	}

}
