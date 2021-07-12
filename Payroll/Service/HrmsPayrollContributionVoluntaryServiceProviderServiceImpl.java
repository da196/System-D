package com.Hrms.Payroll.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.Hrms.Payroll.Entity.HrmsPayrollContributionVoluntaryServiceProvider;
import com.Hrms.Payroll.Repository.HrmsPayrollContributionVoluntaryServiceProviderRepository;

@Service
public class HrmsPayrollContributionVoluntaryServiceProviderServiceImpl
		implements HrmsPayrollContributionVoluntaryServiceProviderService {

	@Autowired
	private HrmsPayrollContributionVoluntaryServiceProviderRepository hrmsPayrollContributionVoluntaryServiceProviderRepository;

	@Override
	public ResponseEntity<HrmsPayrollContributionVoluntaryServiceProvider> addContributionVoluntaryServiceProvider(
			HrmsPayrollContributionVoluntaryServiceProvider hrmsPayrollContributionVoluntaryServiceProvider) {
		if (hrmsPayrollContributionVoluntaryServiceProviderRepository
				.existsByNameAndActive(hrmsPayrollContributionVoluntaryServiceProvider.getName(), 1)) {
			return ResponseEntity.status(HttpStatus.ALREADY_REPORTED)
					.body(hrmsPayrollContributionVoluntaryServiceProvider);
		} else {
			hrmsPayrollContributionVoluntaryServiceProvider.setActive(1);
			hrmsPayrollContributionVoluntaryServiceProvider.setApproved(0);
			hrmsPayrollContributionVoluntaryServiceProvider.setUnique_id(UUID.randomUUID());

			return ResponseEntity.status(HttpStatus.OK).body(hrmsPayrollContributionVoluntaryServiceProviderRepository
					.saveAndFlush(hrmsPayrollContributionVoluntaryServiceProvider));

		}
	}

	@Override
	public ResponseEntity<HrmsPayrollContributionVoluntaryServiceProvider> getContributionVoluntaryServiceProviderById(
			int id) {
		if (hrmsPayrollContributionVoluntaryServiceProviderRepository.existsByIdAndActive(id, 1)) {
			return ResponseEntity.status(HttpStatus.OK)
					.body(hrmsPayrollContributionVoluntaryServiceProviderRepository.findByIdAndActive(id, 1));
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
		}
	}

	@Override
	public ResponseEntity<HrmsPayrollContributionVoluntaryServiceProvider> updateContributionVoluntaryServiceProvider(
			HrmsPayrollContributionVoluntaryServiceProvider hrmsPayrollContributionVoluntaryServiceProvider, int id) {
		if (hrmsPayrollContributionVoluntaryServiceProviderRepository.existsByIdAndActive(id, 1)) {
			hrmsPayrollContributionVoluntaryServiceProvider.setActive(1);
			hrmsPayrollContributionVoluntaryServiceProvider.setApproved(0);
			hrmsPayrollContributionVoluntaryServiceProvider.setDate_updated(LocalDateTime.now());
			if (hrmsPayrollContributionVoluntaryServiceProviderRepository.findById(id).get()
					.getDate_created() != null) {
				hrmsPayrollContributionVoluntaryServiceProvider.setDate_created(
						hrmsPayrollContributionVoluntaryServiceProviderRepository.findById(id).get().getDate_created());
			}

			if (hrmsPayrollContributionVoluntaryServiceProviderRepository.findById(id).get().getUnique_id() != null) {
				hrmsPayrollContributionVoluntaryServiceProvider.setUnique_id(
						hrmsPayrollContributionVoluntaryServiceProviderRepository.findById(id).get().getUnique_id());
			}

			return ResponseEntity.status(HttpStatus.OK).body(hrmsPayrollContributionVoluntaryServiceProviderRepository
					.saveAndFlush(hrmsPayrollContributionVoluntaryServiceProvider));
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
		}
	}

	@Override
	public ResponseEntity<?> deleteContributionVoluntaryServiceProvider(int id) {
		if (hrmsPayrollContributionVoluntaryServiceProviderRepository.existsByIdAndActive(id, 1)) {
			HrmsPayrollContributionVoluntaryServiceProvider hrmsPayrollContributionVoluntaryServiceProvider = hrmsPayrollContributionVoluntaryServiceProviderRepository
					.findByIdAndActive(id, 1);
			hrmsPayrollContributionVoluntaryServiceProvider.setActive(0);
			hrmsPayrollContributionVoluntaryServiceProvider.setDate_updated(LocalDateTime.now());

			return ResponseEntity.status(HttpStatus.OK).body(hrmsPayrollContributionVoluntaryServiceProviderRepository
					.saveAndFlush(hrmsPayrollContributionVoluntaryServiceProvider));
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
		}
	}

	@Override
	public ResponseEntity<List<HrmsPayrollContributionVoluntaryServiceProvider>> getAllContributionVoluntaryServiceProvider() {
		return ResponseEntity.status(HttpStatus.OK)
				.body(hrmsPayrollContributionVoluntaryServiceProviderRepository.findByActive(1));
	}

}
