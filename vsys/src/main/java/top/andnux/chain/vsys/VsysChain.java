package top.andnux.chain.vsys;

import java.util.Date;

import top.andnux.chain.core.Chain;
import top.andnux.chain.core.Env;
import top.andnux.chain.core.Measure;
import top.andnux.chain.core.State;
import top.andnux.chain.core.database.entity.AccountEntity;
import top.andnux.chain.core.database.entity.WalletEntity;
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
    public AccountEntity createAccount(WalletEntity wallet, Object... objects) throws Exception {
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
        AccountEntity accountEntity = new AccountEntity();
        accountEntity.setChain(name());
        accountEntity.setEnv(Env.getCurrent());
        Date time = new Date();
        accountEntity.setCreateTime(time);
        accountEntity.setNonce(String.valueOf(nonce));
        accountEntity.setName(name());
        accountEntity.setAddress(account.getAddress());
        accountEntity.setState(State.NORMAL);
        accountEntity.setIcon("");
        accountEntity.setPrivateKey(account.getPrivateKey());
        accountEntity.setPublicKey(account.getPublicKey());
        accountEntity.setUpdateTime(time);
        accountEntity.setValue(account.getAddress());
        accountEntity.setWallet(wallet.getId());
        return accountEntity;
    }

    @Override
    public AccountEntity createAccountByMnemonic(WalletEntity entity, Object... objects) throws Exception {
        throw new IllegalArgumentException("Mnemonic create is not supported");
    }

    @Override
    public AccountEntity importAccountByPrivateKey(WalletEntity wallet, String privateKey, Object... objects) throws Exception {
        String seed = Wallet.generateSeed();
        Env current = Env.getCurrent();
        NetworkType network;
        if (current == Env.MAIN) {
            network = NetworkType.Mainnet;
        } else {
            network = NetworkType.Testnet;
        }
        Account account = new Account(network, privateKey);
        AccountEntity accountEntity = new AccountEntity();
        accountEntity.setChain(name());
        accountEntity.setEnv(Env.getCurrent());
        Date time = new Date();
        accountEntity.setCreateTime(time);
        accountEntity.setName(name());
        accountEntity.setAddress(account.getAddress());
        accountEntity.setState(State.NORMAL);
        accountEntity.setIcon("");
        accountEntity.setPrivateKey(account.getPrivateKey());
        accountEntity.setPublicKey(account.getPublicKey());
        accountEntity.setUpdateTime(time);
        accountEntity.setValue(account.getAddress());
        accountEntity.setWallet(wallet.getId());
        return accountEntity;
    }

    @Override
    public AccountEntity importAccountByPublicKey(WalletEntity wallet, String publicKey, Object... objects) throws Exception {
        String seed = Wallet.generateSeed();
        Env current = Env.getCurrent();
        NetworkType network;
        if (current == Env.MAIN) {
            network = NetworkType.Mainnet;
        } else {
            network = NetworkType.Testnet;
        }
        String base58Address = Account.getAddress(publicKey, network.toByte());
        Account account = new Account(network, publicKey, base58Address);
        AccountEntity accountEntity = new AccountEntity();
        accountEntity.setChain(name());
        accountEntity.setEnv(Env.getCurrent());
        Date time = new Date();
        accountEntity.setCreateTime(time);
        accountEntity.setName(name());
        accountEntity.setAddress(account.getAddress());
        accountEntity.setState(State.NORMAL);
        accountEntity.setIcon("");
        accountEntity.setPublicKey(account.getPublicKey());
        accountEntity.setUpdateTime(time);
        accountEntity.setValue(account.getAddress());
        accountEntity.setWallet(wallet.getId());
        return accountEntity;
    }

    @Override
    public AccountEntity importAccountByMnemonic(WalletEntity entity, String publicKey, Object... objects) throws Exception {
        throw new IllegalArgumentException("Mnemonic import is not supported");
    }
}
