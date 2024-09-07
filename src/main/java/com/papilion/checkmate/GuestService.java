package com.papilion.checkmate;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.annotation.PostConstruct;



@Service
public class GuestService {

    private final GuestRepository guestRepository;

    @Autowired
    public GuestService(GuestRepository guestRepository) {
        this.guestRepository = guestRepository;
    }

    @PostConstruct
    public void init() {
        try {
            readFile();
        } catch (FileNotFoundException e) {
            System.err.println("Error reading the CSV file: " + e.getMessage());
            e.printStackTrace();
        }
    }
  

    public void readFile() throws FileNotFoundException{
        File myObj = new File("uploads/TESTF/orders-1.csv");
        Scanner myReader = new Scanner(myObj);
        Guest myGuest = new Guest();
        ArrayList<Guest> guestList = new ArrayList<>();
        Boolean firstLine = true;
        String [] dd;
        String id;

        while (myReader.hasNextLine()) {

          String data = myReader.nextLine();
          
          if(firstLine){
            firstLine = false;  
            continue;
          }

          dd = data.split(",");
          id = dd[1].replace("\"", "").trim();
          System.out.println("MY ID IS "+id+"L".replaceAll("\\D", ""));
          myGuest.setId(Long.parseLong(id));
          myGuest.setPaymentMethod(dd[14].replace("\"", ""));
          myGuest.setFirstName(dd[15].replace("\"", ""));
          myGuest.setLastName(dd[16].replace("\"", ""));
          myGuest.setEmail(dd[17].replace("\"", ""));
          
          guestRepository.save(myGuest);
        }
        myReader.close();
    }
}


