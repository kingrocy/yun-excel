package com.yun.test;

import com.alibaba.fastjson.JSONReader;
import com.alibaba.fastjson.TypeReference;
import com.yunhui.excel.poi.ExcelUtils;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @Date : 2019-11-25 09:42
 * @Desc :
 */
public class ExportFeature {

    public static void main(String[] args) throws FileNotFoundException {
        String path = ExportFeature.class.getClassLoader().getResource("feature.json").getPath();
        JSONReader reader=new JSONReader(new FileReader(new File(path)));
        List<FeatureConfigBean> featureConfs = reader.readObject(new TypeReference<List<FeatureConfigBean>>() {
        });

        List<Features> features=new ArrayList<>();

        for (FeatureConfigBean featureConf : featureConfs) {
            Features feature=new Features();

            feature.setCol(featureConf.getCol());
            feature.setDbField(featureConf.getDbField());
            if(featureConf.getJoin()!=null){
                feature.setTable(featureConf.getJoin().getTargetTable());
            }else{
                feature.setTable(featureConf.getTable());
            }
            if(featureConf.getCol().startsWith("car_info")){
                feature.setType("车辆特征");
            }else if(featureConf.getCol().startsWith("seller_info")){
                feature.setType("用户特征");
            }
            features.add(feature);
        }

        OutputStream outputStream=new FileOutputStream(new File("/Users/dushaoyun/test/features.xls"));
        ExcelUtils.export(features,outputStream);
    }
}
