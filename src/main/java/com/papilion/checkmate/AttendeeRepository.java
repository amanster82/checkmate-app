package com.papilion.checkmate;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

public interface AttendeeRepository extends JpaRepository<Attendee, Long> {
    Optional<Attendee> findByGuestIdAndMemberIdAndGuestEmail(Long guestId, Integer memberId, String guestEmail);
    
    List<Attendee> findByEventId(Event eventId);  // New method to search by Event ID
    
    Optional<Attendee> findAllAttendeesById(int Id);


}

