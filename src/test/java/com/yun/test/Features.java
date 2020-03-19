package com.yun.test;

import com.yunhui.excel.annotation.ExcelField;
import lombok.Data;

/**
 * @Date : 2019-11-25 09:39
 * @Desc :
 */
@Data
public class Features {


    @ExcelField(sort=1,name="特征名称")
    private String col;
    @ExcelField(sort=2,name="表")
    private String table;
    @ExcelField(sort=3,name="字段")
    private String dbField;
    @ExcelField(sort=4,name="分类")
    private String type;
}
