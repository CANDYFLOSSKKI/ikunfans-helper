package com.ctey.ikunfansweb.Service;

import com.ctey.ikunfanscommon.Entity.Bangumi.SubjectPostItemData;
import com.ctey.ikunfanscommon.Entity.SubjectInfoData;
import com.ctey.ikunfanscommon.Entity.TokenClaimEntity;
import com.ctey.ikunfanscommon.Req.SubjectContentReq;
import com.ctey.ikunfanscommon.Util.CollectionEmptyUtil;
import com.ctey.ikunfanscommon.Util.MessagePrintUtil;
import com.ctey.ikunfanscommon.Util.ModelTransformUtil;
import com.ctey.ikunfansconnect.Service.AliyunQwenService;
import com.ctey.ikunfansconnect.Service.BangumiService;
import com.ctey.ikunfansdb.Model.Favorite;
import com.ctey.ikunfansdb.Model.Subject;
import com.ctey.ikunfansdb.Model.Tag;
import com.ctey.ikunfansdb.Service.FavoriteService;
import com.ctey.ikunfansdb.Service.SubjectService;
import com.ctey.ikunfansdb.Service.TagService;
import com.ctey.ikunfansdb.Service.UserService;
import com.ctey.ikunfansdb.Util.DbModelTransformUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import com.ctey.ikunfansweb.Config.DockingInterfaceConfig.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.concurrent.TimeUnit;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import static com.ctey.ikunfanscommon.Static.WebModuleStatic.ASYNC_SERVICE_TIMEOUT;

@Component
public class SubjectWebService {
    @Qualifier("webTaskAsyncPool")
    private final Executor webTaskAsyncPool;
    private final TokenWebService tokenWebService;
    private final UserService userService;
    private final SubjectService subjectService;
    private final TagService tagService;
    private final FavoriteService favoriteService;
    private final AliyunQwenService aliyunQwenService;
    private final BangumiService bangumiService;

    @Autowired
    public SubjectWebService(Executor webTaskAsyncPool, TokenWebService tokenWebService, UserService userService, SubjectService subjectService, TagService tagService, FavoriteService favoriteService, AliyunQwenService aliyunQwenService, BangumiService bangumiService) {
        this.webTaskAsyncPool = webTaskAsyncPool;
        this.tokenWebService = tokenWebService;
        this.userService = userService;
        this.subjectService = subjectService;
        this.tagService = tagService;
        this.favoriteService = favoriteService;
        this.aliyunQwenService = aliyunQwenService;
        this.bangumiService = bangumiService;
    }

    @Docking
    public List<SubjectInfoData> collectTargetSubjects(String auth, SubjectContentReq req) {
        TokenClaimEntity claim = tokenWebService.handleAccountAuth(auth);
        if (claim == null) { return null; }
        if (!userService.hasOverlapByAuth(claim)) { return null; }
        if (CollectionEmptyUtil.forString(req.getContent())) { return null; }
        String callBackMsg = aliyunQwenService.streamCallWithMessage(req.getContent());
        List<String> keywords = Pattern.compile("《([^》]+)》")
                .matcher(callBackMsg)
                .results()
                .map(result -> result.group(1))
                .limit(3)
                .toList();
        MessagePrintUtil.printCallBackKeyWords(keywords);
        List<SubjectPostItemData> subjectPostItemDataList = bangumiService.collectTargetSubjects(keywords);
        if (CollectionEmptyUtil.forList(subjectPostItemDataList)) { return null; }
        List<Integer> subjectIdList = new ArrayList<>(subjectService.getIdList());
        collectAndStoreTagData(subjectIdList, subjectPostItemDataList);
        collectAndStoreSubjectData(subjectIdList, subjectPostItemDataList);
        collectAndStoreFavoriteData(claim.getId(), subjectPostItemDataList);
        return subjectPostItemDataList.stream().map(ModelTransformUtil::subjectItemDataToInfo).toList();
    }

    @Docking
    public List<SubjectInfoData> collectUserSubjects(String auth) {
        TokenClaimEntity claim = tokenWebService.handleAccountAuth(auth);
        if (claim == null) { return null; }
        if (!userService.hasOverlapByAuth(claim)) { return null; }
        List<SubjectInfoData> subjectInfoDataList = new ArrayList<>();
        List<Favorite> favoriteList = favoriteService.getLimitFavoriteListById(claim.getId());
        if (CollectionEmptyUtil.forList(favoriteList)) { return subjectInfoDataList; }
        try {
            var collectSubjectTask = collectFavoriteSubjectList(favoriteList);
            var collectTagTask = collectFavoriteTagList(favoriteList);
            CompletableFuture.allOf(collectSubjectTask, collectTagTask).get(ASYNC_SERVICE_TIMEOUT, TimeUnit.SECONDS);
            List<Subject> subjectList = collectSubjectTask.get();
            List<Tag> tagList = collectTagTask.get();
            subjectInfoDataList.addAll(subjectList.stream().map(subject -> {
                List<String> tagTargetList = tagList.stream().filter(tag -> Objects.equals(tag.getSubjectId(), subject.getId())).map(Tag::getName).toList();
                return DbModelTransformUtil.subjectAndTagListToSubjectInfo(subject, tagTargetList);
            }).toList());
        } catch (Exception e) { e.printStackTrace(); }
        return subjectInfoDataList;
    }

    public void collectAndStoreTagData(List<Integer> subjectIdList, List<SubjectPostItemData> subjectPostItemDataList) {
        CompletableFuture.runAsync(() -> {
            List<Tag> tagList = subjectPostItemDataList.stream()
                    .filter(subjectItemData -> !(subjectIdList.contains(subjectItemData.getId())))
                    .flatMap(subjectItemData -> subjectItemData.getTags().stream()
                    .limit(3)
                    .map(tagData -> DbModelTransformUtil.subjectItemTagToTag(subjectItemData.getId(), tagData))
                    .toList().stream())
                    .collect(Collectors.toList());
            tagService.storeTagList(tagList);
        }, webTaskAsyncPool);
    }

    public void collectAndStoreSubjectData(List<Integer> subjectIdList, List<SubjectPostItemData> subjectPostItemDataList) {
        CompletableFuture.runAsync(() -> {
            List<Subject> subjectList = subjectPostItemDataList.stream()
                    .filter(subjectItemData -> !(subjectIdList.contains(subjectItemData.getId())))
                    .map(DbModelTransformUtil::subjectItemDataToSubject)
                    .toList();
            subjectService.storeSubjectList(subjectList);
        }, webTaskAsyncPool);
    }

    public void collectAndStoreFavoriteData(String id, List<SubjectPostItemData> subjectPostItemDataList) {
        CompletableFuture.runAsync(() -> {
            List<Favorite> favoriteList = favoriteService.getFavoriteListById(id);
            List<Favorite> readyToInsertList = new ArrayList<>();
            subjectPostItemDataList.stream().forEach(subjectItemData -> {
                favoriteList.stream().filter(favorite -> favorite.getSubjectId().intValue() == subjectItemData.getId()).findFirst().ifPresentOrElse(
                        (favorite) -> favorite.setCount(favorite.getCount() + 1),
                        () -> readyToInsertList.add(DbModelTransformUtil.subjectItemDataToFavorite(id, subjectItemData))
                );
            });
            favoriteService.updateFavoriteList(favoriteList);
            favoriteService.storeFavoriteList(readyToInsertList);
        }, webTaskAsyncPool);
    }

    public CompletableFuture<List<Subject>> collectFavoriteSubjectList(List<Favorite> favoriteList) {
        return CompletableFuture.supplyAsync(() -> {
            List<Long> subjectIdList = favoriteList.stream().map(Favorite::getSubjectId).toList();
            return subjectService.getFavoriteSubjectList(subjectIdList);
        }, webTaskAsyncPool);
    }

    public CompletableFuture<List<Tag>> collectFavoriteTagList(List<Favorite> favoriteList) {
        return CompletableFuture.supplyAsync(() -> {
            List<Long> subjectIdList = favoriteList.stream().map(Favorite::getSubjectId).toList();
            return tagService.getFavoriteTagList(subjectIdList);
        }, webTaskAsyncPool);
    }

}
