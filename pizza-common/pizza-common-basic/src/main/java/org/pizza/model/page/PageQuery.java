package org.pizza.model.page;

import org.pizza.model.Query;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author 高巍
 * @since 2020/11/17 5:09 下午
 */
@EqualsAndHashCode(callSuper = true)
@ApiModel(description = "查询条件")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PageQuery extends Query {
    @ApiModelProperty(value = "当前页", example = "1")
    private Integer current = 1;
    @ApiModelProperty(value = "每页的数量", example = "10")
    private Integer size = 10;
    @ApiModelProperty("排序字段信息")
    protected List<OrderItem> orders;
}


