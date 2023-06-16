package az.code.auction.service;

import az.code.auction.entity.AuctRacer;
import az.code.auction.entity.Auction;
import az.code.auction.entity.Data;
import az.code.auction.repo.IDataRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DataService {
    IDataRepo myRepo;

    public Data save(Data data)
    {
        return myRepo.save(data);
    }
    public void update(Data data)
    {
        myRepo.save(data);
    }
    public void deleteById(Long id)
    {
        myRepo.deleteById(id);
    }
    public void delete(Data data)
    {
        myRepo.delete(data);
    }
    List<Data> findAll()
    {
        return myRepo.findAll();
    }


}
