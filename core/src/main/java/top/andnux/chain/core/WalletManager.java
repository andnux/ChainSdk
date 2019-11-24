package top.andnux.chain.core;

import java.util.List;

import top.andnux.chain.core.database.DatabaseManager;
import top.andnux.chain.core.database.dao.AccountDao;
import top.andnux.chain.core.database.dao.WalletDao;
import top.andnux.chain.core.database.entity.AccountEntity;
import top.andnux.chain.core.database.entity.WalletEntity;

public class WalletManager {

    private static final WalletManager ourInstance = new WalletManager();
    private WalletEntity mCurrentWallet;
    private AccountEntity mCurrentAccount;
    private final WalletDao walletDao;
    private final AccountDao accountDao;

    public static WalletManager getInstance() {
        return ourInstance;
    }

    private WalletManager() {
        walletDao = DatabaseManager.getInstance().getAppDatabase().walletDap();
        accountDao = DatabaseManager.getInstance().getAppDatabase().accountDao();
    }

    public WalletEntity getCurrentWallet() {
        mCurrentWallet = walletDao.queryCurrent();
        return mCurrentWallet;
    }

    public void setCurrentWallet(WalletEntity currentWallet) {
        mCurrentWallet = currentWallet;
        List<WalletEntity> list = walletDao.queryAll();
        for (WalletEntity entity : list) {
            entity.setCurrent(false);
        }
        walletDao.update(list.toArray(new WalletEntity[0]));
        currentWallet.setCurrent(true);
        walletDao.update(currentWallet);
    }

    public AccountEntity getCurrentAccount() {
        WalletEntity currentWallet = getCurrentWallet();
        if (currentWallet == null) return null;
        mCurrentAccount = accountDao.queryById(currentWallet.getMainAccount());
        return mCurrentAccount;
    }

    public void setCurrentAccount(AccountEntity currentAccount) {
        if (getCurrentWallet() == null) return;
        WalletEntity currentWallet = getCurrentWallet();
        if (currentAccount == null) return;
        currentWallet.setMainAccount(currentAccount.getId());
        walletDao.update(currentWallet);
        mCurrentAccount = currentAccount;
    }
}
