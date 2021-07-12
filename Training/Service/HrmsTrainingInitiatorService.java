package com.Hrms.Training.Service;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.Hrms.Training.Entity.HrmsTrainingInitiator;

@Service
public interface HrmsTrainingInitiatorService {

	public ResponseEntity<HrmsTrainingInitiator> addTrainingInitiator(HrmsTrainingInitiator hrmsTrainingInitiator);

	public ResponseEntity<HrmsTrainingInitiator> getTrainingInitiatorById(int id);

	public ResponseEntity<HrmsTrainingInitiator> updateTrainingInitiator(HrmsTrainingInitiator hrmsTrainingInitiator,
			int id);

	public ResponseEntity<?> deleteTrainingInitiator(int id);

	public ResponseEntity<List<HrmsTrainingInitiator>> getAllTrainingInitiator();

}
