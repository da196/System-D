package com.Hrms.Employee.Service;

import java.util.List;
import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.Hrms.Employee.DTO.EducationinstitutionResponse;
import com.Hrms.Employee.Entity.HrmsEducationinstitution;

@Service
public interface HrmsEducationinstitutionService {

	public ResponseEntity<HrmsEducationinstitution> save(HrmsEducationinstitution hrmsEducationinstitution);

	public ResponseEntity<Optional<HrmsEducationinstitution>> viewHrmsEducationinstitution(int id);

	public ResponseEntity<HrmsEducationinstitution> update(HrmsEducationinstitution hrmsEducationinstitution, int id);

	public ResponseEntity<?> deleteHrmsEducationinstitution(int id);

	public ResponseEntity<List<HrmsEducationinstitution>> listHrmsEducationinstitution();

	public ResponseEntity<List<EducationinstitutionResponse>> listHrmsEducationinstitutionDetailed();

	public ResponseEntity<EducationinstitutionResponse> viewHrmsEducationinstitutionDetailed(int id);

	public ResponseEntity<List<HrmsEducationinstitution>> listEducationinstitutionByCountryId(int countryId);
}
