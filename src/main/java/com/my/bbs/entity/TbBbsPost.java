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
 * @date 2022-12-09 14:28:23
 */
@Getter
@Setter
@Table(name = "tb_bbs_post")
public class TbBbsPost implements Serializable {
	private static final long serialVersionUID = 1L;

	// 帖子主键id
	@Column(name = "post_id")
	@ApiModelProperty(name="postId",value="帖子主键id")
	private Long postId;

	// 发布者id
	@Column(name = "publish_user_id")
	@ApiModelProperty(name="publishUserId",value="发布者id")
	private Long publishUserId;

	// 帖子标题
	@Column(name = "post_title")
	@ApiModelProperty(name="postTitle",value="帖子标题")
	private String postTitle;

	// 帖子内容
	@Column(name = "post_content")
	@ApiModelProperty(name="postContent",value="帖子内容")
	private String postContent;

	// 帖子分类id
	@Column(name = "post_category_id")
	@ApiModelProperty(name="postCategoryId",value="帖子分类id")
	private Integer postCategoryId;

	// 帖子分类(冗余字段)
	@Column(name = "post_category_name")
	@ApiModelProperty(name="postCategoryName",value="帖子分类(冗余字段)")
	private String postCategoryName;

	// 0-未审核 1-审核通过 2-审核失败 (默认审核通过)
	@Column(name = "post_status")
	@ApiModelProperty(name="postStatus",value="0-未审核 1-审核通过 2-审核失败 (默认审核通过)")
	private Integer postStatus;

	// 阅读量
	@Column(name = "post_views")
	@ApiModelProperty(name="postViews",value="阅读量")
	private Long postViews;

	// 评论量
	@Column(name = "post_comments")
	@ApiModelProperty(name="postComments",value="评论量")
	private Long postComments;

	// 收藏量
	@Column(name = "post_collects")
	@ApiModelProperty(name="postCollects",value="收藏量")
	private Long postCollects;

	// 最新修改时间
	@Column(name = "last_update_time")
	@ApiModelProperty(name="lastUpdateTime",value="最新修改时间")
	private Date lastUpdateTime;

	// 添加时间
	@TableField(fill = FieldFill.INSERT)
	@Column(name = "create_time")
	@ApiModelProperty(name="createTime",value="添加时间")
	private Date createTime;

	// 
	@Column(name = "media_url")
	@ApiModelProperty(name="mediaUrl",value="")
	private String mediaUrl;

	// 
	@TableLogic
	@Column(name = "is_del")
	@ApiModelProperty(name="isDel",value="")
	private Integer isDel;


}
