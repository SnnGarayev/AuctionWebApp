package az.code.auction.dto;

import az.code.auction.entity.AuctStatus;
import az.code.auction.entity.Categoriy;
import az.code.auction.entity.User;

import java.time.LocalDate;

public class AuctionDTO {
    private Long id;
    private User auctioner;
    private AuctStatus myStatus;
    private LocalDate startTime;
    private Categoriy myCategoriy;
}

