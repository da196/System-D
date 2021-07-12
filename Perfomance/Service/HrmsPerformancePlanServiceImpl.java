package com.Hrms.Perfomance.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.Hrms.Perfomance.Entity.HrmsPerformancePlan;
import com.Hrms.Perfomance.Repository.HrmsPerformancePlanRepository;

@Service
public class HrmsPerformancePlanServiceImpl implements HrmsPerformancePlanService {

	@Autowired
	private HrmsPerformancePlanRepository hrmsPerformancePlanRepository;

	@Override
	public ResponseEntity<HrmsPerformancePlan> addPerformancePlan(HrmsPerformancePlan hrmsPerformancePlan) {

		if (hrmsPerformancePlanRepository.existsByNameAndActive(hrmsPerformancePlan.getName(), 1)) {
			return ResponseEntity.status(HttpStatus.ALREADY_REPORTED).body(hrmsPerformancePlan);
		} else {
			hrmsPerformancePlan.setActive(1);
			hrmsPerformancePlan.setApproved(0);
			hrmsPerformancePlan.setUnique_id(UUID.randomUUID());
			if (hrmsPerformancePlan.getYearstarting() != 0 && hrmsPerformancePlan.getYearending() != 0) {
				int startext = hrmsPerformancePlan.getYearstarting() + 1;

				String s = String.valueOf(startext);
				if (s.length() > 2) {
					s = s.substring(s.length() - 2);
				}

				int endext = hrmsPerformancePlan.getYearstarting() + 1;

				String e = String.valueOf(endext);

				if (e.length() > 2) {
					e = e.substring(e.length() - 2);
				}

				String financialyear = hrmsPerformancePlan.getYearstarting() + "/" + s + " -"
						+ hrmsPerformancePlan.getYearending() + "/" + e;

				hrmsPerformancePlan.setFinancialyear(financialyear);

				int yearduration = (hrmsPerformancePlan.getYearending() - hrmsPerformancePlan.getYearstarting()) + 1;
				hrmsPerformancePlan.setYearduration(yearduration);
			}

			return ResponseEntity.status(HttpStatus.OK)
					.body(hrmsPerformancePlanRepository.saveAndFlush(hrmsPerformancePlan));

		}
	}

	@Override
	public ResponseEntity<HrmsPerformancePlan> getPerformancePlanById(int id) {
		if (hrmsPerformancePlanRepository.existsByIdAndActive(id, 1)) {
			return ResponseEntity.status(HttpStatus.OK).body(hrmsPerformancePlanRepository.findByIdAndActive(id, 1));
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
		}
	}

	@Override
	public ResponseEntity<HrmsPerformancePlan> updatePerformancePlan(HrmsPerformancePlan hrmsPerformancePlan, int id) {

		if (hrmsPerformancePlanRepository.existsByIdAndActive(id, 1)) {
			hrmsPerformancePlan.setActive(1);
			hrmsPerformancePlan.setApproved(0);
			hrmsPerformancePlan.setDate_updated(LocalDateTime.now());

			if (hrmsPerformancePlan.getYearstarting() != 0 && hrmsPerformancePlan.getYearending() != 0) {
				int startext = hrmsPerformancePlan.getYearstarting() + 1;

				String s = String.valueOf(startext);
				if (s.length() > 2) {
					s = s.substring(s.length() - 2);
				}

				int endext = hrmsPerformancePlan.getYearstarting() + 1;

				String e = String.valueOf(endext);

				if (e.length() > 2) {
					e = e.substring(e.length() - 2);
				}

				String financialyear = hrmsPerformancePlan.getYearstarting() + "/" + s + " -"
						+ hrmsPerformancePlan.getYearending() + "/" + e;

				hrmsPerformancePlan.setFinancialyear(financialyear);

				int yearduration = (hrmsPerformancePlan.getYearending() - hrmsPerformancePlan.getYearstarting()) + 1;
				hrmsPerformancePlan.setYearduration(yearduration);
			}
			if (hrmsPerformancePlanRepository.findById(id).get().getDate_created() != null) {
				hrmsPerformancePlan.setDate_created(hrmsPerformancePlanRepository.findById(id).get().getDate_created());
			}

			if (hrmsPerformancePlanRepository.findById(id).get().getUnique_id() != null) {
				hrmsPerformancePlan.setUnique_id(hrmsPerformancePlanRepository.findById(id).get().getUnique_id());
			}

			return ResponseEntity.status(HttpStatus.OK)
					.body(hrmsPerformancePlanRepository.saveAndFlush(hrmsPerformancePlan));
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
		}
	}

	@Override
	public ResponseEntity<?> deletePerformancePlan(int id) {
		if (hrmsPerformancePlanRepository.existsByIdAndActive(id, 1)) {
			HrmsPerformancePlan hrmsPerformancePlan = hrmsPerformancePlanRepository.findByIdAndActive(id, 1);
			hrmsPerformancePlan.setActive(0);
			hrmsPerformancePlan.setDate_updated(LocalDateTime.now());

			return ResponseEntity.status(HttpStatus.OK)
					.body(hrmsPerformancePlanRepository.saveAndFlush(hrmsPerformancePlan));
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
		}
	}

	@Override
	public ResponseEntity<List<HrmsPerformancePlan>> getAllPerformancePlan() {
		return ResponseEntity.status(HttpStatus.OK).body(hrmsPerformancePlanRepository.findByActive(1));
	}

}
