package top.andnux.chain.core.database.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import top.andnux.chain.core.database.entity.NodeEntity;
import top.andnux.chain.core.database.entity.WalletEntity;

@Dao
public interface NodeDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(NodeEntity... entities);

    @Delete()
    void delete(NodeEntity... entities);

    @Query("SELECT * from tb_node")
    List<NodeEntity> queryAll();

    @Update(onConflict = OnConflictStrategy.REPLACE)
    int update(NodeEntity... entities);
}
