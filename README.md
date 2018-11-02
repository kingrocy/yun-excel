# YunExcel

## 简介

使用注解标识实体类来进行Excel导出的工具类


## 测试案例

### 导出excel到文件系统

实体类：

        @ExcelTable(tableName = "测试excel名称")
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
        
        
导出代码：

        List<People> list=new ArrayList<>();
        for(int i=0;i<20;i++){
            People people=new People("xxxx",24,"xxxx","xxxxxxxxxxx","xxxxxxxxxxx","浙江省杭州市余杭区xxxxxxxxx");
            list.add(people);
        }
        OutputStream outputStream=new FileOutputStream(new File("D://test//test.xls"));
        //导出
        ExcelUtils.export(list,outputStream);
        

具体代码见测试类。。。
