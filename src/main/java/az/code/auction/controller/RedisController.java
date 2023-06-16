package az.code.auction.controller;

import az.code.auction.entity.Auction;
import az.code.auction.redis.RedisEntity;
import az.code.auction.redis.RedisService;
import az.code.auction.repo.IAuctRacerRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/redis")
@RequiredArgsConstructor
public class RedisController {

    private final RedisService service;
    private final IAuctRacerRepo iAuctRacerRepo;

    @GetMapping
    public List<RedisEntity> getAll(){
        return service.getAll();
    }

    @PostMapping
    public RedisEntity save(@RequestBody Auction auction){

        return service.save(RedisEntity.builder().id(auction.getId()).auction(auction).build());
    }
    @GetMapping("/{id}")
    public RedisEntity findbyId(@PathVariable Long id){
        return service.findById(id).get();

    }

    @DeleteMapping("/{auctionId}")
    public String deleteRacer(@PathVariable Long auctionId){
        service.remove(auctionId);
        return "Deleted";
    }

    @DeleteMapping
    public String deleteAll(){
        service.clearCache();
        return "Cleared Cache";
    }

}
