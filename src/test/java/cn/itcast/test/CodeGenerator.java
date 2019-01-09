package cn.itcast.test;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.config.DataSourceConfig;
import com.baomidou.mybatisplus.generator.config.GlobalConfig;
import com.baomidou.mybatisplus.generator.config.PackageConfig;
import com.baomidou.mybatisplus.generator.config.StrategyConfig;
import com.baomidou.mybatisplus.generator.config.rules.DateType;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;

public class CodeGenerator {
    public static void main(String[] args) {
        // 全局配置
        GlobalConfig gc = new GlobalConfig();
        // 自动获取当前项目路径的绝对路径
        String projectPath = System.getProperty("user.dir");
        // 如果是maven 项目，那么需要拼接下面的路径作为输出的根路径
        // 如果是单纯的  eclise 项目的话，那么可能只需要拼接个  /src
        gc.setOutputDir(projectPath + "/src/main/java");
        gc.setActiveRecord(true);	// 设置是否开启 ActiveRecord 模式
        gc.setFileOverride(true);// 设置是否进行文件覆盖
        // 设置作者
         gc.setAuthor("eric");
        // 是否自动打开生成的目录
        gc.setOpen(false);
        
        // 设置生成的service 接口名格式：   如果不设置的话默认生成   IStudentService, 我们一般不要那个 I
        gc.setServiceName("%sService");
        // 设置主键生成策略
        gc.setIdType(IdType.AUTO);
        // 设置时间策略
        gc.setDateType(DateType.ONLY_DATE);
        
        

        // 数据源配置
        DataSourceConfig dsc = new DataSourceConfig();
        // 【注意】 这个也需要手动去配置
        dsc.setUrl("jdbc:mysql://localhost:3306/mybatis_plus?serverTimezone=UTC&useSSL=false");
        // dsc.setSchemaName("public");
        dsc.setDriverName("com.mysql.cj.jdbc.Driver");
        dsc.setUsername("root");
        dsc.setPassword("root");
        

        // 包名配置
        PackageConfig pc = new PackageConfig();
        pc.setParent("cn.itcast");     // 设置父路径
        pc.setController("controller"); // 设置controller 层包名
        pc.setService("service");       // 设置 service 层包名
        pc.setServiceImpl("service.impl");      // 设置 service 实现类包名
        pc.setEntity("entity");         // 设置实体类 包名
        pc.setMapper("mapper");         // 设置dao 层的包名
        pc.setXml("mapper");            // 设置 映射文件的包名， 可以放在 dao 层

        // 策略配置
        StrategyConfig sc = new StrategyConfig();
        sc.setCapitalMode(true);    // 设置是否大写命名
        // 设置实体类属性名的命名策略
        sc.setColumnNaming(NamingStrategy.underline_to_camel);   
        // 设置表名的前缀
        sc.setTablePrefix("t_");
        // 设置需要添加的表名
        // 【这个需要手动添加】
        sc.setInclude("t_student");
        
        // 创建自动生成器对象并加载上面的配置
        AutoGenerator ag = new AutoGenerator();
        ag.setGlobalConfig(gc);
        ag.setDataSource(dsc);
        ag.setPackageInfo(pc);
        ag.setStrategy(sc);
        
        // 执行自动生成
        ag.execute();
    }

}