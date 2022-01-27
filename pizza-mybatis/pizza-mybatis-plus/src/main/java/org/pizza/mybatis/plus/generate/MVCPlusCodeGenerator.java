
package org.pizza.mybatis.plus.generate;

import cn.hutool.core.convert.Convert;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.core.toolkit.StringPool;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.InjectionConfig;
import com.baomidou.mybatisplus.generator.config.*;
import com.baomidou.mybatisplus.generator.config.converts.MySqlTypeConvert;
import com.baomidou.mybatisplus.generator.config.converts.OracleTypeConvert;
import com.baomidou.mybatisplus.generator.config.converts.PostgreSqlTypeConvert;
import com.baomidou.mybatisplus.generator.config.po.TableInfo;
import com.baomidou.mybatisplus.generator.config.rules.DbColumnType;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 代码生成器配置类
 *
 * @author gv
 */
@Data
@Slf4j
public class MVCPlusCodeGenerator {
    /**
     * 代码所在系统
     */
    private String systemName;
    /**
     * 代码模块名称
     */
    private String moduleName;
    /**
     * 代码所在服务名
     */
    private String serviceName;
    /**
     * 代码生成的包名
     */
    private String packageName;
    /**
     * 代码后端生成的地址
     */
    private String packageDir = ".code";
    /**
     * 代码前端生成的地址
     */
    private String packageWebDir;
    /**
     * 需要去掉的表前缀
     */
    private String[] tablePrefix = {};
    /**
     * 需要生成的表名(两者只能取其一)
     */
    private String[] includeTables = {};
    /**
     * 需要排除的表名(两者只能取其一)
     */
    private String[] excludeTables = {};
    /**
     * 是否包含基础业务字段
     */
    private Boolean hasSuperEntity = Boolean.FALSE;

    /**
     * 覆盖项目代码
     */
    private Boolean cover = Boolean.FALSE;

    /**
     * 基础业务字段
     */
    private String[] superEntityColumns = {"create_time", "create_user", "update_time", "update_user", "deleted","tenant_id"};
    /**
     * 租户字段
     */
    private String tenantColumn = "tenant_id";
    /**
     * 是否启用swagger
     */
    private Boolean isSwagger2 = Boolean.TRUE;
    /**
     * 数据库驱动名
     */
    private String driverName;
    /**
     * 数据库链接地址
     */
    private String url;
    /**
     * 数据库用户名
     */
    private String username;
    /**
     * 数据库密码
     */
    private String password;

    public void run(CodeConfig config) {
        AutoGenerator mpg = new AutoGenerator();
        GlobalConfig gc = new GlobalConfig();
        String outputDir = getOutputDir();
        String author = config.getAuthor();
        gc.setOutputDir(outputDir);
        gc.setAuthor(author);
        gc.setFileOverride(true);
        gc.setOpen(false);
        gc.setActiveRecord(false);
        gc.setEnableCache(false);
        gc.setBaseResultMap(true);
        gc.setBaseColumnList(true);
        gc.setMapperName("%sMapper");
        gc.setXmlName("%sMapper");
        gc.setServiceName("I%sService");
        gc.setServiceImplName("%sServiceImpl");
        gc.setControllerName("%sController");
        gc.setSwagger2(isSwagger2);
        mpg.setGlobalConfig(gc);
        DataSourceConfig dsc = new DataSourceConfig();
        String driverName = Convert.toStr(this.driverName, config.getDriverName());
        if (StrUtil.containsAny(driverName, DbType.MYSQL.getDb())) {
            dsc.setDbType(DbType.MYSQL);
            dsc.setTypeConvert(new MySqlTypeConvert() {
                // 自定义数据库表字段类型转换【可选】
                @Override
                public DbColumnType processTypeConvert(GlobalConfig globalConfig, String fieldType) {
                    if (fieldType.toLowerCase().contains("datetime")) {
                        return DbColumnType.DATE;
                    }
                    if (fieldType.toLowerCase().contains("time")) {
                        return DbColumnType.DATE;
                    }
                    if (fieldType.toLowerCase().contains("longblob")) {
                        return DbColumnType.BYTE_ARRAY;
                    }
                    return (DbColumnType) super.processTypeConvert(globalConfig, fieldType);
                }
            });
        } else if (StrUtil.containsAny(driverName, DbType.POSTGRE_SQL.getDb())) {
            dsc.setDbType(DbType.POSTGRE_SQL);
            dsc.setTypeConvert(new PostgreSqlTypeConvert());
        } else {
            dsc.setDbType(DbType.ORACLE);
            dsc.setTypeConvert(new OracleTypeConvert());
        }
        dsc.setDriverName(driverName);
        dsc.setUrl(Convert.toStr(this.url, config.getUrl()));
        dsc.setUsername(Convert.toStr(this.username, config.getUsername()));
        dsc.setPassword(Convert.toStr(this.password, config.getPassword()));
        mpg.setDataSource(dsc);
        // 策略配置
        StrategyConfig strategy = new StrategyConfig();
        // strategy.setCapitalMode(true);// 全局大写命名
        // strategy.setDbColumnUnderline(true);//全局下划线命名
        strategy.setNaming(NamingStrategy.underline_to_camel);
        strategy.setColumnNaming(NamingStrategy.underline_to_camel);
        strategy.setTablePrefix(tablePrefix);
        if (includeTables.length > 0) {
            strategy.setInclude(includeTables);
        }
        if (excludeTables.length > 0) {
            strategy.setExclude(excludeTables);
        }
        if (this.hasSuperEntity) {
            strategy.setSuperEntityClass("com.baida.mybatis.plus.base.TenantEntity");
            strategy.setSuperEntityColumns(this.superEntityColumns);
            strategy.setSuperServiceClass("com.baida.mybatis.plus.base.BaseService");
            strategy.setSuperServiceImplClass("com.baida.mybatis.plus.base.BaseServiceImpl");
        } else {
            strategy.setSuperServiceClass("com.baomidou.mybatisplus.extension.service.IService");
            strategy.setSuperServiceImplClass("com.baomidou.mybatisplus.extension.service.impl.ServiceImpl");
        }
        // 自定义 controller 父类
//		strategy.setSuperControllerClass("org.springblade.core.boot.ctrl.BladeController");
        strategy.setEntityBuilderModel(false);
        strategy.setEntityLombokModel(true);
        strategy.setControllerMappingHyphenStyle(true);
        strategy.setLogicDeleteFieldName("is_deleted");
        mpg.setStrategy(strategy);
        // 包配置
        PackageConfig pc = new PackageConfig();
        // 控制台扫描
        pc.setModuleName(null);
        pc.setParent(packageName);
        pc.setController("controller");
        pc.setEntity("model.po");
        pc.setXml("mapper");
        mpg.setPackageInfo(pc);
        mpg.setCfg(getInjectionConfig());
        mpg.execute();
    }

    private InjectionConfig getInjectionConfig() {
        String servicePackage = serviceName.split("-").length > 1 ? serviceName.split("-")[1] : serviceName;
        // 自定义配置
        Map<String, Object> map = new HashMap<>(16);
        InjectionConfig cfg = new InjectionConfig() {
            @Override
            public void initMap() {
                map.put("moduleName", moduleName);
                map.put("serviceName", serviceName);
                map.put("servicePackage", servicePackage);
                map.put("servicePackageLowerCase", servicePackage.toLowerCase());
                map.put("tenantColumn", tenantColumn);
                this.setMap(map);
            }
        };
        List<FileOutConfig> focList = new ArrayList<>();

        //VO
        focList.add(new FileOutConfig("/templates/mvc/entityVO.java.vm") {
            @Override
            public String outputFile(TableInfo tableInfo) {
                return getOutputDir() + "/" + packageName.replace(".", "/") + "/" + "model/vo" + "/" + tableInfo.getEntityName() + "VO" + StringPool.DOT_JAVA;
            }
        });
        //DTO
        focList.add(new FileOutConfig("/templates/mvc/entityDTO.java.vm") {
            @Override
            public String outputFile(TableInfo tableInfo) {
                return getOutputDir() + "/" + packageName.replace(".", "/") + "/" + "model/dto" + "/" + tableInfo.getEntityName() + "DTO" + StringPool.DOT_JAVA;
            }
        });
        //对项目内部代码覆盖
        if (cover) {
            //mapper.xml
            focList.add(new FileOutConfig("/templates/mvc/mapper.xml.vm") {
                @Override
                public String outputFile(TableInfo tableInfo) {
                    return serviceName + "-dao/src/main/resources/mapper/" + tableInfo.getEntityName() + "Mapper.xml";
                }
            });
            //mapper.java
            focList.add(new FileOutConfig("/templates/mvc/mapper.java.vm") {
                @Override
                public String outputFile(TableInfo tableInfo) {
                    return serviceName + "-dao/src/main/java/" + packageName.replace(".", "/") + "/mapper/" + tableInfo.getEntityName() + "Mapper.java";
                }
            });
            //entity.java
            focList.add(new FileOutConfig("/templates/mvc/entity.java.vm") {
                @Override
                public String outputFile(TableInfo tableInfo) {
                    return serviceName + "-dao/src/main/java/" + packageName.replace(".", "/") + "/model/po/" + tableInfo.getEntityName() + ".java";
                }
            });
        }
        cfg.setFileOutConfigList(focList);
        return cfg;
    }


    /**
     * 生成到项目中
     *
     * @return outputDir
     */
    public String getOutputDir() {
        return packageDir + "/src/main/java";
    }

    /**
     * 生成到Web项目中
     *
     * @return outputDir
     */
    public String getOutputWebDir() {
        return (StrUtil.isBlank(packageWebDir) ? System.getProperty("user.dir") : packageWebDir) + "/src";
    }

    /**
     * 页面生成的文件名
     */
    private String getGeneratorViewPath(String viewOutputDir, TableInfo tableInfo, String suffixPath) {
        String name = StringUtils.firstToLowerCase(tableInfo.getEntityName());
        String path = viewOutputDir + "/" + name + "/" + name + suffixPath;
        File viewDir = new File(path).getParentFile();
        if (!viewDir.exists()) {
            viewDir.mkdirs();
        }
        return path;
    }


}
