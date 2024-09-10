package com.papilion.checkmate;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GuestMemberRepository extends JpaRepository<Guest, Long> {

    @Query("SELECT new com.papilion.checkmate.GuestMemberDTO(g.id, m.id, g.firstName, m.firstName, g.email, m.email, g.paymentMethod, m.level, m.username, m.joinedDate, m.expireDate) " +
           "FROM Guest g JOIN Member m ON g.email = m.email")
    List<GuestMemberDTO> findGuestMemberData();
}
