package org.lpw.ranch.ad;

import org.lpw.tephra.dao.model.Jsonable;
import org.lpw.tephra.dao.model.ModelSupport;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * @author lpw
 */
@Component(AdModel.NAME + ".model")
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
@Entity(name = AdModel.NAME)
@Table(name = "t_ad")
public class AdModel extends ModelSupport {
    static final String NAME = "ranch.ad";

    private String type; // 类型
    private int sort; // 顺序
    private String name; // 名称
    private String resource; // 资源地址
    private String operation; // 操作
    private String target; // 目标地址
    private int state; // 状态：0-下线；1-上线

    @Jsonable
    @Column(name = "c_type")
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Jsonable
    @Column(name = "c_sort")
    public int getSort() {
        return sort;
    }

    public void setSort(int sort) {
        this.sort = sort;
    }

    @Jsonable
    @Column(name = "c_name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Jsonable
    @Column(name = "c_resource")
    public String getResource() {
        return resource;
    }

    public void setResource(String resource) {
        this.resource = resource;
    }

    @Jsonable
    @Column(name = "c_operation")
    public String getOperation() {
        return operation;
    }

    public void setOperation(String operation) {
        this.operation = operation;
    }

    @Jsonable
    @Column(name = "c_target")
    public String getTarget() {
        return target;
    }

    public void setTarget(String target) {
        this.target = target;
    }

    @Jsonable
    @Column(name = "c_state")
    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }
}
