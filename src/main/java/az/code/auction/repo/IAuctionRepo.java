package az.code.auction.repo;

import az.code.auction.entity.AuctRacer;
import az.code.auction.entity.Auction;
import az.code.auction.entity.User;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface IAuctionRepo extends JpaRepository<Auction, Long>{
    @Query("select a.id from Auction a where a.isActive = true and lower(a.title) like %:search% ")
    List<Long> search(Pageable pageable , String search);

    @Query("select a.id from Auction a where a.isActive = true and a.title like %:search%")
    List<Long> searchLength( String search);
    @Query("select a.id from Auction a where a.isActive = true and a.myCategoriy = :categoryId order by random()")
    List<Long> getCategory(Pageable pageable, Long categoryId);

    @Query("select a.id from Auction a where a.isActive = true and a.myCategoriy = :categoryId")
    List<Long> getCategoryLength( Long categoryId);
    @Query("select a.id from Auction a where a.isActive = true and a.auctioner.id = :userId")
    List<Long> getAuctioneerAuctions(Pageable pageable,Long userId);
    @Query("select a.id from Auction a where a.isActive = true and a.auctioner.id = :userId")
    List<Long> getAuctioneerAuctionsLength(Long userId);

}
