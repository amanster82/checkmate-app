package com.papilion.checkmate;

import java.util.ArrayList;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GuestRepository extends JpaRepository<Guest, Long>{

    //Return every guest    
    ArrayList<Guest> findAll();
    
}