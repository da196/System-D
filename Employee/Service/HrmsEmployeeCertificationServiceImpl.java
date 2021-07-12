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
import com.Hrms.Employee.DTO.HrmsEmployeeCertificationRequest;
import com.Hrms.Employee.DTO.HrmsEmployeeCertificationResponse;
import com.Hrms.Employee.Entity.HrmsAttachment;
import com.Hrms.Employee.Entity.HrmsEmployee;
import com.Hrms.Employee.Entity.HrmsEmployeeCertification;
import com.Hrms.Employee.Entity.HrmsEmployeeCertificationAttachment;
import com.Hrms.Employee.Repository.HrmsAttachmentRepository;
import com.Hrms.Employee.Repository.HrmsAttachmentTypeRepository;
import com.Hrms.Employee.Repository.HrmsCertificationCategoryRepository;
import com.Hrms.Employee.Repository.HrmsEmployeeCertificationAttachmentRepository;
import com.Hrms.Employee.Repository.HrmsEmployeeCertificationRepository;
import com.Hrms.Employee.Repository.HrmsEmployeeRepository;
import com.Hrms.Employee.Repository.HrmsLocationCountryRepository;

@Service
public class HrmsEmployeeCertificationServiceImpl implements HrmsEmployeeCertificationService {
	@Autowired
	private HrmsEmployeeCertificationRepository hrmsEmployeeCertificationRepository;

	@Autowired
	private HrmsAttachmentRepository hrmsAttachmentRepository;

	@Autowired
	private HrmsEmployeeRepository hrmsEmployeeRepository;

	@Autowired
	private HrmsLocationCountryRepository hrmsLocationCountryRepository;

	@Autowired
	private HrmsCertificationCategoryRepository hrmsCertificationCategoryRepository;

	@Autowired
	private HrmsAttachmentTypeRepository hrmsAttachmentTypeRepository;

	@Autowired
	private HrmsEmployeeCertificationAttachmentRepository hrmsEmployeeCertificationAttachmentRepository;

	@Autowired
	private SendEmail sendEmail;

	@Override
	public ResponseEntity<HrmsEmployeeCertificationRequest> addEmployeeCertification(
			HrmsEmployeeCertificationRequest hrmsEmployeeCertificationRequest) {
		if (hrmsEmployeeRepository.existsById(hrmsEmployeeCertificationRequest.getEmployeeid())
				&& hrmsLocationCountryRepository.existsById(hrmsEmployeeCertificationRequest.getCountryid())
				&& hrmsCertificationCategoryRepository
						.existsById(hrmsEmployeeCertificationRequest.getCertificationcategoryid())) {
			if (!hrmsEmployeeCertificationRepository
					.existsByDatestartAndDatendAndEmployeeidAndActiveAndCertificationcategoryid(
							hrmsEmployeeCertificationRequest.getDatestart(),
							hrmsEmployeeCertificationRequest.getDatend(),
							hrmsEmployeeCertificationRequest.getEmployeeid(), 1,
							hrmsEmployeeCertificationRequest.getCertificationcategoryid())) {
				// add attachment

				HrmsAttachment attachment = new HrmsAttachment();
				attachment.setUnique_id(UUID.randomUUID());
				attachment.setActive(1);
				attachment.setApproved(0);
				attachment.setDescription(hrmsEmployeeCertificationRequest.getDescription());
				attachment.setName(hrmsEmployeeCertificationRequest.getAttachmentname());
				attachment.setUri(hrmsEmployeeCertificationRequest.getUri());
				attachment.setAttachmenttypeid(hrmsEmployeeCertificationRequest.getAttachmenttypeid());

				int atid = hrmsAttachmentRepository.save(attachment).getId();

				// add employee certification

				HrmsEmployeeCertification empcert = new HrmsEmployeeCertification();
				empcert.setUnique_id(UUID.randomUUID());
				empcert.setActive(1);
				empcert.setApproved(0);
				empcert.setCertificationcategoryid(hrmsEmployeeCertificationRequest.getCertificationcategoryid());
				empcert.setCountryid(hrmsEmployeeCertificationRequest.getCountryid());
				empcert.setDatend(hrmsEmployeeCertificationRequest.getDatend());
				empcert.setDatestart(hrmsEmployeeCertificationRequest.getDatestart());
				empcert.setDescription(hrmsEmployeeCertificationRequest.getDescription());
				empcert.setEmployeeid(hrmsEmployeeCertificationRequest.getEmployeeid());
				empcert.setExpire(hrmsEmployeeCertificationRequest.getExpire());
				empcert.setInstitution(hrmsEmployeeCertificationRequest.getInstitution());

				int empcertid = hrmsEmployeeCertificationRepository.save(empcert).getId();

				HrmsEmployeeCertificationAttachment empcertatt = new HrmsEmployeeCertificationAttachment();
				empcertatt.setActive(1);
				empcertatt.setApproved(0);
				empcertatt.setUnique_id(UUID.randomUUID());
				empcertatt.setAttachmentid(atid);
				empcertatt.setCertificationid(empcertid);
				empcertatt.setDescription(hrmsEmployeeCertificationRequest.getDescription());
				empcertatt.setEmployeeid(hrmsEmployeeCertificationRequest.getEmployeeid());

				hrmsEmployeeCertificationAttachmentRepository.save(empcertatt);

				return ResponseEntity.status(HttpStatus.OK).body(hrmsEmployeeCertificationRequest);
			} else {

				return ResponseEntity.status(HttpStatus.ALREADY_REPORTED).body(hrmsEmployeeCertificationRequest);
			}
		} else {
			return ResponseEntity.status(HttpStatus.PRECONDITION_FAILED).body(hrmsEmployeeCertificationRequest);
		}

	}

	@Override
	public ResponseEntity<List<HrmsEmployeeCertificationResponse>> getEmployeeCertificationByEmpId(int empid) {
		if (hrmsEmployeeCertificationAttachmentRepository.existsByEmployeeidAndActive(empid, 1)) {

			List<HrmsEmployeeCertificationResponse> empcert = new ArrayList<>();

			List<HrmsEmployeeCertification> empcerlist = hrmsEmployeeCertificationRepository
					.findByEmployeeidAndActive(empid, 1);

			empcerlist.forEach(dbempcert -> {

				HrmsEmployeeCertificationResponse empcertrepo = new HrmsEmployeeCertificationResponse();
				empcertrepo.setActive(dbempcert.getActive());
				empcertrepo.setApproved(dbempcert.getApproved());
				int attachmentid = hrmsEmployeeCertificationAttachmentRepository
						.findByEmployeeidAndCertificationid(empid, dbempcert.getId()).getAttachmentid();

				empcertrepo.setAttachmentname(hrmsAttachmentRepository.findById(attachmentid).get().getName());

				empcertrepo.setAttachmentid(attachmentid);
				empcertrepo.setAttachmenttypeid(
						hrmsAttachmentRepository.findById(attachmentid).get().getAttachmenttypeid());

				empcertrepo.setAttachmenttypename(hrmsAttachmentTypeRepository
						.findById(hrmsAttachmentRepository.findById(attachmentid).get().getAttachmenttypeid()).get()
						.getName());

				empcertrepo.setCertificationcategoryid(dbempcert.getCertificationcategoryid());

				empcertrepo.setCertificationcategoryname(hrmsCertificationCategoryRepository
						.findById(dbempcert.getCertificationcategoryid()).get().getName());
				empcertrepo.setCountryid(dbempcert.getCountryid());
				empcertrepo.setCountryname(
						hrmsLocationCountryRepository.findById(dbempcert.getCountryid()).get().getName());
				empcertrepo.setDatend(dbempcert.getDatend());
				empcertrepo.setDatestart(dbempcert.getDatestart());
				empcertrepo.setDescription(dbempcert.getDescription());
				empcertrepo.setEmployeeid(dbempcert.getEmployeeid());
				empcertrepo.setExpire(dbempcert.getExpire());
				empcertrepo.setId(dbempcert.getId());
				empcertrepo.setInstitution(dbempcert.getInstitution());
				empcertrepo.setUri(hrmsAttachmentRepository.findById(attachmentid).get().getUri());
				empcertrepo.setApprovalComment(dbempcert.getApprovalComment());

				empcert.add(empcertrepo);

			});

			return ResponseEntity.status(HttpStatus.OK).body(empcert);

		} else {

			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
		}
	}

	@Override
	public ResponseEntity<HrmsEmployeeCertificationRequest> update(
			HrmsEmployeeCertificationRequest hrmsEmployeeCertificationRequest, int id) {

		if (hrmsEmployeeCertificationRepository.existsById(id)
				&& hrmsAttachmentRepository.existsById(hrmsEmployeeCertificationRequest.getAttachmentid())
				&& hrmsCertificationCategoryRepository
						.existsById(hrmsEmployeeCertificationRequest.getCertificationcategoryid())
				&& hrmsLocationCountryRepository.existsById(hrmsEmployeeCertificationRequest.getCountryid())) {

			// add employee certification

			HrmsEmployeeCertification empcert = hrmsEmployeeCertificationRepository.findByIdAndActive(id, 1);

			empcert.setCertificationcategoryid(hrmsEmployeeCertificationRequest.getCertificationcategoryid());
			empcert.setCountryid(hrmsEmployeeCertificationRequest.getCountryid());
			empcert.setDatend(hrmsEmployeeCertificationRequest.getDatend());
			empcert.setDatestart(hrmsEmployeeCertificationRequest.getDatestart());
			empcert.setDescription(hrmsEmployeeCertificationRequest.getDescription());
			empcert.setEmployeeid(hrmsEmployeeCertificationRequest.getEmployeeid());
			empcert.setExpire(hrmsEmployeeCertificationRequest.getExpire());
			empcert.setInstitution(hrmsEmployeeCertificationRequest.getInstitution());
			empcert.setApproved(0);

			int empcertid = hrmsEmployeeCertificationRepository.save(empcert).getId();

			HrmsEmployeeCertificationAttachment empcertatt = hrmsEmployeeCertificationAttachmentRepository
					.findByCertificationid(empcertid);
			empcertatt.setDescription(hrmsEmployeeCertificationRequest.getDescription());
			empcertatt.setApproved(0);

			int atid = hrmsEmployeeCertificationAttachmentRepository.save(empcertatt).getAttachmentid();

			// add attachment

			HrmsAttachment attachment = hrmsAttachmentRepository.findById(atid).get();
			if (hrmsEmployeeCertificationRequest.getUri() == null) {

				attachment.setDate_updated(LocalDateTime.now());
				attachment.setDescription(hrmsEmployeeCertificationRequest.getDescription());

			} else {
				attachment.setDate_updated(LocalDateTime.now());
				attachment.setActive(1);
				attachment.setApproved(0);
				attachment.setDescription(hrmsEmployeeCertificationRequest.getDescription());
				attachment.setName(hrmsEmployeeCertificationRequest.getAttachmentname());
				attachment.setUri(hrmsEmployeeCertificationRequest.getUri());
				attachment.setAttachmenttypeid(hrmsEmployeeCertificationRequest.getAttachmenttypeid());
			}

			hrmsAttachmentRepository.save(attachment);

			return ResponseEntity.status(HttpStatus.OK).body(hrmsEmployeeCertificationRequest);
		} else {
			return ResponseEntity.status(HttpStatus.PRECONDITION_FAILED).body(hrmsEmployeeCertificationRequest);
		}
	}

	@Override
	public ResponseEntity<?> deleteEmployeeCertification(int id) {
		if (hrmsEmployeeCertificationRepository.existsByIdAndActive(id, 1)) {
			// set emp certification

			HrmsEmployeeCertification hrmsEmployeeCertification = hrmsEmployeeCertificationRepository
					.findByIdAndActive(id, 1);
			hrmsEmployeeCertification.setActive(0);
			hrmsEmployeeCertification.setDate_updated(LocalDateTime.now());
			hrmsEmployeeCertificationRepository.save(hrmsEmployeeCertification);

			// set empcert attachment

			HrmsEmployeeCertificationAttachment hrmsEmployeeCertificationAttachment = hrmsEmployeeCertificationAttachmentRepository
					.findByCertificationid(id);

			hrmsEmployeeCertificationAttachment.setActive(0);
			hrmsEmployeeCertificationAttachment.setDate_updated(LocalDateTime.now());

			int attid = hrmsEmployeeCertificationAttachmentRepository.save(hrmsEmployeeCertificationAttachment)
					.getAttachmentid();

			// set attachment

			HrmsAttachment hrmsAttachment = hrmsAttachmentRepository.findById(attid).get();
			hrmsAttachment.setActive(0);
			hrmsAttachment.setDate_updated(LocalDateTime.now());

			hrmsAttachmentRepository.save(hrmsAttachment);
			return ResponseEntity.status(HttpStatus.OK).body(null);
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
		}
	}

	@Override
	public ResponseEntity<List<HrmsEmployeeCertificationResponse>> listEmployeeCertification() {

		List<HrmsEmployeeCertificationResponse> empcert = new ArrayList<>();

		// List<HrmsEmployeeCertification> empcerlist =
		// hrmsEmployeeCertificationRepository.findByActive(1);
		List<HrmsEmployeeCertification> empcerlist = hrmsEmployeeCertificationRepository
				.findByActiveOrderByEmployeeid(1);

		empcerlist.forEach(dbempcert -> {

			HrmsEmployeeCertificationResponse empcertrepo = new HrmsEmployeeCertificationResponse();
			empcertrepo.setActive(dbempcert.getActive());
			empcertrepo.setApproved(dbempcert.getApproved());
			int attachmentid = hrmsEmployeeCertificationAttachmentRepository.findByCertificationid(dbempcert.getId())
					.getAttachmentid();

			empcertrepo.setAttachmentname(hrmsAttachmentRepository.findById(attachmentid).get().getName());

			empcertrepo.setAttachmentid(attachmentid);
			empcertrepo
					.setAttachmenttypeid(hrmsAttachmentRepository.findById(attachmentid).get().getAttachmenttypeid());

			empcertrepo.setAttachmenttypename(hrmsAttachmentTypeRepository
					.findById(hrmsAttachmentRepository.findById(attachmentid).get().getAttachmenttypeid()).get()
					.getName());

			empcertrepo.setCertificationcategoryid(dbempcert.getCertificationcategoryid());

			empcertrepo.setCertificationcategoryname(hrmsCertificationCategoryRepository
					.findById(dbempcert.getCertificationcategoryid()).get().getName());
			empcertrepo.setCountryid(dbempcert.getCountryid());
			empcertrepo
					.setCountryname(hrmsLocationCountryRepository.findById(dbempcert.getCountryid()).get().getName());
			empcertrepo.setDatend(dbempcert.getDatend());
			empcertrepo.setDatestart(dbempcert.getDatestart());
			empcertrepo.setDescription(dbempcert.getDescription());
			empcertrepo.setEmployeeid(dbempcert.getEmployeeid());
			empcertrepo.setExpire(dbempcert.getExpire());
			empcertrepo.setId(dbempcert.getId());
			empcertrepo.setInstitution(dbempcert.getInstitution());
			empcertrepo.setUri(hrmsAttachmentRepository.findById(attachmentid).get().getUri());

			empcertrepo.setEmployeeid(dbempcert.getEmployeeid());

			HrmsEmployee hrmsEmployee = hrmsEmployeeRepository.findById(dbempcert.getEmployeeid()).get();

			StringBuilder fullname = new StringBuilder();
			fullname.append(hrmsEmployee.getFirstName().trim());
			fullname.append("  " + hrmsEmployee.getMiddleName().trim());
			fullname.append(" " + hrmsEmployee.getLastName().trim());

			empcertrepo.setEmployeename(fullname.toString());
			empcertrepo.setApprovalComment(dbempcert.getApprovalComment());

			empcert.add(empcertrepo);

		});

		return ResponseEntity.status(HttpStatus.OK).body(empcert);

	}

	@Override
	public ResponseEntity<HrmsEmployeeCertificationResponse> getEmployeeCertificationById(int id) {
		if (hrmsEmployeeCertificationAttachmentRepository.existsByIdAndActive(id, 1)) {

			HrmsEmployeeCertification dbempcert = hrmsEmployeeCertificationRepository.findByIdAndActive(id, 1);

			HrmsEmployeeCertificationResponse empcertrepo = new HrmsEmployeeCertificationResponse();
			empcertrepo.setActive(dbempcert.getActive());
			empcertrepo.setApproved(dbempcert.getApproved());
			int attachmentid = hrmsEmployeeCertificationAttachmentRepository
					.findByEmployeeidAndCertificationid(dbempcert.getEmployeeid(), id).getAttachmentid();

			empcertrepo.setAttachmentname(hrmsAttachmentRepository.findById(attachmentid).get().getName());

			empcertrepo.setAttachmentid(attachmentid);
			empcertrepo
					.setAttachmenttypeid(hrmsAttachmentRepository.findById(attachmentid).get().getAttachmenttypeid());

			empcertrepo.setAttachmenttypename(hrmsAttachmentTypeRepository
					.findById(hrmsAttachmentRepository.findById(attachmentid).get().getAttachmenttypeid()).get()
					.getName());

			empcertrepo.setCertificationcategoryid(dbempcert.getCertificationcategoryid());

			empcertrepo.setCertificationcategoryname(hrmsCertificationCategoryRepository
					.findById(dbempcert.getCertificationcategoryid()).get().getName());
			empcertrepo.setCountryid(dbempcert.getCountryid());
			empcertrepo
					.setCountryname(hrmsLocationCountryRepository.findById(dbempcert.getCountryid()).get().getName());
			empcertrepo.setDatend(dbempcert.getDatend());
			empcertrepo.setDatestart(dbempcert.getDatestart());
			empcertrepo.setDescription(dbempcert.getDescription());
			empcertrepo.setEmployeeid(dbempcert.getEmployeeid());
			empcertrepo.setExpire(dbempcert.getExpire());
			empcertrepo.setId(dbempcert.getId());
			empcertrepo.setInstitution(dbempcert.getInstitution());
			empcertrepo.setUri(hrmsAttachmentRepository.findById(attachmentid).get().getUri());
			empcertrepo.setApprovalComment(dbempcert.getApprovalComment());

			return ResponseEntity.status(HttpStatus.OK).body(empcertrepo);

		} else {

			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
		}
	}

	@Override
	public ResponseEntity<List<HrmsEmployeeCertificationResponse>> listEmployeeCertificationNonApproved() {
		List<HrmsEmployeeCertificationResponse> empcert = new ArrayList<>();

		// List<HrmsEmployeeCertification> empcerlist =
		// hrmsEmployeeCertificationRepository.findByActive(1);
		List<HrmsEmployeeCertification> empcerlist = hrmsEmployeeCertificationRepository
				.findByActiveAndApprovedOrderByEmployeeid(1, 0);

		empcerlist.forEach(dbempcert -> {

			HrmsEmployeeCertificationResponse empcertrepo = new HrmsEmployeeCertificationResponse();
			empcertrepo.setActive(dbempcert.getActive());
			empcertrepo.setApproved(dbempcert.getApproved());
			int attachmentid = hrmsEmployeeCertificationAttachmentRepository.findByCertificationid(dbempcert.getId())
					.getAttachmentid();

			empcertrepo.setAttachmentname(hrmsAttachmentRepository.findById(attachmentid).get().getName());

			empcertrepo.setAttachmentid(attachmentid);
			empcertrepo
					.setAttachmenttypeid(hrmsAttachmentRepository.findById(attachmentid).get().getAttachmenttypeid());

			empcertrepo.setAttachmenttypename(hrmsAttachmentTypeRepository
					.findById(hrmsAttachmentRepository.findById(attachmentid).get().getAttachmenttypeid()).get()
					.getName());

			empcertrepo.setCertificationcategoryid(dbempcert.getCertificationcategoryid());

			empcertrepo.setCertificationcategoryname(hrmsCertificationCategoryRepository
					.findById(dbempcert.getCertificationcategoryid()).get().getName());
			empcertrepo.setCountryid(dbempcert.getCountryid());
			empcertrepo
					.setCountryname(hrmsLocationCountryRepository.findById(dbempcert.getCountryid()).get().getName());
			empcertrepo.setDatend(dbempcert.getDatend());
			empcertrepo.setDatestart(dbempcert.getDatestart());
			empcertrepo.setDescription(dbempcert.getDescription());
			empcertrepo.setEmployeeid(dbempcert.getEmployeeid());
			empcertrepo.setExpire(dbempcert.getExpire());
			empcertrepo.setId(dbempcert.getId());
			empcertrepo.setInstitution(dbempcert.getInstitution());
			empcertrepo.setUri(hrmsAttachmentRepository.findById(attachmentid).get().getUri());

			empcertrepo.setEmployeeid(dbempcert.getEmployeeid());

			HrmsEmployee hrmsEmployee = hrmsEmployeeRepository.findById(dbempcert.getEmployeeid()).get();

			StringBuilder fullname = new StringBuilder();
			fullname.append(hrmsEmployee.getFirstName().trim());
			fullname.append("  " + hrmsEmployee.getMiddleName().trim());
			fullname.append(" " + hrmsEmployee.getLastName().trim());

			empcertrepo.setEmployeename(fullname.toString());
			empcertrepo.setApprovalComment(dbempcert.getApprovalComment());

			empcert.add(empcertrepo);

		});

		return ResponseEntity.status(HttpStatus.OK).body(empcert);

	}

	@Override
	public ResponseEntity<?> approveOrRejectEmployeeCertification(EmployeeApprovalComment employeeApprovalComment,
			int id, int status) {
		if (hrmsEmployeeCertificationRepository.existsByIdAndActive(id, 1) && (status == 1 || status == -1)) {
			// approve employee certification

			HrmsEmployeeCertification hrmsEmployeeCertification = hrmsEmployeeCertificationRepository.findById(id)
					.get();

			hrmsEmployeeCertification.setApproved(status);
			hrmsEmployeeCertification.setDate_updated(LocalDateTime.now());
			hrmsEmployeeCertification.setApprovalComment(employeeApprovalComment.getComment());
			hrmsEmployeeCertification.setApproverEmployeeid(employeeApprovalComment.getApproverEmployeeid());

			hrmsEmployeeCertificationRepository.saveAndFlush(hrmsEmployeeCertification);

			// approve employee certification attachment
			HrmsEmployeeCertificationAttachment hrmsEmployeeCertificationAttachment = hrmsEmployeeCertificationAttachmentRepository
					.findByCertificationid(id);

			hrmsEmployeeCertificationAttachment.setApproved(status);
			hrmsEmployeeCertificationAttachment.setDate_updated(LocalDateTime.now());
			hrmsEmployeeCertificationAttachmentRepository.saveAndFlush(hrmsEmployeeCertificationAttachment);

			// approve attachment

			HrmsAttachment hrmsAttachment = hrmsAttachmentRepository
					.findById(hrmsEmployeeCertificationAttachment.getAttachmentid()).get();

			hrmsAttachment.setApproved(status);
			hrmsAttachment.setDate_updated(LocalDateTime.now());
			hrmsAttachmentRepository.saveAndFlush(hrmsAttachment);

			// send notification
			String messages = "";

			HrmsEmployee hrmsEmployee = hrmsEmployeeRepository.findById(hrmsEmployeeCertification.getEmployeeid())
					.get();

			StringBuilder fullname = new StringBuilder();
			fullname.append(hrmsEmployee.getFirstName().trim());
			fullname.append("  " + hrmsEmployee.getMiddleName().trim());
			fullname.append(" " + hrmsEmployee.getLastName().trim());
			String username = fullname.toString();
			String usermail = hrmsEmployee.getEmail();
			if (status == -1) {
				messages = " Your Certification entry for   " + hrmsEmployeeCertification.getDescription()
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
