package com.Hrms.Payroll.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.Hrms.Payroll.Entity.HrmsSocialSecuritySchemeServiceProvider;
import com.Hrms.Payroll.Repository.HrmsSocialSecuritySchemeServiceProviderRepository;

@Service
public class HrmsSocialSecuritySchemeServiceProviderServiceImpl
		implements HrmsSocialSecuritySchemeServiceProviderService {
	@Autowired
	private HrmsSocialSecuritySchemeServiceProviderRepository hrmsSocialSecuritySchemeServiceProviderRepository;

	@Override
	public ResponseEntity<HrmsSocialSecuritySchemeServiceProvider> addSocialSecuritySchemeServiceProvider(
			HrmsSocialSecuritySchemeServiceProvider hrmsSocialSecuritySchemeServiceProvider) {
		if (hrmsSocialSecuritySchemeServiceProviderRepository
				.existsByNameAndActive(hrmsSocialSecuritySchemeServiceProvider.getName(), 1)) {
			return ResponseEntity.status(HttpStatus.ALREADY_REPORTED).body(hrmsSocialSecuritySchemeServiceProvider);
		} else {
			hrmsSocialSecuritySchemeServiceProvider.setActive(1);
			hrmsSocialSecuritySchemeServiceProvider.setApproved(0);
			hrmsSocialSecuritySchemeServiceProvider.setUnique_id(UUID.randomUUID());

			return ResponseEntity.status(HttpStatus.OK).body(hrmsSocialSecuritySchemeServiceProviderRepository
					.saveAndFlush(hrmsSocialSecuritySchemeServiceProvider));

		}
	}

	@Override
	public ResponseEntity<HrmsSocialSecuritySchemeServiceProvider> getSocialSecuritySchemeServiceProviderById(int id) {
		if (hrmsSocialSecuritySchemeServiceProviderRepository.existsByIdAndActive(id, 1)) {
			return ResponseEntity.status(HttpStatus.OK)
					.body(hrmsSocialSecuritySchemeServiceProviderRepository.findByIdAndActive(id, 1));
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
		}
	}

	@Override
	public ResponseEntity<HrmsSocialSecuritySchemeServiceProvider> updateSocialSecuritySchemeServiceProvider(
			HrmsSocialSecuritySchemeServiceProvider hrmsSocialSecuritySchemeServiceProvider, int id) {
		if (hrmsSocialSecuritySchemeServiceProviderRepository.existsByIdAndActive(id, 1)) {
			hrmsSocialSecuritySchemeServiceProvider.setActive(1);
			hrmsSocialSecuritySchemeServiceProvider.setApproved(0);
			hrmsSocialSecuritySchemeServiceProvider.setDate_updated(LocalDateTime.now());
			if (hrmsSocialSecuritySchemeServiceProviderRepository.findById(id).get().getDate_created() != null) {
				hrmsSocialSecuritySchemeServiceProvider.setDate_created(
						hrmsSocialSecuritySchemeServiceProviderRepository.findById(id).get().getDate_created());
			}

			if (hrmsSocialSecuritySchemeServiceProviderRepository.findById(id).get().getUnique_id() != null) {
				hrmsSocialSecuritySchemeServiceProvider.setUnique_id(
						hrmsSocialSecuritySchemeServiceProviderRepository.findById(id).get().getUnique_id());
			}

			return ResponseEntity.status(HttpStatus.OK).body(hrmsSocialSecuritySchemeServiceProviderRepository
					.saveAndFlush(hrmsSocialSecuritySchemeServiceProvider));
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
		}
	}

	@Override
	public ResponseEntity<?> deleteSocialSecuritySchemeServiceProvider(int id) {
		if (hrmsSocialSecuritySchemeServiceProviderRepository.existsByIdAndActive(id, 1)) {
			HrmsSocialSecuritySchemeServiceProvider hrmsSocialSecuritySchemeServiceProvider = hrmsSocialSecuritySchemeServiceProviderRepository
					.findByIdAndActive(id, 1);
			hrmsSocialSecuritySchemeServiceProvider.setActive(0);
			hrmsSocialSecuritySchemeServiceProvider.setDate_updated(LocalDateTime.now());

			return ResponseEntity.status(HttpStatus.OK).body(hrmsSocialSecuritySchemeServiceProviderRepository
					.saveAndFlush(hrmsSocialSecuritySchemeServiceProvider));
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
		}
	}

	@Override
	public ResponseEntity<List<HrmsSocialSecuritySchemeServiceProvider>> getAllSocialSecuritySchemeServiceProvider() {
		return ResponseEntity.status(HttpStatus.OK)
				.body(hrmsSocialSecuritySchemeServiceProviderRepository.findByActive(1));
	}
}
