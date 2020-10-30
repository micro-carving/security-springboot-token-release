package com.security.token.entity;

import com.security.token.enums.ResultEnum;
import lombok.Data;
import lombok.Setter;

import java.io.Serializable;

/**
 * TODO
 *
 * @author : OlinH
 * @version : v1.0
 * @since : 2020/10/31
 */
@Setter
@Data
public class Result implements Serializable {

    private ResultEnum resultCode;
    private Object data;

    private Result(){}

    /**
     * 成功
     *
     * @return {BaseResult}
     */
    public static Result success() {
        Result result  = new Result();
        result.setResultCode(ResultEnum.SUCCESS);
        return result;
    }

    /**
     * 成功
     *
     * @param data ：实体对象数据
     * @return ：{BaseResult}
     */
    public static Result success(Object data) {
        Result result  = new Result();
        result.setResultCode(ResultEnum.SUCCESS);
        result.setData(data);
        return result;
    }

    /**
     * 失败
     *
     * @param resultCode ：结果枚举类状态码
     * @return ：{BaseResult}
     */
    public static Result failure(ResultEnum resultCode) {
        Result result  = new Result();
        result.setResultCode(resultCode);
        return result;
    }
}
