package com.ctey.ikunfanscommon.Entity.Bangumi;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SubjectPostItemData {
    private int id;
    private int type;
    private String date;
    private String image;
    private String summary;
    private String name;
    @JsonProperty("name_cn")
    private String nameCN;
    private List<SubjectTagData> tags;
    private double score;
    private int rank;
}
