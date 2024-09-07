package com.papilion.checkmate;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import jakarta.persistence.*;

@Entity
@Table(name="Events")
public class Event {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    protected int id;
    protected String Title;
    protected Date creationDate = new Date();
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = JsonFormat.DEFAULT_LOCALE)
    protected Date eventDate;
    
    protected String coverImage;
    protected String MembershipFile;
    protected String GuestListFile;

    public Event() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public Date getEventDate() {
        return eventDate;
    }

    public void setEventDate(Date eventDate) {
        this.eventDate = eventDate;
    }

    public String getCoverImage() {
        return coverImage;
    }
    
    public void setCoverImage(String coverImage) {
        this.coverImage = coverImage;
    }

    public String getMembershipFile() {
        return MembershipFile;
    }

    public void setMembershipFile(String membershipFile) {
        MembershipFile = membershipFile;
    }

    public String getGuestListFile() {
        return GuestListFile;
    }

    public void setGuestListFile(String guestListFile) {
        GuestListFile = guestListFile;
    }

    @Override
    public String toString() {
        return "Event [id=" + id + ", Title=" + Title + ", creationDate=" + creationDate + ", eventDate=" + eventDate
                + ", coverImage=" + coverImage + ", MembershipFile=" + MembershipFile + ", GuestListFile="
                + GuestListFile + "]";
    }
}
