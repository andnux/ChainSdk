package top.andnux.chain.eos;

import top.andnux.chain.core.Chain;
import top.andnux.chain.core.Measure;
import top.andnux.chain.core.database.entity.AccountEntity;
import top.andnux.chain.core.database.entity.WalletEntity;

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
    public AccountEntity createAccount(WalletEntity entity, Object... objects)throws Exception {
        return null;
    }

    @Override
    public AccountEntity importAccountByPrivateKey(WalletEntity entity,String privateKey, Object... objects) throws Exception{
        return null;
    }

    @Override
    public AccountEntity importAccountByPublicKey(WalletEntity entity,String publicKey, Object... objects)throws Exception {
        return null;
    }

    @Override
    public AccountEntity importAccountByMnemonic(WalletEntity entity,String publicKey, Object... objects)throws Exception {
        return null;
    }
}
