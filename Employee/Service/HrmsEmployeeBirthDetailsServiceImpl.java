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
import com.Hrms.Employee.DTO.EmployeeBirthDetailsRequest;
import com.Hrms.Employee.DTO.EmployeeBirthDetailsResponse;
import com.Hrms.Employee.Entity.HrmsAttachment;
import com.Hrms.Employee.Entity.HrmsEmployee;
import com.Hrms.Employee.Entity.HrmsEmployeeBirthDetails;
import com.Hrms.Employee.Repository.HrmsAttachmentRepository;
import com.Hrms.Employee.Repository.HrmsAttachmentTypeRepository;
import com.Hrms.Employee.Repository.HrmsEmployeeBirthDetailsRepository;
import com.Hrms.Employee.Repository.HrmsEmployeeRepository;
import com.Hrms.Employee.Repository.HrmsLocationCityRepository;
import com.Hrms.Employee.Repository.HrmsLocationCountryRepository;
import com.Hrms.Employee.Repository.HrmsLocationDistrictRepository;
import com.Hrms.Employee.Repository.HrmsLocationWardRepository;

@Service
public class HrmsEmployeeBirthDetailsServiceImpl implements HrmsEmployeeBirthDetailsService {

	@Autowired
	private HrmsEmployeeBirthDetailsRepository hrmsEmployeeBirthDetailsRepository;

	@Autowired
	private HrmsAttachmentRepository hrmsAttachmentRepository;

	@Autowired
	private HrmsAttachmentTypeRepository hrmsAttachmentTypeRepository;

	@Autowired
	private HrmsEmployeeRepository hrmsEmployeeRepository;

	@Autowired
	private HrmsLocationCountryRepository hrmsLocationCountryRepository;

	@Autowired
	private HrmsLocationWardRepository hrmsLocationWardRepository;

	@Autowired
	private HrmsLocationDistrictRepository hrmsLocationDistrictRepository;

	@Autowired
	private HrmsLocationCityRepository hrmsLocationCityRepository;

	@Autowired
	private SendEmail sendEmail;

	@Override
	public ResponseEntity<HrmsEmployeeBirthDetails> addEmpBirthDetails(
			EmployeeBirthDetailsRequest employeeBirthDetailsRequest) {
		if (hrmsAttachmentTypeRepository.existsById(employeeBirthDetailsRequest.getAttachmenttypeid())
				&& hrmsEmployeeRepository.existsById(employeeBirthDetailsRequest.getEmployeeid())
				&& hrmsLocationCountryRepository.existsById(employeeBirthDetailsRequest.getCountryid())) {
			if (!(hrmsEmployeeBirthDetailsRepository
					.existsByEmployeeidAndActive(employeeBirthDetailsRequest.getEmployeeid(), 1))) {
				// add attachment details now
				HrmsAttachment attachment = new HrmsAttachment();
				attachment.setActive(1);
				attachment.setApproved(0);
				attachment.setAttachmenttypeid(employeeBirthDetailsRequest.getAttachmenttypeid());

				attachment.setDescription(employeeBirthDetailsRequest.getDescription());
				attachment.setName(employeeBirthDetailsRequest.getAttachmentname());
				attachment.setUri(employeeBirthDetailsRequest.getUri());
				attachment.setUnique_id(UUID.randomUUID());

				// insert attachment now
				HrmsAttachment attachmentadded = hrmsAttachmentRepository.save(attachment);

				// add birth details now

				HrmsEmployeeBirthDetails bdetails = new HrmsEmployeeBirthDetails();
				bdetails.setActive(1);
				bdetails.setAdditionalinformation(employeeBirthDetailsRequest.getDescription());
				bdetails.setApproved(0);
				bdetails.setBirthcertificateid(attachmentadded.getId());
				bdetails.setCountryid(employeeBirthDetailsRequest.getCountryid());
				bdetails.setCityid(employeeBirthDetailsRequest.getCityid());
				bdetails.setDistrictid(employeeBirthDetailsRequest.getDistrictid());
				bdetails.setWardid(employeeBirthDetailsRequest.getWardid());
				bdetails.setDob(employeeBirthDetailsRequest.getDob());
				bdetails.setEmployeeid(employeeBirthDetailsRequest.getEmployeeid());
				bdetails.setUnique_id(UUID.randomUUID());
				bdetails.setDob(
						hrmsEmployeeRepository.findById(employeeBirthDetailsRequest.getEmployeeid()).get().getDob());

				// insert into employee birth details table now

				return ResponseEntity.status(HttpStatus.OK).body(hrmsEmployeeBirthDetailsRepository.save(bdetails));
			} else {
				return ResponseEntity.status(HttpStatus.ALREADY_REPORTED).body(null);

			}
		} else {
			return ResponseEntity.status(HttpStatus.PRECONDITION_FAILED).body(null);

		}
	}

	@Override
	public ResponseEntity<HrmsEmployeeBirthDetails> updateEmployeeBirthDetails(
			EmployeeBirthDetailsRequest employeeBirthDetailsRequest, int id) {

		if (hrmsEmployeeBirthDetailsRepository.existsById(id)
				&& hrmsAttachmentRepository.existsById(employeeBirthDetailsRequest.getAttachmentid())) {
			HrmsEmployeeBirthDetails bdetails = hrmsEmployeeBirthDetailsRepository.findById(id).get();
			HrmsAttachment attachment = hrmsAttachmentRepository.findById(employeeBirthDetailsRequest.getAttachmentid())
					.get();
			if (employeeBirthDetailsRequest.getUri() == null) {
				attachment.setDate_updated(LocalDateTime.now());
				attachment.setDescription(employeeBirthDetailsRequest.getDescription());
				attachment.setApproved(0);

			}

			if (employeeBirthDetailsRequest.getUri() != null) {
				attachment.setDate_updated(LocalDateTime.now());

				attachment.setAttachmenttypeid(employeeBirthDetailsRequest.getAttachmenttypeid());

				attachment.setName(employeeBirthDetailsRequest.getAttachmentname());
				attachment.setUri(employeeBirthDetailsRequest.getUri());
				attachment.setDescription(employeeBirthDetailsRequest.getDescription());
				attachment.setApproved(0);
			}
			// update attchment table now

			hrmsAttachmentRepository.save(attachment);

			bdetails.setDate_updated(LocalDateTime.now());
			bdetails.setApproved(0);

			bdetails.setAdditionalinformation(employeeBirthDetailsRequest.getDescription());
			bdetails.setAdditionalinformation(employeeBirthDetailsRequest.getAdditionalinformation());
			bdetails.setCountryid(employeeBirthDetailsRequest.getCountryid());
			bdetails.setCityid(employeeBirthDetailsRequest.getCityid());
			bdetails.setDistrictid(employeeBirthDetailsRequest.getDistrictid());
			bdetails.setWardid(employeeBirthDetailsRequest.getWardid());
			bdetails.setDob(
					hrmsEmployeeRepository.findById(employeeBirthDetailsRequest.getEmployeeid()).get().getDob());
			// now update birth details
			return ResponseEntity.status(HttpStatus.OK).body(hrmsEmployeeBirthDetailsRepository.save(bdetails));

		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
		}
	}

	@Override
	public ResponseEntity<?> deleteEmployeeBirthDetails(int id) {

		if (hrmsEmployeeBirthDetailsRepository.existsByIdAndActive(id, 1)) {

			// set empbirth details to deactive mode
			HrmsEmployeeBirthDetails hrmsEmployeeBirthDetails = hrmsEmployeeBirthDetailsRepository.findById(id).get();
			hrmsEmployeeBirthDetails.setActive(0);
			hrmsEmployeeBirthDetails.setDate_updated(LocalDateTime.now());

			int i = hrmsEmployeeBirthDetailsRepository.save(hrmsEmployeeBirthDetails).getBirthcertificateid();

			// set attachment to deactive
			HrmsAttachment hrmsAttachment = hrmsAttachmentRepository.findById(i).get();
			hrmsAttachment.setActive(0);
			hrmsAttachment.setDate_updated(LocalDateTime.now());

			hrmsAttachmentRepository.save(hrmsAttachment);

			return ResponseEntity.status(HttpStatus.OK).body(null);
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
		}
	}

	@Override
	public ResponseEntity<EmployeeBirthDetailsResponse> getEmployeeBirthDetailsByEmpId(int empid) {

		if (hrmsEmployeeBirthDetailsRepository.existsByEmployeeidAndActive(empid, 1)) {
			HrmsEmployeeBirthDetails empbirthdtls = hrmsEmployeeBirthDetailsRepository.findByEmployeeidAndActive(empid,
					1);

			EmployeeBirthDetailsResponse empbdtls = new EmployeeBirthDetailsResponse();

			String pattern = "yyyy-MM-dd";
			SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
			empbdtls.setCityid(empbirthdtls.getCityid());
			if (empbirthdtls.getCityid() != 0) {
				empbdtls.setCityname(hrmsLocationCityRepository.findById(empbirthdtls.getCityid()).get().getName());
			}
			empbdtls.setDistrictid(empbirthdtls.getDistrictid());
			if (empbirthdtls.getDistrictid() != 0) {
				empbdtls.setDistrictname(hrmsLocationDistrictRepository.findById(1).get().getName());
			}
			empbdtls.setWardid(empbirthdtls.getWardid());
			if (empbirthdtls.getWardid() != 0) {
				empbdtls.setWardanme(hrmsLocationWardRepository.findById(empbirthdtls.getWardid()).get().getName());
			}
			empbdtls.setActive(empbirthdtls.getActive());
			empbdtls.setAdditionalinformation(empbirthdtls.getAdditionalinformation());
			empbdtls.setApproved(empbirthdtls.getApproved());
			empbdtls.setAttachmentid(empbirthdtls.getBirthcertificateid());
			empbdtls.setAttachmentname(
					hrmsAttachmentRepository.findById(empbirthdtls.getBirthcertificateid()).get().getName());
			empbdtls.setAttachmenttypename(hrmsAttachmentTypeRepository.findById(
					hrmsAttachmentRepository.findById(empbirthdtls.getBirthcertificateid()).get().getAttachmenttypeid())
					.get().getName());
			empbdtls.setAttachmenttypeid(hrmsAttachmentRepository.findById(empbirthdtls.getBirthcertificateid()).get()
					.getAttachmenttypeid());
			empbdtls.setCountryid(empbirthdtls.getCountryid());
			empbdtls.setCountryname(
					hrmsLocationCountryRepository.findById(empbirthdtls.getCountryid()).get().getName());
			empbdtls.setDescription(
					hrmsAttachmentRepository.findById(empbirthdtls.getBirthcertificateid()).get().getDescription());

			empbdtls.setDob(simpleDateFormat.format(empbirthdtls.getDob()));
			empbdtls.setEmployeeid(empbirthdtls.getEmployeeid());
			empbdtls.setId(empbirthdtls.getId());
			empbdtls.setUri(hrmsAttachmentRepository.findById(empbirthdtls.getBirthcertificateid()).get().getUri());
			empbdtls.setApprovalComment(empbirthdtls.getApprovalComment());

			return ResponseEntity.status(HttpStatus.OK).body(empbdtls);

		} else {

			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
		}
	}

	@Override
	public ResponseEntity<EmployeeBirthDetailsResponse> getEmployeeBirthDetailsById(int id) {
		if (hrmsEmployeeBirthDetailsRepository.existsByIdAndActive(id, 1)) {
			HrmsEmployeeBirthDetails empbirthdtls = hrmsEmployeeBirthDetailsRepository.findById(id).get();

			EmployeeBirthDetailsResponse empbdtls = new EmployeeBirthDetailsResponse();

			String pattern = "yyyy-MM-dd";
			SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);

			empbdtls.setActive(empbirthdtls.getActive());
			empbdtls.setAdditionalinformation(empbirthdtls.getAdditionalinformation());
			empbdtls.setApproved(empbirthdtls.getApproved());
			empbdtls.setAttachmentid(empbirthdtls.getBirthcertificateid());
			empbdtls.setAttachmentname(
					hrmsAttachmentRepository.findById(empbirthdtls.getBirthcertificateid()).get().getName());
			empbdtls.setAttachmenttypename(hrmsAttachmentTypeRepository.findById(
					hrmsAttachmentRepository.findById(empbirthdtls.getBirthcertificateid()).get().getAttachmenttypeid())
					.get().getName());
			empbdtls.setAttachmenttypeid(hrmsAttachmentRepository.findById(empbirthdtls.getBirthcertificateid()).get()
					.getAttachmenttypeid());
			empbdtls.setCountryid(empbirthdtls.getCountryid());
			empbdtls.setCountryname(
					hrmsLocationCountryRepository.findById(empbirthdtls.getCountryid()).get().getName());
			empbdtls.setDescription(
					hrmsAttachmentRepository.findById(empbirthdtls.getBirthcertificateid()).get().getDescription());

			empbdtls.setDob(simpleDateFormat.format(empbirthdtls.getDob()));
			empbdtls.setEmployeeid(empbirthdtls.getEmployeeid());
			empbdtls.setId(empbirthdtls.getId());
			empbdtls.setUri(hrmsAttachmentRepository.findById(empbirthdtls.getBirthcertificateid()).get().getUri());
			empbdtls.setApprovalComment(empbirthdtls.getApprovalComment());

			empbdtls.setCityid(empbirthdtls.getCityid());
			if (empbirthdtls.getCityid() != 0) {
				empbdtls.setCityname(hrmsLocationCityRepository.findById(empbirthdtls.getCityid()).get().getName());
			}
			empbdtls.setDistrictid(empbirthdtls.getDistrictid());
			if (empbirthdtls.getDistrictid() != 0) {
				empbdtls.setDistrictname(hrmsLocationDistrictRepository.findById(1).get().getName());
			}
			empbdtls.setWardid(empbirthdtls.getWardid());
			if (empbirthdtls.getWardid() != 0) {
				empbdtls.setWardanme(hrmsLocationWardRepository.findById(empbirthdtls.getWardid()).get().getName());
			}

			return ResponseEntity.status(HttpStatus.OK).body(empbdtls);

		} else {

			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
		}
	}

	@Override
	public ResponseEntity<List<EmployeeBirthDetailsResponse>> listEmployeeBirthDetails() {

		List<EmployeeBirthDetailsResponse> employeebdetailslist = new ArrayList<>();

		hrmsEmployeeBirthDetailsRepository.findByActiveOrderByEmployeeid(1).forEach(employeebdetails -> {

			EmployeeBirthDetailsResponse empbdtls = new EmployeeBirthDetailsResponse();

			String pattern = "yyyy-MM-dd";
			SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);

			empbdtls.setActive(employeebdetails.getActive());
			empbdtls.setAdditionalinformation(employeebdetails.getAdditionalinformation());
			empbdtls.setApproved(employeebdetails.getApproved());
			empbdtls.setCountryid(employeebdetails.getCountryid());
			empbdtls.setCountryname(
					hrmsLocationCountryRepository.findById(employeebdetails.getCountryid()).get().getName());

			empbdtls.setAttachmentid(employeebdetails.getBirthcertificateid());
			empbdtls.setAttachmentname(
					hrmsAttachmentRepository.findById(employeebdetails.getBirthcertificateid()).get().getName());
			empbdtls.setAttachmenttypename(hrmsAttachmentTypeRepository.findById(hrmsAttachmentRepository
					.findById(employeebdetails.getBirthcertificateid()).get().getAttachmenttypeid()).get().getName());
			empbdtls.setAttachmenttypeid(hrmsAttachmentRepository.findById(employeebdetails.getBirthcertificateid())
					.get().getAttachmenttypeid());

			empbdtls.setDescription(
					hrmsAttachmentRepository.findById(employeebdetails.getBirthcertificateid()).get().getDescription());

			empbdtls.setDob(simpleDateFormat.format(employeebdetails.getDob()));
			empbdtls.setEmployeeid(employeebdetails.getEmployeeid());

			HrmsEmployee hrmsEmployee = hrmsEmployeeRepository.findById(employeebdetails.getEmployeeid()).get();

			StringBuilder fullname = new StringBuilder();
			fullname.append(hrmsEmployee.getFirstName().trim());
			fullname.append("  " + hrmsEmployee.getMiddleName().trim());
			fullname.append(" " + hrmsEmployee.getLastName().trim());

			empbdtls.setEmployeename(fullname.toString());
			empbdtls.setApprovalComment(employeebdetails.getApprovalComment());

			empbdtls.setId(employeebdetails.getId());
			empbdtls.setUri(hrmsAttachmentRepository.findById(employeebdetails.getBirthcertificateid()).get().getUri());

			empbdtls.setCityid(employeebdetails.getCityid());
			if (employeebdetails.getCityid() != 0) {
				empbdtls.setCityname(hrmsLocationCityRepository.findById(employeebdetails.getCityid()).get().getName());
			}
			empbdtls.setDistrictid(employeebdetails.getDistrictid());
			if (employeebdetails.getDistrictid() != 0) {
				empbdtls.setDistrictname(hrmsLocationDistrictRepository.findById(1).get().getName());
			}
			empbdtls.setWardid(employeebdetails.getWardid());
			if (employeebdetails.getWardid() != 0) {
				empbdtls.setWardanme(hrmsLocationWardRepository.findById(employeebdetails.getWardid()).get().getName());
			}

			employeebdetailslist.add(empbdtls);
		});
		return ResponseEntity.status(HttpStatus.OK).body(employeebdetailslist);
	}

	@Override
	public ResponseEntity<List<EmployeeBirthDetailsResponse>> listEmployeeBirthDetailsNonApproved() {
		List<EmployeeBirthDetailsResponse> employeebdetailslist = new ArrayList<>();

		hrmsEmployeeBirthDetailsRepository.findByActiveAndApprovedOrderByEmployeeid(1, 0).forEach(employeebdetails -> {

			EmployeeBirthDetailsResponse empbdtls = new EmployeeBirthDetailsResponse();

			String pattern = "yyyy-MM-dd";
			SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);

			empbdtls.setActive(employeebdetails.getActive());
			empbdtls.setAdditionalinformation(employeebdetails.getAdditionalinformation());
			empbdtls.setApproved(employeebdetails.getApproved());
			empbdtls.setCountryid(employeebdetails.getCountryid());
			empbdtls.setCountryname(
					hrmsLocationCountryRepository.findById(employeebdetails.getCountryid()).get().getName());

			empbdtls.setAttachmentid(employeebdetails.getBirthcertificateid());
			empbdtls.setAttachmentname(
					hrmsAttachmentRepository.findById(employeebdetails.getBirthcertificateid()).get().getName());
			empbdtls.setAttachmenttypename(hrmsAttachmentTypeRepository.findById(hrmsAttachmentRepository
					.findById(employeebdetails.getBirthcertificateid()).get().getAttachmenttypeid()).get().getName());
			empbdtls.setAttachmenttypeid(hrmsAttachmentRepository.findById(employeebdetails.getBirthcertificateid())
					.get().getAttachmenttypeid());

			empbdtls.setDescription(
					hrmsAttachmentRepository.findById(employeebdetails.getBirthcertificateid()).get().getDescription());

			empbdtls.setDob(simpleDateFormat.format(employeebdetails.getDob()));
			empbdtls.setEmployeeid(employeebdetails.getEmployeeid());

			HrmsEmployee hrmsEmployee = hrmsEmployeeRepository.findById(employeebdetails.getEmployeeid()).get();

			StringBuilder fullname = new StringBuilder();
			fullname.append(hrmsEmployee.getFirstName().trim());
			fullname.append("  " + hrmsEmployee.getMiddleName().trim());
			fullname.append(" " + hrmsEmployee.getLastName().trim());

			empbdtls.setEmployeename(fullname.toString());
			empbdtls.setApprovalComment(employeebdetails.getApprovalComment());

			empbdtls.setId(employeebdetails.getId());
			empbdtls.setUri(hrmsAttachmentRepository.findById(employeebdetails.getBirthcertificateid()).get().getUri());

			empbdtls.setCityid(employeebdetails.getCityid());
			if (employeebdetails.getCityid() != 0) {
				empbdtls.setCityname(hrmsLocationCityRepository.findById(employeebdetails.getCityid()).get().getName());
			}
			empbdtls.setDistrictid(employeebdetails.getDistrictid());
			if (employeebdetails.getDistrictid() != 0) {
				empbdtls.setDistrictname(hrmsLocationDistrictRepository.findById(1).get().getName());
			}
			empbdtls.setWardid(employeebdetails.getWardid());
			if (employeebdetails.getWardid() != 0) {
				empbdtls.setWardanme(hrmsLocationWardRepository.findById(employeebdetails.getWardid()).get().getName());
			}

			employeebdetailslist.add(empbdtls);
		});
		return ResponseEntity.status(HttpStatus.OK).body(employeebdetailslist);
	}

	@Override
	public ResponseEntity<?> approveOrRejectEmployeeBirthDetails(EmployeeApprovalComment employeeApprovalComment,
			int id, int status) {
		if (hrmsEmployeeBirthDetailsRepository.existsByIdAndActive(id, 1) && (status == 1 || status == -1)) {

			// approve employee birth details below
			HrmsEmployeeBirthDetails hrmsEmployeeBirthDetails = hrmsEmployeeBirthDetailsRepository.findById(id).get();
			hrmsEmployeeBirthDetails.setApproved(status);
			hrmsEmployeeBirthDetails.setDate_updated(LocalDateTime.now());
			hrmsEmployeeBirthDetails.setApprovalComment(employeeApprovalComment.getComment());
			hrmsEmployeeBirthDetails.setApproverEmployeeid(employeeApprovalComment.getApproverEmployeeid());

			hrmsEmployeeBirthDetailsRepository.saveAndFlush(hrmsEmployeeBirthDetails);

			// approve attachment now
			HrmsAttachment hrmsAttachment = hrmsAttachmentRepository
					.findById(hrmsEmployeeBirthDetails.getBirthcertificateid()).get();
			hrmsAttachment.setApproved(status);
			hrmsAttachment.setDate_updated(LocalDateTime.now());
			hrmsAttachmentRepository.saveAndFlush(hrmsAttachment);

			// send notification
			String messages = "";

			HrmsEmployee hrmsEmployee = hrmsEmployeeRepository.findById(hrmsEmployeeBirthDetails.getEmployeeid()).get();

			StringBuilder fullname = new StringBuilder();
			fullname.append(hrmsEmployee.getFirstName().trim());
			fullname.append("  " + hrmsEmployee.getMiddleName().trim());
			fullname.append(" " + hrmsEmployee.getLastName().trim());
			String username = fullname.toString();
			String usermail = hrmsEmployee.getEmail();
			if (status == -1) {
				messages = " Your Birth details entry   " + " has been rejected,Kindly edit and re-submit again "
						+ "  Comments for rejection is  " + employeeApprovalComment.getComment();
				sendEmail.sendmailNotification(username, usermail, messages);
			}

			return ResponseEntity.status(HttpStatus.OK).body(id);
		} else {
			return ResponseEntity.status(HttpStatus.PRECONDITION_FAILED).body(id);
		}
	}

}
