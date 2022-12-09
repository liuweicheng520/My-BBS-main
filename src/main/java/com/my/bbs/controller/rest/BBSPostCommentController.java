package com.my.bbs.controller.rest;

import com.my.bbs.common.Constants;
import com.my.bbs.common.ServiceResultEnum;
import com.my.bbs.config.TextAnalyticsClientUtil;
import com.my.bbs.config.TranslatorUtil;
import com.my.bbs.entity.BBSPostComment;
import com.my.bbs.entity.BBSUser;
import com.my.bbs.entity.TbPostComment;
import com.my.bbs.service.BBSPostCommentService;
import com.my.bbs.service.TbPostCommentService;
import com.my.bbs.util.Result;
import com.my.bbs.util.ResultGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Date;

@Controller
public class BBSPostCommentController {

    @Resource
    private BBSPostCommentService bbsPostCommentService;
    @Autowired
    private TbPostCommentService tbPostCommentService;
    @Autowired
    private TextAnalyticsClientUtil analyticsClientUtil;

    @PostMapping("/replyPost")
    @ResponseBody
    public Result replyPost(@RequestParam("postId") Long postId,
                            @RequestParam(value = "parentCommentUserId", required = false) Long parentCommentUserId,
                            @RequestParam("commentBody") String commentBody,
                            @RequestParam("verifyCode") String verifyCode,
                            HttpSession httpSession) throws IOException {
        if (null == postId || postId < 0) {
            return ResultGenerator.genFailResult("postId参数错误");
        }
        if (!StringUtils.hasLength(commentBody)) {
            return ResultGenerator.genFailResult("commentBody参数错误");
        }
        if (commentBody.trim().length() > 200) {
            return ResultGenerator.genFailResult("评论内容过长");
        }
        String kaptchaCode = httpSession.getAttribute(Constants.VERIFY_CODE_KEY) + "";
        if (!StringUtils.hasLength(kaptchaCode) || !verifyCode.equals(kaptchaCode)) {
            return ResultGenerator.genFailResult(ServiceResultEnum.LOGIN_VERIFY_CODE_ERROR.getResult());
        }
        BBSUser bbsUser = (BBSUser) httpSession.getAttribute(Constants.USER_SESSION_KEY);

        System.out.println(TranslatorUtil.translate(commentBody));
        TbPostComment bbsPostComment = new TbPostComment();
        bbsPostComment.setCommentBody(TranslatorUtil.translate(commentBody));
        bbsPostComment.setCommentUserId(bbsUser.getUserId());
        bbsPostComment.setParentCommentUserId(parentCommentUserId);
        bbsPostComment.setPostId(postId);
        bbsPostComment.setSentiment(analyticsClientUtil.analyzeSentiment(bbsPostComment.getCommentBody()));
        bbsPostComment.setCommentCreateTime(new Date());

        if (tbPostCommentService.saved(bbsPostComment) > 0) {
            httpSession.removeAttribute(Constants.VERIFY_CODE_KEY);
            return ResultGenerator.genSuccessResult();
        } else {
            return ResultGenerator.genFailResult("请求失败，请检查参数及账号是否有操作权限");
        }
    }


    @PostMapping("/delReply/{commentId}")
    @ResponseBody
    public Result delReply(@PathVariable("commentId") Long commentId,
                           HttpSession httpSession) {

        if (null == commentId || commentId < 0) {
            return ResultGenerator.genFailResult("commentId参数错误");
        }

        BBSUser bbsUser = (BBSUser) httpSession.getAttribute(Constants.USER_SESSION_KEY);

        if (bbsPostCommentService.delPostComment(commentId, bbsUser.getUserId())) {
            return ResultGenerator.genSuccessResult();
        } else {
            return ResultGenerator.genFailResult("请求失败，请检查参数及账号是否有操作权限");
        }
    }
}
