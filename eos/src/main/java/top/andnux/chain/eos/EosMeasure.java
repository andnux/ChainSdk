package top.andnux.chain.eos;

import top.andnux.chain.core.AppExecutors;
import top.andnux.chain.core.Measure;
import top.andnux.chain.eos.api.EosRpc;

public class EosMeasure implements Measure {
    @Override
    public void measure(String url, int index, CallBack callBack) {
        AppExecutors executors = AppExecutors.getInstance();
        long start = System.currentTimeMillis();
        executors.networkIO().execute(() -> {
            try {
                new EosRpc(url).getChainInfo();
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
