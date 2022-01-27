package org.pizza.model.page;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author 高巍
 * @since 2020/11/17 5:18 下午
 */
@Data
@Accessors(chain = true)
@ToString
@ApiModel(description = "排序字段")
public class OrderItem implements Serializable {


    private static final long serialVersionUID = 1L;
    /**
     * 需要进行排序的字段
     */
    @ApiModelProperty(value = "字段",example = "id")
    private String column;

    /**
     * 是否正序排列，默认 true
     */
    @ApiModelProperty(value = "是否正序",example = "false")
    private boolean asc = false;

    public static OrderItem asc(String column) {
        return build(column, true);
    }

    public static OrderItem desc(String column) {
        return build(column, false);
    }

    public static List<OrderItem> ascs(String... columns) {
        return Arrays.stream(columns).map(OrderItem::asc).collect(Collectors.toList());
    }

    public static List<OrderItem> descs(String... columns) {
        return Arrays.stream(columns).map(OrderItem::desc).collect(Collectors.toList());
    }

    private static OrderItem build(String column, boolean asc) {
        OrderItem item = new OrderItem();
        item.setColumn(column);
        item.setAsc(asc);
        return item;
    }
}
