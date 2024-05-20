package com.ctey.ikunfansdb.Model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@TableName("ikunfans_subject")
public class Subject extends Model<Subject> {
    @TableId(type = IdType.INPUT)
    private Long id;
    private String name;
    @TableField("name_cn")
    private String nameCN;
    @TableField("air_date")
    private String airDate;
    private String image;
    private String summary;
    private Double score;
    @TableField("score_rank")
    private Integer scoreRank;
}
