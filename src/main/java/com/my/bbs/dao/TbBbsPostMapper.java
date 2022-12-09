package com.my.bbs.dao;
import com.my.bbs.entity.TbBbsPost;

import java.util.List;
import java.util.Map;
import org.apache.ibatis.annotations.Mapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * 
 *
 * @author liuy
 * @email liuy
 * @date 2022-12-09 14:28:23
 */
@Mapper
public interface TbBbsPostMapper extends BaseMapper<TbBbsPost> {

    TbBbsPost findById(Long postId);

    TbBbsPost findByMap(Map<String, Object> map);

    List<TbBbsPost> list(Map<String, Object> map);

    int save(TbBbsPost tbBbsPost);

    int updatez(TbBbsPost tbBbsPost);

    int deletez(Long post_id);

    int batchDelete(Long[] postIds);


}
