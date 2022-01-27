
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
import org.pizza.mybatis.plus.base.BaseEntity;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 领域驱动代码生成器
 *
 * @author gv
 */
@Data
@Slf4j
public class DDDPlusCodeGenerator {
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
     * key->表名 value->所在领域
     */
    private HashMap<String, String> tableDomainMapping;
    /**
     * 表前缀
     */
    public  String[] tablePrefix;
    /**
     * 是否包含基础业务字段
     */
    private Boolean hasSuperEntity = Boolean.TRUE;

    /**
     * 基础业务字段
     */
    private String[] superEntityColumns = {"create_time", "create_user", "update_time", "update_user", "deleted", "tenant_id"};
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
        //模板配置
        TemplateConfig templateConfig = new TemplateConfig();
        templateConfig.disable(TemplateType.ENTITY, TemplateType.SERVICE, TemplateType.CONTROLLER,TemplateType.MAPPER,TemplateType.XML);
        mpg.setTemplate(templateConfig);
        //全局配置
        GlobalConfig gc = getGlobalConfig(config);
        mpg.setGlobalConfig(gc);
        //数据库配置
        DataSourceConfig dsc = getDataSourceConfig(config);
        mpg.setDataSource(dsc);
        // 策略配置
        StrategyConfig strategy = getStrategyConfig();
        mpg.setStrategy(strategy);
        // 包配置
        PackageConfig pc = getPackageConfig();
        mpg.setPackageInfo(pc);
        //注入配置
        mpg.setCfg(getInjectionConfig());
        mpg.execute();
    }

    private PackageConfig getPackageConfig() {
        // 包配置
        PackageConfig pc = new PackageConfig();
        // 控制台扫描
        pc.setModuleName(null);
        pc.setParent(packageName);
        pc.setEntity("model.po");
        pc.setXml("mapper");
        return pc;
    }

    private StrategyConfig getStrategyConfig() {
        // 策略配置
        StrategyConfig strategy = new StrategyConfig();
        // strategy.setCapitalMode(true);// 全局大写命名
        // strategy.setDbColumnUnderline(true);//全局下划线命名
        strategy.setNaming(NamingStrategy.underline_to_camel);
        strategy.setColumnNaming(NamingStrategy.underline_to_camel);
        strategy.setTablePrefix(tablePrefix);
        if (tableDomainMapping.size() > 0) {
            strategy.setInclude(tableDomainMapping.keySet().toArray(new String[]{}));
        }
        if (this.hasSuperEntity) {
            strategy.setSuperEntityClass(BaseEntity.class);
            strategy.setSuperEntityColumns(this.superEntityColumns);
        }
        strategy.setChainModel(true);
        strategy.setEntityLombokModel(true);
        strategy.setControllerMappingHyphenStyle(true);
        strategy.setLogicDeleteFieldName("deleted");
        return strategy;
    }

    private GlobalConfig getGlobalConfig(CodeConfig config) {
        //全局配置
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
        gc.setSwagger2(isSwagger2);
        return gc;
    }

    private DataSourceConfig getDataSourceConfig(CodeConfig config) {
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
        return dsc;
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
        focList.add(new FileOutConfig("/templates/ddd/entityVO.java.vm") {
            @Override
            public String outputFile(TableInfo tableInfo) {
                String domainPkg = tableDomainMapping.get(tableInfo.getName());
                //特殊处理
                tableInfo.setServiceName(domainPkg+".vo");
                return getOutputDir() + "/" + packageName.replace(".", "/") + "/" +domainPkg+ "/vo" + "/" + tableInfo.getEntityName() + "VO" + StringPool.DOT_JAVA;
            }
        });
        //DTO
        focList.add(new FileOutConfig("/templates/ddd/entityDTO.java.vm") {
            @Override
            public String outputFile(TableInfo tableInfo) {
                String domainPkg = tableDomainMapping.get(tableInfo.getName());
                //特殊处理
                tableInfo.setServiceName(domainPkg+".dto");
                return getOutputDir() + "/" + packageName.replace(".", "/") + "/" + domainPkg + "/dto" + "/" + tableInfo.getEntityName() + "DTO" + StringPool.DOT_JAVA;
            }
        });
        //DO
        focList.add(new FileOutConfig("/templates/ddd/entityDO.java.vm") {
            @Override
            public String outputFile(TableInfo tableInfo) {
                String domainPkg = tableDomainMapping.get(tableInfo.getName());
                //特殊处理
                tableInfo.setServiceName("domain."+domainPkg+".entity");
                return getOutputDir() + "/" + packageName.replace(".", "/") + "/" +domainPkg+ "/entity" + "/" + tableInfo.getEntityName() + StringPool.DOT_JAVA;
            }
        });
        //对项目内部代码覆盖
        //mapper.xml
        focList.add(new FileOutConfig("/templates/ddd/mapper.xml.vm") {
            @Override
            public String outputFile(TableInfo tableInfo) {
                String domainPkg = tableDomainMapping.get(tableInfo.getName());
                tableInfo.setServiceName(domainPkg+".po");
                return getOutputDir() + "/" + packageName.replace(".", "/") +"/" +domainPkg+ "/mapper/" + tableInfo.getEntityName() + "Mapper.xml";
            }
        });
        //mapper.java
        focList.add(new FileOutConfig("/templates/ddd/mapper.java.vm") {
            @Override
            public String outputFile(TableInfo tableInfo) {
                String domainPkg = tableDomainMapping.get(tableInfo.getName());
                //特殊处理
                tableInfo.setServiceName(domainPkg+".mysql.mapper");
                return getOutputDir() + "/" + packageName.replace(".", "/") +"/" +domainPkg+ "/mapper/" + tableInfo.getEntityName() + "Mapper.java";
            }
        });
        //PO
        focList.add(new FileOutConfig("/templates/ddd/entity.java.vm") {
            @Override
            public String outputFile(TableInfo tableInfo) {
                tableInfo.setConvert(true);
                String domainPkg = tableDomainMapping.get(tableInfo.getName());
                tableInfo.setServiceName(domainPkg);
                return getOutputDir() + "/" + packageName.replace(".", "/") + "/" +domainPkg+"/po/" + tableInfo.getEntityName() + "PO.java";
            }
        });
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
