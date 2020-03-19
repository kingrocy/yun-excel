package com.yun.city_distance;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.yunhui.excel.poi.ExcelUtils;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.http.client.fluent.Request;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.Stream;

/**
 * @Date : 2020/3/19 11:09 上午
 * @Author : dushaoyun
 * @desc 计算城市之间的距离
 */
public class CityDistanceCalculate {

    private static final String key = "7a9a586e7951a3b8dce4606141727258";

    public static final Map<String, String> location_cache = new ConcurrentHashMap<>();

    public static final Map<DistanceBean, Double> distance_cache = new ConcurrentHashMap<>();

    public static String getLocation(String cityName) {
        if (location_cache.containsKey(cityName)) {
            System.out.println("get location from cache");
            return location_cache.get(cityName);
        }
        String url = "https://restapi.amap.com/v3/geocode/geo?key=%s&address=%s";
        try {
            String result = Request.Get(String.format(url, key, cityName)).execute().returnContent().asString();
            JSONObject jsonObject = JSON.parseObject(result);
            if (jsonObject.containsKey("geocodes")) {
                JSONArray geocodes = jsonObject.getJSONArray("geocodes");
                if(CollectionUtils.isNotEmpty(geocodes)){
                    String location = geocodes.getJSONObject(0).getString("location");
                    System.out.println("location:"+location);
                    location_cache.put(cityName, location);
                    return location;
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Double getDistance(String source, String target) {
        if(source==null||target==null){
            return 0d;
        }
        String url = "https://restapi.amap.com/v3/distance?origins=%s&destination=%s&key=%s";
        try {
            String result = Request.Get(String.format(url, source, target, key)).execute().returnContent().asString();
            JSONObject jsonObject = JSON.parseObject(result);
            if (jsonObject.containsKey("results")) {
                String distance = jsonObject.getJSONArray("results").getJSONObject(0).getString("distance");
                if(distance!=null&&"1".equals(distance)){
                    return 0d;
                }
                System.out.println("distance:"+distance);
                return Long.valueOf(distance) * 1.00 / 1000;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return 0d;
    }


    public static Double getCityDistance(String sourceCity, String targetCity) {
        String sourceLocation = getLocation(sourceCity);
        String targetLocation = getLocation(targetCity);
        DistanceBean bean = new DistanceBean(sourceCity, targetCity);
        if (distance_cache.containsKey(bean)) {
            System.out.println("get distance from cache");
            return distance_cache.get(bean);
        }
        Double distance = getDistance(sourceLocation, targetLocation);
        distance_cache.put(bean, distance);
        return distance;
    }

    public static void main(String[] args) throws FileNotFoundException {
        String sourceCity = "北京,苏州,长沙,佛山,成都,昆明,玉树,郑州,宁波,西安,青岛,杭州,武汉,合肥,上海,淮南,重庆,石家庄,临沂,济南,阳泉,厦门,芜湖,赣州,贵阳,金华,台州,南宁,东营,沈阳,太原,秦皇岛,大同,唐山,广州,常州,大连,东莞,宜昌,大庆,淄博,潍坊,南京,嘉兴,潮州,阜阳,银川,深圳,济宁,温州,呼和浩特,泉州,德阳,阳江,南昌,盐城,惠州,桂林,茂名,南通,马鞍山,哈尔滨,海口,洛阳,许昌,无锡,荆州,德州,中山,随州,肇庆,珠海,萍乡,舟山,五家渠,柳州,衡水,天津,烟台,泰州,阜新,绍兴,韶关,平顶山,张家口,保定,大理,铜陵,清远,岳阳,吉安,廊坊,衡阳,淮安,百色,连云港,丽水,南阳,眉山,九江,黄冈,承德,龙岩,南充,铁岭,张家界,株洲,鞍山,镇江,泰安,滁州,莆田,黄石,上饶,福州,牡丹江,漳州,来宾,衢州,湛江,吴忠,徐州,郴州,黔西南,广安,沧州,延安,榆林,乐山,梅州,兰州,汕尾,菏泽,新余,信阳,长春,威海,新乡,鹤壁,包头,商丘,盘锦,天水,锦州,汕头,临汾,运城,泸州,晋城,扬州,攀枝花,丹东,湖州,宜春,枣庄,三明,佳木斯,襄阳,营口,揭阳,吉林,南平,本溪,驻马店,宿州,松原,荆门,咸阳,抚顺,安庆,四平,遵义,文山,宿迁,通辽,忻州,长治,酒泉,池州,北海,绵阳,邯郸,渭南,鄂州,孝感,景德镇,常德,娄底,贵港,十堰,资阳,晋中,梧州,凉山,乌兰察布盟,益阳,亳州,内江,宜宾,达州,江门,日照,绥化,葫芦岛,湘潭,白山,六安,河源,朝阳,双鸭山,宣城,咸宁,河池,张掖,滨州,吕梁,邵阳,济源,蚌埠,贺州,玉林,汉中,安顺,防城港,三门峡,遂宁,楚雄,开封,焦作,钦州,恩施,莱芜,宁德,朔州,曲靖,鹤岗,邢台,巴彦淖尔盟,玉溪,宝鸡,丽江,武威,阿克苏,赤峰,齐齐哈尔,三亚,伊春,聊城,西宁,安阳,潜江,六盘水,鄂尔多斯,白城,鹰潭,呼伦贝尔,昭通,怀化,红河,自贡,毕节,澄迈,文昌,抚州,周口,铜川,克拉玛依,儋州市,广元,永州,安康,东方,临沧,临夏,乌鲁木齐,辽阳,拉萨,普洱,湘西,铜仁,平凉,雅安,庆阳,黔东南,七台河,淮北,德宏,仙桃,西双版纳,海东,甘南,漯河,鸡西,巴中,白银,濮阳,保山,黔南,迪庆,固原,金昌,嘉峪关,崇左,海西,云浮,陇南,乌兰察布,定西,天门,喀什,伊犁哈萨克";
        String targetCity = "东莞,南宁,佛山,中山,珠海,柳州,清远,重庆,泉州,沧州,桂林,梅州,长沙,南京,呼和浩特,厦门,昆明,惠州,大理,无锡,阜阳,揭阳,南阳,齐齐哈尔,福州,临沂,郑州,哈尔滨,南昌,三明,保定,茂名,襄阳,十堰,漳州,淮安,海口,三亚,郴州,葫芦岛,来宾,河源,聊城,随州,恩施,盘锦,抚顺,怀化,百色,河池,宝鸡,榆林,白城,韶关,上海,义乌,六盘水,平顶山,贺州,黄冈,孝感,芜湖,潜江,舟山,曲靖,贵阳,晋城,兴义,阳泉,杭州,安顺,遵义,北海,西安,贵港,黔西南,阳江,赣州,乐山,岳阳,汕头,湛江,玉溪,许昌,大庆,常州,宁波,石家庄,廊坊,唐山,烟台,青岛,潍坊,洛阳,沈阳,成都,长春,银川,济南,温州,合肥,武汉,太原,大连,嘉兴,湖州,菏泽,淄博,龙岩,兰州,邯郸,德州,衡阳,宜昌,马鞍山,六安,莆田,承德,济宁,安庆,佳木斯,常德,枣庄,苏州,金华,赤峰,锦州,绵阳,鞍山,滁州,威海,日照,上饶,宁德,秦皇岛,镇江,驻马店,朝阳,德阳,东营,黄石,吉安,荆门,荆州,九江,开封,临汾,牡丹江,南充,咸宁,湘潭,扬州,营口,肇庆,宿州,徐州,宿迁,西宁,连云港,南通,台州,衢州,包头,绍兴,株洲,大同,张家口,运城,吉林,丽水,延安,通辽,江门,泰州,盐城,宜宾,拉萨,文山,乌鲁木齐,丽江,潮州,宜春,广州,邵阳,酒泉,凉山,渭南,信阳,毕节,普洱,红河,铜仁";
        String[] sourceCitys = sourceCity.split(",");
        String[] targetCitys = targetCity.split(",");
        List<ExportBean> exportBeanList=new CopyOnWriteArrayList<>();
        Stream.of(sourceCitys).parallel().forEach(source->{
            for (String target : targetCitys) {
                Double distance = getCityDistance(source, target);
                ExportBean exportBean=new ExportBean(source,target,distance);
                exportBeanList.add(exportBean);
            }
        });
        System.out.println("exportBeanList size:"+exportBeanList.size());
        OutputStream outputStream=new FileOutputStream(new File("/Users/dushaoyun/test/city.xls"));
        //方式1：在实体类加上导出注解
        ExcelUtils.export(exportBeanList,outputStream);
    }
}
