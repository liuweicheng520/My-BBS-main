package com.my.bbs.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.my.bbs.dao.BBSPostMapper;
import com.my.bbs.dao.BBSUserMapper;
import com.my.bbs.entity.BBSPost;
import com.my.bbs.entity.BBSUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

import com.my.bbs.dao.TbPostCommentMapper;
import com.my.bbs.entity.TbPostComment;
import com.my.bbs.service.TbPostCommentService;

@Service
public class TbPostCommentServiceImpl extends ServiceImpl<TbPostCommentMapper, TbPostComment>  implements TbPostCommentService {
    @Autowired
    private TbPostCommentMapper tbPostCommentMapper;
    @Autowired
    private BBSPostMapper bbsPostMapper;

    @Autowired
    private BBSUserMapper bbsUserMapper;
    @Override
    public TbPostComment findById(Long commentId) {
        return tbPostCommentMapper.findById(commentId);
    }

    @Override
    public TbPostComment findByMap(Map<String, Object> map) {
        return tbPostCommentMapper.findByMap(map);
    }

    @Override
    public List<TbPostComment> list(Map<String, Object> map) {
        return tbPostCommentMapper.list(map);
    }

    @Override
    public int saved(TbPostComment tbPostComment) {
        BBSPost bbsPost = bbsPostMapper.selectByPrimaryKey(tbPostComment.getPostId());
        if (bbsPost == null) {
            return 0;
        }
        BBSUser bbsUser = bbsUserMapper.selectByPrimaryKey(tbPostComment.getCommentUserId());

        if (bbsUser == null || bbsUser.getUserStatus().intValue() == 1) {
            //账号已被封禁
            return 0;
        }
        bbsPost.setPostComments(bbsPost.getPostComments() + 1);
        bbsPostMapper.updateByPrimaryKeySelective(bbsPost);

        return tbPostCommentMapper.save(tbPostComment);
    }

    @Override
    public int update(TbPostComment tbPostComment) {
        return tbPostCommentMapper.updatez(tbPostComment);
    }

    @Override
    public int delete(Long commentId) {
        return tbPostCommentMapper.deletez(commentId);
    }

    @Override
    public int batchDelete(Long[] commentIds) {
        return tbPostCommentMapper.batchDelete(commentIds);
    }

}
