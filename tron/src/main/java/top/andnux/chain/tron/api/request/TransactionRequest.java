package top.andnux.chain.tron.api.request;

import java.io.Serializable;

public class TransactionRequest implements Serializable {

    private String to_address;
    private String owner_address;
    private int amount;

    public TransactionRequest() {
    }

    public TransactionRequest(String to_address, String owner_address, int amount) {
        this.to_address = to_address;
        this.owner_address = owner_address;
        this.amount = amount;
    }

    public String getTo_address() {
        return to_address;
    }

    public void setTo_address(String to_address) {
        this.to_address = to_address;
    }

    public String getOwner_address() {
        return owner_address;
    }

    public void setOwner_address(String owner_address) {
        this.owner_address = owner_address;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    @Override
    public String toString() {
        return "CreateTransactionRequest{" +
                "to_address='" + to_address + '\'' +
                ", owner_address='" + owner_address + '\'' +
                ", amount=" + amount +
                '}';
    }
}
