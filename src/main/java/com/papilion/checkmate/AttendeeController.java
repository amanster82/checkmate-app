package com.papilion.checkmate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin
@RequestMapping(path="/api/v1/updateAttendee")
public class AttendeeController {

    @Autowired
    private final AttendeeService attendeeService;

    
    @Autowired
    public AttendeeController(AttendeeService attendeeService) {
        this.attendeeService = attendeeService;
    }



    @PutMapping
    public void updateAttendee(@RequestBody Attendee attendee){
        attendeeService.updateAttendee(attendee);
    }
}
