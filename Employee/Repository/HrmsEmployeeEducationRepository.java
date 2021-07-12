package com.Hrms.Employee.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.Hrms.Employee.Entity.HrmsEmployeeEducation;

@Repository
public interface HrmsEmployeeEducationRepository extends JpaRepository<HrmsEmployeeEducation, Integer> {

	boolean existsByEmployeeid(int id);

	List<HrmsEmployeeEducation> findByEmployeeid(int id);

	boolean existsByEmployeeidAndLevelidAndCourseidAndCountryidAndInstitutionidAndDatestartAndDatend(int employeeid,
			int levelid, int courseid, int countryid, int institutionid, String datestart, String datend);

	List<HrmsEmployeeEducation> findByEmployeeidAndActive(int id, int i);

	HrmsEmployeeEducation findByIdAndActive(int id, int i);

	boolean existsByIdAndActive(int id, int i);

	List<HrmsEmployeeEducation> findByActiveOrderByEmployeeid(int i);

	List<HrmsEmployeeEducation> findByActiveAndApproved(int i, int j);

	boolean existsByIdAndApproved(int id, int i);

	List<HrmsEmployeeEducation> findByActiveAndApprovedOrderByEmployeeid(int i, int j);

	boolean existsByEmployeeidAndActive(int id, int i);

}
