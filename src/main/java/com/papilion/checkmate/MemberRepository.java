package com.papilion.checkmate;

import java.util.ArrayList;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long>{

    //Return every Member    
    ArrayList<Member> findAll();
    
}