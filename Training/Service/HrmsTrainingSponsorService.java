package com.Hrms.Training.Service;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.Hrms.Training.Entity.HrmsTrainingSponsor;

@Service
public interface HrmsTrainingSponsorService {

	public ResponseEntity<List<HrmsTrainingSponsor>> getAllTrainingSponsor();

	public ResponseEntity<HrmsTrainingSponsor> addTrainingSponsor(HrmsTrainingSponsor hrmsTrainingSponsor);

	public ResponseEntity<HrmsTrainingSponsor> getTrainingSponsorById(int id);

	public ResponseEntity<HrmsTrainingSponsor> updateTrainingSponsorr(HrmsTrainingSponsor hrmsTrainingSponsor, int id);

	public ResponseEntity<?> deleteTrainingSponsor(int id);

}
