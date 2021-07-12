package com.Hrms.Employee.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.Hrms.Employee.Entity.HrmsEmployeeEmploymentHistory;

@Repository
public interface HrmsEmployeeEmploymentHistoryRepository extends JpaRepository<HrmsEmployeeEmploymentHistory, Integer> {

	boolean existsByEmployeeid(int empId);

	List<HrmsEmployeeEmploymentHistory> findByEmployeeid(int empId);

	List<HrmsEmployeeEmploymentHistory> findByEmployeeidAndActive(int empId, int i);

	boolean existsByIdAndActive(int id, int i);

	HrmsEmployeeEmploymentHistory findByIdAndActive(int id, int i);

	boolean existsByEmployeeidAndActive(int empId, int i);

	boolean existsByEmployeeidAndCountryidAndActiveAndEmployerAndDatendAndDatestartAndPositionAndDuties(int employeeid,
			int countryid, int i, String employer, String datend, String datestart, String position, String duties);

	List<HrmsEmployeeEmploymentHistory> findByActiveOrderByEmployeeid(int i);

	List<HrmsEmployeeEmploymentHistory> findByActiveAndApprovedOrderByEmployeeid(int i, int j);

	List<HrmsEmployeeEmploymentHistory> findByEmployeeidAndActiveOrderByDatestartDesc(int empId, int i);

}
