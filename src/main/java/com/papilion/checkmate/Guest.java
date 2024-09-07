package com.papilion.checkmate;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;


@Entity
@Table(name="Guest")
public class Guest {
    @Id
    protected long id;
    protected String paymentMethod;
    protected String firstName;
    protected String lastName;
    protected String email;

    public Guest() {}

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
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

    @Override
    public String toString() {
        return "Guest [id=" + id + ", paymentMethod=" + paymentMethod + ", firstName=" + firstName + ", lastName="
                + lastName + ", email=" + email + "]";
    }
    
}
