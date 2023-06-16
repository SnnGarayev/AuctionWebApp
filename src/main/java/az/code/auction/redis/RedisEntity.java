package az.code.auction.redis;

import az.code.auction.entity.Auction;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import java.io.Serializable;

@Builder()
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@RedisHash(value = "redisEntity")
public class RedisEntity implements Serializable {
    @Id
    private Long id;

    private Auction auction;

}
