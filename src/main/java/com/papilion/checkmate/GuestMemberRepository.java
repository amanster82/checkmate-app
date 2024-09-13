package com.papilion.checkmate;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface GuestMemberRepository extends JpaRepository<Guest, Long> {

    // Optionally, you can still fetch data for display
    @Query("SELECT new com.papilion.checkmate.GuestMemberDTO(g.id, m.id, g.firstName, m.firstName, g.email, m.email, g.paymentMethod, m.level, m.username, m.joinedDate, m.expireDate) " +
           "FROM Guest g LEFT JOIN Member m ON g.email = m.email WHERE g.eventId = :eventId")
    List<GuestMemberDTO> findGuestMemberData(Event eventId);
}
