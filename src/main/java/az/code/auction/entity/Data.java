package az.code.auction.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;


@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "datas")
public class Data {
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Id
    private Long id;
    @Column(name = "ip")
    String name;//ip adress
    @Column(name = "type")
    String surname;//type

    public String getIp() {
        return name;
    }

    public void setIp(String name) {
        this.name = name;
    }

    public String getType() {
        return surname;
    }

    public void setType(String surname) {
        this.surname = surname;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @ManyToOne
    @JoinColumn(name = "userId",referencedColumnName = "id")
    User user;
}
