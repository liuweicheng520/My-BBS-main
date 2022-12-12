package com.my.bbs.entity;

import lombok.Data;

import java.util.Date;

/**
 * 帖子-实体类
 */
@Data
public class BBSPost {
    private Long postId;

    private Long publishUserId;

    private String postTitle;

    private Integer postCategoryId;

    private String postCategoryName;

    private Byte postStatus;

    private Long postViews;

    private Long postComments;

    private Long postCollects;

    private Date lastUpdateTime;

    private Date createTime;

    private String postContent;

    private String mediaUrl;





    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", postId=").append(postId);
        sb.append(", publishUserId=").append(publishUserId);
        sb.append(", postTitle=").append(postTitle);
        sb.append(", postCategoryId=").append(postCategoryId);
        sb.append(", postCategoryName=").append(postCategoryName);
        sb.append(", postStatus=").append(postStatus);
        sb.append(", postViews=").append(postViews);
        sb.append(", postComments=").append(postComments);
        sb.append(", postCollects=").append(postCollects);
        sb.append(", lastUpdateTime=").append(lastUpdateTime);
        sb.append(", createTime=").append(createTime);
        sb.append(", postContent=").append(postContent);
        sb.append("]");
        return sb.toString();
    }
}
