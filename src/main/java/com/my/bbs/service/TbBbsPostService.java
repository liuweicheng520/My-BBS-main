package com.my.bbs.service;

import com.my.bbs.entity.TbBbsPost;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;
import java.util.Map;

/**
 * 
 *
 * @author liuy
 * @email liuy
 * @date 2022-12-09 14:28:25
 */
public interface TbBbsPostService  extends IService<TbBbsPost> {

	TbBbsPost findById(Long postId);

	TbBbsPost findByMap(Map<String, Object> map);

	List<TbBbsPost> list(Map<String, Object> map);

	int saved(TbBbsPost tbBbsPost);

	int update(TbBbsPost tbBbsPost);

	int delete(Long postId);

	int batchDelete(Long[] postIds);
}
