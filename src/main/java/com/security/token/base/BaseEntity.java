package com.security.token.base;

import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

/**
 * 基础实体抽象类
 *
 * @author : OlinH
 * @version : v1.0
 * @since : 2020/10/30
 */
@Data
@EqualsAndHashCode
public abstract class BaseEntity<T> implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 主键id，序列化字符串，分配一个主键id
     */
    @JsonSerialize(using = ToStringSerializer.class)
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private Long id;

    /**
     * 创建人
     */
    @TableField(value = "create_user_id", fill = FieldFill.INSERT)
    private String createUserId;

    /**
     * 创建时间，序列化字符串的格式如下
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    @TableField(value = "create_time", fill = FieldFill.INSERT)
    private Date createTime;

    /**
     * 修改人
     */
    @TableField(value = "update_user_id",
            insertStrategy = FieldStrategy.IGNORED,
            updateStrategy = FieldStrategy.IGNORED,
            fill = FieldFill.UPDATE)
    private String updateUserId;

    /**
     * 修改时间，序列化字符串的格式如下
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    @TableField(value = "update_time",
            insertStrategy = FieldStrategy.IGNORED,
            updateStrategy = FieldStrategy.IGNORED,
            fill = FieldFill.UPDATE)
    private Date updateTime;

    /**
     * 是否删除标识符，逻辑删除，避免物理删除（0正常 1已删除）
     */
    @TableLogic
    @TableField(value = "is_deleted", fill = FieldFill.INSERT_UPDATE)
    private Integer isDeleted;
}
