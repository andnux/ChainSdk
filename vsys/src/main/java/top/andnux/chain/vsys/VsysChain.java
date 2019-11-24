package top.andnux.chain.vsys;

import top.andnux.chain.core.Chain;
import top.andnux.chain.core.Env;
import top.andnux.chain.core.Measure;
import top.andnux.chain.core.database.entity.AccountEntity;
import v.systems.Account;
import v.systems.type.NetworkType;

public class VsysChain implements Chain {

    @Override
    public Measure getMeasure() {
        return new VsysMeasure();
    }

    @Override
    public String name() {
        return "VSYS";
    }

    @Override
    public AccountEntity createAccount(Object... objects) {
        String seed = Wallet.generateSeed();
        Env current = Env.getCurrent();
        NetworkType network;
        if (current == Env.MAIN) {
            network = NetworkType.Mainnet;
        } else {
            network = NetworkType.Testnet;
        }
        Integer nonce = Integer.valueOf(objects[0].toString());
        Account account = new Account(network, seed, nonce);
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
