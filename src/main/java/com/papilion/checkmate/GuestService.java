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
  

    public void readFile(Event event) throws FileNotFoundException{
        System.out.println("WHAT IS MY EVENT"+ event);
        File myObj = new File("./"+event.GuestListFile);
        Scanner myReader = new Scanner(myObj);
        Boolean firstLine = true;
        String [] dd;
        String id;

        while (myReader.hasNextLine()) {

          String data = myReader.nextLine();
          Guest myGuest = new Guest();

          if(firstLine){
            firstLine = false;  
            continue;
          }

          dd = data.split(",");
          id = dd[1].replace("\"", "").trim(); //removeQuotes
          System.out.println("MY ID IS "+id+"L".replaceAll("\\D", ""));
          myGuest.setEventId(event);
          myGuest.setId(Long.parseLong(id));
          myGuest.setPaymentMethod(dd[14].replace("\"", ""));
          myGuest.setFirstName(dd[15].replace("\"", ""));
          myGuest.setLastName(dd[16].replace("\"", ""));
          myGuest.setEmail(dd[17].replace("\"", ""));
          
          guestRepository.save(myGuest);

          System.out.println(myGuest.toString());
        }
        myReader.close();
    }

}


