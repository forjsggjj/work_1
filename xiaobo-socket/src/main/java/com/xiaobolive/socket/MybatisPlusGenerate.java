package com.xiaobolive.socket;

import com.baomidou.mybatisplus.core.exceptions.MybatisPlusException;
import com.baomidou.mybatisplus.core.toolkit.StringPool;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.InjectionConfig;
import com.baomidou.mybatisplus.generator.config.*;
import com.baomidou.mybatisplus.generator.config.builder.ConfigBuilder;
import com.baomidou.mybatisplus.generator.config.po.TableInfo;
import com.baomidou.mybatisplus.generator.config.rules.FileType;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * mybatis代码生成工具
 * 官网：http://mp.baomidou.com
 * @Author wly
 * @Date 2018/7/4 14:10
 */
public class MybatisPlusGenerate {
    /**
     * <p>
     * 读取控制台内容
     * </p>
     */
    public static String scanner(String tip) {
        Scanner scanner = new Scanner(System.in);
        StringBuilder help = new StringBuilder();
        help.append("请输入" + tip + "：");
        System.out.println(help.toString());
        if (scanner.hasNext()) {
            String ipt = scanner.next();
            if (StringUtils.isNotEmpty(ipt)) {
                return ipt;
            }
        }
        throw new MybatisPlusException("请输入正确的" + tip + "！");
    }

    public static void main(String[] args) {
        // 代码生成器
        AutoGenerator mpg = new AutoGenerator();

        // 全局配置
        GlobalConfig gc = new GlobalConfig();
        String projectPath = System.getProperty("user.dir")+"/xiaobo-socket/";
        gc.setOutputDir(projectPath + "/src/main/java");
        gc.setAuthor(scanner("作者"));
        gc.setOpen(false);
        gc.setFileOverride(true);
         gc.setSwagger2(true); //实体属性 Swagger2 注解
        mpg.setGlobalConfig(gc);
        // 自定义配置
        InjectionConfig cfg = new InjectionConfig() {
            @Override
            public void initMap() {
                // to do nothing
            }
        };
        // 如果模板引擎是 freemarker
//        String templatePath = "/templates/mapper.xml.ftl";
        // 如果模板引擎是 velocity
         String templatePath = "/templates/mapper.xml.vm";

        // 自定义输出配置
        List<FileOutConfig> focList = new ArrayList<>();
        // 自定义配置会被优先输出
        focList.add(new FileOutConfig(templatePath) {
            @Override
            public String outputFile(TableInfo tableInfo) {
                // 自定义输出文件名 ， 如果你 Entity 设置了前后缀、此处注意 xml 的名称会跟着发生变化！！
                String mapperXmlPath = projectPath + "/src/main/resources/mapper/" +"/" + tableInfo.getEntityName() + "Mapper" + StringPool.DOT_XML;

                return mapperXmlPath;
            }
        });


        cfg.setFileCreate(new IFileCreate() {
            @Override
            public boolean isCreate(ConfigBuilder configBuilder, FileType fileType, String filePath) {

                checkDir(filePath);
                // 判断自定义文件夹是否需要创建
                File file = new File(filePath);
                if(FileType.ENTITY == fileType)
                    return true;
                return !file.exists();
            }
        });

        cfg.setFileOutConfigList(focList);
        mpg.setCfg(cfg);

        // 配置模板
        TemplateConfig templateConfig = new TemplateConfig();

        // 配置自定义输出模板
        //指定自定义模板路径，注意不要带上.ftl/.vm, 会根据使用的模板引擎自动识别
        // templateConfig.setEntity("templates/entity2.java");
//        templateConfig.setService(null);
//        templateConfig.setServiceImpl(null);
//        templateConfig.setController(null);
//        templateConfig.setMapper(null);
        templateConfig.setXml(null);

        mpg.setTemplate(templateConfig);
        // 数据源配置
        DataSourceConfig dsc = new DataSourceConfig();
        dsc.setUrl("jdbc:mysql://121.37.176.94:3306/xiaobolive?useUnicode=true&characterEncoding=utf8&autoReconnect=true&serverTimezone=Asia%2fShanghai");
        // dsc.setSchemaName("public");
        dsc.setDriverName("com.mysql.jdbc.Driver");
        dsc.setUsername("root");
        dsc.setPassword("jiangguoliupi!!!");
        mpg.setDataSource(dsc);

        // 包配置
        PackageConfig pc = new PackageConfig();
//        pc.setModuleName(scanner("模块名"));
        pc.setModuleName(scanner("模块名"));

        pc.setParent("com.xiaobolive.socket");
        mpg.setPackageInfo(pc);




        // 策略配置
        StrategyConfig strategy = new StrategyConfig();
        strategy.setNaming(NamingStrategy.underline_to_camel);
        strategy.setColumnNaming(NamingStrategy.underline_to_camel);
         strategy.setEntityLombokModel(true);
        strategy.setRestControllerStyle(true);
        // 公共父类
         // 写于父类中的公共字段
         strategy.setInclude(scanner("表名，多个英文逗号分割").split(","));
        strategy.setControllerMappingHyphenStyle(true);
        strategy.setTablePrefix("ddc" + "_");

        mpg.setStrategy(strategy);
         mpg.execute();
    }
}