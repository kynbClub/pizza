package org.pizza.mybatis.plus;


import org.pizza.mybatis.plus.generate.CodeConfig;
import org.pizza.mybatis.plus.generate.DDDPlusCodeGenerator;

import java.util.HashMap;

/**
 * @author 高巍
 * @since 2020/11/19 9:37 上午
 */
public class TestCodeGenerator {
    /**
     * 代码所在服务名
     */
    public static String SERVICE_NAME = "scmp-service-user";
    /**
     * 代码生成的包名
     */
    public static String PACKAGE_NAME = "com.jianxuan.scmp";
    /**
     * 后端代码生成路径
     */
    public static String PACKAGE_DIR = "/Users/gaowei/dev/java_projects/pizza/.code";
    /**
     * 需要去掉的表前缀
     */
    public static String[] TABLE_PREFIX = {};
    /**
     * 返回要生成的包名和其对应的所在领域
     * key->表名 value->所在领域
     * @return
     */
    private static HashMap<String, String> includeTables() {
        HashMap<String, String> map = new HashMap<>();
        //key->表名 value->所在领域
//        map.put("hrms_department", "department");
//        map.put("hrms_department_structure_snapshoot", "department");
//        map.put("hrms_post", "department");
//        map.put("hrms_post_category", "department");

//        map.put("hrms_member_aggregate", "member");
//        map.put("hrms_sync_log", "sync");
//        map.put("hrms_company", "company");
//        map.put("hrms_sync_detail", "sync");
//        map.put("hrms_excel_import_task_log", "excel");
        map.put("user", "user");
        return map;
    }

    public static void main(String[] args) {
        DDDPlusCodeGenerator generator = new DDDPlusCodeGenerator();
        generator.setServiceName(SERVICE_NAME);
        generator.setPackageName(PACKAGE_NAME);
        generator.setTablePrefix(TABLE_PREFIX);
        //覆盖项目内的代码、会覆盖mapper.java、mapper.xml、po对象
        generator.setTableDomainMapping(includeTables());
        generator.setPackageDir(PACKAGE_DIR);
        generator.run(new CodeConfig()
                .setUrl("jdbc:mysql://127.0.0.1:3306/scmp_all?useUnicode=true&characterEncoding=UTF-8&useSSL=true&autoReconnect=true&serverTimezone=Asia/Shanghai")
                .setDriverName("com.mysql.cj.jdbc.Driver")
                .setUsername("root")
                .setPassword("123456789")
                .setAuthor("高巍"));
    }
}
