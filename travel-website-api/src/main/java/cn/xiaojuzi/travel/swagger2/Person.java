package cn.xiaojuzi.travel.swagger2;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value="用户",description="平台注册用户模型")
public class Person {
    @ApiModelProperty(value="昵称",name="name",dataType = "String",required = true)
    private String name;
    @ApiModelProperty(value="性别",name="sex",dataType = "String",required = true)
    private String sex;

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }
}
