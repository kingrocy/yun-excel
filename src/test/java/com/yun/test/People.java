package com.yun.test;

import com.yunhui.excel.annotation.ExcelField;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * Title: People.java <br>
 * Description: <br>
 * Copyright (c) 聚阿网络科技版权所有 2018 <br>
 * Create DateTime: 2018年11月02日 16:25 <br>
 *
 * @author yun
 */
@Data
@AllArgsConstructor
public class People {

    @ExcelField(sort=1,name="姓名")
    private String name;

    @ExcelField(sort=2,name="年龄")
    private Integer age;

    @ExcelField(sort=3,name="微信")
    private String wx;

    @ExcelField(sort=4,name="QQ")
    private String qq;

    @ExcelField(sort=5,name="手机号")
    private String phone;

    @ExcelField(sort=6,name="地址")
    private String address;
}