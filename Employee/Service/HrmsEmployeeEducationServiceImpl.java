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
import com.Hrms.Employee.DTO.EmployeeEducationResponseV2;
import com.Hrms.Employee.DTO.HrmsEmployeeEducationRequest;
import com.Hrms.Employee.Entity.HrmsAttachment;
import com.Hrms.Employee.Entity.HrmsEmployee;
import com.Hrms.Employee.Entity.HrmsEmployeeEducation;
import com.Hrms.Employee.Entity.HrmsEmployeeEducationAttachment;
import com.Hrms.Employee.Repository.HrmsAttachmentRepository;
import com.Hrms.Employee.Repository.HrmsAttachmentTypeRepository;
import com.Hrms.Employee.Repository.HrmsEducationcourseRepository;
import com.Hrms.Employee.Repository.HrmsEducationinstitutionRepository;
import com.Hrms.Employee.Repository.HrmsEducationlevelRepository;
import com.Hrms.Employee.Repository.HrmsEmployeeEducationAttachmentRepository;
import com.Hrms.Employee.Repository.HrmsEmployeeEducationRepository;
import com.Hrms.Employee.Repository.HrmsEmployeeRepository;
import com.Hrms.Employee.Repository.HrmsLocationCountryRepository;

@Service
public class HrmsEmployeeEducationServiceImpl implements HrmsEmployeeEducationService {
	@Autowired
	private HrmsEmployeeEducationRepository hrmsEmployeeEducationRepository;

	@Autowired
	private HrmsEmployeeRepository hrmsEmployeeRepository;

	@Autowired
	private HrmsAttachmentRepository hrmsAttachmentRepository;

	@Autowired
	private HrmsEmployeeEducationAttachmentRepository hrmsEmployeeEducationAttachmentRepository;

	@Autowired
	private HrmsEducationinstitutionRepository hrmsEducationinstitutionRepository;

	@Autowired
	private HrmsEducationcourseRepository hrmsEducationcourseRepository;

	@Autowired
	private HrmsEducationlevelRepository hrmsEducationlevelRepository;

	@Autowired
	private HrmsLocationCountryRepository hrmsLocationCountryRepository;

	@Autowired
	private HrmsAttachmentTypeRepository hrmsAttachmentTypeRepository;

	@Autowired
	private SendEmail sendEmail;

	@Override
	public ResponseEntity<?> addEmployeeEducation(HrmsEmployeeEducationRequest hrmsEmployeeEducationRequest) {
		if (!hrmsEmployeeEducationRepository
				.existsByEmployeeidAndLevelidAndCourseidAndCountryidAndInstitutionidAndDatestartAndDatend(
						hrmsEmployeeEducationRequest.getEmployeeid(), hrmsEmployeeEducationRequest.getLevelid(),
						hrmsEmployeeEducationRequest.getCourseid(), hrmsEmployeeEducationRequest.getCountryid(),
						hrmsEmployeeEducationRequest.getInstitutionid(), hrmsEmployeeEducationRequest.getDatestart(),
						hrmsEmployeeEducationRequest.getDatend())) {

			HrmsAttachment attachment = new HrmsAttachment();

			HrmsEmployeeEducation eduback = new HrmsEmployeeEducation();

			HrmsEmployeeEducationAttachment empedattachment = new HrmsEmployeeEducationAttachment();

			attachment.setUnique_id(UUID.randomUUID());
			eduback.setUnique_id(UUID.randomUUID());
			empedattachment.setUnique_id(UUID.randomUUID());

			// attachment settings
			attachment.setActive(1);
			attachment.setApproved(0);
			attachment.setAttachmenttypeid(hrmsEmployeeEducationRequest.getAttachmenttypeid());
			attachment.setDescription(hrmsEmployeeEducationRequest.getAttachmentdescription());
			attachment.setName(hrmsEmployeeEducationRequest.getAttachmentname());
			attachment.setUri(hrmsEmployeeEducationRequest.getUri());

			// insert attchment now
			int ia = hrmsAttachmentRepository.save(attachment).getId();

			// employee education setting

			eduback.setActive(1);
			eduback.setApproved(0);
			eduback.setCountryid(hrmsEmployeeEducationRequest.getCountryid());
			eduback.setCourseid(hrmsEmployeeEducationRequest.getCourseid());
			eduback.setDatend(hrmsEmployeeEducationRequest.getDatend());
			eduback.setDatestart(hrmsEmployeeEducationRequest.getDatestart());
			eduback.setDescriptionend(hrmsEmployeeEducationRequest.getDescriptionend());
			eduback.setDescriptionstart(hrmsEmployeeEducationRequest.getDescriptionstart());
			eduback.setEmployeeid(hrmsEmployeeEducationRequest.getEmployeeid());
			eduback.setInstitutionid(hrmsEmployeeEducationRequest.getInstitutionid());
			eduback.setLevelid(hrmsEmployeeEducationRequest.getLevelid());

			// insert employee education now

			int ea = hrmsEmployeeEducationRepository.save(eduback).getId();

			// employee edu attchment

			empedattachment.setActive(1);
			empedattachment.setApproved(0);
			empedattachment.setAttachment_id(ia);
			empedattachment.setEducationid(ea);
			empedattachment.setEmployeeid(hrmsEmployeeEducationRequest.getEmployeeid());

			// insert employee education attachment table now
			hrmsEmployeeEducationAttachmentRepository.save(empedattachment);

			return ResponseEntity.status(HttpStatus.OK).body(hrmsEmployeeEducationRequest);

		} else {

			return ResponseEntity.status(HttpStatus.ALREADY_REPORTED).body(hrmsEmployeeEducationRequest);
		}
	}

	@Override
	public ResponseEntity<EmployeeEducationResponseV2> getEmployeeEducationById(int id) {
		if (hrmsEmployeeEducationRepository.existsByIdAndActive(id, 1)) {

			HrmsEmployeeEducation dbemp = hrmsEmployeeEducationRepository.findByIdAndActive(id, 1);

			EmployeeEducationResponseV2 emprespo = new EmployeeEducationResponseV2();

			emprespo.setId(dbemp.getId());
			emprespo.setEndYear(dbemp.getDatend());
			emprespo.setStartYear(dbemp.getDatestart());
			emprespo.setInstituteName(
					hrmsEducationinstitutionRepository.findById(dbemp.getInstitutionid()).get().getName());
			emprespo.setInstituteId(dbemp.getInstitutionid());
			emprespo.setLevelId(dbemp.getLevelid());
			emprespo.setLevelName(hrmsEducationlevelRepository.findById(dbemp.getLevelid()).get().getName());
			emprespo.setCourseId(dbemp.getCourseid());
			emprespo.setCourseName(hrmsEducationcourseRepository.findById(dbemp.getCourseid()).get().getName());
			emprespo.setCountryId(dbemp.getCountryid());
			emprespo.setCountryname(hrmsLocationCountryRepository.findById(dbemp.getCountryid()).get().getName());
			emprespo.setAttachmentId(hrmsEmployeeEducationAttachmentRepository
					.findByEmployeeidAndEducationid(dbemp.getEmployeeid(), id).get().getAttachment_id());

			emprespo.setAttachmentName(hrmsAttachmentRepository
					.findById(hrmsEmployeeEducationAttachmentRepository
							.findByEmployeeidAndEducationid(dbemp.getEmployeeid(), id).get().getAttachment_id())
					.get().getName());

			emprespo.setAttachmentUri(hrmsAttachmentRepository
					.findById(hrmsEmployeeEducationAttachmentRepository
							.findByEmployeeidAndEducationid(dbemp.getEmployeeid(), id).get().getAttachment_id())
					.get().getUri());

			emprespo.setAttachmentName(hrmsAttachmentRepository
					.findById(hrmsEmployeeEducationAttachmentRepository
							.findByEmployeeidAndEducationid(dbemp.getEmployeeid(), id).get().getAttachment_id())
					.get().getName());

			emprespo.setAttachmentTypeId(hrmsAttachmentRepository
					.findById(hrmsEmployeeEducationAttachmentRepository
							.findByEmployeeidAndEducationid(dbemp.getEmployeeid(), id).get().getAttachment_id())
					.get().getAttachmenttypeid());

			emprespo.setAttachmentTypeName(hrmsAttachmentTypeRepository.findById(hrmsAttachmentRepository
					.findById(hrmsEmployeeEducationAttachmentRepository
							.findByEmployeeidAndEducationid(dbemp.getEmployeeid(), id).get().getAttachment_id())
					.get().getAttachmenttypeid()).get().getName());
			emprespo.setApproved(dbemp.getApproved());

			emprespo.setApprovalComment(dbemp.getApprovalComment());

			return ResponseEntity.status(HttpStatus.OK).body(emprespo);

		} else {

			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
		}
	}

	@Override
	public ResponseEntity<HrmsEmployeeEducationRequest> updateEmployeeEducation(
			HrmsEmployeeEducationRequest hrmsEmployeeEducationRequest, int id) {

		if (hrmsEmployeeEducationRepository.existsById(id)) {

			HrmsAttachment attachment = hrmsAttachmentRepository.findById(hrmsEmployeeEducationAttachmentRepository
					.findByEmployeeidAndEducationid(hrmsEmployeeEducationRequest.getEmployeeid(), id).get()
					.getAttachment_id()).get();

			HrmsEmployeeEducation eduback = hrmsEmployeeEducationRepository.findById(id).get();

			HrmsEmployeeEducationAttachment empedattachment = hrmsEmployeeEducationAttachmentRepository
					.findByEmployeeidAndEducationid(hrmsEmployeeEducationRequest.getEmployeeid(), id).get();

			// attachment settings
			if (hrmsEmployeeEducationRequest.getUri() == null) {
				attachment.setDate_updated(LocalDateTime.now());
				attachment.setApproved(0);

			} else {
				attachment.setDate_updated(LocalDateTime.now());
				attachment.setAttachmenttypeid(hrmsEmployeeEducationRequest.getAttachmenttypeid());
				attachment.setDescription(hrmsEmployeeEducationRequest.getAttachmentdescription());
				attachment.setName(hrmsEmployeeEducationRequest.getAttachmentname());
				attachment.setUri(hrmsEmployeeEducationRequest.getUri());
				attachment.setApproved(0);
			}
			// insert attchment now
			// int ia =
			hrmsAttachmentRepository.save(attachment).getId();

			// employee education setting

			eduback.setDate_updated(LocalDateTime.now());
			eduback.setCountryid(hrmsEmployeeEducationRequest.getCountryid());
			eduback.setCourseid(hrmsEmployeeEducationRequest.getCourseid());
			eduback.setDatend(hrmsEmployeeEducationRequest.getDatend());
			eduback.setDatestart(hrmsEmployeeEducationRequest.getDatestart());
			eduback.setDescriptionend(hrmsEmployeeEducationRequest.getDescriptionend());
			eduback.setDescriptionstart(hrmsEmployeeEducationRequest.getDescriptionstart());
			eduback.setEmployeeid(hrmsEmployeeEducationRequest.getEmployeeid());
			eduback.setInstitutionid(hrmsEmployeeEducationRequest.getInstitutionid());
			eduback.setLevelid(hrmsEmployeeEducationRequest.getLevelid());
			eduback.setApproved(0);

			// insert employee education now

			// int ea =
			hrmsEmployeeEducationRepository.save(eduback).getId();

			// employee edu attchment

			empedattachment.setDate_updated(LocalDateTime.now());
			empedattachment.setEmployeeid(hrmsEmployeeEducationRequest.getEmployeeid());
			empedattachment.setApproved(0);

			// insert employee education attachment table now
			hrmsEmployeeEducationAttachmentRepository.save(empedattachment);

			return ResponseEntity.status(HttpStatus.OK).body(hrmsEmployeeEducationRequest);

		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(hrmsEmployeeEducationRequest);
		}
	}

	@Override
	public ResponseEntity<?> deleteEmployeeEducation(int id) {
		// hrmsEmployeeEducationAttachmentRepository
		// hrmsAttachmentRepository
		if (hrmsEmployeeEducationRepository.existsById(id)) {
			HrmsEmployeeEducation hrmsEmployeeEducation = hrmsEmployeeEducationRepository.findById(id).get();
			hrmsEmployeeEducation.setActive(0);
			hrmsEmployeeEducation.setDate_updated(LocalDateTime.now());

			int empedatid = hrmsEmployeeEducationRepository.save(hrmsEmployeeEducation).getId();
			HrmsEmployeeEducationAttachment hrmsEmployeeEducationAttachment = hrmsEmployeeEducationAttachmentRepository
					.findByEducationid(empedatid);
			hrmsEmployeeEducationAttachment.setActive(0);
			hrmsEmployeeEducationAttachment.setDate_updated(LocalDateTime.now());
			int attachemntid = hrmsEmployeeEducationAttachmentRepository.save(hrmsEmployeeEducationAttachment)
					.getAttachment_id();

			HrmsAttachment hrmsAttachment = hrmsAttachmentRepository.findById(attachemntid).get();
			hrmsAttachment.setActive(0);
			hrmsAttachment.setDate_updated(LocalDateTime.now());

			hrmsAttachmentRepository.save(hrmsAttachment);

			return ResponseEntity.status(HttpStatus.OK).body(null);
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
		}
	}

	@Override
	public ResponseEntity<List<EmployeeEducationResponseV2>> listEmployeeEducation() {

		List<EmployeeEducationResponseV2> employeeeducationlist = new ArrayList<>();
		List<HrmsEmployeeEducation> hrmsEmployeeEducation = hrmsEmployeeEducationRepository
				.findByActiveOrderByEmployeeid(1);

		hrmsEmployeeEducation.forEach(employeeeducation -> {

			EmployeeEducationResponseV2 emprespo = new EmployeeEducationResponseV2();

			emprespo.setId(employeeeducation.getId());
			emprespo.setEndYear(employeeeducation.getDatend());
			emprespo.setStartYear(employeeeducation.getDatestart());
			emprespo.setInstituteName(
					hrmsEducationinstitutionRepository.findById(employeeeducation.getInstitutionid()).get().getName());
			emprespo.setInstituteId(employeeeducation.getInstitutionid());
			emprespo.setLevelId(employeeeducation.getLevelid());
			emprespo.setLevelName(
					hrmsEducationlevelRepository.findById(employeeeducation.getLevelid()).get().getName());
			emprespo.setCourseId(employeeeducation.getCourseid());
			emprespo.setCourseName(
					hrmsEducationcourseRepository.findById(employeeeducation.getCourseid()).get().getName());
			emprespo.setCountryId(employeeeducation.getCountryid());
			emprespo.setCountryname(
					hrmsLocationCountryRepository.findById(employeeeducation.getCountryid()).get().getName());
			emprespo.setAttachmentId(hrmsEmployeeEducationAttachmentRepository
					.findByEmployeeidAndEducationid(employeeeducation.getEmployeeid(), employeeeducation.getId()).get()
					.getAttachment_id());
			emprespo.setAttachmentName(hrmsAttachmentRepository.findById(hrmsEmployeeEducationAttachmentRepository
					.findByEmployeeidAndEducationid(employeeeducation.getEmployeeid(), employeeeducation.getId()).get()
					.getAttachment_id()).get().getName());

			emprespo.setAttachmentUri(hrmsAttachmentRepository.findById(hrmsEmployeeEducationAttachmentRepository
					.findByEmployeeidAndEducationid(employeeeducation.getEmployeeid(), employeeeducation.getId()).get()
					.getAttachment_id()).get().getUri());

			emprespo.setAttachmentName(hrmsAttachmentRepository.findById(hrmsEmployeeEducationAttachmentRepository
					.findByEmployeeidAndEducationid(employeeeducation.getEmployeeid(), employeeeducation.getId()).get()
					.getAttachment_id()).get().getName());

			emprespo.setAttachmentTypeId(hrmsAttachmentRepository.findById(hrmsEmployeeEducationAttachmentRepository
					.findByEmployeeidAndEducationid(employeeeducation.getEmployeeid(), employeeeducation.getId()).get()
					.getAttachment_id()).get().getAttachmenttypeid());

			emprespo.setAttachmentTypeName(
					hrmsAttachmentTypeRepository.findById(hrmsAttachmentRepository
							.findById(hrmsEmployeeEducationAttachmentRepository
									.findByEmployeeidAndEducationid(employeeeducation.getEmployeeid(),
											employeeeducation.getId())
									.get().getAttachment_id())
							.get().getAttachmenttypeid()).get().getName());
			emprespo.setApproved(employeeeducation.getApproved());

			emprespo.setEmployeeid(employeeeducation.getEmployeeid());
			emprespo.setApprovalComment(employeeeducation.getApprovalComment());

			HrmsEmployee hrmsEmployee = hrmsEmployeeRepository.findById(employeeeducation.getEmployeeid()).get();

			StringBuilder fullname = new StringBuilder();
			fullname.append(hrmsEmployee.getFirstName().trim());
			fullname.append("  " + hrmsEmployee.getMiddleName().trim());
			fullname.append(" " + hrmsEmployee.getLastName().trim());

			emprespo.setEmployeename(fullname.toString());

			employeeeducationlist.add(emprespo);

		});

		return ResponseEntity.status(HttpStatus.OK).body(employeeeducationlist);
	}

	@Override
	public ResponseEntity<List<EmployeeEducationResponseV2>> getEmployeeEducationByempId(int id) {
		if (hrmsEmployeeEducationRepository.existsByEmployeeid(id)) {

			List<EmployeeEducationResponseV2> usrlist = new ArrayList<>();

			List<HrmsEmployeeEducation> userlist = hrmsEmployeeEducationRepository.findByEmployeeidAndActive(id, 1);

			userlist.forEach(dbemp -> {

				EmployeeEducationResponseV2 emprespo = new EmployeeEducationResponseV2();

				emprespo.setId(dbemp.getId());
				emprespo.setEndYear(dbemp.getDatend());
				emprespo.setStartYear(dbemp.getDatestart());
				emprespo.setInstituteName(
						hrmsEducationinstitutionRepository.findById(dbemp.getInstitutionid()).get().getName());
				emprespo.setInstituteId(dbemp.getInstitutionid());
				emprespo.setLevelId(dbemp.getLevelid());
				emprespo.setLevelName(hrmsEducationlevelRepository.findById(dbemp.getLevelid()).get().getName());
				emprespo.setCourseId(dbemp.getCourseid());
				emprespo.setCourseName(hrmsEducationcourseRepository.findById(dbemp.getCourseid()).get().getName());
				emprespo.setCountryId(dbemp.getCountryid());
				emprespo.setCountryname(hrmsLocationCountryRepository.findById(dbemp.getCountryid()).get().getName());
				emprespo.setAttachmentId(hrmsEmployeeEducationAttachmentRepository
						.findByEmployeeidAndEducationid(id, dbemp.getId()).get().getAttachment_id());
				emprespo.setAttachmentName(hrmsAttachmentRepository
						.findById(hrmsEmployeeEducationAttachmentRepository
								.findByEmployeeidAndEducationid(id, dbemp.getId()).get().getAttachment_id())
						.get().getName());

				emprespo.setAttachmentUri(hrmsAttachmentRepository
						.findById(hrmsEmployeeEducationAttachmentRepository
								.findByEmployeeidAndEducationid(id, dbemp.getId()).get().getAttachment_id())
						.get().getUri());

				emprespo.setAttachmentName(hrmsAttachmentRepository
						.findById(hrmsEmployeeEducationAttachmentRepository
								.findByEmployeeidAndEducationid(id, dbemp.getId()).get().getAttachment_id())
						.get().getName());

				emprespo.setAttachmentTypeId(hrmsAttachmentRepository
						.findById(hrmsEmployeeEducationAttachmentRepository
								.findByEmployeeidAndEducationid(id, dbemp.getId()).get().getAttachment_id())
						.get().getAttachmenttypeid());

				emprespo.setAttachmentTypeName(hrmsAttachmentTypeRepository.findById(hrmsAttachmentRepository
						.findById(hrmsEmployeeEducationAttachmentRepository
								.findByEmployeeidAndEducationid(id, dbemp.getId()).get().getAttachment_id())
						.get().getAttachmenttypeid()).get().getName());
				emprespo.setApproved(dbemp.getApproved());
				emprespo.setApprovalComment(dbemp.getApprovalComment());

				usrlist.add(emprespo);

			});

			return ResponseEntity.status(HttpStatus.OK).body(usrlist);

		} else {

			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
		}
	}

	@Override
	public ResponseEntity<List<EmployeeEducationResponseV2>> listEmployeeEducationNonApproved() {
		List<EmployeeEducationResponseV2> employeeeducationlist = new ArrayList<>();
		List<HrmsEmployeeEducation> hrmsEmployeeEducation = hrmsEmployeeEducationRepository
				.findByActiveAndApprovedOrderByEmployeeid(1, 0);

		hrmsEmployeeEducation.forEach(employeeeducation -> {

			EmployeeEducationResponseV2 emprespo = new EmployeeEducationResponseV2();

			emprespo.setId(employeeeducation.getId());
			emprespo.setEndYear(employeeeducation.getDatend());
			emprespo.setStartYear(employeeeducation.getDatestart());
			emprespo.setInstituteName(
					hrmsEducationinstitutionRepository.findById(employeeeducation.getInstitutionid()).get().getName());
			emprespo.setInstituteId(employeeeducation.getInstitutionid());
			emprespo.setLevelId(employeeeducation.getLevelid());
			emprespo.setLevelName(
					hrmsEducationlevelRepository.findById(employeeeducation.getLevelid()).get().getName());
			emprespo.setCourseId(employeeeducation.getCourseid());
			emprespo.setCourseName(
					hrmsEducationcourseRepository.findById(employeeeducation.getCourseid()).get().getName());
			emprespo.setCountryId(employeeeducation.getCountryid());
			emprespo.setCountryname(
					hrmsLocationCountryRepository.findById(employeeeducation.getCountryid()).get().getName());
			emprespo.setAttachmentId(hrmsEmployeeEducationAttachmentRepository
					.findByEmployeeidAndEducationid(employeeeducation.getEmployeeid(), employeeeducation.getId()).get()
					.getAttachment_id());
			emprespo.setAttachmentName(hrmsAttachmentRepository.findById(hrmsEmployeeEducationAttachmentRepository
					.findByEmployeeidAndEducationid(employeeeducation.getEmployeeid(), employeeeducation.getId()).get()
					.getAttachment_id()).get().getName());

			emprespo.setAttachmentUri(hrmsAttachmentRepository.findById(hrmsEmployeeEducationAttachmentRepository
					.findByEmployeeidAndEducationid(employeeeducation.getEmployeeid(), employeeeducation.getId()).get()
					.getAttachment_id()).get().getUri());

			emprespo.setAttachmentName(hrmsAttachmentRepository.findById(hrmsEmployeeEducationAttachmentRepository
					.findByEmployeeidAndEducationid(employeeeducation.getEmployeeid(), employeeeducation.getId()).get()
					.getAttachment_id()).get().getName());

			emprespo.setAttachmentTypeId(hrmsAttachmentRepository.findById(hrmsEmployeeEducationAttachmentRepository
					.findByEmployeeidAndEducationid(employeeeducation.getEmployeeid(), employeeeducation.getId()).get()
					.getAttachment_id()).get().getAttachmenttypeid());

			emprespo.setAttachmentTypeName(
					hrmsAttachmentTypeRepository.findById(hrmsAttachmentRepository
							.findById(hrmsEmployeeEducationAttachmentRepository
									.findByEmployeeidAndEducationid(employeeeducation.getEmployeeid(),
											employeeeducation.getId())
									.get().getAttachment_id())
							.get().getAttachmenttypeid()).get().getName());
			emprespo.setApproved(employeeeducation.getApproved());

			emprespo.setEmployeeid(employeeeducation.getEmployeeid());

			HrmsEmployee hrmsEmployee = hrmsEmployeeRepository.findById(employeeeducation.getEmployeeid()).get();

			StringBuilder fullname = new StringBuilder();
			fullname.append(hrmsEmployee.getFirstName().trim());
			fullname.append("  " + hrmsEmployee.getMiddleName().trim());
			fullname.append(" " + hrmsEmployee.getLastName().trim());

			emprespo.setEmployeename(fullname.toString());
			emprespo.setApprovalComment(employeeeducation.getApprovalComment());

			employeeeducationlist.add(emprespo);

		});

		return ResponseEntity.status(HttpStatus.OK).body(employeeeducationlist);
	}

	@Override
	public ResponseEntity<?> approveOrejectEmployeeEducation(EmployeeApprovalComment employeeApprovalComment, int id,
			int status) {
		if (hrmsEmployeeEducationRepository.existsByIdAndApproved(id, 0) && (status == 1 || status == -1)) {

			// update employee eductaion to approved
			HrmsEmployeeEducation hrmsEmployeeEducation = hrmsEmployeeEducationRepository.findById(id).get();
			hrmsEmployeeEducation.setApproved(status);
			hrmsEmployeeEducation.setDate_updated(LocalDateTime.now());
			hrmsEmployeeEducation.setApproverEmployeeid(employeeApprovalComment.getApproverEmployeeid());
			hrmsEmployeeEducation.setApprovalComment(employeeApprovalComment.getComment());
			hrmsEmployeeEducationRepository.saveAndFlush(hrmsEmployeeEducation);

			// approve employee education attachment now

			HrmsEmployeeEducationAttachment hrmsEmployeeEducationAttachment = hrmsEmployeeEducationAttachmentRepository
					.findByEducationid(id);
			hrmsEmployeeEducationAttachment.setApproved(status);
			hrmsEmployeeEducationAttachment.setDate_updated(LocalDateTime.now());

			hrmsEmployeeEducationAttachmentRepository.saveAndFlush(hrmsEmployeeEducationAttachment);

			// approve attachment now
			HrmsAttachment hrmsAttachment = hrmsAttachmentRepository
					.findById(hrmsEmployeeEducationAttachment.getAttachment_id()).get();

			hrmsAttachment.setApproved(status);
			hrmsAttachment.setDate_updated(LocalDateTime.now());
			hrmsAttachmentRepository.saveAndFlush(hrmsAttachment);
			// send notification
			String messages = "";

			HrmsEmployee hrmsEmployee = hrmsEmployeeRepository.findById(hrmsEmployeeEducation.getEmployeeid()).get();

			StringBuilder fullname = new StringBuilder();
			fullname.append(hrmsEmployee.getFirstName().trim());
			fullname.append("  " + hrmsEmployee.getMiddleName().trim());
			fullname.append(" " + hrmsEmployee.getLastName().trim());
			String username = fullname.toString();
			String usermail = hrmsEmployee.getEmail();
			if (status == 1) {
				/*
				 * messages = " Your Education entry for " +
				 * hrmsEducationcourseRepository.findById(hrmsEmployeeEducation.getCourseid()).
				 * get().getName() + " has been approved ";
				 * sendEmail.sendmailNotification(username, usermail, messages);
				 */

			} else {
				messages = " Your Education entry for "
						+ hrmsEducationcourseRepository.findById(hrmsEmployeeEducation.getCourseid()).get().getName()
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
