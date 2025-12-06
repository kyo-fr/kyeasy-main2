package org.ares.cloud.common.convert;

import java.util.List;

/**
 * @author hugo  tangxkwork@163.com
 * @description 类型转换
 * @date 2024/01/20/17:27
 **/
public interface BaseConvert<E ,D> {
    /**
     * 将实体转换为数据传输对象。
     *
     * @param entity 实体。
     * @return 数据传输对象。
     */
    D toDto(E entity);

    /**
     * 将多个实体转换为数据传输对象。
     *
     * @param list 实体列表。
     * @return 数据传输对象列表。
     */
    List<D> listToDto(List<E> list);

    /**
     * 将数据传输对象转换为实体。
     *
     * @param dto 数据传输对象。
     * @return 实体。
     */
    E toEntity(D dto);

    /**
     * 将多个数据传输对象转换为实体。
     *
     * @param list 数据传输对象列表。
     * @return 实体列表。
     */
    List<E> listToEntities(List<D> list);
}
