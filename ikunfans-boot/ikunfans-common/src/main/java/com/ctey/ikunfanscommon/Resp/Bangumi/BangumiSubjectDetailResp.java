package com.ctey.ikunfanscommon.Resp.Bangumi;

import com.ctey.ikunfanscommon.Entity.Bangumi.*;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BangumiSubjectDetailResp {
    private int id;
    private int type;
    private String name;
    @JsonProperty("name_cn")
    private String nameCN;
    private String summary;
    private boolean nsfw;
    private boolean locked;
    private String date;
    private String platform;
    private SubjectImageData images;
    private List<SubjectInfoBoxData> infobox;
    private int volumes;
    private int eps;
    @JsonProperty("total_episodes")
    private int totalEpisodes;
    private SubjectDetailRatingData rating;
    private SubjectCollectionData collection;
    private List<SubjectTagData> tags;
}
