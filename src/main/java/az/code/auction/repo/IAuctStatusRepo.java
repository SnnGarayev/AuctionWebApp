package az.code.auction.repo;

import az.code.auction.entity.AuctStatus;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IAuctStatusRepo extends JpaRepository<AuctStatus, Long> {
}
