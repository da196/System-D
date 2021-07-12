package com.Hrms.Training.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.Hrms.Training.Entity.HrmsTraining;

@Repository
public interface HrmsTrainingRepository extends JpaRepository<HrmsTraining, Integer> {

	boolean existsByDescriptionAndInstitutionAndFinancialyearidAndActive(String description, String institution,
			int financialyearid, int i);

	boolean existsByIdAndActive(int id, int i);

	HrmsTraining findByIdAndActive(int id, int i);

	List<HrmsTraining> findByFinancialyearidAndActive(int fyear, int i);

	List<HrmsTraining> findByFinancialyearidAndEmployeeidAndAndActive(int finyearid, int employeeid, int i);

	List<HrmsTraining> findByFinancialyearidAndTrainingcategoryidAndActive(int finyearid, int categoryid, int i);

	boolean existsByEmployeeidAndDescriptionAndInstitutionAndFinancialyearidAndActive(int employeeid,
			String description, String institution, int financialyearid, int i);

	List<HrmsTraining> findByFinancialyearidAndEmployeeidAndActive(int finyear, int employeeid, int i);

	List<HrmsTraining> findByApprovedAndActive(int i, int j);

	List<HrmsTraining> findByFinancialyearidAndEmployeeidAndActiveAndApproved(int finyear, int employeeid, int i,
			int j);

	List<HrmsTraining> findByEmployeeidAndActiveAndApproved(int employeeid, int i, int j);

	boolean existsByIdAndActiveAndApproved(int id, int i, int j);

}
