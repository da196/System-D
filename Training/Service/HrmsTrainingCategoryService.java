package com.Hrms.Training.Service;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.Hrms.Training.Entity.HrmsTrainingCategory;

@Service
public interface HrmsTrainingCategoryService {

	public ResponseEntity<HrmsTrainingCategory> addTrainingCategory(HrmsTrainingCategory hrmsTrainingCategory);

	public ResponseEntity<HrmsTrainingCategory> getTrainingCategoryById(int id);

	public ResponseEntity<HrmsTrainingCategory> updateTrainingCategory(HrmsTrainingCategory hrmsTrainingCategory,
			int id);

	public ResponseEntity<?> deleteTrainingCategory(int id);

	public ResponseEntity<List<HrmsTrainingCategory>> getAllTrainingCategory();

}
