package com.papilion.checkmate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class GuestMemberService {

    @Autowired
    private GuestMemberRepository guestMemberRepository;

    @Autowired
    private AttendeeRepository attendeeRepository; // Repository for the table you want to insert into

    // public List<GuestMemberDTO> getGuestMemberData() {
    // return guestMemberRepository.findGuestMemberData();
    // }

    public List<Attendee> processAndInsertData(Event eventId) {
        // Step 1: Fetch GuestMemberDTO data
        List<GuestMemberDTO> guestMemberList = guestMemberRepository.findGuestMemberData(eventId);

        // Step 2: Convert GuestMemberDTOs to Attendees
        List<Attendee> attendeeList = guestMemberList.stream()
                .map(dto -> new Attendee(
                        0,
                        eventId,
                        dto.getGuestId(), // Map fields appropriately
                        dto.getMemberId(), // Assuming you have a way to map memberId
                        dto.getGuestFirstName(), // Map fields as per Attendee attributes
                        dto.getMemberFirstName(), // Same here
                        dto.getGuestEmail(), // Same here
                        dto.getMemberEmail(), // Same here
                        dto.getGuestPaymentMethod(), // Same here
                        dto.getMemberLevel(), // Same here
                        dto.getMemberUsername(), // Same here
                        dto.getMemberJoinedDate(), // Same here
                        dto.getMemberExpireDate(), // Same here,
                        false,
                        false))
                .filter(attendee -> !attendeeRepository.findByGuestIdAndMemberIdAndGuestEmail(
                        attendee.getGuestId(),
                        attendee.getMemberId(),
                        attendee.getGuestEmail()).isPresent() // Only insert if attendee doesn't exist
                )
                .distinct() // Removes duplicates based on equals() and hashCode()
                .collect(Collectors.toList());

        // Step 3: If attendeeList is empty, fetch from the attendee table
        if (attendeeList.isEmpty()) {
            // Search the Attendee table for existing records for the event
            attendeeList = attendeeRepository.findByEventId(eventId);
        } else {
            // Step 4: Save new Attendees to the repository
            attendeeRepository.saveAll(attendeeList);
        }

        return attendeeList;
    }
}
