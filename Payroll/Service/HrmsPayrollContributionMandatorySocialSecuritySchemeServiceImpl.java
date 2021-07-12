package com.Hrms.Payroll.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.Hrms.Employee.Repository.HrmsCurrencyRepository;
import com.Hrms.Payroll.DTO.PayrollContributionMandatorySocialSecuritySchemeResponse;
import com.Hrms.Payroll.Entity.HrmsPayrollContributionMandatorySocialSecurityScheme;
import com.Hrms.Payroll.Repository.HrmsPayrollContributionMandatorySocialSecuritySchemeRepository;
import com.Hrms.Payroll.Repository.HrmsPayrollContributionTypeMandatoryRepository;
import com.Hrms.Payroll.Repository.HrmsSocialSecuritySchemeServiceProviderRepository;

@Service
public class HrmsPayrollContributionMandatorySocialSecuritySchemeServiceImpl
		implements HrmsPayrollContributionMandatorySocialSecuritySchemeService {

	@Autowired
	private HrmsPayrollContributionMandatorySocialSecuritySchemeRepository hrmsPayrollContributionMandatorySocialSecuritySchemeRepository;

	@Autowired
	private HrmsPayrollContributionTypeMandatoryRepository hrmsPayrollContributionTypeMandatoryRepository;

	@Autowired
	private HrmsCurrencyRepository hrmsCurrencyRepository;

	@Autowired
	private HrmsSocialSecuritySchemeServiceProviderRepository hrmsSocialSecuritySchemeServiceProviderRepository;

	@Override
	public ResponseEntity<HrmsPayrollContributionMandatorySocialSecurityScheme> addPayrollContributionMandatorySocialSecurityScheme(
			HrmsPayrollContributionMandatorySocialSecurityScheme hrmsPayrollContributionMandatorySocialSecurityScheme) {
		if (hrmsPayrollContributionMandatorySocialSecuritySchemeRepository.existsByContributiontypeidAndActive(
				hrmsPayrollContributionMandatorySocialSecurityScheme.getContributiontypeid(), 1)) {
			return ResponseEntity.status(HttpStatus.ALREADY_REPORTED)
					.body(hrmsPayrollContributionMandatorySocialSecurityScheme);
		} else {
			if (hrmsSocialSecuritySchemeServiceProviderRepository
					.existsByIdAndActive(hrmsPayrollContributionMandatorySocialSecurityScheme.getServiceproviderid(), 1)
					&& hrmsPayrollContributionTypeMandatoryRepository.existsByIdAndActive(
							hrmsPayrollContributionMandatorySocialSecurityScheme.getContributiontypeid(), 1)) {
				hrmsPayrollContributionMandatorySocialSecurityScheme.setActive(1);
				hrmsPayrollContributionMandatorySocialSecurityScheme.setApproved(0);
				hrmsPayrollContributionMandatorySocialSecurityScheme.setUnique_id(UUID.randomUUID());

				hrmsPayrollContributionMandatorySocialSecurityScheme
						.setRate(hrmsPayrollContributionMandatorySocialSecurityScheme.getRateemployee()
								+ hrmsPayrollContributionMandatorySocialSecurityScheme.getRateemployer());
				hrmsPayrollContributionMandatorySocialSecurityScheme
						.setAmount(hrmsPayrollContributionMandatorySocialSecurityScheme.getAmountemployee()
								+ hrmsPayrollContributionMandatorySocialSecurityScheme.getAmountemployer());

				return ResponseEntity.status(HttpStatus.OK)
						.body(hrmsPayrollContributionMandatorySocialSecuritySchemeRepository
								.saveAndFlush(hrmsPayrollContributionMandatorySocialSecurityScheme));
			} else {
				return ResponseEntity.status(HttpStatus.PRECONDITION_FAILED)
						.body(hrmsPayrollContributionMandatorySocialSecurityScheme);
			}

		}
	}

	@Override
	public ResponseEntity<PayrollContributionMandatorySocialSecuritySchemeResponse> getPayrollContributionMandatorySocialSecuritySchemeById(
			int id) {
		if (hrmsPayrollContributionMandatorySocialSecuritySchemeRepository.existsByIdAndActive(id, 1)) {

			HrmsPayrollContributionMandatorySocialSecurityScheme dbm = hrmsPayrollContributionMandatorySocialSecuritySchemeRepository
					.findByIdAndActive(id, 1);
			PayrollContributionMandatorySocialSecuritySchemeResponse payrollContributionMandatorySocialSecuritySchemeResponse = new PayrollContributionMandatorySocialSecuritySchemeResponse();

			payrollContributionMandatorySocialSecuritySchemeResponse.setActive(dbm.getActive());
			payrollContributionMandatorySocialSecuritySchemeResponse.setAmount(dbm.getAmount());
			payrollContributionMandatorySocialSecuritySchemeResponse.setAmountemployee(dbm.getAmountemployee());
			payrollContributionMandatorySocialSecuritySchemeResponse.setAmountemployer(dbm.getAmountemployer());
			payrollContributionMandatorySocialSecuritySchemeResponse.setApproved(dbm.getApproved());
			if (dbm.getContributiontypeid() != 0
					&& hrmsPayrollContributionTypeMandatoryRepository.existsById(dbm.getContributiontypeid())) {
				payrollContributionMandatorySocialSecuritySchemeResponse
						.setContributiontype(hrmsPayrollContributionTypeMandatoryRepository
								.findById(dbm.getContributiontypeid()).get().getName());
			}
			payrollContributionMandatorySocialSecuritySchemeResponse.setContributiontypeid(dbm.getContributiontypeid());
			if (dbm.getCurrencyid() != 0 && hrmsCurrencyRepository.existsById(dbm.getCurrencyid())) {
				payrollContributionMandatorySocialSecuritySchemeResponse
						.setCurrency(hrmsCurrencyRepository.findById(dbm.getCurrencyid()).get().getName());
			}

			payrollContributionMandatorySocialSecuritySchemeResponse.setCurrencyid(dbm.getCurrencyid());

			payrollContributionMandatorySocialSecuritySchemeResponse.setDescription(dbm.getDescription());

			payrollContributionMandatorySocialSecuritySchemeResponse.setId(dbm.getId());

			payrollContributionMandatorySocialSecuritySchemeResponse.setIsformularcomputed(dbm.getIsformularcomputed());

			payrollContributionMandatorySocialSecuritySchemeResponse.setRate(dbm.getRate());

			payrollContributionMandatorySocialSecuritySchemeResponse.setRateemployee(dbm.getRateemployee());

			payrollContributionMandatorySocialSecuritySchemeResponse.setRateemployer(dbm.getRateemployer());
			if (dbm.getServiceproviderid() != 0
					&& hrmsSocialSecuritySchemeServiceProviderRepository.existsById(dbm.getServiceproviderid())) {
				payrollContributionMandatorySocialSecuritySchemeResponse
						.setServiceprovider(hrmsSocialSecuritySchemeServiceProviderRepository
								.findById(dbm.getServiceproviderid()).get().getName());
			}

			payrollContributionMandatorySocialSecuritySchemeResponse.setServiceproviderid(dbm.getServiceproviderid());

			return ResponseEntity.status(HttpStatus.OK).body(payrollContributionMandatorySocialSecuritySchemeResponse);
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
		}
	}

	@Override
	public ResponseEntity<HrmsPayrollContributionMandatorySocialSecurityScheme> updatePayrollContributionMandatorySocialSecurityScheme(
			HrmsPayrollContributionMandatorySocialSecurityScheme hrmsPayrollContributionMandatorySocialSecurityScheme,
			int id) {
		if (hrmsPayrollContributionMandatorySocialSecuritySchemeRepository.existsByIdAndActive(id, 1)) {

			if (hrmsSocialSecuritySchemeServiceProviderRepository
					.existsByIdAndActive(hrmsPayrollContributionMandatorySocialSecurityScheme.getServiceproviderid(), 1)
					&& hrmsPayrollContributionTypeMandatoryRepository.existsByIdAndActive(
							hrmsPayrollContributionMandatorySocialSecurityScheme.getContributiontypeid(), 1)) {
				hrmsPayrollContributionMandatorySocialSecurityScheme.setActive(1);
				hrmsPayrollContributionMandatorySocialSecurityScheme.setApproved(0);
				hrmsPayrollContributionMandatorySocialSecurityScheme.setDate_updated(LocalDateTime.now());

				hrmsPayrollContributionMandatorySocialSecurityScheme
						.setRate(hrmsPayrollContributionMandatorySocialSecurityScheme.getRateemployee()
								+ hrmsPayrollContributionMandatorySocialSecurityScheme.getRateemployer());
				hrmsPayrollContributionMandatorySocialSecurityScheme
						.setAmount(hrmsPayrollContributionMandatorySocialSecurityScheme.getAmountemployee()
								+ hrmsPayrollContributionMandatorySocialSecurityScheme.getAmountemployer());

				UUID uuid = hrmsPayrollContributionMandatorySocialSecuritySchemeRepository.findById(id).get()
						.getUnique_id();
				if (uuid != null) {

					hrmsPayrollContributionMandatorySocialSecurityScheme.setUnique_id(uuid);
				}
				LocalDateTime createdd = hrmsPayrollContributionMandatorySocialSecuritySchemeRepository.findById(id)
						.get().getDate_created();
				if (createdd != null) {
					hrmsPayrollContributionMandatorySocialSecurityScheme.setDate_created(createdd);
				}
				return ResponseEntity.status(HttpStatus.OK)
						.body(hrmsPayrollContributionMandatorySocialSecuritySchemeRepository
								.saveAndFlush(hrmsPayrollContributionMandatorySocialSecurityScheme));
			} else {
				return ResponseEntity.status(HttpStatus.PRECONDITION_FAILED)
						.body(hrmsPayrollContributionMandatorySocialSecurityScheme);
			}
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
		}
	}

	@Override
	public ResponseEntity<?> deletePayrollContributionMandatorySocialSecurityScheme(int id) {
		if (hrmsPayrollContributionMandatorySocialSecuritySchemeRepository.existsByIdAndActive(id, 1)) {
			HrmsPayrollContributionMandatorySocialSecurityScheme hrmsPayrollContributionMandatorySocialSecurityScheme = hrmsPayrollContributionMandatorySocialSecuritySchemeRepository
					.findByIdAndActive(id, 1);
			hrmsPayrollContributionMandatorySocialSecurityScheme.setActive(0);
			hrmsPayrollContributionMandatorySocialSecurityScheme.setDate_updated(LocalDateTime.now());

			return ResponseEntity.status(HttpStatus.OK)
					.body(hrmsPayrollContributionMandatorySocialSecuritySchemeRepository
							.saveAndFlush(hrmsPayrollContributionMandatorySocialSecurityScheme));
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
		}
	}

	@Override
	public ResponseEntity<List<PayrollContributionMandatorySocialSecuritySchemeResponse>> getAllPayrollContributionMandatorySocialSecurityScheme() {

		List<PayrollContributionMandatorySocialSecuritySchemeResponse> payrollContributionMandatorySocialSecuritySchemeResponselist = new ArrayList<>();

		List<HrmsPayrollContributionMandatorySocialSecurityScheme> dbms = hrmsPayrollContributionMandatorySocialSecuritySchemeRepository
				.findByActiveOrderByIdDesc(1);
		dbms.forEach(dbm -> {
			PayrollContributionMandatorySocialSecuritySchemeResponse payrollContributionMandatorySocialSecuritySchemeResponse = new PayrollContributionMandatorySocialSecuritySchemeResponse();

			payrollContributionMandatorySocialSecuritySchemeResponse.setActive(dbm.getActive());
			payrollContributionMandatorySocialSecuritySchemeResponse.setAmount(dbm.getAmount());
			payrollContributionMandatorySocialSecuritySchemeResponse.setAmountemployee(dbm.getAmountemployee());
			payrollContributionMandatorySocialSecuritySchemeResponse.setAmountemployer(dbm.getAmountemployer());
			payrollContributionMandatorySocialSecuritySchemeResponse.setApproved(dbm.getApproved());
			if (dbm.getContributiontypeid() != 0
					&& hrmsPayrollContributionTypeMandatoryRepository.existsById(dbm.getContributiontypeid())) {
				payrollContributionMandatorySocialSecuritySchemeResponse
						.setContributiontype(hrmsPayrollContributionTypeMandatoryRepository
								.findById(dbm.getContributiontypeid()).get().getName());
			}
			payrollContributionMandatorySocialSecuritySchemeResponse.setContributiontypeid(dbm.getContributiontypeid());
			if (dbm.getCurrencyid() != 0 && hrmsCurrencyRepository.existsById(dbm.getCurrencyid())) {
				payrollContributionMandatorySocialSecuritySchemeResponse
						.setCurrency(hrmsCurrencyRepository.findById(dbm.getCurrencyid()).get().getName());
			}

			payrollContributionMandatorySocialSecuritySchemeResponse.setCurrencyid(dbm.getCurrencyid());

			payrollContributionMandatorySocialSecuritySchemeResponse.setDescription(dbm.getDescription());

			payrollContributionMandatorySocialSecuritySchemeResponse.setId(dbm.getId());

			payrollContributionMandatorySocialSecuritySchemeResponse.setIsformularcomputed(dbm.getIsformularcomputed());

			payrollContributionMandatorySocialSecuritySchemeResponse.setRate(dbm.getRate());

			payrollContributionMandatorySocialSecuritySchemeResponse.setRateemployee(dbm.getRateemployee());

			payrollContributionMandatorySocialSecuritySchemeResponse.setRateemployer(dbm.getRateemployer());
			if (dbm.getServiceproviderid() != 0
					&& hrmsSocialSecuritySchemeServiceProviderRepository.existsById(dbm.getServiceproviderid())) {
				payrollContributionMandatorySocialSecuritySchemeResponse
						.setServiceprovider(hrmsSocialSecuritySchemeServiceProviderRepository
								.findById(dbm.getServiceproviderid()).get().getName());
			}

			payrollContributionMandatorySocialSecuritySchemeResponse.setServiceproviderid(dbm.getServiceproviderid());

			payrollContributionMandatorySocialSecuritySchemeResponselist
					.add(payrollContributionMandatorySocialSecuritySchemeResponse);

		});
		return ResponseEntity.status(HttpStatus.OK).body(payrollContributionMandatorySocialSecuritySchemeResponselist);
	}

}
