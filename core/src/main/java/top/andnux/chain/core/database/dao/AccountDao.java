package top.andnux.chain.core.database.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import top.andnux.chain.core.database.entity.AccountEntity;

@Dao
public interface AccountDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(AccountEntity... entities);

    @Delete()
    void delete(AccountEntity... entities);

    @Query("SELECT * from tb_account")
    List<AccountEntity> query();

    @Query("SELECT * from tb_account where id==:id")
    AccountEntity queryById(Long id);

    @Update(onConflict = OnConflictStrategy.REPLACE)
    int update(AccountEntity... entities);

}
