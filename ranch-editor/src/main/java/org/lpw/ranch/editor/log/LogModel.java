package org.lpw.ranch.editor.log;

import org.lpw.tephra.dao.model.Daily;
import org.lpw.tephra.dao.model.Jsonable;
import org.lpw.tephra.dao.model.ModelSupport;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.sql.Timestamp;

/**
 * @author lpw
 */
@Component(LogModel.NAME + ".model")
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
@Entity(name = LogModel.NAME)
@Table(name = "t_editor_log")
@Daily
public class LogModel extends ModelSupport {
    static final String NAME = "ranch.editor.log";

    private String editor; // 编辑器
    private String parent; // 父元素
    private String element; // 元素
    private int sort; // 顺序
    private String json; // 扩展属性集
    private Timestamp create; // 创建时间
    private long modify; // 修改时间
    private int operation; // 操作：0-新增；1-修改；2-删除
    private Timestamp time; // 时间

    @Jsonable
    @Column(name = "c_editor")
    public String getEditor() {
        return editor;
    }

    public void setEditor(String editor) {
        this.editor = editor;
    }

    @Jsonable
    @Column(name = "c_parent")
    public String getParent() {
        return parent;
    }

    public void setParent(String parent) {
        this.parent = parent;
    }

    @Jsonable
    @Column(name = "c_element")
    public String getElement() {
        return element;
    }

    public void setElement(String element) {
        this.element = element;
    }

    @Jsonable
    @Column(name = "c_sort")
    public int getSort() {
        return sort;
    }

    public void setSort(int sort) {
        this.sort = sort;
    }

    @Jsonable(extend = true)
    @Column(name = "c_json")
    public String getJson() {
        return json;
    }

    public void setJson(String json) {
        this.json = json;
    }

    @Jsonable
    @Column(name = "c_create")
    public Timestamp getCreate() {
        return create;
    }

    public void setCreate(Timestamp create) {
        this.create = create;
    }

    @Jsonable
    @Column(name = "c_modify")
    public long getModify() {
        return modify;
    }

    public void setModify(long modify) {
        this.modify = modify;
    }

    @Jsonable
    @Column(name = "c_operation")
    public int getOperation() {
        return operation;
    }

    public void setOperation(int operation) {
        this.operation = operation;
    }

    @Jsonable
    @Column(name = "c_time")
    public Timestamp getTime() {
        return time;
    }

    public void setTime(Timestamp time) {
        this.time = time;
    }
}
