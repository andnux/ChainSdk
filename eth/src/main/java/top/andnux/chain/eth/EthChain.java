package top.andnux.chain.eth;


import top.andnux.chain.core.Chain;
import top.andnux.chain.core.Measure;
import top.andnux.chain.core.database.entity.AccountEntity;
import top.andnux.chain.core.database.entity.WalletEntity;

public class EthChain implements Chain {

    @Override
    public Measure getMeasure() {
        return new EthMeasure();
    }

    @Override
    public String name() {
        return "ETH";
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
