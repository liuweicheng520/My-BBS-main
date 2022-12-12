package com.my.bbs.controller.rest;

import com.microsoft.azure.cognitiveservices.vision.contentmoderator.models.Screen;
import com.my.bbs.common.Constants;
import com.my.bbs.common.ServiceResultEnum;
import com.my.bbs.config.ContentModeratorClientUtil;
import com.my.bbs.config.TextAnalyticsClientUtil;
import com.my.bbs.config.TranslatorUtil;
import com.my.bbs.entity.BBSPostComment;
import com.my.bbs.entity.BBSUser;
import com.my.bbs.entity.TbPostComment;
import com.my.bbs.service.BBSPostCommentService;
import com.my.bbs.service.TbPostCommentService;
import com.my.bbs.util.Result;
import com.my.bbs.util.ResultGenerator;
import io.swagger.annotations.ApiOperation;
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
@ApiOperation("PostComment")
public class BBSPostCommentController {

    @Resource
    private BBSPostCommentService bbsPostCommentService;
    @Autowired
    private TbPostCommentService tbPostCommentService;
    @Autowired
    private TextAnalyticsClientUtil analyticsClientUtil;
    @Autowired
    private ContentModeratorClientUtil contentModeratorClientUtil;

    @PostMapping("/replyPost")
    @ApiOperation("replyPost")
    @ResponseBody
    public Result replyPost(@RequestParam("postId") Long postId,
                            @RequestParam(value = "parentCommentUserId", required = false) Long parentCommentUserId,
                            @RequestParam("commentBody") String commentBody,
                            @RequestParam("verifyCode") String verifyCode,
                            HttpSession httpSession) throws IOException {
        if (null == postId || postId < 0) {
            return ResultGenerator.genFailResult("The postId parameter is incorrect");
        }
        if (!StringUtils.hasLength(commentBody)) {
            return ResultGenerator.genFailResult("The commentBody parameter is incorrect");
        }
        if (commentBody.trim().length() > 200) {
            return ResultGenerator.genFailResult("The comments are too long");
        }
        String kaptchaCode = httpSession.getAttribute(Constants.VERIFY_CODE_KEY) + "";
        if (!StringUtils.hasLength(kaptchaCode) || !verifyCode.equals(kaptchaCode)) {
            return ResultGenerator.genFailResult(ServiceResultEnum.LOGIN_VERIFY_CODE_ERROR.getResult());

        }
        Screen screen = contentModeratorClientUtil.reviewText(commentBody.trim());
        if (screen.terms() != null && screen.terms().size() > 0) {
            return ResultGenerator.genFailResult("contains profanity !  suspected: " + screen.terms().get(0).term());
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
            return ResultGenerator.genFailResult("The request failed. Please check whether the parameters and account have operation rights");
        }
    }


    @PostMapping("/delReply/{commentId}")
    @ApiOperation("deleteReply")
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
            return ResultGenerator.genFailResult("The request failed. Please check whether the parameters and account have operation rights");
        }
    }
}
