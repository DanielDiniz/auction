# Auction

The main goal of this project is to return the winner of an Auction. 

### Output

Return the winner in format:
```
{
  "name": ...,
  "value": ...,
  "revenue": ...
}
```

### Rules:

* The Minimum unique bid wins 
* Bids with two decimal points, positive and different of zero
* Revenue is 0.98 multiplies the total number of bids
* The maximum number of bids are 999, after this we ignore it

### How to Use

#### Project
Use the class AuctionApplication to run the local application

#### Local API Call
The API will be available on `http://localhost:8080/winner`

#### Call Format
* HttpMethod : POST
* Body: array of bids in JSON format
```
[
  {"name": ..., "value": ...},
  {"name": ..., "value": ...},
  {"name": ..., "value": ...},
  ...
]
```

### Example:

