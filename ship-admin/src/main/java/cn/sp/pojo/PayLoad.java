package cn.sp.pojo;


/**
 * Created by 2YSP on 2019/8/26.
 */
public class PayLoad {

    private Integer userId;

    private String name;

    public PayLoad() {
    }

    public PayLoad(Integer userId, String name) {
        this.userId = userId;
        this.name = name;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


}
