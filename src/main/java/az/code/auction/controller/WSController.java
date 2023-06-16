package az.code.auction.controller;

import az.code.auction.entity.Auction;
import az.code.auction.entity.Message;
import az.code.auction.redis.RedisService;
import az.code.auction.service.MainService;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.*;

@RestController
//@RequestMapping("/ws")
@RequiredArgsConstructor
@CrossOrigin
public class WSController {
    private final SimpMessagingTemplate simpMessagingTemplate;
    private final MainService mainService;
    private final RedisService redisService;
    @MessageMapping("/private-message")
    public void chatEndPoint(@RequestBody Message message){
        Auction auction = mainService.addPrice(message.getUserId(), message.getAuctionId(), message.getPrice(), message.getInTimePrice());
        simpMessagingTemplate.convertAndSendToUser(String.valueOf(message.getAuctionId()),"/private",auction.getMyStatus());
    }



}
