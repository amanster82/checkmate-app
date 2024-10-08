package com.papilion.checkmate;

import java.time.LocalDate;
import java.util.Objects;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name="Attendee")
public class Attendee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Auto-generate the ID
    private int Id;
    
    @ManyToOne
    @JoinColumn(name = "event_id")
    private Event eventId;
    private Long guestId;
    private Integer memberId; // Boxed type to allow null values
    private Long guestTicketid;
    private String guestFirstName;
    private String memberFirstName;
    private String guestLastName;
    private String memberLastName;
    private String guestEmail;
    private String memberEmail;
    private String guestPaymentMethod;
    private String memberLevel;
    private String memberUsername; // Changed to camelCase
    private LocalDate memberJoinedDate; // Changed to camelCase
    private LocalDate memberExpireDate; // Changed to camelCase
    private boolean checkedInStatus;
    private boolean signedWaiver;
    private boolean bypassMembership;

    public Attendee(){}

    public Attendee(int id, Event eventId, Long guestId, Integer memberId, Long guestTicketid, String guestFirstName,
            String memberFirstName, String guestLastName, String memberLastName, String guestEmail, String memberEmail,
            String guestPaymentMethod, String memberLevel, String memberUsername, LocalDate memberJoinedDate,
            LocalDate memberExpireDate, boolean checkedInStatus, boolean signedWaiver, boolean bypassMembership) {
        Id = id;
        this.eventId = eventId;
        this.guestId = guestId;
        this.memberId = memberId;
        this.guestTicketid = guestTicketid;
        this.guestFirstName = guestFirstName;
        this.memberFirstName = memberFirstName;
        this.guestLastName = guestLastName;
        this.memberLastName = memberLastName;
        this.guestEmail = guestEmail;
        this.memberEmail = memberEmail;
        this.guestPaymentMethod = guestPaymentMethod;
        this.memberLevel = memberLevel;
        this.memberUsername = memberUsername;
        this.memberJoinedDate = memberJoinedDate;
        this.memberExpireDate = memberExpireDate;
        this.checkedInStatus = checkedInStatus;
        this.signedWaiver = signedWaiver;
        this.bypassMembership = bypassMembership;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Attendee attendee = (Attendee) o;
        return Objects.equals(guestId, attendee.guestId) &&
               Objects.equals(memberId, attendee.memberId) &&
               Objects.equals(guestEmail, attendee.guestEmail);  // Adjust the comparison fields as necessary
    }

    @Override
    public int hashCode() {
        return Objects.hash(guestId, memberId, guestEmail);  // Ensure hashCode matches the fields used in equals()
    }

    public Event getEventId() {
        return eventId;
    }

    public void setEventId(Event eventId) {
        this.eventId = eventId;
    }

    public void setMemberId(Integer memberId) {
        this.memberId = memberId;
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

    public Long getGuestTicketid() {
        return guestTicketid;
    }

    public void setGuestTicketid(Long guestTicketid) {
        this.guestTicketid = guestTicketid;
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

    public String getGuestLastName() {
        return guestLastName;
    }

    public void setGuestLastName(String guestLastName) {
        this.guestLastName = guestLastName;
    }

    public String getMemberLastName() {
        return memberLastName;
    }

    public void setMemberLastName(String memberLastName) {
        this.memberLastName = memberLastName;
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

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public boolean isCheckedInStatus() {
        return checkedInStatus;
    }

    public void setCheckedInStatus(boolean checkedInStatus) {
        this.checkedInStatus = checkedInStatus;
    }

    public boolean getSignedWaiver() {
        return signedWaiver;
    }

    public void setSignedWaiver(boolean signedWaiver) {
        this.signedWaiver = signedWaiver;
    }

    public void setMemberExpireDate(LocalDate memberExpireDate) {
        this.memberExpireDate = memberExpireDate;
    }

    public boolean isBypassMembership() {
        return bypassMembership;
    }

    public void setBypassMembership(boolean bypassMembership) {
        this.bypassMembership = bypassMembership;
    }

    @Override
    public String toString() {
        return "Attendee [Id=" + Id + ", guestId=" + guestId + ", memberId=" + memberId + ", guestFirstName="
                + guestFirstName + ", memberFirstName=" + memberFirstName + ", guestEmail=" + guestEmail
                + ", memberEmail=" + memberEmail + ", guestPaymentMethod=" + guestPaymentMethod + ", memberLevel="
                + memberLevel + ", memberUsername=" + memberUsername + ", memberJoinedDate=" + memberJoinedDate
                + ", memberExpireDate=" + memberExpireDate + ", checkedInStatus=" + checkedInStatus
                + ", signedWaiver=" + signedWaiver + "]";
    }
    
}
