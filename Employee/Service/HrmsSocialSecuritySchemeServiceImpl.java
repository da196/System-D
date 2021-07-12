package com.Hrms.Employee.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.Hrms.Employee.Entity.HrmsSocialSecurityScheme;
import com.Hrms.Employee.Repository.HrmsSocialSecuritySchemeRepository;

@Service
public class HrmsSocialSecuritySchemeServiceImpl implements HrmsSocialSecuritySchemeService {

	@Autowired
	private HrmsSocialSecuritySchemeRepository hrmsSocialSecuritySchemeRepository;

	@Override
	public ResponseEntity<HrmsSocialSecurityScheme> save(HrmsSocialSecurityScheme hrmsSocialSecurityScheme) {

		UUID uuid = UUID.randomUUID();
		hrmsSocialSecurityScheme.setUnique_id(uuid);
		hrmsSocialSecurityScheme.setActive(1);
		hrmsSocialSecurityScheme.setApproved(0);

		if (hrmsSocialSecuritySchemeRepository.existsByName(hrmsSocialSecurityScheme.getName())) {
			return ResponseEntity.status(HttpStatus.ALREADY_REPORTED).body(null);

		} else {

			return ResponseEntity.status(HttpStatus.OK)
					.body(hrmsSocialSecuritySchemeRepository.save(hrmsSocialSecurityScheme));
		}
	}

	@Override
	public ResponseEntity<Optional<HrmsSocialSecurityScheme>> viewHrmsSocialSecurityScheme(int id) {

		if (hrmsSocialSecuritySchemeRepository.existsById(id)) {

			return ResponseEntity.status(HttpStatus.OK).body(hrmsSocialSecuritySchemeRepository.findById(id));
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
		}
	}

	@Override
	public ResponseEntity<HrmsSocialSecurityScheme> update(HrmsSocialSecurityScheme hrmsSocialSecurityScheme, int id) {

		LocalDateTime LocalTime = LocalDateTime.now();
		hrmsSocialSecurityScheme.setDate_updated(LocalTime);
		hrmsSocialSecurityScheme.setActive(1);
		hrmsSocialSecurityScheme.setApproved(0);
		if (hrmsSocialSecuritySchemeRepository.existsById(id)) {
			return ResponseEntity.status(HttpStatus.OK)
					.body(hrmsSocialSecuritySchemeRepository.save(hrmsSocialSecurityScheme));
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(hrmsSocialSecurityScheme);
		}
	}

	@Override
	public ResponseEntity<?> deleteHrmsSocialSecurityScheme(int id) {

		if (hrmsSocialSecuritySchemeRepository.existsById(id)) {
			hrmsSocialSecuritySchemeRepository.deleteById(id);
			return ResponseEntity.status(HttpStatus.OK).body(null);
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
		}
	}

	@Override
	public ResponseEntity<List<HrmsSocialSecurityScheme>> listHrmsSocialSecurityScheme() {

		return ResponseEntity.status(HttpStatus.OK).body(hrmsSocialSecuritySchemeRepository.findAll());
	}

}
