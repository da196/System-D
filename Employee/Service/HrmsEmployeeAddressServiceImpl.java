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

import com.Hrms.Communication.SendEmail;
import com.Hrms.Employee.DTO.EmployeeApprovalComment;
import com.Hrms.Employee.DTO.HrmsEmployeeAddressContactRequest;
import com.Hrms.Employee.DTO.HrmsEmployeeAddressContactResponse;
import com.Hrms.Employee.Entity.HrmsContact;
import com.Hrms.Employee.Entity.HrmsEmployee;
import com.Hrms.Employee.Entity.HrmsEmployeeAddress;
import com.Hrms.Employee.Entity.HrmsEmployeeContact;
import com.Hrms.Employee.Entity.HrmsLocationAddress;
import com.Hrms.Employee.Repository.HrmsContactRepository;
import com.Hrms.Employee.Repository.HrmsContactTypeRepository;
import com.Hrms.Employee.Repository.HrmsEmployeeAddressRepository;
import com.Hrms.Employee.Repository.HrmsEmployeeContactRepository;
import com.Hrms.Employee.Repository.HrmsEmployeeRepository;
import com.Hrms.Employee.Repository.HrmsLocationAddressRepository;
import com.Hrms.Employee.Repository.HrmsLocationAddressTypeRepository;
import com.Hrms.Employee.Repository.HrmsLocationCityRepository;

@Service
public class HrmsEmployeeAddressServiceImpl implements HrmsEmployeeAddressService {

	@Autowired
	private HrmsEmployeeAddressRepository hrmsEmployeeAddressRepository;

	@Autowired
	private HrmsLocationAddressTypeRepository hrmsLocationAddressTypeRepository;

	@Autowired
	private HrmsLocationAddressRepository hrmsLocationAddressRepository;

	@Autowired
	private HrmsLocationCityRepository hrmsLocationCityRepository;

	@Autowired
	private HrmsContactRepository hrmsContactRepository;

	@Autowired
	private HrmsContactTypeRepository hrmsContactTypeRepository;

	@Autowired
	private HrmsEmployeeContactRepository hrmsEmployeeContactRepository;

	@Autowired
	private HrmsEmployeeRepository hrmsEmployeeRepository;

	@Autowired
	private SendEmail sendEmail;

	@Override
	public ResponseEntity<HrmsEmployeeAddressContactRequest> addEmployeeAddress(
			HrmsEmployeeAddressContactRequest hrmsEmployeeAddressContactRequest) {
		if (hrmsEmployeeAddressContactRequest.getAddresstypeid() == 1) {

			if (hrmsEmployeeAddressRepository.existsByEmployeeid(hrmsEmployeeAddressContactRequest.getEmployeeid())
					&& DoubleAddressverifier(hrmsEmployeeAddressContactRequest.getEmployeeid(),
							hrmsEmployeeAddressContactRequest.getAddresstypeid())) {
				return ResponseEntity.status(HttpStatus.ALREADY_REPORTED).body(hrmsEmployeeAddressContactRequest);
			} else {

				if (hrmsLocationAddressTypeRepository.existsById(hrmsEmployeeAddressContactRequest.getAddresstypeid())

						&& hrmsEmployeeRepository.existsById(hrmsEmployeeAddressContactRequest.getEmployeeid())
						&& hrmsLocationCityRepository.existsById(hrmsEmployeeAddressContactRequest.getAdresscityid())) {

					HrmsContact contact = new HrmsContact();

					HrmsEmployeeContact empcontact = new HrmsEmployeeContact();

					// add contact details

					contact.setUnique_id(UUID.randomUUID());
					contact.setActive(1);
					contact.setApproved(0);
					// Contact type was requested to be removed by DCRM Team
					// contact.setContacttypeid(hrmsEmployeeAddressContactRequest.getContacttypeid());
					contact.setEmailaddress(hrmsEmployeeAddressContactRequest.getContactemailaddress());
					contact.setPhoneprimary(hrmsEmployeeAddressContactRequest.getContactphoneprimary());
					contact.setPhonesecondary(hrmsEmployeeAddressContactRequest.getContactphonesecondary());
					int contactid = hrmsContactRepository.save(contact).getId();

					// add employee contact now

					empcontact.setUnique_id(UUID.randomUUID());
					empcontact.setActive(1);
					empcontact.setApproved(0);
					empcontact.setContactid(contactid);
					empcontact.setDateend(hrmsEmployeeAddressContactRequest.getContactdateend());
					empcontact.setDatestart(hrmsEmployeeAddressContactRequest.getContactdatestart());
					empcontact.setDescriptionend(hrmsEmployeeAddressContactRequest.getContactdescriptionend());
					empcontact.setDescriptionstart(hrmsEmployeeAddressContactRequest.getAdressdescriptionstart());
					empcontact.setEmployeeid(hrmsEmployeeAddressContactRequest.getEmployeeid());

					hrmsEmployeeContactRepository.save(empcontact);

					// add adress details

					HrmsLocationAddress adress = new HrmsLocationAddress();

					adress.setActive(1);
					adress.setApproved(0);
					adress.setUnique_id(UUID.randomUUID());
					adress.setAddressline1(hrmsEmployeeAddressContactRequest.getAddressline1());
					adress.setAddressline2(hrmsEmployeeAddressContactRequest.getAddressline2());
					adress.setAddresstypeid(hrmsEmployeeAddressContactRequest.getAddresstypeid());
					adress.setCityid(hrmsEmployeeAddressContactRequest.getAdresscityid());
					adress.setDescription(hrmsEmployeeAddressContactRequest.getAdressdescription());
					adress.setPostalcode(hrmsEmployeeAddressContactRequest.getPostalcode());

					int adressid = hrmsLocationAddressRepository.save(adress).getId();

					// add employee adress details
					HrmsEmployeeAddress empadress = new HrmsEmployeeAddress();

					empadress.setActive(1);
					empadress.setApproved(0);
					empadress.setAddressid(adressid);
					empadress.setUnique_id(UUID.randomUUID());
					empadress.setDate_start(hrmsEmployeeAddressContactRequest.getAdressdatestart());
					empadress.setDate_end(hrmsEmployeeAddressContactRequest.getAdressdateend());
					empadress.setDescription_end(hrmsEmployeeAddressContactRequest.getAdressdescriptionend());
					empadress.setDescription_start(hrmsEmployeeAddressContactRequest.getAdressdescriptionstart());
					empadress.setDescription_end(hrmsEmployeeAddressContactRequest.getAdressdescription());
					empadress.setEmployeeid(hrmsEmployeeAddressContactRequest.getEmployeeid());

					hrmsEmployeeAddressRepository.save(empadress);

					return ResponseEntity.status(HttpStatus.OK).body(hrmsEmployeeAddressContactRequest);
				} else {
					return ResponseEntity.status(HttpStatus.PRECONDITION_FAILED)
							.body(hrmsEmployeeAddressContactRequest);
				}
			}
		} else {
			if (hrmsLocationAddressTypeRepository.existsById(hrmsEmployeeAddressContactRequest.getAddresstypeid())
					&& hrmsEmployeeRepository.existsById(hrmsEmployeeAddressContactRequest.getEmployeeid())
					&& hrmsLocationCityRepository.existsById(hrmsEmployeeAddressContactRequest.getAdresscityid())) {

				if (hrmsEmployeeAddressRepository.existsByEmployeeid(hrmsEmployeeAddressContactRequest.getEmployeeid())
						&& DoubleAddressverifier(hrmsEmployeeAddressContactRequest.getEmployeeid(),
								hrmsEmployeeAddressContactRequest.getAddresstypeid())) {
					return ResponseEntity.status(HttpStatus.ALREADY_REPORTED).body(hrmsEmployeeAddressContactRequest);
				}

				// add adress details

				HrmsLocationAddress adress = new HrmsLocationAddress();

				adress.setActive(1);
				adress.setApproved(0);
				adress.setUnique_id(UUID.randomUUID());
				adress.setAddressline1(hrmsEmployeeAddressContactRequest.getAddressline1());
				adress.setAddressline2(hrmsEmployeeAddressContactRequest.getAddressline2());
				adress.setAddresstypeid(hrmsEmployeeAddressContactRequest.getAddresstypeid());
				adress.setCityid(hrmsEmployeeAddressContactRequest.getAdresscityid());
				adress.setDescription(hrmsEmployeeAddressContactRequest.getAdressdescription());
				adress.setPostalcode(hrmsEmployeeAddressContactRequest.getPostalcode());

				int adressid = hrmsLocationAddressRepository.save(adress).getId();

				// add employee adress details
				HrmsEmployeeAddress empadress = new HrmsEmployeeAddress();

				empadress.setActive(1);
				empadress.setApproved(0);
				empadress.setAddressid(adressid);
				empadress.setUnique_id(UUID.randomUUID());
				empadress.setDate_start(hrmsEmployeeAddressContactRequest.getAdressdatestart());
				empadress.setDate_end(hrmsEmployeeAddressContactRequest.getAdressdateend());
				empadress.setDescription_end(hrmsEmployeeAddressContactRequest.getAdressdescriptionend());
				empadress.setDescription_start(hrmsEmployeeAddressContactRequest.getAdressdescriptionstart());
				empadress.setDescription_end(hrmsEmployeeAddressContactRequest.getAdressdescription());
				empadress.setEmployeeid(hrmsEmployeeAddressContactRequest.getEmployeeid());

				hrmsEmployeeAddressRepository.save(empadress);

				return ResponseEntity.status(HttpStatus.OK).body(hrmsEmployeeAddressContactRequest);
			} else {
				return ResponseEntity.status(HttpStatus.PRECONDITION_FAILED).body(hrmsEmployeeAddressContactRequest);
			}
		}

	}

	@Override
	public ResponseEntity<Optional<HrmsEmployeeAddress>> getEmployeeAddress(int id) {
		if (hrmsEmployeeAddressRepository.existsById(id)) {

			return ResponseEntity.status(HttpStatus.OK).body(hrmsEmployeeAddressRepository.findById(id));
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
		}
	}

	@Override
	public ResponseEntity<HrmsEmployeeAddressContactRequest> updateEmployeeAddress(
			HrmsEmployeeAddressContactRequest hrmsEmployeeAddressContactRequest, int aid, int cid) {

		LocalDateTime LocalTime = LocalDateTime.now();

		if (hrmsLocationAddressRepository.existsByIdAndActive(aid, 1)) {

			if (hrmsLocationAddressTypeRepository.existsById(hrmsEmployeeAddressContactRequest.getAddresstypeid())

					&& hrmsEmployeeRepository.existsById(hrmsEmployeeAddressContactRequest.getEmployeeid())
					&& hrmsLocationCityRepository.existsById(hrmsEmployeeAddressContactRequest.getAdresscityid())) {

				if (hrmsEmployeeAddressContactRequest.getContactid() != 0
						&& hrmsContactRepository.existsById(hrmsEmployeeAddressContactRequest.getContactid())) {
					HrmsContact contact = hrmsContactRepository.findByIdAndActive(cid, 1);

					HrmsEmployeeContact empcontact = hrmsEmployeeContactRepository.findByContactid(cid);

					// add contact details
					contact.setDate_updated(LocalTime);
					// contact.setContacttypeid(hrmsEmployeeAddressContactRequest.getContacttypeid());
					contact.setEmailaddress(hrmsEmployeeAddressContactRequest.getContactemailaddress());
					contact.setPhoneprimary(hrmsEmployeeAddressContactRequest.getContactphoneprimary());
					contact.setPhonesecondary(hrmsEmployeeAddressContactRequest.getContactphonesecondary());
					int contactid = hrmsContactRepository.save(contact).getId();

					// add employee contact now
					empcontact.setDate_updated(LocalDateTime.now());
					empcontact.setDateend(hrmsEmployeeAddressContactRequest.getContactdateend());
					empcontact.setDatestart(hrmsEmployeeAddressContactRequest.getContactdatestart());
					empcontact.setDescriptionend(hrmsEmployeeAddressContactRequest.getContactdescriptionend());
					empcontact.setDescriptionstart(hrmsEmployeeAddressContactRequest.getAdressdescriptionstart());

					hrmsEmployeeContactRepository.save(empcontact);
				}
				if (hrmsEmployeeAddressContactRequest.getContactid() == 0
						&& hrmsEmployeeAddressContactRequest.getAdresscityid() == 1) {

					HrmsContact contact = new HrmsContact();

					HrmsEmployeeContact empcontact = new HrmsEmployeeContact();

					// add contact details

					contact.setUnique_id(UUID.randomUUID());
					contact.setActive(1);
					contact.setApproved(0);
					// Contact type was requested to be removed by DCRM Team
					// contact.setContacttypeid(hrmsEmployeeAddressContactRequest.getContacttypeid());
					contact.setEmailaddress(hrmsEmployeeAddressContactRequest.getContactemailaddress());
					contact.setPhoneprimary(hrmsEmployeeAddressContactRequest.getContactphoneprimary());
					contact.setPhonesecondary(hrmsEmployeeAddressContactRequest.getContactphonesecondary());
					int contactid = hrmsContactRepository.save(contact).getId();

					// add employee contact now

					empcontact.setUnique_id(UUID.randomUUID());
					empcontact.setActive(1);
					empcontact.setApproved(0);
					empcontact.setContactid(contactid);
					empcontact.setDateend(hrmsEmployeeAddressContactRequest.getContactdateend());
					empcontact.setDatestart(hrmsEmployeeAddressContactRequest.getContactdatestart());
					empcontact.setDescriptionend(hrmsEmployeeAddressContactRequest.getContactdescriptionend());
					empcontact.setDescriptionstart(hrmsEmployeeAddressContactRequest.getAdressdescriptionstart());
					empcontact.setEmployeeid(hrmsEmployeeAddressContactRequest.getEmployeeid());

					hrmsEmployeeContactRepository.save(empcontact);
					// add adress details

					HrmsLocationAddress adress = hrmsLocationAddressRepository.findByIdAndActive(aid, 1);
					adress.setAddressline1(hrmsEmployeeAddressContactRequest.getAddressline1());
					adress.setAddressline2(hrmsEmployeeAddressContactRequest.getAddressline2());
					adress.setAddresstypeid(hrmsEmployeeAddressContactRequest.getAddresstypeid());
					adress.setCityid(hrmsEmployeeAddressContactRequest.getAdresscityid());
					adress.setDescription(hrmsEmployeeAddressContactRequest.getAdressdescription());
					adress.setPostalcode(hrmsEmployeeAddressContactRequest.getPostalcode());
					adress.setDate_updated(LocalDateTime.now());
					int adressid = hrmsLocationAddressRepository.save(adress).getId();

					// add employee adress details
					HrmsEmployeeAddress empadress = hrmsEmployeeAddressRepository.findByAddressid(aid);
					empadress.setDate_updated(LocalDateTime.now());
					empadress.setDate_start(hrmsEmployeeAddressContactRequest.getAdressdatestart());
					empadress.setDate_end(hrmsEmployeeAddressContactRequest.getAdressdateend());
					empadress.setDescription_end(hrmsEmployeeAddressContactRequest.getAdressdescriptionend());
					empadress.setDescription_start(hrmsEmployeeAddressContactRequest.getAdressdescriptionstart());
					empadress.setDescription_end(hrmsEmployeeAddressContactRequest.getAdressdescription());

					hrmsEmployeeAddressRepository.save(empadress);

					return ResponseEntity.status(HttpStatus.OK).body(hrmsEmployeeAddressContactRequest);
				} else {

					// add adress details

					HrmsLocationAddress adress = hrmsLocationAddressRepository.findByIdAndActive(aid, 1);
					adress.setAddressline1(hrmsEmployeeAddressContactRequest.getAddressline1());
					adress.setAddressline2(hrmsEmployeeAddressContactRequest.getAddressline2());
					adress.setAddresstypeid(hrmsEmployeeAddressContactRequest.getAddresstypeid());
					adress.setCityid(hrmsEmployeeAddressContactRequest.getAdresscityid());
					adress.setDescription(hrmsEmployeeAddressContactRequest.getAdressdescription());
					adress.setPostalcode(hrmsEmployeeAddressContactRequest.getPostalcode());
					adress.setDate_updated(LocalDateTime.now());
					int adressid = hrmsLocationAddressRepository.save(adress).getId();

					// add employee adress details
					HrmsEmployeeAddress empadress = hrmsEmployeeAddressRepository.findByAddressid(aid);
					empadress.setDate_updated(LocalDateTime.now());
					empadress.setDate_start(hrmsEmployeeAddressContactRequest.getAdressdatestart());
					empadress.setDate_end(hrmsEmployeeAddressContactRequest.getAdressdateend());
					empadress.setDescription_end(hrmsEmployeeAddressContactRequest.getAdressdescriptionend());
					empadress.setDescription_start(hrmsEmployeeAddressContactRequest.getAdressdescriptionstart());
					empadress.setDescription_end(hrmsEmployeeAddressContactRequest.getAdressdescription());

					hrmsEmployeeAddressRepository.save(empadress);

					return ResponseEntity.status(HttpStatus.OK).body(hrmsEmployeeAddressContactRequest);
				}
			} else {
				return ResponseEntity.status(HttpStatus.PRECONDITION_FAILED).body(hrmsEmployeeAddressContactRequest);
			}
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(hrmsEmployeeAddressContactRequest);
		}
	}

	@Override
	public ResponseEntity<?> deleteEmployeeAddress(int aid, int cid) {

		if (cid != 0) {
			if (hrmsLocationAddressRepository.existsByIdAndActive(aid, 1)
					&& hrmsContactRepository.existsByIdAndActive(cid, 1)) {
				// deactivate contact and adress tables
				// start with adress

				HrmsLocationAddress hrmsLocationAddress = hrmsLocationAddressRepository.findByIdAndActive(aid, 1);

				hrmsLocationAddress.setActive(0);
				hrmsLocationAddress.setDate_updated(LocalDateTime.now());
				hrmsLocationAddressRepository.save(hrmsLocationAddress);

				// start contact now
				HrmsContact hrmsContact = hrmsContactRepository.findByIdAndActive(cid, 1);
				hrmsContact.setActive(0);
				hrmsContact.setDate_updated(LocalDateTime.now());
				hrmsContactRepository.save(hrmsContact);

				// move to deactivate emp adress now
				HrmsEmployeeAddress hrmsEmployeeAddress = hrmsEmployeeAddressRepository.findByAddressid(aid);
				hrmsEmployeeAddress.setActive(0);
				hrmsEmployeeAddress.setDate_updated(LocalDateTime.now());
				hrmsEmployeeAddressRepository.save(hrmsEmployeeAddress);

				// move to deactivate emp contact now
				HrmsEmployeeContact hrmsEmployeeContact = hrmsEmployeeContactRepository.findByContactid(cid);
				hrmsEmployeeContact.setActive(0);
				hrmsEmployeeContact.setDate_updated(LocalDateTime.now());
				hrmsEmployeeContactRepository.save(hrmsEmployeeContact);
				return ResponseEntity.status(HttpStatus.OK).body(null);
			} else {
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
			}
		} else {
			if (hrmsLocationAddressRepository.existsByIdAndActive(aid, 1)) {
				// deactivate contact and adress tables
				// start with adress

				HrmsLocationAddress hrmsLocationAddress = hrmsLocationAddressRepository.findByIdAndActive(aid, 1);

				hrmsLocationAddress.setActive(0);
				hrmsLocationAddress.setDate_updated(LocalDateTime.now());
				hrmsLocationAddressRepository.save(hrmsLocationAddress);

				// start contact now

				// move to deactivate emp adress now
				HrmsEmployeeAddress hrmsEmployeeAddress = hrmsEmployeeAddressRepository.findByAddressid(aid);
				hrmsEmployeeAddress.setActive(0);
				hrmsEmployeeAddress.setDate_updated(LocalDateTime.now());
				hrmsEmployeeAddressRepository.save(hrmsEmployeeAddress);

				// move to deactivate emp contact now

				return ResponseEntity.status(HttpStatus.OK).body(null);
			} else {
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
			}
		}
	}

	@Override
	public ResponseEntity<List<HrmsEmployeeAddressContactResponse>> listEmployeeAddress() {
		List<HrmsEmployeeAddressContactResponse> empadresscontactlist = new ArrayList<>();
		List<HrmsEmployeeAddress> empadres = hrmsEmployeeAddressRepository.findByActiveOrderByEmployeeid(1);

		List<HrmsEmployeeContact> empcontact = hrmsEmployeeContactRepository.findByActiveOrderByEmployeeid(1);

		for (int i = 0; i < empadres.size(); i++) {
			HrmsEmployeeAddressContactResponse empcontactadress = new HrmsEmployeeAddressContactResponse();
			empcontactadress.setActive(empadres.get(i).getActive());
			empcontactadress.setApproved(empadres.get(i).getApproved());
			int adresid = empadres.get(i).getAddressid();
			empcontactadress.setAdressid(adresid);

			empcontactadress.setEmployeeid(empadres.get(i).getEmployeeid());

			HrmsEmployee hrmsEmployee = hrmsEmployeeRepository.findById(empadres.get(i).getEmployeeid()).get();

			StringBuilder fullname = new StringBuilder();
			fullname.append(hrmsEmployee.getFirstName().trim());
			fullname.append("  " + hrmsEmployee.getMiddleName().trim());
			fullname.append(" " + hrmsEmployee.getLastName().trim());
			empcontactadress.setApprovalComment(empadres.get(i).getApprovalComment());
			empcontactadress.setEmployeename(fullname.toString());

			empcontactadress.setAddressline1(hrmsLocationAddressRepository.findById(adresid).get().getAddressline1());

			empcontactadress.setAddressline2(hrmsLocationAddressRepository.findById(adresid).get().getAddressline2());

			int adrestypeid = hrmsLocationAddressRepository.findById(adresid).get().getAddresstypeid();

			empcontactadress.setAddresstypeid(adrestypeid);

			empcontactadress
					.setAddresstypename(hrmsLocationAddressTypeRepository.findById(adrestypeid).get().getName());
			int cityid = hrmsLocationAddressRepository.findById(adresid).get().getCityid();
			empcontactadress.setAdresscityid(cityid);
			empcontactadress.setAdresscity(hrmsLocationCityRepository.findById(cityid).get().getName());
			if (empadres.get(i).getDate_end() != null) {
				empcontactadress.setAdressdateend(empadres.get(i).getDate_end());
			}

			if (empadres.get(i).getDate_start() != null) {
				empcontactadress.setAdressdatestart(empadres.get(i).getDate_start());
			}

			empcontactadress
					.setAdressdescription(hrmsLocationAddressRepository.findById(adresid).get().getDescription());
			empcontactadress.setAdressdescriptionend(empadres.get(i).getDescription_end());
			empcontactadress.setAdressdescriptionstart(empadres.get(i).getDescription_start());
			empcontactadress.setEmployeeid(empadres.get(i).getEmployeeid());

			empcontactadress.setEmpadressid(empadres.get(i).getId());
			if (i < empcontact.size()) {
				empcontactadress.setEmpcontactid(empcontact.get(i).getId());
				if (empcontact.get(i).getDateend() != null) {
					empcontactadress.setContactdateend(empcontact.get(i).getDateend());
				}

				if (empcontact.get(i).getDatestart() != null) {
					empcontactadress.setContactdatestart(empcontact.get(i).getDatestart());
				}

				empcontactadress.setContactdescriptionend(empcontact.get(i).getDescriptionend());
				empcontactadress.setContactdescriptionstart(empcontact.get(i).getDescriptionstart());
				int contactid = empcontact.get(i).getContactid();
				empcontactadress.setContactid(contactid);

				empcontactadress
						.setContactemailaddress(hrmsContactRepository.findById(contactid).get().getEmailaddress());
				empcontactadress.setContactid(contactid);
				empcontactadress
						.setContactphoneprimary(hrmsContactRepository.findById(contactid).get().getPhoneprimary());
				empcontactadress
						.setContactphonesecondary(hrmsContactRepository.findById(contactid).get().getPhonesecondary());
				// int contypeid =
				// hrmsContactRepository.findById(contactid).get().getContacttypeid();
				// empcontactadress.setContacttypeid(contypeid);
				// empcontactadress.setContacttypename(hrmsContactTypeRepository.findById(contypeid).get().getName());
			}
			empcontactadress.setPostalcode(hrmsLocationAddressRepository.findById(adresid).get().getPostalcode());
			empadresscontactlist.add(empcontactadress);

		}

		return ResponseEntity.status(HttpStatus.OK).body(empadresscontactlist);

	}

	@Override
	public ResponseEntity<List<HrmsEmployeeAddressContactResponse>> getEmployeeAddressByEmpId(int empid) {
		if (hrmsEmployeeAddressRepository.existsByEmployeeid(empid)
				&& hrmsEmployeeContactRepository.existsByEmployeeid(empid)) {

			List<HrmsEmployeeAddressContactResponse> empadresscontactlist = new ArrayList<>();
			List<HrmsEmployeeAddress> empadres = hrmsEmployeeAddressRepository.findByEmployeeidAndActive(empid, 1);

			List<HrmsEmployeeContact> empcontact = hrmsEmployeeContactRepository.findByEmployeeidAndActive(empid, 1);

			for (int i = 0; i < empadres.size(); i++) {
				HrmsEmployeeAddressContactResponse empcontactadress = new HrmsEmployeeAddressContactResponse();
				empcontactadress.setActive(empadres.get(i).getActive());
				empcontactadress.setApproved(empadres.get(i).getApproved());
				int adresid = empadres.get(i).getAddressid();
				empcontactadress.setAdressid(adresid);
				empcontactadress.setApprovalComment(empadres.get(i).getApprovalComment());
				empcontactadress
						.setAddressline1(hrmsLocationAddressRepository.findById(adresid).get().getAddressline1());

				empcontactadress
						.setAddressline2(hrmsLocationAddressRepository.findById(adresid).get().getAddressline2());

				int adrestypeid = hrmsLocationAddressRepository.findById(adresid).get().getAddresstypeid();

				empcontactadress.setAddresstypeid(adrestypeid);

				empcontactadress
						.setAddresstypename(hrmsLocationAddressTypeRepository.findById(adrestypeid).get().getName());
				int cityid = hrmsLocationAddressRepository.findById(adresid).get().getCityid();
				empcontactadress.setAdresscityid(cityid);
				empcontactadress.setAdresscity(hrmsLocationCityRepository.findById(cityid).get().getName());
				if (empadres.get(i).getDate_end() != null) {
					empcontactadress.setAdressdateend(empadres.get(i).getDate_end());
				}

				if (empadres.get(i).getDate_start() != null) {
					empcontactadress.setAdressdatestart(empadres.get(i).getDate_start());
				}

				empcontactadress
						.setAdressdescription(hrmsLocationAddressRepository.findById(adresid).get().getDescription());
				empcontactadress.setAdressdescriptionend(empadres.get(i).getDescription_end());
				empcontactadress.setAdressdescriptionstart(empadres.get(i).getDescription_start());
				empcontactadress.setEmployeeid(empadres.get(i).getEmployeeid());

				empcontactadress.setEmpadressid(empadres.get(i).getId());
				if (i < empcontact.size()) {
					empcontactadress.setEmpcontactid(empcontact.get(i).getId());
					if (empcontact.get(i).getDateend() != null) {
						empcontactadress.setContactdateend(empcontact.get(i).getDateend());
					}

					if (empcontact.get(i).getDatestart() != null) {
						empcontactadress.setContactdatestart(empcontact.get(i).getDatestart());
					}

					empcontactadress.setContactdescriptionend(empcontact.get(i).getDescriptionend());
					empcontactadress.setContactdescriptionstart(empcontact.get(i).getDescriptionstart());
					int contactid = empcontact.get(i).getContactid();
					empcontactadress.setContactid(contactid);

					empcontactadress
							.setContactemailaddress(hrmsContactRepository.findById(contactid).get().getEmailaddress());
					empcontactadress.setContactid(contactid);
					empcontactadress
							.setContactphoneprimary(hrmsContactRepository.findById(contactid).get().getPhoneprimary());
					empcontactadress.setContactphonesecondary(
							hrmsContactRepository.findById(contactid).get().getPhonesecondary());
					// int contypeid =
					// hrmsContactRepository.findById(contactid).get().getContacttypeid();
					// empcontactadress.setContacttypeid(contypeid);
					// empcontactadress.setContacttypename(hrmsContactTypeRepository.findById(contypeid).get().getName());
				}
				empcontactadress.setPostalcode(hrmsLocationAddressRepository.findById(adresid).get().getPostalcode());
				empadresscontactlist.add(empcontactadress);

			}

			return ResponseEntity.status(HttpStatus.OK).body(empadresscontactlist);
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
		}
	}

	@Override
	public ResponseEntity<HrmsEmployeeAddressContactResponse> getEmployeeAddressByAdressIdAndContact(int aid, int cid) {
		if (hrmsLocationAddressRepository.existsById(aid) && hrmsContactRepository.existsById(cid)) {

			HrmsEmployeeAddress empadres = hrmsEmployeeAddressRepository.findByAddressid(aid);

			HrmsEmployeeContact empcontact = hrmsEmployeeContactRepository.findByContactid(cid);

			HrmsEmployeeAddressContactResponse empcontactadress = new HrmsEmployeeAddressContactResponse();

			empcontactadress.setActive(empadres.getActive());
			empcontactadress.setApproved(empadres.getApproved());
			int adresid = empadres.getAddressid();
			empcontactadress.setAdressid(adresid);
			empcontactadress.setApprovalComment(empadres.getApprovalComment());
			empcontactadress.setAddressline1(hrmsLocationAddressRepository.findById(adresid).get().getAddressline1());

			empcontactadress.setAddressline2(hrmsLocationAddressRepository.findById(adresid).get().getAddressline2());

			int adrestypeid = hrmsLocationAddressRepository.findById(adresid).get().getAddresstypeid();

			empcontactadress.setAddresstypeid(adrestypeid);

			empcontactadress
					.setAddresstypename(hrmsLocationAddressTypeRepository.findById(adrestypeid).get().getName());
			int cityid = hrmsLocationAddressRepository.findById(adresid).get().getCityid();
			empcontactadress.setAdresscityid(cityid);
			empcontactadress.setAdresscity(hrmsLocationCityRepository.findById(cityid).get().getName());
			if (empadres.getDate_end() != null) {
				empcontactadress.setAdressdateend(empadres.getDate_end());
			}

			if (empadres.getDate_start() != null) {
				empcontactadress.setAdressdatestart(empadres.getDate_start());
			}

			empcontactadress
					.setAdressdescription(hrmsLocationAddressRepository.findById(adresid).get().getDescription());
			empcontactadress.setAdressdescriptionend(empadres.getDescription_end());
			empcontactadress.setAdressdescriptionstart(empadres.getDescription_start());
			empcontactadress.setEmployeeid(empadres.getEmployeeid());
			empcontactadress.setEmpadressid(empadres.getId());
			empcontactadress.setEmpcontactid(empcontact.getId());
			if (empcontact.getDateend() != null) {
				empcontactadress.setContactdateend(empcontact.getDateend());
			}

			if (empcontact.getDatestart() != null) {
				empcontactadress.setContactdatestart(empcontact.getDatestart());
			}

			empcontactadress.setContactdescriptionend(empcontact.getDescriptionend());
			empcontactadress.setContactdescriptionstart(empcontact.getDescriptionstart());
			int contactid = empcontact.getContactid();
			empcontactadress.setContactid(contactid);

			empcontactadress.setContactemailaddress(hrmsContactRepository.findById(contactid).get().getEmailaddress());
			empcontactadress.setContactid(contactid);
			empcontactadress.setContactphoneprimary(hrmsContactRepository.findById(contactid).get().getPhoneprimary());
			empcontactadress
					.setContactphonesecondary(hrmsContactRepository.findById(contactid).get().getPhonesecondary());
			// int contypeid =
			// hrmsContactRepository.findById(contactid).get().getContacttypeid();
			// empcontactadress.setContacttypeid(contypeid);
			// empcontactadress.setContacttypename(hrmsContactTypeRepository.findById(contypeid).get().getName());
			empcontactadress.setPostalcode(hrmsLocationAddressRepository.findById(adresid).get().getPostalcode());

			return ResponseEntity.status(HttpStatus.OK).body(empcontactadress);
		} else {

			if (hrmsLocationAddressRepository.existsById(aid) && !hrmsContactRepository.existsById(cid)) {
				HrmsEmployeeAddress empadres = hrmsEmployeeAddressRepository.findByAddressid(aid);

				// HrmsEmployeeContact empcontact =
				// hrmsEmployeeContactRepository.findByContactid(cid);

				HrmsEmployeeAddressContactResponse empcontactadress = new HrmsEmployeeAddressContactResponse();

				empcontactadress.setActive(empadres.getActive());
				empcontactadress.setApproved(empadres.getApproved());
				int adresid = empadres.getAddressid();
				empcontactadress.setAdressid(adresid);
				empcontactadress
						.setAddressline1(hrmsLocationAddressRepository.findById(adresid).get().getAddressline1());

				empcontactadress
						.setAddressline2(hrmsLocationAddressRepository.findById(adresid).get().getAddressline2());

				int adrestypeid = hrmsLocationAddressRepository.findById(adresid).get().getAddresstypeid();

				empcontactadress.setAddresstypeid(adrestypeid);

				empcontactadress
						.setAddresstypename(hrmsLocationAddressTypeRepository.findById(adrestypeid).get().getName());
				int cityid = hrmsLocationAddressRepository.findById(adresid).get().getCityid();
				empcontactadress.setAdresscityid(cityid);
				empcontactadress.setAdresscity(hrmsLocationCityRepository.findById(cityid).get().getName());
				if (empadres.getDate_end() != null) {
					empcontactadress.setAdressdateend(empadres.getDate_end());
				}

				if (empadres.getDate_start() != null) {
					empcontactadress.setAdressdatestart(empadres.getDate_start());
				}

				empcontactadress
						.setAdressdescription(hrmsLocationAddressRepository.findById(adresid).get().getDescription());
				empcontactadress.setAdressdescriptionend(empadres.getDescription_end());
				empcontactadress.setAdressdescriptionstart(empadres.getDescription_start());
				empcontactadress.setEmployeeid(empadres.getEmployeeid());
				empcontactadress.setEmpadressid(empadres.getId());
				empcontactadress.setPostalcode(hrmsLocationAddressRepository.findById(adresid).get().getPostalcode());

				return ResponseEntity.status(HttpStatus.OK).body(empcontactadress);

			} else {
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
			}
		}
	}

	public boolean DoubleAddressverifier(int empid, int addressType) {
		boolean found = false;
		List<HrmsEmployeeAddress> empadresslist = hrmsEmployeeAddressRepository.findByEmployeeidAndActive(empid, 1);
		int i = 0;
		while (i < empadresslist.size()) {
			if (hrmsLocationAddressRepository.findById(empadresslist.get(i).getAddressid()).get()
					.getAddresstypeid() == addressType) {
				found = true;
				i = empadresslist.size();
			} else {
				i++;
			}

		}

		return found;

	}

	@Override
	public ResponseEntity<List<HrmsEmployeeAddressContactResponse>> listEmployeeAddressNonApproved() {
		List<HrmsEmployeeAddressContactResponse> empadresscontactlist = new ArrayList<>();
		List<HrmsEmployeeAddress> empadres = hrmsEmployeeAddressRepository.findByActiveAndApprovedOrderByEmployeeid(1,
				0);

		List<HrmsEmployeeContact> empcontact = hrmsEmployeeContactRepository.findByActiveAndApprovedOrderByEmployeeid(1,
				0);

		for (int i = 0; i < empadres.size(); i++) {
			HrmsEmployeeAddressContactResponse empcontactadress = new HrmsEmployeeAddressContactResponse();
			empcontactadress.setActive(empadres.get(i).getActive());
			empcontactadress.setApproved(empadres.get(i).getApproved());
			int adresid = empadres.get(i).getAddressid();
			empcontactadress.setAdressid(adresid);

			empcontactadress.setEmployeeid(empadres.get(i).getEmployeeid());

			HrmsEmployee hrmsEmployee = hrmsEmployeeRepository.findById(empadres.get(i).getEmployeeid()).get();

			StringBuilder fullname = new StringBuilder();
			fullname.append(hrmsEmployee.getFirstName().trim());
			fullname.append("  " + hrmsEmployee.getMiddleName().trim());
			fullname.append(" " + hrmsEmployee.getLastName().trim());

			empcontactadress.setApprovalComment(empadres.get(i).getApprovalComment());

			empcontactadress.setEmployeename(fullname.toString());

			empcontactadress.setAddressline1(hrmsLocationAddressRepository.findById(adresid).get().getAddressline1());

			empcontactadress.setAddressline2(hrmsLocationAddressRepository.findById(adresid).get().getAddressline2());

			int adrestypeid = hrmsLocationAddressRepository.findById(adresid).get().getAddresstypeid();

			empcontactadress.setAddresstypeid(adrestypeid);

			empcontactadress
					.setAddresstypename(hrmsLocationAddressTypeRepository.findById(adrestypeid).get().getName());
			int cityid = hrmsLocationAddressRepository.findById(adresid).get().getCityid();
			empcontactadress.setAdresscityid(cityid);
			empcontactadress.setAdresscity(hrmsLocationCityRepository.findById(cityid).get().getName());
			if (empadres.get(i).getDate_end() != null) {
				empcontactadress.setAdressdateend(empadres.get(i).getDate_end());
			}

			if (empadres.get(i).getDate_start() != null) {
				empcontactadress.setAdressdatestart(empadres.get(i).getDate_start());
			}

			empcontactadress
					.setAdressdescription(hrmsLocationAddressRepository.findById(adresid).get().getDescription());
			empcontactadress.setAdressdescriptionend(empadres.get(i).getDescription_end());
			empcontactadress.setAdressdescriptionstart(empadres.get(i).getDescription_start());
			empcontactadress.setEmployeeid(empadres.get(i).getEmployeeid());

			empcontactadress.setEmpadressid(empadres.get(i).getId());
			if (i < empcontact.size()) {
				empcontactadress.setEmpcontactid(empcontact.get(i).getId());
				if (empcontact.get(i).getDateend() != null) {
					empcontactadress.setContactdateend(empcontact.get(i).getDateend());
				}

				if (empcontact.get(i).getDatestart() != null) {
					empcontactadress.setContactdatestart(empcontact.get(i).getDatestart());
				}

				empcontactadress.setContactdescriptionend(empcontact.get(i).getDescriptionend());
				empcontactadress.setContactdescriptionstart(empcontact.get(i).getDescriptionstart());
				int contactid = empcontact.get(i).getContactid();
				empcontactadress.setContactid(contactid);

				empcontactadress
						.setContactemailaddress(hrmsContactRepository.findById(contactid).get().getEmailaddress());
				empcontactadress.setContactid(contactid);
				empcontactadress
						.setContactphoneprimary(hrmsContactRepository.findById(contactid).get().getPhoneprimary());
				empcontactadress
						.setContactphonesecondary(hrmsContactRepository.findById(contactid).get().getPhonesecondary());
				// int contypeid =
				// hrmsContactRepository.findById(contactid).get().getContacttypeid();
				// empcontactadress.setContacttypeid(contypeid);
				// empcontactadress.setContacttypename(hrmsContactTypeRepository.findById(contypeid).get().getName());
			}
			empcontactadress.setPostalcode(hrmsLocationAddressRepository.findById(adresid).get().getPostalcode());
			empadresscontactlist.add(empcontactadress);

		}

		return ResponseEntity.status(HttpStatus.OK).body(empadresscontactlist);
	}

	@Override
	public ResponseEntity<?> approvedOrRejectEmployeeAddress(EmployeeApprovalComment employeeApprovalComment, int aid,
			int cid, int status) {
		if ((hrmsLocationAddressRepository.existsById(aid) && hrmsContactRepository.existsById(cid))
				&& (status == 1 || status == -1)) {

			// approve address
			HrmsLocationAddress hrmsLocationAddress = hrmsLocationAddressRepository.findById(aid).get();
			hrmsLocationAddress.setApproved(status);
			hrmsLocationAddress.setDate_updated(LocalDateTime.now());
			hrmsLocationAddressRepository.saveAndFlush(hrmsLocationAddress);

			// approve contact
			HrmsContact hrmsContact = hrmsContactRepository.findById(cid).get();
			hrmsContact.setApproved(status);
			hrmsContact.setDate_updated(LocalDateTime.now());
			hrmsContactRepository.saveAndFlush(hrmsContact);

			// approve employee address
			HrmsEmployeeAddress hrmsEmployeeAddress = hrmsEmployeeAddressRepository.findByAddressid(aid);
			hrmsEmployeeAddress.setApproved(status);
			hrmsEmployeeAddress.setApprovalComment(employeeApprovalComment.getComment());
			hrmsEmployeeAddress.setApproverEmployeeid(employeeApprovalComment.getApproverEmployeeid());
			hrmsEmployeeAddress.setDate_updated(LocalDateTime.now());
			hrmsEmployeeAddressRepository.saveAndFlush(hrmsEmployeeAddress);

			// approve employee contact
			HrmsEmployeeContact hrmsEmployeeContact = hrmsEmployeeContactRepository.findByContactid(cid);
			hrmsEmployeeContact.setApprovalComment(employeeApprovalComment.getComment());
			hrmsEmployeeContact.setApproved(status);
			hrmsEmployeeContact.setApproverEmployeeid(employeeApprovalComment.getApproverEmployeeid());
			hrmsEmployeeContact.setDate_updated(LocalDateTime.now());
			hrmsEmployeeContactRepository.saveAndFlush(hrmsEmployeeContact);

			// send notification
			String messages = "";

			HrmsEmployee hrmsEmployee = hrmsEmployeeRepository.findById(hrmsEmployeeAddress.getEmployeeid()).get();

			StringBuilder fullname = new StringBuilder();
			fullname.append(hrmsEmployee.getFirstName().trim());
			fullname.append("  " + hrmsEmployee.getMiddleName().trim());
			fullname.append(" " + hrmsEmployee.getLastName().trim());
			String username = fullname.toString();
			String usermail = hrmsEmployee.getEmail();
			if (status == -1) {
				messages = " Your Address contact details entry   "
						+ " has been rejected,Kindly edit and re-submit again " + "  Comments for rejection is  "
						+ employeeApprovalComment.getComment();
				sendEmail.sendmailNotification(username, usermail, messages);
			}

			return ResponseEntity.status(HttpStatus.OK).body(null);
		} else {
			if ((hrmsLocationAddressRepository.existsById(aid) && cid == 0) && (status == 1 || status == -1)) {

				// approve adress
				HrmsLocationAddress hrmsLocationAddress = hrmsLocationAddressRepository.findById(aid).get();
				hrmsLocationAddress.setApproved(status);
				hrmsLocationAddress.setDate_updated(LocalDateTime.now());
				hrmsLocationAddressRepository.saveAndFlush(hrmsLocationAddress);

				// approve employee address
				HrmsEmployeeAddress hrmsEmployeeAddress = hrmsEmployeeAddressRepository.findByAddressid(aid);
				hrmsEmployeeAddress.setApproved(status);
				hrmsEmployeeAddress.setApprovalComment(employeeApprovalComment.getComment());
				hrmsEmployeeAddress.setApproverEmployeeid(employeeApprovalComment.getApproverEmployeeid());
				hrmsEmployeeAddress.setDate_updated(LocalDateTime.now());
				hrmsEmployeeAddressRepository.saveAndFlush(hrmsEmployeeAddress);

				// send notification
				String messages = "";

				HrmsEmployee hrmsEmployee = hrmsEmployeeRepository.findById(hrmsEmployeeAddress.getEmployeeid()).get();

				StringBuilder fullname = new StringBuilder();
				fullname.append(hrmsEmployee.getFirstName().trim());
				fullname.append("  " + hrmsEmployee.getMiddleName().trim());
				fullname.append(" " + hrmsEmployee.getLastName().trim());
				String username = fullname.toString();
				String usermail = hrmsEmployee.getEmail();
				if (status == -1) {
					messages = " Your Address contact details entry   "
							+ " has been rejected,Kindly edit and re-submit again " + "  Comments for rejection is  "
							+ employeeApprovalComment.getComment();
					sendEmail.sendmailNotification(username, usermail, messages);
				}

				return ResponseEntity.status(HttpStatus.OK).body(null);

			} else {
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
			}

		}
	}

}
