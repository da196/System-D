package com.Hrms.Employee.Repository;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.Hrms.Employee.Entity.HrmsUser;
import com.Hrms.Employee.Entity.HrmsUserRoles;

@Repository
public interface HrmsUserRolesRepository extends JpaRepository<HrmsUserRoles, Integer> {

	Set<HrmsUserRoles> findByUserId(int userid);

	Set<HrmsUserRoles> findByUserIdAndActive(int id, int i);

	boolean existsByRoleId(int roleId);

	boolean existsByUserId(int userId);

	boolean existsByUserIdAndRoleId(int userId, int roleId);

	boolean existsByIdAndActive(int id, int i);

	HrmsUserRoles findByIdAndActive(int id, int i);

	List<HrmsUserRoles> findByActive(int i);

	List<HrmsUserRoles> findByUserId(Optional<HrmsUser> findByEmail);

	boolean existsByUserIdAndActive(int userid, int i);

	boolean existsByUserIdAndRoleIdIn(int userId, List<Integer> roles);

	boolean existsByUserIdAndRoleIdInAndActive(int userId, List<Integer> roles, int i);

}
