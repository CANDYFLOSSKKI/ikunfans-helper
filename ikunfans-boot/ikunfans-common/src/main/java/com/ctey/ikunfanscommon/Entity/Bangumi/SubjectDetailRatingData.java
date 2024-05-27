package com.ctey.ikunfanscommon.Entity.Bangumi;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SubjectDetailRatingData {
    private int rank;
    private int total;
    private Object count;
    private double score;
}
