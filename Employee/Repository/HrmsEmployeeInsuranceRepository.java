package com.Hrms.Employee.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.Hrms.Employee.Entity.HrmsEmployeeInsurance;

@Repository
public interface HrmsEmployeeInsuranceRepository extends JpaRepository<HrmsEmployeeInsurance, Integer> {

	boolean existsByInsurancenumber(String insurancenumber);

	boolean existsByIdAndActive(int id, int i);

	boolean existsByEmployeeidAndActive(int empId, int i);

	List<HrmsEmployeeInsurance> findByEmployeeidAndActive(int empId, int i);

	List<HrmsEmployeeInsurance> findByActive(int i);

	boolean existsByInsurancenumberAndActive(String insurancenumber, int i);

	boolean existsByEmployeeidAndInsurancetypeidAndInsuranceprovideridAndActive(int employeeid, int insurancetypeid,
			int insuranceproviderid, int i);

	List<HrmsEmployeeInsurance> findByActiveOrderByIdDesc(int i);

	List<HrmsEmployeeInsurance> findByEmployeeidAndActiveOrderByIdDesc(int empId, int i);

}
