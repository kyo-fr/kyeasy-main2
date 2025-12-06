package org.ares.cloud.ws.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Update;
import org.ares.cloud.ws.entity.NotificationMessage;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author hugo
 * @version 1.0
 * @description: 消息通知Mapper
 * @date 2024/11/11 16:30
 */
@Mapper
public interface NotificationMessageMapper extends BaseMapper<NotificationMessage> {

    
    /**
     * 更新消息为已读状态
     * @param id 消息ID
     * @return 影响行数
     */
    @Update("UPDATE ws_notification_message SET is_read = 1, update_time = SYSTIMESTAMP WHERE id = #{id}")
    int updateReadStatus(@Param("id") Long id);
    
    /**
     * 批量更新消息为已读状态
     * @param ids 消息ID列表
     * @return 影响行数
     */
    @Update("UPDATE ws_notification_message SET is_read = 1, update_time = SYSTIMESTAMP WHERE id IN "
           + "<foreach collection='ids' item='id' open='(' separator=',' close=')'>"
           + "#{id}"
           + "</foreach>")
    int batchUpdateReadStatus(@Param("ids") List<Long> ids);
}