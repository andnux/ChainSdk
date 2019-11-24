package top.andnux.chain.eth;

import org.web3j.protocol.Web3j;
import org.web3j.protocol.http.HttpService;

import top.andnux.chain.core.AppExecutors;
import top.andnux.chain.core.Measure;

public class EthMeasure implements Measure {

    @Override
    public void measure(String url, int index, CallBack callBack) {
        Web3j web3j = Web3j.build(new HttpService(url));
        AppExecutors executors = AppExecutors.getInstance();
        long start = System.currentTimeMillis();
        executors.networkIO().execute(() -> {
            try {
                web3j.ethGasPrice().send().getGasPrice();
                long end = System.currentTimeMillis();
                executors.mainThread().execute(() -> {
                    if (callBack == null) return;
                    callBack.onSuccess(url, index, String.valueOf(end - start));
                });
            } catch (Exception e) {
                e.printStackTrace();
                executors.mainThread().execute(() -> {
                    if (callBack == null) return;
                    callBack.onError(url, index, e);
                });
            }
        });
    }
}
