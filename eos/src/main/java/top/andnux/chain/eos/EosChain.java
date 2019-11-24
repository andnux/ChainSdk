package top.andnux.chain.eos;

import top.andnux.chain.core.Chain;
import top.andnux.chain.core.Measure;
import top.andnux.chain.core.database.entity.AccountEntity;

public class EosChain implements Chain {

    @Override
    public Measure getMeasure() {
        return new EosMeasure();
    }

    @Override
    public String name() {
        return "EOS";
    }

    @Override
    public AccountEntity createAccount(Object... objects) {
        return null;
    }

    @Override
    public AccountEntity importAccountByPrivateKey(String privateKey, Object... objects) {
        return null;
    }

    @Override
    public AccountEntity importAccountByPublicKey(String publicKey, Object... objects) {
        return null;
    }

    @Override
    public AccountEntity importAccountByMnemonic(String publicKey, Object... objects) {
        return null;
    }
}
