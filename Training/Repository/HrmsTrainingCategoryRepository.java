package com.Hrms.Training.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.Hrms.Training.Entity.HrmsTrainingCategory;

@Repository
public interface HrmsTrainingCategoryRepository extends JpaRepository<HrmsTrainingCategory, Integer> {

	List<HrmsTrainingCategory> findByActive(int i);

	boolean existsByIdAndActive(int trainingcategoryid, int i);

	HrmsTrainingCategory findByIdAndActive(int id, int i);

	boolean existsByNameAndActive(String name, int i);

	boolean existsByCodeAndActive(int code, int i);

}
