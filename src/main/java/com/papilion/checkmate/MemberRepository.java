package com.papilion.checkmate;

import java.util.ArrayList;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long>{

    //Return every Member    
    @SuppressWarnings("null")
    ArrayList<Member> findAll();
    

    @Query("SELECT m FROM Member m WHERE m.firstName LIKE %:pattern%")
    ArrayList<Member> findByFirstNamePattern(@Param("pattern") String name);

}