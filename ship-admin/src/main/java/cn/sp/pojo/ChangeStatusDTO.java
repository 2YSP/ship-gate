package cn.sp.pojo;

/**
 * @Author: Ship
 * @Description:
 * @Date: Created in 2021/1/5
 */
public class ChangeStatusDTO {

    private Integer id;

    private Byte enabled;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Byte getEnabled() {
        return enabled;
    }

    public void setEnabled(Byte enabled) {
        this.enabled = enabled;
    }
}
