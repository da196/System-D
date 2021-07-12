package com.Hrms.Employee.Service;

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
import com.Hrms.Employee.DTO.HrmsEmployeeRefereeResponse;
import com.Hrms.Employee.Entity.HrmsEmployee;
import com.Hrms.Employee.Entity.HrmsEmployeeReferee;
import com.Hrms.Employee.Repository.HrmsEmployeeRefereeRepository;
import com.Hrms.Employee.Repository.HrmsEmployeeRepository;
import com.Hrms.Employee.Repository.HrmsNationalityRepository;
import com.Hrms.Employee.Repository.HrmsRefereeCategoryRepository;

@Service
public class HrmsEmployeeRefereeServiceImpl implements HrmsEmployeeRefereeService {

	@Autowired
	private HrmsEmployeeRefereeRepository hrmsEmployeeRefereeRepository;

	@Autowired
	private HrmsEmployeeRepository hrmsEmployeeRepository;

	@Autowired
	private HrmsNationalityRepository hrmsNationalityRepository;

	@Autowired
	private HrmsRefereeCategoryRepository hrmsRefereeCategoryRepository;

	@Autowired
	private SendEmail sendEmail;

	@Override
	public ResponseEntity<HrmsEmployeeReferee> addEmployeeReferee(HrmsEmployeeReferee hrmsEmployeeReferee) {

		if (hrmsEmployeeRefereeRepository.existsByEmployeeidAndActiveAndFullname(hrmsEmployeeReferee.getEmployeeid(), 1,
				hrmsEmployeeReferee.getFullname())) {

			return ResponseEntity.status(HttpStatus.ALREADY_REPORTED).body(hrmsEmployeeReferee);
		} else {

			UUID uuid = UUID.randomUUID();
			hrmsEmployeeReferee.setUnique_id(uuid);
			hrmsEmployeeReferee.setActive(1);
			hrmsEmployeeReferee.setApproved(0);

			return ResponseEntity.status(HttpStatus.OK).body(hrmsEmployeeRefereeRepository.save(hrmsEmployeeReferee));
		}

	}

	@Override
	public ResponseEntity<List<HrmsEmployeeRefereeResponse>> getEmployeeRefereeByEmpId(int id) {

		if (hrmsEmployeeRefereeRepository.existsByEmployeeidAndActive(id, 1)) {

			List<HrmsEmployeeRefereeResponse> refereelistreturn = new ArrayList<>();

			List<HrmsEmployeeReferee> refereelist = hrmsEmployeeRefereeRepository.findByEmployeeidAndActive(id, 1);

			refereelist.forEach(dbemp -> {
				HrmsEmployeeRefereeResponse referee = new HrmsEmployeeRefereeResponse();

				referee.setAddress(dbemp.getAddress());
				referee.setEmail(dbemp.getEmail());
				referee.setFullname(dbemp.getFullname());
				referee.setId(dbemp.getId());
				referee.setInstitution(dbemp.getInstitution());
				referee.setNationality(hrmsNationalityRepository.findById(dbemp.getNationalityid()).get().getName());
				referee.setNationalityid(dbemp.getNationalityid());
				referee.setPhoneprimary(dbemp.getPhoneprimary());
				referee.setPhonesecondary(dbemp.getPhonesecondary());
				referee.setPosition(dbemp.getPosition());
				referee.setApprovalComment(referee.getApprovalComment());

				// the below has been requested to be removed by DCRM department
				// referee.setRefereecategoryid(dbemp.getRefereecategoryid());
				// referee.setRefereecategoryname(
				// hrmsRefereeCategoryRepository.findById(dbemp.getRefereecategoryid()).get().getName());

				referee.setApproved(dbemp.getApproved());

				refereelistreturn.add(referee);

			});

			return ResponseEntity.status(HttpStatus.OK).body(refereelistreturn);
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
		}
	}

	@Override
	public ResponseEntity<HrmsEmployeeReferee> updateEmployeeReferee(HrmsEmployeeReferee hrmsEmployeeReferee, int id) {

		LocalDateTime LocalTime = LocalDateTime.now();
		hrmsEmployeeReferee.setDate_updated(LocalTime);
		hrmsEmployeeReferee.setApproved(0);
		if (hrmsEmployeeRefereeRepository.existsById(id)) {
			hrmsEmployeeReferee.setActive(1);
			return ResponseEntity.status(HttpStatus.OK).body(hrmsEmployeeRefereeRepository.save(hrmsEmployeeReferee));
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(hrmsEmployeeReferee);
		}
	}

	@Override
	public ResponseEntity<?> deleteEmployeeReferee(int id) {

		if (hrmsEmployeeRefereeRepository.existsByIdAndActive(id, 1)) {
			HrmsEmployeeReferee hrmsEmployeeReferee = hrmsEmployeeRefereeRepository.findByIdAndActive(id, 1);
			hrmsEmployeeReferee.setActive(0);
			hrmsEmployeeReferee.setDate_updated(LocalDateTime.now());
			hrmsEmployeeRefereeRepository.save(hrmsEmployeeReferee);
			return ResponseEntity.status(HttpStatus.OK).body(null);
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
		}
	}

	@Override
	public ResponseEntity<List<HrmsEmployeeRefereeResponse>> listEmployeeReferees() {

		List<HrmsEmployeeRefereeResponse> refereelistreturn = new ArrayList<>();

		List<HrmsEmployeeReferee> refereelist = hrmsEmployeeRefereeRepository.findByActiveOrderByEmployeeid(1);

		refereelist.forEach(dbemp -> {
			HrmsEmployeeRefereeResponse referee = new HrmsEmployeeRefereeResponse();

			referee.setAddress(dbemp.getAddress());
			referee.setEmail(dbemp.getEmail());
			referee.setFullname(dbemp.getFullname());
			referee.setId(dbemp.getId());
			referee.setInstitution(dbemp.getInstitution());
			referee.setNationality(hrmsNationalityRepository.findById(dbemp.getNationalityid()).get().getName());
			referee.setNationalityid(dbemp.getNationalityid());
			referee.setPhoneprimary(dbemp.getPhoneprimary());
			referee.setPhonesecondary(dbemp.getPhonesecondary());
			referee.setPosition(dbemp.getPosition());
			referee.setApprovalComment(dbemp.getApprovalComment());

			// the below has been requested to be removed by DCRM department
			// referee.setRefereecategoryid(dbemp.getRefereecategoryid());
			// referee.setRefereecategoryname(
			// hrmsRefereeCategoryRepository.findById(dbemp.getRefereecategoryid()).get().getName());

			referee.setApproved(dbemp.getApproved());
			referee.setEmployeeid(dbemp.getEmployeeid());
			HrmsEmployee hrmsEmployee = hrmsEmployeeRepository.findById(dbemp.getEmployeeid()).get();

			StringBuilder fullname = new StringBuilder();
			fullname.append(hrmsEmployee.getFirstName().trim());
			fullname.append("  " + hrmsEmployee.getMiddleName().trim());
			fullname.append(" " + hrmsEmployee.getLastName().trim());

			referee.setEmployeename(fullname.toString());

			refereelistreturn.add(referee);

		});

		return ResponseEntity.status(HttpStatus.OK).body(refereelistreturn);
	}

	@Override
	public ResponseEntity<List<HrmsEmployeeRefereeResponse>> getEmployeeRefereeByEmail(String email) {
		if (hrmsEmployeeRepository.existsByEmail(email)) {

			List<HrmsEmployeeRefereeResponse> refereelistreturn = new ArrayList<>();

			List<HrmsEmployeeReferee> refereelist = hrmsEmployeeRefereeRepository
					.findByEmployeeidAndActive(hrmsEmployeeRepository.findByEmail(email).getId(), 1);

			refereelist.forEach(dbemp -> {
				HrmsEmployeeRefereeResponse referee = new HrmsEmployeeRefereeResponse();

				referee.setAddress(dbemp.getAddress());
				referee.setEmail(dbemp.getEmail());
				referee.setFullname(dbemp.getFullname());
				referee.setId(dbemp.getId());
				referee.setInstitution(dbemp.getInstitution());
				referee.setNationality(hrmsNationalityRepository.findById(dbemp.getNationalityid()).get().getName());
				referee.setNationalityid(dbemp.getNationalityid());
				referee.setPhoneprimary(dbemp.getPhoneprimary());
				referee.setPhonesecondary(dbemp.getPhonesecondary());
				referee.setPosition(dbemp.getPosition());
				referee.setApprovalComment(dbemp.getApprovalComment());

				// below was requested to be removed by DCRM team
				// referee.setRefereecategoryid(dbemp.getRefereecategoryid());
				// referee.setRefereecategoryname(
				// hrmsRefereeCategoryRepository.findById(dbemp.getRefereecategoryid()).get().getName());
				referee.setApproved(dbemp.getApproved());

				refereelistreturn.add(referee);

			});

			return ResponseEntity.status(HttpStatus.OK).body(refereelistreturn);
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
		}
	}

	@Override
	public ResponseEntity<HrmsEmployeeRefereeResponse> getEmployeeRefereeById(int id) {

		if (hrmsEmployeeRefereeRepository.existsById(id)) {

			HrmsEmployeeReferee dbemp = hrmsEmployeeRefereeRepository.findByIdAndActive(id, 1);

			HrmsEmployeeRefereeResponse referee = new HrmsEmployeeRefereeResponse();

			referee.setAddress(dbemp.getAddress());
			referee.setEmail(dbemp.getEmail());
			referee.setFullname(dbemp.getFullname());
			referee.setId(dbemp.getId());
			referee.setInstitution(dbemp.getInstitution());
			referee.setNationality(hrmsNationalityRepository.findById(dbemp.getNationalityid()).get().getName());
			referee.setNationalityid(dbemp.getNationalityid());
			referee.setPhoneprimary(dbemp.getPhoneprimary());
			referee.setPhonesecondary(dbemp.getPhonesecondary());
			referee.setPosition(dbemp.getPosition());
			referee.setApprovalComment(dbemp.getApprovalComment());

			// below was requested to be removed by DCRM Team
			// referee.setRefereecategoryid(dbemp.getRefereecategoryid());
			// referee.setRefereecategoryname(
			// hrmsRefereeCategoryRepository.findById(dbemp.getRefereecategoryid()).get().getName());
			referee.setApproved(dbemp.getApproved());

			return ResponseEntity.status(HttpStatus.OK).body(referee);
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
		}
	}

	@Override
	public ResponseEntity<List<HrmsEmployeeRefereeResponse>> listEmployeeRefereesNonApproved() {
		List<HrmsEmployeeRefereeResponse> refereelistreturn = new ArrayList<>();

		List<HrmsEmployeeReferee> refereelist = hrmsEmployeeRefereeRepository
				.findByActiveAndApprovedOrderByEmployeeid(1, 0);

		refereelist.forEach(dbemp -> {
			HrmsEmployeeRefereeResponse referee = new HrmsEmployeeRefereeResponse();

			referee.setAddress(dbemp.getAddress());
			referee.setEmail(dbemp.getEmail());
			referee.setFullname(dbemp.getFullname());
			referee.setId(dbemp.getId());
			referee.setInstitution(dbemp.getInstitution());
			referee.setNationality(hrmsNationalityRepository.findById(dbemp.getNationalityid()).get().getName());
			referee.setNationalityid(dbemp.getNationalityid());
			referee.setPhoneprimary(dbemp.getPhoneprimary());
			referee.setPhonesecondary(dbemp.getPhonesecondary());
			referee.setPosition(dbemp.getPosition());
			referee.setApprovalComment(dbemp.getApprovalComment());

			// the below has been requested to be removed by DCRM department
			// referee.setRefereecategoryid(dbemp.getRefereecategoryid());
			// referee.setRefereecategoryname(
			// hrmsRefereeCategoryRepository.findById(dbemp.getRefereecategoryid()).get().getName());

			referee.setApproved(dbemp.getApproved());
			referee.setEmployeeid(dbemp.getEmployeeid());
			HrmsEmployee hrmsEmployee = hrmsEmployeeRepository.findById(dbemp.getEmployeeid()).get();

			StringBuilder fullname = new StringBuilder();
			fullname.append(hrmsEmployee.getFirstName().trim());
			fullname.append("  " + hrmsEmployee.getMiddleName().trim());
			fullname.append(" " + hrmsEmployee.getLastName().trim());

			referee.setEmployeename(fullname.toString());

			refereelistreturn.add(referee);

		});

		return ResponseEntity.status(HttpStatus.OK).body(refereelistreturn);
	}

	@Override
	public ResponseEntity<?> approveOrRejectEmployeeReferee(EmployeeApprovalComment employeeApprovalComment, int id,
			int status) {
		if (hrmsEmployeeRefereeRepository.existsByIdAndActive(id, 1) && (status == 1 || status == -1)) {

			HrmsEmployeeReferee hrmsEmployeeReferee = hrmsEmployeeRefereeRepository.findByIdAndActive(id, 1);
			hrmsEmployeeReferee.setApproved(status);
			hrmsEmployeeReferee.setDate_updated(LocalDateTime.now());
			hrmsEmployeeReferee.setApprovalComment(employeeApprovalComment.getComment());
			hrmsEmployeeReferee.setApproverEmployeeid(employeeApprovalComment.getApproverEmployeeid());

			hrmsEmployeeRefereeRepository.saveAndFlush(hrmsEmployeeReferee);
			// send notification
			String messages = "";

			HrmsEmployee hrmsEmployee = hrmsEmployeeRepository.findById(hrmsEmployeeReferee.getEmployeeid()).get();

			StringBuilder fullname = new StringBuilder();
			fullname.append(hrmsEmployee.getFirstName().trim());
			fullname.append("  " + hrmsEmployee.getMiddleName().trim());
			fullname.append(" " + hrmsEmployee.getLastName().trim());
			String username = fullname.toString();
			String usermail = hrmsEmployee.getEmail();
			if (status == -1) {
				messages = " Your Referee entry for   " + hrmsEmployeeReferee.getFullname()
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
