package az.code.auction.repo;

import az.code.auction.entity.Data;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface IDataRepo extends JpaRepository<Data,Long> {

}
