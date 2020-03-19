package com.yun.city_distance;

import com.yunhui.excel.annotation.ExcelField;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Date : 2020/3/19 1:37 下午
 * @Author : dushaoyun
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ExportBean {

    @ExcelField(sort=1,name="仓库所在城市")
    private String sourceCity;

    @ExcelField(sort=2,name="目的地城市")
    private String targetCity;

    @ExcelField(sort=3,name="距离")
    private Double distance;
}
