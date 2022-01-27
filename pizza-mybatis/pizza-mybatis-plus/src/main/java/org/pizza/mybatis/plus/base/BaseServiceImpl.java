
package org.pizza.mybatis.plus.base;

import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.validation.annotation.Validated;

import java.util.Date;
import java.util.List;

/**
 * 业务封装基础类
 *
 * @param <M> mapper
 * @param <T> model
 * @author gv
 */
@Validated
public class BaseServiceImpl<M extends BaseMapper<T>, T extends BaseEntity> extends ServiceImpl<M, T> implements BaseService<T> {

	@Override
	public boolean save(T entity) {
		Date now = DateUtil.date();
        entity.setCreateTime(now);
        entity.setUpdateTime(now);
        return super.save(entity);
	}

	@Override
	public boolean updateById(T entity) {
		entity.setUpdateTime(DateUtil.date());
		return super.updateById(entity);
	}

	@Override
	public boolean deleteLogic( List<Long> ids) {
		return super.removeByIds(ids);
	}

}
