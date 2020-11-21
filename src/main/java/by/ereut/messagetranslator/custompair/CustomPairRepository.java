package by.ereut.messagetranslator.custompair;

import org.springframework.context.annotation.Profile;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Profile({"dev", "test"})
public interface CustomPairRepository extends CrudRepository<CustomPair, Long> {

    List<CustomPair> findByBalanceLessThan(Double balance);

}
