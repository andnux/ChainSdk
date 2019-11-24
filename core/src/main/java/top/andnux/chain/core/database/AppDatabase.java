package top.andnux.chain.core.database;

import androidx.room.Database;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import top.andnux.chain.core.database.dao.AccountDao;
import top.andnux.chain.core.database.dao.NodeDao;
import top.andnux.chain.core.database.dao.WalletDao;
import top.andnux.chain.core.database.entity.AccountEntity;
import top.andnux.chain.core.database.entity.NodeEntity;
import top.andnux.chain.core.database.entity.WalletEntity;

@Database(entities = {
        AccountEntity.class,
        WalletEntity.class,
        NodeEntity.class
}, version = 1)
@TypeConverters({Converters.class})
public abstract class AppDatabase extends RoomDatabase {

    public abstract AccountDao accountDao();

    public abstract WalletDao walletDap();
    
    public abstract NodeDao nodeDao();

}