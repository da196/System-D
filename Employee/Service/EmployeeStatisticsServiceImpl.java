package com.Hrms.Employee.Service;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.Hrms.Employee.DTO.EmployeeChildUnitCount;
import com.Hrms.Employee.DTO.EmployeeCount;
import com.Hrms.Employee.DTO.EmployeeCountAll;
import com.Hrms.Employee.DTO.EmployeeEducationlevelCount;
import com.Hrms.Employee.DTO.EmployeeGlobalCount;
import com.Hrms.Employee.DTO.EmployeeParentUnitCount;
import com.Hrms.Employee.DTO.EmployeeStatistics;
import com.Hrms.Employee.DTO.UnitEmployeeDetails;
import com.Hrms.Employee.Entity.HrmsEmployee;
import com.Hrms.Employee.Entity.HrmsEmployeeEducation;
import com.Hrms.Employee.Entity.HrmsOrginisationUnit;
import com.Hrms.Employee.Repository.HrmsEmployeeEducationRepository;
import com.Hrms.Employee.Repository.HrmsEmployeeRepository;
import com.Hrms.Employee.Repository.HrmsOrganisationUnitTypeRepository;
import com.Hrms.Employee.Repository.HrmsOrginisationUnitRepository;

@Service
public class EmployeeStatisticsServiceImpl implements EmployeeStatisticsService {
	@Autowired
	private HrmsEmployeeRepository hrmsEmployeeRepository;

	@Autowired
	private HrmsOrganisationUnitTypeRepository hrmsOrganisationUnitTypeRepository;

	@Autowired
	private HrmsOrginisationUnitRepository hrmsOrginisationUnitRepository;

	@Autowired
	private HrmsEmployeeEducationRepository hrmsEmployeeEducationRepository;

	@Override
	public ResponseEntity<EmployeeStatistics> findByempid(int empid) {
		if (hrmsEmployeeRepository.existsById(empid)) {
			EmployeeStatistics employeeStatistics = new EmployeeStatistics();

			int unitId = hrmsEmployeeRepository.findById(empid).get().getUnitId();

			HrmsOrginisationUnit hrmsOrginisationUnit = hrmsOrginisationUnitRepository.findById(unitId).get();

			int unittypeid = hrmsOrginisationUnit.getUnitTypeid();

			String unitname = hrmsOrganisationUnitTypeRepository.findById(unittypeid).get().getName().toLowerCase();

			List<EmployeeStatistics> employeeStatisticslist = new ArrayList<>();

			if (unitname.contains("section")) {

				int parentid = hrmsOrginisationUnit.getParentId();
				String depart = hrmsOrginisationUnitRepository.findById(hrmsOrginisationUnit.getId()).get().getName();

				// find all sub category of this parent unit
				List<HrmsOrginisationUnit> hrmsOrginisationUnitt = hrmsOrginisationUnitRepository
						.findByparentId(parentid);

				List<Integer> departdetails = departmentDetails(hrmsOrginisationUnit.getId());
				hrmsOrginisationUnitt.forEach(orgunit -> {

					employeeStatistics.setDepartmentfemalenumber(departdetails.get(0));
					employeeStatistics.setDepartmentmalenumber(departdetails.get(1));
					employeeStatistics.setDepartmenttotalnumber(departdetails.get(2));
					employeeStatistics.setDepartmentname(depart);

					employeeStatisticslist.add(employeeStatistics);
				});

			} else {
				if (unitname.contains("unit")) {

					HrmsOrginisationUnit hrmsOrginisationUnito = hrmsOrginisationUnitRepository.findById(unitId).get();

					String depart = hrmsOrginisationUnito.getName();

					// find all sub category of this parent unit
					List<HrmsOrginisationUnit> hrmsOrginisationUnitt = hrmsOrginisationUnitRepository
							.findByparentId(unitId);

					List<Integer> sectionDetail = sectionDetails(unitId);

					employeeStatistics.setDepartmentfemalenumber(sectionDetail.get(0));
					employeeStatistics.setDepartmentmalenumber(sectionDetail.get(1));
					employeeStatistics.setDepartmenttotalnumber(sectionDetail.get(2));
					employeeStatistics.setDepartmentname(depart);
					employeeStatistics.setEmpid(empid);

					return ResponseEntity.status(HttpStatus.OK).body(employeeStatistics);

				} else {
					if (unitname.contains("department")) {

						HrmsOrginisationUnit hrmsOrginisationUnito = hrmsOrginisationUnitRepository.findById(unitId)
								.get();

						String depart = hrmsOrginisationUnito.getName();

						// find all sub category of this parent unit
						List<HrmsOrginisationUnit> hrmsOrginisationUnitt = hrmsOrginisationUnitRepository
								.findByparentId(unitId);

						List<Integer> sectionDetail = sectionDetails(unitId);

						employeeStatistics.setDepartmentfemalenumber(sectionDetail.get(0));
						employeeStatistics.setDepartmentmalenumber(sectionDetail.get(1));
						employeeStatistics.setDepartmenttotalnumber(sectionDetail.get(2));
						employeeStatistics.setDepartmentname(depart);
						employeeStatistics.setEmpid(empid);

						return ResponseEntity.status(HttpStatus.OK).body(employeeStatistics);
					} else {
						if (unitname.contains("director")) {
							HrmsOrginisationUnit hrmsOrginisationUnito = hrmsOrginisationUnitRepository.findById(unitId)
									.get();

							String depart = hrmsOrginisationUnito.getName();

							// find all sub category of this parent unit
							List<HrmsOrginisationUnit> hrmsOrginisationUnitt = hrmsOrginisationUnitRepository
									.findByparentId(unitId);

							List<Integer> sectionDetail = sectionDetails(unitId);

							employeeStatistics.setDepartmentfemalenumber(sectionDetail.get(0));
							employeeStatistics.setDepartmentmalenumber(sectionDetail.get(1));
							employeeStatistics.setDepartmenttotalnumber(sectionDetail.get(2));
							employeeStatistics.setDepartmentname(depart);
							employeeStatistics.setEmpid(empid);

							return ResponseEntity.status(HttpStatus.OK).body(employeeStatistics);

						} else {
							if (unitname.contains("board")) {
								HrmsOrginisationUnit hrmsOrginisationUnito = hrmsOrginisationUnitRepository
										.findById(unitId).get();

								String depart = hrmsOrginisationUnito.getName();

								// find all sub category of this parent unit
								List<HrmsOrginisationUnit> hrmsOrginisationUnitt = hrmsOrginisationUnitRepository
										.findByparentId(unitId);

								List<Integer> sectionDetail = sectionDetails(unitId);

								employeeStatistics.setDepartmentfemalenumber(sectionDetail.get(0));
								employeeStatistics.setDepartmentmalenumber(sectionDetail.get(1));
								employeeStatistics.setDepartmenttotalnumber(sectionDetail.get(2));
								employeeStatistics.setDepartmentname(depart);
								employeeStatistics.setEmpid(empid);

								return ResponseEntity.status(HttpStatus.OK).body(employeeStatistics);

							} else {
								return ResponseEntity.status(HttpStatus.OK).body(null);
							}
						}
					}
				}
			}

			return ResponseEntity.status(HttpStatus.OK).body(employeeStatistics);
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
		}
	}

	public List<Integer> sectionDetails(int unitid) {
		int female = 0;
		int male = 0;
		int total = 0;
		List<Integer> sectiondata = new ArrayList<>();

		Integer[] status = { 2, 12, 11 };

		female = hrmsEmployeeRepository.countByUnitIdAndGenderidAndEmploymentstatusidInAndActive(unitid, 2, status, 1);
		male = hrmsEmployeeRepository.countByUnitIdAndGenderidAndEmploymentstatusidInAndActive(unitid, 1, status, 1);
		total = female + male;
		sectiondata.add(female);
		sectiondata.add(male);
		sectiondata.add(total);
		return sectiondata;
	}

	public List<Integer> departmentDetails(int unitId) {

		List<Integer> departdata = new ArrayList<>();
		// int sectionids =

		int female = hrmsEmployeeRepository.countByUnitIdAndGenderidAndEmploymentstatusidAndActive(unitId, 2, 2, 1);
		int male = hrmsEmployeeRepository.countByUnitIdAndGenderidAndEmploymentstatusidAndActive(unitId, 1, 2, 1);
		departdata.add(female);
		departdata.add(male);
		departdata.add(female + male);

		return departdata;
	}

	@Override
	public ResponseEntity<EmployeeCount> getEmployeeCount(int type) {
		if (type == 0 || type == 1 || type == 2) {
			DecimalFormat df = new DecimalFormat("#.##");
			df.setRoundingMode(RoundingMode.CEILING);
			EmployeeCount result = new EmployeeCount();
			Integer[] status = { 2, 12, 11 };

			int total = hrmsEmployeeRepository.countByEmploymentstatusidInAndActive(status, 1);
			int men = hrmsEmployeeRepository.countByEmploymentstatusidInAndGenderidAndActive(status, 1, 1);
			int female = hrmsEmployeeRepository.countByEmploymentstatusidInAndGenderidAndActive(status, 2, 1);

			switch (type) {
			case 0:

				result.setTotalnumber(total);
				result.setTotalpercentage(df.format(100.00) + "%");
				break;
			case 1:

				result.setTotalnumber(men);
				Double perc = (men * 100 / (double) total);

				result.setTotalpercentage(df.format(perc) + "%");
				break;
			case 2:
				result.setTotalnumber(female);
				Double perce = (female * 100 / (double) total);

				result.setTotalpercentage(df.format(perce) + "%");
				break;
			default:
				result.setTotalnumber(0);
				result.setTotalpercentage(df.format(0.00) + "%");
				break;
			}
			return ResponseEntity.status(HttpStatus.OK).body(result);

		} else {
			return ResponseEntity.status(HttpStatus.PRECONDITION_FAILED).body(null);
		}
	}

	@Override
	public ResponseEntity<List<UnitEmployeeDetails>> getUnitDetails() {
		List<UnitEmployeeDetails> unitdetails = new ArrayList<>();
		List<HrmsOrginisationUnit> hrmsOrginisationUnit = hrmsOrginisationUnitRepository.findByActive(1);
		DecimalFormat df = new DecimalFormat("#.##");
		df.setRoundingMode(RoundingMode.CEILING);
		hrmsOrginisationUnit.forEach(unit -> {
			if (hrmsEmployeeRepository.existsByUnitId(unit.getId())) {
				UnitEmployeeDetails unitEmployeeDetails = new UnitEmployeeDetails();
				Integer[] status = { 2, 12, 11 };
				int globaltotal = hrmsEmployeeRepository.countByEmpstatus(status);
				int female = hrmsEmployeeRepository
						.countByUnitIdAndGenderidAndEmploymentstatusidInAndActive(unit.getId(), 2, status, 1);
				int male = hrmsEmployeeRepository.countByUnitIdAndGenderidAndEmploymentstatusidInAndActive(unit.getId(),
						1, status, 1);
				int total = female + male;
				unitEmployeeDetails.setFemalenumber(female);
				unitEmployeeDetails.setMalenumber(male);
				unitEmployeeDetails.setTotalnumber(total);
				unitEmployeeDetails.setGlobalpercentage(df.format((total * 100 / (double) globaltotal)) + "%");
				unitEmployeeDetails.setUnitid(unit.getId());
				unitEmployeeDetails.setUnitname(unit.getName());
				unitEmployeeDetails.setUnitshortname(unit.getAbbreviation());
				unitdetails.add(unitEmployeeDetails);
			}
		});

		return ResponseEntity.status(HttpStatus.OK).body(unitdetails);
	}

	@Override
	public ResponseEntity<List<EmployeeEducationlevelCount>> getEducationlevelCount() {
		int phd = 0;
		int masters = 0;
		int bachelor = 0;
		int others = 0;

		DecimalFormat df = new DecimalFormat("#.##");
		df.setRoundingMode(RoundingMode.CEILING);

		List<EmployeeEducationlevelCount> empeducountlist = new ArrayList<>();

		Integer[] status = { 2, 12, 11, 3 };
		int totalemp = hrmsEmployeeRepository.countByEmploymentstatusidInAndActive(status, 1);
		List<Integer> levels = new ArrayList<>();
		List<HrmsEmployee> hrmsEmployees = hrmsEmployeeRepository.findByEmploymentstatusidInAndActive(status, 1);
		hrmsEmployees.forEach(empl -> {

			List<HrmsEmployeeEducation> hrmsEmployeeEducation = hrmsEmployeeEducationRepository
					.findByEmployeeidAndActive(empl.getId(), 1);

			if (hrmsEmployeeEducation != null && hrmsEmployeeEducation.size() >= 2) {
				HrmsEmployeeEducation empedu = Collections.min(hrmsEmployeeEducation,
						Comparator.comparing(ed -> ed.getLevelid()));

				levels.add(empedu.getLevelid());
			} else {
				if (hrmsEmployeeEducation.size() == 1) {
					levels.add(hrmsEmployeeEducation.get(0).getLevelid());
				}
			}

		});

		List<Integer> distinct = Arrays.asList(1, 2, 3);

		for (Integer s : distinct) {
			EmployeeEducationlevelCount employeeEducationlevelCount = new EmployeeEducationlevelCount();
			if (s == 1) {
				phd = Collections.frequency(levels, s);
				employeeEducationlevelCount.setEducationlevelid(s);
				employeeEducationlevelCount.setEducationlevename("Phd");
				employeeEducationlevelCount.setEdulevelempnumber(phd);
				employeeEducationlevelCount.setEducationlevelpercentage(df.format(phd * 100 / (double) totalemp) + "%");
				empeducountlist.add(employeeEducationlevelCount);
			}
			if (s == 2) {
				masters = Collections.frequency(levels, s);
				employeeEducationlevelCount.setEducationlevelid(s);
				employeeEducationlevelCount.setEducationlevename("Masters");
				employeeEducationlevelCount.setEdulevelempnumber(masters);
				employeeEducationlevelCount
						.setEducationlevelpercentage(df.format(masters * 100 / (double) totalemp) + "%");
				empeducountlist.add(employeeEducationlevelCount);
			}

			if (s == 3) {
				bachelor = Collections.frequency(levels, s);

				employeeEducationlevelCount.setEducationlevelid(s);
				employeeEducationlevelCount.setEducationlevename("Bachelor");
				employeeEducationlevelCount.setEdulevelempnumber(bachelor);
				employeeEducationlevelCount
						.setEducationlevelpercentage(df.format(bachelor * 100 / (double) totalemp) + "%");
				empeducountlist.add(employeeEducationlevelCount);
			} else {

			}

		}

		others = totalemp - (phd + masters + bachelor);

		EmployeeEducationlevelCount employeeEducationlevelCount = new EmployeeEducationlevelCount();
		employeeEducationlevelCount.setEducationlevelid(4);
		employeeEducationlevelCount.setEducationlevename("Others");
		employeeEducationlevelCount.setEdulevelempnumber(others);
		employeeEducationlevelCount.setEducationlevelpercentage(df.format(others * 100 / (double) totalemp) + "%");
		empeducountlist.add(employeeEducationlevelCount);

		return ResponseEntity.status(HttpStatus.OK).body(empeducountlist);
	}

	@Override
	public ResponseEntity<EmployeeCountAll> findAllCount() {
		Integer[] status = { 2, 12, 11 };
		int total = hrmsEmployeeRepository.countByEmploymentstatusidInAndActive(status, 1);
		int male = hrmsEmployeeRepository.countByGenderidAndEmploymentstatusidInAndActive(1, status, 1);
		EmployeeCountAll employeeCountAll = new EmployeeCountAll();
		employeeCountAll.setMale(male);
		employeeCountAll.setTotalnumber(total);
		employeeCountAll.setFemale(total - male);

		return ResponseEntity.status(HttpStatus.OK).body(employeeCountAll);
	}

	@Override
	public ResponseEntity<EmployeeGlobalCount> findAllGeneralCountPerUnit() {
		EmployeeGlobalCount employeeGlobalCount = new EmployeeGlobalCount();

		Integer[] status = { 2 };
		int totalemployee = hrmsEmployeeRepository.countByEmploymentstatusidInAndActive(status, 1);

		Integer[] unittypeid = { 10, 14, 33 };

		List<HrmsOrginisationUnit> dbms = hrmsOrginisationUnitRepository.findByUnitTypeidInAndActive(unittypeid, 1);

		List<EmployeeParentUnitCount> employeeParentUnitCountlist = new ArrayList<>();
		employeeGlobalCount.setTotalemployee(totalemployee);
		for (HrmsOrginisationUnit dbm : dbms) {

			EmployeeParentUnitCount employeeParentUnitCount = new EmployeeParentUnitCount();
			if (dbm.getUnitTypeid() != 0 && hrmsOrganisationUnitTypeRepository.existsById(dbm.getUnitTypeid())) {
				employeeParentUnitCount
						.setUnittype(hrmsOrganisationUnitTypeRepository.findById(dbm.getUnitTypeid()).get().getName());
			}
			employeeParentUnitCount.setParentunit(dbm.getName());
			employeeParentUnitCount.setParentunitid(dbm.getId());
			int totalparentunitnumber = getUnitNumbers(dbm.getId());

			employeeParentUnitCount.setTotalparentunitnumber(totalemployee);
			if (totalemployee != 0) {

				DecimalFormat df = new DecimalFormat("#.##");
				df.setRoundingMode(RoundingMode.CEILING);

				String parentunitpercentage = df.format(totalparentunitnumber * 100 / (double) totalemployee) + "%";

				employeeParentUnitCount.setParentunitpercentage(parentunitpercentage);
			}
			List<EmployeeChildUnitCount> employeeChildUnitCountlist = new ArrayList<>();

			List<HrmsOrginisationUnit> dbms1 = hrmsOrginisationUnitRepository.findByparentIdAndActive(dbm.getId(), 1);

			for (HrmsOrginisationUnit dbm1 : dbms1) {

				EmployeeChildUnitCount employeeChildUnitCount = new EmployeeChildUnitCount();

				employeeChildUnitCount.setChildunit(dbm1.getName());
				employeeChildUnitCount.setChildunitid(dbm1.getId());
				if (dbm1.getUnitTypeid() != 0 && hrmsOrganisationUnitTypeRepository.existsById(dbm1.getUnitTypeid())) {
					employeeChildUnitCount.setUnittype(
							hrmsOrganisationUnitTypeRepository.findById(dbm1.getUnitTypeid()).get().getName());
				}

				int totalchildnumber = getUnitNumbers(dbm.getId());

				employeeChildUnitCount.setTotalchildnumber(totalchildnumber);

				if (totalemployee != 0) {

					DecimalFormat df1 = new DecimalFormat("#.##");
					df1.setRoundingMode(RoundingMode.CEILING);

					String childunitpercentage = df1.format(totalparentunitnumber * 100 / (double) totalemployee) + "%";

					employeeChildUnitCount.setChildunitpercentage(childunitpercentage);
				}

				employeeChildUnitCountlist.add(employeeChildUnitCount);

			}

			employeeParentUnitCount.setEmployeeChildUnitCountlist(employeeChildUnitCountlist);

			employeeParentUnitCountlist.add(employeeParentUnitCount);

		}

		employeeGlobalCount.setEmployeeParentUnitCountlist(employeeParentUnitCountlist);

		return ResponseEntity.status(HttpStatus.OK).body(employeeGlobalCount);
	}

	public Integer getUnitNumbers(int unitid) {
		List<Integer> unitlist = new ArrayList<>();
		Integer[] status = { 2 };
		unitlist.add(unitid);

		List<HrmsOrginisationUnit> dbms = hrmsOrginisationUnitRepository.findByparentId(unitid);

		for (HrmsOrginisationUnit dbm : dbms) {

			unitlist.add(dbm.getId());

		}

		int totalcount = hrmsEmployeeRepository.countByUnitIdInAndEmploymentstatusidInAndActive(unitlist, status, 1);

		return totalcount;

	}

}
