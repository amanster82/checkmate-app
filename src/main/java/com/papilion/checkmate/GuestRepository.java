package com.papilion.checkmate;

import java.util.ArrayList;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import jakarta.transaction.Transactional;

@Repository
public interface GuestRepository extends JpaRepository<Guest, Long>{

    //Return every guest    
    @SuppressWarnings("null")
    ArrayList<Guest> findAll();
    
    // Custom method to delete guests by event
    @Transactional
    void deleteByEventId(Event event);
    
}