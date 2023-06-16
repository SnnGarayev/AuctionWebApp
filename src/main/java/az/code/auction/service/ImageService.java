package az.code.auction.service;

import az.code.auction.entity.Image;
import az.code.auction.entity.User;
import az.code.auction.repo.IImageRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ImageService {

    private final IImageRepo imageRepo;


    public Image save(Image img)
    {
        return imageRepo.save(img);
    }
    public List<Image> saveAll(List<Image> images){
        return imageRepo.saveAll(images);
    }
    public Image update(Image img)
    {
        return imageRepo.save(img);
    }
    public void deleteById(Long id){imageRepo.deleteById(id);}
    public void delete(Image img)
    {
        imageRepo.delete(img);
    }
    public Optional<Image> findById(Long id)
    {
        return imageRepo.findById(id);
    }
    public List<Image> findAll()
    {
        return imageRepo.findAll();
    }
}
