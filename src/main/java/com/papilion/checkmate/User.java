package com.papilion.checkmate;

import java.util.Date;


// @Entity
// @Table(name="User")
public class User {
    // @Id
    protected int id;
    protected int membershipId;
    protected String FirstName;
    protected String LastName;
    protected String userName;
    protected String membershipType;
    protected String email;
    protected Date joinDate;
    protected Boolean verified;
    protected String guestFirstName;
    protected String guestLastName;
    protected String guestEmail;
    protected Date guestPurchaseDate;
    protected String paymentMethod;    
}
