package com.my.bbs.config;

import com.microsoft.azure.cognitiveservices.vision.contentmoderator.ContentModeratorClient;
import com.microsoft.azure.cognitiveservices.vision.contentmoderator.ContentModeratorManager;
import com.microsoft.azure.cognitiveservices.vision.contentmoderator.models.AzureRegionBaseUrl;
import com.microsoft.azure.cognitiveservices.vision.contentmoderator.models.Screen;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @Author liuweicheng
 * @Date 2022/12/9
 **/
@Component
public class ContentModeratorClientUtil {

    private ContentModeratorClient contentModeratorClient = ContentModeratorManager.authenticate(AzureRegionBaseUrl.fromString("https://liuy24.cognitiveservices.azure.com/"),
            "3dba8ca6b2714351bbbd91e3a37408c4");

    public ContentModeratorClient getContentModeratorClient() {
        if (contentModeratorClient == null) {
            contentModeratorClient = ContentModeratorManager.authenticate(AzureRegionBaseUrl.fromString("https://liuy24.cognitiveservices.azure.com/"),
                    "3dba8ca6b2714351bbbd91e3a37408c4");
        } else {
            return contentModeratorClient;
        }
        return contentModeratorClient;
    }

    public Screen reviewText(String text) {
        return contentModeratorClient.textModerations().screenText("text/plain", new String(text).getBytes(), null);
    }

}
