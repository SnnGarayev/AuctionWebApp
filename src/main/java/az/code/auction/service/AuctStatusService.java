package az.code.auction.service;

import az.code.auction.entity.AuctStatus;
import az.code.auction.entity.Auction;
import az.code.auction.repo.IAuctStatusRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuctStatusService {
    private final IAuctStatusRepo myRepo;
    public AuctStatus save(AuctStatus auctStatus)
    {
        return myRepo.save(auctStatus);
    }
    void update(AuctStatus auctStatus)
    {
        myRepo.save(auctStatus);
    }
    void deleteById(Long id)
    {
        myRepo.deleteById(id);
    }
    void delete(AuctStatus auctStatus)
    {
        myRepo.delete(auctStatus);
    }
    Optional<AuctStatus> findById(Long id)
    {
        return myRepo.findById(id);
    }
    List<AuctStatus> findAll()
    {
        return myRepo.findAll();
    }
}

