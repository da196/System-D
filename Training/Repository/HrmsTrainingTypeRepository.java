package com.Hrms.Training.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.Hrms.Training.Entity.HrmsTrainingType;

@Repository
public interface HrmsTrainingTypeRepository extends JpaRepository<HrmsTrainingType, Integer> {

	List<HrmsTrainingType> findByActive(int i);

	boolean existsByIdAndActive(int trainingtypeid, int i);

	HrmsTrainingType findByIdAndActive(int trainingtypeid, int i);

	boolean existsByNameAndActive(String name, int i);

	boolean existsByCodeAndActive(int code, int i);

}
