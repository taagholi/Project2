package server;

import java.math.BigDecimal;

/**
 * Created by Dotin school 6 on 1/23/2015.
 */
public class Account {

    private String customer;
    private String id;
    private BigDecimal balance;
    private BigDecimal upperBound;


    public String getCustomer() {
        return customer;
    }

    public void setCustomer(String customer) {
        this.customer = customer;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public BigDecimal getBalance() {return balance;}

    public void setBalance(BigDecimal Balance) {
        this.balance = Balance;
    }

    public BigDecimal getUpperBound() {
        return upperBound;
    }

    public void setUpperBound(BigDecimal upperBound) {
            this.upperBound = upperBound;
    }



}
