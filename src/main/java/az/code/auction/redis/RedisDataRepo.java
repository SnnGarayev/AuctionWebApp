package az.code.auction.redis;

import az.code.auction.entity.AuctRacer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface RedisDataRepo extends CrudRepository<RedisEntity,Long> {
    Page<RedisEntity> findAll(Pageable pageable);
//    List<RedisEntity> getRedisEntitiesByAuction
}
