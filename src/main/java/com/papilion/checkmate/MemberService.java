package com.papilion.checkmate;

import java.io.File;
import java.io.FileNotFoundException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Scanner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import jakarta.annotation.PostConstruct;



@Service
public class MemberService {

    private final MemberRepository memberRepository;

    @Autowired
    public MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
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
        File myObj = new File("uploads/TESTF/members.csv");
        Scanner myReader = new Scanner(myObj);
        Member myMember = new Member();
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
          id = dd[0].replace("\"", "").trim();
          System.out.println("MY ID IS "+id+"L".replaceAll("\\D", ""));
          myMember.setId(Integer.parseInt(id));
          myMember.setUsername(dd[1].replace("\"", ""));
          myMember.setFirstName(dd[2].replace("\"", ""));
          myMember.setLastName(dd[3].replace("\"", ""));
          myMember.setEmail(dd[4].replace("\"", ""));
          myMember.setLevel(dd[5].replace("\"", ""));


          DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");


          String joinDate = dd[13].replace("\"", "");
          System.out.println("WHAT IS JOIN DATE"+ joinDate);
          LocalDate jDate = LocalDate.parse(joinDate, formatter);
          System.out.println("---->"+jDate); // 2010-01-02
          myMember.setJoinedDate(jDate);

          
          String expireDate = dd[14].replace("\"", "");
          if(expireDate.equals("N/A")){
            expireDate = "9999-01-01";
          }
          System.out.println("WHAT IS EXPIRE DATE"+ expireDate);
          LocalDate eDate = LocalDate.parse(expireDate, formatter);
          System.out.println("---->"+eDate); // 2010-01-02
          myMember.setExpireDate(eDate);

          memberRepository.save(myMember);
        }
        myReader.close();
    }
}


