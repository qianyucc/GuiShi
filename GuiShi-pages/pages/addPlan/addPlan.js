// pages/add/add.js
const api = require('../../config/config.js');
const app = getApp();
Page({
  data: {
    update:false,
    date: app.globalData.currentTime,
    dateb: app.globalData.currentTime,
    customItem: '全部'
  },
  setevent: function (e) {
    console.log('事件发送选择改变，携带值为', e.detail.value)
    this.setData({
      event: e.detail.value
    });
  },

  bindDateChange: function (e) {
    console.log('picker发送选择改变，携带值为', e.detail.value)
    this.setData({
      starttime: e.detail.value
    })
  },
  bind2DateChange: function (e) {
    console.log('picker发送选择改变，携带值为', e.detail.value)
    this.setData({
      endtime: e.detail.value
    })
  },

  formSubmit: function (e) {
    let that = this;
    console.log('form发生了timelien-submit事件，携带数据为：', e.detail.value);
    var event = e.detail.value.event;
    var starttime = e.detail.value.starttime
    var endtime = e.detail.value.endtime;

    var reqData;
    var title;
    var reqUrl;
    if(this.data.update==false){
      reqUrl = api.addUrl;
      title = '添加成功！',
      reqData = {
        type: "plan",
        data: {
          uid: app.globalData.userInfo.uid,
          content: event,
          startTime: starttime,
          endTime: endtime,
        }
      }
    }else{
      reqUrl = api.updateUrl;
      title = '修改成功！',
      reqData = {
        type: "plan",
        id:that.data.plan.id,
        data: {
          content: event,
          startTime: starttime,
          endTime: endtime
        }
      }
    }

    wx.request({
      url: reqUrl,
      method: "POST",
      data:reqData,
      success: function (res) {
        console.log(res.data);
        wx.showToast({
          title: title,
          icon: 'success',
          duration: 1500,
          mask: true
        });
      },
      fail: err => {
        console.log(err);
      }
    });
    wx.navigateBack({
      delta: 1
    })
  },
  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    let plan = {};
    for (let key in options) {
      plan[key] = decodeURIComponent(options[key]);
    }
    console.log(plan);
    if (JSON.stringify(plan) != "{}") {
      this.setData({
        input:plan.content,
        date: plan.starttime.slice(0,5),
        dateb: plan.endtime.slice(0, 5),
        starttime: plan.starttime.slice(0, 5),
        endtime: plan.endtime.slice(0, 5),
        update: true,
        plan: plan
      });
    }
  },

  /**
   * 生命周期函数--监听页面初次渲染完成
   */
  onReady: function () {

  },

  /**
   * 生命周期函数--监听页面显示
   */
  onShow: function () {

  },

  /**
   * 生命周期函数--监听页面隐藏
   */
  onHide: function () {

  },

  /**
   * 生命周期函数--监听页面卸载
   */
  onUnload: function () {

  },

  /**
   * 页面相关事件处理函数--监听用户下拉动作
   */
  onPullDownRefresh: function () {

  },

  /**
   * 页面上拉触底事件的处理函数
   */
  onReachBottom: function () {

  },

  /**
   * 用户点击右上角分享
   */
  onShareAppMessage: function () {

  }
})