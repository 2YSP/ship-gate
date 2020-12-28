package cn.sp.constants;

/**
 * @Author: Ship
 * @Description:
 * @Date: Created in 2020/12/28
 */
public enum OperationTypeEnum {
    /**
     * 新增
     */
    INSERT("INSERT","新增"),
    /**
     * 修改
     */
    UPDATE("UPDATE","修改"),
    /**
     * 删除
     */
    DELETE("DELETE","删除");

    private String code;

    private String desc;

    OperationTypeEnum(String code,String desc){
        this.code = code;
        this.desc = desc;
    }

    public String getCode() {
        return code;
    }

    public String getDesc() {
        return desc;
    }
}
