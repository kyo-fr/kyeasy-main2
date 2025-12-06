package org.ares.cloud.common.constant.interfaces;

/**
 * @Author hugo  tangxkwork@163.com
 * @description 持久化状态
 * @date 2024/01/17/01:08
 **/
public interface PersistStatus {
    /**
     * 未变化的
     */
    String UNCHANGED = "nrm";


    /**
     * 新增的
     */
    String ADDED = "new";

    /**
     * 修改的
     */
    String MODIFIED = "upd";

    /**
     * 逻辑删除的
     */
    String DELETED = "del";
}
