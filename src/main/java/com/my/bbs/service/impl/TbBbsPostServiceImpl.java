package com.my.bbs.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

import com.my.bbs.dao.TbBbsPostMapper;
import com.my.bbs.entity.TbBbsPost;
import com.my.bbs.service.TbBbsPostService;

@Service
public class TbBbsPostServiceImpl extends ServiceImpl<TbBbsPostMapper, TbBbsPost>  implements TbBbsPostService {
    @Autowired
    private TbBbsPostMapper tbBbsPostMapper;

    @Override
    public TbBbsPost findById(Long postId) {
        return tbBbsPostMapper.findById(postId);
    }

    @Override
    public TbBbsPost findByMap(Map<String, Object> map) {
        return tbBbsPostMapper.findByMap(map);
    }

    @Override
    public List<TbBbsPost> list(Map<String, Object> map) {
        return tbBbsPostMapper.list(map);
    }

    @Override
    public int saved(TbBbsPost tbBbsPost) {
        return tbBbsPostMapper.save(tbBbsPost);
    }

    @Override
    public int update(TbBbsPost tbBbsPost) {
        return tbBbsPostMapper.updatez(tbBbsPost);
    }

    @Override
    public int delete(Long postId) {
        return tbBbsPostMapper.deletez(postId);
    }

    @Override
    public int batchDelete(Long[] postIds) {
        return tbBbsPostMapper.batchDelete(postIds);
    }

}
