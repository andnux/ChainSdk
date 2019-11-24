package top.andnux.chain.core;

public interface Measure {

    void measure(String url, int index, CallBack callBack);

    interface CallBack {

        void onSuccess(String url, int index, String speed);

        void onError(String url, int index, Exception e);
    }
}
