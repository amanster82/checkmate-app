package com.papilion.checkmate;

import java.time.LocalDate;

public class GuestMemberDTO {
    private Long guestId;
    private Integer memberId;
    private String guestFirstName;
    private String memberFirstName;
    private String guestEmail;
    private String memberEmail;
    private String guestPaymentMethod;
    private String memberLevel;
    private String memberUsername; // Changed to camelCase
    private LocalDate memberJoinedDate; // Changed to camelCase
    private LocalDate memberExpireDate; // Changed to camelCase

    public GuestMemberDTO(Long guestId, Integer memberId, String guestFirstName, String memberFirstName, 
                          String guestEmail, String memberEmail, String guestPaymentMethod, 
                          String memberLevel, String memberUsername, LocalDate memberJoinedDate, 
                          LocalDate memberExpireDate) {
        this.guestId = guestId;
        this.memberId = memberId;
        this.guestFirstName = guestFirstName;
        this.memberFirstName = memberFirstName;
        this.guestEmail = guestEmail;
        this.memberEmail = memberEmail;
        this.guestPaymentMethod = guestPaymentMethod;
        this.memberLevel = memberLevel;
        this.memberUsername = memberUsername;
        this.memberJoinedDate = memberJoinedDate;
        this.memberExpireDate = memberExpireDate;
    }

    // Getters and Setters

    public Long getGuestId() {
        return guestId;
    }

    public void setGuestId(Long guestId) {
        this.guestId = guestId;
    }

    public Integer getMemberId() {
        int id = (memberId != null) ? memberId : 0;  // Default to 0 if memberId is null
        return id;
    }

    public void setMemberId(Integer memberId) {
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

    public String getMemberUsername() {
        return memberUsername;
    }

    public void setMemberUsername(String memberUsername) {
        this.memberUsername = memberUsername;
    }

    public LocalDate getMemberJoinedDate() {
        return memberJoinedDate;
    }

    public void setMemberJoinedDate(LocalDate memberJoinedDate) {
        this.memberJoinedDate = memberJoinedDate;
    }

    public LocalDate getMemberExpireDate() {
        return memberExpireDate;
    }

    public void setMemberExpireDate(LocalDate memberExpireDate) {
        this.memberExpireDate = memberExpireDate;
    }

    @Override
    public String toString() {
        return "GuestMemberDTO{" +
                "guestId=" + guestId +
                ", memberId=" + memberId +
                ", guestFirstName='" + guestFirstName + '\'' +
                ", memberFirstName='" + memberFirstName + '\'' +
                ", guestEmail='" + guestEmail + '\'' +
                ", memberEmail='" + memberEmail + '\'' +
                ", guestPaymentMethod='" + guestPaymentMethod + '\'' +
                ", memberLevel='" + memberLevel + '\'' +
                ", memberUsername='" + memberUsername + '\'' +
                ", memberJoinedDate=" + memberJoinedDate +
                ", memberExpireDate=" + memberExpireDate +
                '}';
    }
}
