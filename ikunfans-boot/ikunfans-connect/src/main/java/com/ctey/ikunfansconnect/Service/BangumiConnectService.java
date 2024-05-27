package com.ctey.ikunfansconnect.Service;

import com.ctey.ikunfanscommon.Entity.Bangumi.CalendarItemData;
import com.ctey.ikunfanscommon.Entity.Bangumi.SubjectGetItemData;
import com.ctey.ikunfanscommon.Entity.Bangumi.SubjectPostItemData;
import com.ctey.ikunfanscommon.Entity.SubjectInfokvData;
import com.ctey.ikunfanscommon.Resp.Bangumi.BangumiSubjectDetailResp;
import com.ctey.ikunfanscommon.Resp.SubjectDetailResp;
import com.ctey.ikunfanscommon.Util.CollectionEmptyUtil;
import com.ctey.ikunfanscommon.Util.DataParamParseUtil;
import com.ctey.ikunfanscommon.Util.ModelTransformUtil;
import com.ctey.ikunfansconnect.Config.BangumiFeignAPIConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;

import static com.ctey.ikunfanscommon.Static.ConnModuleStatic.BANGUMI_ITEM_LIMIT;
import static com.ctey.ikunfanscommon.Static.ConnModuleStatic.BANGUMI_PRESET_SEARCH_QUERY;

@Component
public class BangumiConnectService {
    @Qualifier("bangumiInstance")
    private final BangumiFeignAPIConfig bangumiInstance;

    @Autowired
    public BangumiConnectService(BangumiFeignAPIConfig bangumiInstance) {
        this.bangumiInstance = bangumiInstance;
    }

    public List<SubjectPostItemData> collectPostTargetSubjects(List<String> keywords) {
        List<SubjectPostItemData> subjectPostItemDataList = new ArrayList<>();
        if (CollectionEmptyUtil.forList(keywords)) { return subjectPostItemDataList; }
        keywords.stream().forEach(key -> {
            List<SubjectPostItemData> itemList = bangumiInstance.searchSubjectsV0(ModelTransformUtil.getDefaultBangumiSubjectReq(key)).getData();
            if (!CollectionEmptyUtil.forList(itemList)) {
                subjectPostItemDataList.addAll(itemList.stream().filter(DataParamParseUtil::filterSubjectPostItem).limit(BANGUMI_ITEM_LIMIT).toList());
            }
            try { Thread.sleep(1000L); }
            catch (Exception e) { e.printStackTrace(); }
        });
        return parsePostTargetSubjects(subjectPostItemDataList);
    }

    public List<SubjectGetItemData> collectGetTargetSubjects(List<String> keywords) {
        List<SubjectGetItemData> subjectGetItemDataList = new ArrayList<>();
        if (CollectionEmptyUtil.forList(keywords)) { return subjectGetItemDataList; }
        keywords.stream().forEach(key -> {
            List<SubjectGetItemData> itemList = bangumiInstance.searchSubjects(DataParamParseUtil.filterUrlEncode(URLEncoder.encode(key, StandardCharsets.UTF_8)), BANGUMI_PRESET_SEARCH_QUERY).getList();
            if (!CollectionEmptyUtil.forList(itemList)) {
                subjectGetItemDataList.addAll(itemList.stream().filter(DataParamParseUtil::filterSubjectGetItem).limit(BANGUMI_ITEM_LIMIT).toList());
            }
            try { Thread.sleep(1000L); }
            catch (Exception e) { e.printStackTrace(); }
        });
        return parseGetTargetSubjects(subjectGetItemDataList);
    }

    public List<CalendarItemData> collectCalendarTargetSubjects() {
        return bangumiInstance.getCalendar();
    }

    public SubjectDetailResp collectTargetSubjectDetail(long id) {
        return parseSubjectDetailData(bangumiInstance.searchSubjectById((int)id));
    }

    public List<SubjectPostItemData> parsePostTargetSubjects(List<SubjectPostItemData> dataList) {
        if (CollectionEmptyUtil.forList(dataList)) { return dataList; }
        return dataList.stream()
                .filter(DataParamParseUtil.filterDistinctByKey(SubjectPostItemData::getId))
                .sorted(Comparator.comparingInt(SubjectPostItemData::getRank))
                .toList();
    }

    public List<SubjectGetItemData> parseGetTargetSubjects(List<SubjectGetItemData> dataList) {
        if (CollectionEmptyUtil.forList(dataList)) { return dataList; }
        return dataList.stream()
                .filter(DataParamParseUtil.filterDistinctByKey(SubjectGetItemData::getId))
                .sorted(Comparator.comparingInt(SubjectGetItemData::getRank))
                .toList();
    }

    public SubjectDetailResp parseSubjectDetailData(BangumiSubjectDetailResp data) {
        List<SubjectInfokvData> infobox = data.getInfobox().stream().map(info -> {
            if (info.getValue() instanceof String) {
                return new SubjectInfokvData(info.getKey(), (String) info.getValue());
            }
            return null;
        }).filter(Objects::nonNull).toList();
        return new SubjectDetailResp(
                data.getId(),
                CollectionEmptyUtil.forString(data.getNameCN()) ? data.getName() : data.getNameCN(),
                data.getName(),
                data.getImages() == null ? "" : data.getImages().getCommon(),
                data.getSummary(),
                data.getPlatform(),
                data.getDate(),
                data.getRating().getScore(),
                data.getRating().getRank(),
                data.getTotalEpisodes(),
                data.getCollection().getOnHold(),
                data.getCollection().getDropped(),
                data.getCollection().getWish(),
                data.getCollection().getCollect(),
                data.getCollection().getDoing(),
                infobox,
                false
        );
    }

}
