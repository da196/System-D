package com.Hrms.Payroll.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.Hrms.Payroll.Entity.HrmsPayrollContributionTypeVoluntary;
import com.Hrms.Payroll.Repository.HrmsPayrollContributionTypeVoluntaryRepository;
import com.Hrms.Payroll.Repository.HrmsPayrollContributionVoluntaryServiceProviderRepository;

@Service
public class HrmsPayrollContributionTypeVoluntaryServiceImpl implements HrmsPayrollContributionTypeVoluntaryService {

	@Autowired
	private HrmsPayrollContributionTypeVoluntaryRepository hrmsPayrollContributionTypeVoluntaryRepository;

	@Autowired
	private HrmsPayrollContributionVoluntaryServiceProviderRepository hrmsPayrollContributionVoluntaryServiceProviderRepository;

	@Override
	public ResponseEntity<HrmsPayrollContributionTypeVoluntary> addPayrollContributionTypeVoluntary(
			HrmsPayrollContributionTypeVoluntary hrmsPayrollContributionTypeVoluntary) {
		if (hrmsPayrollContributionTypeVoluntaryRepository
				.existsByNameAndActive(hrmsPayrollContributionTypeVoluntary.getName(), 1)) {
			return ResponseEntity.status(HttpStatus.ALREADY_REPORTED).body(hrmsPayrollContributionTypeVoluntary);
		} else {
			hrmsPayrollContributionTypeVoluntary.setActive(1);
			hrmsPayrollContributionTypeVoluntary.setApproved(0);
			hrmsPayrollContributionTypeVoluntary.setUnique_id(UUID.randomUUID());

			return ResponseEntity.status(HttpStatus.OK).body(
					hrmsPayrollContributionTypeVoluntaryRepository.saveAndFlush(hrmsPayrollContributionTypeVoluntary));

		}
	}

	@Override
	public ResponseEntity<HrmsPayrollContributionTypeVoluntary> getPayrollContributionTypeVoluntaryById(int id) {
		if (hrmsPayrollContributionTypeVoluntaryRepository.existsByIdAndActive(id, 1)) {
			return ResponseEntity.status(HttpStatus.OK)
					.body(hrmsPayrollContributionTypeVoluntaryRepository.findByIdAndActive(id, 1));
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
		}
	}

	@Override
	public ResponseEntity<HrmsPayrollContributionTypeVoluntary> updatePayrollContributionTypeVoluntary(
			HrmsPayrollContributionTypeVoluntary hrmsPayrollContributionTypeVoluntary, int id) {
		if (hrmsPayrollContributionTypeVoluntaryRepository.existsByIdAndActive(id, 1)) {
			hrmsPayrollContributionTypeVoluntary.setActive(1);
			hrmsPayrollContributionTypeVoluntary.setApproved(0);
			hrmsPayrollContributionTypeVoluntary.setDate_updated(LocalDateTime.now());
			if (hrmsPayrollContributionTypeVoluntaryRepository.findById(id).get().getDate_created() != null) {
				hrmsPayrollContributionTypeVoluntary.setDate_created(
						hrmsPayrollContributionTypeVoluntaryRepository.findById(id).get().getDate_created());
			}

			if (hrmsPayrollContributionTypeVoluntaryRepository.findById(id).get().getUnique_id() != null) {
				hrmsPayrollContributionTypeVoluntary
						.setUnique_id(hrmsPayrollContributionTypeVoluntaryRepository.findById(id).get().getUnique_id());
			}

			return ResponseEntity.status(HttpStatus.OK).body(
					hrmsPayrollContributionTypeVoluntaryRepository.saveAndFlush(hrmsPayrollContributionTypeVoluntary));
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
		}
	}

	@Override
	public ResponseEntity<?> deletePayrollContributionTypeVoluntary(int id) {
		if (hrmsPayrollContributionTypeVoluntaryRepository.existsByIdAndActive(id, 1)) {
			HrmsPayrollContributionTypeVoluntary hrmsPayrollContributionTypeVoluntary = hrmsPayrollContributionTypeVoluntaryRepository
					.findByIdAndActive(id, 1);
			hrmsPayrollContributionTypeVoluntary.setActive(0);
			hrmsPayrollContributionTypeVoluntary.setDate_updated(LocalDateTime.now());

			return ResponseEntity.status(HttpStatus.OK).body(
					hrmsPayrollContributionTypeVoluntaryRepository.saveAndFlush(hrmsPayrollContributionTypeVoluntary));
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
		}
	}

	@Override
	public ResponseEntity<List<HrmsPayrollContributionTypeVoluntary>> getAllPayrollContributionTypeVoluntary() {
		return ResponseEntity.status(HttpStatus.OK)
				.body(hrmsPayrollContributionTypeVoluntaryRepository.findByActive(1));
	}

	@Override
	public ResponseEntity<List<HrmsPayrollContributionTypeVoluntary>> getContributionTypeVoluntaryByServiceProviderId(
			int serviceproviderid) {

		return ResponseEntity.status(HttpStatus.OK)
				.body(hrmsPayrollContributionTypeVoluntaryRepository.findByProvideridAndActive(serviceproviderid, 1));
	}
}
