package az.code.auction.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Message {
    Long price;
    Long inTimePrice;
    Long auctionId;
    Long userId;
}
