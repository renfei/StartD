package net.renfei.startd.inf.dao;

import java.util.List;
import net.renfei.startd.inf.model.LeafAlloc;
import net.renfei.startd.inf.model.LeafAllocExample;
import org.apache.ibatis.annotations.*;

@Mapper
public interface LeafAllocMapper {
    long countByExample(LeafAllocExample example);

    int deleteByExample(LeafAllocExample example);

    int deleteByPrimaryKey(String bizTag);

    int insert(LeafAlloc record);

    int insertSelective(LeafAlloc record);

    List<LeafAlloc> selectByExample(LeafAllocExample example);

    LeafAlloc selectByPrimaryKey(String bizTag);

    int updateByExampleSelective(@Param("record") LeafAlloc record, @Param("example") LeafAllocExample example);

    int updateByExample(@Param("record") LeafAlloc record, @Param("example") LeafAllocExample example);

    int updateByPrimaryKeySelective(LeafAlloc record);

    int updateByPrimaryKey(LeafAlloc record);

    @Update("UPDATE leaf_alloc SET max_id = max_id + #{step} WHERE biz_tag = #{key}")
    void updateMaxIdByCustomStep(@Param("leafAlloc") LeafAlloc leafAlloc);

    @Update("UPDATE leaf_alloc SET max_id = max_id + step WHERE biz_tag = #{tag}")
    void updateMaxId(@Param("tag") String tag);
}