package com.danielcruz.bid.controller;

import com.danielcruz.bid.exception.NoWinnerException;
import com.danielcruz.bid.model.Bid;
import com.danielcruz.bid.model.AuctionResult;
import com.danielcruz.bid.service.AuctionService;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class AuctionController {

    private final AuctionService service = new AuctionService();

    @RequestMapping(value = "/winner",
                    method = RequestMethod.POST,
                    consumes = MediaType.APPLICATION_JSON_VALUE,
                    produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody
    AuctionResult winner(@RequestBody List<Bid> bids) {
        AuctionResult winner = service.getWinner(bids);
        //The method getWinner returns null if there isn't winner
        if(winner == null){
            throw new NoWinnerException("No one wins this auctions");
        }
        return winner;
    }

}
