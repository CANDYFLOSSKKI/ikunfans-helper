package com.ctey.ikunfansdb.Model;

import com.baomidou.mybatisplus.annotation.TableField;
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
@TableName("ikunfans_favorite")
public class Favorite extends Model<Favorite> {
    @TableField("user_id")
    private String userId;
    @TableField("subject_id")
    private Long subjectId;
    private Integer count;
}
