package top.andnux.chain.core;

import top.andnux.chain.core.database.entity.AccountEntity;

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
     * @param objects
     * @return
     */
    AccountEntity createAccount(Object... objects);

    /**
     * 私钥导入
     *
     * @param privateKey
     * @param objects
     * @return
     */
    AccountEntity importAccountByPrivateKey(String privateKey, Object... objects);

    /**
     * 公钥导入
     *
     * @param publicKey
     * @return
     */
    AccountEntity importAccountByPublicKey(String publicKey, Object... objects);


    /**
     * 助记词导入
     *
     * @param publicKey
     * @param objects
     * @return
     */
    AccountEntity importAccountByMnemonic(String publicKey, Object... objects);
}
