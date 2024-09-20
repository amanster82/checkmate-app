package com.papilion.checkmate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;

@Service
public class AttendeeService {

    @Autowired
	private final AttendeeRepository attendeeRepository;

    @Autowired
	public AttendeeService(AttendeeRepository attendeeRepo) {
		this.attendeeRepository = attendeeRepo;
	}

    @Transactional
	public void updateAttendee(Attendee attendeeJSON) {
		
        Attendee attendeeLookup = attendeeRepository.findById((long) attendeeJSON.getId()).orElseThrow(()-> new IllegalStateException("Attendee with id "+ attendeeJSON + " does not exist"));

        System.out.println("FOUND! ===>" + attendeeLookup.toString());

		attendeeLookup.setGuestId(attendeeJSON.getGuestId());
		attendeeLookup.setMemberId(attendeeJSON.getMemberId()); // Integer to allow null
		attendeeLookup.setGuestTicketid(attendeeJSON.getGuestTicketid());
		attendeeLookup.setGuestFirstName(attendeeJSON.getGuestFirstName());
		attendeeLookup.setMemberFirstName(attendeeJSON.getMemberFirstName());
		attendeeLookup.setGuestLastName(attendeeJSON.getGuestLastName());
		attendeeLookup.setMemberLastName(attendeeJSON.getMemberLastName());
		attendeeLookup.setGuestEmail(attendeeJSON.getGuestEmail());
		attendeeLookup.setMemberEmail(attendeeJSON.getMemberEmail());
		attendeeLookup.setGuestPaymentMethod(attendeeJSON.getGuestPaymentMethod());
		attendeeLookup.setMemberLevel(attendeeJSON.getMemberLevel());
		attendeeLookup.setMemberUsername(attendeeJSON.getMemberUsername());
		attendeeLookup.setMemberJoinedDate(attendeeJSON.getMemberJoinedDate()); // Assuming LocalDate format is handled
		attendeeLookup.setMemberExpireDate(attendeeJSON.getMemberExpireDate()); // Assuming LocalDate format is handled
		attendeeLookup.setCheckedInStatus(attendeeJSON.isCheckedInStatus()); // boolean
		attendeeLookup.setSignedWaiver(attendeeJSON.getSignedWaiver()); // boolean
		attendeeLookup.setBypassMembership(attendeeJSON.isBypassMembership()); // boolean

		attendeeRepository.save(attendeeLookup);
	}
			
}

