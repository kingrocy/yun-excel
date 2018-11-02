package com.yun.test;

import com.yunhui.excel.annotation.ExcelField;
import com.yunhui.excel.annotation.ExcelTable;
import com.yunhui.excel.poi.ExcelUtils;
import lombok.AllArgsConstructor;
import lombok.Data;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Title: ExportToFile.java <br>
 * Description: <br>
 * Copyright (c) 聚阿网络科技版权所有 2018 <br>
 * Create DateTime: 2018年11月02日 14:26 <br>
 *
 * @author yun
 */
public class ExportToFile {

    public static void main(String[] args) throws FileNotFoundException {
        List<People> list=new ArrayList<>();
        for(int i=0;i<20;i++){
            People people=new People("xxxx",24,"xxxx","xxxxxxxxxxx","xxxxxxxxxxx","浙江省杭州市余杭区xxxxxxxxx");
            list.add(people);
        }
        OutputStream outputStream=new FileOutputStream(new File("D://test//test.xls"));
        //导出
        ExcelUtils.export(list,outputStream);
    }

}
