package az.code.auction.entity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "categories")
public class Categoriy {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    Long id;
//    @JsonIgnore
//    @ToString.Exclude
//    @OneToOne(mappedBy = "myCategoriy")
//    Auction auction;

    @Column(name = "categoriName", nullable = false)
    String categoriyName;
}
