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

import com.Hrms.Employee.DTO.HrmsAllowanceResponse;
import com.Hrms.Employee.DTO.SalaryStructureedms;
import com.Hrms.Employee.Entity.HrmsAllowance;
import com.Hrms.Employee.Entity.HrmsSalarystructure;
import com.Hrms.Employee.Repository.HrmsAllowanceRepository;
import com.Hrms.Employee.Repository.HrmsAllowancetypeRepository;
import com.Hrms.Employee.Repository.HrmsCurrencyRepository;
import com.Hrms.Employee.Repository.HrmsDesignationRepository;
import com.Hrms.Employee.Repository.HrmsEmploymentCategoryRepository;
import com.Hrms.Employee.Repository.HrmsSalaryScaleRepository;
import com.Hrms.Employee.Repository.HrmsSalaryscalenotchRepository;
import com.Hrms.Employee.Repository.HrmsSalarystructureRepository;

@Service
public class HrmsAllowanceServiceImpl implements HrmsAllowanceService {

	@Autowired
	private HrmsAllowanceRepository hrmsAllowanceRepository;
	@Autowired
	private HrmsAllowancetypeRepository hrmsAllowancetypeRepository;

	@Autowired
	private HrmsDesignationRepository hrmsDesignationRepository;

	@Autowired
	private HrmsEmploymentCategoryRepository hrmsEmploymentCategoryRepository;

	@Autowired
	private HrmsSalarystructureRepository hrmsSalarystructureRepository;

	@Autowired
	private HrmsSalaryScaleRepository hrmsSalaryScaleRepository;

	@Autowired
	private HrmsSalaryscalenotchRepository hrmsSalaryscalenotchRepository;

	@Autowired
	private HrmsCurrencyRepository hrmsCurrencyRepository;

	@Override
	public ResponseEntity<HrmsAllowance> save(HrmsAllowance hrmsAllowance) {
		if (hrmsEmploymentCategoryRepository.existsById(hrmsAllowance.getEmploymentcategoryid())
				&& hrmsSalarystructureRepository.existsById(hrmsAllowance.getSalarystructureid())) {
			UUID uuid = UUID.randomUUID();
			hrmsAllowance.setUnique_id(uuid);
			hrmsAllowance.setActive(1);
			hrmsAllowance.setApproved(0);

			return ResponseEntity.status(HttpStatus.OK).body(hrmsAllowanceRepository.save(hrmsAllowance));
		} else {
			return ResponseEntity.status(HttpStatus.FAILED_DEPENDENCY).body(hrmsAllowance);
		}
	}

	@Override
	public ResponseEntity<Optional<HrmsAllowance>> viewHrmsHrmsAllowance(int id) {
		if (hrmsAllowanceRepository.existsById(id)) {

			return ResponseEntity.status(HttpStatus.OK).body(hrmsAllowanceRepository.findById(id));
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
		}
	}

	@Override
	public ResponseEntity<HrmsAllowance> updateHrmsAllowance(HrmsAllowance hrmsAllowance, int id) {
		LocalDateTime LocalTime = LocalDateTime.now();
		hrmsAllowance.setDate_updated(LocalTime);
		if (hrmsEmploymentCategoryRepository.existsById(hrmsAllowance.getEmploymentcategoryid())
				&& hrmsSalarystructureRepository.existsById(hrmsAllowance.getSalarystructureid())) {
			if (hrmsAllowanceRepository.existsById(id)) {
				return ResponseEntity.status(HttpStatus.OK).body(hrmsAllowanceRepository.save(hrmsAllowance));
			} else {
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body(hrmsAllowance);
			}
		} else {
			return ResponseEntity.status(HttpStatus.FAILED_DEPENDENCY).body(hrmsAllowance);
		}
	}

	@Override
	public ResponseEntity<?> deleteHrmsAllowance(int id) {

		if (hrmsAllowanceRepository.existsById(id)) {
			HrmsAllowance hrmsAllowance = hrmsAllowanceRepository.findById(id).get();
			hrmsAllowance.setActive(0);
			hrmsAllowance.setDate_updated(LocalDateTime.now());

			return ResponseEntity.status(HttpStatus.OK).body(hrmsAllowanceRepository.save(hrmsAllowance));
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
		}
	}

	@Override
	public ResponseEntity<List<HrmsAllowance>> listHrmsAllowance() {

		return ResponseEntity.status(HttpStatus.OK).body(hrmsAllowanceRepository.findAll());
	}

	@Override
	public ResponseEntity<List<HrmsAllowance>> FindByDesignationId(int id) {
		if (hrmsAllowanceRepository.existsByDesignationid(id)) {

			return ResponseEntity.status(HttpStatus.OK).body(hrmsAllowanceRepository.findByDesignationid(id));
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
		}

	}

	@Override
	public ResponseEntity<List<HrmsAllowanceResponse>> listHrmsAllowanceDetailed() {
		List<HrmsAllowanceResponse> alowancelist = new ArrayList<>();
		hrmsAllowanceRepository.findByActive(1).forEach(allowance -> {

			HrmsAllowanceResponse allowancerespo = new HrmsAllowanceResponse();
			allowancerespo.setActive(allowance.getActive());
			allowancerespo.setCurrencyid(allowance.getCurrencyid());
			if (hrmsCurrencyRepository.existsById(allowance.getCurrencyid())) {

				allowancerespo.setCurrency(hrmsCurrencyRepository.findById(allowance.getCurrencyid()).get().getName());
			}

			allowancerespo.setAllowancetypeid(allowance.getAllowancetypeid());
			if (allowance.getAllowancetypeid() != 0) {
				allowancerespo.setAllowancetypename(
						hrmsAllowancetypeRepository.findById(allowance.getAllowancetypeid()).get().getName());
			}
			allowancerespo.setAmount(allowance.getAmount());
			allowancerespo.setApproved(allowance.getApproved());
			allowancerespo.setDescription(allowance.getDescription());
			allowancerespo.setDesignationid(allowance.getDesignationid());
			if (allowance.getDesignationid() != 0) {
				allowancerespo.setDesignationname(
						hrmsDesignationRepository.findById(allowance.getDesignationid()).get().getName());
			}
			allowancerespo.setEmploymentcategoryid(allowance.getEmploymentcategoryid());
			if (allowance.getEmploymentcategoryid() != 0) {
				allowancerespo.setEmploymentcategoryname(
						hrmsEmploymentCategoryRepository.findById(allowance.getEmploymentcategoryid()).get().getName());
			}
			allowancerespo.setId(allowance.getId());
			allowancerespo.setSalarystructureid(allowance.getSalarystructureid());
			if (allowance.getSalarystructureid() != 0) {
				// below is the notch get query
				// hrmsSalaryscalenotchRepository.findById(hrmsSalarystructureRepository.findById(allowance.getSalarystructureid()).get().getNotchId()).get().getName();
				allowancerespo
						.setSalarystructurename(hrmsSalaryScaleRepository
								.findById(hrmsSalarystructureRepository.findById(allowance.getSalarystructureid()).get()
										.getScaleId())
								.get().getName()
								+ "-"
								+ hrmsSalaryscalenotchRepository
										.findById(hrmsSalarystructureRepository
												.findById(allowance.getSalarystructureid()).get().getNotchId())
										.get().getName());
			}
			alowancelist.add(allowancerespo);

		});
		return ResponseEntity.status(HttpStatus.OK).body(alowancelist);
	}

	@Override
	public ResponseEntity<List<HrmsAllowanceResponse>> getAllowancesTravel() {
		List<HrmsAllowanceResponse> alowancelist = new ArrayList<>();
		// 5 = allowance type for travell
		hrmsAllowanceRepository.findByAllowancetypeidAndActive(5, 1).forEach(allowance -> {

			HrmsAllowanceResponse allowancerespo = new HrmsAllowanceResponse();
			allowancerespo.setActive(allowance.getActive());
			allowancerespo.setCurrencyid(allowance.getCurrencyid());
			if (hrmsCurrencyRepository.existsById(allowance.getCurrencyid())) {

				allowancerespo.setCurrency(hrmsCurrencyRepository.findById(allowance.getCurrencyid()).get().getName());
			}

			allowancerespo.setAllowancetypeid(allowance.getAllowancetypeid());
			if (allowance.getAllowancetypeid() != 0) {
				allowancerespo.setAllowancetypename(
						hrmsAllowancetypeRepository.findById(allowance.getAllowancetypeid()).get().getName());
			}
			allowancerespo.setAmount(allowance.getAmount());
			allowancerespo.setApproved(allowance.getApproved());
			allowancerespo.setDescription(allowance.getDescription());
			allowancerespo.setDesignationid(allowance.getDesignationid());
			if (allowance.getDesignationid() != 0) {
				allowancerespo.setDesignationname(
						hrmsDesignationRepository.findById(allowance.getDesignationid()).get().getName());
			}
			allowancerespo.setEmploymentcategoryid(allowance.getEmploymentcategoryid());
			if (allowance.getEmploymentcategoryid() != 0) {
				allowancerespo.setEmploymentcategoryname(
						hrmsEmploymentCategoryRepository.findById(allowance.getEmploymentcategoryid()).get().getName());
			}
			allowancerespo.setId(allowance.getId());
			allowancerespo.setSalarystructureid(allowance.getSalarystructureid());
			if (allowance.getSalarystructureid() != 0) {
				// below is the notch get query
				// hrmsSalaryscalenotchRepository.findById(hrmsSalarystructureRepository.findById(allowance.getSalarystructureid()).get().getNotchId()).get().getName();
				allowancerespo
						.setSalarystructurename(hrmsSalaryScaleRepository
								.findById(hrmsSalarystructureRepository.findById(allowance.getSalarystructureid()).get()
										.getScaleId())
								.get().getName()
								+ "-"
								+ hrmsSalaryscalenotchRepository
										.findById(hrmsSalarystructureRepository
												.findById(allowance.getSalarystructureid()).get().getNotchId())
										.get().getName());
			}
			alowancelist.add(allowancerespo);

		});
		return ResponseEntity.status(HttpStatus.OK).body(alowancelist);
	}

	@Override
	public ResponseEntity<List<SalaryStructureedms>> getAllsalaryStructure() {

		List<SalaryStructureedms> salaryStructureedmslist = new ArrayList<>();

		List<HrmsSalarystructure> dbms = hrmsSalarystructureRepository.findByActive(1);

		dbms.forEach(dbm -> {
			SalaryStructureedms salaryStructureedms = new SalaryStructureedms();
			salaryStructureedms.setSalarystructureid(dbm.getId());

			salaryStructureedms
					.setSalarystructurename(hrmsSalaryScaleRepository.findById(dbm.getScaleId()).get().getName() + "-"
							+ hrmsSalaryscalenotchRepository.findById(dbm.getNotchId()).get().getCode());

			salaryStructureedmslist.add(salaryStructureedms);

		});

		return ResponseEntity.status(HttpStatus.OK).body(salaryStructureedmslist);
	}

}
