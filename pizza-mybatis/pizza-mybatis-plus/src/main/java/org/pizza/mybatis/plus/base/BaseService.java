
package org.pizza.mybatis.plus.base;

import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * 基础业务接口
 *
 * @param <T>
 * @author gv
 */
public interface BaseService<T> extends IService<T> {

	/**
	 * 逻辑删除
	 *
	 * @param ids id集合(逗号分隔)
	 * @return boolean
	 */
	boolean deleteLogic( List<Long> ids);

}
