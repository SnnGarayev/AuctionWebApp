package az.code.auction.repo;

import az.code.auction.entity.AuctRacer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IAuctRacerRepo extends JpaRepository<AuctRacer, Long> {
}
