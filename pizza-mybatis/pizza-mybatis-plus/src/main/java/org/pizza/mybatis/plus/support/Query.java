package org.pizza.mybatis.plus.support;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author 高巍
 * @since 2020/10/13 4:47 下午
 */
@ApiModel(
        description = "查询条件"
)
@Data
public class Query  implements Serializable {

    @ApiModelProperty("当前页")
    private Integer current;
    @ApiModelProperty("每页的数量")
    private Integer size;
    @ApiModelProperty(
            hidden = true
    )
    private String ascs;
    @ApiModelProperty(
            hidden = true
    )
    private String descs;
}
