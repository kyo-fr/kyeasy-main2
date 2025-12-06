package org.ares.cloud.job.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.ares.cloud.job.entity.PlatformApprovalEntity;

import java.util.List;

@Mapper
public interface MerchantSubscribeMapper {
    //查询所有商户订阅审批数据 根据订阅类型为订阅且到期时间小于当前时间
    @Select("SELECT user_id ,TENANT_ID, id,MEMORY, END_TIME FROM PLATFORM_APPROVAL    WHERE   SUB_STATUS = 'sub'")
    List<PlatformApprovalEntity> getMerchantSubscribes();

    @Update("UPDATE PLATFORM_APPROVAL SET SUB_STATUS = #{subStatus} WHERE id = #{id}")
    int updateSubStatus(String subStatus, String id);

    @Select("select  sum(CHANGE_MEMORY) as usedCounts from  PLATFORM_APPROVAL_RECORD where TENANT_ID=#{tenantId} and APPROVAL_ID=#{approvalId} and  RECORD_TYPE in ('used','over')")
    long selectTenantUsedCount(String tenantId,String approvalId);

    @Select("select  sum(CHANGE_MEMORY) as sendCounts from  PLATFORM_APPROVAL_RECORD where TENANT_ID=#{tenantId} and APPROVAL_ID=#{approvalId} and  RECORD_TYPE ='send'")
    long selectTenantSendCount(String tenantId,String approvalId);

    @Insert("insert into PLATFORM_APPROVAL_RECORD(id,TENANT_ID,APPROVAL_ID,CHANGE_MEMORY,CREATE_TIME,DESCRIPTION,RECORD_TYPE) values(#{id},#{tenantId},#{approvalId},#{changeMemory},#{createTime},#{description},#{recordType})")
    int insertApprovalRecord(String id,String tenantId,String approvalId,long changeMemory,long createTime,String description,String recordType);
}
