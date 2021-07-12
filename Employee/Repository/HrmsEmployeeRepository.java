package com.Hrms.Employee.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.Hrms.Employee.Entity.HrmsEmployee;

@Repository
public interface HrmsEmployeeRepository extends JpaRepository<HrmsEmployee, Integer> {

	boolean existsByCardNumber(String cardNumber);

	HrmsEmployee findByEmail(String username);

	boolean existsByEmail(String email);

	int countByUnitIdAndGenderid(int unitid, int i);

	@Query("select count(*) from HrmsEmployee em where em.unitId = ?1 and em.genderid=?2 and em.employmentstatusid in (?3)")
	int countByUnitIdAndGenderidAndempstatus(Integer id, Integer id2, Integer[] status);

	int countByUnitIdAndGenderidAndEmploymentstatusid(int id, int id2, int i);

	@Query("select count(*) from HrmsEmployee em where em.employmentstatusid in (?1)") // total employee
	int countByEmpstatus(Integer[] status);

	@Query("select count(*) from HrmsEmployee em where em.genderid=?2 and em.employmentstatusid in (?1)") // male
	int countByEmpstatusAndGender(Integer[] status, int i);

	boolean existsByUnitId(int i);

	List<HrmsEmployee> findByEmploymentstatusidIn(Integer[] status);

	int countByGenderidAndEmploymentstatusidIn(int i, Integer[] status);

	HrmsEmployee findFirstByDesignationId(int id);

	boolean existsByDesignationId(int id);

	HrmsEmployee findByDesignationIdAndEmploymentstatusid(int supervisordesignationid, int i);

	boolean existsByDesignationIdAndEmploymentstatusid(int supervisordesignationid, int i);

	boolean existsByIdAndActive(int employeeid, int i);

	boolean existsByDesignationIdAndIssupervisorAndEmploymentstatusid(int id, int i, int j);

	Integer countByEmploymentstatusidIn(Integer[] status);

	boolean existsByFileNumber(String fileNumber);

	int countByGenderidInAndEmploymentstatusidIn(Integer[] gender, Integer[] status);

	int countByDutystationidAndGenderidAndEmploymentstatusidIn(int id, int i, Integer[] status);

	int countByGenderidAndEmploymentstatusidInAndDesignationIdIn(int i, Integer[] status, List<Integer> directors);

	int countByUnitIdInAndEmploymentstatusidIn(List<Integer> subsections, Integer[] status);

	List<HrmsEmployee> findByEmploymentstatusidInAndUnitIdIn(Integer[] status, List<Integer> subsections);

	List<HrmsEmployee> findByUnitIdInAndEmploymentstatusidIn(List<Integer> directors, Integer[] status);

	List<HrmsEmployee> findByActive(int i);

	int countByEmploymentstatusid(int i);

	List<HrmsEmployee> findByEmploymentstatusidInAndActive(Integer[] status, int i);

	boolean existsByDesignationIdAndIssupervisorAndEmploymentstatusidAndUnitId(int id, int i, int j, int departmentId);

	boolean existsByIdAndEmploymentstatusidInAndActive(int empId, Integer[] status, int i);

	HrmsEmployee findByIdAndEmploymentstatusidInAndActive(int empId, Integer[] status, int i);

	int countByDutystationidAndGenderidAndEmploymentstatusidInAndActive(int id, int i, Integer[] status, int j);

	int countByGenderidInAndEmploymentstatusidInAndActive(Integer[] gender, Integer[] status, int i);

	int countByGenderidAndEmploymentstatusidInAndActive(int i, Integer[] status, int j);

	int countByGenderidAndEmploymentstatusidInAndDesignationIdInAndActive(int i, Integer[] status,
			List<Integer> directors, int j);

	int countByEmploymentstatusidInAndActive(Integer[] status, int i);

	int countByUnitIdInAndEmploymentstatusidInAndActive(List<Integer> subsections, Integer[] status, int i);

	List<HrmsEmployee> findByEmploymentstatusidInAndUnitIdInAndActive(Integer[] status, List<Integer> subsections,
			int i);

	List<HrmsEmployee> findByUnitIdInAndEmploymentstatusidInAndActive(List<Integer> subsections, Integer[] status,
			int i);

	int countByEmploymentstatusidAndActive(int i, int j);

	int countByUnitIdAndGenderidAndEmploymentstatusidInAndActive(int id, int i, Integer[] status, int j);

	int countByEmploymentstatusidInAndGenderidAndActive(Integer[] status, int i, int j);

	int countByUnitIdAndGenderidAndEmploymentstatusidAndActive(int unitId, int i, int j, int k);

	int countByUnitIdAndGenderidAndEmploymentstatusidAndActive(int unitid, int i, Integer[] status, int k);

	HrmsEmployee findByIdAndActive(int empid, int i);

	HrmsEmployee findByIdAndEmploymentstatusidAndActive(int empid, int i, int j);

	List<HrmsEmployee> findByAndEmploymentstatusidAndActive(int i, int j);

	List<HrmsEmployee> findByEmploymentstatusidAndActive(int i, int j);

	List<HrmsEmployee> findBySupervisordesignationidAndEmploymentstatusid(int supervisordesignationid, int i);

	HrmsEmployee findFirstById(int employeeid);

	HrmsEmployee findFirstByDesignationIdAndEmploymentstatusid(int supervisordesignationid, int i);

	boolean existsByIdAndActiveOrderByIdDesc(int supervisorid, int i);

	List<HrmsEmployee> findByActiveAndEmploymentstatusid(int i, int j);

	List<HrmsEmployee> findByIsprobationAndActive(int i, int j);

	boolean existsByDesignationIdAndIssupervisorAndActive(int supervisordesignationid, int i, int j);

	HrmsEmployee findFirstByDesignationIdAndIssupervisorAndActive(int supervisordesignationid, int i, int j);

	boolean existsByIdAndIssupervisorAndActive(int supervisorid, int i, int j);

	HrmsEmployee findByIdAndIssupervisorAndActive(int supervisorid, int i, int j);

	HrmsEmployee findByIdAndActiveAndEmploymentstatusid(int employeeid, int i, int j);

	HrmsEmployee findByDesignationIdAndEmploymentstatusidAndActive(int headdesignation, int i, int j);

	boolean existsByIdAndActiveAndEmploymentstatusid(int employeeid, int i, int j);

	HrmsEmployee findFirstByDesignationIdAndEmploymentstatusidAndActive(int supervisordesignation, int i, int j);

	boolean existsByIdAndIssupervisorAndActiveAndUnitIdIn(int employeeid, int i, int j, List<Integer> units);

	HrmsEmployee findFirstByDesignationIdAndEmploymentstatusidAndActiveAndIssupervisor(int i, int j, int k, int l);

	HrmsEmployee findByIdAndIssupervisorAndActiveAndEmploymentstatusid(int supervisorid, int i, int j, int k);

	List<HrmsEmployee> findByActiveAndEmploymentstatusidIn(int i, List<Integer> status);

}
