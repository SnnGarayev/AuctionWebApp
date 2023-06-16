package az.code.auction.controller;

import az.code.auction.entity.Auction;
import az.code.auction.redis.RedisEntity;
import az.code.auction.redis.RedisService;
import az.code.auction.service.AuctionService;
import az.code.auction.service.MailService;
import az.code.auction.service.MainService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
@CrossOrigin
public class MainController {
    private final AuctionService auctionService;
    private final MainService mainService;
    private final RedisService redisService;

    @PostMapping("/auction/edit/{userId}/{auctionId}")
    public void editAuction(@PathVariable Long userId, @PathVariable Long auctionId, @RequestBody String newDescription) {
        Auction auct = redisService.findById(auctionId).get().getAuction();
        if (auct.getAuctioner().getId().equals(userId)) auct.setDescription(newDescription);
        redisService.save(RedisEntity.builder().auction(auct).id(auctionId).build());
    }

    @PostMapping("/auction/create/{userId}")
    public ResponseEntity<RedisEntity> saveAuction(@PathVariable Long userId, @RequestBody Auction newAuction) {
        return ResponseEntity.ok(mainService.createAuction(userId, newAuction));
    }

    @GetMapping("/{auctionId}")
    public ResponseEntity<?> findById(@PathVariable Long auctionId) {
        return redisService.findById(auctionId).map(redis-> ResponseEntity.ok(redis.getAuction()))
                .orElse(ResponseEntity.status(404).build());
    }

    @DeleteMapping("/auction/delete/{userId}/{auctionId}")
    public void deletedAuction(@PathVariable Long userId, @PathVariable Long auctionId) {

        if (auctionService.findById(auctionId).get().getAuctioner().getId().equals(userId)) {
            redisService.remove(auctionId);
            auctionService.deleteById(auctionId);
        }
    }

    @GetMapping("/auction/stop/{userId}/{auctionId}")
    public Auction stopAuction(@PathVariable Long auctionId, @PathVariable Long userId) {
        if (auctionService.findById(auctionId).get().getAuctioner().getId().equals(userId)) {
            return mainService.endAuction(auctionId);
        }else{
            return null;
        }
    }

    @GetMapping("/join/{userId}/{auctionId}")
    public ResponseEntity<Auction> joinAuction( @PathVariable Long userId,@PathVariable Long auctionId) {
        Auction auction = mainService.joinAuction(userId, auctionId);
        if (auction != null){
            return ResponseEntity.ok(auction);
        }else{
            return ResponseEntity.status(409).build();
        }
    }

    @GetMapping("/get-all")
    public List<RedisEntity> getAll(int page, int size) {
        return redisService.findAll(PageRequest.of(page, size));
    }

    @GetMapping("/search")
    public List<RedisEntity> searchAuction(int page, int size, String search) {
        List<RedisEntity> entities = new ArrayList<>();
        auctionService.searchTitle(page, size, search).stream().forEach(id -> {
            entities.add(redisService.findById(id).get());
        });
        return entities;
    }

    @GetMapping("/category/{categoryId}")
    public List<RedisEntity> filter(@PathVariable Long categoryId,@RequestParam int page, int size) {
        List<RedisEntity> entities = new ArrayList<>();
        auctionService.getCategory(page, size, categoryId).forEach(id -> {
            entities.add(redisService.findById(id).get());
        });
        return entities;
    }

    @GetMapping("/auction/active-auctions/{userId}")
    public List<Auction> getAuctioneerAuctions(@PathVariable Long userId,@RequestParam Integer page, Integer size) {
        List<Auction> entities = new ArrayList<>();
        auctionService.getAuctioneerAuctions(userId, page, size).forEach(id -> {
            entities.add(redisService.findById(id).get().getAuction());
        });
        return entities;
    }

    @GetMapping("/auction/active-auctions/{userId}/length")
    public Integer getAuctioneerAuctions(@PathVariable Long userId) {
        return auctionService.getAuctioneerAuctions(userId);
    }

    @GetMapping("/bidder/active-auctions/{userId}")
    public List<Auction> getBidderAuctions(@PathVariable Long userId) {
        return mainService.getBidderAuctions(userId);
    }

    @GetMapping("/get-all/length")
    public Integer getAll() {
        return redisService.findLength();
    }

    @GetMapping("/search/length")
    public Integer searchAuction( String search) {
        return auctionService.searchTitle( search);
    }

    @GetMapping("/category/{categoryId}/length")
    public Integer filter(@PathVariable Long categoryId) {
        return auctionService.getCategory(categoryId);
    }


}
