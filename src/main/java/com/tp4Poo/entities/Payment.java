package com.tp4Poo.entities;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="payments")

public class Payment implements Serializable{


	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
 
  
    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="userId", referencedColumnName = "id") //"referencedColumnName" especifica otra columna como la columna de identificación predeterminada de la otra tabla
    private User owner;
    
    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="eventId", referencedColumnName = "id")
    private Event event;
    
    @Column
    private String cardName;
    
    @Column
    private String cardNumber;

    
    
    public Payment() {
    }

    public Payment(long id, User user, Event event, String cardName, String cardNumber) {
        this.id = id;
        this.event=event;
        this.owner=user;
        this.cardName = cardName;
        this.cardNumber = cardNumber;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }

    public Event getEvent() {
        return event;
    }

    public void setEvent(Event event) {
        this.event = event;
    }

    public String getCardName() {
        return cardName;
    }

    public void setCardName(String card) {
        this.cardName = card;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

	@Override
	public String toString() {
		return "Payment [id=" + id + ", owner=" + owner + ", event=" + event + ", cardName=" + cardName
				+ ", cardNumber=" + cardNumber + "]";
	}
    
    
        
}