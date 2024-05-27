package com.ctey.ikunfanscommon.Resp;

import com.ctey.ikunfanscommon.Entity.SubjectInfokvData;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SubjectDetailResp {
    private long id;
    @JsonProperty("nameCN")
    private String nameCN;
    private String name;
    private String image;
    private String summary;
    private String platform;
    private String date;
    private double score;
    private int rank;
    private int eps;
    // 沿用SubjectCollectionData
    @JsonProperty("onHold")
    private int onHold;
    private int dropped;
    private int wish;
    private int collect;
    private int doing;
    private List<SubjectInfokvData> infobox;
    @JsonProperty("isFavorite")
    private boolean isFavorite;
}
