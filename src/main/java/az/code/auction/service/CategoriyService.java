package az.code.auction.service;

import az.code.auction.entity.Categoriy;
import az.code.auction.repo.ICategoriyRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoriyService {
    private final ICategoriyRepo myRepo;
    public Categoriy save(Categoriy categoriy)
    {
        return myRepo.save(categoriy);
    }
    public void update(Categoriy categoriy)
    {
        myRepo.save(categoriy);
    }
    public void deleteById(Long id)
    {
        myRepo.deleteById(id);
    }
    public void delete(Categoriy categoriy)
    {
        myRepo.delete(categoriy);
    }
    public Categoriy findById(Long id)
    {
        return myRepo.findById(id).get();
    }
    public List<Categoriy> findAll()
    {
        return myRepo.findAll();
    }
}

