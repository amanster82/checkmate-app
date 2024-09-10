package com.papilion.checkmate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GuestMemberService {

    @Autowired
    private GuestMemberRepository guestMemberRepository;

    public List<GuestMemberDTO> getGuestMemberData() {
        return guestMemberRepository.findGuestMemberData();
    }
}
