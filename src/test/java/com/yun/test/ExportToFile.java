package com.yun.test;
import com.yunhui.excel.poi.ExcelUtils;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.*;

/**
 * Title: ExportToFile.java <br>
 * Description: <br>
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
        OutputStream outputStream=new FileOutputStream(new File("/Users/dushaoyun/test/test.xls"));
        //方式1：在实体类加上导出注解
        ExcelUtils.export(list,outputStream);

        //方式2：用一个map 标识导出字段和excel头部
        Map<String,String> headMap=new LinkedHashMap<>();

        headMap.put("age","年龄");
        headMap.put("wx","微信");
        headMap.put("name","姓名");
        headMap.put("phone","手机号");

        ExcelUtils.export(list,headMap,outputStream);

    }

}
