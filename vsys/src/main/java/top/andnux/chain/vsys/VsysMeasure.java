package top.andnux.chain.vsys;

import top.andnux.chain.core.AppExecutors;
import top.andnux.chain.core.Env;
import top.andnux.chain.core.Measure;
import v.systems.Blockchain;
import v.systems.type.NetworkType;

public class VsysMeasure implements Measure {

    @Override
    public void measure(String url, int index, CallBack callBack) {
        NetworkType type = Env.getCurrent() == Env.MAIN
                ? NetworkType.Mainnet : NetworkType.Testnet;
        Blockchain blockchain = new Blockchain(type, url);
        AppExecutors executors = AppExecutors.getInstance();
        long start = System.currentTimeMillis();
        executors.networkIO().execute(() -> {
            try {
                blockchain.getHeight();
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
