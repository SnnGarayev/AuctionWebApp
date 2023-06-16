package az.code.auction.repo;

import az.code.auction.entity.Image;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IImageRepo extends JpaRepository<Image,Long> {
}
