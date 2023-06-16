package az.code.auction.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.scheduling.annotation.Scheduled;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "auctions")
public class Auction {

    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id")
    @Id
    Long id;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "auctionerId", referencedColumnName = "id")
    User auctioner;

    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "auction_status_Id", referencedColumnName = "id")
    AuctStatus myStatus;
    @Column(name = "startTime")
    Long endTime;

    Long myCategoriy;

    String title;
    String description;
    @OneToMany(mappedBy = "auction",fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    List<Image> images;

    @Column(name = "isActive")
    boolean isActive=true;

    public boolean getIsActive()
    {
        return isActive;
    }
    public void setIsActive(boolean type)
    {
        isActive = type;
    }

    public String getEndTime() {
        Long i = this.endTime - System.currentTimeMillis();
        Long on = 600000*6*24l;
        return (this.endTime- System.currentTimeMillis())/(1000*60*60) +
                ":" +((this.endTime- System.currentTimeMillis())%(1000*60*60))/(1000*60) +
                ":" + ((this.endTime - System.currentTimeMillis())/1000%60);
    }

    public Long getTime(){
        return this.endTime;
    }
}
