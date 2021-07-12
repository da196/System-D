package com.Hrms.Employee.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.Hrms.Employee.Entity.HrmsNationality;

@Repository
public interface HrmsNationalityRepository extends JpaRepository<HrmsNationality, Integer> {

	List<HrmsNationality> findAllByOrderByIdAsc();

	List<HrmsNationality> findByActiveOrderByIdAsc(int i);

}
