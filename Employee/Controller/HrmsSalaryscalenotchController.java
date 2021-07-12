package com.Hrms.Employee.Controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.Hrms.Employee.Entity.HrmsSalaryscalenotch;
import com.Hrms.Employee.Service.HrmsSalaryscalenotchService;

@RestController
@RequestMapping("/v1/salaryscalenotch")
public class HrmsSalaryscalenotchController {

	@Autowired
	private HrmsSalaryscalenotchService hrmsSalaryscalenotchService;

	@PostMapping(value = "/addSalaryscalenotch")
	public ResponseEntity<HrmsSalaryscalenotch> addSalaryscalenotch(
			@RequestBody HrmsSalaryscalenotch hrmsSalaryscalenotch) {

		return hrmsSalaryscalenotchService.save(hrmsSalaryscalenotch);
	}

	@GetMapping(value = "/getSalaryscalenotch/{id}")
	public ResponseEntity<Optional<HrmsSalaryscalenotch>> getSalaryscalenotch(@PathVariable("id") int id) {

		return hrmsSalaryscalenotchService.viewHrmsSalaryscalenotch(id);

	}

	@PutMapping(value = "/updateSalaryscalenotch/{id}")
	public ResponseEntity<HrmsSalaryscalenotch> updateSalaryscalenotch(
			@RequestBody HrmsSalaryscalenotch hrmsSalaryscalenotch, @PathVariable("id") int id) {

		return hrmsSalaryscalenotchService.update(hrmsSalaryscalenotch, id);

	}

	@DeleteMapping(value = "/deleteSalaryscalenotch/{id}")
	public ResponseEntity<?> deleteSalaryscalenotch(@PathVariable("id") int id) {

		return hrmsSalaryscalenotchService.deleteHrmsSalaryscalenotch(id);

	}

	@GetMapping(value = "/getAllSalaryscalenotch")
	public ResponseEntity<List<HrmsSalaryscalenotch>> listSalaryscalenotch() {
		return hrmsSalaryscalenotchService.listHrmsSalaryscalenotch();

	}

}
