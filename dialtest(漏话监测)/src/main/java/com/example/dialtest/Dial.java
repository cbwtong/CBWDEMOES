package dialtest.snail.com.dt;


import org.xutils.db.annotation.Column;
import org.xutils.db.annotation.Table;

/**
 * Created by Administrator on 2016/11/17.
 * 存储数据实体类
 */
@Table(name = "dial")
public
class
Dial {

    @Column(name = "id", isId = true)
    private int id;//主键

    @Column(name = "content")
    private String content;//内容

    @Column(name = "time")
    private String time;//时间

    @Column(name = "dialing")
    private String dialing;//主叫

    @Column(name = "called")
    private String called;//被叫

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getCalled() {
        return called;
    }

    public void setCalled(String called) {
        this.called = called;
    }

    public String getDialing() {
        return dialing;
    }

    public void setDialing(String dialing) {
        this.dialing = dialing;
    }
}
