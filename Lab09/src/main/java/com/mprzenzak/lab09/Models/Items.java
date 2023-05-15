package com.mprzenzak.lab09.Models;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;

import java.util.List;

//@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "items")
public class Items {
    private List<InfoCard> infoCards;

    @XmlElement(name = "karta_informacyjna")

    public List<InfoCard> getInfoCards() {
        return infoCards;
    }

    public void setInfoCards(List<InfoCard> infoCards) {
        this.infoCards = infoCards;
    }
}
