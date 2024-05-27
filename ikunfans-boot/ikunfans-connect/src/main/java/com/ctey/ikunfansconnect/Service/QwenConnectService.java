package com.ctey.ikunfansconnect.Service;

import com.alibaba.dashscope.aigc.generation.Generation;
import com.alibaba.dashscope.aigc.generation.GenerationResult;
import com.alibaba.dashscope.aigc.generation.models.QwenParam;
import com.alibaba.dashscope.common.Message;
import com.alibaba.dashscope.common.Role;
import com.alibaba.dashscope.utils.Constants;
import io.reactivex.Flowable;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Component;

import java.util.Arrays;

import static com.ctey.ikunfanscommon.Static.ConnModuleStatic.*;

@Component
public class QwenConnectService {
    private final Generation qwenGen = new Generation();

    @PostConstruct
    public void initQwenConnectParam() {
        Constants.apiKey = ALIYUN_APPKEY;
    }

    public String streamCallWithMessage(String content) {
        Message userMsg = Message.builder()
                        .role(Role.USER.getValue())
                        .content(ALIYUN_CONTENT_PREFIX + content)
                        .build();
        QwenParam param = QwenParam.builder()
                        .model(ALIYUN_MODEL)
                        .messages(Arrays.asList(userMsg))
                        .resultFormat(QwenParam.ResultFormat.MESSAGE)
                        .topP(0.8)
                        .incrementalOutput(true)
                        .build();
       try {
           Flowable<GenerationResult> result = qwenGen.streamCall(param);
           StringBuilder fullContent = new StringBuilder();
           result.blockingForEach(item -> {
               fullContent.append(item.getOutput().getChoices().get(0).getMessage().getContent());
           });
           return fullContent.toString();
       } catch (Exception e) { e.printStackTrace(); }
       return null;
    }

    public String resultCallWithMessage(String content) {
        Message userMsg = Message.builder()
                        .role(Role.USER.getValue())
                        .content(ALIYUN_CONTENT_PREFIX + content)
                        .build();
        QwenParam param = QwenParam.builder()
                        .model(ALIYUN_MODEL)
                        .messages(Arrays.asList(userMsg))
                        .resultFormat(QwenParam.ResultFormat.MESSAGE)
                        .topP(0.8)
                        .build();
        try {
            GenerationResult result = qwenGen.call(param);
            return result.getOutput().getText();
        } catch (Exception e) { e.printStackTrace(); }
        return null;
    }

}
