package com.Hrms.Perfomance.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.Hrms.Perfomance.Entity.HrmsPerformanceEppa;

@Repository
public interface HrmsPerformanceEppaRepository extends JpaRepository<HrmsPerformanceEppa, Integer> {

	boolean existsByDescriptionAndEmployeeidAndFinancialyearidAndActive(String description, int employeeid,
			int financialyearid, int i);

	boolean existsByIdAndActive(int id, int i);

	HrmsPerformanceEppa findByIdAndActive(int id, int i);

	List<HrmsPerformanceEppa> findByActive(int i);

	// List<HrmsPerformanceEppa> findByOutputidAndActive(int outputid, int i);

	List<HrmsPerformanceEppa> findByFinancialyearidAndActive(int financialyearid, int i);

	List<HrmsPerformanceEppa> findByEmployeeidAndFinancialyearidAndActive(int employeeid, int financialyearid, int i);

	boolean existsByEmployeeidAndFinancialyearidAndActive(int employeeid, int financialyearid, int i);

	HrmsPerformanceEppa findByEmployeeidAndFinancialyearid(int employeeid, int financialyearid);

	List<HrmsPerformanceEppa> findByEmployeeidInAndFinancialyearidAndActive(List<Integer> ids, int financialyearid,
			int i);

	List<HrmsPerformanceEppa> findByEmployeeidAndFinancialyearidInAndActive(int employeeid, List<Integer> ids, int i);

	HrmsPerformanceEppa findByEmployeeidAndFinancialyearidAndApprovedInAndActive(int employeeid, int financialyearid,
			List<Integer> approveds, int i);

}
