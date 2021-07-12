package com.Hrms.Training.Service;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.Hrms.Training.Entity.HrmsTrainingPurpose;

@Service
public interface HrmsTrainingPurposeService {
	public ResponseEntity<List<HrmsTrainingPurpose>> getAllTrainingPurpose();

	public ResponseEntity<HrmsTrainingPurpose> addTrainingPurpose(HrmsTrainingPurpose hrmsTrainingPurpose);

	public ResponseEntity<HrmsTrainingPurpose> getTrainingPurposeById(int id);

	public ResponseEntity<HrmsTrainingPurpose> updateTrainingPurpose(HrmsTrainingPurpose hrmsTrainingPurpose, int id);

	public ResponseEntity<?> deleteTrainingPurpose(int id);

}
