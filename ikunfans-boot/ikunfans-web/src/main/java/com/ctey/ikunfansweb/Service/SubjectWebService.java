package com.ctey.ikunfansweb.Service;

import com.ctey.ikunfanscommon.Entity.Bangumi.CalendarItemData;
import com.ctey.ikunfanscommon.Entity.Bangumi.SubjectGetItemData;
import com.ctey.ikunfanscommon.Entity.Bangumi.SubjectPostItemData;
import com.ctey.ikunfanscommon.Entity.CalendarInfoData;
import com.ctey.ikunfanscommon.Entity.SubjectInfoData;
import com.ctey.ikunfanscommon.Entity.TokenClaimEntity;
import com.ctey.ikunfanscommon.Req.SubjectContentReq;
import com.ctey.ikunfanscommon.Resp.SubjectCalendarResp;
import com.ctey.ikunfanscommon.Resp.SubjectContentResp;
import com.ctey.ikunfanscommon.Resp.SubjectDetailResp;
import com.ctey.ikunfanscommon.Util.CollectionEmptyUtil;
import com.ctey.ikunfanscommon.Util.DataParamParseUtil;
import com.ctey.ikunfanscommon.Util.ModelTransformUtil;
import com.ctey.ikunfansconnect.Service.BangumiConnectService;
import com.ctey.ikunfansconnect.Service.QwenConnectService;
import com.ctey.ikunfansdb.Model.Favorite;
import com.ctey.ikunfansdb.Model.Subject;
import com.ctey.ikunfansdb.Service.FavoriteService;
import com.ctey.ikunfansdb.Service.SubjectService;
import com.ctey.ikunfansdb.Service.UserService;
import com.ctey.ikunfansdb.Util.DbModelTransformUtil;
import com.ctey.ikunfansweb.Config.DockingInterfaceConfig.Docking;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import static com.ctey.ikunfanscommon.Static.WebModuleStatic.AI_MODE_GET;
import static com.ctey.ikunfanscommon.Static.WebModuleStatic.AI_MODE_POST;

@Component
public class SubjectWebService {
    private final TokenWebService tokenWebService;
    private final UserService userService;
    private final SubjectService subjectService;
    private final FavoriteService favoriteService;
    private final QwenConnectService qwenConnectService;
    private final BangumiConnectService bangumiConnectService;

    @Autowired
    public SubjectWebService(TokenWebService tokenWebService, UserService userService, SubjectService subjectService, FavoriteService favoriteService, QwenConnectService qwenConnectService, BangumiConnectService bangumiConnectService) {
        this.tokenWebService = tokenWebService;
        this.userService = userService;
        this.subjectService = subjectService;
        this.favoriteService = favoriteService;
        this.qwenConnectService = qwenConnectService;
        this.bangumiConnectService = bangumiConnectService;
    }

    @Docking
    public SubjectContentResp collectTargetSubjects(String auth, SubjectContentReq req) {
        TokenClaimEntity claim = tokenWebService.handleAccountAuth(auth);
        if (claim == null || !userService.hasOverlapByAuth(claim)) { return null; }
        String callBackMsg = qwenConnectService.streamCallWithMessage(req.getContent());
        System.out.println(callBackMsg);
        List<String> callBackKeywords = DataParamParseUtil.parseCallBackKeywords(callBackMsg);
        List<SubjectInfoData> subjectInfoDataList = processSearchSubjects(callBackKeywords, req.getMode());
        return new SubjectContentResp(
                callBackMsg,
                callBackKeywords,
                subjectInfoDataList
        );
    }

    @Docking
    public List<SubjectInfoData> collectUserSubjects(String auth) {
        TokenClaimEntity claim = tokenWebService.handleAccountAuth(auth);
        if (claim == null || !userService.hasOverlapByAuth(claim)) { return null; }
        List<SubjectInfoData> subjectInfoDataList = new ArrayList<>();
        List<Favorite> favoriteList = favoriteService.getFavoriteListById(claim.getId());
        if (!CollectionEmptyUtil.forList(favoriteList)) {
            List<Subject> subjectList = subjectService.getFavoriteSubjectList(favoriteList.stream().map(Favorite::getSubjectId).toList());
            if (!CollectionEmptyUtil.forList(subjectList)) {
                subjectInfoDataList.addAll(subjectList.stream().map(DbModelTransformUtil::subjectToSubjectInfo).toList());
            }
        }
        return subjectInfoDataList;
    }

    @Docking
    public boolean processAddUserFavorite(String auth, Long id) {
        TokenClaimEntity claim = tokenWebService.handleAccountAuth(auth);
        if (claim == null || !userService.hasOverlapByAuth(claim)) { return false; }
        favoriteService.addFavorite(claim.getId(), id);
        if (!subjectService.hasSubject(id)) {
            subjectService.storeSubjectList(List.of(DbModelTransformUtil.subjectDetailToSubject(bangumiConnectService.collectTargetSubjectDetail(id))));
        }
        return true;
    }

    @Docking
    public boolean processRemoveUserFavorite(String auth, Long id) {
        TokenClaimEntity claim = tokenWebService.handleAccountAuth(auth);
        if (claim == null || !userService.hasOverlapByAuth(claim)) { return false; }
        favoriteService.removeFavorite(claim.getId(), id);
        return true;
    }

    @Docking
    public SubjectContentResp collectSimpleTargetSubjects(String auth, SubjectContentReq req) {
        TokenClaimEntity claim = tokenWebService.handleAccountAuth(auth);
        if (claim == null || !userService.hasOverlapByAuth(claim)) { return null; }
        String content = DataParamParseUtil.parseSimpleContent(req.getContent());
        List<SubjectInfoData> subjectInfoDataList = processSearchSubjects(List.of(content), req.getMode());
        return new SubjectContentResp(
                null,
                List.of(content),
                subjectInfoDataList
        );
    }

    @Docking
    public SubjectCalendarResp collectCalendarSubjects(String auth) {
        TokenClaimEntity claim = tokenWebService.handleAccountAuth(auth);
        if (claim == null || !userService.hasOverlapByAuth(claim)) { return null; }
        SubjectCalendarResp resp = ModelTransformUtil.getDefaultSubjectCalendarResp();
        List<CalendarItemData> calendarItemDataList = bangumiConnectService.collectCalendarTargetSubjects();
        calendarItemDataList.stream()
            .sorted(Comparator.comparing(item -> item.getWeekday().getId()))
            .forEach(calendarItemData -> resp.getInfoList().add(new CalendarInfoData(
                    calendarItemData.getWeekday().getId(),
                    calendarItemData.getWeekday().getCn(),
                    calendarItemData.getWeekday().getJa(),
                    calendarItemData.getItems().stream().map(ModelTransformUtil::subjectCalendarToMiniInfo).toList()
            )));
        return resp;
    }

    @Docking
    public SubjectDetailResp collectSubjectDetail(String auth, long id) {
        TokenClaimEntity claim = tokenWebService.handleAccountAuth(auth);
        if (claim == null || !userService.hasOverlapByAuth(claim)) { return null; }
        SubjectDetailResp resp = bangumiConnectService.collectTargetSubjectDetail(id);
        resp.setFavorite(favoriteService.getFavoriteStatus(claim.getId(), id));
        return resp;
    }

    public List<SubjectInfoData> processSearchSubjects(List<String> keywords, int mode) {
        List<Integer> subjectIdList = new ArrayList<>(subjectService.getIdList());
        List<SubjectInfoData> subjectInfoDataList = new ArrayList<>();
        switch (mode) {
            case AI_MODE_GET -> {
                List<SubjectGetItemData> subjectItemDataList = bangumiConnectService.collectGetTargetSubjects(keywords);
                if (!CollectionEmptyUtil.forList(subjectItemDataList)) {
                    subjectService.storeSubjectList(subjectItemDataList.stream()
                            .filter(item -> !subjectIdList.contains(item.getId()))
                            .map(DbModelTransformUtil::subjectItemDataToSubject).toList());
                    subjectInfoDataList.addAll(subjectItemDataList.stream().map(DbModelTransformUtil::subjectItemDataToSubjectInfo).toList());
                }
            }
            case AI_MODE_POST -> {
                List<SubjectPostItemData> subjectItemDataList = bangumiConnectService.collectPostTargetSubjects(keywords);
                if (!CollectionEmptyUtil.forList(subjectItemDataList)) {
                    subjectService.storeSubjectList(subjectItemDataList.stream()
                            .filter(item -> !subjectIdList.contains(item.getId()))
                            .map(DbModelTransformUtil::subjectItemDataToSubject).toList());
                    subjectInfoDataList.addAll(subjectItemDataList.stream().map(DbModelTransformUtil::subjectItemDataToSubjectInfo).toList());
                }
            }
        }
        return subjectInfoDataList;
    }

}
