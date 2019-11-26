package top.andnux.chain.eos;

import java.security.SecureRandom;

import io.github.novacrypto.bip39.MnemonicGenerator;
import io.github.novacrypto.bip39.Words;
import io.github.novacrypto.bip39.wordlists.English;
import io.github.novacrypto.bip44.Account;
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
    public AccountEntity createAccount(WalletEntity entity, String password, Object... objects) throws Exception {
        String[] pathArray = "m/44'/194'/0'/0/0".split("/");
        StringBuilder sb = new StringBuilder();
        byte[] entropy = new byte[Words.TWELVE.byteLength()];
        SecureRandom secureRandom = new SecureRandom();
        secureRandom.nextBytes(entropy);
        new MnemonicGenerator(English.INSTANCE).createMnemonic(entropy, sb::append);
        String mnemonics = sb.toString();
        String privateKey = Ecc.seedPrivate(mnemonics);
        return null;
    }

    @Override
    public AccountEntity createAccountByMnemonic(WalletEntity entity, String password, Object... objects) throws Exception {
        return null;
    }

    @Override
    public AccountEntity importAccountByPrivateKey(WalletEntity entity, String password, String privateKey, Object... objects) throws Exception {
        return null;
    }

    @Override
    public AccountEntity importAccountByPublicKey(WalletEntity entity, String password, String publicKey, Object... objects) throws Exception {
        return null;
    }

    @Override
    public AccountEntity importAccountByMnemonic(WalletEntity entity, String password, String publicKey, Object... objects) throws Exception {
        return null;
    }
}
