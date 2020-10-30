package com.security.token.base;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

/**
 * TODO
 *
 * @author : OlinH
 * @version : v1.0
 * @since : 2020/10/30
 */
@Data
@EqualsAndHashCode
public abstract class BaseEntity<T> implements Serializable {
    /**
     * 主键id，序列化字符串，分配一个主键id
     */
    @JsonSerialize(using = ToStringSerializer.class)
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private Long id;

    /**
     * 创建人
     */
    private String createUserId;

    /**
     * 创建时间，序列化字符串的格式如下
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    /**
     * 修改人
     */
    private String updateUserId;

    /**
     * 修改时间，序列化字符串的格式如下
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updateTime;

    /**
     * 是否删除标识符，逻辑删除，避免物理删除（0正常 1已删除）
     */
    @TableLogic
    private Integer isDeleted;

    /**
     * 获取实例
     * @return 返回实体类
     */
    public abstract T buildEntity();
}
