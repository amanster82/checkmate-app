package com.papilion.checkmate;

import java.io.FileNotFoundException;
import java.util.Date;
import java.util.List;

import org.apache.tomcat.util.http.parser.MediaType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin
@RequestMapping(path="api/v1/events")
public class EventController {

    private final EventService eventService;
    private final GuestService guestService;
    private final MemberService memberService;

    @Autowired
    public EventController(EventService eventService, GuestService guestService, MemberService memberService){
        this.eventService = eventService;
        this.guestService = guestService;
        this.memberService = memberService;
    }

    
    @PostMapping
    public void registerNewEvent(@RequestBody Event event){ 
        Event myEvent = eventService.addNewEvent(event);
        try {
            guestService.readFile(myEvent);
            memberService.readFile();
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }


    @GetMapping("/getAllEvents")
	public List<Event> getEvents(){
        return eventService.getAllEvents();
	}

    @PutMapping("/updateEvent")
    public Event updateEvent(@RequestBody Event event){
        System.out.println("THIS IS MY EVENT"+ event.toString());
        // return null;
        Event myEvent = eventService.updateEvent(event);
        // try {
        //     guestService.readFile(myEvent);
        //     memberService.readFile();
        // } catch (FileNotFoundException e) {
        //     // TODO Auto-generated catch block
        //     e.printStackTrace();
        // }
        return myEvent;
    }
    

}
