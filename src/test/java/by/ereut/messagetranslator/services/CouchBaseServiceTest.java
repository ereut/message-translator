package by.ereut.messagetranslator.services;

import by.ereut.messagetranslator.CustomPair;
import by.ereut.messagetranslator.CustomPairService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertTrue;

@ActiveProfiles("test")
@SpringBootTest
@RunWith(SpringJUnit4ClassRunner.class)
public class CouchBaseServiceTest {

    @Autowired
    private CustomPairService service;

    @Before
    public void setUp() {
        service.saveAll(Arrays.asList(new CustomPair("4", 900.0)));
    }

    @Test
    public void lessThenBalanceTest() {
        CustomPair first = new CustomPair("1", 12.45);
        CustomPair second = new CustomPair("2", 22.87);
        CustomPair third = new CustomPair("3", 100.22);
        service.saveAll(Arrays.asList(first, second, third));
        assertTrue(service.findPairWithLessThanBalance(300.0).size() == 3);
        assertTrue(service.findPairWithLessThanBalance(3.0).isEmpty());
        assertTrue(!service.findPairWithLessThanBalance(50.0).contains(third));
    }

}
