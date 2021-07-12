package com.Hrms.Employee.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.Hrms.Employee.Entity.HrmsUserLogins;

@Repository
public interface HrmsUserLoginsRepository extends JpaRepository<HrmsUserLogins, Integer> {

	boolean existsByuserId(int id);

	Optional<HrmsUserLogins> findByuserIdAndActive(int userid, int i);

	boolean existsByuserIdAndApproved(int userid, int i);

	boolean existsByuserIdAndActive(int userid, int i);

	List<HrmsUserLogins> findByuserId(int id);

	int countByUserIdAndDatcreatedAfterAndApproved(int userId, LocalDateTime thresholdDate, int approved);

}
