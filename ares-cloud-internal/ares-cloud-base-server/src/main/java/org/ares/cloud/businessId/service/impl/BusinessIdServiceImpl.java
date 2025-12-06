package org.ares.cloud.businessId.service.impl;


import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import jakarta.annotation.Resource;
import lombok.AllArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.ares.cloud.businessId.convert.BusinessIdConvert;
import org.ares.cloud.businessId.dto.BusinessIdDto;
import org.ares.cloud.businessId.entity.BusinessIdEntity;
import org.ares.cloud.businessId.enums.BusinessIdError;
import org.ares.cloud.businessId.query.BusinessIdQuery;
import org.ares.cloud.businessId.repository.BusinessIdRepository;
import org.ares.cloud.businessId.service.BusinessIdService;
import org.ares.cloud.common.context.ApplicationContext;
import org.ares.cloud.common.dto.PageResult;
import org.ares.cloud.common.exception.BaseException;
import org.ares.cloud.common.exception.BusinessException;
import org.ares.cloud.common.utils.DateUtils;
import org.ares.cloud.database.service.impl.BaseServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.net.InetAddress;

/**
* @author hugo tangxkwork@163.com
* @description 系统业务id 服务实现
* @version 1.0.0
* @date 2024-10-13
*/
@Service
@AllArgsConstructor
public class BusinessIdServiceImpl extends BaseServiceImpl<BusinessIdRepository, BusinessIdEntity> implements BusinessIdService{

    @Resource
    private BusinessIdConvert convert;

    // ==================== 短雪花ID生成器相关 ====================
    
    /**
     * 机器ID（0-99），根据IP地址最后两位生成
     */
    private static final int MACHINE_ID;
    
    /**
     * 序列号（0-9999）
     */
    private static int sequence = 0;
    
    /**
     * 上次生成ID的时间戳（秒级）
     */
    private static long lastTimestamp = -1L;
    
    static {
        // 根据IP地址生成机器ID
        int machineId = 12; // 默认值
        try {
            InetAddress ip = InetAddress.getLocalHost();
            byte[] ipAddress = ip.getAddress();
            // 使用IP地址最后一位对100取模
            machineId = Math.abs(ipAddress[ipAddress.length - 1]) % 100;
        } catch (Exception e) {
            // 如果获取IP失败，使用默认值
        }
        MACHINE_ID = machineId;
    }

    /**
    * 创建
    * @param dto 数据模型
    */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void create(BusinessIdDto dto) {
        BusinessIdEntity entity = convert.toEntity(dto);
        this.baseMapper.insert(entity);
    }

    /**
    * 批量创建
    * @param dos 数据模型集合
    */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void create(List<BusinessIdDto> dos) {
        List<BusinessIdEntity> entities = convert.listToEntities(dos);
        this.saveBatch(entities); 
    }
        
    /**
    * 更新
    * @param dto 数据模型
    */
    @Override
    public void update(BusinessIdDto dto) {
        BusinessIdEntity entity = convert.toEntity(dto);
        this.updateById(entity);
    }

    /**
    * 批量更新
    * @param dos 数据模型集合
    */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(List<BusinessIdDto> dos) {
        List<BusinessIdEntity> entities = convert.listToEntities(dos);
        this.saveOrUpdateBatch(entities);
    }

    /**
    * 根据id删除
    * @param id  主键
    */
    @Override
    public void deleteById(String id) {
        this.baseMapper.deleteById(id);
    }

    /**
    * 根据ids删除
    * @param ids 主键集合
    */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteByIds(List<String> ids) {
        this.baseMapper.deleteBatchIds(ids);
    }

    /**
    * 根据id获取详情
    * @param id 主键
    */
    @Override
    public BusinessIdDto loadById(String id) {
        BusinessIdEntity entity = this.baseMapper.selectById(id);
        if (entity != null){
            return  convert.toDto(entity);
        }
        return null;
    }
    /**
    * 根据id获取详情
    * @param ids 主键
    */
    @Override
    public List<BusinessIdDto> loadByIds(List<String> ids) {
        List<BusinessIdEntity> entities = this.baseMapper.selectBatchIds(ids);
        return convert.listToDto(entities);
    }

    /**
    * 加载所有数据
    * @return 数据集合
    */
    @Override
    public List<BusinessIdDto> loadAll() {
        LambdaQueryWrapper<BusinessIdEntity> wrapper = new LambdaQueryWrapper<>();
        List<BusinessIdEntity> entities =  this.baseMapper.selectList(wrapper);
        return convert.listToDto(entities);
    }

    /**
    * 查询列表
    * @param query 查询对象
    * @return
    */
    @Override
    public PageResult<BusinessIdDto> loadList(BusinessIdQuery query) {
        IPage<BusinessIdEntity> page = baseMapper.selectPage(getPage(query), getWrapper(query));
        return new PageResult<BusinessIdDto>(convert.listToDto(page.getRecords()), page.getTotal());
    }

    @Override
    public String generateRandomBusinessId() {
        // 获取当前日期的格式为 yyyyMMdd
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        String datePart = sdf.format(new Date());
        // 生成8位随机数
        Random random = new Random();
        int randomNum = random.nextInt(99999999);
        String randomPart = String.format("%08d", randomNum); // 保证是8位数字

        // 返回组合的业务ID
        return datePart + randomPart;
    }

    @Override
    @Transactional
    public String generateBusinessId(String moduleName) {
        // 获取当前日期
        Date now = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat(DateUtils.YYYYMMDD); // 你可以根据周期灵活调整格式
        String currentDate = dateFormat.format(now);

        // 查询该模块对应的当前最大流水号
        BusinessIdEntity sequence = baseMapper.findByModuleName(moduleName);
        if (sequence == null){
            sequence = initializeSequence(moduleName, currentDate);
        }
        // 检查是否需要重置流水号
        boolean needReset = checkIfNeedReset(sequence, now);
        // 如果需要重置
        if (needReset) {
            sequence.setMaxSequence(0L); // 重置流水号为0
            sequence.setCurrentDate(getCurrentDateForCycle(now, sequence.getCycleType()));
        }
        // 自增流水号
        Long nextSequence = sequence.getMaxSequence() + 1;

        // 更新最大流水号
        sequence.setMaxSequence(nextSequence);
        if (StringUtils.isNotBlank(sequence.getId())){
            baseMapper.updateById(sequence);
        }else{
            baseMapper.insert(sequence);
        }
        StringBuilder stringBuilder = new StringBuilder();
        if(StringUtils.isNotBlank(sequence.getPrefix())){
           stringBuilder.append(sequence.getPrefix());
        }
        if (StringUtils.isNotBlank(sequence.getDateTemp())){
           stringBuilder.append(DateUtils.dateTimeNow(sequence.getDateTemp()));
        }
        // 生成业务ID
        String serialPart = String.format("%08d", nextSequence); // 8位流水号，不足补零
        return stringBuilder.append(serialPart).toString();
    }
    /**
     * 初始化该模块当天/周/月/年内的流水号记录
     * @param moduleName 模块名称
     * @param currentDate 当前日期（yyyyMMdd/yyyymm等）
     * @return 初始化的序列对象
     */
    private BusinessIdEntity initializeSequence(String moduleName, String currentDate) {
        // 创建新流水号记录
        BusinessIdEntity newSequence = new BusinessIdEntity();
        newSequence.setModuleName(moduleName);
        newSequence.setCurrentDate(currentDate);
        newSequence.setMaxSequence(0L); // 初始为 0
        newSequence.setDateTemp(DateUtils.YYYYMMDD);
        newSequence.setCycleType(1);
        return newSequence;
    }
    /**
    * 获取条件
    * @param query
    * @return
    */
    private Wrapper<BusinessIdEntity> getWrapper(BusinessIdQuery query){
        LambdaQueryWrapper<BusinessIdEntity> wrapper = new LambdaQueryWrapper<>();
        if (StringUtils.isBlank(query.getOrder())){
            wrapper.orderByDesc(BusinessIdEntity::getId);
        }
        return wrapper;
    }
    /**
     * 检查是否需要重置流水号
     * @param sequence 当前模块的序列对象
     * @param now 当前时间
     * @return 是否需要重置
     */
    private boolean checkIfNeedReset(BusinessIdEntity sequence, Date now) {
        String currentStoredDate = sequence.getCurrentDate();

        String currentDate = getCurrentDateForCycle(now, sequence.getCycleType());
        if (StringUtils.isBlank(currentDate)){
            return false;
        }
        // 比较当前日期与存储日期是否跨周期
        return !currentStoredDate.equals(currentDate);
    }
    /**
     * 根据周期类型获取当前的日期表示
     * @param now 当前时间
     * @param cycleType 周期类型（日/周/月/年）
     * @return 对应周期的日期字符串
     */
    private String getCurrentDateForCycle(Date now, Integer cycleType) {
        SimpleDateFormat dateFormat;
        switch (cycleType) {
            case 1:
                dateFormat = new SimpleDateFormat("yyyyMMdd"); // 每天重置
                break;
            case 2:
                dateFormat = new SimpleDateFormat("yyyy-'W'ww"); // 每周重置，格式为YYYY-WW
                break;
            case 3:
                dateFormat = new SimpleDateFormat("yyyyMM"); // 每月重置
                break;
            case 4:
                dateFormat = new SimpleDateFormat("yyyy"); // 每年重置
                break;
            default:
               return null;
        }
        return dateFormat.format(now);
    }

    /**
     * 生成16位短雪花ID
     * 格式：秒级时间戳(10位) + 机器ID(2位) + 序列号(4位)
     * 示例：1730015724123001
     * 
     * 特点：
     * 1. 使用秒级时间戳，减少位数
     * 2. 支持单机每秒10000个ID
     * 3. 支持100台机器
     * 4. 全局唯一、趋势递增
     * 
     * @return 16位雪花ID字符串
     */
    @Override
    public synchronized String generateSnowflakeId() {
        long now = System.currentTimeMillis() / 1000; // 秒级时间戳
        
        if (now == lastTimestamp) {
            // 同一秒内，序列号递增
            sequence = (sequence + 1) % 10000;
            if (sequence == 0) {
                // 序列号用完，等待下一秒
                while (now <= lastTimestamp) {
                    now = System.currentTimeMillis() / 1000;
                }
            }
        } else {
            // 新的一秒，序列号重置
            sequence = 0;
            lastTimestamp = now;
        }
        
        // 格式化：10位时间戳 + 2位机器ID + 4位序列号 = 16位
        return String.format("%010d%02d%04d", now, MACHINE_ID, sequence);
    }
}