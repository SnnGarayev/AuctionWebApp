package az.code.auction.redis;

import az.code.auction.entity.AuctRacer;
import az.code.auction.entity.Auction;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;


@Service
@RequiredArgsConstructor
public class RedisService {
    private final RedisDataRepo redisDataRepo;

    public List<RedisEntity> getAll(){
        return  StreamSupport.stream(redisDataRepo.findAll().spliterator(), false)
                .collect(Collectors.toList());
    }
    public RedisEntity save(RedisEntity redisEntity){
        return redisDataRepo.save(redisEntity);
    }
    public Optional<RedisEntity> findById(Long id){
        return  redisDataRepo.findById(id);
    }

    public RedisEntity remove(Long id){
        RedisEntity redisEntity = findById(id).get();
        redisDataRepo.delete(redisEntity);
        return redisEntity;
    }

    public void clearCache(){
        redisDataRepo.deleteAll();
    }

    public List<RedisEntity> findAll(Pageable pageable){
        return redisDataRepo.findAll(pageable).get().filter(item-> item.getAuction().getIsActive()).toList();
    }

    public Integer findLength(){
        return (int) StreamSupport.stream(redisDataRepo.findAll().spliterator(), false).count();
    }
    public List<Auction> getBidderAuctions(Long userId){
        List<Auction> auctions = new ArrayList<>();
        getAll().forEach(redis->{
            redis.getAuction().getMyStatus().getRacers().forEach(racer -> {
                if (racer.getRacer().getId().equals(userId)){
                    auctions.add(redis.getAuction());
                }
            });
        });
        return auctions;
    }

    public List<Auction> getTopAuctions(int size){
        List<Auction> auctions = new ArrayList<>();
        for (int i = 0; i < getAll().size() ; i++) {

        }
        return auctions;
    }
}
