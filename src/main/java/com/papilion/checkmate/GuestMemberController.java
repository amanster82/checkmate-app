package com.papilion.checkmate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping(path="/api/v1/guest-member-data")
public class GuestMemberController {

    @Autowired
    private GuestMemberService guestMemberService;
    @Autowired
    private EventService eventService;

    @GetMapping(path = "{eventId}")
    public List<Attendee> getGuestMemberData(@PathVariable("eventId") int eventId) {
        
        Event event = eventService.getEventById(eventId);

        return guestMemberService.processAndInsertData(event);
    }
}
