package com.my.bbs.service;

import com.my.bbs.entity.TbPostComment;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;
import java.util.Map;

/**
 * 
 *
 * @author liuy
 * @email liuy
 * @date 2022-12-09 17:50:04
 */
public interface TbPostCommentService  extends IService<TbPostComment> {

	TbPostComment findById(Long commentId);

	TbPostComment findByMap(Map<String, Object> map);

	List<TbPostComment> list(Map<String, Object> map);

	int saved(TbPostComment tbPostComment);

	int update(TbPostComment tbPostComment);

	int delete(Long commentId);

	int batchDelete(Long[] commentIds);
}
