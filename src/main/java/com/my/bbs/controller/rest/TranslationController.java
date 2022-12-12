package com.my.bbs.controller.rest;

import com.microsoft.azure.cognitiveservices.vision.contentmoderator.models.Screen;
import com.my.bbs.common.Constants;
import com.my.bbs.common.ServiceResultEnum;
import com.my.bbs.config.ContentModeratorClientUtil;
import com.my.bbs.config.TextAnalyticsClientUtil;
import com.my.bbs.config.TranslatorUtil;
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
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Date;

/**
 * @author liuy24
 */
@Controller
@RequestMapping("/translation")
@ApiOperation("/translation")
public class TranslationController {


    @GetMapping("/get")
    @ApiOperation("translation")
    @ResponseBody
    public Result translation(String content) throws IOException {
        if (!StringUtils.hasLength(content)) {
            return ResultGenerator.genFailResult("The content parameter is incorrect");
        }
        return ResultGenerator.genSuccessResult(TranslatorUtil.translate(content));
    }
}

