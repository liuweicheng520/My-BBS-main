package com.my.bbs.controller.rest;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.microsoft.azure.cognitiveservices.vision.contentmoderator.ContentModeratorClient;
import com.microsoft.azure.cognitiveservices.vision.contentmoderator.ContentModeratorManager;
import com.microsoft.azure.cognitiveservices.vision.contentmoderator.models.AzureRegionBaseUrl;
import com.microsoft.azure.cognitiveservices.vision.contentmoderator.models.Screen;
import com.my.bbs.common.Constants;
import com.my.bbs.common.ServiceResultEnum;
import com.my.bbs.config.ContentModeratorClientUtil;
import com.my.bbs.entity.BBSPost;
import com.my.bbs.entity.BBSPostCategory;
import com.my.bbs.entity.BBSUser;
import com.my.bbs.entity.TbBbsPost;
import com.my.bbs.service.*;
import com.my.bbs.util.PageResult;
import com.my.bbs.util.Result;
import com.my.bbs.util.ResultGenerator;
import com.nimbusds.oauth2.sdk.ParseException;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.List;

@Controller
public class BBSPostPageController {
    @Resource
    private BBSPostService bbsPostService;
    @Resource
    private BBSPostCategoryService bbsPostCategoryService;
    @Resource
    private BBSUserService bbsUserService;
    @Resource
    private BBSPostCollectService bbsPostCollectService;
    @Resource
    private BBSPostCommentService bbsPostCommentService;
    @Autowired
    private TbBbsPostService tbBbsPostService;
    @Autowired
    private ContentModeratorClientUtil contentModeratorClientUtil;

    @GetMapping("detail/{postId}")
    public String postDetail(HttpServletRequest request, @PathVariable(value = "postId") Long postId,
                             @RequestParam(value = "commentPage", required = false, defaultValue = "1") Integer commentPage) {
        List<BBSPostCategory> bbsPostCategories = bbsPostCategoryService.getBBSPostCategories();
        if (CollectionUtils.isEmpty(bbsPostCategories)) {
            return "error/error_404";
        }
        //将分类数据封装到request域中
        request.setAttribute("bbsPostCategories", bbsPostCategories);

        // 帖子内容
        BBSPost bbsPost = bbsPostService.getBBSPostForDetail(postId);
        if (bbsPost == null) {
            return "error/error_404";
        }
        request.setAttribute("bbsPost", bbsPost);
        // 发帖用户信息
        BBSUser bbsUser = bbsUserService.getUserById(bbsPost.getPublishUserId());
        if (bbsUser == null) {
            return "error/error_404";
        }
        request.setAttribute("bbsUser", bbsUser);

        // 是否收藏了本贴
        BBSUser currentUser = (BBSUser) request.getSession().getAttribute(Constants.USER_SESSION_KEY);
        request.setAttribute("currentUserCollectFlag", bbsPostCollectService.validUserCollect(currentUser.getUserId(), postId));

        // 本周热议的帖子
        request.setAttribute("hotTopicBBSPostList", bbsPostService.getHotTopicBBSPostList());

        // 评论数据
        PageResult commentsPage = bbsPostCommentService.getCommentsByPostId(postId, commentPage);
        request.setAttribute("commentsPage", commentsPage);

        return "jie/detail";
    }

    @GetMapping("editPostPage/{postId}")
    public String editPostPage(HttpServletRequest request, @PathVariable(value = "postId") Long postId) {
        BBSUser bbsUser = (BBSUser) request.getSession().getAttribute(Constants.USER_SESSION_KEY);
        List<BBSPostCategory> bbsPostCategories = bbsPostCategoryService.getBBSPostCategories();
        if (CollectionUtils.isEmpty(bbsPostCategories)) {
            return "error/error_404";
        }
        //将分类数据封装到request域中
        request.setAttribute("bbsPostCategories", bbsPostCategories);
        if (null == postId || postId < 0) {
            return "error/error_404";
        }
        BBSPost bbsPost = bbsPostService.getBBSPostById(postId);
        if (bbsPost == null) {
            return "error/error_404";
        }
        if (!bbsUser.getUserId().equals(bbsPost.getPublishUserId())) {
            request.setAttribute("message", "非本人发帖，无权限修改");
            return "error/error";
        }
        request.setAttribute("bbsPost", bbsPost);
        request.setAttribute("postId", postId);
        return "jie/edit";
    }

    @GetMapping("/addPostPage")
    public String addPostPage(HttpServletRequest request) {
        List<BBSPostCategory> bbsPostCategories = bbsPostCategoryService.getBBSPostCategories();
        if (CollectionUtils.isEmpty(bbsPostCategories)) {
            return "error/error_404";
        }
        //将分类数据封装到request域中
        request.setAttribute("bbsPostCategories", bbsPostCategories);
        return "jie/add";
    }



    public static void main(String[] args) throws ParseException {
        ContentModeratorClient client = ContentModeratorManager.authenticate(AzureRegionBaseUrl.fromString("https://liuy24.cognitiveservices.azure.com/"),
                "3dba8ca6b2714351bbbd91e3a37408c4");
        Gson gson = new GsonBuilder().setPrettyPrinting().create();

    }
}
