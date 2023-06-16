package az.code.auction.repo;

import az.code.auction.entity.Categoriy;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ICategoriyRepo extends JpaRepository<Categoriy, Long> {
}
