package com.Hrms.Training.Service;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.Hrms.Training.Entity.HrmsTrainingType;

@Service
public interface HrmsTrainingTypeService {

	public ResponseEntity<List<HrmsTrainingType>> getAllTrainingType();

	public ResponseEntity<HrmsTrainingType> addTrainingType(HrmsTrainingType hrmsTrainingType);

	public ResponseEntity<HrmsTrainingType> getTrainingTypeById(int id);

	public ResponseEntity<HrmsTrainingType> updateTrainingType(HrmsTrainingType hrmsTrainingType, int id);

	public ResponseEntity<?> deleteTrainingType(int id);

}
