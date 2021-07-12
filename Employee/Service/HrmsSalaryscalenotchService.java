package com.Hrms.Employee.Service;

import java.util.List;
import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.Hrms.Employee.Entity.HrmsSalaryscalenotch;

@Service
public interface HrmsSalaryscalenotchService {
	public ResponseEntity<HrmsSalaryscalenotch> save(HrmsSalaryscalenotch hrmsSalaryscalenotch);

	public ResponseEntity<Optional<HrmsSalaryscalenotch>> viewHrmsSalaryscalenotch(int id);

	public ResponseEntity<HrmsSalaryscalenotch> update(HrmsSalaryscalenotch hrmsSalaryscalenotch, int id);

	public ResponseEntity<?> deleteHrmsSalaryscalenotch(int id);

	public ResponseEntity<List<HrmsSalaryscalenotch>> listHrmsSalaryscalenotch();

}
