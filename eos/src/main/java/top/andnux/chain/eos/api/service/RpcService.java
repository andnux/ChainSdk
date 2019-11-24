package top.andnux.chain.eos.api.service;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import top.andnux.chain.eos.api.vo.Block;
import top.andnux.chain.eos.api.vo.ChainInfo;
import top.andnux.chain.eos.api.vo.TableRows;
import top.andnux.chain.eos.api.vo.TableRowsReq;
import top.andnux.chain.eos.api.vo.account.Account;
import top.andnux.chain.eos.api.vo.transaction.Transaction;
import top.andnux.chain.eos.api.vo.transaction.push.TxRequest;

/**
 * 
 * @author espritblock http://eblock.io
 *
 */
public interface RpcService {

	@GET("/v1/chain/get_info")
	Call<ChainInfo> getChainInfo();

	@POST("/v1/chain/get_block")
	Call<Block> getBlock(@Body Map<String, String> requestFields);

	@POST("/v1/chain/get_account")
	Call<Account> getAccount(@Body Map<String, String> requestFields);

	@POST("/v1/chain/push_transaction")
	Call<Transaction> pushTransaction(@Body TxRequest request);

	@POST("/v1/chain/get_table_rows")
	Call<TableRows> getTableRows(@Body TableRowsReq request);

}
