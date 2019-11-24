package top.andnux.chain.core.database.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import top.andnux.chain.core.database.entity.WalletEntity;

@Dao
public interface WalletDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(WalletEntity... entities);

    @Delete()
    void delete(WalletEntity... entities);

    @Query("SELECT * from tb_wallet")
    List<WalletEntity> queryAll();

    @Query("SELECT * from tb_wallet where current=='true'")
    WalletEntity queryCurrent();

    @Update(onConflict = OnConflictStrategy.REPLACE)
    int update(WalletEntity... entities);
}
