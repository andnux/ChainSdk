package v.systems;

import android.annotation.SuppressLint;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import v.systems.contract.Contract;
import v.systems.contract.ContractInfo;
import v.systems.contract.ContractType;
import v.systems.contract.TokenBalance;
import v.systems.contract.TokenInfo;
import v.systems.entity.Balance;
import v.systems.entity.BalanceDetail;
import v.systems.entity.Block;
import v.systems.entity.SlotInfo;
import v.systems.error.ApiError;
import v.systems.error.TransactionError;
import v.systems.transaction.ContendSlotTransaction;
import v.systems.transaction.ExecuteContractFunctionTransaction;
import v.systems.transaction.LeaseCancelTransaction;
import v.systems.transaction.LeaseTransaction;
import v.systems.transaction.PaymentTransaction;
import v.systems.transaction.ProvenTransaction;
import v.systems.transaction.RegisterContractTransaction;
import v.systems.transaction.ReleaseSlotTransaction;
import v.systems.transaction.Transaction;
import v.systems.transaction.TransactionParser;
import v.systems.type.NetworkType;
import v.systems.type.TransactionType;
import v.systems.utils.HttpClient;
import v.systems.utils.JsonHelper;

public class Blockchain {
    public static final long V_UNITY = 100000000L;
    private NetworkType network;
    private String nodeUrl;
    private String apiKey;
    private Gson gson;
    private JsonParser parser;

    public NetworkType getNetwork() {
        return network;
    }

    public void setNetwork(NetworkType network) {
        this.network = network;
    }

    public String getNodeUrl() {
        return nodeUrl;
    }

    public void setNodeUrl(String nodeUrl) {
        this.nodeUrl = nodeUrl;
    }

    public String getApiKey() {
        return apiKey;
    }

    public void setApiKey(String apiKey) {
        this.apiKey = apiKey;
    }

    public Gson getGson() {
        return gson;
    }

    public void setGson(Gson gson) {
        this.gson = gson;
    }

    public JsonParser getParser() {
        return parser;
    }

    public void setParser(JsonParser parser) {
        this.parser = parser;
    }

    public Blockchain(NetworkType network, String nodeUrl) {
        this.network = network;
        this.nodeUrl = nodeUrl;
        gson = JsonHelper.getGsonInstance();
        parser = JsonHelper.getParserInstance();
    }

    public Blockchain(NetworkType network, String nodeUrl, String apiKey) {
        this(network, nodeUrl);
        this.apiKey = apiKey;
    }

    public Long getBalance(String address) throws IOException, ApiError {
        String url = String.format("%s/addresses/balance/%s", nodeUrl, address);
        Balance balance = this.callChainAPI(url, Balance.class);
        return balance.getBalance();
    }

    public BalanceDetail getBalanceDetail(String address) throws IOException, ApiError {
        String url = String.format("%s/addresses/balance/details/%s", nodeUrl, address);
        return this.callChainAPI(url, BalanceDetail.class);
    }

    public List<Transaction> getActiveLeaseTransactions(String address) throws IOException, ApiError {
        String url = String.format("%s/transactions/activeLeaseList/%s", nodeUrl, address);
        return this.getTransactionByUrl(url, true);
    }

    public List<Transaction> getTransactionHistory(String address, int num) throws IOException, ApiError {
        if (num <= 0) {
            return new ArrayList<>();
        }
        @SuppressLint("DefaultLocale")
        String url = String.format("%s/transactions/address/%s/limit/%d", nodeUrl, address, num);
        return this.getTransactionByUrl(url, false);
    }

    private List<Transaction> getTransactionByUrl(String url, boolean useApiKey) throws IOException, ApiError {
        List<Transaction> result = new ArrayList<Transaction>();
        Map<String, String> header = new HashMap<String, String>();
        if (useApiKey && this.apiKey != null) {
            header.put("api_key", this.apiKey);
        }
        String json = HttpClient.get(url, header);
        try {
            JsonElement jsonElement = parser.parse(json);
            if (!jsonElement.isJsonArray()) {
                throw new ApiError(json);
            }
            JsonArray jsonArray = jsonElement.getAsJsonArray();
            if (jsonArray.size() == 0) {
                return result;
            }
            jsonElement = jsonArray.get(0);
            if (!jsonElement.isJsonArray()) {
                throw new ApiError(json);
            }
            jsonArray = jsonElement.getAsJsonArray();
            for (int i = 0; i < jsonArray.size(); i++) {
                Transaction tx = TransactionParser.parse(jsonArray.get(i));
                result.add(tx);
            }

        } catch (Exception ex) {
            throw ApiError.fromJson(json);
        }
        return result;
    }

    public Transaction getTransactionById(String txId) throws IOException, ApiError {
        String url = String.format("%s/transactions/info/%s", nodeUrl, txId);
        String json = HttpClient.get(url);
        try {
            return TransactionParser.parse(json);
        } catch (Exception ex) {
            throw ApiError.fromJson(json, TransactionError.class);
        }
    }

    public Transaction getUnconfirmedTransactionById(String txId) throws IOException, ApiError {
        String url = String.format("%s/transactions/unconfirmed/info/%s", nodeUrl, txId);
        String json = HttpClient.get(url);
        try {
            return TransactionParser.parse(json);
        } catch (Exception ex) {
            throw ApiError.fromJson(json, TransactionError.class);
        }
    }

    public ProvenTransaction sendTransaction(TransactionType txType, String json) throws IOException, ApiError {
        String url;
        switch (txType) {
            case Payment:
                url = String.format("%s/vsys/broadcast/payment", nodeUrl);
                return this.callChainAPI(url, json, PaymentTransaction.class);
            case Lease:
                url = String.format("%s/leasing/broadcast/lease", nodeUrl);
                return this.callChainAPI(url, json, LeaseTransaction.class);
            case LeaseCancel:
                url = String.format("%s/leasing/broadcast/cancel", nodeUrl);
                return this.callChainAPI(url, json, LeaseCancelTransaction.class);
            case ContendSlot:
                url = String.format("%s/spos/broadcast/contend", nodeUrl);
                return this.callChainAPI(url, json, ContendSlotTransaction.class);
            case ReleaseSlot:
                url = String.format("%s/spos/broadcast/release", nodeUrl);
                return this.callChainAPI(url, json, ReleaseSlotTransaction.class);
            case RegisterContract:
                url = String.format("%s/contract/broadcast/register", nodeUrl);
                return this.callChainAPI(url, json, RegisterContractTransaction.class);
            case ExecuteContractFunction:
                url = String.format("%s/contract/broadcast/execute", nodeUrl);
                return this.callChainAPI(url, json, ExecuteContractFunctionTransaction.class);
            default:
                throw new ApiError("Unsupported Transaction Type");
        }
    }

    public Integer getHeight() throws IOException, ApiError {
        String url = String.format("%s/blocks/height", nodeUrl);
        String json = HttpClient.get(url);
        try {
            JsonElement jsonElement = parser.parse(json);
            if (!jsonElement.isJsonObject()) {
                throw new ApiError(json);
            }
            JsonObject jsonObj = jsonElement.getAsJsonObject();
            JsonElement heightElement = jsonObj.get("height");
            if (heightElement == null) {
                throw ApiError.fromJson(json);
            }
            return heightElement.getAsInt();
        } catch (Exception ex) {
            throw ApiError.fromJson(json);
        }
    }

    public Block getLastBlock() throws IOException, ApiError {
        String url = String.format("%s/blocks/last", nodeUrl);
        return this.callChainAPI(url, Block.class);
    }

    public Block getBlockByHeight(int height) throws IOException, ApiError  {
        String url = String.format("%s/blocks/at/%d", nodeUrl, height);
        return this.callChainAPI(url, Block.class);
    }

    public SlotInfo getSlotInfo(int slotId) throws IOException, ApiError {
        String url = String.format("%s/consensus/slotInfo/%d", nodeUrl, slotId);
        return this.callChainAPI(url, SlotInfo.class);
    }

    public List<SlotInfo> getAllSlotInfo() throws IOException, ApiError {
        String url = String.format("%s/consensus/allSlotsInfo", nodeUrl);
        String json = HttpClient.get(url);
        List<SlotInfo> result = new ArrayList<SlotInfo>();
        Integer height = 0;
        try {
            JsonElement jsonElement = parser.parse(json);
            if (!jsonElement.isJsonArray()) {
                throw new ApiError(json);
            }
            JsonArray jsonArray = jsonElement.getAsJsonArray();
            for (int i = 0; i < jsonArray.size(); i++) {
                SlotInfo info = gson.fromJson(jsonArray.get(i), SlotInfo.class);
                if (info.getHeight() != null) {
                    height = info.getHeight();
                }
                if (info.getAddress() != null) {
                    result.add(info);
                }
            }
        } catch (Exception ex) {
            throw ApiError.fromJson(json);
        }
        for (SlotInfo info : result) {
            if (info.getHeight() == null) {
                info.setHeight(height);
            }
        }
        return result;
    }

    public TokenInfo getTokenInfo(String tokenId) throws IOException, ApiError {
        String url = String.format("%s/contract/tokenInfo/%s", nodeUrl, tokenId);
        return this.callChainAPI(url, TokenInfo.class);
    }

    public ContractType getContractTypeByTokenId(String tokenId) throws IOException, ApiError {
        String contractId = TokenInfo.getContractId(tokenId);
        ContractInfo contractInfo = this.getContractInfo(contractId);
        return contractInfo.getContractType();
    }

    public TokenBalance getTokenBalance(String address, String tokenId) throws IOException, ApiError {
        String url = String.format("%s/contract/balance/%s/%s", nodeUrl, address, tokenId);
        return this.callChainAPI(url, TokenBalance.class);
    }

    public ContractInfo getContractInfo(String contractId) throws IOException, ApiError {
        String url = String.format("%s/contract/info/%s", nodeUrl, contractId);
        return this.callChainAPI(url, ContractInfo.class);
    }

    public Contract getContractContent(String contractId) throws IOException, ApiError {
        String url = String.format("%s/contract/content/%s", nodeUrl, contractId);
        return this.callChainAPI(url, Contract.class);
    }

    private <T> T callChainAPI(String url, Class<T> classType) throws IOException, ApiError {
        String json = HttpClient.get(url);
        return parseResponse(classType, json);
    }

    private <T> T callChainAPI(String url, String jsonData, Class<T> classType) throws IOException, ApiError {
        String json = HttpClient.post(url, jsonData);
        return parseResponse(classType, json);
    }

    private <T> T parseResponse(Class<T> classType, String json) throws ApiError {
        try {
            JsonElement jsonElement = parser.parse(json);
            if (jsonElement.isJsonObject()) {
                JsonObject jsonObj = jsonElement.getAsJsonObject();
                if (jsonObj.get("error") != null) {
                    throw ApiError.fromJson(json);
                } else {
                    return gson.fromJson(jsonElement, classType);
                }
            } else {
                return gson.fromJson(json, classType);
            }
        } catch (Exception ex) {
            throw ApiError.fromJson(json);
        }
    }
}
