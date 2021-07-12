package com.Hrms.Employee.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.Hrms.Employee.Entity.HrmsEmployeeRelative;

@Repository
public interface HrmsEmployeeRelativeRepository extends JpaRepository<HrmsEmployeeRelative, Integer> {

	boolean existsByEmployeeidAndRelativecategoryidAndFirstname(int employeeid, int relativecategoryid,
			String firstname);

	boolean existsByEmployeeid(int id);

	List<HrmsEmployeeRelative> findByEmployeeid(int id);

	boolean existsByIdAndActive(int id, int i);

	List<HrmsEmployeeRelative> findByEmployeeidAndActive(int id, int i);

	List<HrmsEmployeeRelative> findByActiveOrderByEmployeeid(int i);

	List<HrmsEmployeeRelative> findByActiveAndApprovedOrderByEmployeeid(int i, int j);

	boolean existsByEmployeeidAndRelativecategoryidAndActive(int employeeid, int relativecategoryid, int i);

	boolean existsByEmployeeidAndRelativecategoryidAndFirstnameAndActive(int employeeid, int relativecategoryid,
			String firstname, int i);

	boolean existsByEmployeeidAndRelativecategoryidAndFirstnameAndMiddlenameAndLastnameAndActive(int employeeid,
			int relativecategoryid, String firstname, String middlename, String lastname, int i);

}
