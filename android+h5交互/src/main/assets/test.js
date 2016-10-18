/**
 * Created by Administrator on 2016/8/4.
 */
//定义本地方法 效果提供给android端调用 被调用后将获得参数值
function callH5(data) {
    document.getElementById("result").innerHTML = "result success for Android to:" + data;
}
//定义本地点击事件 效果调用android方法 传递参数给Android客户端
function myOnclick() {
    document.getElementById("result").innerHTML = "按钮被点击了"
    //调用android本地方法 电泳对象由Android定义实例化,调用方法由Android提供(具体对象名和方法待定,可变更)
    myObj.callAndroid("弹窗显示回调成功了~~~");
}
