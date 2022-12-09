package com.my.bbs.dao;
import com.my.bbs.entity.TbPostComment;

import java.util.List;
import java.util.Map;
import org.apache.ibatis.annotations.Mapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * 
 *
 * @author liuy
 * @email liuy
 * @date 2022-12-09 17:50:03
 */
@Mapper
public interface TbPostCommentMapper extends BaseMapper<TbPostComment> {

    TbPostComment findById(Long commentId);

    TbPostComment findByMap(Map<String, Object> map);

    List<TbPostComment> list(Map<String, Object> map);

    int save(TbPostComment tbPostComment);

    int updatez(TbPostComment tbPostComment);

    int deletez(Long comment_id);

    int batchDelete(Long[] commentIds);


}
