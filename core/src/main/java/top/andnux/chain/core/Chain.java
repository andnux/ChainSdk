package top.andnux.chain.core;

import top.andnux.chain.core.database.entity.AccountEntity;
import top.andnux.chain.core.database.entity.WalletEntity;

public interface Chain {

    Measure getMeasure();

    /**
     * 链名字
     *
     * @return
     */
    String name();

    /**
     * 创建帐号
     *
     * @param password
     * @param objects
     * @return
     */
    AccountEntity createAccount(WalletEntity entity, String password, Object... objects) throws Exception;


    /**
     * 创建帐号用助记词
     *
     * @param objects
     * @return
     */
    AccountEntity createAccountByMnemonic(WalletEntity entity, String password, Object... objects) throws Exception;

    /**
     * 私钥导入
     *
     * @param privateKey
     * @param objects
     * @return
     */
    AccountEntity importAccountByPrivateKey(WalletEntity entity, String password, String privateKey, Object... objects) throws Exception;

    /**
     * 公钥导入
     *
     * @param publicKey
     * @return
     */
    AccountEntity importAccountByPublicKey(WalletEntity entity, String password, String publicKey, Object... objects) throws Exception;


    /**
     * 助记词导入
     *
     * @param publicKey
     * @param objects
     * @return
     */
    AccountEntity importAccountByMnemonic(WalletEntity entity, String password, String publicKey, Object... objects) throws Exception;
}
