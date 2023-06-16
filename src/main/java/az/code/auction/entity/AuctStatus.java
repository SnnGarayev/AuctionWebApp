package az.code.auction.entity;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;


@Getter
@Setter
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "auctStatus")
public class AuctStatus {//Herracin izleyicileri ve qiymeti bu klassda olur

    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Id
    private Long id;

    Long price;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "AuctionId", referencedColumnName = "id")
    Auction myAuction;

    @Transient
    User winner;

    int winnerQue;
    @OneToMany(mappedBy= "myStatus",fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    List<AuctRacer> racers = new ArrayList<>();

    public void addRacer(AuctRacer newRacer)
    {
        racers.add(newRacer);
    }

}
