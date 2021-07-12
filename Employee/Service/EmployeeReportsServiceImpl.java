package com.Hrms.Employee.Service;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.Hrms.Employee.DTO.EmployeeAgeAverageperDirectorate;
import com.Hrms.Employee.DTO.EmployeeAgeAverageperDirectorateResponse;
import com.Hrms.Employee.DTO.EmployeeAgeDistributionTopStaff;
import com.Hrms.Employee.DTO.EmployeeAgeDistributionTopStaffResponse;
import com.Hrms.Employee.DTO.EmployeeExitReasonResponse;
import com.Hrms.Employee.DTO.EmployeeGenderDistributionTopStaffResponse;
import com.Hrms.Employee.DTO.EmployeeHeadCountDistribution;
import com.Hrms.Employee.DTO.EmployeeHeadCountDistributionperDirectorate;
import com.Hrms.Employee.DTO.EmployeeHeadCountDistributionperDirectorateResponse;
import com.Hrms.Employee.DTO.EmployeeHeadCountDistributionperGenderResponse;
import com.Hrms.Employee.DTO.EmployeeStaffDistributionByAge;
import com.Hrms.Employee.DTO.EmployeeStaffDistributionByAgeAndDirectorate;
import com.Hrms.Employee.DTO.EmployeeStaffDistributionByAgeAndDirectorateResponse;
import com.Hrms.Employee.DTO.EmployeeStaffDistributionByAgeResponse;
import com.Hrms.Employee.DTO.StaffGrossTurnOver;
import com.Hrms.Employee.DTO.StaffGrossTurnOverResponse;
import com.Hrms.Employee.Entity.HrmsDesignation;
import com.Hrms.Employee.Entity.HrmsEmployee;
import com.Hrms.Employee.Entity.HrmsEmployeeEmploymentStatus;
import com.Hrms.Employee.Entity.HrmsOrganisationOffice;
import com.Hrms.Employee.Entity.HrmsOrginisationUnit;
import com.Hrms.Employee.Repository.HrmsDesignationRepository;
import com.Hrms.Employee.Repository.HrmsEmployeeEmploymentStatusRepository;
import com.Hrms.Employee.Repository.HrmsEmployeeRepository;
import com.Hrms.Employee.Repository.HrmsOrganisationOfficeRepository;
import com.Hrms.Employee.Repository.HrmsOrganisationUnitTypeRepository;
import com.Hrms.Employee.Repository.HrmsOrginisationUnitRepository;

@Service
public class EmployeeReportsServiceImpl implements EmployeeReportsService {
	@Autowired
	private HrmsEmployeeRepository hrmsEmployeeRepository;

	@Autowired
	private HrmsOrganisationOfficeRepository hrmsOrganisationOfficeRepository;

	@Autowired
	private HrmsDesignationRepository hrmsDesignationRepository;

	@Autowired
	private HrmsOrganisationUnitTypeRepository hrmsOrganisationUnitTypeRepository;

	@Autowired
	private HrmsOrginisationUnitRepository hrmsOrginisationUnitRepository;

	@Autowired
	private HrmsEmployeeEmploymentStatusRepository hrmsEmployeeEmploymentStatusRepository;

	@Override
	public ResponseEntity<EmployeeHeadCountDistributionperGenderResponse> findHeadCountPerGender() {
		EmployeeHeadCountDistributionperGenderResponse employeeHeadCountDistributionperGenderResponse = new EmployeeHeadCountDistributionperGenderResponse();

		List<EmployeeHeadCountDistribution> employeeHeadCountDistributionlist = new ArrayList<>();
		List<HrmsOrganisationOffice> dbmo = hrmsOrganisationOfficeRepository.findByActiveOrderById(1);
		Integer[] status = { 2, 12, 11 };
		Integer[] gender = { 2, 1 };
		dbmo.forEach(dbm -> {

			EmployeeHeadCountDistribution employeeHeadCountDistribution = new EmployeeHeadCountDistribution();
			String location = dbm.getName();
			int femalenumber = hrmsEmployeeRepository
					.countByDutystationidAndGenderidAndEmploymentstatusidInAndActive(dbm.getId(), 2, status, 1);
			int malenumber = hrmsEmployeeRepository
					.countByDutystationidAndGenderidAndEmploymentstatusidInAndActive(dbm.getId(), 1, status, 1);
			int totallocationnumber = (femalenumber + malenumber);

			employeeHeadCountDistribution.setId(dbm.getId());
			employeeHeadCountDistribution.setFemalenumber(femalenumber);
			employeeHeadCountDistribution.setMalenumber(malenumber);
			employeeHeadCountDistribution.setLocation(location);
			employeeHeadCountDistribution.setTotallocationnumber(totallocationnumber);

			employeeHeadCountDistributionlist.add(employeeHeadCountDistribution);

		});
		int totalnumber = hrmsEmployeeRepository.countByGenderidInAndEmploymentstatusidInAndActive(gender, status, 1);

		int totalfemale = hrmsEmployeeRepository.countByGenderidAndEmploymentstatusidInAndActive(2, status, 1);
		int totalmale = hrmsEmployeeRepository.countByGenderidAndEmploymentstatusidInAndActive(1, status, 1);

		employeeHeadCountDistributionperGenderResponse.setTotalfemale(totalfemale);
		employeeHeadCountDistributionperGenderResponse.setTotalmale(totalmale);

		employeeHeadCountDistributionperGenderResponse
				.setEmployeeHeadCountDistributionlist(employeeHeadCountDistributionlist);
		employeeHeadCountDistributionperGenderResponse.setTotalnumber(totalnumber);

		return ResponseEntity.status(HttpStatus.OK).body(employeeHeadCountDistributionperGenderResponse);
	}

	@Override
	public ResponseEntity<EmployeeGenderDistributionTopStaffResponse> findTopStaffCountPerGender() {
		List<Integer> directors = new ArrayList<>();

		List<Integer> heads = new ArrayList<>();
		Integer[] status = { 2, 12, 11 };
		Integer[] gender = { 2, 1 };

		List<HrmsDesignation> dbmo = hrmsDesignationRepository.findByActive(1);

		dbmo.forEach(dbm -> {
			if (dbm.getName().toLowerCase().contains("director")) {
				directors.add(dbm.getId());

			}

			if (dbm.getName().toLowerCase().contains("head")) {
				heads.add(dbm.getId());

			}

		});

		EmployeeGenderDistributionTopStaffResponse employeeGenderDistributionTopStaffResponse = new EmployeeGenderDistributionTopStaffResponse();
		int maledirectors = hrmsEmployeeRepository.countByGenderidAndEmploymentstatusidInAndDesignationIdInAndActive(1,
				status, directors, 1);
		int femaleheads = hrmsEmployeeRepository.countByGenderidAndEmploymentstatusidInAndDesignationIdInAndActive(2,
				status, heads, 1);

		int femaledirectors = hrmsEmployeeRepository
				.countByGenderidAndEmploymentstatusidInAndDesignationIdInAndActive(2, status, directors, 1);
		int maleheads = hrmsEmployeeRepository.countByGenderidAndEmploymentstatusidInAndDesignationIdInAndActive(1,
				status, heads, 1);
		employeeGenderDistributionTopStaffResponse.setFemaledirectors(femaledirectors);
		employeeGenderDistributionTopStaffResponse.setFemaleheads(femaleheads);
		employeeGenderDistributionTopStaffResponse.setMaledirectors(maledirectors);
		employeeGenderDistributionTopStaffResponse.setMaleheads(maleheads);

		return ResponseEntity.status(HttpStatus.OK).body(employeeGenderDistributionTopStaffResponse);
	}

	@Override
	public ResponseEntity<EmployeeHeadCountDistributionperDirectorateResponse> findHeadCountPerDirectorate() {
		Integer[] unittype = { 10, 14 };

		Integer[] status = { 2, 12, 11 };
		EmployeeHeadCountDistributionperDirectorateResponse employeeHeadCountDistributionperDirectorateResponselist = new EmployeeHeadCountDistributionperDirectorateResponse();

		List<EmployeeHeadCountDistributionperDirectorate> employeeHeadCountDistributionperDirectoratelist = new ArrayList<>();
		List<HrmsOrginisationUnit> hrmsOrginisationUnitlist = hrmsOrginisationUnitRepository
				.findByActiveAndUnitTypeidIn(1, unittype);// here we have all department and director office

		// total number of employee
		int totalnumberg = hrmsEmployeeRepository.countByEmploymentstatusidInAndActive(status, 1);

		hrmsOrginisationUnitlist.forEach(dbm -> {

			List<HrmsOrginisationUnit> hrmsOrginisationUnitlist1 = hrmsOrginisationUnitRepository
					.findByparentIdAndActive(dbm.getId(), 1);
			List<Integer> subsections = new ArrayList<>();
			subsections.add(dbm.getId());
			Double departmentpercentage = 0.00; // initialize department %

			hrmsOrginisationUnitlist1.forEach(dbm1 -> { // get all sections or unit under this department
				if (!dbm.getAbbreviation().toLowerCase().contains("dgos")) {
					subsections.add(dbm1.getId());
				}
			});

			EmployeeHeadCountDistributionperDirectorate employeeHeadCountDistributionperDirectorate = new EmployeeHeadCountDistributionperDirectorate();

			int totalnumber = hrmsEmployeeRepository.countByUnitIdInAndEmploymentstatusidInAndActive(subsections,
					status, 1);
			if (totalnumberg != 0) {
				departmentpercentage = (double) Math.round((100 * totalnumber / (double) totalnumberg));

			}
			employeeHeadCountDistributionperDirectorate.setDepartmentname(dbm.getName());
			employeeHeadCountDistributionperDirectorate.setId(dbm.getId());
			employeeHeadCountDistributionperDirectorate.setTotalnumber(totalnumber);
			employeeHeadCountDistributionperDirectorate.setDepartmentpercentage(departmentpercentage);
			employeeHeadCountDistributionperDirectoratelist.add(employeeHeadCountDistributionperDirectorate);

		});

		employeeHeadCountDistributionperDirectorateResponselist
				.setEmployeeHeadCountDistributionperDirectoratelist(employeeHeadCountDistributionperDirectoratelist);
		employeeHeadCountDistributionperDirectorateResponselist.setTotalnumberg(totalnumberg);

		return ResponseEntity.status(HttpStatus.OK).body(employeeHeadCountDistributionperDirectorateResponselist);
	}

	@Override
	public ResponseEntity<EmployeeAgeAverageperDirectorateResponse> findAgeAveragePerDirectorate() {

		Integer[] unittype = { 10, 14 };

		Integer[] status = { 2, 12, 11 };
		EmployeeAgeAverageperDirectorateResponse employeeAgeAverageperDirectorateResponse = new EmployeeAgeAverageperDirectorateResponse();

		List<EmployeeAgeAverageperDirectorate> employeeAgeAverageperDirectoratelist = new ArrayList<>();
		List<HrmsOrginisationUnit> hrmsOrginisationUnitlist = hrmsOrginisationUnitRepository
				.findByActiveAndUnitTypeidIn(1, unittype);// here we have all department and director office
		hrmsOrginisationUnitlist.forEach(dbm -> {

			List<HrmsOrginisationUnit> hrmsOrginisationUnitlist1 = hrmsOrginisationUnitRepository
					.findByparentIdAndActive(dbm.getId(), 1);
			List<Integer> subsections = new ArrayList<>();
			subsections.add(dbm.getId());

			hrmsOrginisationUnitlist1.forEach(dbm1 -> { // get all sections or unit under this department
				if (!dbm.getAbbreviation().toLowerCase().contains("dgos")) {
					subsections.add(dbm1.getId());
				}
			});
			if (subsections != null) {
				List<HrmsEmployee> dbms = hrmsEmployeeRepository.findByEmploymentstatusidInAndUnitIdInAndActive(status,
						subsections, 1);
				int yearsome = 0;
				for (HrmsEmployee dbm2 : dbms) {
					// Converting String to Date

					SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
					String dob = formatter.format(dbm2.getDob());
					Date date = null;
					try {
						date = formatter.parse(dob);
					} catch (ParseException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					// Converting obtained Date object to LocalDate object
					Instant instant = date.toInstant();
					ZonedDateTime zone = instant.atZone(ZoneId.systemDefault());
					LocalDate givenDate = zone.toLocalDate();
					// Calculating the difference between given date to current date.
					Period period = Period.between(givenDate, LocalDate.now());
					yearsome = yearsome + period.getYears();

				}
				EmployeeAgeAverageperDirectorate employeeAgeAverageperDirectorate = new EmployeeAgeAverageperDirectorate();
				employeeAgeAverageperDirectorate.setDirectorate(dbm.getName());
				int totalcount = hrmsEmployeeRepository.countByUnitIdInAndEmploymentstatusidInAndActive(subsections,
						status, 1);
				DecimalFormat df = new DecimalFormat("#.##");
				df.setRoundingMode(RoundingMode.CEILING);
				Double average = 0.0;
				if (totalcount != 0) {
					average = Double.parseDouble(df.format((double) yearsome / totalcount));

				}
				employeeAgeAverageperDirectorate.setAgeaverage(average);
				employeeAgeAverageperDirectorate.setId(dbm.getId());

				employeeAgeAverageperDirectoratelist.add(employeeAgeAverageperDirectorate);
			}

		});
		employeeAgeAverageperDirectorateResponse
				.setEmployeeAgeAverageperDirectoratelist(employeeAgeAverageperDirectoratelist);

		return ResponseEntity.status(HttpStatus.OK).body(employeeAgeAverageperDirectorateResponse);
	}

	@Override
	public ResponseEntity<EmployeeAgeDistributionTopStaffResponse> findAgeDistributionTopStaff() {

		EmployeeAgeDistributionTopStaffResponse employeeAgeDistributionTopStaffResponse = new EmployeeAgeDistributionTopStaffResponse();

		List<EmployeeAgeDistributionTopStaff> employeeAgeDistributionTopStafflist = new ArrayList<>();

		// get list of topstaff unit ids
		List<Integer> directors = new ArrayList<>();

		List<Integer> heads = new ArrayList<>();
		Integer[] status = { 2, 12, 11 };
		Integer[] gender = { 2, 1 };

		List<HrmsDesignation> dbmo = hrmsDesignationRepository.findByActive(1);

		dbmo.forEach(dbm -> {
			if (dbm.getName().toLowerCase().contains("director")) {
				directors.add(dbm.getId());

			}

			if (dbm.getName().toLowerCase().contains("head")) {
				heads.add(dbm.getId());

			}

		});
		int totaldirectors = 0;
		int totalheads = 0;
		int totaltopstaff = 0;
		// get all employee list who are active

		List<String> agerange = new ArrayList<>();
		agerange.add("18-20");
		agerange.add("21-30");
		agerange.add("31-40");
		agerange.add("41-50");
		agerange.add("51-55");
		agerange.add("56-59");
		agerange.add("60-75");
		for (String ar : agerange) {

			if (ar.equals("18-20")) {
				int directorsc = 0;
				int headsc = 0;
				int totalc = 0;
				EmployeeAgeDistributionTopStaff employeeAgeDistributionTopStaff = new EmployeeAgeDistributionTopStaff();
				// count directors and heads who are in 18 to 20 years range
				// directors
				List<HrmsEmployee> dbms = hrmsEmployeeRepository.findByEmploymentstatusidInAndActive(status, 1);

				for (HrmsEmployee dbm : dbms) {
					int age = getAge(dbm.getDob());
					if ((age >= 18 && age <= 20) && present(directors, dbm.getDesignationId())) {
						directorsc = directorsc + 1;

					}

					if ((age >= 18 && age <= 20) && present(heads, dbm.getDesignationId())) {
						headsc = headsc + 1;

					}

				}

				totaldirectors = totaldirectors + directorsc;
				totalheads = totalheads + headsc;

				employeeAgeDistributionTopStaff.setAgegroup(ar);
				employeeAgeDistributionTopStaff.setDirectors(directorsc);
				employeeAgeDistributionTopStaff.setHeads(headsc);
				totalc = (directorsc + headsc);
				employeeAgeDistributionTopStaff.setTotalinAgeGroup(totalc);

				employeeAgeDistributionTopStafflist.add(employeeAgeDistributionTopStaff);

			}

			if (ar.equals("21-30")) {
				int directorsc = 0;
				int headsc = 0;
				int totalc = 0;
				EmployeeAgeDistributionTopStaff employeeAgeDistributionTopStaff = new EmployeeAgeDistributionTopStaff();
				// count directors and heads who are in 21 to 30 years range
				// directors
				List<HrmsEmployee> dbms = hrmsEmployeeRepository.findByEmploymentstatusidInAndActive(status, 1);

				for (HrmsEmployee dbm : dbms) {
					int age = getAge(dbm.getDob());
					if ((age >= 21 && age <= 30) && present(directors, dbm.getDesignationId())) {
						directorsc = directorsc + 1;

					}

					if ((age >= 21 && age <= 30) && present(heads, dbm.getDesignationId())) {
						headsc = headsc + 1;

					}

				}

				totaldirectors = totaldirectors + directorsc;
				totalheads = totalheads + headsc;

				employeeAgeDistributionTopStaff.setAgegroup(ar);
				employeeAgeDistributionTopStaff.setDirectors(directorsc);
				employeeAgeDistributionTopStaff.setHeads(headsc);
				totalc = (directorsc + headsc);
				employeeAgeDistributionTopStaff.setTotalinAgeGroup(totalc);

				employeeAgeDistributionTopStafflist.add(employeeAgeDistributionTopStaff);

			}

			if (ar.equals("31-40")) {
				int directorsc = 0;
				int headsc = 0;
				int totalc = 0;
				EmployeeAgeDistributionTopStaff employeeAgeDistributionTopStaff = new EmployeeAgeDistributionTopStaff();
				// count directors and heads who are in 31 to 40 years range
				// directors
				List<HrmsEmployee> dbms = hrmsEmployeeRepository.findByEmploymentstatusidInAndActive(status, 1);

				for (HrmsEmployee dbm : dbms) {
					int age = getAge(dbm.getDob());
					if ((age >= 31 && age <= 40) && present(directors, dbm.getDesignationId())) {
						directorsc = directorsc + 1;

					}

					if ((age >= 31 && age <= 40) && present(heads, dbm.getDesignationId())) {
						headsc = headsc + 1;

					}

				}

				totaldirectors = totaldirectors + directorsc;
				totalheads = totalheads + headsc;

				employeeAgeDistributionTopStaff.setAgegroup(ar);
				employeeAgeDistributionTopStaff.setDirectors(directorsc);
				employeeAgeDistributionTopStaff.setHeads(headsc);
				totalc = (directorsc + headsc);
				employeeAgeDistributionTopStaff.setTotalinAgeGroup(totalc);

				employeeAgeDistributionTopStafflist.add(employeeAgeDistributionTopStaff);

			}

			if (ar.equals("41-50")) {
				int directorsc = 0;
				int headsc = 0;
				int totalc = 0;
				EmployeeAgeDistributionTopStaff employeeAgeDistributionTopStaff = new EmployeeAgeDistributionTopStaff();
				// count directors and heads who are in 41 to 50 years range
				// directors
				List<HrmsEmployee> dbms = hrmsEmployeeRepository.findByEmploymentstatusidInAndActive(status, 1);

				for (HrmsEmployee dbm : dbms) {
					int age = getAge(dbm.getDob());
					if ((age >= 41 && age <= 50) && present(directors, dbm.getDesignationId())) {

						directorsc = directorsc + 1;

					}

					if ((age >= 41 && age <= 50) && present(heads, dbm.getDesignationId())) {
						headsc = headsc + 1;

					}

				}

				totaldirectors = totaldirectors + directorsc;
				totalheads = totalheads + headsc;

				employeeAgeDistributionTopStaff.setAgegroup(ar);
				employeeAgeDistributionTopStaff.setDirectors(directorsc);
				employeeAgeDistributionTopStaff.setHeads(headsc);
				totalc = (directorsc + headsc);
				employeeAgeDistributionTopStaff.setTotalinAgeGroup(totalc);

				employeeAgeDistributionTopStafflist.add(employeeAgeDistributionTopStaff);

			}

			if (ar.equals("51-55")) {
				int directorsc = 0;
				int headsc = 0;
				int totalc = 0;
				EmployeeAgeDistributionTopStaff employeeAgeDistributionTopStaff = new EmployeeAgeDistributionTopStaff();
				// count directors and heads who are in 51 to 55 years range
				// directors
				List<HrmsEmployee> dbms = hrmsEmployeeRepository.findByEmploymentstatusidInAndActive(status, 1);

				for (HrmsEmployee dbm : dbms) {
					int age = getAge(dbm.getDob());
					if ((age >= 51 && age <= 55) && present(directors, dbm.getDesignationId())) {
						directorsc = directorsc + 1;

					}

					if ((age >= 51 && age <= 55) && present(heads, dbm.getDesignationId())) {
						headsc = headsc + 1;

					}

				}

				totaldirectors = totaldirectors + directorsc;
				totalheads = totalheads + headsc;

				employeeAgeDistributionTopStaff.setAgegroup(ar);
				employeeAgeDistributionTopStaff.setDirectors(directorsc);
				employeeAgeDistributionTopStaff.setHeads(headsc);
				totalc = (directorsc + headsc);
				employeeAgeDistributionTopStaff.setTotalinAgeGroup(totalc);

				employeeAgeDistributionTopStafflist.add(employeeAgeDistributionTopStaff);

			}

			if (ar.equals("56-59")) {
				int directorsc = 0;
				int headsc = 0;
				int totalc = 0;
				EmployeeAgeDistributionTopStaff employeeAgeDistributionTopStaff = new EmployeeAgeDistributionTopStaff();
				// count directors and heads who are in 56 to 59 years range
				// directors
				List<HrmsEmployee> dbms = hrmsEmployeeRepository.findByEmploymentstatusidInAndActive(status, 1);

				for (HrmsEmployee dbm : dbms) {
					int age = getAge(dbm.getDob());
					if ((age >= 56 && age <= 59) && present(directors, dbm.getDesignationId())) {
						directorsc = directorsc + 1;

					}

					if ((age >= 56 && age <= 59) && present(heads, dbm.getDesignationId())) {
						headsc = headsc + 1;

					}

				}

				totaldirectors = totaldirectors + directorsc;
				totalheads = totalheads + headsc;

				employeeAgeDistributionTopStaff.setAgegroup(ar);
				employeeAgeDistributionTopStaff.setDirectors(directorsc);
				employeeAgeDistributionTopStaff.setHeads(headsc);
				totalc = (directorsc + headsc);
				employeeAgeDistributionTopStaff.setTotalinAgeGroup(totalc);

				employeeAgeDistributionTopStafflist.add(employeeAgeDistributionTopStaff);

			}

			if (ar.equals("60-75")) {
				int directorsc = 0;
				int headsc = 0;
				int totalc = 0;
				EmployeeAgeDistributionTopStaff employeeAgeDistributionTopStaff = new EmployeeAgeDistributionTopStaff();
				// count directors and heads who are in 60 to 75 years range
				// directors
				List<HrmsEmployee> dbms = hrmsEmployeeRepository.findByEmploymentstatusidInAndActive(status, 1);

				for (HrmsEmployee dbm : dbms) {
					int age = getAge(dbm.getDob());
					if ((age >= 60 && age <= 75) && present(directors, dbm.getDesignationId())) {
						directorsc = directorsc + 1;

					}

					if ((age >= 60 && age <= 75) && present(heads, dbm.getDesignationId())) {
						headsc = headsc + 1;

					}

				}

				totaldirectors = totaldirectors + directorsc;
				totalheads = totalheads + headsc;

				employeeAgeDistributionTopStaff.setAgegroup(ar);
				employeeAgeDistributionTopStaff.setDirectors(directorsc);
				employeeAgeDistributionTopStaff.setHeads(headsc);
				totalc = (directorsc + headsc);
				employeeAgeDistributionTopStaff.setTotalinAgeGroup(totalc);

				employeeAgeDistributionTopStafflist.add(employeeAgeDistributionTopStaff);

			}

		}
		totaltopstaff = totaldirectors + totalheads;
		employeeAgeDistributionTopStaffResponse.setAgeGroupdetails(employeeAgeDistributionTopStafflist);
		employeeAgeDistributionTopStaffResponse.setTotaldirectors(totaldirectors);
		employeeAgeDistributionTopStaffResponse.setTotalheads(totalheads);
		employeeAgeDistributionTopStaffResponse.setTotaltopstaff(totaltopstaff);

		return ResponseEntity.status(HttpStatus.OK).body(employeeAgeDistributionTopStaffResponse);
	}

	public int getAge(Date dob) {
		int years = 0;
		if (dob != null) {
			Calendar birthDay = Calendar.getInstance();
			birthDay.setTimeInMillis(dob.getTime());

			// create calendar object for current day
			long currentTime = System.currentTimeMillis();
			Calendar now = Calendar.getInstance();
			now.setTimeInMillis(currentTime);

			// Get difference between years
			years = now.get(Calendar.YEAR) - birthDay.get(Calendar.YEAR);
		}

		return years;
	}

	public Boolean present(List<Integer> units, int a) {
		Boolean found = false;
		for (int n : units) {
			if (n == a) {
				found = true;
			}
		}

		return found;
	}

	@Override
	public ResponseEntity<EmployeeStaffDistributionByAgeResponse> findStaffDistributionByAge() {

		EmployeeStaffDistributionByAgeResponse employeeStaffDistributionByAgeResponse = new EmployeeStaffDistributionByAgeResponse();

		List<EmployeeStaffDistributionByAge> employeeStaffDistributionByAgelist = new ArrayList<>();

		Integer[] status = { 2, 12, 11 };
		Integer[] gender = { 2, 1 };
		int totalnumberg = hrmsEmployeeRepository.countByEmploymentstatusidInAndActive(status, 1);

		List<String> agerange = new ArrayList<>();
		agerange.add("18-25");
		agerange.add("26-35");
		agerange.add("36-45");
		agerange.add("46-55");
		agerange.add("56-60");
		agerange.add("61-75");
		for (String ar : agerange) {

			if (ar.equals("18-25")) {
				int totalnumber = 0;
				Double agegrouppercentage = 0.00;

				EmployeeStaffDistributionByAge employeeStaffDistributionByAge = new EmployeeStaffDistributionByAge();

				// count directors and heads who are in 18 to 25 years range
				// directors
				List<HrmsEmployee> dbms = hrmsEmployeeRepository.findByEmploymentstatusidInAndActive(status, 1);

				for (HrmsEmployee dbm : dbms) {
					int age = getAge(dbm.getDob());
					if ((age >= 18 && age <= 25)) {

						totalnumber = totalnumber + 1;

					}

				}

				employeeStaffDistributionByAge.setAgegroup(ar);
				employeeStaffDistributionByAge.setTotalnumber(totalnumber);
				if (totalnumberg != 0) {

					agegrouppercentage = (double) Math.round((100 * totalnumber / (double) totalnumberg));

					employeeStaffDistributionByAge.setAgegrouppercentage(agegrouppercentage);
				}

				employeeStaffDistributionByAgelist.add(employeeStaffDistributionByAge);

			}

			if (ar.equals("26-35")) {
				int totalnumber = 0;
				Double agegrouppercentage = 0.00;

				EmployeeStaffDistributionByAge employeeStaffDistributionByAge = new EmployeeStaffDistributionByAge();

				// count directors and heads who are in 26 to 35 years range
				// directors
				List<HrmsEmployee> dbms = hrmsEmployeeRepository.findByEmploymentstatusidInAndActive(status, 1);

				for (HrmsEmployee dbm : dbms) {
					int age = getAge(dbm.getDob());
					if ((age >= 26 && age <= 35)) {

						totalnumber = totalnumber + 1;

					}

				}

				employeeStaffDistributionByAge.setAgegroup(ar);
				employeeStaffDistributionByAge.setTotalnumber(totalnumber);
				if (totalnumberg != 0) {

					agegrouppercentage = (double) Math.round((100 * totalnumber / (double) totalnumberg));

					employeeStaffDistributionByAge.setAgegrouppercentage(agegrouppercentage);
				}

				employeeStaffDistributionByAgelist.add(employeeStaffDistributionByAge);

			}

			if (ar.equals("36-45")) {
				int totalnumber = 0;
				Double agegrouppercentage = 0.00;

				EmployeeStaffDistributionByAge employeeStaffDistributionByAge = new EmployeeStaffDistributionByAge();

				// count directors and heads who are in 36 to 45 years range
				// directors
				List<HrmsEmployee> dbms = hrmsEmployeeRepository.findByEmploymentstatusidInAndActive(status, 1);

				for (HrmsEmployee dbm : dbms) {
					int age = getAge(dbm.getDob());
					if ((age >= 36 && age <= 45)) {

						totalnumber = totalnumber + 1;

					}

				}

				employeeStaffDistributionByAge.setAgegroup(ar);
				employeeStaffDistributionByAge.setTotalnumber(totalnumber);
				if (totalnumberg != 0) {

					agegrouppercentage = (double) Math.round((100 * totalnumber / (double) totalnumberg));

					employeeStaffDistributionByAge.setAgegrouppercentage(agegrouppercentage);
				}

				employeeStaffDistributionByAgelist.add(employeeStaffDistributionByAge);

			}

			if (ar.equals("46-55")) {
				int totalnumber = 0;
				Double agegrouppercentage = 0.00;

				EmployeeStaffDistributionByAge employeeStaffDistributionByAge = new EmployeeStaffDistributionByAge();

				// count directors and heads who are in 46 to 55 years range
				// directors
				List<HrmsEmployee> dbms = hrmsEmployeeRepository.findByEmploymentstatusidInAndActive(status, 1);

				for (HrmsEmployee dbm : dbms) {
					int age = getAge(dbm.getDob());
					if ((age >= 46 && age <= 55)) {

						totalnumber = totalnumber + 1;

					}

				}

				employeeStaffDistributionByAge.setAgegroup(ar);
				employeeStaffDistributionByAge.setTotalnumber(totalnumber);
				if (totalnumberg != 0) {

					agegrouppercentage = (double) Math.round((100 * totalnumber / (double) totalnumberg));

					employeeStaffDistributionByAge.setAgegrouppercentage(agegrouppercentage);
				}

				employeeStaffDistributionByAgelist.add(employeeStaffDistributionByAge);

			}

			if (ar.equals("56-60")) {
				int totalnumber = 0;
				Double agegrouppercentage = 0.00;

				EmployeeStaffDistributionByAge employeeStaffDistributionByAge = new EmployeeStaffDistributionByAge();

				// count directors and heads who are in 56 to 60 years range
				// directors
				List<HrmsEmployee> dbms = hrmsEmployeeRepository.findByEmploymentstatusidInAndActive(status, 1);

				for (HrmsEmployee dbm : dbms) {
					int age = getAge(dbm.getDob());
					if ((age >= 56 && age <= 60)) {

						totalnumber = totalnumber + 1;

					}

				}

				employeeStaffDistributionByAge.setAgegroup(ar);
				employeeStaffDistributionByAge.setTotalnumber(totalnumber);
				if (totalnumberg != 0) {

					agegrouppercentage = (double) Math.round((100 * totalnumber / (double) totalnumberg));

					employeeStaffDistributionByAge.setAgegrouppercentage(agegrouppercentage);
				}

				employeeStaffDistributionByAgelist.add(employeeStaffDistributionByAge);

			}

			if (ar.equals("61-75")) {
				int totalnumber = 0;
				Double agegrouppercentage = 0.00;

				EmployeeStaffDistributionByAge employeeStaffDistributionByAge = new EmployeeStaffDistributionByAge();

				// count directors and heads who are in 61 to 75 years range
				// directors
				List<HrmsEmployee> dbms = hrmsEmployeeRepository.findByEmploymentstatusidInAndActive(status, 1);

				for (HrmsEmployee dbm : dbms) {
					int age = getAge(dbm.getDob());
					if ((age > 60 && age <= 75)) {

						totalnumber = totalnumber + 1;

					}

				}

				employeeStaffDistributionByAge.setAgegroup(ar);
				employeeStaffDistributionByAge.setTotalnumber(totalnumber);
				if (totalnumberg != 0) {

					agegrouppercentage = (double) Math.round((100 * totalnumber / (double) totalnumberg));

					employeeStaffDistributionByAge.setAgegrouppercentage(agegrouppercentage);
				}

				employeeStaffDistributionByAgelist.add(employeeStaffDistributionByAge);

			}

		}

		employeeStaffDistributionByAgeResponse
				.setEmployeeStaffDistributionByAgelist(employeeStaffDistributionByAgelist);

		return ResponseEntity.status(HttpStatus.OK).body(employeeStaffDistributionByAgeResponse);
	}

	@Override
	public ResponseEntity<List<EmployeeStaffDistributionByAgeAndDirectorateResponse>> findStaffDistributionByAgeAndDirectorate() {
		Integer[] unittype = { 10, 14 };

		Integer[] status = { 2, 12, 11 };

		List<HrmsOrginisationUnit> hrmsOrginisationUnitlist = hrmsOrginisationUnitRepository
				.findByActiveAndUnitTypeidIn(1, unittype);// here we have all department and director office

		// total number of employee
		int totalnumberg = hrmsEmployeeRepository.countByEmploymentstatusidInAndActive(status, 1);

		List<String> agerange = new ArrayList<>();
		agerange.add("18-25");
		agerange.add("26-35");
		agerange.add("36-45");
		agerange.add("46-55");
		agerange.add("56-60");
		agerange.add("61-75");
		List<EmployeeStaffDistributionByAgeAndDirectorateResponse> employeeStaffDistributionByAgeAndDirectorateResponselist = new ArrayList<>();
		for (String ar : agerange) {

			EmployeeStaffDistributionByAgeAndDirectorateResponse employeeStaffDistributionByAgeAndDirectorateResponse = new EmployeeStaffDistributionByAgeAndDirectorateResponse();
			if (ar.equals("18-25")) {

				Double agegroupDepartmentPercentage = 0.00;
				int totalDepartmentNumber = 0;

				employeeStaffDistributionByAgeAndDirectorateResponse.setAgegroup(ar);

				List<EmployeeStaffDistributionByAgeAndDirectorate> employeeStaffDistributionByAgeAndDirectoratelist = new ArrayList<>();

				for (HrmsOrginisationUnit dbm : hrmsOrginisationUnitlist) {

					List<HrmsOrginisationUnit> hrmsOrginisationUnitlist1 = hrmsOrginisationUnitRepository
							.findByparentIdAndActive(dbm.getId(), 1);
					List<Integer> subsections = new ArrayList<>();
					subsections.add(dbm.getId());

					hrmsOrginisationUnitlist1.forEach(dbm1 -> { // get all sections or unit under this department
						if (!dbm.getAbbreviation().toLowerCase().contains("dgos")) {
							subsections.add(dbm1.getId());
						}
					});
					List<HrmsEmployee> dbms = hrmsEmployeeRepository
							.findByUnitIdInAndEmploymentstatusidInAndActive(subsections, status, 1);
					for (HrmsEmployee dbm1 : dbms) {
						int age = getAge(dbm1.getDob());
						if ((age >= 18 && age <= 25)) {

							totalDepartmentNumber = totalDepartmentNumber + 1;

						}
					}

					EmployeeStaffDistributionByAgeAndDirectorate employeeStaffDistributionByAgeAndDirectorate = new EmployeeStaffDistributionByAgeAndDirectorate();
					employeeStaffDistributionByAgeAndDirectorate
							.setDepartmentName(hrmsOrginisationUnitRepository.findById(dbm.getId()).get().getName());

					employeeStaffDistributionByAgeAndDirectorate.setTotalDepartmentNumber(totalDepartmentNumber);

					if (totalnumberg != 0) {

						agegroupDepartmentPercentage = (double) Math
								.round((100 * totalDepartmentNumber / (double) totalnumberg));

						employeeStaffDistributionByAgeAndDirectorate
								.setAgegroupDepartmentPercentage(agegroupDepartmentPercentage);
					}

					employeeStaffDistributionByAgeAndDirectorate
							.setAgegroupDepartmentPercentage(agegroupDepartmentPercentage);

					employeeStaffDistributionByAgeAndDirectoratelist.add(employeeStaffDistributionByAgeAndDirectorate);

				}

				employeeStaffDistributionByAgeAndDirectorateResponse
						.setEmployeeStaffDistributionByAgeAndDirectoratelist(
								employeeStaffDistributionByAgeAndDirectoratelist);

			}

			if (ar.equals("26-35")) {

				Double agegroupDepartmentPercentage = 0.00;
				int totalDepartmentNumber = 0;

				employeeStaffDistributionByAgeAndDirectorateResponse.setAgegroup(ar);

				List<EmployeeStaffDistributionByAgeAndDirectorate> employeeStaffDistributionByAgeAndDirectoratelist = new ArrayList<>();

				for (HrmsOrginisationUnit dbm : hrmsOrginisationUnitlist) {

					List<HrmsOrginisationUnit> hrmsOrginisationUnitlist1 = hrmsOrginisationUnitRepository
							.findByparentIdAndActive(dbm.getId(), 1);
					List<Integer> subsections = new ArrayList<>();
					subsections.add(dbm.getId());

					hrmsOrginisationUnitlist1.forEach(dbm1 -> { // get all sections or unit under this department
						if (!dbm.getAbbreviation().toLowerCase().contains("dgos")) {
							subsections.add(dbm1.getId());
						}
					});
					List<HrmsEmployee> dbms = hrmsEmployeeRepository
							.findByUnitIdInAndEmploymentstatusidInAndActive(subsections, status, 1);
					for (HrmsEmployee dbm1 : dbms) {
						int age = getAge(dbm1.getDob());
						if ((age >= 26 && age <= 35)) {

							totalDepartmentNumber = totalDepartmentNumber + 1;

						}
					}

					EmployeeStaffDistributionByAgeAndDirectorate employeeStaffDistributionByAgeAndDirectorate = new EmployeeStaffDistributionByAgeAndDirectorate();
					employeeStaffDistributionByAgeAndDirectorate
							.setDepartmentName(hrmsOrginisationUnitRepository.findById(dbm.getId()).get().getName());

					employeeStaffDistributionByAgeAndDirectorate.setTotalDepartmentNumber(totalDepartmentNumber);

					if (totalnumberg != 0) {

						agegroupDepartmentPercentage = (double) Math
								.round((100 * totalDepartmentNumber / (double) totalnumberg));

						employeeStaffDistributionByAgeAndDirectorate
								.setAgegroupDepartmentPercentage(agegroupDepartmentPercentage);
					}

					employeeStaffDistributionByAgeAndDirectorate
							.setAgegroupDepartmentPercentage(agegroupDepartmentPercentage);

					employeeStaffDistributionByAgeAndDirectoratelist.add(employeeStaffDistributionByAgeAndDirectorate);

				}

				employeeStaffDistributionByAgeAndDirectorateResponse
						.setEmployeeStaffDistributionByAgeAndDirectoratelist(
								employeeStaffDistributionByAgeAndDirectoratelist);

			}

			if (ar.equals("36-45")) {

				Double agegroupDepartmentPercentage = 0.00;
				int totalDepartmentNumber = 0;

				employeeStaffDistributionByAgeAndDirectorateResponse.setAgegroup(ar);

				List<EmployeeStaffDistributionByAgeAndDirectorate> employeeStaffDistributionByAgeAndDirectoratelist = new ArrayList<>();

				for (HrmsOrginisationUnit dbm : hrmsOrginisationUnitlist) {

					List<HrmsOrginisationUnit> hrmsOrginisationUnitlist1 = hrmsOrginisationUnitRepository
							.findByparentIdAndActive(dbm.getId(), 1);
					List<Integer> subsections = new ArrayList<>();
					subsections.add(dbm.getId());

					hrmsOrginisationUnitlist1.forEach(dbm1 -> { // get all sections or unit under this department
						if (!dbm.getAbbreviation().toLowerCase().contains("dgos")) {
							subsections.add(dbm1.getId());
						}
					});
					List<HrmsEmployee> dbms = hrmsEmployeeRepository
							.findByUnitIdInAndEmploymentstatusidInAndActive(subsections, status, 1);
					for (HrmsEmployee dbm1 : dbms) {
						int age = getAge(dbm1.getDob());
						if ((age >= 36 && age <= 45)) {

							totalDepartmentNumber = totalDepartmentNumber + 1;

						}
					}

					EmployeeStaffDistributionByAgeAndDirectorate employeeStaffDistributionByAgeAndDirectorate = new EmployeeStaffDistributionByAgeAndDirectorate();
					employeeStaffDistributionByAgeAndDirectorate
							.setDepartmentName(hrmsOrginisationUnitRepository.findById(dbm.getId()).get().getName());

					employeeStaffDistributionByAgeAndDirectorate.setTotalDepartmentNumber(totalDepartmentNumber);

					if (totalnumberg != 0) {

						agegroupDepartmentPercentage = (double) Math
								.round((100 * totalDepartmentNumber / (double) totalnumberg));

						employeeStaffDistributionByAgeAndDirectorate
								.setAgegroupDepartmentPercentage(agegroupDepartmentPercentage);
					}

					employeeStaffDistributionByAgeAndDirectorate
							.setAgegroupDepartmentPercentage(agegroupDepartmentPercentage);

					employeeStaffDistributionByAgeAndDirectoratelist.add(employeeStaffDistributionByAgeAndDirectorate);

				}

				employeeStaffDistributionByAgeAndDirectorateResponse
						.setEmployeeStaffDistributionByAgeAndDirectoratelist(
								employeeStaffDistributionByAgeAndDirectoratelist);

			}

			if (ar.equals("46-55")) {

				Double agegroupDepartmentPercentage = 0.00;
				int totalDepartmentNumber = 0;

				employeeStaffDistributionByAgeAndDirectorateResponse.setAgegroup(ar);

				List<EmployeeStaffDistributionByAgeAndDirectorate> employeeStaffDistributionByAgeAndDirectoratelist = new ArrayList<>();

				for (HrmsOrginisationUnit dbm : hrmsOrginisationUnitlist) {

					List<HrmsOrginisationUnit> hrmsOrginisationUnitlist1 = hrmsOrginisationUnitRepository
							.findByparentIdAndActive(dbm.getId(), 1);
					List<Integer> subsections = new ArrayList<>();
					subsections.add(dbm.getId());

					hrmsOrginisationUnitlist1.forEach(dbm1 -> { // get all sections or unit under this department
						if (!dbm.getAbbreviation().toLowerCase().contains("dgos")) {
							subsections.add(dbm1.getId());
						}
					});
					List<HrmsEmployee> dbms = hrmsEmployeeRepository
							.findByUnitIdInAndEmploymentstatusidInAndActive(subsections, status, 1);
					for (HrmsEmployee dbm1 : dbms) {
						int age = getAge(dbm1.getDob());
						if ((age >= 46 && age <= 55)) {

							totalDepartmentNumber = totalDepartmentNumber + 1;

						}
					}

					EmployeeStaffDistributionByAgeAndDirectorate employeeStaffDistributionByAgeAndDirectorate = new EmployeeStaffDistributionByAgeAndDirectorate();
					employeeStaffDistributionByAgeAndDirectorate
							.setDepartmentName(hrmsOrginisationUnitRepository.findById(dbm.getId()).get().getName());

					employeeStaffDistributionByAgeAndDirectorate.setTotalDepartmentNumber(totalDepartmentNumber);

					if (totalnumberg != 0) {

						agegroupDepartmentPercentage = (double) Math
								.round((100 * totalDepartmentNumber / (double) totalnumberg));

						employeeStaffDistributionByAgeAndDirectorate
								.setAgegroupDepartmentPercentage(agegroupDepartmentPercentage);
					}

					employeeStaffDistributionByAgeAndDirectorate
							.setAgegroupDepartmentPercentage(agegroupDepartmentPercentage);

					employeeStaffDistributionByAgeAndDirectoratelist.add(employeeStaffDistributionByAgeAndDirectorate);

				}

				employeeStaffDistributionByAgeAndDirectorateResponse
						.setEmployeeStaffDistributionByAgeAndDirectoratelist(
								employeeStaffDistributionByAgeAndDirectoratelist);

			}

			if (ar.equals("56-60")) {

				Double agegroupDepartmentPercentage = 0.00;
				int totalDepartmentNumber = 0;

				employeeStaffDistributionByAgeAndDirectorateResponse.setAgegroup(ar);

				List<EmployeeStaffDistributionByAgeAndDirectorate> employeeStaffDistributionByAgeAndDirectoratelist = new ArrayList<>();

				for (HrmsOrginisationUnit dbm : hrmsOrginisationUnitlist) {

					List<HrmsOrginisationUnit> hrmsOrginisationUnitlist1 = hrmsOrginisationUnitRepository
							.findByparentIdAndActive(dbm.getId(), 1);
					List<Integer> subsections = new ArrayList<>();
					subsections.add(dbm.getId());

					hrmsOrginisationUnitlist1.forEach(dbm1 -> { // get all sections or unit under this department
						if (!dbm.getAbbreviation().toLowerCase().contains("dgos")) {
							subsections.add(dbm1.getId());
						}
					});
					List<HrmsEmployee> dbms = hrmsEmployeeRepository
							.findByUnitIdInAndEmploymentstatusidInAndActive(subsections, status, 1);
					for (HrmsEmployee dbm1 : dbms) {
						int age = getAge(dbm1.getDob());
						if ((age >= 56 && age <= 60)) {

							totalDepartmentNumber = totalDepartmentNumber + 1;

						}
					}

					EmployeeStaffDistributionByAgeAndDirectorate employeeStaffDistributionByAgeAndDirectorate = new EmployeeStaffDistributionByAgeAndDirectorate();
					employeeStaffDistributionByAgeAndDirectorate
							.setDepartmentName(hrmsOrginisationUnitRepository.findById(dbm.getId()).get().getName());

					employeeStaffDistributionByAgeAndDirectorate.setTotalDepartmentNumber(totalDepartmentNumber);

					if (totalnumberg != 0) {

						agegroupDepartmentPercentage = (double) Math
								.round((100 * totalDepartmentNumber / (double) totalnumberg));

						employeeStaffDistributionByAgeAndDirectorate
								.setAgegroupDepartmentPercentage(agegroupDepartmentPercentage);
					}

					employeeStaffDistributionByAgeAndDirectorate
							.setAgegroupDepartmentPercentage(agegroupDepartmentPercentage);

					employeeStaffDistributionByAgeAndDirectoratelist.add(employeeStaffDistributionByAgeAndDirectorate);

				}

				employeeStaffDistributionByAgeAndDirectorateResponse
						.setEmployeeStaffDistributionByAgeAndDirectoratelist(
								employeeStaffDistributionByAgeAndDirectoratelist);

			}

			if (ar.equals("61-75")) {

				Double agegroupDepartmentPercentage = 0.00;
				int totalDepartmentNumber = 0;

				employeeStaffDistributionByAgeAndDirectorateResponse.setAgegroup(ar);

				List<EmployeeStaffDistributionByAgeAndDirectorate> employeeStaffDistributionByAgeAndDirectoratelist = new ArrayList<>();

				for (HrmsOrginisationUnit dbm : hrmsOrginisationUnitlist) {

					List<HrmsOrginisationUnit> hrmsOrginisationUnitlist1 = hrmsOrginisationUnitRepository
							.findByparentIdAndActive(dbm.getId(), 1);
					List<Integer> subsections = new ArrayList<>();
					subsections.add(dbm.getId());

					hrmsOrginisationUnitlist1.forEach(dbm1 -> { // get all sections or unit under this department
						if (!dbm.getAbbreviation().toLowerCase().contains("dgos")) {
							subsections.add(dbm1.getId());
						}
					});
					List<HrmsEmployee> dbms = hrmsEmployeeRepository
							.findByUnitIdInAndEmploymentstatusidInAndActive(subsections, status, 1);
					for (HrmsEmployee dbm1 : dbms) {
						int age = getAge(dbm1.getDob());
						if ((age >= 61 && age <= 75)) {

							totalDepartmentNumber = totalDepartmentNumber + 1;

						}
					}

					EmployeeStaffDistributionByAgeAndDirectorate employeeStaffDistributionByAgeAndDirectorate = new EmployeeStaffDistributionByAgeAndDirectorate();
					employeeStaffDistributionByAgeAndDirectorate
							.setDepartmentName(hrmsOrginisationUnitRepository.findById(dbm.getId()).get().getName());

					employeeStaffDistributionByAgeAndDirectorate.setTotalDepartmentNumber(totalDepartmentNumber);

					if (totalnumberg != 0) {

						agegroupDepartmentPercentage = (double) Math
								.round((100 * totalDepartmentNumber / (double) totalnumberg));

						employeeStaffDistributionByAgeAndDirectorate
								.setAgegroupDepartmentPercentage(agegroupDepartmentPercentage);
					}

					employeeStaffDistributionByAgeAndDirectorate
							.setAgegroupDepartmentPercentage(agegroupDepartmentPercentage);

					employeeStaffDistributionByAgeAndDirectoratelist.add(employeeStaffDistributionByAgeAndDirectorate);

				}

				employeeStaffDistributionByAgeAndDirectorateResponse
						.setEmployeeStaffDistributionByAgeAndDirectoratelist(
								employeeStaffDistributionByAgeAndDirectoratelist);

			}

			employeeStaffDistributionByAgeAndDirectorateResponselist
					.add(employeeStaffDistributionByAgeAndDirectorateResponse);
		}

		return ResponseEntity.status(HttpStatus.OK).body(employeeStaffDistributionByAgeAndDirectorateResponselist);
	}

	@Override
	public ResponseEntity<EmployeeExitReasonResponse> findReasonForEmployeesExit() {

		EmployeeExitReasonResponse employeeExitReasonResponse = new EmployeeExitReasonResponse();

		int inactiveNumber = hrmsEmployeeRepository.countByEmploymentstatusidAndActive(9, 1);

		int individualTrainingNumber = hrmsEmployeeRepository.countByEmploymentstatusidAndActive(11, 1);

		int corporateTrainingNumber = hrmsEmployeeRepository.countByEmploymentstatusidAndActive(12, 1);

		int retirementNumber = hrmsEmployeeRepository.countByEmploymentstatusidAndActive(21, 1);

		int resignationNumber = hrmsEmployeeRepository.countByEmploymentstatusidAndActive(22, 1);

		int terminationNumber = hrmsEmployeeRepository.countByEmploymentstatusidAndActive(1, 1);

		int transferNumber = hrmsEmployeeRepository.countByEmploymentstatusidAndActive(3, 1);

		int deathNumber = hrmsEmployeeRepository.countByEmploymentstatusidAndActive(4, 1);

		employeeExitReasonResponse.setCorporateTrainingNumber(corporateTrainingNumber);
		employeeExitReasonResponse.setDeathNumber(deathNumber);
		employeeExitReasonResponse.setInactiveNumber(inactiveNumber);
		employeeExitReasonResponse.setIndividualTrainingNumber(individualTrainingNumber);
		employeeExitReasonResponse.setResignationNumber(resignationNumber);
		employeeExitReasonResponse.setRetirementNumber(retirementNumber);
		employeeExitReasonResponse.setTerminationNumber(terminationNumber);
		employeeExitReasonResponse.setTransferNumber(transferNumber);

		return ResponseEntity.status(HttpStatus.OK).body(employeeExitReasonResponse);
	}

	@Override
	public ResponseEntity<StaffGrossTurnOverResponse> findStaffGrossTurnOver() {
		StaffGrossTurnOverResponse staffGrossTurnOverResponse = new StaffGrossTurnOverResponse();
		List<StaffGrossTurnOver> staffGrossTurnOverlist = new ArrayList<>();
		Double averageLTO = 0.00;
		Integer[] status = { 2, 12, 11 };
		Integer[] statusleavers = { 22, 1, 4, 9 };
		Double grossTurnOverg = 0.00;

		List<String> financialyearrange = new ArrayList<>();
		financialyearrange.add("2018-19");
		financialyearrange.add("2019-20");
		financialyearrange.add("2020-21");
		financialyearrange.add("2021-22");
		for (String fy : financialyearrange) {
			int newEmployees = 0;
			int employeesPresent = 0;
			int voluntaryAndInvoluntaryLeavers = 0;
			Double grossTurnOver = 0.00;

			StaffGrossTurnOver staffGrossTurnOver = new StaffGrossTurnOver();
			if (fy.equals("2018-19")) {
				// List<HrmsEmployee> dbms =
				// hrmsEmployeeRepository.findByEmploymentstatusidIn(status);
				List<HrmsEmployee> dbms = hrmsEmployeeRepository.findByActive(1);
				for (HrmsEmployee dbm : dbms) {
					Calendar doe = Calendar.getInstance();

					Calendar doexit = Calendar.getInstance();

					if (dbm.getDateofemployment() != null) {
						doe.setTimeInMillis(dbm.getDateofemployment().getTime()); // DOE = date of employment

						int gap = (2019 - doe.get(Calendar.YEAR));
						int monthoe = doe.get(Calendar.MONTH) + 1;

						if ((gap == 1 && monthoe >= 7) || (gap == 0 && monthoe < 7)
								&& hrmsEmployeeEmploymentStatusRepository.existsByEmployeeidAndActive(dbm.getId(), 1)) {
							newEmployees = newEmployees + 1; // new employee within 18 to 19

						}

						// now find employee who left the employer voluntary or involuntary
						if (hrmsEmployeeEmploymentStatusRepository.existsByEmployeeidAndStatusidInAndActive(dbm.getId(),
								statusleavers, 1)) {
							HrmsEmployeeEmploymentStatus hrmsEmployeeEmploymentStatus = hrmsEmployeeEmploymentStatusRepository
									.findFirstByEmployeeidAndStatusidInAndActive(dbm.getId(), statusleavers, 1);
							if (hrmsEmployeeEmploymentStatus.getDatestart() != null) {
								doexit.setTimeInMillis(hrmsEmployeeEmploymentStatus.getDatestart().getTime()); // DOE =
																												// date
																												// of
																												// employment
								int gapexit = (2019 - doexit.get(Calendar.YEAR));
								int monthexit = doe.get(Calendar.MONTH) + 1;
								if ((gapexit == 1 && monthexit >= 7) || (gapexit == 0 && monthexit < 7)) {
									voluntaryAndInvoluntaryLeavers = voluntaryAndInvoluntaryLeavers + 1; // exited
																											// employee
																											// within 18
																											// to 19

								}

							}

						}

						// Employee present at a particular year interval

						Calendar yoedate = Calendar.getInstance();// year of employment date
						yoedate.setTimeInMillis(dbm.getDateofemployment().getTime());
						int yoe = yoedate.get(Calendar.YEAR); // year of employment
						int moe = yoedate.get(Calendar.MONTH) + 1;
						if ((yoe < 2019 || (yoe == 2019 && moe < 7))
								&& hrmsEmployeeEmploymentStatusRepository.existsByEmployeeidAndActive(dbm.getId(), 1)) {
							HrmsEmployeeEmploymentStatus hrmsEmployeeEmploymentStatus = hrmsEmployeeEmploymentStatusRepository
									.findFirstByEmployeeidAndActive(dbm.getId(), 1);
							if (hrmsEmployeeEmploymentStatus.getDatestart() != null
									&& (hrmsEmployeeEmploymentStatus.getStatusid() == 22
											|| hrmsEmployeeEmploymentStatus.getStatusid() == 1
											|| hrmsEmployeeEmploymentStatus.getStatusid() == 9
											|| hrmsEmployeeEmploymentStatus.getStatusid() == 4
											|| hrmsEmployeeEmploymentStatus.getStatusid() == 21)) {
								Calendar yoexitdate = Calendar.getInstance();// year of employment date
								yoexitdate.setTimeInMillis(hrmsEmployeeEmploymentStatus.getDatestart().getTime());
								int yoexit = yoexitdate.get(Calendar.YEAR); // year of exit
								int moexit = yoexitdate.get(Calendar.MONTH) + 1; // month of exit

								if (yoexit > 2019 || (yoexit == 2019 && moexit >= 7)) {
									employeesPresent = employeesPresent + 1;
								}

							}
							if (hrmsEmployeeEmploymentStatus.getDatestart() != null
									&& (hrmsEmployeeEmploymentStatus.getStatusid() != 22
											&& hrmsEmployeeEmploymentStatus.getStatusid() != 1
											&& hrmsEmployeeEmploymentStatus.getStatusid() != 4
											&& hrmsEmployeeEmploymentStatus.getStatusid() != 21
											&& hrmsEmployeeEmploymentStatus.getStatusid() != 9)) {
								employeesPresent = employeesPresent + 1; // here we know this employee was employed
																			// before 2019 but his or her ststus of
																			// employment is not yet changed to exit
							}

						}

					}
				}
				if (employeesPresent != 0) {
					grossTurnOver = (newEmployees / (double) employeesPresent);
				}
				grossTurnOverg = grossTurnOverg + grossTurnOver;
				staffGrossTurnOver.setFinancialYear(fy);
				staffGrossTurnOver.setNewEmployees(newEmployees);
				staffGrossTurnOver.setEmployeesPresent(employeesPresent);
				staffGrossTurnOver.setVoluntaryAndInvoluntaryLeavers(voluntaryAndInvoluntaryLeavers);
				staffGrossTurnOver.setGrossTurnOver(grossTurnOver);

				staffGrossTurnOverlist.add(staffGrossTurnOver);

			}

			if (fy.equals("2019-20")) {
				// List<HrmsEmployee> dbms =
				// hrmsEmployeeRepository.findByEmploymentstatusidIn(status);
				List<HrmsEmployee> dbms = hrmsEmployeeRepository.findByActive(1);
				for (HrmsEmployee dbm : dbms) {
					Calendar doe = Calendar.getInstance();

					Calendar doexit = Calendar.getInstance();

					if (dbm.getDateofemployment() != null) {
						doe.setTimeInMillis(dbm.getDateofemployment().getTime()); // DOE = date of employment
						int gap = (2020 - doe.get(Calendar.YEAR));
						int monthoe = doe.get(Calendar.MONTH) + 1;
						if ((gap == 1 && monthoe >= 7) || (gap == 0 && monthoe < 7)
								&& hrmsEmployeeEmploymentStatusRepository.existsByEmployeeidAndActive(dbm.getId(), 1)) {
							newEmployees = newEmployees + 1; // new employee within 18 to 19

						}

						// now find employee who left the employer voluntary or involuntary
						if (hrmsEmployeeEmploymentStatusRepository.existsByEmployeeidAndStatusidInAndActive(dbm.getId(),
								statusleavers, 1)) {
							HrmsEmployeeEmploymentStatus hrmsEmployeeEmploymentStatus = hrmsEmployeeEmploymentStatusRepository
									.findFirstByEmployeeidAndStatusidInAndActive(dbm.getId(), statusleavers, 1);
							if (hrmsEmployeeEmploymentStatus.getDatestart() != null) {
								doexit.setTimeInMillis(hrmsEmployeeEmploymentStatus.getDatestart().getTime()); // DOE =
																												// date
																												// of
																												// employment
								int gapexit = (2020 - doexit.get(Calendar.YEAR));
								int monthexit = doe.get(Calendar.MONTH) + 1;
								if ((gapexit == 1 && monthexit >= 7) || (gapexit == 0 && monthexit < 7)) {
									voluntaryAndInvoluntaryLeavers = voluntaryAndInvoluntaryLeavers + 1; // exited
																											// employee
																											// within 18
																											// to 19

								}

							}

						}

						// Employee present at a particular year interval

						Calendar yoedate = Calendar.getInstance();// year of employment date
						yoedate.setTimeInMillis(dbm.getDateofemployment().getTime());
						int yoe = yoedate.get(Calendar.YEAR); // year of employment
						int moe = yoedate.get(Calendar.MONTH) + 1;
						if ((yoe < 2020 || (yoe == 2020 && moe < 7))
								&& hrmsEmployeeEmploymentStatusRepository.existsByEmployeeidAndActive(dbm.getId(), 1)) {
							HrmsEmployeeEmploymentStatus hrmsEmployeeEmploymentStatus = hrmsEmployeeEmploymentStatusRepository
									.findFirstByEmployeeidAndActive(dbm.getId(), 1);
							if (hrmsEmployeeEmploymentStatus.getDatestart() != null
									&& (hrmsEmployeeEmploymentStatus.getStatusid() == 22
											|| hrmsEmployeeEmploymentStatus.getStatusid() == 1
											|| hrmsEmployeeEmploymentStatus.getStatusid() == 4
											|| hrmsEmployeeEmploymentStatus.getStatusid() == 21
											|| hrmsEmployeeEmploymentStatus.getStatusid() == 9)) {
								Calendar yoexitdate = Calendar.getInstance();// year of employment date
								yoexitdate.setTimeInMillis(hrmsEmployeeEmploymentStatus.getDatestart().getTime());
								int yoexit = yoexitdate.get(Calendar.YEAR); // year of exit
								int moexit = yoexitdate.get(Calendar.MONTH) + 1; // month of exit

								if (yoexit > 2020 || (yoexit == 2020 && moexit >= 7)) {
									employeesPresent = employeesPresent + 1;
								}

							}
							if (hrmsEmployeeEmploymentStatus.getDatestart() != null
									&& (hrmsEmployeeEmploymentStatus.getStatusid() != 22
											&& hrmsEmployeeEmploymentStatus.getStatusid() != 1
											&& hrmsEmployeeEmploymentStatus.getStatusid() != 4
											&& hrmsEmployeeEmploymentStatus.getStatusid() != 9)
									&& hrmsEmployeeEmploymentStatus.getStatusid() != 21) {
								employeesPresent = employeesPresent + 1; // here we know this employee was employed
																			// before 2019 but his or her ststus of
																			// employment is not yet changed to exit
							}

						}

					}
				}
				if (employeesPresent != 0) {
					grossTurnOver = (newEmployees / (double) employeesPresent);
				}
				grossTurnOverg = grossTurnOverg + grossTurnOver;
				staffGrossTurnOver.setFinancialYear(fy);
				staffGrossTurnOver.setNewEmployees(newEmployees);
				staffGrossTurnOver.setEmployeesPresent(employeesPresent);
				staffGrossTurnOver.setVoluntaryAndInvoluntaryLeavers(voluntaryAndInvoluntaryLeavers);
				staffGrossTurnOver.setGrossTurnOver(grossTurnOver);

				staffGrossTurnOverlist.add(staffGrossTurnOver);

			}

			if (fy.equals("2020-21")) {
				// List<HrmsEmployee> dbms =
				// hrmsEmployeeRepository.findByEmploymentstatusidIn(status);
				List<HrmsEmployee> dbms = hrmsEmployeeRepository.findByActive(1);
				for (HrmsEmployee dbm : dbms) {
					Calendar doe = Calendar.getInstance();

					Calendar doexit = Calendar.getInstance();

					if (dbm.getDateofemployment() != null) {
						doe.setTimeInMillis(dbm.getDateofemployment().getTime()); // DOE = date of employment
						int gap = (2021 - doe.get(Calendar.YEAR));
						int monthoe = doe.get(Calendar.MONTH) + 1;
						if ((gap == 1 && monthoe >= 7) || (gap == 0 && monthoe < 7)
								&& hrmsEmployeeEmploymentStatusRepository.existsByEmployeeidAndActive(dbm.getId(), 1)) {
							newEmployees = newEmployees + 1; // new employee within 21 to 20

						}

						// now find employee who left the employer voluntary or involuntary
						if (hrmsEmployeeEmploymentStatusRepository.existsByEmployeeidAndStatusidInAndActive(dbm.getId(),
								statusleavers, 1)) {
							HrmsEmployeeEmploymentStatus hrmsEmployeeEmploymentStatus = hrmsEmployeeEmploymentStatusRepository
									.findFirstByEmployeeidAndStatusidInAndActive(dbm.getId(), statusleavers, 1);
							if (hrmsEmployeeEmploymentStatus.getDatestart() != null) {
								doexit.setTimeInMillis(hrmsEmployeeEmploymentStatus.getDatestart().getTime()); // DOE =
																												// date
																												// of
																												// employment
								int gapexit = (2021 - doexit.get(Calendar.YEAR));
								int monthexit = doe.get(Calendar.MONTH) + 1;
								if ((gapexit == 1 && monthexit >= 7) || (gapexit == 0 && monthexit < 7)) {
									voluntaryAndInvoluntaryLeavers = voluntaryAndInvoluntaryLeavers + 1; // exited
																											// employee
																											// within 18
																											// to 19

								}

							}

						}

						// Employee present at a particular year interval

						Calendar yoedate = Calendar.getInstance();// year of employment date
						yoedate.setTimeInMillis(dbm.getDateofemployment().getTime());
						int yoe = yoedate.get(Calendar.YEAR); // year of employment
						int moe = yoedate.get(Calendar.MONTH) + 1;
						if ((yoe < 2021 || (yoe == 2021 && moe < 7))
								&& hrmsEmployeeEmploymentStatusRepository.existsByEmployeeidAndActive(dbm.getId(), 1)) {
							HrmsEmployeeEmploymentStatus hrmsEmployeeEmploymentStatus = hrmsEmployeeEmploymentStatusRepository
									.findFirstByEmployeeidAndActive(dbm.getId(), 1);
							if (hrmsEmployeeEmploymentStatus.getDatestart() != null
									&& (hrmsEmployeeEmploymentStatus.getStatusid() == 22
											|| hrmsEmployeeEmploymentStatus.getStatusid() == 1
											|| hrmsEmployeeEmploymentStatus.getStatusid() == 4
											|| hrmsEmployeeEmploymentStatus.getStatusid() == 21
											|| hrmsEmployeeEmploymentStatus.getStatusid() == 9)) {
								Calendar yoexitdate = Calendar.getInstance();// year of employment date
								yoexitdate.setTimeInMillis(hrmsEmployeeEmploymentStatus.getDatestart().getTime());
								int yoexit = yoexitdate.get(Calendar.YEAR); // year of exit
								int moexit = yoexitdate.get(Calendar.MONTH) + 1; // month of exit

								if (yoexit > 2021 || (yoexit == 2021 && moexit >= 7)) {
									employeesPresent = employeesPresent + 1;
								}

							}
							if (hrmsEmployeeEmploymentStatus.getDatestart() != null
									&& (hrmsEmployeeEmploymentStatus.getStatusid() != 22
											&& hrmsEmployeeEmploymentStatus.getStatusid() != 1
											&& hrmsEmployeeEmploymentStatus.getStatusid() != 4
											&& hrmsEmployeeEmploymentStatus.getStatusid() != 21
											&& hrmsEmployeeEmploymentStatus.getStatusid() != 9)) {
								employeesPresent = employeesPresent + 1; // here we know this employee was employed
																			// before 2019 but his or her ststus of
																			// employment is not yet changed to exit
							}

						}

					}
				}
				if (employeesPresent != 0) {
					grossTurnOver = (newEmployees / (double) employeesPresent);
				}
				grossTurnOverg = grossTurnOverg + grossTurnOver;
				staffGrossTurnOver.setFinancialYear(fy);
				staffGrossTurnOver.setNewEmployees(newEmployees);
				staffGrossTurnOver.setEmployeesPresent(employeesPresent);
				staffGrossTurnOver.setVoluntaryAndInvoluntaryLeavers(voluntaryAndInvoluntaryLeavers);
				staffGrossTurnOver.setGrossTurnOver(grossTurnOver);

				staffGrossTurnOverlist.add(staffGrossTurnOver);

			}

			if (fy.equals("2021-22")) {
				// List<HrmsEmployee> dbms =
				// hrmsEmployeeRepository.findByEmploymentstatusidIn(status);
				List<HrmsEmployee> dbms = hrmsEmployeeRepository.findByActive(1);
				for (HrmsEmployee dbm : dbms) {
					Calendar doe = Calendar.getInstance();

					Calendar doexit = Calendar.getInstance();

					if (dbm.getDateofemployment() != null) {
						doe.setTimeInMillis(dbm.getDateofemployment().getTime()); // DOE = date of employment
						int gap = (2022 - doe.get(Calendar.YEAR));
						int monthoe = doe.get(Calendar.MONTH) + 1;
						if ((gap == 1 && monthoe >= 7) || (gap == 0 && monthoe < 7)
								&& hrmsEmployeeEmploymentStatusRepository.existsByEmployeeidAndActive(dbm.getId(), 1)) {
							newEmployees = newEmployees + 1; // new employee within 22 to 21

						}

						// now find employee who left the employer voluntary or involuntary
						if (hrmsEmployeeEmploymentStatusRepository.existsByEmployeeidAndStatusidInAndActive(dbm.getId(),
								statusleavers, 1)) {
							HrmsEmployeeEmploymentStatus hrmsEmployeeEmploymentStatus = hrmsEmployeeEmploymentStatusRepository
									.findFirstByEmployeeidAndStatusidInAndActive(dbm.getId(), statusleavers, 1);
							if (hrmsEmployeeEmploymentStatus.getDatestart() != null) {
								doexit.setTimeInMillis(hrmsEmployeeEmploymentStatus.getDatestart().getTime()); // DOE =
																												// date
																												// of
																												// employment
								int gapexit = (2022 - doexit.get(Calendar.YEAR));
								int monthexit = doe.get(Calendar.MONTH) + 1;

								if ((gapexit == 1 && monthexit >= 7) || (gapexit == 0 && monthexit < 7)) {
									voluntaryAndInvoluntaryLeavers = voluntaryAndInvoluntaryLeavers + 1; // exited
																											// employee
																											// within 21
																											// to 22

								}

							}

						}

						// Employee present at a particular year interval

						Calendar yoedate = Calendar.getInstance();// year of employment date
						yoedate.setTimeInMillis(dbm.getDateofemployment().getTime());
						int yoe = yoedate.get(Calendar.YEAR); // year of employment
						int moe = yoedate.get(Calendar.MONTH) + 1;
						if ((yoe < 2022 || (yoe == 2022 && moe < 7))
								&& hrmsEmployeeEmploymentStatusRepository.existsByEmployeeidAndActive(dbm.getId(), 1)) {
							HrmsEmployeeEmploymentStatus hrmsEmployeeEmploymentStatus = hrmsEmployeeEmploymentStatusRepository
									.findFirstByEmployeeidAndActive(dbm.getId(), 1);
							if (hrmsEmployeeEmploymentStatus.getDatestart() != null
									&& (hrmsEmployeeEmploymentStatus.getStatusid() == 22
											|| hrmsEmployeeEmploymentStatus.getStatusid() == 1
											|| hrmsEmployeeEmploymentStatus.getStatusid() == 4
											|| hrmsEmployeeEmploymentStatus.getStatusid() == 21
											|| hrmsEmployeeEmploymentStatus.getStatusid() == 9)) {
								Calendar yoexitdate = Calendar.getInstance();// year of employment date
								yoexitdate.setTimeInMillis(hrmsEmployeeEmploymentStatus.getDatestart().getTime());
								int yoexit = yoexitdate.get(Calendar.YEAR); // year of exit
								int moexit = yoexitdate.get(Calendar.MONTH) + 1; // month of exit

								if (yoexit > 2022 || (yoexit == 2022 && moexit >= 7)) {
									employeesPresent = employeesPresent + 1;
								}

							}
							if (hrmsEmployeeEmploymentStatus.getDatestart() != null
									&& (hrmsEmployeeEmploymentStatus.getStatusid() != 22
											&& hrmsEmployeeEmploymentStatus.getStatusid() != 1
											&& hrmsEmployeeEmploymentStatus.getStatusid() != 4
											&& hrmsEmployeeEmploymentStatus.getStatusid() != 21
											&& hrmsEmployeeEmploymentStatus.getStatusid() != 9)) {
								employeesPresent = employeesPresent + 1; // here we know this employee was employed
																			// before 2022 but his or her ststus of
																			// employment is not yet changed to exit
							}

						}

					}
				}
				if (employeesPresent != 0) {
					grossTurnOver = (newEmployees / (double) employeesPresent);
				}
				grossTurnOverg = grossTurnOverg + grossTurnOver;
				staffGrossTurnOver.setFinancialYear(fy);
				staffGrossTurnOver.setNewEmployees(newEmployees);
				staffGrossTurnOver.setEmployeesPresent(employeesPresent);
				staffGrossTurnOver.setVoluntaryAndInvoluntaryLeavers(voluntaryAndInvoluntaryLeavers);
				staffGrossTurnOver.setGrossTurnOver(grossTurnOver);

				staffGrossTurnOverlist.add(staffGrossTurnOver);

			}

		}
		if (financialyearrange.size() != 0) {
			averageLTO = (grossTurnOverg / (double) financialyearrange.size());
		}

		staffGrossTurnOverResponse.setStaffGrossTurnOverlist(staffGrossTurnOverlist);

		staffGrossTurnOverResponse.setAverageLTO(averageLTO);

		return ResponseEntity.status(HttpStatus.OK).body(staffGrossTurnOverResponse);
	}

	@Override
	public ResponseEntity<?> findyearAndMonth() {
		int newEmployees = 0;
		List<Integer> empids = new ArrayList<>();

		List<HrmsEmployee> dbms = hrmsEmployeeRepository.findByActive(1);
		for (HrmsEmployee dbm : dbms) {
			// Employee present at a particular year interval

			Calendar doe = Calendar.getInstance();

			Calendar doexit = Calendar.getInstance();

			if (dbm.getDateofemployment() != null) {
				doe.setTimeInMillis(dbm.getDateofemployment().getTime()); // DOE = date of employment
				int gap = (2020 - doe.get(Calendar.YEAR));
				int monthoe = doe.get(Calendar.MONTH) + 1;
				if ((gap == 1 && monthoe >= 7) || (gap == 0 && monthoe < 7)
						&& hrmsEmployeeEmploymentStatusRepository.existsByEmployeeidAndActive(dbm.getId(), 1)) {
					newEmployees = newEmployees + 1; // new employee within 18 to 19

					empids.add(dbm.getId());

				}

			}

		}
		System.out.println(" employeesPresent  = " + newEmployees + "  " + empids);

		String dateBeforeString = "2020-11-24";
		String dateAfterString = "2020-11-29";

		// Parsing the date
		LocalDate dateBefore = LocalDate.parse(dateBeforeString);
		LocalDate dateAfter = LocalDate.parse(dateAfterString);

		// calculating number of days in between
		long noOfDaysBetween = ChronoUnit.DAYS.between(dateBefore, dateAfter);

		return ResponseEntity.status(HttpStatus.OK).body(empids);
	}

}
