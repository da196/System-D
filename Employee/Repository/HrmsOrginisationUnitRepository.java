package com.Hrms.Employee.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.Hrms.Employee.Entity.HrmsOrginisationUnit;

@Repository
public interface HrmsOrginisationUnitRepository extends JpaRepository<HrmsOrginisationUnit, Integer> {

	@Query("SELECT e FROM HrmsOrginisationUnit e WHERE e.unitTypeid IN (:department)")
	List<HrmsOrginisationUnit> findAllDepartment(@Param("department") List<Integer> department);

	@Query("SELECT e FROM HrmsOrginisationUnit e WHERE e.unitTypeid IN (:section)")
	List<HrmsOrginisationUnit> findAllSection(@Param("section") List<Integer> section);

	List<HrmsOrginisationUnit> findByparentId(int parentid);

	List<HrmsOrginisationUnit> findByUnitTypeidIn(Integer[] unittypeid);

	List<HrmsOrginisationUnit> findByActiveAndUnitTypeidIn(int i, Integer[] unittypeid);

	List<HrmsOrginisationUnit> findByparentIdAndActive(int id, int i);

	boolean existsByIdAndActive(int departmentId, int i);

	List<HrmsOrginisationUnit> findByActive(int i);

	// boolean existsByIdByIdIn(int[] units);

	boolean existsByIdIn(List<Integer> units);

	List<HrmsOrginisationUnit> findByUnitTypeidInAndActive(Integer[] unittypeid, int i);

	boolean existsByIdAndActiveAndUnitTypeid(int unitId, int i, int j);

	HrmsOrginisationUnit findByIdAndActive(int unitId, int i);

}
