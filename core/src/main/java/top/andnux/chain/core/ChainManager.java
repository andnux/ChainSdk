package top.andnux.chain.core;

import android.text.TextUtils;

import java.util.HashSet;
import java.util.Set;

public class ChainManager {

    private static final ChainManager ourInstance = new ChainManager();
    private Set<Chain> mChains = new HashSet<>();

    public static ChainManager getInstance() {
        return ourInstance;
    }

    private ChainManager() {

    }

    public Chain getChain(String chain) {
        if (TextUtils.isEmpty(chain)) return null;
        for (Chain next : mChains) {
            if (chain.equalsIgnoreCase(next.name())) {
                return next;
            }
        }
        return null;
    }

    public void addChain(Chain chain) {
        mChains.add(chain);
    }

    public Set<Chain> getChains() {
        return mChains;
    }
}
