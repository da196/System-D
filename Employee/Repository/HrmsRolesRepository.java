package com.Hrms.Employee.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.Hrms.Employee.Entity.HrmsRoles;

@Repository
public interface HrmsRolesRepository extends JpaRepository<HrmsRoles, Integer> {

	boolean existsByNameAndName(int code, String name);

	boolean existsByName(String name);

	boolean existsByIdAndActive(int roleid, int i);

	boolean existsByIdIn(List<Integer> roles);

}
