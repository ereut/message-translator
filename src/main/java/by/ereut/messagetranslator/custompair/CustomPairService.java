package by.ereut.messagetranslator.custompair;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@Profile({"dev", "test"})
public class CustomPairService {

    private final CustomPairRepository repository;

    public CustomPairService(CustomPairRepository repository) {
        this.repository = repository;
    }

    public Iterable<CustomPair> saveAll(Iterable<CustomPair> pairs) {
        return repository.saveAll(pairs);
    }

    public List<CustomPair> findPairWithLessThanBalance(Double balanceValue) {
        return repository.findByBalanceLessThan(balanceValue);
    }


}
