package com.my.bbs.config;

import com.alibaba.fastjson.JSON;
import com.azure.ai.textanalytics.TextAnalyticsClient;
import com.azure.ai.textanalytics.TextAnalyticsClientBuilder;
import com.azure.ai.textanalytics.models.SentimentConfidenceScores;
import com.azure.core.credential.AzureKeyCredential;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.microsoft.azure.cognitiveservices.vision.contentmoderator.ContentModeratorClient;
import com.microsoft.azure.cognitiveservices.vision.contentmoderator.ContentModeratorManager;
import com.microsoft.azure.cognitiveservices.vision.contentmoderator.models.AzureRegionBaseUrl;
import org.springframework.stereotype.Component;

/**
 * @Author liuweicheng
 * @Date 2022/12/9
 **/
@Component
public class TextAnalyticsClientUtil {

    private TextAnalyticsClient textAnalyticsClient = new TextAnalyticsClientBuilder()
            .credential(new AzureKeyCredential("862020ce3e0f4e8197eb4a9ce9175151"))
            .endpoint("https://liuy245.cognitiveservices.azure.com/")
            .buildClient();

    public TextAnalyticsClient getTextAnalyticsClient() {
        if (textAnalyticsClient == null) {
            textAnalyticsClient = new TextAnalyticsClientBuilder()
                    .credential(new AzureKeyCredential("862020ce3e0f4e8197eb4a9ce9175151"))
                    .endpoint("https://liuy245.cognitiveservices.azure.com/")
                    .buildClient();
        } else {
            return textAnalyticsClient;
        }
        return textAnalyticsClient;
    }


    /**
     *  analyzeSentiment
     * @param content textContent
     * @return 1 = positive; 0 = neutral; -1 = negative
     */
    public Integer analyzeSentiment(String content) {

        SentimentConfidenceScores sentimentConfidenceScores = textAnalyticsClient.analyzeSentiment(content).getConfidenceScores();
        final double negativeScore = sentimentConfidenceScores.getNegative();
        final double neutralScore = sentimentConfidenceScores.getNeutral();
        final double positiveScore = sentimentConfidenceScores.getPositive();

        if (positiveScore > negativeScore && positiveScore > neutralScore) {
            return 1;
        }
        if (neutralScore > positiveScore && neutralScore > negativeScore) {
            return 0;
        }
        if (negativeScore > positiveScore && negativeScore > neutralScore) {
            return -1;
        }
        return 0;
    }


    public static void main(String[] args) {
        TextAnalyticsClient textAnalyticsClient = new TextAnalyticsClientBuilder()
                .credential(new AzureKeyCredential("862020ce3e0f4e8197eb4a9ce9175151"))
                .endpoint("https://liuy245.cognitiveservices.azure.com/")
                .buildClient();
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        SentimentConfidenceScores sentimentConfidenceScores = textAnalyticsClient.analyzeSentiment("很好,非常棒,我喜欢").getConfidenceScores();
        System.out.println(JSON.toJSON(sentimentConfidenceScores));
    }
}
