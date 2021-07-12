package com.Hrms.Perfomance.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.Hrms.Perfomance.Entity.HrmsPerformanceFinancialYear;
import com.Hrms.Perfomance.Repository.HrmsPerformanceFinancialYearRepository;

@Service
public class HrmsPerformanceFinancialYearServiceImpl implements HrmsPerformanceFinancialYearService {

	@Autowired
	private HrmsPerformanceFinancialYearRepository hrmsPerformanceFinancialYearRepository;

	@Override
	public ResponseEntity<HrmsPerformanceFinancialYear> addPerformanceFinancialYear(
			HrmsPerformanceFinancialYear hrmsPerformanceFinancialYear) {

		if (hrmsPerformanceFinancialYearRepository.existsByYearstartingAndYearendingAndActive(
				hrmsPerformanceFinancialYear.getYearstarting(), hrmsPerformanceFinancialYear.getYearending(), 1)) {
			return ResponseEntity.status(HttpStatus.ALREADY_REPORTED).body(hrmsPerformanceFinancialYear);
		} else {
			hrmsPerformanceFinancialYear.setActive(1);
			hrmsPerformanceFinancialYear.setApproved(0);
			hrmsPerformanceFinancialYear.setUnique_id(UUID.randomUUID());

			if (hrmsPerformanceFinancialYear.getYearstarting() != 0
					&& hrmsPerformanceFinancialYear.getYearending() != 0) {

				int yearduration = (hrmsPerformanceFinancialYear.getYearending()
						- hrmsPerformanceFinancialYear.getYearstarting()) + 1;
				hrmsPerformanceFinancialYear.setDuration(yearduration);

				String description = hrmsPerformanceFinancialYear.getYearstarting() + "/"
						+ hrmsPerformanceFinancialYear.getYearending();

				hrmsPerformanceFinancialYear.setDescription(description);
			}

			return ResponseEntity.status(HttpStatus.OK)
					.body(hrmsPerformanceFinancialYearRepository.saveAndFlush(hrmsPerformanceFinancialYear));

		}
	}

	@Override
	public ResponseEntity<HrmsPerformanceFinancialYear> getPerformanceFinancialYearById(int id) {
		if (hrmsPerformanceFinancialYearRepository.existsByIdAndActive(id, 1)) {
			return ResponseEntity.status(HttpStatus.OK)
					.body(hrmsPerformanceFinancialYearRepository.findByIdAndActive(id, 1));
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
		}
	}

	@Override
	public ResponseEntity<HrmsPerformanceFinancialYear> updatePerformanceFinancialYear(
			HrmsPerformanceFinancialYear hrmsPerformanceFinancialYear, int id) {
		if (hrmsPerformanceFinancialYearRepository.existsByIdAndActive(id, 1)) {
			hrmsPerformanceFinancialYear.setActive(1);
			hrmsPerformanceFinancialYear.setApproved(0);
			hrmsPerformanceFinancialYear.setDate_updated(LocalDateTime.now());

			if (hrmsPerformanceFinancialYear.getYearstarting() != 0
					&& hrmsPerformanceFinancialYear.getYearending() != 0) {

				int yearduration = (hrmsPerformanceFinancialYear.getYearending()
						- hrmsPerformanceFinancialYear.getYearstarting()) + 1;
				hrmsPerformanceFinancialYear.setDuration(yearduration);
			}
			if (hrmsPerformanceFinancialYearRepository.findById(id).get().getDate_created() != null) {
				hrmsPerformanceFinancialYear
						.setDate_created(hrmsPerformanceFinancialYearRepository.findById(id).get().getDate_created());
			}

			if (hrmsPerformanceFinancialYearRepository.findById(id).get().getUnique_id() != null) {
				hrmsPerformanceFinancialYear
						.setUnique_id(hrmsPerformanceFinancialYearRepository.findById(id).get().getUnique_id());
			}

			return ResponseEntity.status(HttpStatus.OK)
					.body(hrmsPerformanceFinancialYearRepository.saveAndFlush(hrmsPerformanceFinancialYear));
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
		}
	}

	@Override
	public ResponseEntity<?> deletePerformanceFinancialYear(int id) {
		if (hrmsPerformanceFinancialYearRepository.existsByIdAndActive(id, 1)) {
			HrmsPerformanceFinancialYear hrmsPerformanceFinancialYear = hrmsPerformanceFinancialYearRepository
					.findByIdAndActive(id, 1);
			hrmsPerformanceFinancialYear.setActive(0);
			hrmsPerformanceFinancialYear.setDate_updated(LocalDateTime.now());

			return ResponseEntity.status(HttpStatus.OK)
					.body(hrmsPerformanceFinancialYearRepository.saveAndFlush(hrmsPerformanceFinancialYear));
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
		}
	}

	@Override
	public ResponseEntity<List<HrmsPerformanceFinancialYear>> getAllPerformanceFinancialYear() {
		return ResponseEntity.status(HttpStatus.OK).body(hrmsPerformanceFinancialYearRepository.findByActive(1));

	}

}
