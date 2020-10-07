package com.danielcruz.bid.service;

import com.danielcruz.bid.controller.AuctionController;
import com.danielcruz.bid.exception.BadBidValueException;
import com.danielcruz.bid.exception.NoWinnerException;
import com.danielcruz.bid.model.Bid;
import com.danielcruz.bid.model.AuctionResult;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

class AuctionServiceTest {

    @DisplayName("Sample test explaned on email")
    @Test
    void emailSampleTest() {
        List<Bid> bids = new ArrayList<>();
        bids.add(new Bid("João",0.01));
        bids.add(new Bid("Maria", 0.3));
        bids.add(new Bid("Renata",  0.01));
        bids.add(new Bid("Pedro", 12.34));

        AuctionResult result = new AuctionController().winner(bids);

        assertTrue(result.getName().equals("Maria") && result.getValue() == 0.3 && result.getRevenue() == 3.92);
    }

    @DisplayName("Testing negative bid values")
    @Test
    void negativeErrorTest(){
        List<Bid> bids = new ArrayList<>();
        bids.add(new Bid("João",0.01));
        bids.add(new Bid("Maria", 0.3));
        bids.add(new Bid("Paulo", -0.3));

        assertThrows(BadBidValueException.class, () -> new AuctionController().winner(bids));
    }

    @DisplayName("Testing bid with zero value")
    @Test
    void zeroErrorTest(){
        List<Bid> bids = new ArrayList<>();
        bids.add(new Bid("João",0.01));
        bids.add(new Bid("Maria", 0.3));
        bids.add(new Bid("Paulo", 0));

        assertThrows(BadBidValueException.class, () -> new AuctionController().winner(bids));
    }

    @DisplayName("Testing bid with more than 2 decimal points")
    @Test
    void moreThenTwoDecimalsTest(){
        List<Bid> bids = new ArrayList<>();
        bids.add(new Bid("João",0.01));
        bids.add(new Bid("Maria", 0.3));
        bids.add(new Bid("Paulo", 0.651));

        assertThrows(BadBidValueException.class, () -> new AuctionController().winner(bids));
    }

    @DisplayName("Testing when we don't have any winners on the auction")
    @Test
    void noWinnerTest(){
        List<Bid> bids = new ArrayList<>();
        bids.add(new Bid("João",0.01));
        bids.add(new Bid("Maria", 0.30));
        bids.add(new Bid("Paulo", 5.66));
        bids.add(new Bid("Renata", 6.23));
        bids.add(new Bid("Pedro", 0.01));
        bids.add(new Bid("Thiago", 5.66));
        bids.add(new Bid("Fernanda", 0.30));
        bids.add(new Bid("Rodrigo", 6.23));

        assertThrows(NoWinnerException.class, () -> new AuctionController().winner(bids));
    }

    @DisplayName("Testing revenue value for 100 bids")
    @Test
    void testRevenue100Test(){
        List<Bid> bids = new ArrayList<>();
        for(var i = 1; i <= 100; i++){
            bids.add(new Bid("Name"+i, i));
        }
        AuctionResult result = new AuctionController().winner(bids);
        assertEquals(100*0.98, result.getRevenue());
    }

    @DisplayName("Testing revenue value for 500 bids")
    @Test
    void testRevenue500Test(){
        List<Bid> bids = new ArrayList<>();
        for(var i = 1; i <= 500; i++){
            bids.add(new Bid("Name"+i, i));
        }
        AuctionResult result = new AuctionController().winner(bids);
        assertEquals(500*0.98, result.getRevenue());
    }

    @DisplayName("Testing revenue value for 999 bids")
    @Test
    void testRevenue999Test(){
        List<Bid> bids = new ArrayList<>();
        for(var i = 1; i <= 999; i++){
            bids.add(new Bid("Name"+i, i));
        }
        AuctionResult result = new AuctionController().winner(bids);
        assertEquals(999*0.98, result.getRevenue());
    }

    @DisplayName("Testing revenue value for 1000 bids")
    @Test
    void testRevenue1000Test(){
        List<Bid> bids = new ArrayList<>();
        for(var i = 1; i <= 1000; i++){
            bids.add(new Bid("Name"+i, i));
        }
        AuctionResult result = new AuctionController().winner(bids);
        assertEquals(999*0.98, result.getRevenue());
    }

    @DisplayName("Testing revenue value for 10000 bids")
    @Test
    void testRevenue10000Test(){
        List<Bid> bids = new ArrayList<>();
        for(var i = 1; i <= 10000; i++){
            bids.add(new Bid("Name"+i, i));
        }
        AuctionResult result = new AuctionController().winner(bids);
        assertEquals(999*0.98, result.getRevenue());
    }

    @DisplayName("Testing 200 random bid values")
    @Test
    void randomBidValues200Test(){
        int count = 200;
        List<Bid> bids = new ArrayList<>();
        for(var i = 1; i <= count; i++){
            bids.add(new Bid("Name"+i, getRandomDouble(0.01, 1.0)));
        }
        AuctionResult result = new AuctionController().winner(bids);
        assertEquals(count*0.98, result.getRevenue());
        assertTrue(checkIfIsUnique(bids, result));
    }

    private boolean checkIfIsUnique(List<Bid> bids, AuctionResult result) {
        List<Bid> filtered = bids.stream().filter(x -> result.getValue().equals(x.getValue())).collect(Collectors.toList());
        return filtered.size() == 1;
    }

    private Double getRandomDouble(Double min, Double max){
        double randomBid = ThreadLocalRandom.current().nextDouble(min, max);
        return BigDecimal.valueOf(randomBid).setScale(2, RoundingMode.HALF_UP).doubleValue();
    }

}