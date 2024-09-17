package com.papilion.checkmate;

import java.util.ArrayList;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EventRepository extends JpaRepository<Event, Long>{

    //Return every event    
    @SuppressWarnings("null")
    ArrayList<Event> findAll();
    
}