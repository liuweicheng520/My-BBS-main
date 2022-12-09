package com.my.bbs.entity;

import javax.persistence.Column;
import java.util.Date;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import com.baomidou.mybatisplus.annotation.*;
import java.io.Serializable;
import java.math.BigDecimal;
import lombok.Getter;
import lombok.Setter;
import io.swagger.annotations.ApiModelProperty;


/**
 * 
 *
 * @date 2022-12-09 17:57:27
 */
@Getter
@Setter
@Table(name = "tb_post_comment")
public class TbPostComment implements Serializable {
	private static final long serialVersionUID = 1L;

	// 主键id
	@Column(name = "comment_id")
	@ApiModelProperty(name="commentId",value="主键id")
	private Long commentId;

	// 关联的帖子主键
	@Column(name = "post_id")
	@ApiModelProperty(name="postId",value="关联的帖子主键")
	private Long postId;

	// 评论内容
	@Column(name = "comment_body")
	@ApiModelProperty(name="commentBody",value="评论内容")
	private String commentBody;

	// 评论者id
	@Column(name = "comment_user_id")
	@ApiModelProperty(name="commentUserId",value="评论者id")
	private Long commentUserId;

	// 所回复的上一级评论的userId
	@Column(name = "parent_comment_user_id")
	@ApiModelProperty(name="parentCommentUserId",value="所回复的上一级评论的userId")
	private Long parentCommentUserId;

	// 评论时间
	@Column(name = "comment_create_time")
	@ApiModelProperty(name="commentCreateTime",value="评论时间")
	private Date commentCreateTime;

	// 是否删除 0-未删除 1-已删除
	@Column(name = "is_deleted")
	@ApiModelProperty(name="isDeleted",value="是否删除 0-未删除 1-已删除")
	private Integer isDeleted;

	// positive:1,neutral:0,negative:-1
	@Column(name = "sentiment")
	@ApiModelProperty(name="sentiment",value="positive:1,neutral:0,negative:-1")
	private Integer sentiment;

	// 
	@Column(name = "del_flag")
	@ApiModelProperty(name="delFlag",value="")
	private Integer delFlag;


}
