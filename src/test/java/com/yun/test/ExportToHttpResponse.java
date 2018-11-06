package com.yun.test;

import com.yunhui.excel.poi.ExcelUtils;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

/**
 * Title: ExportToHttpResponse.java <br>
 * Description: <br>
 * Copyright (c) 聚阿网络科技版权所有 2018 <br>
 * Create DateTime: 2018年11月02日 14:26 <br>
 *
 * @author yun
 */
public class ExportToHttpResponse {

    public static void main(String[] args) {

        List<People> list=new ArrayList<>();

        String fileName="测试";

        HttpServletResponse response=null;

        ExcelUtils.export(list,fileName,response);

    }

}
