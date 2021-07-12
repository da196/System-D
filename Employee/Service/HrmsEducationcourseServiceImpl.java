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

import com.Hrms.Employee.DTO.EducationcourseDetailed;
import com.Hrms.Employee.Entity.HrmsEducationcourse;
import com.Hrms.Employee.Repository.HrmsEducationcourseRepository;
import com.Hrms.Employee.Repository.HrmsEducationlevelRepository;

@Service
public class HrmsEducationcourseServiceImpl implements HrmsEducationcourseService {

	@Autowired
	private HrmsEducationcourseRepository hrmsEducationcourseRepository;

	@Autowired
	private HrmsEducationlevelRepository hrmsEducationlevelRepository;

	@Override
	public ResponseEntity<HrmsEducationcourse> save(HrmsEducationcourse hrmsEducationcourse) {

		UUID uuid = UUID.randomUUID();
		hrmsEducationcourse.setUnique_id(uuid);
		hrmsEducationcourse.setActive(1);
		hrmsEducationcourse.setApproved(0);

		if (hrmsEducationcourseRepository.existsByName(hrmsEducationcourse.getName())) {
			return ResponseEntity.status(HttpStatus.ALREADY_REPORTED).body(hrmsEducationcourse);

		} else {
			if (hrmsEducationcourseRepository.existsByEducationlevelid(hrmsEducationcourse.getEducationlevelid())) {

				return ResponseEntity.status(HttpStatus.OK)
						.body(hrmsEducationcourseRepository.save(hrmsEducationcourse));
			} else {
				return ResponseEntity.status(HttpStatus.PRECONDITION_FAILED).body(hrmsEducationcourse);
			}
		}
	}

	@Override
	public ResponseEntity<Optional<HrmsEducationcourse>> viewHrmsEducationcourse(int id) {

		if (hrmsEducationcourseRepository.existsById(id)) {

			return ResponseEntity.status(HttpStatus.OK).body(hrmsEducationcourseRepository.findById(id));
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
		}
	}

	@Override
	public ResponseEntity<HrmsEducationcourse> update(HrmsEducationcourse hrmsEducationcourse, int id) {

		LocalDateTime LocalTime = LocalDateTime.now();
		hrmsEducationcourse.setDate_updated(LocalTime);
		hrmsEducationcourse.setApproved(0);

		if (hrmsEducationcourseRepository.existsById(id)) {
			if (hrmsEducationcourseRepository.existsByEducationlevelid(hrmsEducationcourse.getEducationlevelid())) {
				return ResponseEntity.status(HttpStatus.OK)
						.body(hrmsEducationcourseRepository.save(hrmsEducationcourse));
			} else {
				return ResponseEntity.status(HttpStatus.PRECONDITION_FAILED).body(hrmsEducationcourse);
			}
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(hrmsEducationcourse);
		}
	}

	@Override
	public ResponseEntity<?> deleteHrmsEducationcourse(int id) {

		if (hrmsEducationcourseRepository.existsById(id)) {
			HrmsEducationcourse hrmsEducationcourse = hrmsEducationcourseRepository.findById(id).get();
			hrmsEducationcourse.setActive(0);
			hrmsEducationcourse.setDate_updated(LocalDateTime.now());
			return ResponseEntity.status(HttpStatus.OK).body(hrmsEducationcourseRepository.save(hrmsEducationcourse));
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
		}
	}

	@Override
	public ResponseEntity<List<HrmsEducationcourse>> listHrmsEducationcourse() {

		// return
		// ResponseEntity.status(HttpStatus.OK).body(hrmsEducationcourseRepository.findAll());

		return ResponseEntity.status(HttpStatus.OK).body(hrmsEducationcourseRepository.findAllByOrderByNameAsc());

	}

	@Override
	public ResponseEntity<List<HrmsEducationcourse>> EducationcourseByLevelCode(int levelCode) {
		List<HrmsEducationcourse> courselist = new ArrayList<>();
		hrmsEducationcourseRepository.findAll().forEach(course -> {
			if (course.getEducationlevelid() == levelCode) {
				courselist.add(course);

			}

		});

		return ResponseEntity.status(HttpStatus.OK).body(courselist);
	}

	@Override
	public ResponseEntity<List<EducationcourseDetailed>> listHrmsEducationcourseDetailed() {
		List<EducationcourseDetailed> courselist = new ArrayList<>();
		hrmsEducationcourseRepository.findByActiveOrderByNameAsc(1).forEach(courses -> {
			EducationcourseDetailed educationcourseDetailed = new EducationcourseDetailed();
			educationcourseDetailed.setAbbreviation(courses.getAbbreviation());
			educationcourseDetailed.setActive(courses.getActive());
			educationcourseDetailed.setApproved(courses.getApproved());
			educationcourseDetailed.setCode(courses.getCode());
			educationcourseDetailed.setDescription(courses.getDescription());
			educationcourseDetailed.setId(courses.getId());
			educationcourseDetailed.setName(courses.getName());
			educationcourseDetailed.setEducationlevelid(courses.getEducationlevelid());
			if (hrmsEducationlevelRepository.existsById(courses.getEducationlevelid())) {
				educationcourseDetailed.setEducationlevel(
						hrmsEducationlevelRepository.findById(courses.getEducationlevelid()).get().getName());
				courselist.add(educationcourseDetailed);
			}

		});

		return ResponseEntity.status(HttpStatus.OK).body(courselist);
	}

}
