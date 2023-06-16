package az.code.auction.service;

import az.code.auction.entity.*;
import az.code.auction.redis.*;
import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MainService {
    private final AuctionService as;
    private final RedisService rs;
    private final UserService us;
    private final MailService ms;
    private final AuctRacerService ars;


    public RedisEntity createAuction(Long userId, Auction newAuction) {
        newAuction.setAuctioner(us.findById(userId).get());
        newAuction.setEndTime(System.currentTimeMillis() + (1000*60*60*24));
        Auction auction = as.save(newAuction);

        return rs.save(RedisEntity.builder().id(auction.getId()).auction(auction).build());
    }

    public Auction endAuction(Long id) {
        Auction auction = rs.findById(id).get().getAuction();
        List<AuctRacer> racers = auction.getMyStatus().getRacers();
        racers.stream().sorted(Comparator.comparingLong(AuctRacer::getMyPrice)).collect(Collectors.toList());
        for (int i = 0; i < racers.size(); i++) {
            racers.get(i).setWinQueue(racers.size()-i);
        }
        AuctStatus status = auction.getMyStatus();
        status.setRacers(racers);
        auction.setMyStatus(status);
        auction.setIsActive(false);
        Auction act = as.save(auction);
        getWinner(id);
        rs.remove(id);
        return act;
    }

    public String getWinner(Long auctionId) {
        Auction ac = as.findById(auctionId).get();
        int a = ac.getMyStatus().getWinnerQue();
        Long winnerId = 0L;
        List<AuctRacer> racers = ac.getMyStatus().getRacers();
        if (a < racers.size()) {
            for (int i = 0; i < racers.size(); i++) {
                if (racers.get(i).getWinQueue() == a + 1) {
                    System.out.println("mail gonderildi");
                    ac.getMyStatus().setWinner(racers.get(i).getRacer());
                    ac.getMyStatus().setWinnerQue(a + 1);
                    ac.getMyStatus().setPrice(racers.get(i).getMyPrice());
                    as.save(ac);
                    ms.sendMail(
                            ac.getAuctioner().getEmail(),
                            ac.getTitle()+" auction is over",
                            "Winner : " +racers.get(i).getRacer().getName()+ "\nBid : " + "$"+ ac.getMyStatus().getPrice()  + "\nPhone number : " + racers.get(i).getRacer().getPhoneNumber() + "\nEmail : " + racers.get(i).getRacer().getEmail());
                    ms.sendMail(
                            racers.get(i).getRacer().getEmail(),
                            "Congratulations !",
                            "You won auction " + ac.getId() + " with a bid of $" + ac.getMyStatus().getPrice() + ". The auctioneer will contact you.");
                }
                else if(racers.get(i).getWinQueue()==a-1)winnerId=racers.get(i).getId();
            }
            if (a>1) {
                List<Data> auctionerDatas = ac.getAuctioner().getMyDatas();
                List<Data> winnerDatas = ac.getMyStatus().getWinner().getMyDatas();
                for (int i = 0; i < auctionerDatas.size(); i++) {
                    for (int j = 0; j < winnerDatas.size(); j++) {
                        if (Objects.equals(auctionerDatas.get(i).getType(), winnerDatas.get(j).getType()) && Objects.equals(auctionerDatas.get(i).getIp(), winnerDatas.get(j).getIp())) {
                            Long total = 0L;
                            for (int m = 0; m < racers.size(); m++) {
                                if (racers.get(m).getWinQueue() == 1) total = racers.get(m).getTotal();
                            }
                            addBandas(ac.getAuctioner().getIgnoreCode(), us.findById(winnerId).get().getIgnoreCode());
                            for (int t = 0; t < racers.size(); t++) {
                                racers.get(t).setMyPrice(racers.get(t).getMyPrice() - total);
                            }
                            ac.getMyStatus().setRacers(racers);
                            as.update(ac);
                        }
                    }

                }
            }
        }

        return "e-Mail unvaniniza melumat gonderlidi";
    }



    public Auction joinAuction(Long userId, Long auctionId) {
        Optional<Auction> auction = as.findById(auctionId);
        Optional<User> user = us.findById(userId);

        if (auction.get().getIsActive() && ( user.get().getRole().equals("AUCTIONER") || !auction.get().getAuctioner().getId().equals(user.get().getIgnoreCode()))) {
            boolean selector = true;
            Auction redisAuct = rs.findById(auctionId).get().getAuction();
            for (AuctRacer racer : redisAuct.getMyStatus().getRacers()) {
                if (racer.getId().equals(userId)) {
                    selector = false;
                    break;
                }
            }
            if (selector) {
                AuctRacer newRacer = new AuctRacer();
                if (user.isPresent()) {
                    newRacer.setRacer(user.get());
                    newRacer.setId(user.get().getId());

                }
                redisAuct.getMyStatus().getRacers().add(newRacer);
                return rs.save(RedisEntity.builder().id(redisAuct.getId()).auction(redisAuct).build()).getAuction();
            }else{
                return redisAuct;
            }
        }
        return null;

    }

    public Auction addPrice(Long userId, Long auctionId, Long price, Long inTimePrice) {
        Auction act = rs.findById(auctionId).get().getAuction();
        if (inTimePrice < price && act.getIsActive()) {
            for (int i = 0; i < act.getMyStatus().getRacers().size(); i++) {
                if (act.getMyStatus().getRacers().get(i).getId().equals(userId) && act.getMyStatus().getPrice() < price) {
                    act.getMyStatus().getRacers().get(i).setMyPrice(price,inTimePrice);
                    if (act.getMyStatus().getPrice() < price) {
                        act.getMyStatus().setPrice(price);
                        act.getMyStatus().setWinner(us.findById(userId).get());
                    }
                    act = rs.save(RedisEntity.builder().id(act.getId()).auction(act).build()).getAuction();
                }
            }
        }
        return act;

    }

    public void securedTest(Long userId, Long cokie, @NonNull HttpServletRequest request) {
        User user = us.findById(userId).get();
        boolean test = false;
        String tt = Arrays.stream(Arrays.stream(request.getHeader("user-agent").split("[(]")).toList().get(1).split("[)]")).toList().get(0);
        if (!user.getIgnoreCode().equals(cokie)) {
            addBandas(user.getIgnoreCode(), cokie);
        }
        List<Data> datas = user.getMyDatas();
        for (int j = 0; j < datas.size(); j++) {
            if (datas.get(j).getIp().equals(request.getRemoteAddr()) && datas.get(j).getType().equals(tt)) {
                test = true;
            }
        }
        if (!test) {
            Data newData = new Data();
            newData.setIp(request.getRemoteAddr());
            newData.setType(tt);
            user.getMyDatas().add(newData);
            us.save(user);
        }

    }
    public void addBandas(Long usercode, Long cokie) {
        List<User> cokiesBanda = us.getBanda(cokie);
        List<User> usersBanda = us.getBanda(usercode);
        if (cokiesBanda.size() > usersBanda.size()) {
            for (int i = 0; i < usersBanda.size(); i++) {
                usersBanda.get(i).setIgnoreCode(cokie);
                us.save(usersBanda.get(i));
            }

        } else {
            for (int i = 0; i < cokiesBanda.size(); i++) {
                cokiesBanda.get(i).setIgnoreCode(usercode);
                us.save(cokiesBanda.get(i));
            }

        }
    }

    public void deleter(Long b,Long id) {
        Long a=b-System.currentTimeMillis();
        Thread t1 = new Thread(() -> {
            try {
                Thread.sleep(a);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            if(id!=null)endAuction(id);
            selectAuction();
        });
        t1.start();
    }

    @PostConstruct
    public void selectAuction(){

        List<RedisEntity> auctions = rs.getAll();
        Auction min= new Auction();
        min.setEndTime(System.currentTimeMillis()+(1000*60*60*24));
        if(auctions!=null)
        {
            for(int i=0;i<auctions.size();i++)
            {
                if(auctions.get(i).getAuction().getTime()<=System.currentTimeMillis())
                {
                    endAuction(auctions.get(i).getAuction().getId());
                }
                else
                {
                    if(min.getTime()>auctions.get(i).getAuction().getTime())
                    {
                        min = auctions.get(i).getAuction();
                    }
                }
            }
        }
        deleter(min.getTime(),min.getId());

    }

    public List<Auction> getBidderAuctions(Long userId){
        return rs.getBidderAuctions(userId);
    }



}
