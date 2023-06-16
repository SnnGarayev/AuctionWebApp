package az.code.auction.service;

import az.code.auction.entity.AuctRacer;
import az.code.auction.entity.Auction;
import az.code.auction.repo.IAuctRacerRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuctRacerService {
    private final IAuctRacerRepo myRepo;
    void save(AuctRacer auctRacer)
    {
        myRepo.save(auctRacer);
    }
    void update(AuctRacer auctRacer)
    {
        myRepo.save(auctRacer);
    }
    void deleteById(Long id)
    {
        myRepo.deleteById(id);
    }
    void delete(AuctRacer auctRacer)
    {
        myRepo.delete(auctRacer);
    }
    Optional<AuctRacer> findById(Long id)
    {
        return myRepo.findById(id);
    }
    List<AuctRacer> findAll()
    {
        return myRepo.findAll();
    }
}

