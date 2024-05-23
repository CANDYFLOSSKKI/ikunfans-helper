package com.ctey.ikunfansconnect.Service;

import com.ctey.ikunfanscommon.Entity.Bangumi.SubjectPostItemData;
import com.ctey.ikunfanscommon.Resp.Bangumi.BangumiPostSubjectResp;
import com.ctey.ikunfanscommon.Util.CollectionEmptyUtil;
import com.ctey.ikunfanscommon.Util.DataParamParseUtil;
import com.ctey.ikunfanscommon.Util.ModelTransformUtil;
import com.ctey.ikunfansconnect.Config.BangumiFeignAPIConfig;
import feign.Feign;
import feign.Request;
import feign.jackson.JacksonDecoder;
import feign.jackson.JacksonEncoder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import static com.ctey.ikunfanscommon.Static.ConnectModuleStatic.BANGUMI_BASE_API;
import static com.ctey.ikunfanscommon.Static.WebModuleStatic.ASYNC_SERVICE_TIMEOUT;

@Component
public class BangumiService {
    @Qualifier("bangumiTaskAsyncPool")
    private final Executor bangumiTaskAsyncPool;
    private final BangumiFeignAPIConfig bangumiInstance = Feign.builder()
            .encoder(new JacksonEncoder())
            .decoder(new JacksonDecoder())
            .options(new Request.Options(10, TimeUnit.SECONDS, 10, TimeUnit.SECONDS, true))
            .target(BangumiFeignAPIConfig.class, BANGUMI_BASE_API);


    @Autowired
    public BangumiService(Executor bangumiTaskAsyncPool) {
        this.bangumiTaskAsyncPool = bangumiTaskAsyncPool;
    }

    public List<SubjectPostItemData> collectTargetSubjects(List<String> keywords) throws Exception{
        List<SubjectPostItemData> subjectPostItemDataList = new ArrayList<>();
        List<CompletableFuture<BangumiPostSubjectResp>> subjectSearchTaskList = new ArrayList<>();
        if (CollectionEmptyUtil.forList(keywords)) { return List.of(); }
        AtomicInteger index = new AtomicInteger(0);
        keywords.stream().forEach(keyword -> {
            subjectSearchTaskList.add(CompletableFuture.supplyAsync(() -> {
                try { Thread.sleep(index.getAndIncrement() * 1000L); }
                catch (Exception e) { e.printStackTrace(); }
                return bangumiInstance.searchSubjectsV0(ModelTransformUtil.getDefaultBangumiSubjectReq(keyword));
            }, bangumiTaskAsyncPool));
        });
        CompletableFuture.allOf(subjectSearchTaskList.toArray(CompletableFuture[]::new))
                         .get(ASYNC_SERVICE_TIMEOUT, TimeUnit.SECONDS);
        subjectSearchTaskList.stream().forEach(task -> { try {
                List<SubjectPostItemData> itemList = task.get().getData();
                if (CollectionEmptyUtil.forList(itemList)) { return; }
                subjectPostItemDataList.addAll(itemList.stream()
                    .filter(i -> i.getRank() != 0)
                    .limit(3)
                    .toList());
        } catch (Exception e) { e.printStackTrace(); }});
        if (CollectionEmptyUtil.forList(subjectPostItemDataList)) { return List.of(); }
        return subjectPostItemDataList.stream()
            .filter(DataParamParseUtil.modelDistinctByKey(SubjectPostItemData::getId))
            .sorted(Comparator.comparingInt(SubjectPostItemData::getRank))
            .toList();
    }

}
