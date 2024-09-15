package com.papilion.checkmate;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EventService {

    private final EventRepository eventRepository;
    private String UPLOAD_DIR = "uploads/";


    @Autowired
    public EventService(EventRepository eventRepository) {
        this.eventRepository = eventRepository;
    }

    public Event addNewEvent(Event event) {
        //Create a file Object for Directory
        File directoryPath = new File(UPLOAD_DIR +"\\" + event.getTitle()); 
        //List all files and directories
        String contents[] = directoryPath.list();
        // ArrayList<String> filePaths = new ArrayList();
        for(int i=0; i<contents.length; i++) {
            // filePaths.add(directoryPath+"\\"+ contents[i]);
            System.out.println("HERE--------->" + contents[i]);
            if(contents[i].indexOf("A_") > -1){
                event.setGuestListFile(directoryPath+"\\"+ contents[i]);
            }else if(contents[i].indexOf("B_") > -1){
                event.setMembershipFile(directoryPath+"\\"+ contents[i]);
            }else{
                event.setCoverImage(directoryPath+"\\"+ contents[i]);
            }
        }
		   eventRepository.save(event);
           return event;
    }

    public Event getEventById(int eventId) {
        // Use the eventRepository to find the Event by its ID
        Optional<Event> eventOptional = eventRepository.findById((long) eventId);
        
        // If the event is found, return it; otherwise, throw an exception
        if (eventOptional.isPresent()) {
            return eventOptional.get();
        } else {
            throw new RuntimeException("Event with ID " + eventId + " not found.");
        }
    }

    public ArrayList<Event> getAllEvents(){
        return eventRepository.findAll();
    }

}
