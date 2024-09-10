package com.papilion.checkmate;

import java.time.LocalDate;

import org.springframework.cglib.core.Local;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

public class GuestMemberDTO {

    private Long guestId;
    private int memberId;
    private String guestFirstName;
    private String memberFirstName;
    private String guestEmail;
    private String memberEmail;
    private String guestPaymentMethod;
    private String memberLevel;
    private String member_username;
    private LocalDate member_joinedDate;
    private LocalDate member_expireDate;

    public GuestMemberDTO(Long guestId, int memberId, String guestFirstName, String memberFirstName, String guestEmail,
            String memberEmail, String guestPaymentMethod, String memberLevel, String member_username,
            LocalDate member_joinedDate, LocalDate member_expireDate) {
        this.guestId = guestId;
        this.memberId = memberId;
        this.guestFirstName = guestFirstName;
        this.memberFirstName = memberFirstName;
        this.guestEmail = guestEmail;
        this.memberEmail = memberEmail;
        this.guestPaymentMethod = guestPaymentMethod;
        this.memberLevel = memberLevel;
        this.member_username = member_username;
        this.member_joinedDate = member_joinedDate;
        this.member_expireDate = member_expireDate;
    }

    public Long getGuestId() {
        return guestId;
    }

    public void setGuestId(Long guestId) {
        this.guestId = guestId;
    }

    public int getMemberId() {
        return memberId;
    }

    public void setMemberId(int memberId) {
        this.memberId = memberId;
    }

    public String getGuestFirstName() {
        return guestFirstName;
    }

    public void setGuestFirstName(String guestFirstName) {
        this.guestFirstName = guestFirstName;
    }

    public String getMemberFirstName() {
        return memberFirstName;
    }

    public void setMemberFirstName(String memberFirstName) {
        this.memberFirstName = memberFirstName;
    }

    public String getGuestEmail() {
        return guestEmail;
    }

    public void setGuestEmail(String guestEmail) {
        this.guestEmail = guestEmail;
    }

    public String getMemberEmail() {
        return memberEmail;
    }

    public void setMemberEmail(String memberEmail) {
        this.memberEmail = memberEmail;
    }

    public String getGuestPaymentMethod() {
        return guestPaymentMethod;
    }

    public void setGuestPaymentMethod(String guestPaymentMethod) {
        this.guestPaymentMethod = guestPaymentMethod;
    }

    public String getMemberLevel() {
        return memberLevel;
    }

    public void setMemberLevel(String memberLevel) {
        this.memberLevel = memberLevel;
    }

    public String getMember_username() {
        return member_username;
    }

    public void setMember_username(String member_username) {
        this.member_username = member_username;
    }

    public LocalDate getMember_joinedDate() {
        return member_joinedDate;
    }

    public void setMember_joinedDate(LocalDate member_joinedDate) {
        this.member_joinedDate = member_joinedDate;
    }

    public LocalDate getMember_expireDate() {
        return member_expireDate;
    }

    public void setMember_expireDate(LocalDate member_expireDate) {
        this.member_expireDate = member_expireDate;
    }

}
