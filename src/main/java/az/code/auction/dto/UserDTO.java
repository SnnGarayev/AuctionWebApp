package az.code.auction.dto;

import az.code.auction.entity.AuctRacer;
import az.code.auction.entity.Auction;

import java.util.List;

public class UserDTO {
    Long id;
    String role;
    String name;
    String phoneNumber;
    String email;
    List<Auction> myAuctions;
    List<AuctRacer> myRacing;
}
