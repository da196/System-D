package com.Hrms.Employee.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.Hrms.Employee.Entity.HrmsEmployeeCertification;

public interface HrmsEmployeeCertificationRepository extends JpaRepository<HrmsEmployeeCertification, Integer> {

	boolean existsByDatestartAndDatendAndEmployeeidAndActiveAndCertificationcategoryid(String datestart, String datend,
			int employeeid, int i, int certificationcategoryid);

	List<HrmsEmployeeCertification> findByEmployeeid(int empid);

	List<HrmsEmployeeCertification> findByEmployeeidAndActive(int empid, int i);

	List<HrmsEmployeeCertification> findByActive(int i);

	HrmsEmployeeCertification findByIdAndActive(int id, int i);

	boolean existsByIdAndActive(int id, int i);

	List<HrmsEmployeeCertification> findByActiveOrderByEmployeeid(int i);

	List<HrmsEmployeeCertification> findByActiveAndApprovedOrderByEmployeeid(int i, int j);

	boolean existsByEmployeeidAndActive(int id, int i);

}
