package com.papilion.checkmate;

import java.io.FileNotFoundException;
import java.util.Date;

import org.apache.tomcat.util.http.parser.MediaType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin
@RequestMapping(path="api/v1/event")
public class EventController {

    private final EventService eventService;
    private final GuestService guestService;

    @Autowired
    public EventController(EventService eventService, GuestService guestService){
        this.eventService = eventService;
        this.guestService = guestService;
    }

    
    @PostMapping
    public void registerNewEvent(@RequestBody Event event){ 
        Event myEvent = eventService.addNewEvent(event);
        try {
            guestService.readFile(myEvent);
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        

    }
    

}
