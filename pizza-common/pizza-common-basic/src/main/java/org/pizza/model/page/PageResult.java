package org.pizza.model.page;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.pizza.model.DTO;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;

import static java.util.stream.Collectors.toList;

/**
 * @author 高巍
 * @since 2020/11/17 5:09 下午
 */
@ApiModel(description = "分页结果")
@Data
public class PageResult<T> implements DTO {

    private static final long serialVersionUID = 8545996863226528798L;

    /**
     * 查询数据列表
     */
    @ApiModelProperty("查询数据列表")
    protected List<T> records = Collections.emptyList();

    /**
     * 总数
     */
    @ApiModelProperty("总数")
    protected long total = 0;
    /**
     * 每页显示条数，默认 10
     */
    @ApiModelProperty("每页显示条数")
    protected long size = 10;

    /**
     * 当前页
     */
    @ApiModelProperty("current")
    protected long current = 1;

    /**
     * 排序字段信息
     */
    @ApiModelProperty("排序字段信息")
    protected List<OrderItem> orders = new ArrayList<>();

    /**
     * 自动优化 COUNT SQL
     */
    protected boolean optimizeCountSql = true;
    /**
     * 是否进行 count 查询
     */
    protected boolean isSearchCount = true;
    /**
     * 是否命中count缓存
     */
    protected boolean hitCount = false;

    public PageResult() {
    }

    /**
     * 分页构造函数
     *
     * @param current 当前页
     * @param size    每页显示条数
     */
    public PageResult(long current, long size) {
        this(current, size, 0);
    }

    public PageResult(long current, long size, long total) {
        this(current, size, total, true);
    }

    public PageResult(long current, long size, boolean isSearchCount) {
        this(current, size, 0, isSearchCount);
    }

    public PageResult(long current, long size, long total, boolean isSearchCount) {
        if (current > 1) {
            this.current = current;
        }
        this.size = size;
        this.total = total;
        this.isSearchCount = isSearchCount;
    }

    /**
     * 是否存在上一页
     *
     * @return true / false
     */
    public boolean hasPrevious() {
        return this.current > 1;
    }

    /**
     * 是否存在下一页
     *
     * @return true / false
     */
    public boolean hasNext() {
        return this.current < this.getPages();
    }

    /**
     * 当前分页总页数
     */
    private long getPages() {
        if (getSize() == 0) {
            return 0L;
        }
        long pages = getTotal() / getSize();
        if (getTotal() % getSize() != 0) {
            pages++;
        }
        return pages;
    }

    /**
     * 计算当前分页偏移量
     */
    public long offset() {
        return getCurrent() > 0 ? (getCurrent() - 1) * getSize() : 0;
    }

    /**
     * IPage 的泛型转换
     *
     * @param mapper 转换函数
     * @param <R>    转换后的泛型
     * @return 转换泛型后的 IPage
     */
    @SuppressWarnings("unchecked")
    public <R> PageResult<R> convert(Function<? super T, ? extends R> mapper) {
        List<R> collect = this.getRecords().stream().map(mapper).collect(toList());
        return ((PageResult<R>) this).setRecords(collect);
    }


    public List<T> getRecords() {
        return this.records;
    }

    public PageResult<T> setRecords(List<T> records) {
        this.records = records;
        return this;
    }

    public long getTotal() {
        return this.total;
    }

    public PageResult<T> setTotal(long total) {
        this.total = total;
        return this;
    }

    public long getSize() {
        return this.size;
    }

    public PageResult<T> setSize(long size) {
        this.size = size;
        return this;
    }

    public long getCurrent() {
        return this.current;
    }

    public PageResult<T> setCurrent(long current) {
        this.current = current;
        return this;
    }


    /**
     * 查找 order 中正序排序的字段数组
     *
     * @param filter 过滤器
     * @return 返回正序排列的字段数组
     */
    private String[] mapOrderToArray(Predicate<OrderItem> filter) {
        List<String> columns = new ArrayList<>(orders.size());
        orders.forEach(i -> {
            if (filter.test(i)) {
                columns.add(i.getColumn());
            }
        });
        return columns.toArray(new String[0]);
    }

    /**
     * 移除符合条件的条件
     *
     * @param filter 条件判断
     */
    private void removeOrder(Predicate<OrderItem> filter) {
        for (int i = orders.size() - 1; i >= 0; i--) {
            if (filter.test(orders.get(i))) {
                orders.remove(i);
            }
        }
    }

    /**
     * 添加新的排序条件，构造条件可以使用工厂：
     *
     * @param items 条件
     * @return 返回分页参数本身
     */
    public PageResult<T> addOrder(OrderItem... items) {
        orders.addAll(Arrays.asList(items));
        return this;
    }

    /**
     * 添加新的排序条件，构造条件可以使用工厂：
     *
     * @param items 条件
     * @return 返回分页参数本身
     */
    public PageResult<T> addOrder(List<OrderItem> items) {
        orders.addAll(items);
        return this;
    }


    public List<OrderItem> orders() {
        return getOrders();
    }

    public List<OrderItem> getOrders() {
        return orders;
    }

    public void setOrders(List<OrderItem> orders) {
        this.orders = orders;
    }

    public boolean optimizeCountSql() {
        return optimizeCountSql;
    }

    public boolean isOptimizeCountSql() {
        return optimizeCountSql();
    }

    public boolean isSearchCount() {
        if (total < 0) {
            return false;
        }
        return isSearchCount;
    }

    public PageResult<T> setSearchCount(boolean isSearchCount) {
        this.isSearchCount = isSearchCount;
        return this;
    }

    public PageResult<T> setOptimizeCountSql(boolean optimizeCountSql) {
        this.optimizeCountSql = optimizeCountSql;
        return this;
    }

    public void hitCount(boolean hit) {
        this.hitCount = hit;
    }

    public void setHitCount(boolean hit) {
        this.hitCount = hit;
    }

    public boolean isHitCount() {
        return hitCount;
    }


}
