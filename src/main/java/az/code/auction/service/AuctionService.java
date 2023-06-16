package az.code.auction.service;

import az.code.auction.entity.AuctRacer;
import az.code.auction.entity.Auction;
import az.code.auction.entity.User;
import az.code.auction.repo.IAuctionRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuctionService {

    private final IAuctionRepo auctionRepo;

    public Auction save(Auction auction)
    {
         return auctionRepo.save(auction);
    }
    public void update(Auction auction)
    {
        auctionRepo.save(auction);
    }
    public void deleteById(Long id)
    {
        auctionRepo.deleteById(id);
    }
    public void delete(Auction auction)
    {
        auctionRepo.delete(auction);
    }
    public Optional<Auction> findById(Long id)
    {
        return auctionRepo.findById(id);
    }
    public List<Auction> findAll()
    {
        List<Auction> actlist =  auctionRepo.findAll();
        for(int i=0;i<actlist.size();i++)
        {
            if(!actlist.get(i).getIsActive())actlist.remove(i);
        }
        return actlist;
    }

    public List<Long> searchTitle(int page,int size , String search){
        return auctionRepo.search(PageRequest.of(page,size),search);

    }
    public Integer searchTitle( String search){
        return auctionRepo.searchLength(search).size();
    }
    public List<Long> getCategory(int page, int size , Long categoryId){
        return auctionRepo.getCategory(PageRequest.of(page, size),categoryId);
    }
    public Integer getCategory( Long categoryId){
        return auctionRepo.getCategoryLength(categoryId).size();
    }
    public List<Long> getAuctioneerAuctions(Long userId, int page, int size){
        return auctionRepo.getAuctioneerAuctions(PageRequest.of(page, size),userId);
    }
    public Integer getAuctioneerAuctions(Long userId){
        return auctionRepo.getAuctioneerAuctionsLength(userId).size();
    }

}

