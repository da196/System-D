package com.Hrms.Employee.Service;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.Hrms.Employee.DTO.EmployeeEducationResponseV2;
import com.Hrms.Employee.DTO.EmployeeForVideoConference;
import com.Hrms.Employee.DTO.EmployeeGeneralRequest;
import com.Hrms.Employee.DTO.EmployeeRelativeResponse;
import com.Hrms.Employee.DTO.EmployeeResponse;
import com.Hrms.Employee.DTO.EmployeeResponseDetailed;
import com.Hrms.Employee.DTO.EmployeeResponseEdms;
import com.Hrms.Employee.DTO.HrmsEmployeeAddressContactResponse;
import com.Hrms.Employee.DTO.HrmsEmployeeCertificationResponse;
import com.Hrms.Employee.DTO.HrmsEmployeeEmploymentHistoryResponseByEmpId;
import com.Hrms.Employee.DTO.HrmsEmployeeShortCoursesR;
import com.Hrms.Employee.Entity.HrmsContact;
import com.Hrms.Employee.Entity.HrmsEmployee;
import com.Hrms.Employee.Entity.HrmsEmployeeAddress;
import com.Hrms.Employee.Entity.HrmsEmployeeCertification;
import com.Hrms.Employee.Entity.HrmsEmployeeContact;
import com.Hrms.Employee.Entity.HrmsEmployeeEducation;
import com.Hrms.Employee.Entity.HrmsEmployeeEmploymentHistory;
import com.Hrms.Employee.Entity.HrmsEmployeeEmploymentStatus;
import com.Hrms.Employee.Entity.HrmsEmployeeEmploymentStatusReason;
import com.Hrms.Employee.Entity.HrmsEmployeeOffice;
import com.Hrms.Employee.Entity.HrmsEmployeeRead;
import com.Hrms.Employee.Entity.HrmsEmployeeRelative;
import com.Hrms.Employee.Entity.HrmsEmployeeShortCourses;
import com.Hrms.Employee.Entity.HrmsEmployeeUnit;
import com.Hrms.Employee.Entity.HrmsLocationAddress;
import com.Hrms.Employee.Entity.HrmsUser;
import com.Hrms.Employee.Repository.HrmsAttachmentRepository;
import com.Hrms.Employee.Repository.HrmsAttachmentTypeRepository;
import com.Hrms.Employee.Repository.HrmsCertificationCategoryRepository;
import com.Hrms.Employee.Repository.HrmsContactRepository;
import com.Hrms.Employee.Repository.HrmsContactTypeRepository;
import com.Hrms.Employee.Repository.HrmsDesignationRepository;
import com.Hrms.Employee.Repository.HrmsEducationcourseRepository;
import com.Hrms.Employee.Repository.HrmsEducationinstitutionRepository;
import com.Hrms.Employee.Repository.HrmsEducationlevelRepository;
import com.Hrms.Employee.Repository.HrmsEmployeeAddressRepository;
import com.Hrms.Employee.Repository.HrmsEmployeeCertificationAttachmentRepository;
import com.Hrms.Employee.Repository.HrmsEmployeeCertificationRepository;
import com.Hrms.Employee.Repository.HrmsEmployeeContactRepository;
import com.Hrms.Employee.Repository.HrmsEmployeeEducationAttachmentRepository;
import com.Hrms.Employee.Repository.HrmsEmployeeEducationRepository;
import com.Hrms.Employee.Repository.HrmsEmployeeEmploymentHistoryRepository;
import com.Hrms.Employee.Repository.HrmsEmployeeEmploymentStatusReasonRepository;
import com.Hrms.Employee.Repository.HrmsEmployeeEmploymentStatusRepository;
import com.Hrms.Employee.Repository.HrmsEmployeeOfficeRepository;
import com.Hrms.Employee.Repository.HrmsEmployeeReadRepository;
import com.Hrms.Employee.Repository.HrmsEmployeeRelativeAttachmentRepository;
import com.Hrms.Employee.Repository.HrmsEmployeeRelativeRepository;
import com.Hrms.Employee.Repository.HrmsEmployeeRepository;
import com.Hrms.Employee.Repository.HrmsEmployeeShortCourseAttachmentRepository;
import com.Hrms.Employee.Repository.HrmsEmployeeShortCoursesRepository;
import com.Hrms.Employee.Repository.HrmsEmployeeUnitRepository;
import com.Hrms.Employee.Repository.HrmsEmploymentStatusReasonRepository;
import com.Hrms.Employee.Repository.HrmsLocationAddressRepository;
import com.Hrms.Employee.Repository.HrmsLocationAddressTypeRepository;
import com.Hrms.Employee.Repository.HrmsLocationCityRepository;
import com.Hrms.Employee.Repository.HrmsLocationCountryRepository;
import com.Hrms.Employee.Repository.HrmsNationalityRepository;
import com.Hrms.Employee.Repository.HrmsOrganisationOfficeCategoryRepository;
import com.Hrms.Employee.Repository.HrmsOrganisationOfficeRepository;
import com.Hrms.Employee.Repository.HrmsOrginisationUnitRepository;
import com.Hrms.Employee.Repository.HrmsRelativeCategoryRepository;
import com.Hrms.Employee.Repository.HrmsSalaryScaleRepository;
import com.Hrms.Employee.Repository.HrmsSalaryscalenotchRepository;
import com.Hrms.Employee.Repository.HrmsSalarystructureRepository;
import com.Hrms.Employee.Repository.HrmsUserRepository;
import com.Hrms.Employee.Repository.HrmsemployeesalaryRepository;

@Service
public class HrmsEmployeeServiceImpl implements HrmsEmployeeService {

	@Autowired
	private HrmsEmployeeRepository hrmsEmployeeRepository;

	@Autowired

	private HrmsEmploymentStatusReasonRepository hrmsEmploymentStatusReasonRepository;

	@Autowired
	private HrmsEmployeeReadRepository hrmsEmployeeReadRepository;

	@Autowired
	private HrmsDesignationRepository hrmsDesignationRepository;

	@Autowired
	private HrmsEmployeeEmploymentStatusReasonRepository hrmsEmployeeEmploymentStatusReasonRepository;

	@Autowired
	private HrmsNationalityRepository hrmsNationalityRepository;

	@Autowired
	private HrmsOrginisationUnitRepository hrmsOrginisationUnitRepository;

	@Autowired
	private HrmsemployeesalaryRepository hrmsemployeesalaryRepository;

	@Autowired
	private HrmsSalarystructureRepository hrmsSalarystructureRepository;

	@Autowired
	private HrmsSalaryScaleRepository hrmsSalaryScaleRepository;

	@Autowired
	private HrmsLocationCityRepository hrmsLocationCityRepository;

	@Autowired
	private HrmsUserRepository hrmsUserRepository;

	@Autowired
	private HrmsEmployeeUnitRepository hrmsEmployeeUnitRepository;

	@Autowired
	private HrmsContactRepository hrmsContactRepository;

	@Autowired
	private HrmsSalaryscalenotchRepository hrmsSalaryscalenotchRepository;

	@Autowired
	private HrmsContactTypeRepository hrmsContactTypeRepository;

	@Autowired
	private HrmsLocationAddressRepository hrmsLocationAddressRepository;

	@Autowired
	private HrmsEmployeeContactRepository hrmsEmployeeContactRepository;

	@Autowired
	private HrmsEmployeeAddressRepository hrmsEmployeeAddressRepository;

	@Autowired
	private HrmsEmployeeEducationRepository hrmsEmployeeEducationRepository;

	@Autowired
	private HrmsEducationinstitutionRepository hrmsEducationinstitutionRepository;

	@Autowired
	private HrmsAttachmentRepository hrmsAttachmentRepository;

	@Autowired
	private HrmsEmployeeEducationAttachmentRepository hrmsEmployeeEducationAttachmentRepository;

	@Autowired
	private HrmsEducationcourseRepository hrmsEducationcourseRepository;

	@Autowired
	private HrmsEducationlevelRepository hrmsEducationlevelRepository;

	@Autowired
	private HrmsLocationCountryRepository hrmsLocationCountryRepository;

	@Autowired
	private HrmsAttachmentTypeRepository hrmsAttachmentTypeRepository;

	@Autowired
	private HrmsEmployeeShortCoursesRepository hrmsEmployeeShortCoursesRepository;

	@Autowired
	private HrmsEmployeeShortCourseAttachmentRepository hrmsEmployeeShortCourseAttachmentRepository;

	@Autowired
	private HrmsEmployeeRelativeRepository hrmsEmployeeRelativeRepository;

	@Autowired
	private HrmsEmployeeRelativeAttachmentRepository hrmsEmployeeRelativeAttachmentRepository;

	@Autowired
	private HrmsRelativeCategoryRepository hrmsRelativeCategoryRepository;

	@Autowired
	private HrmsLocationAddressTypeRepository hrmsLocationAddressTypeRepository;

	@Autowired

	private HrmsEmployeeEmploymentHistoryRepository hrmsEmployeeEmploymentHistoryRepository;

	@Autowired

	private HrmsOrganisationOfficeCategoryRepository hrmsOrganisationOfficeCategoryRepository;

	@Autowired

	private HrmsOrganisationOfficeRepository hrmsOrganisationOfficeRepository;

	@Autowired
	private HrmsEmployeeOfficeRepository hrmsEmployeeOfficeRepository;

	@Autowired
	private HrmsEmployeeCertificationRepository hrmsEmployeeCertificationRepository;

	@Autowired
	private HrmsEmployeeCertificationAttachmentRepository hrmsEmployeeCertificationAttachmentRepository;

	@Autowired
	private HrmsCertificationCategoryRepository hrmsCertificationCategoryRepository;

	@Autowired
	private HrmsEmployeeEmploymentStatusRepository hrmsEmployeeEmploymentStatusRepository;

	@Override
	public ResponseEntity<HrmsEmployee> save(HrmsEmployee hrmsEmployee) {

		if ((hrmsNationalityRepository.existsById(hrmsEmployee.getNationalityId())
				&& hrmsEmployeeRepository
						.existsByDesignationIdAndEmploymentstatusid(hrmsEmployee.getSupervisordesignationid(), 2)

				&& hrmsOrganisationOfficeRepository.existsByIdAndActive(hrmsEmployee.getDutystationid(), 1)
				&& hrmsOrginisationUnitRepository.existsById(hrmsEmployee.getUnitId())
				&& hrmsEmploymentStatusReasonRepository.existsByIdAndActive(hrmsEmployee.getEmploymentstatusreasonid(),
						1))) {
			if (hrmsEmployeeRepository.existsByEmail(hrmsEmployee.getEmail())
					|| hrmsEmployeeRepository.existsByCardNumber(hrmsEmployee.getCardNumber())
					|| hrmsEmployeeRepository.existsByFileNumber(hrmsEmployee.getFileNumber())) {
				return ResponseEntity.status(HttpStatus.ALREADY_REPORTED).body(null);

			} else {

				// insert in employee
				UUID uuid = UUID.randomUUID();
				hrmsEmployee.setUnique_id(uuid);
				hrmsEmployee.setActive(1);
				hrmsEmployee.setApproved(0);

				int supervisordesignationid = hrmsEmployee.getSupervisordesignationid();
				int supervisorid = hrmsEmployeeRepository
						.findByDesignationIdAndEmploymentstatusid(supervisordesignationid, 2).getId();
				hrmsEmployee.setSupervisorId(supervisorid);
				// set retirement date

				// SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
				// Date date = new Date();

				// Convert Date to Calendar
				Calendar c = Calendar.getInstance();
				c.setTime(hrmsEmployee.getDob());

				// Perform addition/subtraction
				c.add(Calendar.YEAR, 60);

				// Convert calendar back to Date
				Date retirementdate = c.getTime();

				hrmsEmployee.setDateofretirement(retirementdate);

				// hrmsEmployee.setDateofretirement(hrmsEmployee.getDateofretirement());
				// insert hrms user
				HrmsUser hrmsUser = new HrmsUser();
				hrmsUser.setActive(1);
				hrmsUser.setApproved(0);
				hrmsUser.setUnique_id(UUID.randomUUID());
				hrmsUser.setEmail(hrmsEmployee.getEmail());

				hrmsUserRepository.save(hrmsUser);

				int empid = hrmsEmployeeRepository.save(hrmsEmployee).getId();
				// deactivate the active employee employment status

				/*
				 * HrmsEmployeeEmploymentStatus hrmsEmployeeEmploymentStatus1 =
				 * hrmsEmployeeEmploymentStatusRepository .findByEmployeeidAndStatusid(empid,
				 * 1); hrmsEmployeeEmploymentStatus1.setDate_updated(LocalDateTime.now());
				 * hrmsEmployeeEmploymentStatus1.setDateend(hrmsEmployee.getDateofemployment());
				 * hrmsEmployeeEmploymentStatus1.setStatusid(0);
				 * hrmsEmployeeEmploymentStatusRepository.saveAndFlush(
				 * hrmsEmployeeEmploymentStatus1);
				 */

				// insert employee employment status
				HrmsEmployeeEmploymentStatus hrmsEmployeeEmploymentStatus = new HrmsEmployeeEmploymentStatus();
				hrmsEmployeeEmploymentStatus.setActive(1);
				hrmsEmployeeEmploymentStatus.setApproved(0);
				hrmsEmployeeEmploymentStatus.setDate_created(LocalDateTime.now());
				hrmsEmployeeEmploymentStatus.setDatestart(hrmsEmployee.getDateofemployment());
				// hrmsEmployeeEmploymentStatus.setDescriptionstart(descriptionstart);
				hrmsEmployeeEmploymentStatus.setEmployeeid(empid);
				hrmsEmployeeEmploymentStatus.setStatusid(hrmsEmployee.getEmploymentstatusid());
				hrmsEmployeeEmploymentStatus.setUnique_id(UUID.randomUUID());

				hrmsEmployeeEmploymentStatusRepository.saveAndFlush(hrmsEmployeeEmploymentStatus);

				// insert employee organization office

				HrmsEmployeeOffice hrmsEmployeeOffice = new HrmsEmployeeOffice();
				hrmsEmployeeOffice.setActive(1);
				hrmsEmployeeOffice.setApproved(1);
				hrmsEmployeeOffice.setDatestart(LocalDateTime.now());

				hrmsEmployeeOffice.setEmployeeid(empid);
				hrmsEmployeeOffice.setOfficeid(hrmsEmployee.getDutystationid());
				hrmsEmployeeOffice.setUnique_id(UUID.randomUUID());

				hrmsEmployeeOfficeRepository.saveAndFlush(hrmsEmployeeOffice);

				// insert in employee unit
				HrmsEmployeeUnit hrmsEmployeeUnit = new HrmsEmployeeUnit();

				hrmsEmployeeUnit.setActive(1);
				hrmsEmployeeUnit.setApproved(0);
				hrmsEmployeeUnit.setDateStart(LocalDateTime.now());
				hrmsEmployeeUnit.setEmployeeid(empid);
				hrmsEmployeeUnit.setUnique_id(UUID.randomUUID());
				hrmsEmployeeUnit.setUnitId(hrmsEmployee.getUnitId());

				hrmsEmployeeUnitRepository.save(hrmsEmployeeUnit);

				// insert employee employment reason

				HrmsEmployeeEmploymentStatusReason hrmsEmployeeEmploymentStatusReason = new HrmsEmployeeEmploymentStatusReason();

				hrmsEmployeeEmploymentStatusReason.setActive(1);
				hrmsEmployeeEmploymentStatusReason.setApproved(1);
				hrmsEmployeeEmploymentStatusReason.setDate_created(LocalDateTime.now());
				hrmsEmployeeEmploymentStatusReason.setDescription("Reason for employment status");
				hrmsEmployeeEmploymentStatusReason.setEmployeeid(empid);
				hrmsEmployeeEmploymentStatusReason.setStatusreasonid(hrmsEmployee.getEmploymentstatusreasonid());
				hrmsEmployeeEmploymentStatusReason.setUnique_id(UUID.randomUUID());

				hrmsEmployeeEmploymentStatusReasonRepository.saveAndFlush(hrmsEmployeeEmploymentStatusReason);

				return ResponseEntity.status(HttpStatus.OK).body(hrmsEmployee);
			}
		} else {
			return ResponseEntity.status(HttpStatus.FAILED_DEPENDENCY).body(hrmsEmployee);
		}

	}

	@Override
	public ResponseEntity<EmployeeResponse> viewHrmsEmployee(int id) {
		if (hrmsEmployeeRepository.existsById(id)) {

			HrmsEmployeeRead dbemp = hrmsEmployeeReadRepository.findById(id).get();

			EmployeeResponse emprespo = new EmployeeResponse();
			String pattern = "yyyy-MM-dd";
			SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
			if (hrmsemployeesalaryRepository.existsByEmployeeidAndActive(id, 1)) {
				int salarystructureid = hrmsemployeesalaryRepository.findByEmployeeidAndActive(id, 1)
						.getSalarystructureId();

				if (hrmsSalarystructureRepository.existsById(salarystructureid)) {

					int salaryscaleid = hrmsSalarystructureRepository.findById(salarystructureid).get().getScaleId();

					if (hrmsSalaryScaleRepository.existsById(salaryscaleid)) {
						String salaryscale = hrmsSalaryScaleRepository.findById(salaryscaleid).get().getName();
						emprespo.setSalarystructure(salaryscale);

					}
				}
			}

			emprespo.setActive(dbemp.getActive());
			emprespo.setApproved(dbemp.getApproved());

			emprespo.setCardNumber(dbemp.getCardNumber());
			emprespo.setDate_created(dbemp.getDate_created());
			emprespo.setDate_updated(dbemp.getDate_updated());
			emprespo.setNationalId(dbemp.getNationalId());
			emprespo.setPassportNo(dbemp.getPassportNo());

			if (dbemp.getDutystationid() != 0) {
				emprespo.setDutystation(
						hrmsOrganisationOfficeRepository.findById(dbemp.getDutystationid()).get().getName());
				emprespo.setDutystationid(dbemp.getDutystationid());
				emprespo.setDutystationtypeid(
						hrmsOrganisationOfficeRepository.findById(dbemp.getDutystationid()).get().getOfficetypeid());
				emprespo.setDutystationtypename(hrmsOrganisationOfficeCategoryRepository.findById(
						hrmsOrganisationOfficeRepository.findById(dbemp.getDutystationid()).get().getOfficetypeid())
						.get().getName());
				emprespo.setDutystationcityid(
						hrmsOrganisationOfficeRepository.findById(dbemp.getDutystationid()).get().getCityid());
				emprespo.setDutystationcity(hrmsLocationCityRepository
						.findById(hrmsOrganisationOfficeRepository.findById(dbemp.getDutystationid()).get().getCityid())
						.get().getName());

			}
			emprespo.setDateofemployment(simpleDateFormat.format(dbemp.getDateofemployment()));

			emprespo.setDesignationId(dbemp.getDesignation().getId());
			emprespo.setDesignation(dbemp.getDesignation().getName());

			emprespo.setDob(simpleDateFormat.format(dbemp.getDob()));

			if (dbemp.getDateofretirement() != null) {
				emprespo.setDateofretirement(simpleDateFormat.format(dbemp.getDateofretirement()));
			} else {
				// Convert Date to Calendar
				Calendar c = Calendar.getInstance();
				c.setTime(dbemp.getDob());

				// Perform addition/subtraction
				c.add(Calendar.YEAR, 60);

				// Convert calendar back to Date
				Date retirementdate = c.getTime();

				emprespo.setDateofretirement(simpleDateFormat.format(retirementdate));

			}
			emprespo.setEmail(dbemp.getEmail());
			emprespo.setEmploymentcategoryId(dbemp.getEmploymentcategory().getId());
			emprespo.setEmploymentcategory(dbemp.getEmploymentcategory().getName());
			emprespo.setEmploymentstatusid(dbemp.getEmploymentStatus().getId());
			emprespo.setEmploymentstatus(dbemp.getEmploymentStatus().getName());
			emprespo.setFileNumber(dbemp.getFileNumber());
			emprespo.setFirstName(dbemp.getFirstName());

			emprespo.setGenderid(dbemp.getGender().getId());
			emprespo.setGender(dbemp.getGender().getName());
			emprespo.setId(dbemp.getId());
			emprespo.setIsprobation(dbemp.getIsprobation());
			emprespo.setIssupervisor(dbemp.getIssupervisor());
			emprespo.setLastName(dbemp.getLastName());

			emprespo.setMaritalstatusId(dbemp.getMaritalstatus().getId());
			emprespo.setMaritalstatus(dbemp.getMaritalstatus().getName());

			emprespo.setMiddleName(dbemp.getMiddleName());
			emprespo.setNationalityId(dbemp.getNationality().getId());
			emprespo.setNationality(dbemp.getNationality().getName());
			emprespo.setUnique_id(dbemp.getUnique_id());

			emprespo.setUnitId(dbemp.getOrgunit().getId());

			emprespo.setUnit(dbemp.getOrgunit().getName());

			emprespo.setSectionid(dbemp.getSectionid());
			if (dbemp.getSectionid() != 0 && hrmsOrginisationUnitRepository.existsById(dbemp.getSectionid())) {
				emprespo.setSection(hrmsOrginisationUnitRepository.findById(dbemp.getSectionid()).get().getName());
			}

			emprespo.setPicture(dbemp.getPicture());
			emprespo.setReligionId(dbemp.getReligion().getId());
			emprespo.setReligion(dbemp.getReligion().getName());
			emprespo.setSalutationId(dbemp.getSalutation().getId());
			emprespo.setSalutation(dbemp.getSalutation().getName());

			emprespo.setSupervisordesignationid(dbemp.getSupervisordesignation().getId());
			if (dbemp.getSupervisordesignation().getId() != 0
					&& hrmsEmployeeRepository.existsByDesignationId(dbemp.getSupervisordesignation().getId())) {
				StringBuilder builderSupervisor = new StringBuilder();
				builderSupervisor.append(dbemp.getSupervisordesignation().getName().trim());
				HrmsEmployee hrmsEmployee = hrmsEmployeeRepository
						.findFirstByDesignationId(dbemp.getSupervisordesignation().getId());

				builderSupervisor.append(" -  " + hrmsEmployee.getFirstName().trim());
				builderSupervisor.append(" " + hrmsEmployee.getLastName().trim());
				emprespo.setSupervisor(builderSupervisor.toString());
			}

			return ResponseEntity.status(HttpStatus.OK).body(emprespo);

		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
		}
	}

	@Override
	public ResponseEntity<HrmsEmployee> update(HrmsEmployee hrmsEmployee, int id) {

		LocalDateTime LocalTime = LocalDateTime.now();
		hrmsEmployee.setDate_updated(LocalTime);
		hrmsEmployee.setApproved(0);
		hrmsEmployee.setActive(1);

		int supervisordesignationid = hrmsEmployee.getSupervisordesignationid();
		int supervisorid = hrmsEmployeeRepository.findByDesignationIdAndEmploymentstatusid(supervisordesignationid, 2)
				.getId();
		hrmsEmployee.setSupervisorId(supervisorid);

		// Convert Date to Calendar
		Calendar c = Calendar.getInstance();
		c.setTime(hrmsEmployee.getDob());

		// Perform addition/subtraction
		c.add(Calendar.YEAR, 60);

		// Convert calendar back to Date
		Date retirementdate = c.getTime();

		hrmsEmployee.setDateofretirement(retirementdate);

		if (hrmsEmployeeRepository.existsById(id)) {
			HrmsEmployee hrmsEmployee1 = hrmsEmployeeRepository.findById(id).get();
			if (!((!hrmsEmployee.getEmail().equals(hrmsEmployee1.getEmail())
					&& hrmsEmployeeRepository.existsByEmail(hrmsEmployee.getEmail()))
					|| (!hrmsEmployee.getCardNumber().equals(hrmsEmployee1.getCardNumber())
							&& hrmsEmployeeRepository.existsByCardNumber(hrmsEmployee.getCardNumber()))
					|| (!hrmsEmployee.getFileNumber().equals(hrmsEmployee1.getFileNumber())
							&& hrmsEmployeeRepository.existsByFileNumber(hrmsEmployee.getFileNumber())))) {

				HrmsEmployeeUnit hrmsEmployeeUnit = hrmsEmployeeUnitRepository.findByIdAndActive(id, 1);
				if ((hrmsNationalityRepository.existsById(hrmsEmployee.getNationalityId())
						&& hrmsEmployeeRepository.existsByDesignationIdAndEmploymentstatusid(
								hrmsEmployee.getSupervisordesignationid(), 2)

						&& hrmsOrganisationOfficeRepository.existsByIdAndActive(hrmsEmployee.getDutystationid(), 1)
						&& hrmsOrginisationUnitRepository.existsById(hrmsEmployee.getUnitId()))) {

					// update employee employmentstatus
					if (hrmsEmployee.getEmploymentstatusid() != hrmsEmployee1.getEmploymentstatusid()) {
						// deactivate the active employee employment status

						HrmsEmployeeEmploymentStatus hrmsEmployeeEmploymentStatus1 = hrmsEmployeeEmploymentStatusRepository
								.findByEmployeeidAndStatusid(id, 1);
						hrmsEmployeeEmploymentStatus1.setDate_updated(LocalDateTime.now());
						hrmsEmployeeEmploymentStatus1.setDateend(new Date());
						hrmsEmployeeEmploymentStatus1.setActive(2);// this is to make sure we keep track of the old
																	// status 2 means old status
						hrmsEmployeeEmploymentStatusRepository.saveAndFlush(hrmsEmployeeEmploymentStatus1);

						// insert employee employment status
						HrmsEmployeeEmploymentStatus hrmsEmployeeEmploymentStatus = new HrmsEmployeeEmploymentStatus();
						hrmsEmployeeEmploymentStatus.setActive(1);
						hrmsEmployeeEmploymentStatus.setApproved(0);
						hrmsEmployeeEmploymentStatus.setDate_created(LocalDateTime.now());
						hrmsEmployeeEmploymentStatus.setDatestart(new Date());
						// hrmsEmployeeEmploymentStatus.setDescriptionstart(descriptionstart);
						hrmsEmployeeEmploymentStatus.setEmployeeid(id);
						hrmsEmployeeEmploymentStatus.setStatusid(hrmsEmployee.getEmploymentstatusid());
						hrmsEmployeeEmploymentStatus.setUnique_id(UUID.randomUUID());
						hrmsEmployeeEmploymentStatus.setId(0);

						hrmsEmployeeEmploymentStatusRepository.saveAndFlush(hrmsEmployeeEmploymentStatus);
					}

					// insert employee organization office
					if (hrmsEmployee.getDutystationid() != hrmsEmployee1.getDutystationid()) {

						if (hrmsEmployeeOfficeRepository.existsByEmployeeidAndActive(id, 1)) {
							// deactivate the active employee work station on employee organization Office
							HrmsEmployeeOffice hrmsEmployeeOffice1 = hrmsEmployeeOfficeRepository
									.findFirstByEmployeeidAndActive(id, 1);
							hrmsEmployeeOffice1.setActive(0);
							hrmsEmployeeOffice1.setApproved(1);
							hrmsEmployeeOffice1.setDatend(LocalDateTime.now());
							hrmsEmployeeOffice1.setDate_updated(LocalDateTime.now());
							hrmsEmployeeOfficeRepository.saveAndFlush(hrmsEmployeeOffice1);

							// now add the new employee office
							HrmsEmployeeOffice hrmsEmployeeOffice2 = new HrmsEmployeeOffice();
							hrmsEmployeeOffice2.setActive(1);
							hrmsEmployeeOffice2.setApproved(1);
							hrmsEmployeeOffice2.setDatestart(LocalDateTime.now());

							hrmsEmployeeOffice2.setEmployeeid(id);
							hrmsEmployeeOffice2.setOfficeid(hrmsEmployee.getDutystationid());
							hrmsEmployeeOffice2.setUnique_id(UUID.randomUUID());

							hrmsEmployeeOfficeRepository.saveAndFlush(hrmsEmployeeOffice2);
						}

						if (!hrmsEmployeeOfficeRepository.existsByEmployeeidAndActive(id, 1)) {

							// now add the new employee office
							HrmsEmployeeOffice hrmsEmployeeOffice = new HrmsEmployeeOffice();
							hrmsEmployeeOffice.setActive(1);
							hrmsEmployeeOffice.setApproved(1);
							hrmsEmployeeOffice.setDatestart(LocalDateTime.now());

							hrmsEmployeeOffice.setEmployeeid(id);
							hrmsEmployeeOffice.setOfficeid(hrmsEmployee.getDutystationid());
							hrmsEmployeeOffice.setUnique_id(UUID.randomUUID());

							hrmsEmployeeOfficeRepository.saveAndFlush(hrmsEmployeeOffice);
						}

					}

					// insert employee employment reason

					if (hrmsEmployee.getEmploymentstatusreasonid() != hrmsEmployee1.getEmploymentstatusreasonid()) {

						HrmsEmployeeEmploymentStatusReason hrmsEmployeeEmploymentStatusReason = new HrmsEmployeeEmploymentStatusReason();

						hrmsEmployeeEmploymentStatusReason.setActive(1);
						hrmsEmployeeEmploymentStatusReason.setApproved(1);
						hrmsEmployeeEmploymentStatusReason.setDate_created(LocalDateTime.now());
						hrmsEmployeeEmploymentStatusReason.setDescription("Reason for employment status");
						hrmsEmployeeEmploymentStatusReason.setEmployeeid(id);
						hrmsEmployeeEmploymentStatusReason
								.setStatusreasonid(hrmsEmployee.getEmploymentstatusreasonid());
						hrmsEmployeeEmploymentStatusReason.setUnique_id(UUID.randomUUID());

						hrmsEmployeeEmploymentStatusReasonRepository.saveAndFlush(hrmsEmployeeEmploymentStatusReason);

					}
					// now check if employee unit does exit and update or create new one if does not
					// exist

					if (hrmsEmployeeUnitRepository.existsByEmployeeidAndActive(id, 1)) {
						if (hrmsEmployee.getUnitId() != hrmsEmployeeRepository.findById(id).get().getUnitId()) {
							// set previous unit id inactive
							HrmsEmployeeUnit hrmsEmployeeUnit1 = hrmsEmployeeUnitRepository
									.findFirstByEmployeeidAndActive(id, 1);

							hrmsEmployeeUnit1.setDate_updated(LocalDateTime.now());
							hrmsEmployeeUnit1.setActive(0);
							hrmsEmployeeUnit1.setApproved(1);
							hrmsEmployeeUnit1.setDateEnd(LocalDateTime.now());
							hrmsEmployeeUnitRepository.saveAndFlush(hrmsEmployeeUnit);

							// now add new employee unit

							HrmsEmployeeUnit hrmsEmployeeUnit2 = new HrmsEmployeeUnit();
							hrmsEmployeeUnit2.setActive(hrmsEmployee.getActive());
							hrmsEmployeeUnit2.setUnitId(hrmsEmployee.getUnitId());
							hrmsEmployeeUnit2.setEmployeeid(id);
							hrmsEmployeeUnit2.setDateStart(LocalDateTime.now());
							hrmsEmployeeUnitRepository.save(hrmsEmployeeUnit2);
						}
					}

					if (!hrmsEmployeeUnitRepository.existsByEmployeeidAndActive(id, 1)) {

						// now add new employee unit
						HrmsEmployeeUnit hrmsEmployeeUnit1 = new HrmsEmployeeUnit();
						hrmsEmployeeUnit1.setActive(hrmsEmployee.getActive());
						hrmsEmployeeUnit1.setUnitId(hrmsEmployee.getUnitId());
						hrmsEmployeeUnit1.setEmployeeid(id);
						hrmsEmployeeUnit1.setDateStart(LocalDateTime.now());
						hrmsEmployeeUnitRepository.save(hrmsEmployeeUnit1);

					}

					if (hrmsEmployee1.getDate_created() != null && hrmsEmployee1.getUnique_id() != null) {
						hrmsEmployee.setDate_created(hrmsEmployee1.getDate_created());

						hrmsEmployee.setUnique_id(hrmsEmployee1.getUnique_id());

					}

					return ResponseEntity.status(HttpStatus.OK).body(hrmsEmployeeRepository.save(hrmsEmployee));
				} else {
					return ResponseEntity.status(HttpStatus.FAILED_DEPENDENCY).body(null);
				}
			} else {
				return ResponseEntity.status(HttpStatus.ALREADY_REPORTED).body(hrmsEmployee);
			}
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(hrmsEmployee);
		}
	}

	@Override
	public ResponseEntity<?> deleteHrmsEmployee(int id) {

		if (hrmsEmployeeRepository.existsById(id)) {

			// delete employee unit
			if (hrmsEmployeeUnitRepository.existsByEmployeeid(id)) {
				HrmsEmployeeUnit hrmsEmployeeUnit = hrmsEmployeeUnitRepository.findByEmployeeidAndActive(id, 1);
				hrmsEmployeeUnit.setActive(0);
				hrmsEmployeeUnit.setDate_updated(LocalDateTime.now());
				hrmsEmployeeUnitRepository.save(hrmsEmployeeUnit);
			}

			// delete employee
			HrmsEmployee hrmsEmployee = hrmsEmployeeRepository.findById(id).get();
			hrmsEmployee.setActive(0);
			hrmsEmployee.setDate_updated(LocalDateTime.now());

			return ResponseEntity.status(HttpStatus.OK).body(hrmsEmployeeRepository.save(hrmsEmployee));

		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
		}
	}

	@Override
	public ResponseEntity<List<EmployeeResponse>> listHrmsEmployee() {
		List<EmployeeResponse> usrlist = new ArrayList<>();
		List<Integer> status = new ArrayList<>();

		status.add(2);// active
		status.add(9);// In active

		List<HrmsEmployeeRead> userlist = hrmsEmployeeReadRepository.findByActiveOrderByIdDesc(1);

		String pattern = "yyyy-MM-dd";
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
		userlist.forEach(dbemp -> {
			// if (dbemp.getEmploymentStatus().getId() == 1) {

			EmployeeResponse emprespo = new EmployeeResponse();

			emprespo.setActive(dbemp.getActive());
			emprespo.setApproved(dbemp.getApproved());
			emprespo.setCardNumber(dbemp.getCardNumber());
			emprespo.setDate_created(dbemp.getDate_created());
			emprespo.setDate_updated(dbemp.getDate_updated());
			emprespo.setNationalId(dbemp.getNationalId());
			emprespo.setSectionid(dbemp.getSectionid());

			emprespo.setTin(dbemp.getTin());
			if (dbemp.getEmploymentenddate() != null) {
				emprespo.setEmploymentenddate(simpleDateFormat.format(dbemp.getEmploymentenddate()));
			}
			emprespo.setSignature(dbemp.getSignature());
			emprespo.setEmploymentstatusreasonid(dbemp.getHrmsEmploymentStatusReason().getId());
			emprespo.setEmploymentstatusreason(dbemp.getHrmsEmploymentStatusReason().getName());

			if (dbemp.getSectionid() != 0 && hrmsOrginisationUnitRepository.existsById(dbemp.getSectionid())) {
				emprespo.setSection(hrmsOrginisationUnitRepository.findById(dbemp.getSectionid()).get().getName());
			}
			emprespo.setPassportNo(dbemp.getPassportNo());

			if (dbemp.getDutystationid() != 0) {
				emprespo.setDutystation(
						hrmsOrganisationOfficeRepository.findById(dbemp.getDutystationid()).get().getName());
				emprespo.setDutystationid(dbemp.getDutystationid());
				emprespo.setDutystationtypeid(
						hrmsOrganisationOfficeRepository.findById(dbemp.getDutystationid()).get().getOfficetypeid());
				emprespo.setDutystationtypename(hrmsOrganisationOfficeCategoryRepository.findById(
						hrmsOrganisationOfficeRepository.findById(dbemp.getDutystationid()).get().getOfficetypeid())
						.get().getName());
				emprespo.setDutystationcityid(
						hrmsOrganisationOfficeRepository.findById(dbemp.getDutystationid()).get().getCityid());
				emprespo.setDutystationcity(hrmsLocationCityRepository
						.findById(hrmsOrganisationOfficeRepository.findById(dbemp.getDutystationid()).get().getCityid())
						.get().getName());

			}
			emprespo.setDateofemployment(simpleDateFormat.format(dbemp.getDateofemployment()));
			if (dbemp.getDateofretirement() != null) {
				emprespo.setDateofretirement(simpleDateFormat.format(dbemp.getDateofretirement()));
			} else {
				// Convert Date to Calendar
				Calendar c = Calendar.getInstance();
				c.setTime(dbemp.getDob());

				// Perform addition/subtraction
				c.add(Calendar.YEAR, 60);

				// Convert calendar back to Date
				Date retirementdate = c.getTime();

				emprespo.setDateofretirement(simpleDateFormat.format(retirementdate));

			}

			emprespo.setDesignation(dbemp.getDesignation().getName());

			emprespo.setDob(simpleDateFormat.format(dbemp.getDob()));
			emprespo.setEmail(dbemp.getEmail());
			emprespo.setEmploymentcategory(dbemp.getEmploymentcategory().getName());
			emprespo.setEmploymentstatus(dbemp.getEmploymentStatus().getName());
			emprespo.setFileNumber(dbemp.getFileNumber());
			emprespo.setFirstName(dbemp.getFirstName());
			emprespo.setGender(dbemp.getGender().getName());
			emprespo.setId(dbemp.getId());
			emprespo.setIsprobation(dbemp.getIsprobation());
			emprespo.setIssupervisor(dbemp.getIssupervisor());
			emprespo.setLastName(dbemp.getLastName());

			emprespo.setMaritalstatus(dbemp.getMaritalstatus().getName());

			emprespo.setMiddleName(dbemp.getMiddleName());
			emprespo.setNationality(dbemp.getNationality().getName());
			emprespo.setUnique_id(dbemp.getUnique_id());

			emprespo.setUnit(dbemp.getOrgunit().getName());

			emprespo.setPicture(dbemp.getPicture());
			emprespo.setReligion(dbemp.getReligion().getName());
			emprespo.setSalutation(dbemp.getSalutation().getName());

			if (dbemp.getSupervisordesignation().getId() != 0
					&& hrmsEmployeeRepository.existsByDesignationId(dbemp.getSupervisordesignation().getId())) {
				StringBuilder builderSupervisor = new StringBuilder();
				builderSupervisor.append(dbemp.getSupervisordesignation().getName().trim());
				HrmsEmployee hrmsEmployee = hrmsEmployeeRepository
						.findFirstByDesignationId(dbemp.getSupervisordesignation().getId());

				builderSupervisor.append(" -  " + hrmsEmployee.getFirstName().trim());
				builderSupervisor.append(" " + hrmsEmployee.getLastName().trim());
				emprespo.setSupervisor(builderSupervisor.toString());

			}

			usrlist.add(emprespo);
			// }

		});

		return ResponseEntity.status(HttpStatus.OK).body(usrlist);
	}

	private List<HrmsEmployeeRead> hrmsEmployeeReadRepositoryfindAllByOrderByIdDesc() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ResponseEntity<EmployeeResponse> getEmployeeByEmail(String email) {
		if (hrmsEmployeeReadRepository.existsByEmail(email)) {

			HrmsEmployeeRead dbemp = hrmsEmployeeReadRepository.findByEmail(email).get();

			EmployeeResponse emprespo = new EmployeeResponse();
			String pattern = "yyyy-MM-dd";
			SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
			int empid = hrmsEmployeeRepository.findByEmail(email).getId();
			if (hrmsemployeesalaryRepository.existsByEmployeeidAndActive(empid, 1)) {
				int salarystructureid = hrmsemployeesalaryRepository.findByEmployeeidAndActive(empid, 1)
						.getSalarystructureId();

				if (hrmsSalarystructureRepository.existsById(salarystructureid)) {

					int salaryscaleid = hrmsSalarystructureRepository.findById(salarystructureid).get().getScaleId();

					if (hrmsSalaryScaleRepository.existsById(salaryscaleid)) {
						// String salaryscale =
						// hrmsSalaryScaleRepository.findById(salaryscaleid).get().getName();
						if (hrmsSalaryScaleRepository.existsById(salaryscaleid)) {
							// String salaryscale =
							// hrmsSalaryScaleRepository.findById(salaryscaleid).get().getName();

							String SalaryScale = hrmsSalaryScaleRepository
									.findById(hrmsSalarystructureRepository.findById(salarystructureid).get()
											.getScaleId())
									.get().getName() + "-"
									+ hrmsSalaryscalenotchRepository.findById(hrmsSalarystructureRepository
											.findById(salarystructureid).get().getNotchId()).get().getName();

							emprespo.setSalarystructure(SalaryScale);

						}

					}
				}
			}

			emprespo.setActive(dbemp.getActive());
			emprespo.setTin(dbemp.getTin());
			if (dbemp.getEmploymentenddate() != null) {
				emprespo.setEmploymentenddate(simpleDateFormat.format(dbemp.getEmploymentenddate()));
			}
			emprespo.setSignature(dbemp.getSignature());
			emprespo.setEmploymentstatusreasonid(dbemp.getHrmsEmploymentStatusReason().getId());
			emprespo.setEmploymentstatusreason(dbemp.getHrmsEmploymentStatusReason().getName());
			emprespo.setSectionid(dbemp.getSectionid());
			if (dbemp.getSectionid() != 0 && hrmsOrginisationUnitRepository.existsById(dbemp.getSectionid())) {
				emprespo.setSection(hrmsOrginisationUnitRepository.findById(dbemp.getSectionid()).get().getName());
			}
			emprespo.setApproved(dbemp.getApproved());
			emprespo.setCardNumber(dbemp.getCardNumber());
			emprespo.setDate_created(dbemp.getDate_created());
			emprespo.setDate_updated(dbemp.getDate_updated());
			emprespo.setNationalId(dbemp.getNationalId());
			emprespo.setPassportNo(dbemp.getPassportNo());
			emprespo.setDateofemployment(simpleDateFormat.format(dbemp.getDateofemployment()));
			emprespo.setDesignation(dbemp.getDesignation().getName());

			if (dbemp.getDutystationid() != 0) {
				emprespo.setDutystation(
						hrmsOrganisationOfficeRepository.findById(dbemp.getDutystationid()).get().getName());
				emprespo.setDutystationid(dbemp.getDutystationid());
				emprespo.setDutystationtypeid(
						hrmsOrganisationOfficeRepository.findById(dbemp.getDutystationid()).get().getOfficetypeid());
				emprespo.setDutystationtypename(hrmsOrganisationOfficeCategoryRepository.findById(
						hrmsOrganisationOfficeRepository.findById(dbemp.getDutystationid()).get().getOfficetypeid())
						.get().getName());
				emprespo.setDutystationcityid(
						hrmsOrganisationOfficeRepository.findById(dbemp.getDutystationid()).get().getCityid());
				emprespo.setDutystationcity(hrmsLocationCityRepository
						.findById(hrmsOrganisationOfficeRepository.findById(dbemp.getDutystationid()).get().getCityid())
						.get().getName());

			}

			emprespo.setDob(simpleDateFormat.format(dbemp.getDob()));

			if (dbemp.getDateofretirement() != null) {
				emprespo.setDateofretirement(simpleDateFormat.format(dbemp.getDateofretirement()));
			} else {
				// Convert Date to Calendar
				Calendar c = Calendar.getInstance();
				c.setTime(dbemp.getDob());

				// Perform addition/subtraction
				c.add(Calendar.YEAR, 60);

				// Convert calendar back to Date
				Date retirementdate = c.getTime();

				emprespo.setDateofretirement(simpleDateFormat.format(retirementdate));

			}
			emprespo.setEmail(dbemp.getEmail());
			emprespo.setEmploymentcategory(dbemp.getEmploymentcategory().getName());
			emprespo.setEmploymentstatus(dbemp.getEmploymentStatus().getName());
			emprespo.setFileNumber(dbemp.getFileNumber());
			emprespo.setFirstName(dbemp.getFirstName());
			emprespo.setGender(dbemp.getGender().getName());
			emprespo.setId(dbemp.getId());
			emprespo.setIsprobation(dbemp.getIsprobation());
			emprespo.setIssupervisor(dbemp.getIssupervisor());
			emprespo.setLastName(dbemp.getLastName());

			emprespo.setMaritalstatus(dbemp.getMaritalstatus().getName());

			emprespo.setMiddleName(dbemp.getMiddleName());
			emprespo.setNationality(dbemp.getNationality().getName());
			emprespo.setUnique_id(dbemp.getUnique_id());

			emprespo.setUnit(dbemp.getOrgunit().getName());

			emprespo.setPicture(dbemp.getPicture());
			emprespo.setReligion(dbemp.getReligion().getName());
			emprespo.setSalutation(dbemp.getSalutation().getName());
			if (dbemp.getSupervisordesignation().getId() != 0
					&& hrmsEmployeeRepository.existsByDesignationId(dbemp.getSupervisordesignation().getId())) {
				StringBuilder builderSupervisor = new StringBuilder();
				builderSupervisor.append(dbemp.getSupervisordesignation().getName().trim());
				HrmsEmployee hrmsEmployee = hrmsEmployeeRepository
						.findFirstByDesignationId(dbemp.getSupervisordesignation().getId());

				builderSupervisor.append(" -  " + hrmsEmployee.getFirstName().trim());
				builderSupervisor.append(" " + hrmsEmployee.getLastName().trim());
				emprespo.setSupervisor(builderSupervisor.toString());

			}

			return ResponseEntity.status(HttpStatus.OK).body(emprespo);

		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
		}
	}

	@Override
	public ResponseEntity<EmployeeResponseEdms> getEmployeeByEmailEdms(String email) {
		if (hrmsEmployeeReadRepository.existsByEmail(email)) {

			HrmsEmployeeRead dbemp = hrmsEmployeeReadRepository.findByEmail(email).get();

			EmployeeResponseEdms emprespo = new EmployeeResponseEdms();
			String pattern = "yyyy-MM-dd";
			SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);

			emprespo.setActive(dbemp.getActive());
			emprespo.setApproved(dbemp.getApproved());
			emprespo.setCardNumber(dbemp.getCardNumber());
			emprespo.setDate_created(dbemp.getDate_created());
			emprespo.setDate_updated(dbemp.getDate_updated());
			if (hrmsemployeesalaryRepository.existsByEmployeeidAndActive(dbemp.getId(), 1)) {
				emprespo.setSalaryStructureid(hrmsemployeesalaryRepository.findByEmployeeidAndActive(dbemp.getId(), 1)
						.getSalarystructureId());
			}

			emprespo.setDateofemployment(simpleDateFormat.format(dbemp.getDateofemployment()));

			emprespo.setDesignationid(dbemp.getDesignation().getId());

			emprespo.setDob(simpleDateFormat.format(dbemp.getDob()));
			emprespo.setEmail(dbemp.getEmail());
			emprespo.setEmploymentcategory(dbemp.getEmploymentcategory().getName());
			emprespo.setEmploymentstatusId(dbemp.getEmploymentStatus().getId());
			emprespo.setFileNumber(dbemp.getFileNumber());
			emprespo.setFirstName(dbemp.getFirstName());
			emprespo.setGender(dbemp.getGender().getName());
			emprespo.setId(dbemp.getId());
			emprespo.setIsprobation(dbemp.getIsprobation());
			emprespo.setIssupervisor(dbemp.getIssupervisor());
			emprespo.setLastName(dbemp.getLastName());

			emprespo.setMaritalstatus(dbemp.getMaritalstatus().getName());

			emprespo.setMiddleName(dbemp.getMiddleName());
			emprespo.setNationality(dbemp.getNationality().getName());
			emprespo.setUnique_id(dbemp.getUnique_id());

			emprespo.setUnitid(dbemp.getOrgunit().getId());

			emprespo.setPicture(dbemp.getPicture());
			emprespo.setReligion(dbemp.getReligion().getName());
			emprespo.setSalutation(dbemp.getSalutation().getName());
			emprespo.setSupervisordesgnationid(dbemp.getSupervisorId().getDesignationId());

			emprespo.setDutystationid(dbemp.getDutystationid());

			return ResponseEntity.status(HttpStatus.OK).body(emprespo);

		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
		}
	}

	@Override
	public ResponseEntity<EmployeeGeneralRequest> addEmployeeDetails(EmployeeGeneralRequest employeeGeneralRequest) {
		if (!hrmsEmployeeRepository.existsByEmail(employeeGeneralRequest.getHrmsEmployee().getEmail())) {
			// 1. parse and insert employee
			// 2. parse and insert contact - foreach
			// 2.1 insert contact
			// 2.2 insert employee contact
			// 3. parse and insert address - foreach
			// 3.1 insert address
			// 3.2. insert employee address
			// return -- as expcted

			HrmsEmployee hrmsEmployee = new HrmsEmployee();
			hrmsEmployee = employeeGeneralRequest.getHrmsEmployee();

			// Convert Date to Calendar
			Calendar c = Calendar.getInstance();
			c.setTime(hrmsEmployee.getDob());

			// Perform addition/subtraction
			c.add(Calendar.YEAR, 60);

			// Convert calendar back to Date
			Date retirementdate = c.getTime();

			hrmsEmployee.setDateofretirement(retirementdate);
			hrmsEmployee.setActive(1);
			hrmsEmployee.setApproved(0);
			hrmsEmployee.setUnique_id(UUID.randomUUID());

			int empid = hrmsEmployeeRepository.saveAndFlush(hrmsEmployee).getId();

			// add employement status reason

			// insert employee employment reason

			HrmsEmployeeEmploymentStatusReason hrmsEmployeeEmploymentStatusReason = new HrmsEmployeeEmploymentStatusReason();

			hrmsEmployeeEmploymentStatusReason.setActive(1);
			hrmsEmployeeEmploymentStatusReason.setApproved(1);
			hrmsEmployeeEmploymentStatusReason.setDate_created(LocalDateTime.now());
			hrmsEmployeeEmploymentStatusReason.setDescription("Reason for employment status");
			hrmsEmployeeEmploymentStatusReason.setEmployeeid(empid);
			hrmsEmployeeEmploymentStatusReason.setStatusreasonid(hrmsEmployee.getEmploymentstatusreasonid());
			hrmsEmployeeEmploymentStatusReason.setUnique_id(UUID.randomUUID());

			hrmsEmployeeEmploymentStatusReasonRepository.saveAndFlush(hrmsEmployeeEmploymentStatusReason);

			List<HrmsContact> hrmsContactlist = employeeGeneralRequest.getHrmsContactlist();

			hrmsContactlist.forEach(contact -> {
				HrmsContact hrmsContact = new HrmsContact();
				hrmsContact = contact;
				hrmsContact.setActive(1);
				hrmsContact.setApproved(0);
				hrmsContact.setDate_created(LocalDateTime.now());
				hrmsContact.setUnique_id(UUID.randomUUID());

				int contactid = hrmsContactRepository.saveAndFlush(hrmsContact).getId(); // save contact and get contact
																							// id

				HrmsEmployeeContact hrmsEmployeeContact = new HrmsEmployeeContact();

				hrmsEmployeeContact.setActive(1);

				hrmsEmployeeContact.setApproved(0);
				hrmsEmployeeContact.setDate_created(LocalDateTime.now());
				hrmsEmployeeContact.setContactid(contactid);
				hrmsEmployeeContact.setEmployeeid(empid);
				hrmsEmployeeContact.setUnique_id(UUID.randomUUID());

				hrmsEmployeeContactRepository.saveAndFlush(hrmsEmployeeContact); // save employee contact

			});

			List<HrmsLocationAddress> hrmsLocationAddresslist = employeeGeneralRequest.getAdresslist();

			hrmsLocationAddresslist.forEach(adress -> {
				HrmsLocationAddress hrmsLocationAddress = new HrmsLocationAddress();

				hrmsLocationAddress = adress;

				hrmsLocationAddress.setActive(1);
				hrmsLocationAddress.setApproved(0);
				hrmsLocationAddress.setDate_created(LocalDateTime.now());
				hrmsLocationAddress.setUnique_id(UUID.randomUUID());
				int adresid = hrmsLocationAddressRepository.saveAndFlush(hrmsLocationAddress).getId(); // add adress and
																										// get id

				HrmsEmployeeAddress hrmsEmployeeAddress = new HrmsEmployeeAddress();
				hrmsEmployeeAddress.setActive(1);
				hrmsEmployeeAddress.setApproved(0);
				hrmsEmployeeAddress.setUnique_id(UUID.randomUUID());
				hrmsEmployeeAddress.setDate_created(LocalDateTime.now());
				hrmsEmployeeAddress.setEmployeeid(empid);
				hrmsEmployeeAddress.setAddressid(adresid);

				hrmsEmployeeAddressRepository.saveAndFlush(hrmsEmployeeAddress);

			});

			return ResponseEntity.status(HttpStatus.OK).body(employeeGeneralRequest);
		} else {
			return ResponseEntity.status(HttpStatus.FAILED_DEPENDENCY).body(null);
		}
	}

	@Override
	public ResponseEntity<EmployeeGeneralRequest> updateEmployeeDetails(EmployeeGeneralRequest employeeGeneralRequest,
			int empid) {

		if (hrmsEmployeeRepository.existsByEmail(employeeGeneralRequest.getHrmsEmployee().getEmail())
				&& hrmsEmployeeRepository.existsById(empid)) {
			// 1. parse and insert employee
			// 2. parse and insert contact - foreach
			// 2.1 insert contact
			// 2.2 insert employee contact
			// 3. parse and insert address - foreach
			// 3.1 insert address
			// 3.2. insert employee address
			// return -- as expcted

			HrmsEmployee hrmsEmployee = hrmsEmployeeRepository.findById(empid).get();
			hrmsEmployee = employeeGeneralRequest.getHrmsEmployee();
			hrmsEmployee.setDate_updated(LocalDateTime.now());

			// Convert Date to Calendar
			Calendar c = Calendar.getInstance();
			c.setTime(hrmsEmployee.getDob());

			// Perform addition/subtraction
			c.add(Calendar.YEAR, 60);

			// Convert calendar back to Date
			Date retirementdate = c.getTime();

			hrmsEmployee.setDateofretirement(retirementdate);

			// update employee employmentstatus
			if (hrmsEmployee.getEmploymentstatusid() != hrmsEmployeeRepository.findByIdAndActive(empid, 1)
					.getEmploymentstatusid()) {
				// deactivate the active employee employment status

				HrmsEmployeeEmploymentStatus hrmsEmployeeEmploymentStatus1 = hrmsEmployeeEmploymentStatusRepository
						.findByEmployeeidAndStatusid(empid, 1);
				hrmsEmployeeEmploymentStatus1.setDate_updated(LocalDateTime.now());
				hrmsEmployeeEmploymentStatus1.setDateend(new Date());
				hrmsEmployeeEmploymentStatus1.setActive(2);// this is to make sure we keep track of the old
															// status 2 means old status
				hrmsEmployeeEmploymentStatusRepository.saveAndFlush(hrmsEmployeeEmploymentStatus1);

				// insert employee employment status
				HrmsEmployeeEmploymentStatus hrmsEmployeeEmploymentStatus = new HrmsEmployeeEmploymentStatus();
				hrmsEmployeeEmploymentStatus.setActive(1);
				hrmsEmployeeEmploymentStatus.setApproved(0);
				hrmsEmployeeEmploymentStatus.setDate_created(LocalDateTime.now());
				hrmsEmployeeEmploymentStatus.setDatestart(new Date());
				// hrmsEmployeeEmploymentStatus.setDescriptionstart(descriptionstart);
				hrmsEmployeeEmploymentStatus.setEmployeeid(empid);
				hrmsEmployeeEmploymentStatus.setStatusid(hrmsEmployee.getEmploymentstatusid());
				hrmsEmployeeEmploymentStatus.setUnique_id(UUID.randomUUID());
				hrmsEmployeeEmploymentStatus.setId(0);

				hrmsEmployeeEmploymentStatusRepository.saveAndFlush(hrmsEmployeeEmploymentStatus);
			}

			hrmsEmployee.setCardNumber(employeeGeneralRequest.getHrmsEmployee().getCardNumber());
			hrmsEmployee.setDateofemployment(employeeGeneralRequest.getHrmsEmployee().getDateofemployment());
			hrmsEmployee.setDesignationId(employeeGeneralRequest.getHrmsEmployee().getDesignationId());
			hrmsEmployee.setDob(employeeGeneralRequest.getHrmsEmployee().getDob());
			hrmsEmployee.setEmail(employeeGeneralRequest.getHrmsEmployee().getEmail());
			hrmsEmployee.setEmploymentcategoryId(employeeGeneralRequest.getHrmsEmployee().getEmploymentcategoryId());
			hrmsEmployee.setEmploymentstatusid(employeeGeneralRequest.getHrmsEmployee().getEmploymentstatusid());
			hrmsEmployee.setFileNumber(employeeGeneralRequest.getHrmsEmployee().getFileNumber());
			hrmsEmployee.setFirstName(employeeGeneralRequest.getHrmsEmployee().getFirstName());
			hrmsEmployee.setGenderid(employeeGeneralRequest.getHrmsEmployee().getGenderid());
			hrmsEmployee.setIsprobation(employeeGeneralRequest.getHrmsEmployee().getIsprobation());
			hrmsEmployee.setIssupervisor(employeeGeneralRequest.getHrmsEmployee().getIssupervisor());
			hrmsEmployee.setLastName(employeeGeneralRequest.getHrmsEmployee().getLastName());
			hrmsEmployee.setMaritalstatusId(employeeGeneralRequest.getHrmsEmployee().getMaritalstatusId());
			hrmsEmployee.setMiddleName(employeeGeneralRequest.getHrmsEmployee().getMiddleName());
			hrmsEmployee.setNationalityId(employeeGeneralRequest.getHrmsEmployee().getNationalityId());
			if (employeeGeneralRequest.getHrmsEmployee().getPicture() != null) {
				hrmsEmployee.setPicture(employeeGeneralRequest.getHrmsEmployee().getPicture());
			}
			hrmsEmployee.setReligionId(employeeGeneralRequest.getHrmsEmployee().getReligionId());
			hrmsEmployee.setSalutationId(employeeGeneralRequest.getHrmsEmployee().getSalutationId());
			hrmsEmployee
					.setSupervisordesignationid(employeeGeneralRequest.getHrmsEmployee().getSupervisordesignationid());
			hrmsEmployee.setUnitId(employeeGeneralRequest.getHrmsEmployee().getUnitId());

			hrmsEmployee.setApproved(0);
			hrmsEmployeeRepository.saveAndFlush(hrmsEmployee);

			List<HrmsContact> hrmsContactlist = employeeGeneralRequest.getHrmsContactlist();

			hrmsContactlist.forEach(contact -> {
				if (hrmsContactRepository.existsById(contact.getId())) {
					HrmsContact hrmsContact = hrmsContactRepository.findById(contact.getId()).get();
					hrmsContact.setDate_updated(LocalDateTime.now());
					hrmsContact.setContacttypeid(contact.getContacttypeid());
					hrmsContact.setEmailaddress(contact.getEmailaddress());
					hrmsContact.setPhoneprimary(contact.getPhoneprimary());
					hrmsContact.setApproved(0);

					if (contact.getPhonesecondary() != null) {
						hrmsContact.setPhonesecondary(contact.getPhonesecondary());

					}

					hrmsContactRepository.saveAndFlush(hrmsContact).getId(); // save contact and get contact
																				// id

					HrmsEmployeeContact hrmsEmployeeContact = hrmsEmployeeContactRepository
							.findByContactid(contact.getId());

					hrmsEmployeeContact.setDate_updated(LocalDateTime.now());
					hrmsEmployeeContact.setApproved(0);

					hrmsEmployeeContactRepository.saveAndFlush(hrmsEmployeeContact); // save employee contact

				}
			});

			List<HrmsLocationAddress> hrmsLocationAddresslist = employeeGeneralRequest.getAdresslist();

			hrmsLocationAddresslist.forEach(locatn -> {

				if (hrmsLocationAddressRepository.existsById(locatn.getId())) {
					HrmsLocationAddress hrmsLocationAddress = hrmsLocationAddressRepository.findById(locatn.getId())
							.get();

					hrmsLocationAddress.setDate_updated(LocalDateTime.now());
					hrmsLocationAddress.setAddressline1(locatn.getAddressline1());
					if (locatn.getAddressline2() != null) {
						hrmsLocationAddress.setAddressline2(locatn.getAddressline2());
					}
					hrmsLocationAddress.setCityid(locatn.getCityid());

					hrmsLocationAddress.setAddresstypeid(locatn.getAddresstypeid());
					hrmsLocationAddress.setApproved(0);

					hrmsLocationAddressRepository.saveAndFlush(hrmsLocationAddress).getId();

					HrmsEmployeeAddress hrmsEmployeeAddress = hrmsEmployeeAddressRepository
							.findByAddressid(locatn.getId());
					hrmsEmployeeAddress.setApproved(0);

					hrmsEmployeeAddress.setDate_updated(LocalDateTime.now());

					hrmsEmployeeAddressRepository.saveAndFlush(hrmsEmployeeAddress);
				}
			});

			return ResponseEntity.status(HttpStatus.OK).body(employeeGeneralRequest);
		} else {
			return ResponseEntity.status(HttpStatus.FAILED_DEPENDENCY).body(null);
		}
	}

	@Override
	public ResponseEntity<EmployeeResponseDetailed> getEmployeeDetailsById(int id) {
		EmployeeResponseDetailed employeeResponseDetailed = new EmployeeResponseDetailed();

		// employee basic information

		if (hrmsEmployeeRepository.existsById(id)) {

			HrmsEmployeeRead dbemp = hrmsEmployeeReadRepository.findById(id).get();

			EmployeeResponse emprespo = new EmployeeResponse();
			String pattern = "yyyy-MM-dd";
			SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
			if (hrmsemployeesalaryRepository.existsByEmployeeidAndActive(id, 1)) {
				int salarystructureid = hrmsemployeesalaryRepository.findByEmployeeidAndActive(id, 1)
						.getSalarystructureId();

				if (hrmsSalarystructureRepository.existsById(salarystructureid)) {

					int salaryscaleid = hrmsSalarystructureRepository.findById(salarystructureid).get().getScaleId();

					if (hrmsSalaryScaleRepository.existsById(salaryscaleid)) {
						// String salaryscale =
						// hrmsSalaryScaleRepository.findById(salaryscaleid).get().getName();

						String SalaryScale = hrmsSalaryScaleRepository
								.findById(hrmsSalarystructureRepository.findById(salarystructureid).get().getScaleId())
								.get().getName()
								+ "-"
								+ hrmsSalaryscalenotchRepository.findById(
										hrmsSalarystructureRepository.findById(salarystructureid).get().getNotchId())
										.get().getName();

						emprespo.setSalarystructure(SalaryScale);

					}
				}
			}

			emprespo.setActive(dbemp.getActive());

			if (dbemp.getEmploymentenddate() != null) {
				emprespo.setEmploymentenddate(simpleDateFormat.format(dbemp.getEmploymentenddate()));
			}
			emprespo.setSignature(dbemp.getSignature());
			emprespo.setEmploymentstatusreasonid(dbemp.getHrmsEmploymentStatusReason().getId());
			emprespo.setEmploymentstatusreason(dbemp.getHrmsEmploymentStatusReason().getName());
			emprespo.setApproved(dbemp.getApproved());
			emprespo.setCardNumber(dbemp.getCardNumber());
			emprespo.setDate_created(dbemp.getDate_created());
			emprespo.setDate_updated(dbemp.getDate_updated());
			emprespo.setNationalId(dbemp.getNationalId());
			emprespo.setPassportNo(dbemp.getPassportNo());

			if (dbemp.getDutystationid() != 0) {
				emprespo.setDutystation(
						hrmsOrganisationOfficeRepository.findById(dbemp.getDutystationid()).get().getName());
				emprespo.setDutystationid(dbemp.getDutystationid());
				emprespo.setDutystationtypeid(
						hrmsOrganisationOfficeRepository.findById(dbemp.getDutystationid()).get().getOfficetypeid());
				emprespo.setDutystationtypename(hrmsOrganisationOfficeCategoryRepository.findById(
						hrmsOrganisationOfficeRepository.findById(dbemp.getDutystationid()).get().getOfficetypeid())
						.get().getName());
				emprespo.setDutystationcityid(
						hrmsOrganisationOfficeRepository.findById(dbemp.getDutystationid()).get().getCityid());
				emprespo.setDutystationcity(hrmsLocationCityRepository
						.findById(hrmsOrganisationOfficeRepository.findById(dbemp.getDutystationid()).get().getCityid())
						.get().getName());

			}
			emprespo.setDateofemployment(simpleDateFormat.format(dbemp.getDateofemployment()));

			emprespo.setDesignationId(dbemp.getDesignation().getId());
			emprespo.setDesignation(dbemp.getDesignation().getName());

			emprespo.setDob(simpleDateFormat.format(dbemp.getDob()));

			if (dbemp.getDateofretirement() != null) {
				emprespo.setDateofretirement(simpleDateFormat.format(dbemp.getDateofretirement()));
			} else {
				// Convert Date to Calendar
				Calendar c = Calendar.getInstance();
				c.setTime(dbemp.getDob());

				// Perform addition/subtraction
				c.add(Calendar.YEAR, 60);

				// Convert calendar back to Date
				Date retirementdate = c.getTime();

				emprespo.setDateofretirement(simpleDateFormat.format(retirementdate));

			}
			emprespo.setEmail(dbemp.getEmail());
			emprespo.setEmploymentcategoryId(dbemp.getEmploymentcategory().getId());
			emprespo.setEmploymentcategory(dbemp.getEmploymentcategory().getName());
			emprespo.setEmploymentstatusid(dbemp.getEmploymentStatus().getId());
			emprespo.setEmploymentstatus(dbemp.getEmploymentStatus().getName());
			emprespo.setFileNumber(dbemp.getFileNumber());
			emprespo.setFirstName(dbemp.getFirstName());

			emprespo.setGenderid(dbemp.getGender().getId());
			emprespo.setGender(dbemp.getGender().getName());
			emprespo.setId(dbemp.getId());
			emprespo.setIsprobation(dbemp.getIsprobation());
			emprespo.setIssupervisor(dbemp.getIssupervisor());
			emprespo.setLastName(dbemp.getLastName());

			emprespo.setMaritalstatusId(dbemp.getMaritalstatus().getId());
			emprespo.setMaritalstatus(dbemp.getMaritalstatus().getName());

			emprespo.setMiddleName(dbemp.getMiddleName());
			emprespo.setNationalityId(dbemp.getNationality().getId());
			emprespo.setNationality(dbemp.getNationality().getName());
			emprespo.setUnique_id(dbemp.getUnique_id());

			emprespo.setUnitId(dbemp.getOrgunit().getId());

			emprespo.setUnit(dbemp.getOrgunit().getName());

			emprespo.setSectionid(dbemp.getSectionid());
			if (dbemp.getSectionid() != 0 && hrmsOrginisationUnitRepository.existsById(dbemp.getSectionid())) {
				emprespo.setSection(hrmsOrginisationUnitRepository.findById(dbemp.getSectionid()).get().getName());
			}

			emprespo.setPicture(dbemp.getPicture());
			emprespo.setReligionId(dbemp.getReligion().getId());
			emprespo.setReligion(dbemp.getReligion().getName());
			emprespo.setSalutationId(dbemp.getSalutation().getId());
			emprespo.setSalutation(dbemp.getSalutation().getName());

			emprespo.setSupervisordesignationid(dbemp.getSupervisordesignation().getId());
			if (dbemp.getSupervisordesignation().getId() != 0
					&& hrmsEmployeeRepository.existsByDesignationId(dbemp.getSupervisordesignation().getId())) {
				StringBuilder builderSupervisor = new StringBuilder();
				builderSupervisor.append(dbemp.getSupervisordesignation().getName().trim());
				HrmsEmployee hrmsEmployee = hrmsEmployeeRepository
						.findFirstByDesignationId(dbemp.getSupervisordesignation().getId());

				builderSupervisor.append(" -  " + hrmsEmployee.getFirstName().trim());
				builderSupervisor.append(" " + hrmsEmployee.getLastName().trim());
				emprespo.setSupervisor(builderSupervisor.toString());

			}
			employeeResponseDetailed.setEmployeeResponse(emprespo);

			// education informations

			if (hrmsEmployeeEducationRepository.existsByEmployeeidAndActive(id, 1)) {

				List<EmployeeEducationResponseV2> usrlist = new ArrayList<>();

				List<HrmsEmployeeEducation> userlist = hrmsEmployeeEducationRepository.findByEmployeeidAndActive(id, 1);

				userlist.forEach(dbemp1 -> {

					EmployeeEducationResponseV2 emprespo1 = new EmployeeEducationResponseV2();
					emprespo1.setApproved(dbemp1.getApproved());

					emprespo1.setId(dbemp1.getId());
					emprespo1.setEndYear(dbemp1.getDatend());
					emprespo1.setStartYear(dbemp1.getDatestart());
					emprespo1.setInstituteName(
							hrmsEducationinstitutionRepository.findById(dbemp1.getInstitutionid()).get().getName());
					emprespo1.setInstituteId(dbemp1.getInstitutionid());
					emprespo1.setLevelId(dbemp1.getLevelid());
					emprespo1.setLevelName(hrmsEducationlevelRepository.findById(dbemp1.getLevelid()).get().getName());
					emprespo1.setCourseId(dbemp1.getCourseid());
					emprespo1.setCourseName(
							hrmsEducationcourseRepository.findById(dbemp1.getCourseid()).get().getName());
					emprespo1.setCountryId(dbemp1.getCountryid());
					emprespo1.setAttachmentId(hrmsEmployeeEducationAttachmentRepository
							.findByEmployeeidAndEducationid(id, dbemp1.getId()).get().getAttachment_id());
					emprespo1.setAttachmentName(hrmsAttachmentRepository
							.findById(hrmsEmployeeEducationAttachmentRepository
									.findByEmployeeidAndEducationid(id, dbemp1.getId()).get().getAttachment_id())
							.get().getName());

					emprespo1.setAttachmentUri(hrmsAttachmentRepository
							.findById(hrmsEmployeeEducationAttachmentRepository
									.findByEmployeeidAndEducationid(id, dbemp1.getId()).get().getAttachment_id())
							.get().getUri());

					emprespo1.setAttachmentName(hrmsAttachmentRepository
							.findById(hrmsEmployeeEducationAttachmentRepository
									.findByEmployeeidAndEducationid(id, dbemp1.getId()).get().getAttachment_id())
							.get().getName());

					emprespo1.setAttachmentTypeId(hrmsAttachmentRepository
							.findById(hrmsEmployeeEducationAttachmentRepository
									.findByEmployeeidAndEducationid(id, dbemp1.getId()).get().getAttachment_id())
							.get().getAttachmenttypeid());

					emprespo1.setAttachmentTypeName(hrmsAttachmentTypeRepository.findById(hrmsAttachmentRepository
							.findById(hrmsEmployeeEducationAttachmentRepository
									.findByEmployeeidAndEducationid(id, dbemp1.getId()).get().getAttachment_id())
							.get().getAttachmenttypeid()).get().getName());
					// emprespo1.setApproved(dbemp1.getApproved());
					emprespo1.setApprovalComment(dbemp1.getApprovalComment());

					usrlist.add(emprespo1);

				});

				employeeResponseDetailed.setEmployeeEducationResponseV2list(usrlist);

			}

			// Short courses

			if (hrmsEmployeeShortCoursesRepository.existsByEmployeeidAndActive(id, 1)) {
				List<HrmsEmployeeShortCoursesR> shortcouseslist = new ArrayList<>();
				List<HrmsEmployeeShortCourses> hrmsEmployeeShortCourses = hrmsEmployeeShortCoursesRepository
						.findByEmployeeidAndActive(id, 1);
				hrmsEmployeeShortCourses.forEach(shortcourses -> {
					HrmsEmployeeShortCoursesR hrmsEmployeeShortCoursesR = new HrmsEmployeeShortCoursesR();

					hrmsEmployeeShortCoursesR.setActive(shortcourses.getActive());
					hrmsEmployeeShortCoursesR.setApproved(shortcourses.getApproved());
					hrmsEmployeeShortCoursesR.setCoursename(shortcourses.getCoursename());
					hrmsEmployeeShortCoursesR.setApprovalComment(shortcourses.getApprovalComment());

					int attachmentid = hrmsEmployeeShortCourseAttachmentRepository
							.findByShortcourseid(shortcourses.getId()).getAttachmentid();
					if (attachmentid != 0) {
						hrmsEmployeeShortCoursesR.setAttachmentdescription(
								hrmsAttachmentRepository.findById(attachmentid).get().getDescription());
						hrmsEmployeeShortCoursesR
								.setAttachmentname(hrmsAttachmentRepository.findById(attachmentid).get().getName());

						int attachmenttypeid = hrmsAttachmentRepository.findById(attachmentid).get()
								.getAttachmenttypeid();
						hrmsEmployeeShortCoursesR.setAttachmenttypeid(attachmenttypeid);
						hrmsEmployeeShortCoursesR.setAttachmenttypename(
								hrmsAttachmentTypeRepository.findById(attachmenttypeid).get().getName());
						hrmsEmployeeShortCoursesR.setAttchmentid(attachmentid);
						hrmsEmployeeShortCoursesR
								.setUri(hrmsAttachmentRepository.findById(attachmentid).get().getUri());

					}
					hrmsEmployeeShortCoursesR.setCountryid(shortcourses.getCountryid());
					hrmsEmployeeShortCoursesR.setCountryname(
							hrmsLocationCountryRepository.findById(shortcourses.getCountryid()).get().getName());
					hrmsEmployeeShortCoursesR.setDatend(simpleDateFormat.format(shortcourses.getDatend()));
					hrmsEmployeeShortCoursesR.setDatestart(simpleDateFormat.format(shortcourses.getDatestart()));
					hrmsEmployeeShortCoursesR.setDescription(shortcourses.getDescription());
					hrmsEmployeeShortCoursesR.setEmployeeid(shortcourses.getEmployeeid());
					hrmsEmployeeShortCoursesR.setEmployeename(hrmsEmployeeRepository
							.findById(shortcourses.getEmployeeid()).get().getFirstName().trim() + " "
							+ hrmsEmployeeRepository.findById(shortcourses.getEmployeeid()).get().getMiddleName().trim()
							+ " "
							+ hrmsEmployeeRepository.findById(shortcourses.getEmployeeid()).get().getLastName().trim());
					hrmsEmployeeShortCoursesR.setExpire(shortcourses.getExpire());
					hrmsEmployeeShortCoursesR.setId(shortcourses.getId());
					hrmsEmployeeShortCoursesR.setInstitution(shortcourses.getInstitution());
					shortcouseslist.add(hrmsEmployeeShortCoursesR);

				});

				employeeResponseDetailed.setHrmsEmployeeShortCoursesRlist(shortcouseslist);

			}

			// Family information

			if (hrmsEmployeeRelativeRepository.existsByEmployeeid(id)) {

				List<EmployeeRelativeResponse> rltivelist = new ArrayList<>();
				List<HrmsEmployeeRelative> relativelist = hrmsEmployeeRelativeRepository.findByEmployeeidAndActive(id,
						1);

				relativelist.forEach(dbemp2 -> {

					EmployeeRelativeResponse emprespo2 = new EmployeeRelativeResponse();

					emprespo2.setEmployeeid(dbemp2.getEmployeeid());
					emprespo2.setId(dbemp2.getId());
					emprespo2.setMobileNo(dbemp2.getPhoneprimary());
					if (dbemp2.getDob() != null) {
						emprespo2.setDob(simpleDateFormat.format(dbemp2.getDob()));
					}
					emprespo2.setPhoneprimary(dbemp2.getPhoneprimary());
					emprespo2.setRelativecategoryid(dbemp2.getRelativecategoryid());
					emprespo2.setRelativecategoryname(
							hrmsRelativeCategoryRepository.findById(dbemp2.getRelativecategoryid()).get().getName());

					emprespo2.setFirstname(dbemp2.getFirstname());
					emprespo2.setMiddlename(dbemp2.getMiddlename());
					emprespo2.setLastname(dbemp2.getLastname());
					emprespo2.setActive(dbemp2.getActive());
					emprespo2.setApproved(dbemp2.getApproved());
					int atachmentid = hrmsEmployeeRelativeAttachmentRepository.findByRelativeid(dbemp2.getId())
							.getAttachmentid();
					if (atachmentid != 0) {
						emprespo2.setAttachmentdescription(
								hrmsAttachmentRepository.findById(atachmentid).get().getDescription());

						emprespo2.setAttachmentid(atachmentid);
						emprespo2.setAttachmentname(hrmsAttachmentRepository.findById(atachmentid).get().getName());
						int attypeid = hrmsAttachmentRepository.findById(atachmentid).get().getAttachmenttypeid();
						emprespo2.setAttachmenttypeid(attypeid);
						emprespo2
								.setAttachmenttypename(hrmsAttachmentTypeRepository.findById(attypeid).get().getName());
						emprespo2.setUri(hrmsAttachmentRepository.findById(atachmentid).get().getUri());
					}

					StringBuilder builderfullname = new StringBuilder();
					builderfullname.append(dbemp2.getFirstname().trim());

					builderfullname.append("  " + dbemp2.getMiddlename().trim());
					builderfullname.append(" " + dbemp2.getLastname().trim());
					emprespo2.setFullname(builderfullname.toString());
					emprespo2.setApprovalComment(dbemp2.getApprovalComment());

					rltivelist.add(emprespo2);

				});

				employeeResponseDetailed.setEmployeeRelativeResponselist(rltivelist);

			}
			// Contact address information

			if (hrmsEmployeeAddressRepository.existsByEmployeeid(id)
					&& hrmsEmployeeContactRepository.existsByEmployeeid(id)) {

				List<HrmsEmployeeAddressContactResponse> empadresscontactlist = new ArrayList<>();
				List<HrmsEmployeeAddress> empadres = hrmsEmployeeAddressRepository.findByEmployeeidAndActive(id, 1);

				List<HrmsEmployeeContact> empcontact = hrmsEmployeeContactRepository.findByEmployeeidAndActive(id, 1);

				for (int i = 0; i < empadres.size(); i++) {
					HrmsEmployeeAddressContactResponse empcontactadress = new HrmsEmployeeAddressContactResponse();
					empcontactadress.setActive(empadres.get(i).getActive());
					empcontactadress.setApproved(empadres.get(i).getApproved());
					int adresid = empadres.get(i).getAddressid();
					empcontactadress.setAdressid(adresid);
					empcontactadress.setApprovalComment(empadres.get(i).getApprovalComment());
					empcontactadress
							.setAddressline1(hrmsLocationAddressRepository.findById(adresid).get().getAddressline1());

					empcontactadress
							.setAddressline2(hrmsLocationAddressRepository.findById(adresid).get().getAddressline2());

					int adrestypeid = hrmsLocationAddressRepository.findById(adresid).get().getAddresstypeid();

					empcontactadress.setAddresstypeid(adrestypeid);

					empcontactadress.setAddresstypename(
							hrmsLocationAddressTypeRepository.findById(adrestypeid).get().getName());
					int cityid = hrmsLocationAddressRepository.findById(adresid).get().getCityid();
					empcontactadress.setAdresscityid(cityid);
					empcontactadress.setAdresscity(hrmsLocationCityRepository.findById(cityid).get().getName());
					if (empadres.get(i).getDate_end() != null) {
						empcontactadress.setAdressdateend(empadres.get(i).getDate_end());
					}

					if (empadres.get(i).getDate_start() != null) {
						empcontactadress.setAdressdatestart(empadres.get(i).getDate_start());
					}

					empcontactadress.setAdressdescription(
							hrmsLocationAddressRepository.findById(adresid).get().getDescription());
					empcontactadress.setAdressdescriptionend(empadres.get(i).getDescription_end());
					empcontactadress.setAdressdescriptionstart(empadres.get(i).getDescription_start());
					empcontactadress.setEmployeeid(empadres.get(i).getEmployeeid());

					empcontactadress.setEmpadressid(empadres.get(i).getId());
					if (i < empcontact.size()) {
						empcontactadress.setEmpcontactid(empcontact.get(i).getId());
						if (empcontact.get(i).getDateend() != null) {
							empcontactadress.setContactdateend(empcontact.get(i).getDateend());
						}

						if (empcontact.get(i).getDatestart() != null) {
							empcontactadress.setContactdatestart(empcontact.get(i).getDatestart());
						}

						empcontactadress.setContactdescriptionend(empcontact.get(i).getDescriptionend());
						empcontactadress.setContactdescriptionstart(empcontact.get(i).getDescriptionstart());
						int contactid = empcontact.get(i).getContactid();
						empcontactadress.setContactid(contactid);

						empcontactadress.setContactemailaddress(
								hrmsContactRepository.findById(contactid).get().getEmailaddress());
						empcontactadress.setContactid(contactid);
						empcontactadress.setContactphoneprimary(
								hrmsContactRepository.findById(contactid).get().getPhoneprimary());
						empcontactadress.setContactphonesecondary(
								hrmsContactRepository.findById(contactid).get().getPhonesecondary());
						// int contypeid =
						// hrmsContactRepository.findById(contactid).get().getContacttypeid();
						// empcontactadress.setContacttypeid(contypeid);
						// empcontactadress.setContacttypename(hrmsContactTypeRepository.findById(contypeid).get().getName());
					}
					empcontactadress
							.setPostalcode(hrmsLocationAddressRepository.findById(adresid).get().getPostalcode());
					empadresscontactlist.add(empcontactadress);

				}
				employeeResponseDetailed.setHrmsEmployeeAddressContactResponselist(empadresscontactlist);

			}

			// employment history

			if (hrmsEmployeeEmploymentHistoryRepository.existsByEmployeeidAndActive(id, 1)) {

				List<HrmsEmployeeEmploymentHistoryResponseByEmpId> emphstrlist = new ArrayList<>();

				List<HrmsEmployeeEmploymentHistory> emplymentlist = hrmsEmployeeEmploymentHistoryRepository
						.findByEmployeeidAndActive(id, 1);

				emplymentlist.forEach(dbemp3 -> {

					HrmsEmployeeEmploymentHistoryResponseByEmpId emphstryrespo = new HrmsEmployeeEmploymentHistoryResponseByEmpId();

					emphstryrespo.setId(dbemp3.getId());
					emphstryrespo.setFromDate(dbemp3.getDatestart());
					emphstryrespo.setToDate(dbemp3.getDatend());
					emphstryrespo.setJobTitle(dbemp3.getPosition());
					emphstryrespo.setInstituteName(dbemp3.getEmployer());
					emphstryrespo.setApproved(dbemp3.getApproved());
					emphstryrespo.setApprovalComment(dbemp3.getApprovalComment());

					emphstrlist.add(emphstryrespo);

				});
				employeeResponseDetailed.setHrmsEmployeeEmploymentHistoryResponseByEmpIdlist(emphstrlist);
			}

			// add employee certification

			if (hrmsEmployeeCertificationRepository.existsByEmployeeidAndActive(id, 1)) {

				List<HrmsEmployeeCertificationResponse> empcert = new ArrayList<>();

				List<HrmsEmployeeCertification> empcerlist = hrmsEmployeeCertificationRepository
						.findByEmployeeidAndActive(id, 1);

				empcerlist.forEach(dbempcert -> {

					HrmsEmployeeCertificationResponse empcertrepo = new HrmsEmployeeCertificationResponse();
					empcertrepo.setActive(dbempcert.getActive());
					empcertrepo.setApproved(dbempcert.getApproved());

					int attachmentid = hrmsEmployeeCertificationAttachmentRepository
							.findByEmployeeidAndCertificationid(id, dbempcert.getId()).getAttachmentid();
					if (attachmentid != 0) {
						empcertrepo.setAttachmentname(hrmsAttachmentRepository.findById(attachmentid).get().getName());
					}

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

				employeeResponseDetailed.setHrmsEmployeeCertificationResponselist(empcert);
			}

			return ResponseEntity.status(HttpStatus.OK).body(employeeResponseDetailed);

		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
		}
	}

	@Override
	public ResponseEntity<HrmsEmployee> updatePhoto(int EmpId, String PhotoUri) {
		if (hrmsEmployeeRepository.existsByIdAndActive(EmpId, 1)) {
			HrmsEmployee hrmsEmployee = hrmsEmployeeRepository.findById(EmpId).get();
			hrmsEmployee.setPicture(PhotoUri);
			hrmsEmployee.setDate_updated(LocalDateTime.now());

			return ResponseEntity.status(HttpStatus.OK).body(hrmsEmployeeRepository.saveAndFlush(hrmsEmployee));

		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
		}
	}

	@Override
	public ResponseEntity<List<HrmsEmployee>> getallemployeelessdetails() {

		List<Integer> status = new ArrayList<>();

		status.add(2);// active
		status.add(9);// In active

		// return ResponseEntity.status(HttpStatus.OK)
		// .body(hrmsEmployeeRepository.findByActiveAndEmploymentstatusid(1, 1));

		return ResponseEntity.status(HttpStatus.OK)
				.body(hrmsEmployeeRepository.findByActiveAndEmploymentstatusidIn(1, status));
	}

	@Override
	public ResponseEntity<List<EmployeeForVideoConference>> getAllEmployeeforVideoConference() {

		List<EmployeeForVideoConference> employeeForVideoConferencelist = new ArrayList<>();

		List<HrmsEmployee> dbms = hrmsEmployeeRepository.findByActiveAndEmploymentstatusid(1, 2);

		for (HrmsEmployee dbm : dbms) {
			EmployeeForVideoConference employeeForVideoConference = new EmployeeForVideoConference();

			employeeForVideoConference.setEmail(dbm.getEmail());

			StringBuilder fullName = new StringBuilder();
			fullName.append(dbm.getFirstName().trim());

			fullName.append("  " + dbm.getMiddleName().trim());
			fullName.append(" " + dbm.getLastName().trim());
			employeeForVideoConference.setFullName(fullName.toString());
			employeeForVideoConference.setId(dbm.getId());

			employeeForVideoConferencelist.add(employeeForVideoConference);

		}

		return ResponseEntity.status(HttpStatus.OK).body(employeeForVideoConferencelist);
	}

	@Override
	public ResponseEntity<HrmsEmployee> updateEmployeePhoto(HrmsEmployee hrmsEmployee) {
		if (hrmsEmployeeRepository.existsByIdAndActive(hrmsEmployee.getId(), 1)) {

			HrmsEmployee dbm = hrmsEmployeeRepository.findByIdAndActive(hrmsEmployee.getId(), 1);

			dbm.setPicture(hrmsEmployee.getPicture());
			dbm.setDate_updated(LocalDateTime.now());
			return ResponseEntity.status(HttpStatus.OK).body(hrmsEmployeeRepository.saveAndFlush(dbm));

		} else {
			return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(hrmsEmployee);
		}
	}

}
