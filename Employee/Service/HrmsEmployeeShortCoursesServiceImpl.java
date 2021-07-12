package com.Hrms.Employee.Service;

import java.text.ParseException;
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
import com.Hrms.Employee.DTO.HrmsEmployeeShortCoursesR;
import com.Hrms.Employee.Entity.HrmsAttachment;
import com.Hrms.Employee.Entity.HrmsEmployee;
import com.Hrms.Employee.Entity.HrmsEmployeeShortCourseAttachment;
import com.Hrms.Employee.Entity.HrmsEmployeeShortCourses;
import com.Hrms.Employee.Repository.HrmsAttachmentRepository;
import com.Hrms.Employee.Repository.HrmsAttachmentTypeRepository;
import com.Hrms.Employee.Repository.HrmsEmployeeRepository;
import com.Hrms.Employee.Repository.HrmsEmployeeShortCourseAttachmentRepository;
import com.Hrms.Employee.Repository.HrmsEmployeeShortCoursesRepository;
import com.Hrms.Employee.Repository.HrmsLocationCountryRepository;

@Service
public class HrmsEmployeeShortCoursesServiceImpl implements HrmsEmployeeShortCoursesService {

	@Autowired
	private HrmsEmployeeShortCoursesRepository hrmsEmployeeShortCoursesRepository;

	@Autowired
	private HrmsAttachmentTypeRepository hrmsAttachmentTypeRepository;

	@Autowired
	private HrmsEmployeeRepository hrmsEmployeeRepository;

	@Autowired
	private HrmsAttachmentRepository hrmsAttachmentRepository;

	@Autowired
	private HrmsEmployeeShortCourseAttachmentRepository hrmsEmployeeShortCourseAttachmentRepository;

	@Autowired
	private HrmsLocationCountryRepository hrmsLocationCountryRepository;

	@Autowired
	private SendEmail sendEmail;

	@Override
	public ResponseEntity<HrmsEmployeeShortCoursesR> addEmployeeShortCourses(
			HrmsEmployeeShortCoursesR hrmsEmployeeShortCoursesR) {

		if (hrmsEmployeeShortCoursesR.getUri() != null) { // verify if the incoming request has attachment information

			if (hrmsEmployeeRepository.existsById(hrmsEmployeeShortCoursesR.getEmployeeid())
					&& hrmsAttachmentTypeRepository.existsById(hrmsEmployeeShortCoursesR.getAttachmenttypeid())
					&& hrmsLocationCountryRepository.existsById(hrmsEmployeeShortCoursesR.getCountryid())) {

				// below is the attachment setting and inserting into hrms attachment table
				HrmsAttachment hrmsAttachment = new HrmsAttachment();

				hrmsAttachment.setActive(1);
				hrmsAttachment.setApproved(0);
				hrmsAttachment.setUnique_id(UUID.randomUUID());
				hrmsAttachment.setDescription(hrmsEmployeeShortCoursesR.getAttachmentdescription());
				hrmsAttachment.setName(hrmsEmployeeShortCoursesR.getAttachmentname());
				hrmsAttachment.setUri(hrmsEmployeeShortCoursesR.getUri());
				hrmsAttachment.setAttachmenttypeid(hrmsEmployeeShortCoursesR.getAttachmenttypeid());

				int attachmentId = hrmsAttachmentRepository.saveAndFlush(hrmsAttachment).getId();

				// now prepare hrms employee short course table and insert

				HrmsEmployeeShortCourses hrmsEmployeeShortCourses = new HrmsEmployeeShortCourses();

				hrmsEmployeeShortCourses.setActive(1);
				hrmsEmployeeShortCourses.setApproved(0);
				hrmsEmployeeShortCourses.setUnique_id(UUID.randomUUID());
				hrmsEmployeeShortCourses.setCountryid(hrmsEmployeeShortCoursesR.getCountryid());
				try {
					hrmsEmployeeShortCourses
							.setDatend(new SimpleDateFormat("yyyy-MM-dd").parse(hrmsEmployeeShortCoursesR.getDatend()));
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				try {
					hrmsEmployeeShortCourses.setDatestart(
							new SimpleDateFormat("yyyy-MM-dd").parse(hrmsEmployeeShortCoursesR.getDatestart()));
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				hrmsEmployeeShortCourses.setDescription(hrmsEmployeeShortCoursesR.getDescription());
				hrmsEmployeeShortCourses.setEmployeeid(hrmsEmployeeShortCoursesR.getEmployeeid());
				hrmsEmployeeShortCourses.setExpire(hrmsEmployeeShortCoursesR.getExpire());
				hrmsEmployeeShortCourses.setInstitution(hrmsEmployeeShortCoursesR.getInstitution());
				hrmsEmployeeShortCourses.setCoursename(hrmsEmployeeShortCoursesR.getCoursename());
				int empshortcourseId = hrmsEmployeeShortCoursesRepository.saveAndFlush(hrmsEmployeeShortCourses)
						.getId();

				// now prepare and insert into employee short course attachment table

				HrmsEmployeeShortCourseAttachment hrmsEmployeeShortCourseAttachment = new HrmsEmployeeShortCourseAttachment();

				hrmsEmployeeShortCourseAttachment.setActive(1);
				hrmsEmployeeShortCourseAttachment.setApproved(0);
				hrmsEmployeeShortCourseAttachment.setAttachmentid(attachmentId);
				hrmsEmployeeShortCourseAttachment.setEmployeeid(hrmsEmployeeShortCoursesR.getEmployeeid());
				hrmsEmployeeShortCourseAttachment.setShortcourseid(empshortcourseId);
				hrmsEmployeeShortCourseAttachment.setUnique_id(UUID.randomUUID());

				hrmsEmployeeShortCourseAttachmentRepository.saveAndFlush(hrmsEmployeeShortCourseAttachment);

				return ResponseEntity.status(HttpStatus.OK).body(hrmsEmployeeShortCoursesR);

			} else {
				return ResponseEntity.status(HttpStatus.PRECONDITION_FAILED).body(hrmsEmployeeShortCoursesR);
			}
		} else {

			if (hrmsEmployeeRepository.existsById(hrmsEmployeeShortCoursesR.getEmployeeid()) &&

					hrmsLocationCountryRepository.existsById(hrmsEmployeeShortCoursesR.getCountryid())) {
				int attachmentId = 0;
				// now prepare hrms employee short course table and insert

				HrmsEmployeeShortCourses hrmsEmployeeShortCourses = new HrmsEmployeeShortCourses();

				hrmsEmployeeShortCourses.setActive(1);
				hrmsEmployeeShortCourses.setApproved(0);
				hrmsEmployeeShortCourses.setUnique_id(UUID.randomUUID());
				hrmsEmployeeShortCourses.setCountryid(hrmsEmployeeShortCoursesR.getCountryid());
				try {
					hrmsEmployeeShortCourses
							.setDatend(new SimpleDateFormat("yyyy-MM-dd").parse(hrmsEmployeeShortCoursesR.getDatend()));
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				try {
					hrmsEmployeeShortCourses.setDatestart(
							new SimpleDateFormat("yyyy-MM-dd").parse(hrmsEmployeeShortCoursesR.getDatestart()));
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				hrmsEmployeeShortCourses.setDescription(hrmsEmployeeShortCoursesR.getDescription());
				hrmsEmployeeShortCourses.setEmployeeid(hrmsEmployeeShortCoursesR.getEmployeeid());
				hrmsEmployeeShortCourses.setExpire(hrmsEmployeeShortCoursesR.getExpire());
				hrmsEmployeeShortCourses.setInstitution(hrmsEmployeeShortCoursesR.getInstitution());
				hrmsEmployeeShortCourses.setCoursename(hrmsEmployeeShortCoursesR.getCoursename());

				int empshortcourseId = hrmsEmployeeShortCoursesRepository.saveAndFlush(hrmsEmployeeShortCourses)
						.getId();

				// now prepare and insert into employee short course attachment table

				HrmsEmployeeShortCourseAttachment hrmsEmployeeShortCourseAttachment = new HrmsEmployeeShortCourseAttachment();

				hrmsEmployeeShortCourseAttachment.setActive(1);
				hrmsEmployeeShortCourseAttachment.setApproved(0);
				hrmsEmployeeShortCourseAttachment.setAttachmentid(attachmentId);
				hrmsEmployeeShortCourseAttachment.setEmployeeid(hrmsEmployeeShortCoursesR.getEmployeeid());
				hrmsEmployeeShortCourseAttachment.setShortcourseid(empshortcourseId);
				hrmsEmployeeShortCourseAttachment.setUnique_id(UUID.randomUUID());

				hrmsEmployeeShortCourseAttachmentRepository.saveAndFlush(hrmsEmployeeShortCourseAttachment);

				return ResponseEntity.status(HttpStatus.OK).body(hrmsEmployeeShortCoursesR);
			} else {
				return ResponseEntity.status(HttpStatus.PRECONDITION_FAILED).body(hrmsEmployeeShortCoursesR);
			}
		}

	}

	@Override
	public ResponseEntity<HrmsEmployeeShortCoursesR> getEmployeeShortCoursesById(int id) {

		String pattern = "yyyy-MM-dd";
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
		if (hrmsEmployeeShortCoursesRepository.existsByIdAndActive(id, 1)) {

			HrmsEmployeeShortCourses hrmsEmployeeShortCourses = hrmsEmployeeShortCoursesRepository.findByIdAndActive(id,
					1);

			HrmsEmployeeShortCoursesR hrmsEmployeeShortCoursesR = new HrmsEmployeeShortCoursesR();

			hrmsEmployeeShortCoursesR.setActive(hrmsEmployeeShortCourses.getActive());
			hrmsEmployeeShortCoursesR.setApproved(hrmsEmployeeShortCourses.getApproved());
			hrmsEmployeeShortCoursesR.setCoursename(hrmsEmployeeShortCourses.getCoursename());
			hrmsEmployeeShortCoursesR.setApprovalComment(hrmsEmployeeShortCourses.getApprovalComment());

			int attachmentid = hrmsEmployeeShortCourseAttachmentRepository.findByShortcourseid(id).getAttachmentid();
			if (attachmentid != 0) {
				hrmsEmployeeShortCoursesR.setAttachmentdescription(
						hrmsAttachmentRepository.findById(attachmentid).get().getDescription());
				hrmsEmployeeShortCoursesR
						.setAttachmentname(hrmsAttachmentRepository.findById(attachmentid).get().getName());

				int attachmenttypeid = hrmsAttachmentRepository.findById(attachmentid).get().getAttachmenttypeid();
				hrmsEmployeeShortCoursesR.setAttachmenttypeid(attachmenttypeid);
				hrmsEmployeeShortCoursesR
						.setAttachmenttypename(hrmsAttachmentTypeRepository.findById(attachmenttypeid).get().getName());
				hrmsEmployeeShortCoursesR.setAttchmentid(attachmentid);
				hrmsEmployeeShortCoursesR.setUri(hrmsAttachmentRepository.findById(attachmentid).get().getUri());

			}
			hrmsEmployeeShortCoursesR.setCountryid(hrmsEmployeeShortCourses.getCountryid());
			hrmsEmployeeShortCoursesR.setCountryname(
					hrmsLocationCountryRepository.findById(hrmsEmployeeShortCourses.getCountryid()).get().getName());
			hrmsEmployeeShortCoursesR.setDatend(simpleDateFormat.format(hrmsEmployeeShortCourses.getDatend()));
			hrmsEmployeeShortCoursesR.setDatestart(simpleDateFormat.format(hrmsEmployeeShortCourses.getDatestart()));
			hrmsEmployeeShortCoursesR.setDescription(hrmsEmployeeShortCourses.getDescription());
			hrmsEmployeeShortCoursesR.setEmployeeid(hrmsEmployeeShortCourses.getEmployeeid());
			hrmsEmployeeShortCoursesR.setEmployeename(hrmsEmployeeRepository
					.findById(hrmsEmployeeShortCourses.getEmployeeid()).get().getFirstName().trim()
					+ " "
					+ hrmsEmployeeRepository.findById(hrmsEmployeeShortCourses.getEmployeeid()).get().getMiddleName()
							.trim()
					+ " " + hrmsEmployeeRepository.findById(hrmsEmployeeShortCourses.getEmployeeid()).get()
							.getLastName().trim());
			hrmsEmployeeShortCoursesR.setExpire(hrmsEmployeeShortCourses.getExpire());
			hrmsEmployeeShortCoursesR.setId(id);
			hrmsEmployeeShortCoursesR.setInstitution(hrmsEmployeeShortCourses.getInstitution());

			return ResponseEntity.status(HttpStatus.OK).body(hrmsEmployeeShortCoursesR);
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
		}
	}

	@Override
	public ResponseEntity<HrmsEmployeeShortCoursesR> updateEmployeeShortCourses(
			HrmsEmployeeShortCoursesR hrmsEmployeeShortCoursesR, int id) {

		String pattern = "yyyy-MM-dd";
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);

		if ((hrmsEmployeeShortCoursesRepository.existsByIdAndActive(id, 1))) {
			// update employee course first
			HrmsEmployeeShortCourses hrmsEmployeeShortCourses = hrmsEmployeeShortCoursesRepository.findByIdAndActive(id,
					1);

			hrmsEmployeeShortCourses.setApproved(0);
			hrmsEmployeeShortCourses.setDate_updated(LocalDateTime.now());
			hrmsEmployeeShortCourses.setCountryid(hrmsEmployeeShortCoursesR.getCountryid());
			hrmsEmployeeShortCourses.setCoursename(hrmsEmployeeShortCoursesR.getCoursename());
			try {
				hrmsEmployeeShortCourses
						.setDatend(new SimpleDateFormat("yyyy-MM-dd").parse(hrmsEmployeeShortCoursesR.getDatend()));
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			try {
				hrmsEmployeeShortCourses.setDatestart(
						new SimpleDateFormat("yyyy-MM-dd").parse(hrmsEmployeeShortCoursesR.getDatestart()));
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			hrmsEmployeeShortCourses.setDescription(hrmsEmployeeShortCoursesR.getDescription());
			hrmsEmployeeShortCourses.setExpire(hrmsEmployeeShortCoursesR.getExpire());
			hrmsEmployeeShortCourses.setInstitution(hrmsEmployeeShortCoursesR.getInstitution());

			// update employee course attachment now

			HrmsEmployeeShortCourseAttachment hrmsEmployeeShortCourseAttachment = hrmsEmployeeShortCourseAttachmentRepository
					.findByShortcourseid(id);

			if (hrmsEmployeeShortCourseAttachment.getAttachmentid() != 0) {
				hrmsEmployeeShortCourseAttachment.setApproved(0);

				hrmsEmployeeShortCourseAttachment.setDate_updated(LocalDateTime.now());
				hrmsEmployeeShortCourseAttachmentRepository.saveAndFlush(hrmsEmployeeShortCourseAttachment);

				// update attachment now
				if (hrmsEmployeeShortCoursesR.getUri() != null) {
					HrmsAttachment hrmsAttachment = hrmsAttachmentRepository
							.findById(hrmsEmployeeShortCourseAttachment.getAttachmentid()).get();
					hrmsAttachment.setApproved(0);
					hrmsAttachment.setDate_updated(LocalDateTime.now());
					hrmsAttachment.setDescription(hrmsEmployeeShortCoursesR.getAttachmentdescription());
					hrmsAttachment.setName(hrmsEmployeeShortCoursesR.getAttachmentname());
					hrmsAttachment.setUri(hrmsEmployeeShortCoursesR.getUri());
					hrmsAttachment.setAttachmenttypeid(hrmsEmployeeShortCoursesR.getAttachmenttypeid());

					hrmsAttachmentRepository.saveAndFlush(hrmsAttachment);
				}

			} else {
				// check if user is updating course but attaching attachment for the first time
				if (hrmsEmployeeShortCoursesR.getUri() != null) {
					HrmsAttachment hrmsAttachment = new HrmsAttachment();
					hrmsAttachment.setActive(1);
					hrmsAttachment.setApproved(0);
					hrmsAttachment.setAttachmenttypeid(hrmsEmployeeShortCoursesR.getAttachmenttypeid());
					hrmsAttachment.setDescription(hrmsEmployeeShortCoursesR.getAttachmentdescription());
					hrmsAttachment.setName(hrmsEmployeeShortCoursesR.getAttachmentname());
					hrmsAttachment.setUri(hrmsEmployeeShortCoursesR.getUri());
					hrmsAttachment.setUnique_id(UUID.randomUUID());
					int attachmendid = hrmsAttachmentRepository.saveAndFlush(hrmsAttachment).getId();

					// set and update employee course attachment

					hrmsEmployeeShortCourseAttachment.setApproved(0);
					hrmsEmployeeShortCourseAttachment.setAttachmentid(attachmendid);

					hrmsEmployeeShortCourseAttachment.setDate_updated(LocalDateTime.now());
					hrmsEmployeeShortCourseAttachmentRepository.saveAndFlush(hrmsEmployeeShortCourseAttachment);

				} else {

					hrmsEmployeeShortCourseAttachment.setApproved(0);

					hrmsEmployeeShortCourseAttachment.setDate_updated(LocalDateTime.now());
					hrmsEmployeeShortCourseAttachmentRepository.saveAndFlush(hrmsEmployeeShortCourseAttachment);

				}
			}

			return ResponseEntity.status(HttpStatus.OK).body(hrmsEmployeeShortCoursesR);
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
		}
	}

	@Override
	public ResponseEntity<?> deleteEmployeeShortCourses(int id) {
		if ((hrmsEmployeeShortCoursesRepository.existsByIdAndActive(id, 1))) {
			HrmsEmployeeShortCourses hrmsEmployeeShortCourses = hrmsEmployeeShortCoursesRepository.findById(id).get();
			// deactivate employee course
			hrmsEmployeeShortCourses.setActive(0);
			hrmsEmployeeShortCourses.setDate_updated(LocalDateTime.now());

			hrmsEmployeeShortCoursesRepository.saveAndFlush(hrmsEmployeeShortCourses);

			// deactivate employee course attachment
			HrmsEmployeeShortCourseAttachment hrmsEmployeeShortCourseAttachment = hrmsEmployeeShortCourseAttachmentRepository
					.findByShortcourseid(hrmsEmployeeShortCourses.getId());

			hrmsEmployeeShortCourseAttachment.setActive(0);
			hrmsEmployeeShortCourseAttachment.setDate_updated(LocalDateTime.now());
			hrmsEmployeeShortCourseAttachmentRepository.saveAndFlush(hrmsEmployeeShortCourseAttachment);

			// deactivate the related attachment but check first if the id has the
			// attachment or not

			if (hrmsEmployeeShortCourseAttachment.getAttachmentid() != 0) {

				HrmsAttachment hrmsAttachment = hrmsAttachmentRepository
						.findById(hrmsEmployeeShortCourseAttachment.getAttachmentid()).get();

				hrmsAttachment.setActive(0);
				hrmsAttachment.setDate_updated(LocalDateTime.now());

				hrmsAttachmentRepository.saveAndFlush(hrmsAttachment);
			}

			return ResponseEntity.status(HttpStatus.OK).body(null);
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
		}
	}

	@Override
	public ResponseEntity<List<HrmsEmployeeShortCoursesR>> listEmployeeShortCourses() {

		String pattern = "yyyy-MM-dd";
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);

		List<HrmsEmployeeShortCoursesR> shortcouseslist = new ArrayList<>();
		List<HrmsEmployeeShortCourses> hrmsEmployeeShortCourses = hrmsEmployeeShortCoursesRepository.findByActive(1);
		hrmsEmployeeShortCourses.forEach(shortcourses -> {
			HrmsEmployeeShortCoursesR hrmsEmployeeShortCoursesR = new HrmsEmployeeShortCoursesR();

			hrmsEmployeeShortCoursesR.setActive(shortcourses.getActive());
			hrmsEmployeeShortCoursesR.setApproved(shortcourses.getApproved());
			hrmsEmployeeShortCoursesR.setCoursename(shortcourses.getCoursename());
			hrmsEmployeeShortCoursesR.setApprovalComment(shortcourses.getApprovalComment());

			int attachmentid = hrmsEmployeeShortCourseAttachmentRepository.findByShortcourseid(shortcourses.getId())
					.getAttachmentid();
			if (attachmentid != 0) {
				hrmsEmployeeShortCoursesR.setAttachmentdescription(
						hrmsAttachmentRepository.findById(attachmentid).get().getDescription());
				hrmsEmployeeShortCoursesR
						.setAttachmentname(hrmsAttachmentRepository.findById(attachmentid).get().getName());

				int attachmenttypeid = hrmsAttachmentRepository.findById(attachmentid).get().getAttachmenttypeid();
				hrmsEmployeeShortCoursesR.setAttachmenttypeid(attachmenttypeid);
				hrmsEmployeeShortCoursesR
						.setAttachmenttypename(hrmsAttachmentTypeRepository.findById(attachmenttypeid).get().getName());
				hrmsEmployeeShortCoursesR.setAttchmentid(attachmentid);
				hrmsEmployeeShortCoursesR.setUri(hrmsAttachmentRepository.findById(attachmentid).get().getUri());

			}
			hrmsEmployeeShortCoursesR.setCountryid(shortcourses.getCountryid());
			hrmsEmployeeShortCoursesR.setCountryname(
					hrmsLocationCountryRepository.findById(shortcourses.getCountryid()).get().getName());
			hrmsEmployeeShortCoursesR.setDatend(simpleDateFormat.format(shortcourses.getDatend()));
			hrmsEmployeeShortCoursesR.setDatestart(simpleDateFormat.format(shortcourses.getDatestart()));
			hrmsEmployeeShortCoursesR.setDescription(shortcourses.getDescription());
			hrmsEmployeeShortCoursesR.setEmployeeid(shortcourses.getEmployeeid());
			hrmsEmployeeShortCoursesR.setEmployeename(
					hrmsEmployeeRepository.findById(shortcourses.getEmployeeid()).get().getFirstName().trim() + " "
							+ hrmsEmployeeRepository.findById(shortcourses.getEmployeeid()).get().getMiddleName().trim()
							+ " "
							+ hrmsEmployeeRepository.findById(shortcourses.getEmployeeid()).get().getLastName().trim());
			hrmsEmployeeShortCoursesR.setExpire(shortcourses.getExpire());
			hrmsEmployeeShortCoursesR.setId(shortcourses.getId());
			hrmsEmployeeShortCoursesR.setInstitution(shortcourses.getInstitution());
			shortcouseslist.add(hrmsEmployeeShortCoursesR);

		});

		return ResponseEntity.status(HttpStatus.OK).body(shortcouseslist);

	}

	@Override
	public ResponseEntity<List<HrmsEmployeeShortCoursesR>> getEmployeeShortCoursesByEmpId(int empid) {

		String pattern = "yyyy-MM-dd";
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);

		if (hrmsEmployeeShortCoursesRepository.existsByEmployeeidAndActive(empid, 1)) {
			List<HrmsEmployeeShortCoursesR> shortcouseslist = new ArrayList<>();
			List<HrmsEmployeeShortCourses> hrmsEmployeeShortCourses = hrmsEmployeeShortCoursesRepository
					.findByEmployeeidAndActive(empid, 1);
			hrmsEmployeeShortCourses.forEach(shortcourses -> {
				HrmsEmployeeShortCoursesR hrmsEmployeeShortCoursesR = new HrmsEmployeeShortCoursesR();

				hrmsEmployeeShortCoursesR.setActive(shortcourses.getActive());
				hrmsEmployeeShortCoursesR.setApproved(shortcourses.getApproved());
				hrmsEmployeeShortCoursesR.setCoursename(shortcourses.getCoursename());
				hrmsEmployeeShortCoursesR.setApprovalComment(shortcourses.getApprovalComment());

				int attachmentid = hrmsEmployeeShortCourseAttachmentRepository.findByShortcourseid(shortcourses.getId())
						.getAttachmentid();
				if (attachmentid != 0) {
					hrmsEmployeeShortCoursesR.setAttachmentdescription(
							hrmsAttachmentRepository.findById(attachmentid).get().getDescription());
					hrmsEmployeeShortCoursesR
							.setAttachmentname(hrmsAttachmentRepository.findById(attachmentid).get().getName());

					int attachmenttypeid = hrmsAttachmentRepository.findById(attachmentid).get().getAttachmenttypeid();
					hrmsEmployeeShortCoursesR.setAttachmenttypeid(attachmenttypeid);
					hrmsEmployeeShortCoursesR.setAttachmenttypename(
							hrmsAttachmentTypeRepository.findById(attachmenttypeid).get().getName());
					hrmsEmployeeShortCoursesR.setAttchmentid(attachmentid);
					hrmsEmployeeShortCoursesR.setUri(hrmsAttachmentRepository.findById(attachmentid).get().getUri());

				}
				hrmsEmployeeShortCoursesR.setCountryid(shortcourses.getCountryid());
				hrmsEmployeeShortCoursesR.setCountryname(
						hrmsLocationCountryRepository.findById(shortcourses.getCountryid()).get().getName());
				hrmsEmployeeShortCoursesR.setDatend(simpleDateFormat.format(shortcourses.getDatend()));
				hrmsEmployeeShortCoursesR.setDatestart(simpleDateFormat.format(shortcourses.getDatestart()));
				hrmsEmployeeShortCoursesR.setDescription(shortcourses.getDescription());
				hrmsEmployeeShortCoursesR.setEmployeeid(shortcourses.getEmployeeid());
				hrmsEmployeeShortCoursesR.setEmployeename(hrmsEmployeeRepository.findById(shortcourses.getEmployeeid())
						.get().getFirstName().trim() + " "
						+ hrmsEmployeeRepository.findById(shortcourses.getEmployeeid()).get().getMiddleName().trim()
						+ " "
						+ hrmsEmployeeRepository.findById(shortcourses.getEmployeeid()).get().getLastName().trim());
				hrmsEmployeeShortCoursesR.setExpire(shortcourses.getExpire());
				hrmsEmployeeShortCoursesR.setId(shortcourses.getId());
				hrmsEmployeeShortCoursesR.setInstitution(shortcourses.getInstitution());
				shortcouseslist.add(hrmsEmployeeShortCoursesR);

			});

			return ResponseEntity.status(HttpStatus.OK).body(shortcouseslist);
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
		}
	}

	@Override
	public ResponseEntity<?> approveOrRejectEmployeeShortCourses(EmployeeApprovalComment employeeApprovalComment,
			int id, int status) {
		if (hrmsEmployeeShortCoursesRepository.existsByIdAndActive(id, 1) && (status == 1 || status == -1)) {

			// approve employee short courses
			HrmsEmployeeShortCourses hrmsEmployeeShortCourses = hrmsEmployeeShortCoursesRepository.findById(id).get();
			hrmsEmployeeShortCourses.setApprovalComment(employeeApprovalComment.getComment());
			hrmsEmployeeShortCourses.setApproverEmployeeid(employeeApprovalComment.getApproverEmployeeid());
			hrmsEmployeeShortCourses.setApproved(status);
			hrmsEmployeeShortCourses.setDate_updated(LocalDateTime.now());
			hrmsEmployeeShortCoursesRepository.saveAndFlush(hrmsEmployeeShortCourses);

			// approve employee short courses attachment

			HrmsEmployeeShortCourseAttachment hrmsEmployeeShortCourseAttachment = hrmsEmployeeShortCourseAttachmentRepository
					.findByShortcourseid(id);
			hrmsEmployeeShortCourseAttachment.setApproved(status);
			hrmsEmployeeShortCourseAttachment.setDate_updated(LocalDateTime.now());
			hrmsEmployeeShortCourseAttachmentRepository.saveAndFlush(hrmsEmployeeShortCourseAttachment);

			// approve related attachment

			HrmsAttachment hrmsAttachment = hrmsAttachmentRepository
					.findById(hrmsEmployeeShortCourseAttachment.getAttachmentid()).get();
			hrmsAttachment.setApproved(status);
			hrmsAttachment.setDate_updated(LocalDateTime.now());
			hrmsAttachmentRepository.saveAndFlush(hrmsAttachment);

			// send notification
			String messages = "";

			HrmsEmployee hrmsEmployee = hrmsEmployeeRepository.findById(hrmsEmployeeShortCourses.getEmployeeid()).get();

			StringBuilder fullname = new StringBuilder();
			fullname.append(hrmsEmployee.getFirstName().trim());
			fullname.append("  " + hrmsEmployee.getMiddleName().trim());
			fullname.append(" " + hrmsEmployee.getLastName().trim());
			String username = fullname.toString();
			String usermail = hrmsEmployee.getEmail();
			if (status == -1) {
				messages = " Your Shortcourse entry  for  " + hrmsEmployeeShortCourses.getCoursename()
						+ " has been rejected,Kindly edit and re-submit again " + "  Comments for rejection is  "
						+ employeeApprovalComment.getComment();
				sendEmail.sendmailNotification(username, usermail, messages);
			}

			return ResponseEntity.status(HttpStatus.OK).body(id);
		} else {
			return ResponseEntity.status(HttpStatus.PRECONDITION_FAILED).body(id);
		}
	}

	@Override
	public ResponseEntity<List<HrmsEmployeeShortCoursesR>> listEmployeeShortCoursesNonApproved() {

		String pattern = "yyyy-MM-dd";
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);

		List<HrmsEmployeeShortCoursesR> shortcouseslist = new ArrayList<>();
		List<HrmsEmployeeShortCourses> hrmsEmployeeShortCourses = hrmsEmployeeShortCoursesRepository
				.findByActiveAndApprovedOrderByEmployeeid(1, 0);
		hrmsEmployeeShortCourses.forEach(shortcourses -> {
			HrmsEmployeeShortCoursesR hrmsEmployeeShortCoursesR = new HrmsEmployeeShortCoursesR();

			hrmsEmployeeShortCoursesR.setActive(shortcourses.getActive());
			hrmsEmployeeShortCoursesR.setApproved(shortcourses.getApproved());
			hrmsEmployeeShortCoursesR.setCoursename(shortcourses.getCoursename());
			hrmsEmployeeShortCoursesR.setApprovalComment(shortcourses.getApprovalComment());

			int attachmentid = hrmsEmployeeShortCourseAttachmentRepository.findByShortcourseid(shortcourses.getId())
					.getAttachmentid();
			if (attachmentid != 0) {
				hrmsEmployeeShortCoursesR.setAttachmentdescription(
						hrmsAttachmentRepository.findById(attachmentid).get().getDescription());
				hrmsEmployeeShortCoursesR
						.setAttachmentname(hrmsAttachmentRepository.findById(attachmentid).get().getName());

				int attachmenttypeid = hrmsAttachmentRepository.findById(attachmentid).get().getAttachmenttypeid();
				hrmsEmployeeShortCoursesR.setAttachmenttypeid(attachmenttypeid);
				hrmsEmployeeShortCoursesR
						.setAttachmenttypename(hrmsAttachmentTypeRepository.findById(attachmenttypeid).get().getName());
				hrmsEmployeeShortCoursesR.setAttchmentid(attachmentid);
				hrmsEmployeeShortCoursesR.setUri(hrmsAttachmentRepository.findById(attachmentid).get().getUri());

			}
			hrmsEmployeeShortCoursesR.setCountryid(shortcourses.getCountryid());
			hrmsEmployeeShortCoursesR.setCountryname(
					hrmsLocationCountryRepository.findById(shortcourses.getCountryid()).get().getName());
			hrmsEmployeeShortCoursesR.setDatend(simpleDateFormat.format(shortcourses.getDatend()));
			hrmsEmployeeShortCoursesR.setDatestart(simpleDateFormat.format(shortcourses.getDatestart()));
			hrmsEmployeeShortCoursesR.setDescription(shortcourses.getDescription());
			hrmsEmployeeShortCoursesR.setEmployeeid(shortcourses.getEmployeeid());
			hrmsEmployeeShortCoursesR.setEmployeename(
					hrmsEmployeeRepository.findById(shortcourses.getEmployeeid()).get().getFirstName().trim() + " "
							+ hrmsEmployeeRepository.findById(shortcourses.getEmployeeid()).get().getMiddleName().trim()
							+ " "
							+ hrmsEmployeeRepository.findById(shortcourses.getEmployeeid()).get().getLastName().trim());
			hrmsEmployeeShortCoursesR.setExpire(shortcourses.getExpire());
			hrmsEmployeeShortCoursesR.setId(shortcourses.getId());
			hrmsEmployeeShortCoursesR.setInstitution(shortcourses.getInstitution());
			shortcouseslist.add(hrmsEmployeeShortCoursesR);

		});

		return ResponseEntity.status(HttpStatus.OK).body(shortcouseslist);

	}

}
