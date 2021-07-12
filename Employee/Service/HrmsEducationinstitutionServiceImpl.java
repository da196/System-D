package com.Hrms.Employee.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.Hrms.Employee.DTO.EducationinstitutionResponse;
import com.Hrms.Employee.Entity.HrmsEducationinstitution;
import com.Hrms.Employee.Repository.HrmsEducationInstCategoryRepository;
import com.Hrms.Employee.Repository.HrmsEducationInstitutionTypeRepository;
import com.Hrms.Employee.Repository.HrmsEducationinstitutionRepository;
import com.Hrms.Employee.Repository.HrmsLocationCityRepository;
import com.Hrms.Employee.Repository.HrmsLocationCountryRepository;

@Service
public class HrmsEducationinstitutionServiceImpl implements HrmsEducationinstitutionService {
	@Autowired
	private HrmsEducationinstitutionRepository hrmsEducationinstitutionRepository;

	@Autowired
	private HrmsLocationCityRepository hrmsLocationCityRepository;

	@Autowired
	private HrmsLocationCountryRepository hrmsLocationCountryRepository;

	@Autowired
	private HrmsEducationInstitutionTypeRepository hrmsEducationInstitutionTypeRepository;

	@Autowired
	private HrmsEducationInstCategoryRepository hrmsEducationInstCategoryRepository;

	@Override
	public ResponseEntity<HrmsEducationinstitution> save(HrmsEducationinstitution hrmsEducationinstitution) {
		if (hrmsLocationCountryRepository.existsById(hrmsEducationinstitution.getCountryId())
				&& hrmsEducationInstitutionTypeRepository.existsById(hrmsEducationinstitution.getTypeid())
				&& hrmsEducationInstCategoryRepository.existsById(hrmsEducationinstitution.getCategoryId())) {
			UUID uuid = UUID.randomUUID();
			hrmsEducationinstitution.setUnique_id(uuid);
			hrmsEducationinstitution.setActive(1);
			hrmsEducationinstitution.setApproved(0);

			if (hrmsEducationinstitutionRepository.existsByNameAndActive(hrmsEducationinstitution.getName(), 1)) {
				return ResponseEntity.status(HttpStatus.ALREADY_REPORTED).body(null);

			} else {

				return ResponseEntity.status(HttpStatus.OK)
						.body(hrmsEducationinstitutionRepository.save(hrmsEducationinstitution));
			}
		} else {
			return ResponseEntity.status(HttpStatus.FAILED_DEPENDENCY).body(hrmsEducationinstitution);
		}
	}

	@Override
	public ResponseEntity<Optional<HrmsEducationinstitution>> viewHrmsEducationinstitution(int id) {

		if (hrmsEducationinstitutionRepository.existsByIdAndActive(id, 1)) {

			return ResponseEntity.status(HttpStatus.OK).body(hrmsEducationinstitutionRepository.findById(id));
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
		}
	}

	@Override
	public ResponseEntity<HrmsEducationinstitution> update(HrmsEducationinstitution hrmsEducationinstitution, int id) {

		LocalDateTime LocalTime = LocalDateTime.now();
		hrmsEducationinstitution.setDate_updated(LocalTime);
		hrmsEducationinstitution.setApproved(0);

		if (hrmsEducationinstitutionRepository.existsByIdAndActive(id, 1)) {
			return ResponseEntity.status(HttpStatus.OK)
					.body(hrmsEducationinstitutionRepository.save(hrmsEducationinstitution));
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(hrmsEducationinstitution);
		}
	}

	@Override
	public ResponseEntity<?> deleteHrmsEducationinstitution(int id) {

		if (hrmsEducationinstitutionRepository.existsByIdAndActive(id, 1)) {
			HrmsEducationinstitution hrmsEducationinstitution = hrmsEducationinstitutionRepository.findById(id).get();
			hrmsEducationinstitution.setActive(0);
			hrmsEducationinstitution.setDate_updated(LocalDateTime.now());
			return ResponseEntity.status(HttpStatus.OK)
					.body(hrmsEducationinstitutionRepository.save(hrmsEducationinstitution));
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
		}
	}

	@Override
	public ResponseEntity<List<HrmsEducationinstitution>> listHrmsEducationinstitution() {

		// return
		// ResponseEntity.status(HttpStatus.OK).body(hrmsEducationinstitutionRepository.findAll());
		return ResponseEntity.status(HttpStatus.OK)
				.body(hrmsEducationinstitutionRepository.findByActiveOrderByNameAsc(1));
	}

	@Override
	public ResponseEntity<List<EducationinstitutionResponse>> listHrmsEducationinstitutionDetailed() {

		List<EducationinstitutionResponse> eduinstitutelist = new ArrayList<>();

		hrmsEducationinstitutionRepository.findByActiveOrderByNameAsc(1).forEach(institutes -> {
			EducationinstitutionResponse educationinstitutionResponse = new EducationinstitutionResponse();
			educationinstitutionResponse.setAbbreviation(institutes.getAbbreviation());
			educationinstitutionResponse.setActive(institutes.getActive());
			educationinstitutionResponse.setApproved(institutes.getApproved());
			educationinstitutionResponse.setCategoryId(institutes.getCategoryId());
			educationinstitutionResponse.setCategory(
					hrmsEducationInstCategoryRepository.findById(institutes.getCategoryId()).get().getName());
			educationinstitutionResponse.setCityId(institutes.getCityId());

			if (hrmsLocationCityRepository.existsById(institutes.getCityId())) {
				educationinstitutionResponse
						.setCity(hrmsLocationCityRepository.findById(institutes.getCityId()).get().getName());
			}
			educationinstitutionResponse.setCode(institutes.getCode());
			educationinstitutionResponse.setCountryId(institutes.getCountryId());

			if (hrmsLocationCountryRepository.existsById(institutes.getCountryId())) {
				educationinstitutionResponse
						.setCountry(hrmsLocationCountryRepository.findById(institutes.getCountryId()).get().getName());
			}
			educationinstitutionResponse.setDescription(institutes.getDescription());
			educationinstitutionResponse.setId(institutes.getId());
			educationinstitutionResponse.setName(institutes.getName());

			educationinstitutionResponse.setTypeid(institutes.getTypeid());
			if (hrmsEducationInstitutionTypeRepository.existsById(institutes.getTypeid())) {
				educationinstitutionResponse.setType(
						hrmsEducationInstitutionTypeRepository.findById(institutes.getTypeid()).get().getName());
			}
			eduinstitutelist.add(educationinstitutionResponse);

		});

		return ResponseEntity.status(HttpStatus.OK).body(eduinstitutelist);
	}

	@Override
	public ResponseEntity<EducationinstitutionResponse> viewHrmsEducationinstitutionDetailed(int id) {
		if (hrmsEducationinstitutionRepository.existsByIdAndActive(id, 1)) {

			HrmsEducationinstitution hrmsEducationinstitution = hrmsEducationinstitutionRepository.findByIdAndActive(id,
					1);

			EducationinstitutionResponse educationinstitutionResponse = new EducationinstitutionResponse();

			educationinstitutionResponse.setAbbreviation(hrmsEducationinstitution.getAbbreviation());
			educationinstitutionResponse.setActive(hrmsEducationinstitution.getActive());
			educationinstitutionResponse.setApproved(hrmsEducationinstitution.getApproved());
			educationinstitutionResponse.setCategoryId(hrmsEducationinstitution.getCategoryId());
			educationinstitutionResponse.setCategory(hrmsEducationInstCategoryRepository
					.findById(hrmsEducationinstitution.getCategoryId()).get().getName());
			educationinstitutionResponse.setCityId(hrmsEducationinstitution.getCityId());

			if (hrmsLocationCityRepository.existsById(hrmsEducationinstitution.getCityId())) {
				educationinstitutionResponse.setCity(
						hrmsLocationCityRepository.findById(hrmsEducationinstitution.getCityId()).get().getName());
			}
			educationinstitutionResponse.setCode(hrmsEducationinstitution.getCode());
			educationinstitutionResponse.setCountryId(hrmsEducationinstitution.getCountryId());

			if (hrmsLocationCountryRepository.existsById(hrmsEducationinstitution.getCountryId())) {
				educationinstitutionResponse.setCountry(hrmsLocationCountryRepository
						.findById(hrmsEducationinstitution.getCountryId()).get().getName());
			}
			educationinstitutionResponse.setDescription(hrmsEducationinstitution.getDescription());
			educationinstitutionResponse.setId(hrmsEducationinstitution.getId());
			educationinstitutionResponse.setName(hrmsEducationinstitution.getName());

			educationinstitutionResponse.setTypeid(hrmsEducationinstitution.getTypeid());
			if (hrmsEducationInstitutionTypeRepository.existsById(hrmsEducationinstitution.getTypeid())) {
				educationinstitutionResponse.setType(hrmsEducationInstitutionTypeRepository
						.findById(hrmsEducationinstitution.getTypeid()).get().getName());

			}

			return ResponseEntity.status(HttpStatus.OK).body(educationinstitutionResponse);

		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
		}
	}

	@Override
	public ResponseEntity<List<HrmsEducationinstitution>> listEducationinstitutionByCountryId(int countryId) {
		if (hrmsLocationCountryRepository.existsById(countryId)) {
			return ResponseEntity.status(HttpStatus.OK)
					.body(hrmsEducationinstitutionRepository.findByCountryIdAndActiveOrderByNameAsc(countryId, 1));

		} else {

			return ResponseEntity.status(HttpStatus.PRECONDITION_FAILED).body(null);
		}
	}
}
