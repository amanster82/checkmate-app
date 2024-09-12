package com.papilion.checkmate;

import java.util.ArrayList;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AttendeeRepository extends JpaRepository<Attendee, Long> {

    // Example: You can query by email, guestId, or any other unique criteria
    Optional<Attendee> findByGuestIdAndMemberIdAndGuestEmail(Long guestId, Integer memberId, String guestEmail);
}
