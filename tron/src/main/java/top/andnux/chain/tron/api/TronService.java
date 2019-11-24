package top.andnux.chain.tron.api;

import retrofit2.http.Body;
import retrofit2.http.POST;
import top.andnux.chain.tron.api.request.BroadcastTransactionRequest;
import top.andnux.chain.tron.api.request.TransactionRequest;
import top.andnux.chain.tron.api.request.TransactionSignRequest;
import top.andnux.chain.tron.api.response.BowBlockResponse;

public interface TronService {

    /**
     * wallet/createtransaction
     * 作用： 创建一个转账的Transaction，如果转账的to地址不存在，则在区块链上创建该账号
     * demo: curl -X POST  http://127.0.0.1:8090/wallet/createtransaction -d
     * '{"to_address": "41e9d79cc47518930bc322d9bf7cddd260a0260a8d",
     * "owner_address": "41D1E7A6BC354106CB410E65FF8B181C600FF14292",
     * "amount": 1000 }'
     * 参数说明：to_address是转账转入地址，需要转为hexString；owner_address是转账转出地址，
     * 需要转为hexString；amount是转账数量
     * 返回值：转账合约
     *
     * @param transactionRequest
     */
    @POST("wallet/createtransaction")
    void walletCreatetransaction(@Body TransactionRequest transactionRequest);

    /**
     * wallet/getnowblock
     * 作用：查询最新块。
     * demo: curl -X POST  http://127.0.0.1:8090/wallet/getnowblock
     * 参数说明：无
     * 返回值：当前块。
     *
     * @return
     */
    @POST("wallet/getnowblock")
    BowBlockResponse walletGetnowblock();

    /**
     * /wallet/gettransactionsign
     * 作用：对交易签名，该api有泄漏private key的风险，请确保在安全的环境中调用该api
     * demo: curl -X POST  http://127.0.0.1:8090/wallet/gettransactionsign -d '{
     * "transaction" : {"txID":"454f156bf1256587ff6ccdbc56e64ad0c51e4f8efea5490dcbc720ee606bc7b8",
     * "raw_data":{"contract":[{"parameter":{"value":{"amount":1000,
     * "owner_address":"41e552f6487585c2b58bc2c9bb4492bc1f17132cd0",
     * "to_address":"41d1e7a6bc354106cb410e65ff8b181c600ff14292"},
     * "type_url":"type.googleapis.com/protocol.TransferContract"},
     * "type":"TransferContract"}],"ref_block_bytes":"267e",
     * "ref_block_hash":"9a447d222e8de9f2","expiration":1530893064000,"timestamp":1530893006233}},
     * "privateKey": "your private key"
     * }'
     * 参数说明：transaction是通过http api创建的合约，privateKey是用户private key
     * 返回值：签名之后的transaction
     */
    @POST("wallet/gettransactionsign")
    void walletGetTransactionSign(TransactionSignRequest transactionSignRequest);

    /**
     * wallet/broadcasttransaction
     * 作用：对签名后的transaction进行广播
     * demo：curl -X POST  http://127.0.0.1:8090/wallet/broadcasttransaction -d
     * '{"signature":["97c825b41c77de2a8bd65b3df55cd4c0df59c307c0187e42321dcc1cc455ddba583dd9502e17cfec5945b34cad0511985a6165999092a6dec84c2bdd97e649fc01"],"txID":"454f156bf1256587ff6ccdbc56e64ad0c51e4f8efea5490dcbc720ee606bc7b8",
     * "raw_data":{"contract":[{"parameter":{"value":{"amount":1000,
     * "owner_address":"41e552f6487585c2b58bc2c9bb4492bc1f17132cd0",
     * "to_address":"41d1e7a6bc354106cb410e65ff8b181c600ff14292"},
     * "type_url":"type.googleapis.com/protocol.TransferContract"},
     * "type":"TransferContract"}],"ref_block_bytes":"267e",
     * "ref_block_hash":"9a447d222e8de9f2","expiration":1530893064000,
     * "timestamp":1530893006233}}'
     * 参数说明：签名之后的Transaction
     * 返回值：广播是否成功
     */
    @POST("wallet/broadcasttransaction")
    void walletBroadcastTransaction(BroadcastTransactionRequest broadcastTransactionRequest);
}
