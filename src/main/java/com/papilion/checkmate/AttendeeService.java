package com.papilion.checkmate;

import java.util.Objects;

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

		attendeeLookup.setGuestFirstName(attendeeJSON.getGuestFirstName());
	
		}
			
}

