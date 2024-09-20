package com.papilion.checkmate;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;


@Entity
@Table(name="Guest")
public class Guest {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Auto-generate the ID
    protected long id;
    protected long ticketId;
    protected String paymentMethod;
    protected String firstName;
    protected String lastName;
    protected String email;

    @ManyToOne
    @JoinColumn(name = "event_id")
    private Event eventId;


    public Guest() {}

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getTicketId() {
        return ticketId;
    }

    public void setTicketId(long ticket_id) {
        this.ticketId = ticket_id;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }
    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getEventId() {
        return eventId.getId();
    }

    public void setEventId(Event event_id) {
        this.eventId = event_id;
    }

    @Override
    public String toString() {
        return "Guest [getId()=" + getId() + ", getPaymentMethod()=" + getPaymentMethod() + ", getFirstName()="
                + getFirstName() + ", getLastName()=" + getLastName() + ", getEmail()=" + getEmail() + ", getEventId()="
                + getEventId() + "]";
    }
    
}
