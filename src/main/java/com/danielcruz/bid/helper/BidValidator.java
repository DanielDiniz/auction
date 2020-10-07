package com.danielcruz.bid.helper;

import com.danielcruz.bid.exception.BadBidValueException;
import com.danielcruz.bid.model.Bid;

import java.math.BigDecimal;
import java.util.List;

public class BidValidator {
    public List<Bid> validateBids(List<Bid> bids) {
        //If there are more than 999 bids in this Auctions, we only use the first 999 bids.
        List<Bid> validBids = bids.size() > 999 ? bids.subList(0, 999) : bids;

        for(Bid bid : validBids){
            //Validation: The bid value needs to be positive
            if(bid.getValue() <= 0){
                throw new BadBidValueException("Value "+bid.getValue()+" must be positive");
            }

            //Validation: The bid value needs to have only 2 decimal points.
            if(BigDecimal.valueOf(bid.getValue()).scale() > 2){
                throw new BadBidValueException("Value "+bid.getValue()+" must have 2 decimal points");
            }
        }

        return validBids;
    }
}
