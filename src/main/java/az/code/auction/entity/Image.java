package az.code.auction.entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
//@Data
public class Image {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    @ManyToOne(fetch = FetchType.LAZY)
    Auction auction;
    String link;
}
