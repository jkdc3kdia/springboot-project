package com.dego.constant;

/**
 * rabbit相关的一些常量：如exchange、queueName、bindKey、routingKey等
 * 注：为了简便，把direct类型的exchange下的queueName、bindKey、routingKey这几个值统一
 *
 */
public class RabbitConstants {
    /**
     * direct类型的exchange
     */
    public static final String MQ_EX_DIRECT = "yinli.ex.direct";

    /**
     * topic类型的exchange
     */
    public static final String MQ_EX_TOPIC = "yinli.ex.topic";

    /**
     * 消费失败并重试指定次数后的消息
     */
    public final static String ERROR_QUEUE = "qu_error";

    /**
     * wms调拨发scs的queueName
     */
    public static final String QU_WMS_DB_SCS = "qu_wms_db_scs";

    /**
     * 调拨scs发ts的queueName
     */
    public static final String QU_SCS_DB_TS = "qu_scs_db_ts";

    /**
     * bos修改配置参数的routingKey
     */
    public static final String ROU_BOS_PARAM = "rou_bos_param";

    /**
     * oms发送订单到oms的queueName
     */
    public static final String QU_OMS_DB_OMS = "qu_oms_db_oms";

    /**
     * oms发送订单到scs的queueName
     */
    public static final String QU_OMS_DB_SCS = "qu_oms_db_scs";

    /**
     * 延时队列 wms锁定库存需要释放
     */
    public static final String QU_WMS_LOCK_STOCK = "qu_wms_lock_stock";
    /**
     * 延时队列 wms锁定库存需要释放 过期后的监听
     */
    public static final String QU_WMS_LOCK_STOCK_EXPIRE = "qu_wms_lock_stock_expire";

    /**
     * 延时队列 ts锁定库存需要释放
     */
    public static final String QU_TS_LOCK_STOCK = "qu_ts_lock_stock";
    /**
     * 延时队列 ts锁定库存需要释放 过期后的监听
     */
    public static final String QU_TS_LOCK_STOCK_EXPIRE = "qu_ts_lock_stock_expire";
    /**
     * 搜索关键字统计
     */
    public static final String SEARCH_KEYWORD_COUNT = "search_keyword_count";

    /**
     * 商品货架期设置
     */
    public static final String QU_COMMODITY_SHELF_LIFE = "qu_commodity_shelf_life";

    /**
     * 品类货架期设置
     */
    public static final String QU_CATEGORY_SHELF_LIFE = "qu_category_shelf_life";

    /**
     * 用户行为采集
     */
    public static final String QU_USER_BEHAVIOR = "qu_user_behavior";

    /**
     * scs发周转筐到tms的queueName
     */
    public static final String QU_SCS_ZZK_TMS = "qu_scs_zzk_tms";

    /**
     * 商品操作记录
     */
    public static final String QU_COMMODITY_OPERATION_LOG = "qu_commodity_operation_log";
}