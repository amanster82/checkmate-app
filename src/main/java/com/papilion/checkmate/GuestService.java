package com.papilion.checkmate;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;




@Service
public class GuestService {

    private final GuestRepository guestRepository;

    @Autowired
    public GuestService(GuestRepository guestRepository) {
        this.guestRepository = guestRepository;
    }

    // @PostConstruct
    // public void init() {
    //     try {
    //         readFile();
    //     } catch (FileNotFoundException e) {
    //         System.err.println("Error reading the CSV file: " + e.getMessage());
    //         e.printStackTrace();
    //     }
    // }
  
    public void readFile(Event event) throws FileNotFoundException {
      System.out.println("WHAT IS MY EVENT: " + event);
      
      // Step 1: Delete existing guests for the event
      guestRepository.deleteByEventId(event);
  
      // Step 2: Read the file and add new guests
      File myObj = new File("./" + event.getGuestListFile());  // Adjusted the getter
      Scanner myReader = new Scanner(myObj);
      boolean firstLine = true;
      String[] dd;
      String id;
  
      while (myReader.hasNextLine()) {
          String data = myReader.nextLine();
          Guest myGuest = new Guest();
  
          if (firstLine) {
              firstLine = false;
              continue;  // Skip header line
          }
  
          dd = data.split(",");
          id = dd[1].replace("\"", "").trim();  // Remove quotes and trim whitespace
          System.out.println("MY ID IS " + id.replaceAll("\\D", "") + "L");
          
          myGuest.setEventId(event);  // Set event reference for Guest
          myGuest.setTicketId(Long.parseLong(id));
          myGuest.setPaymentMethod(dd[14].replace("\"", ""));
          myGuest.setFirstName(dd[15].replace("\"", ""));
          myGuest.setLastName(dd[16].replace("\"", ""));
          myGuest.setEmail(dd[17].replace("\"", ""));
  
          guestRepository.save(myGuest);  // Save new guest
          System.out.println(myGuest.toString());
      }
    myReader.close();
  }

}


