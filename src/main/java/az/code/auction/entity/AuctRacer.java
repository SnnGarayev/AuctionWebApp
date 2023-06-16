package az.code.auction.entity;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "auctRacers")
public class AuctRacer {//Her user auctiona qatildiqda hemin user ucun hemn auctionda bu obyekt yaradilir
    @Id
    private Long id;

    Long total = 0L;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "statusId", referencedColumnName = "id")
    AuctStatus myStatus;

    int winQueue;
    @ManyToOne(fetch = FetchType.LAZY,cascade = CascadeType.ALL)
    @JoinColumn(name = "racerId", referencedColumnName = "id")
    User racer;

    private Long myPrice = 0L;
    public void setMyPrice(Long price,Long auctPrice)
    {
        total+=price-auctPrice;
        myPrice=price;
    }
}
