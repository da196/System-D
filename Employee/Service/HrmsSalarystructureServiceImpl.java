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

import com.Hrms.Employee.DTO.HrmsSalarystructureDetails;
import com.Hrms.Employee.Entity.HrmsSalarystructure;
import com.Hrms.Employee.Repository.HrmsSalaryScaleRepository;
import com.Hrms.Employee.Repository.HrmsSalaryscalenotchRepository;
import com.Hrms.Employee.Repository.HrmsSalarystructureRepository;

@Service
public class HrmsSalarystructureServiceImpl implements HrmsSalarystructureService {

	@Autowired
	private HrmsSalarystructureRepository hrmsSalarystructureRepository;

	@Autowired
	private HrmsSalaryscalenotchRepository hrmsSalaryscalenotchRepository;

	@Autowired
	private HrmsSalaryScaleRepository hrmsSalaryScaleRepository;

	@Override
	public ResponseEntity<HrmsSalarystructure> save(HrmsSalarystructure hrmsSalarystructure) {
		if (hrmsSalarystructureRepository.existsByNotchIdAndScaleIdAndActive(hrmsSalarystructure.getNotchId(),
				hrmsSalarystructure.getScaleId(), 1)) {
			return ResponseEntity.status(HttpStatus.ALREADY_REPORTED).body(hrmsSalarystructure);
		}
		UUID uuid = UUID.randomUUID();
		hrmsSalarystructure.setUnique_id(uuid);
		hrmsSalarystructure.setActive(1);
		hrmsSalarystructure.setApproved(0);
		if (hrmsSalarystructure.getNotchId() != 0 && hrmsSalarystructure.getScaleId() != 0
				&& hrmsSalaryscalenotchRepository.existsByIdAndActive(hrmsSalarystructure.getNotchId(), 1)
				&& hrmsSalaryScaleRepository.existsByIdAndActive(hrmsSalarystructure.getScaleId(), 1)) {

			return ResponseEntity.status(HttpStatus.OK).body(hrmsSalarystructureRepository.save(hrmsSalarystructure));
		} else {
			return ResponseEntity.status(HttpStatus.PRECONDITION_FAILED).body(hrmsSalarystructure);
		}
	}

	@Override
	public ResponseEntity<Optional<HrmsSalarystructure>> viewHrmsSalarystructure(int id) {

		if (hrmsSalarystructureRepository.existsByIdAndActive(id, 1)) {

			return ResponseEntity.status(HttpStatus.OK).body(hrmsSalarystructureRepository.findById(id));
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
		}
	}

	@Override
	public ResponseEntity<HrmsSalarystructure> update(HrmsSalarystructure hrmsSalarystructure, int id) {

		LocalDateTime LocalTime = LocalDateTime.now();
		hrmsSalarystructure.setDate_updated(LocalTime);
		hrmsSalarystructure.setActive(1);
		hrmsSalarystructure.setApproved(0);
		if (hrmsSalarystructureRepository.existsByIdAndActive(id, 1)) {
			if (hrmsSalarystructure.getNotchId() != 0 && hrmsSalarystructure.getScaleId() != 0
					&& hrmsSalaryscalenotchRepository.existsByIdAndActive(hrmsSalarystructure.getNotchId(), 1)
					&& hrmsSalaryScaleRepository.existsByIdAndActive(hrmsSalarystructure.getScaleId(), 1)) {
				return ResponseEntity.status(HttpStatus.OK)
						.body(hrmsSalarystructureRepository.save(hrmsSalarystructure));
			} else {
				return ResponseEntity.status(HttpStatus.PRECONDITION_FAILED).body(hrmsSalarystructure);
			}
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(hrmsSalarystructure);
		}
	}

	@Override
	public ResponseEntity<?> deleteHrmsSalarystructure(int id) {

		if (hrmsSalarystructureRepository.existsById(id)) {
			hrmsSalarystructureRepository.deleteById(id);
			return ResponseEntity.status(HttpStatus.OK).body(null);
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
		}
	}

	@Override
	public ResponseEntity<List<HrmsSalarystructureDetails>> listHrmsSalarystructure() {

		List<HrmsSalarystructureDetails> sstructurelist = new ArrayList<>();
		List<HrmsSalarystructure> hrmsSalarystructurelist = hrmsSalarystructureRepository.findByActive(1);

		hrmsSalarystructurelist.forEach(sstructure -> {
			HrmsSalarystructureDetails sstructuredetails = new HrmsSalarystructureDetails();

			sstructuredetails.setActive(sstructure.getActive());
			sstructuredetails.setApproved(sstructure.getApproved());
			sstructuredetails.setBasicSalary(sstructure.getBasicSalary());
			sstructuredetails.setBasicsalaryMax(sstructure.getBasicsalaryMax());
			sstructuredetails.setBasicsalaryMin(sstructure.getBasicsalaryMin());
			sstructuredetails.setDate_created(sstructure.getDate_created());
			sstructuredetails.setDate_updated(sstructure.getDate_updated());
			sstructuredetails.setId(sstructure.getId());
			if (sstructure.getNotchId() != 0) {
				sstructuredetails
						.setNotch(hrmsSalaryscalenotchRepository.findById(sstructure.getNotchId()).get().getName());
			}
			sstructuredetails.setDescription(sstructure.getDescription());
			sstructuredetails.setNotchId(sstructure.getNotchId());
			sstructuredetails.setScaleId(sstructure.getScaleId());
			if (sstructure.getScaleId() != 0) {
				sstructuredetails
						.setScalename(hrmsSalaryScaleRepository.findById(sstructure.getScaleId()).get().getName());
			}
			sstructurelist.add(sstructuredetails);

		});

		return ResponseEntity.status(HttpStatus.OK).body(sstructurelist);
	}

}
