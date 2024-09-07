package com.papilion.checkmate;

import java.io.File;

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

    public void addNewEvent(Event event) {
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
    }


}
