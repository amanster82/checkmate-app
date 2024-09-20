package com.papilion.checkmate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
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
        // Step 1: Fetch GuestMemberDTO data for the event
        List<GuestMemberDTO> guestMemberList = guestMemberRepository.findGuestMemberData(eventId);
        
        // Step 2: Fetch existing Attendees from the repository
        List<Attendee> existingAttendees = attendeeRepository.findByEventId(eventId);
        
        // Step 3: Convert GuestMemberDTOs to Attendees
        List<Attendee> newAttendees = guestMemberList.stream()
            .map(dto -> new Attendee(
                0,
                eventId,
                dto.getGuestId(),
                dto.getMemberId(),
                dto.getGuestTicketId(),
                dto.getGuestFirstName(),
                dto.getMemberFirstName(),
                dto.getGuestLastName(),
                dto.getMemberLastName(),
                dto.getGuestEmail(),
                dto.getMemberEmail(),
                dto.getGuestPaymentMethod(),
                dto.getMemberLevel(),
                dto.getMemberUsername(),
                dto.getMemberJoinedDate(),
                dto.getMemberExpireDate(),
                false,
                false,
                false
            ))
            .distinct() // Remove duplicates based on equals() and hashCode()
            .collect(Collectors.toList());
    
        // Step 4A: Delete attendees that are in the existing list but not in the new list (Approach A)
        Set<Long> newGuestIds = newAttendees.stream().map(Attendee::getGuestId).collect(Collectors.toSet());
        existingAttendees.stream()
            .filter(existing -> !newGuestIds.contains(existing.getGuestId()))  // Find attendees that don't exist in the new data
            .forEach(attendeeRepository::delete); // Delete attendees
    
        // // Step 4B: Update rows for matching guests (Approach B)
        // for (Attendee newAttendee : newAttendees) {
        //     attendeeRepository.findByGuestIdAndAndGuestEmail(
        //             newAttendee.getGuestId(),
        //             newAttendee.getGuestEmail()
        //     ).ifPresent(existingAttendee -> {
        //         // Update any missing fields in the existing record
        //         existingAttendee.setGuestTicketid(newAttendee.getGuestTicketid());
        //         existingAttendee.setGuestFirstName(newAttendee.getGuestFirstName());
        //         existingAttendee.setMemberFirstName(newAttendee.getMemberFirstName());
        //         existingAttendee.setGuestLastName(newAttendee.getGuestLastName());
        //         existingAttendee.setMemberLastName(newAttendee.getMemberLastName());
        //         existingAttendee.setGuestEmail(newAttendee.getGuestEmail());
        //         existingAttendee.setMemberEmail(newAttendee.getMemberEmail());
        //         existingAttendee.setGuestPaymentMethod(newAttendee.getGuestPaymentMethod());
        //         existingAttendee.setMemberLevel(newAttendee.getMemberLevel());
        //         existingAttendee.setMemberUsername(newAttendee.getMemberUsername());
        //         existingAttendee.setMemberJoinedDate(newAttendee.getMemberJoinedDate());
        //         existingAttendee.setMemberExpireDate(newAttendee.getMemberExpireDate());
        //         existingAttendee.setCheckedInStatus(newAttendee.isCheckedInStatus());
        //         existingAttendee.setSignedWaiver(newAttendee.getSignedWaiver());
        //         existingAttendee.setBypassMembership(newAttendee.isBypassMembership());

        //         // Add more fields if needed
        //         attendeeRepository.save(existingAttendee); // Save updated attendee
        //     });
        // }
    
        // Step 5: Save new Attendees (only those that don't exist already)
        List<Attendee> attendeesToInsert = newAttendees.stream()
            .filter(newAttendee -> !attendeeRepository.findByGuestIdAndAndGuestEmail(
                newAttendee.getGuestId(),
                newAttendee.getGuestEmail()
            ).isPresent())
            .collect(Collectors.toList());
    
        if (!attendeesToInsert.isEmpty()) {
            attendeeRepository.saveAll(attendeesToInsert); // Insert new attendees
        }
    
        // Step 6: Return the combined list of updated and newly inserted attendees
        return attendeeRepository.findByEventId(eventId); // Return the final list of attendees
    }
}
