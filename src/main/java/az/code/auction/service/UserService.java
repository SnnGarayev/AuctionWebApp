package az.code.auction.service;

import az.code.auction.dto.UserLoginDTO;
import az.code.auction.entity.Auction;
import az.code.auction.entity.User;
import az.code.auction.repo.IUserRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final IUserRepo myRepo;
    public User save(User user)
    {
        User newUser = myRepo.save(user);
        if (newUser.getIgnoreCode() == null ){
            newUser.setIgnoreCode(newUser.getId());
            return myRepo.save(newUser);
        }else {
            return newUser;
        }
    }
    public User update(User user)
    {
        return myRepo.save(user);
    }
    public void deleteById(Long id)
    {
        myRepo.deleteById(id);
    }
    public void delete(User user)
    {
        myRepo.delete(user);
    }
    public Optional<User> findEmail (String email){
        return myRepo.findUserByEmail(email);
    }
    public Optional<User> findById(Long id)
    {
        return myRepo.findById(id);
    }
    public List<User> findAll()
    {
        return myRepo.findAll();
    }
    public Optional<User> findUsername(String name){
        return myRepo.findByName(name);
    }
    public List<User> getBanda(Long ignoreCode)
    {
        return myRepo.findByIgnoreCode(ignoreCode);
    }
}

