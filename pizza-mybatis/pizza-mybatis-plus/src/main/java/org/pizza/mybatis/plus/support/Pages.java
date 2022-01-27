
package org.pizza.mybatis.plus.support;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.convert.Convert;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.pizza.model.page.PageQuery;
import org.pizza.model.page.PageResult;
import org.pizza.util.Convertor;
import org.springframework.beans.BeanUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * 分页工具
 *
 * @author gv
 */
public class Pages {

    /**
     * 转化
     *
     * @param query 查询条件
     * @return IPage mybatis plus中的Page
     */
    public static <T> IPage<T> convert(PageQuery query) {
        Page<T> page = new Page<>((long) Convert.toInt(query.getCurrent(), 1), (long) Convert.toInt(query.getSize(), 10));
        if (query.getOrders() != null) {
            List<OrderItem> convert = Convertor.convert(query.getOrders(), OrderItem.class);
            page.addOrder(convert);
        }
        return page;
    }

    /**
     * 转换
     * @param page
     * @param entityClass
     * @param <T>
     * @param <Entity>
     * @return
     */
    public static <T,Entity> PageResult<Entity> convert(IPage<T> page, Class<Entity> entityClass){
        //分页结果转换
        PageResult<Entity> pageResult = new PageResult<>((int) page.getCurrent(), (int) page.getSize(), (int) page.getTotal());
        //排序
        if (page.orders() != null) {
            List< org.pizza.model.page.OrderItem> convert = Convertor.convert(page.orders(), org.pizza.model.page.OrderItem.class);
            pageResult.setOrders(convert);
        }
        pageResult.setHitCount(page.isHitCount());
        pageResult.setSearchCount(page.isSearchCount());
        pageResult.setOptimizeCountSql(page.optimizeCountSql());
        //结果集转换
        List<Entity> collect = page.getRecords()
                .stream()
                .filter(Objects::nonNull)
                .map((element) -> Convertor.convert(entityClass, element))
                .collect(Collectors.toList());
        pageResult.setRecords(collect);
        return pageResult;
    }

    /**
     * 根据实体类快速生成 QueryWrapper
     * @param poClass po类型
     * @param entity  实体类型
     * @param <T>
     * @param <Entity>
     * @return
     */
    public static <T,Entity> QueryWrapper<T> assemble(Class<T> poClass,Entity entity) {
        return new QueryWrapper<>(Convertor.convert(poClass, entity));
    }
    /**
     * 获取mybatis plus中的QueryWrapper
     *
     * @param entity 实体
     * @param <T>    类型
     * @return QueryWrapper
     */
    public static <T> QueryWrapper<T> assemble(T entity) {
        return new QueryWrapper<>(entity);
    }

    /**
     * 获取mybatis plus中的QueryWrapper
     *
     * @param query 查询条件
     * @param clazz 实体类
     * @param <T>   类型
     * @return QueryWrapper
     */
    public static <T> QueryWrapper<T> assemble(Map<String, Object> query, Class<T> clazz) {
        HashMap<String, Object> exclude = CollUtil.newHashMap();
        exclude.put("page", "page");
        exclude.put("size", "size");
        exclude.put("ascs", "ascs");
        exclude.put("descs", "descs");
        return assemble(query, exclude, clazz);
    }

    /**
     * 获取mybatis plus中的QueryWrapper
     *
     * @param query   查询条件
     * @param exclude 排除的查询条件
     * @param clazz   实体类
     * @param <T>     类型
     * @return QueryWrapper
     */
    public static <T> QueryWrapper<T> assemble(Map<String, Object> query, Map<String, Object> exclude, Class<T> clazz) {
        exclude.forEach((k, v) -> {
            query.remove(k);
        });
        QueryWrapper<T> qw = new QueryWrapper<T>();
        qw.setEntity(BeanUtils.instantiateClass(clazz));
        SqlKeyword.buildCondition(query, qw);
        return qw;
    }

}
