package com.papilion.checkmate;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin
@RequestMapping(path="/api/v1/memberlookup")
public class MemberController {

    @Autowired
    private final MemberService memberService;

    @Autowired
    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }

    @GetMapping("/all")
	public List<Member> getAllMembers(){
        return memberService.searchAll();
	}

    @GetMapping("/searchByName/{membername}")
	public List<Member> getMemberByName(@PathVariable("membername") String name){
        return memberService.searchByName(name);
	}
}
