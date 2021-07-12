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
import com.Hrms.Employee.DTO.EmployeeRelativeRequest;
import com.Hrms.Employee.DTO.EmployeeRelativeResponse;
import com.Hrms.Employee.Entity.HrmsAttachment;
import com.Hrms.Employee.Entity.HrmsEmployee;
import com.Hrms.Employee.Entity.HrmsEmployeeRelative;
import com.Hrms.Employee.Entity.HrmsEmployeeRelativeAttachment;
import com.Hrms.Employee.Repository.HrmsAttachmentRepository;
import com.Hrms.Employee.Repository.HrmsAttachmentTypeRepository;
import com.Hrms.Employee.Repository.HrmsEmployeeRelativeAttachmentRepository;
import com.Hrms.Employee.Repository.HrmsEmployeeRelativeRepository;
import com.Hrms.Employee.Repository.HrmsEmployeeRepository;
import com.Hrms.Employee.Repository.HrmsLocationCountryRepository;
import com.Hrms.Employee.Repository.HrmsNationalityRepository;
import com.Hrms.Employee.Repository.HrmsRelativeCategoryRepository;

@Service
public class HrmsEmployeeRelativeServiceImpl implements HrmsEmployeeRelativeService {

	@Autowired
	private HrmsEmployeeRelativeRepository hrmsEmployeeRelativeRepository;

	@Autowired
	private HrmsEmployeeRepository hrmsEmployeeRepository;

	@Autowired
	private HrmsRelativeCategoryRepository hrmsRelativeCategoryRepository;

	@Autowired
	private HrmsLocationCountryRepository hrmsLocationCountryRepository;

	@Autowired
	private HrmsNationalityRepository hrmsNationalityRepository;

	@Autowired
	private HrmsEmployeeRelativeAttachmentRepository hrmsEmployeeRelativeAttachmentRepository;

	@Autowired
	private HrmsAttachmentRepository hrmsAttachmentRepository;

	@Autowired
	private HrmsAttachmentTypeRepository hrmsAttachmentTypeRepository;

	@Autowired
	private SendEmail sendEmail;

	@Override
	public ResponseEntity<EmployeeRelativeRequest> addEmployeeRelative(EmployeeRelativeRequest hrmsEmployeeRelative) {

		if (hrmsEmployeeRepository.existsById(hrmsEmployeeRelative.getEmployeeid())) {
			if (!hrmsRelativeCategoryRepository.findById(hrmsEmployeeRelative.getRelativecategoryid()).get().getName()
					.toLowerCase().contains("kin")) {

				if (hrmsRelativeCategoryRepository.existsById(hrmsEmployeeRelative.getRelativecategoryid())
						&& hrmsLocationCountryRepository.existsById(hrmsEmployeeRelative.getCountryofbirthid())
						&& hrmsLocationCountryRepository.existsById(hrmsEmployeeRelative.getCountryofresidenceid())
						&& hrmsNationalityRepository.existsById(hrmsEmployeeRelative.getNationalityid())) {
					if (hrmsEmployeeRelativeRepository
							.existsByEmployeeidAndRelativecategoryidAndFirstnameAndMiddlenameAndLastnameAndActive(
									hrmsEmployeeRelative.getEmployeeid(), hrmsEmployeeRelative.getRelativecategoryid(),
									hrmsEmployeeRelative.getFirstname(), hrmsEmployeeRelative.getMiddlename(),
									hrmsEmployeeRelative.getLastname(), 1)) {
						return ResponseEntity.status(HttpStatus.ALREADY_REPORTED).body(null);

					} else {
						int atid = 0;
						// add attachment
						if (hrmsEmployeeRelative.getUri() != null) {
							HrmsAttachment hrmsAttachment = new HrmsAttachment();
							hrmsAttachment.setActive(1);
							hrmsAttachment.setApproved(0);
							hrmsAttachment.setAttachmenttypeid(hrmsEmployeeRelative.getAttachmenttypeid());
							hrmsAttachment.setDescription(hrmsEmployeeRelative.getAttachmentdescription());
							hrmsAttachment.setName(hrmsEmployeeRelative.getAttachmentname());
							hrmsAttachment.setUnique_id(UUID.randomUUID());
							hrmsAttachment.setAttachmenttypeid(hrmsEmployeeRelative.getAttachmenttypeid());
							hrmsAttachment.setUri(hrmsEmployeeRelative.getUri());

							atid = hrmsAttachmentRepository.save(hrmsAttachment).getId();
						}
						// add empl relative

						HrmsEmployeeRelative employeeRelative = new HrmsEmployeeRelative();
						employeeRelative.setActive(1);
						employeeRelative.setApproved(0);
						employeeRelative.setUnique_id(UUID.randomUUID());
						employeeRelative.setAddress(hrmsEmployeeRelative.getAddress());
						employeeRelative.setAddresspermanent(hrmsEmployeeRelative.getAddresspermanent());
						employeeRelative.setCountryofbirthid(hrmsEmployeeRelative.getCountryofbirthid());
						employeeRelative.setCountryofresidenceid(hrmsEmployeeRelative.getCountryofresidenceid());
						employeeRelative.setEmployeeid(hrmsEmployeeRelative.getEmployeeid());
						employeeRelative.setFirstname(hrmsEmployeeRelative.getFirstname());
						employeeRelative.setMiddlename(hrmsEmployeeRelative.getMiddlename());
						employeeRelative.setLastname(hrmsEmployeeRelative.getLastname());
						employeeRelative.setNationalityid(hrmsEmployeeRelative.getNationalityid());
						employeeRelative.setPhoneprimary(hrmsEmployeeRelative.getPhoneprimary());
						employeeRelative.setPhonesecondary(hrmsEmployeeRelative.getPhonesecondary());
						employeeRelative.setRelativecategoryid(hrmsEmployeeRelative.getRelativecategoryid());
						employeeRelative.setDob(hrmsEmployeeRelative.getDob());

						int emrelid = hrmsEmployeeRelativeRepository.save(employeeRelative).getId();

						// add emp relative attachment

						HrmsEmployeeRelativeAttachment hrmsEmployeeRelativeAttachment = new HrmsEmployeeRelativeAttachment();
						hrmsEmployeeRelativeAttachment.setActive(1);
						hrmsEmployeeRelativeAttachment.setApproved(0);
						hrmsEmployeeRelativeAttachment.setUnique_id(UUID.randomUUID());
						hrmsEmployeeRelativeAttachment.setAttachmentid(atid);
						hrmsEmployeeRelativeAttachment.setRelativeid(emrelid);

						hrmsEmployeeRelativeAttachmentRepository.save(hrmsEmployeeRelativeAttachment);

						return ResponseEntity.status(HttpStatus.OK).body(hrmsEmployeeRelative);

					}
				} else {
					return ResponseEntity.status(HttpStatus.PRECONDITION_FAILED).body(null);
				}
			} else {
				if (hrmsEmployeeRelativeRepository.existsByEmployeeidAndRelativecategoryidAndFirstname(
						hrmsEmployeeRelative.getEmployeeid(), hrmsEmployeeRelative.getRelativecategoryid(),
						hrmsEmployeeRelative.getFirstname())) {
					return ResponseEntity.status(HttpStatus.ALREADY_REPORTED).body(null);

				} else {
					int atid = 0;
					// If relative category is next of kin , then no need to add atachment details

					// add empl relative

					HrmsEmployeeRelative employeeRelative = new HrmsEmployeeRelative();
					employeeRelative.setActive(1);
					employeeRelative.setApproved(0);
					employeeRelative.setUnique_id(UUID.randomUUID());
					employeeRelative.setAddress(hrmsEmployeeRelative.getAddress());
					employeeRelative.setAddresspermanent(hrmsEmployeeRelative.getAddresspermanent());

					employeeRelative.setEmployeeid(hrmsEmployeeRelative.getEmployeeid());
					employeeRelative.setFirstname(hrmsEmployeeRelative.getFirstname());
					employeeRelative.setMiddlename(hrmsEmployeeRelative.getMiddlename());
					employeeRelative.setLastname(hrmsEmployeeRelative.getLastname());

					employeeRelative.setPhoneprimary(hrmsEmployeeRelative.getPhoneprimary());
					employeeRelative.setPhonesecondary(hrmsEmployeeRelative.getPhonesecondary());
					employeeRelative.setRelativecategoryid(hrmsEmployeeRelative.getRelativecategoryid());
					employeeRelative.setDob(hrmsEmployeeRelative.getDob());

					int emrelid = hrmsEmployeeRelativeRepository.save(employeeRelative).getId();

					// add emp relative attachment

					HrmsEmployeeRelativeAttachment hrmsEmployeeRelativeAttachment = new HrmsEmployeeRelativeAttachment();
					hrmsEmployeeRelativeAttachment.setActive(1);
					hrmsEmployeeRelativeAttachment.setApproved(0);
					hrmsEmployeeRelativeAttachment.setUnique_id(UUID.randomUUID());
					hrmsEmployeeRelativeAttachment.setAttachmentid(atid);
					hrmsEmployeeRelativeAttachment.setRelativeid(emrelid);

					hrmsEmployeeRelativeAttachmentRepository.save(hrmsEmployeeRelativeAttachment);

					return ResponseEntity.status(HttpStatus.OK).body(hrmsEmployeeRelative);
				}

			}
		} else {
			return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(null);
		}
	}

	@Override
	public ResponseEntity<List<EmployeeRelativeResponse>> getEmployeeRelativeByEmpId(int id) {
		String pattern = "yyyy-MM-dd";
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);

		if (hrmsEmployeeRelativeRepository.existsByEmployeeid(id)) {

			List<EmployeeRelativeResponse> rltivelist = new ArrayList<>();
			List<HrmsEmployeeRelative> relativelist = hrmsEmployeeRelativeRepository.findByEmployeeidAndActive(id, 1);

			relativelist.forEach(dbemp -> {

				EmployeeRelativeResponse emprespo = new EmployeeRelativeResponse();

				emprespo.setEmployeeid(dbemp.getEmployeeid());
				emprespo.setId(dbemp.getId());
				emprespo.setMobileNo(dbemp.getPhoneprimary());
				if (dbemp.getDob() != null) {
					emprespo.setDob(simpleDateFormat.format(dbemp.getDob()));
				}

				emprespo.setPhoneprimary(dbemp.getPhoneprimary());
				emprespo.setRelativecategoryid(dbemp.getRelativecategoryid());
				emprespo.setRelativecategoryname(
						hrmsRelativeCategoryRepository.findById(dbemp.getRelativecategoryid()).get().getName());

				emprespo.setFirstname(dbemp.getFirstname());
				emprespo.setMiddlename(dbemp.getMiddlename());
				emprespo.setLastname(dbemp.getLastname());
				emprespo.setActive(dbemp.getActive());
				emprespo.setApproved(dbemp.getApproved());
				int atachmentid = hrmsEmployeeRelativeAttachmentRepository.findByRelativeid(dbemp.getId())
						.getAttachmentid();
				if (atachmentid != 0) {
					emprespo.setAttachmentdescription(
							hrmsAttachmentRepository.findById(atachmentid).get().getDescription());

					emprespo.setAttachmentid(atachmentid);
					emprespo.setAttachmentname(hrmsAttachmentRepository.findById(atachmentid).get().getName());
					int attypeid = hrmsAttachmentRepository.findById(atachmentid).get().getAttachmenttypeid();
					emprespo.setAttachmenttypeid(attypeid);
					emprespo.setAttachmenttypename(hrmsAttachmentTypeRepository.findById(attypeid).get().getName());
					emprespo.setUri(hrmsAttachmentRepository.findById(atachmentid).get().getUri());
				}

				StringBuilder builderfullname = new StringBuilder();
				builderfullname.append(dbemp.getFirstname().trim());

				builderfullname.append("  " + dbemp.getMiddlename().trim());
				builderfullname.append(" " + dbemp.getLastname().trim());
				emprespo.setFullname(builderfullname.toString());
				emprespo.setApprovalComment(dbemp.getApprovalComment());

				rltivelist.add(emprespo);

			});

			return ResponseEntity.status(HttpStatus.OK).body(rltivelist);
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
		}
	}

	@Override
	public ResponseEntity<EmployeeRelativeRequest> updateEmployeeRelative(
			EmployeeRelativeRequest employeeRelativeRequest, int id) {

		if (hrmsEmployeeRelativeRepository.existsById(id)) {
			if (!hrmsRelativeCategoryRepository.findById(employeeRelativeRequest.getRelativecategoryid()).get()
					.getName().toLowerCase().contains("kin")) {

				if (hrmsRelativeCategoryRepository.existsById(employeeRelativeRequest.getRelativecategoryid())
						&& hrmsLocationCountryRepository.existsById(employeeRelativeRequest.getCountryofbirthid())
						&& hrmsLocationCountryRepository.existsById(employeeRelativeRequest.getCountryofresidenceid())
						&& hrmsNationalityRepository.existsById(employeeRelativeRequest.getNationalityid())) {

					// set attachment

					if (employeeRelativeRequest.getUri() == null) {

						// set empl relative

						HrmsEmployeeRelative employeeRelative = hrmsEmployeeRelativeRepository.findById(id).get();

						employeeRelative.setApproved(0);

						employeeRelative.setAddress(employeeRelativeRequest.getAddress());
						employeeRelative.setAddresspermanent(employeeRelativeRequest.getAddresspermanent());
						employeeRelative.setCountryofbirthid(employeeRelativeRequest.getCountryofbirthid());
						employeeRelative.setCountryofresidenceid(employeeRelativeRequest.getCountryofresidenceid());
						employeeRelative.setDob(employeeRelativeRequest.getDob());

						employeeRelative.setFirstname(employeeRelativeRequest.getFirstname());
						employeeRelative.setMiddlename(employeeRelativeRequest.getMiddlename());
						employeeRelative.setLastname(employeeRelativeRequest.getLastname());
						employeeRelative.setNationalityid(employeeRelativeRequest.getNationalityid());
						employeeRelative.setPhoneprimary(employeeRelativeRequest.getPhoneprimary());
						employeeRelative.setPhonesecondary(employeeRelativeRequest.getPhonesecondary());
						employeeRelative.setRelativecategoryid(employeeRelativeRequest.getRelativecategoryid());
						employeeRelative.setDate_updated(LocalDateTime.now());

						hrmsEmployeeRelativeRepository.save(employeeRelative).getId();

						// add emp relative attachment

						HrmsEmployeeRelativeAttachment hrmsEmployeeRelativeAttachment = hrmsEmployeeRelativeAttachmentRepository
								.findByRelativeid(id);
						hrmsEmployeeRelativeAttachment.setApproved(0);
						hrmsEmployeeRelativeAttachment.setDate_updated(LocalDateTime.now());

						hrmsEmployeeRelativeAttachmentRepository.save(hrmsEmployeeRelativeAttachment);

					} else { // we have something on attachment URI
						if (employeeRelativeRequest.getAttachmentid() != 0) { // checking if this is the first time
																				// attachment insertion or not

							HrmsAttachment hrmsAttachment = hrmsAttachmentRepository
									.findById(employeeRelativeRequest.getAttachmentid()).get();

							hrmsAttachment.setApproved(0);
							hrmsAttachment.setAttachmenttypeid(employeeRelativeRequest.getAttachmenttypeid());
							hrmsAttachment.setDescription(employeeRelativeRequest.getAttachmentdescription());
							hrmsAttachment.setName(employeeRelativeRequest.getAttachmentname());
							hrmsAttachment.setAttachmenttypeid(employeeRelativeRequest.getAttachmenttypeid());
							hrmsAttachment.setUri(employeeRelativeRequest.getUri());
							hrmsAttachment.setDate_updated(LocalDateTime.now());

							hrmsAttachmentRepository.save(hrmsAttachment);

							// set empl relative

							HrmsEmployeeRelative employeeRelative = hrmsEmployeeRelativeRepository.findById(id).get();

							employeeRelative.setApproved(0);

							employeeRelative.setAddress(employeeRelativeRequest.getAddress());
							employeeRelative.setAddresspermanent(employeeRelativeRequest.getAddresspermanent());
							employeeRelative.setCountryofbirthid(employeeRelativeRequest.getCountryofbirthid());
							employeeRelative.setCountryofresidenceid(employeeRelativeRequest.getCountryofresidenceid());
							employeeRelative.setDob(employeeRelativeRequest.getDob());

							employeeRelative.setFirstname(employeeRelativeRequest.getFirstname());
							employeeRelative.setMiddlename(employeeRelativeRequest.getMiddlename());
							employeeRelative.setLastname(employeeRelativeRequest.getLastname());
							employeeRelative.setNationalityid(employeeRelativeRequest.getNationalityid());
							employeeRelative.setPhoneprimary(employeeRelativeRequest.getPhoneprimary());
							employeeRelative.setPhonesecondary(employeeRelativeRequest.getPhonesecondary());
							employeeRelative.setRelativecategoryid(employeeRelativeRequest.getRelativecategoryid());
							employeeRelative.setDate_updated(LocalDateTime.now());

							hrmsEmployeeRelativeRepository.save(employeeRelative).getId();

							// add emp relative attachment

							HrmsEmployeeRelativeAttachment hrmsEmployeeRelativeAttachment = hrmsEmployeeRelativeAttachmentRepository
									.findByRelativeid(id);
							hrmsEmployeeRelativeAttachment.setApproved(0);
							hrmsEmployeeRelativeAttachment.setDate_updated(LocalDateTime.now());

							hrmsEmployeeRelativeAttachmentRepository.save(hrmsEmployeeRelativeAttachment);
						} else { // for first time insertion of attachment upon update

							HrmsAttachment hrmsAttachment = new HrmsAttachment();

							hrmsAttachment.setApproved(0);
							hrmsAttachment.setAttachmenttypeid(employeeRelativeRequest.getAttachmenttypeid());
							hrmsAttachment.setDescription(employeeRelativeRequest.getAttachmentdescription());
							hrmsAttachment.setName(employeeRelativeRequest.getAttachmentname());
							hrmsAttachment.setAttachmenttypeid(employeeRelativeRequest.getAttachmenttypeid());
							hrmsAttachment.setUri(employeeRelativeRequest.getUri());
							hrmsAttachment.setDate_updated(LocalDateTime.now());
							hrmsAttachment.setUnique_id(UUID.randomUUID());

							int atid = hrmsAttachmentRepository.save(hrmsAttachment).getId();

							// set empl relative

							HrmsEmployeeRelative employeeRelative = hrmsEmployeeRelativeRepository.findById(id).get();

							employeeRelative.setApproved(0);

							employeeRelative.setAddress(employeeRelativeRequest.getAddress());
							employeeRelative.setAddresspermanent(employeeRelativeRequest.getAddresspermanent());
							employeeRelative.setCountryofbirthid(employeeRelativeRequest.getCountryofbirthid());
							employeeRelative.setCountryofresidenceid(employeeRelativeRequest.getCountryofresidenceid());

							employeeRelative.setFirstname(employeeRelativeRequest.getFirstname());
							employeeRelative.setMiddlename(employeeRelativeRequest.getMiddlename());
							employeeRelative.setLastname(employeeRelativeRequest.getLastname());
							employeeRelative.setNationalityid(employeeRelativeRequest.getNationalityid());
							employeeRelative.setPhoneprimary(employeeRelativeRequest.getPhoneprimary());
							employeeRelative.setPhonesecondary(employeeRelativeRequest.getPhonesecondary());
							employeeRelative.setRelativecategoryid(employeeRelativeRequest.getRelativecategoryid());
							employeeRelative.setDob(employeeRelativeRequest.getDob());
							employeeRelative.setDate_updated(LocalDateTime.now());

							int relativeid = hrmsEmployeeRelativeRepository.save(employeeRelative).getId();

							// add emp relative attachment

							HrmsEmployeeRelativeAttachment hrmsEmployeeRelativeAttachment = hrmsEmployeeRelativeAttachmentRepository
									.findByRelativeid(id);
							hrmsEmployeeRelativeAttachment.setApproved(0);
							hrmsEmployeeRelativeAttachment.setDate_updated(LocalDateTime.now());
							hrmsEmployeeRelativeAttachment.setRelativeid(relativeid);
							hrmsEmployeeRelativeAttachment.setAttachmentid(atid);
							hrmsEmployeeRelativeAttachment.setActive(1);

							hrmsEmployeeRelativeAttachmentRepository.save(hrmsEmployeeRelativeAttachment);

						}
					}

					return ResponseEntity.status(HttpStatus.OK).body(employeeRelativeRequest);

				} else {
					return ResponseEntity.status(HttpStatus.PRECONDITION_FAILED).body(null);
				}
			} else {

				HrmsEmployeeRelative employeeRelative = hrmsEmployeeRelativeRepository.findById(id).get();

				employeeRelative.setApproved(0);

				employeeRelative.setAddress(employeeRelativeRequest.getAddress());
				employeeRelative.setAddresspermanent(employeeRelativeRequest.getAddresspermanent());
				// employeeRelative.setCountryofbirthid(employeeRelativeRequest.getCountryofbirthid());
				// employeeRelative.setCountryofresidenceid(employeeRelativeRequest.getCountryofresidenceid());

				employeeRelative.setFirstname(employeeRelativeRequest.getFirstname());
				employeeRelative.setMiddlename(employeeRelativeRequest.getMiddlename());
				employeeRelative.setLastname(employeeRelativeRequest.getLastname());
				// employeeRelative.setNationalityid(employeeRelativeRequest.getNationalityid());
				employeeRelative.setPhoneprimary(employeeRelativeRequest.getPhoneprimary());
				employeeRelative.setPhonesecondary(employeeRelativeRequest.getPhonesecondary());
				employeeRelative.setRelativecategoryid(employeeRelativeRequest.getRelativecategoryid());
				employeeRelative.setDate_updated(LocalDateTime.now());

				hrmsEmployeeRelativeRepository.save(employeeRelative).getId();

				// add emp relative attachment

				HrmsEmployeeRelativeAttachment hrmsEmployeeRelativeAttachment = hrmsEmployeeRelativeAttachmentRepository
						.findByRelativeid(id);
				hrmsEmployeeRelativeAttachment.setApproved(0);
				hrmsEmployeeRelativeAttachment.setDate_updated(LocalDateTime.now());

				hrmsEmployeeRelativeAttachmentRepository.save(hrmsEmployeeRelativeAttachment);

				return ResponseEntity.status(HttpStatus.OK).body(employeeRelativeRequest);
			}

		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(employeeRelativeRequest);
		}
	}

	@Override
	public ResponseEntity<?> deleteEmployeeRelative(int id) {

		if (hrmsEmployeeRelativeRepository.existsByIdAndActive(id, 1)) {

			// set emp relative
			HrmsEmployeeRelative hrmsEmployeeRelative = hrmsEmployeeRelativeRepository.findById(id).get();
			hrmsEmployeeRelative.setActive(0);
			hrmsEmployeeRelative.setDate_updated(LocalDateTime.now());

			hrmsEmployeeRelativeRepository.save(hrmsEmployeeRelative);

			// set emprelative attachment
			HrmsEmployeeRelativeAttachment hrmsEmployeeRelativeAttachment = hrmsEmployeeRelativeAttachmentRepository
					.findByRelativeid(id);
			hrmsEmployeeRelativeAttachment.setActive(0);
			hrmsEmployeeRelativeAttachment.setDate_updated(LocalDateTime.now());
			int attachmentid = hrmsEmployeeRelativeAttachmentRepository.save(hrmsEmployeeRelativeAttachment)
					.getAttachmentid();

			// set attachment
			if (attachmentid != 0) {
				HrmsAttachment hrmsAttachment = hrmsAttachmentRepository.findById(attachmentid).get();
				hrmsAttachment.setActive(0);
				hrmsAttachment.setDate_updated(LocalDateTime.now());

				hrmsAttachmentRepository.save(hrmsAttachment);
			}

			return ResponseEntity.status(HttpStatus.OK).body(null);
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
		}
	}

	@Override
	public ResponseEntity<List<EmployeeRelativeResponse>> listEmployeeRelatives() {
		String pattern = "yyyy-MM-dd";
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
		List<EmployeeRelativeResponse> rltivelist = new ArrayList<>();
		List<HrmsEmployeeRelative> relativelist = hrmsEmployeeRelativeRepository.findByActiveOrderByEmployeeid(1);

		relativelist.forEach(dbemp -> {

			EmployeeRelativeResponse emprespo = new EmployeeRelativeResponse();

			emprespo.setEmployeeid(dbemp.getEmployeeid());
			if (dbemp.getDob() != null) {
				emprespo.setDob(simpleDateFormat.format(dbemp.getDob()));
			}

			HrmsEmployee hrmsEmployee = hrmsEmployeeRepository.findById(dbemp.getEmployeeid()).get();

			StringBuilder fullname = new StringBuilder();
			fullname.append(hrmsEmployee.getFirstName().trim());
			fullname.append("  " + hrmsEmployee.getMiddleName().trim());
			fullname.append(" " + hrmsEmployee.getLastName().trim());

			emprespo.setEmployeename(fullname.toString());

			emprespo.setId(dbemp.getId());
			emprespo.setMobileNo(dbemp.getPhoneprimary());
			emprespo.setRelativecategoryid(dbemp.getRelativecategoryid());
			emprespo.setRelativecategoryname(
					hrmsRelativeCategoryRepository.findById(dbemp.getRelativecategoryid()).get().getName());

			emprespo.setFirstname(dbemp.getFirstname());
			emprespo.setMiddlename(dbemp.getMiddlename());
			emprespo.setLastname(dbemp.getLastname());
			emprespo.setActive(dbemp.getActive());
			emprespo.setApproved(dbemp.getApproved());
			int atachmentid = hrmsEmployeeRelativeAttachmentRepository.findByRelativeid(dbemp.getId())
					.getAttachmentid();
			if (atachmentid != 0) {
				emprespo.setAttachmentdescription(
						hrmsAttachmentRepository.findById(atachmentid).get().getDescription());
				emprespo.setActive(hrmsAttachmentRepository.findById(atachmentid).get().getActive());
				emprespo.setApproved(hrmsAttachmentRepository.findById(atachmentid).get().getApproved());
				emprespo.setAttachmentid(atachmentid);
				emprespo.setAttachmentname(hrmsAttachmentRepository.findById(atachmentid).get().getName());
				int attypeid = hrmsAttachmentRepository.findById(atachmentid).get().getAttachmenttypeid();
				emprespo.setAttachmenttypeid(attypeid);
				emprespo.setAttachmenttypename(hrmsAttachmentTypeRepository.findById(attypeid).get().getName());
				emprespo.setUri(hrmsAttachmentRepository.findById(atachmentid).get().getUri());
			}

			if (dbemp.getCountryofbirthid() != 0) {
				emprespo.setCountryofbirth(
						hrmsLocationCountryRepository.findById(dbemp.getCountryofbirthid()).get().getName());
			}
			emprespo.setCountryofbirthid(dbemp.getCountryofbirthid());
			if (dbemp.getCountryofresidenceid() != 0) {
				emprespo.setCountryofresidence(
						hrmsLocationCountryRepository.findById(dbemp.getCountryofresidenceid()).get().getName());
			}
			emprespo.setCountryofresidenceid(dbemp.getCountryofresidenceid());
			if (dbemp.getNationalityid() != 0) {
				emprespo.setNationality(hrmsNationalityRepository.findById(dbemp.getNationalityid()).get().getName());
			}
			emprespo.setNationalityid(dbemp.getNationalityid());

			StringBuilder builderfullname = new StringBuilder();
			builderfullname.append(dbemp.getFirstname().trim());

			builderfullname.append("  " + dbemp.getMiddlename().trim());
			builderfullname.append(" " + dbemp.getLastname().trim());
			emprespo.setFullname(builderfullname.toString());
			emprespo.setApprovalComment(dbemp.getApprovalComment());

			rltivelist.add(emprespo);

		});

		return ResponseEntity.status(HttpStatus.OK).body(rltivelist);
	}

	@Override
	public ResponseEntity<EmployeeRelativeResponse> getEmployeeRelativeById(int id) {

		String pattern = "yyyy-MM-dd";
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
		if (hrmsEmployeeRelativeRepository.existsByIdAndActive(id, 1)) {

			HrmsEmployeeRelative dbemp = hrmsEmployeeRelativeRepository.findById(id).get();

			EmployeeRelativeResponse emprespo = new EmployeeRelativeResponse();

			emprespo.setEmployeeid(dbemp.getEmployeeid());
			emprespo.setId(dbemp.getId());
			emprespo.setMobileNo(dbemp.getPhoneprimary());
			emprespo.setRelativecategoryid(dbemp.getRelativecategoryid());
			emprespo.setFirstname(dbemp.getFirstname());
			emprespo.setMiddlename(dbemp.getMiddlename());
			emprespo.setLastname(dbemp.getLastname());
			if (dbemp.getDob() != null) {
				emprespo.setDob(simpleDateFormat.format(dbemp.getDob()));
			}

			emprespo.setRelativecategoryname(
					hrmsRelativeCategoryRepository.findById(dbemp.getRelativecategoryid()).get().getName());

			emprespo.setActive(dbemp.getActive());
			emprespo.setApproved(dbemp.getApproved());
			int atachmentid = hrmsEmployeeRelativeAttachmentRepository.findByRelativeid(dbemp.getId())
					.getAttachmentid();
			if (atachmentid != 0) {
				emprespo.setAttachmentdescription(
						hrmsAttachmentRepository.findById(atachmentid).get().getDescription());
				emprespo.setActive(hrmsAttachmentRepository.findById(atachmentid).get().getActive());
				emprespo.setApproved(hrmsAttachmentRepository.findById(atachmentid).get().getApproved());
				emprespo.setAttachmentid(atachmentid);
				emprespo.setAttachmentname(hrmsAttachmentRepository.findById(atachmentid).get().getName());
				int attypeid = hrmsAttachmentRepository.findById(atachmentid).get().getAttachmenttypeid();
				emprespo.setAttachmenttypeid(attypeid);
				emprespo.setAttachmenttypename(hrmsAttachmentTypeRepository.findById(attypeid).get().getName());
				emprespo.setUri(hrmsAttachmentRepository.findById(atachmentid).get().getUri());
			}
			StringBuilder builderfullname = new StringBuilder();
			builderfullname.append(dbemp.getFirstname().trim());

			builderfullname.append("  " + dbemp.getMiddlename().trim());
			builderfullname.append(" " + dbemp.getLastname().trim());
			emprespo.setFullname(builderfullname.toString());

			emprespo.setAddress(dbemp.getAddress());
			emprespo.setAddresspermanent(dbemp.getAddresspermanent());
			if (dbemp.getCountryofbirthid() != 0) {
				emprespo.setCountryofbirth(
						hrmsLocationCountryRepository.findById(dbemp.getCountryofbirthid()).get().getName());
			}
			emprespo.setCountryofbirthid(dbemp.getCountryofbirthid());
			if (dbemp.getCountryofresidenceid() != 0) {
				emprespo.setCountryofresidence(
						hrmsLocationCountryRepository.findById(dbemp.getCountryofresidenceid()).get().getName());
			}
			emprespo.setCountryofresidenceid(dbemp.getCountryofresidenceid());
			if (dbemp.getNationalityid() != 0) {
				emprespo.setNationality(hrmsNationalityRepository.findById(dbemp.getNationalityid()).get().getName());
			}
			emprespo.setNationalityid(dbemp.getNationalityid());
			emprespo.setApprovalComment(dbemp.getApprovalComment());

			return ResponseEntity.status(HttpStatus.OK).body(emprespo);
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
		}
	}

	@Override
	public ResponseEntity<List<EmployeeRelativeResponse>> listEmployeeRelativesNonApproved() {

		String pattern = "yyyy-MM-dd";
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);

		List<EmployeeRelativeResponse> rltivelist = new ArrayList<>();
		List<HrmsEmployeeRelative> relativelist = hrmsEmployeeRelativeRepository
				.findByActiveAndApprovedOrderByEmployeeid(1, 0);

		relativelist.forEach(dbemp -> {

			EmployeeRelativeResponse emprespo = new EmployeeRelativeResponse();

			emprespo.setEmployeeid(dbemp.getEmployeeid());

			if (dbemp.getDob() != null) {
				emprespo.setDob(simpleDateFormat.format(dbemp.getDob()));
			}

			HrmsEmployee hrmsEmployee = hrmsEmployeeRepository.findById(dbemp.getEmployeeid()).get();

			StringBuilder fullname = new StringBuilder();
			fullname.append(hrmsEmployee.getFirstName().trim());
			fullname.append("  " + hrmsEmployee.getMiddleName().trim());
			fullname.append(" " + hrmsEmployee.getLastName().trim());

			emprespo.setEmployeename(fullname.toString());

			emprespo.setId(dbemp.getId());
			emprespo.setMobileNo(dbemp.getPhoneprimary());
			emprespo.setRelativecategoryid(dbemp.getRelativecategoryid());
			emprespo.setRelativecategoryname(
					hrmsRelativeCategoryRepository.findById(dbemp.getRelativecategoryid()).get().getName());

			emprespo.setFirstname(dbemp.getFirstname());
			emprespo.setMiddlename(dbemp.getMiddlename());
			emprespo.setLastname(dbemp.getLastname());
			emprespo.setActive(dbemp.getActive());
			emprespo.setApproved(dbemp.getApproved());
			int atachmentid = hrmsEmployeeRelativeAttachmentRepository.findByRelativeid(dbemp.getId())
					.getAttachmentid();
			if (atachmentid != 0) {
				emprespo.setAttachmentdescription(
						hrmsAttachmentRepository.findById(atachmentid).get().getDescription());
				emprespo.setActive(hrmsAttachmentRepository.findById(atachmentid).get().getActive());
				emprespo.setApproved(hrmsAttachmentRepository.findById(atachmentid).get().getApproved());
				emprespo.setAttachmentid(atachmentid);
				emprespo.setAttachmentname(hrmsAttachmentRepository.findById(atachmentid).get().getName());
				int attypeid = hrmsAttachmentRepository.findById(atachmentid).get().getAttachmenttypeid();
				emprespo.setAttachmenttypeid(attypeid);
				emprespo.setAttachmenttypename(hrmsAttachmentTypeRepository.findById(attypeid).get().getName());
				emprespo.setUri(hrmsAttachmentRepository.findById(atachmentid).get().getUri());
			}

			if (dbemp.getCountryofbirthid() != 0) {
				emprespo.setCountryofbirth(
						hrmsLocationCountryRepository.findById(dbemp.getCountryofbirthid()).get().getName());
			}
			emprespo.setCountryofbirthid(dbemp.getCountryofbirthid());
			if (dbemp.getCountryofresidenceid() != 0) {
				emprespo.setCountryofresidence(
						hrmsLocationCountryRepository.findById(dbemp.getCountryofresidenceid()).get().getName());
			}
			emprespo.setCountryofresidenceid(dbemp.getCountryofresidenceid());
			if (dbemp.getNationalityid() != 0) {
				emprespo.setNationality(hrmsNationalityRepository.findById(dbemp.getNationalityid()).get().getName());
			}
			emprespo.setNationalityid(dbemp.getNationalityid());

			StringBuilder builderfullname = new StringBuilder();
			builderfullname.append(dbemp.getFirstname().trim());

			builderfullname.append("  " + dbemp.getMiddlename().trim());
			builderfullname.append(" " + dbemp.getLastname().trim());
			emprespo.setFullname(builderfullname.toString());
			emprespo.setApprovalComment(dbemp.getApprovalComment());

			rltivelist.add(emprespo);

		});

		return ResponseEntity.status(HttpStatus.OK).body(rltivelist);
	}

	@Override
	public ResponseEntity<?> approveOrRejectEmployeeRelative(EmployeeApprovalComment employeeApprovalComment, int id,
			int status) {
		if (hrmsEmployeeRelativeRepository.existsByIdAndActive(id, 1) && (status == 1 || status == -1)) {

			// approve employee relative table below
			HrmsEmployeeRelative hrmsEmployeeRelative = hrmsEmployeeRelativeRepository.findById(id).get();
			hrmsEmployeeRelative.setApproved(status);
			hrmsEmployeeRelative.setDate_updated(LocalDateTime.now());
			hrmsEmployeeRelative.setApprovalComment(employeeApprovalComment.getComment());
			hrmsEmployeeRelative.setApproverEmployeeid(employeeApprovalComment.getApproverEmployeeid());
			hrmsEmployeeRelativeRepository.saveAndFlush(hrmsEmployeeRelative);

			// approve employee relative attachment

			HrmsEmployeeRelativeAttachment hrmsEmployeeRelativeAttachment = hrmsEmployeeRelativeAttachmentRepository
					.findByRelativeid(id);
			hrmsEmployeeRelativeAttachment.setApproved(status);
			hrmsEmployeeRelativeAttachment.setDate_updated(LocalDateTime.now());
			hrmsEmployeeRelativeAttachmentRepository.saveAndFlush(hrmsEmployeeRelativeAttachment);

			// now approve attachment if does exist for this id of relative
			// employee relative might miss an attachment if he/she is a next of kin

			if (hrmsEmployeeRelativeAttachment.getAttachmentid() != 0) {
				HrmsAttachment hrmsAttachment = hrmsAttachmentRepository
						.findById(hrmsEmployeeRelativeAttachment.getAttachmentid()).get();
				hrmsAttachment.setApproved(status);
				hrmsAttachment.setDate_updated(LocalDateTime.now());
				hrmsAttachmentRepository.saveAndFlush(hrmsAttachment);
			}

			// send notification
			String messages = "";

			HrmsEmployee hrmsEmployee = hrmsEmployeeRepository.findById(hrmsEmployeeRelative.getEmployeeid()).get();

			StringBuilder fullname = new StringBuilder();
			fullname.append(hrmsEmployee.getFirstName().trim());
			fullname.append("  " + hrmsEmployee.getMiddleName().trim());
			fullname.append(" " + hrmsEmployee.getLastName().trim());
			String username = fullname.toString();
			String usermail = hrmsEmployee.getEmail();
			if (status == -1) {
				messages = " Your Relative entry for   " + hrmsEmployeeRelative.getFirstname() + " "
						+ hrmsEmployeeRelative.getLastname() + " has been rejected,Kindly edit and re-submit again "
						+ "  Comments for rejection is  " + employeeApprovalComment.getComment();
				sendEmail.sendmailNotification(username, usermail, messages);
			}

			return ResponseEntity.status(HttpStatus.OK).body(id);
		} else {
			return ResponseEntity.status(HttpStatus.PRECONDITION_FAILED).body(id);
		}
	}

}
