package com.Hrms.Employee.Repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.Hrms.Employee.Entity.HrmsUser;

@Repository
public interface HrmsUserRepository extends JpaRepository<HrmsUser, Integer> {
	Optional<HrmsUser> findByEmail(String username);

	boolean existsByEmail(String email);

	boolean existsByIdAndActive(int id, int i);

	List<HrmsUser> findByActive(int i);

	boolean existsByEmailAndLocked(String string, int i);

}
