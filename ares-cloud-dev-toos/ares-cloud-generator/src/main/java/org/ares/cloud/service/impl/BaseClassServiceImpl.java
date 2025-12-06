package org.ares.cloud.service.impl;

import org.ares.cloud.common.dto.PageResult;
import org.ares.cloud.entity.BaseClassEntity;
import org.ares.cloud.database.service.impl.BaseServiceImpl;
import org.ares.cloud.query.GenQuery;
import org.ares.cloud.repository.BaseClassRepository;
import org.ares.cloud.service.BaseClassService;
import com.baomidou.mybatisplus.core.metadata.IPage;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * @author hugo  tangxkwork@163.com
 * @description 基类服务
 * @date 2024/01/23/22:56
 **/
@Service
@AllArgsConstructor
public class BaseClassServiceImpl extends BaseServiceImpl<BaseClassRepository, BaseClassEntity> implements BaseClassService {
    @Override
    public PageResult<BaseClassEntity> page(GenQuery query) {
        IPage<BaseClassEntity> page = baseMapper.selectPage(
                getPage(query), getWrapper(query)
        );

        return new PageResult<>(page.getRecords(), page.getTotal());
    }

    @Override
    public List<BaseClassEntity> getList() {
        return baseMapper.selectList(null);
    }

    @Override
    public boolean save(BaseClassEntity entity) {
        entity.setCreateTime(new Date());
        return super.save(entity);
    }
}
