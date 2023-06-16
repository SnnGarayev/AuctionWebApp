package az.code.auction.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id", nullable = false)
    Long id;
    @Column(name = "roles", nullable = false)
    String role;
    @Column(name = "name", nullable = false)
    String name;
    @Column(name = "password", nullable = false)
    String password;
    @Column(name = "phoneNumber", nullable = false)
    String phoneNumber;
    @Column(name = "eMail", nullable = false)
    String email;
//    @JsonIgnore
    @OneToMany(fetch = FetchType.EAGER)
    List<Auction> myAuctions;

    @OneToMany(fetch = FetchType.EAGER)
    List<AuctRacer> myRacing;

    @OneToMany(fetch = FetchType.EAGER,cascade = CascadeType.ALL,mappedBy = "user")
    List<Data> myDatas = new ArrayList<>(1);

    @Column(name = "ignoreCode")
    Long ignoreCode;

}
