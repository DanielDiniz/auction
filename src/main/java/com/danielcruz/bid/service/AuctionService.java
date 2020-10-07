package com.danielcruz.bid.service;

import com.danielcruz.bid.helper.BidValidator;
import com.danielcruz.bid.model.Bid;
import com.danielcruz.bid.model.AuctionResult;

import java.util.*;
import java.util.stream.Collectors;

public class AuctionService {
    private final Double REVENUE_FACTOR = 0.98;

    public AuctionResult getWinner(List<Bid> bids) {
        List<Bid> validBids = new BidValidator().validateBids(bids);
        //If there is just one participant in this Auction, we can consider him as a winner
        if(validBids.size() == 1){
            return new AuctionResult(validBids.get(0), REVENUE_FACTOR);
        }

        //Counting the bids grouping by the bid value
        //We do it to know witch bid is unique
        Map<Double, Long> mappedBids =
                validBids.stream().collect(Collectors.groupingBy(Bid::getValue,
                                LinkedHashMap::new,
                                Collectors.counting()));

        // Based on the previous counting, we need to filter only the unmatched bids
        Map<Double, Long> filtered = mappedBids.entrySet().stream()
                .filter(x -> Long.valueOf(1).equals(x.getValue()))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));

        //if there aren't bids with only one shot, we can consider that the Auction has no winner
        if(filtered.size() == 0){
            return null;
        }

        // If there are bids with only one shot, we need to sort by key (bid value)
        // because we need to get the bid with the min value among all the unmatched bids
        Double key = Collections.min(filtered.entrySet(), Map.Entry.comparingByKey()).getKey();

        //Based on the min bid value, now, we can filter using the origin bids list.
        Bid winner = validBids.stream().filter(x -> key.equals(x.getValue())).findFirst().get();

        //Based on the winner found we'll return adding the revenue value of this Auction
        return new AuctionResult(winner, validBids.size()*REVENUE_FACTOR);
    }
}

