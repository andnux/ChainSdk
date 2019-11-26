package top.andnux.chain.eth;


import android.os.FileUtils;

import org.bitcoinj.crypto.ChildNumber;
import org.bitcoinj.crypto.DeterministicKey;
import org.bitcoinj.crypto.HDKeyDerivation;
import org.web3j.crypto.Credentials;
import org.web3j.crypto.ECKeyPair;
import org.web3j.crypto.MnemonicUtils;
import org.web3j.crypto.WalletUtils;

import java.io.File;
import java.security.SecureRandom;
import top.andnux.chain.core.utils.FilesUtils;
import io.github.novacrypto.bip39.MnemonicGenerator;
import io.github.novacrypto.bip39.Words;
import io.github.novacrypto.bip39.wordlists.English;
import io.github.novacrypto.bip44.Account;
import io.github.novacrypto.bip44.BIP44;
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
    public AccountEntity createAccount(WalletEntity entity, String password, Object... objects) throws Exception {
        String[] pathArray = "m/44'/60'/0'/0/0".split("/");
        StringBuilder sb = new StringBuilder();
        byte[] entropy = new byte[Words.TWELVE.byteLength()];
        SecureRandom secureRandom = new SecureRandom();
        secureRandom.nextBytes(entropy);
        new MnemonicGenerator(English.INSTANCE).createMnemonic(entropy, sb::append);
        String mnemonics = sb.toString();
        byte[] seedBytes = MnemonicUtils.generateSeed(mnemonics, "");
        if (seedBytes == null) {
            return null;
        }
        //  衍生推倒key
        DeterministicKey dkKey = HDKeyDerivation.createMasterPrivateKey(seedBytes);
        for (int i = 1; i < pathArray.length; i++) {
            ChildNumber childNumber;
            if (pathArray[i].endsWith("'")) {
                int number = Integer.parseInt(pathArray[i].substring(0,
                        pathArray[i].length() - 1));
                childNumber = new ChildNumber(number, true);
            } else {
                int number = Integer.parseInt(pathArray[i]);
                childNumber = new ChildNumber(number, false);
            }
            dkKey = HDKeyDerivation.deriveChildKey(dkKey, childNumber);
        }
        ECKeyPair keyPair = ECKeyPair.create(dkKey.getPrivKeyBytes());
        File file = (File) objects[0];
        String walletFile = WalletUtils.generateWalletFile(password, keyPair, file, false);
        String keystore = FileUtils.readFileString(new File(file, walletFile).getAbsolutePath());
        Credentials credentials = WalletUtils.loadCredentials(password, new File(file, walletFile));
        String address = credentials.getAddress();
        return null;
    }

    @Override
    public AccountEntity createAccountByMnemonic(WalletEntity entity,String password,  Object... objects) throws Exception {
        return null;
    }

    @Override
    public AccountEntity importAccountByPrivateKey(WalletEntity entity,String password,  String privateKey, Object... objects) throws Exception {
        return null;
    }

    @Override
    public AccountEntity importAccountByPublicKey(WalletEntity entity,String password,  String publicKey, Object... objects) throws Exception {
        return null;
    }

    @Override
    public AccountEntity importAccountByMnemonic(WalletEntity entity,String password,  String publicKey, Object... objects) throws Exception {
        return null;
    }
}
