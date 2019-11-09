const api = require('../../../config/config.js')

const app = getApp();

Page({
  data: {
    update:false,
    event: '',
    enddate: app.globalData.currentDate,
  },

  onLoad: function (options){
    let countDown = {};
    for (let key in options) {
      countDown[key] = decodeURIComponent(options[key]);
    }
    console.log(countDown);
    if (JSON.stringify(countDown) != "{}"){
      this.setData({
        update:true,
        enddate: countDown.enddate,
        input: countDown.event,
        countDown:countDown
      });
    }
  },

  setevent:function(e){
    console.log('事件发送选择改变，携带值为', e.detail.value)
    this.setData({
      event: e.detail.value
    });
  },

  bindDateChange: function (e) {
    console.log('picker发送选择改变，携带值为', e.detail.value)
    this.setData({
      enddate: e.detail.value
    });
    
  },

  formSubmit: function (e) {
    let that = this;
    console.log('form发生了timelien-submit事件，携带数据为：', e.detail.value);
    var event=e.detail.value.input;
    var enddate=e.detail.value.data;
    if(that.data.update){
      wx.request({
        url: api.updateUrl,
        method: "POST",
        data: {
          type: "countDown",
          id: that.data.countDown.id,
          data: {
            event: event,
            endTime: enddate,
          }
        },
        success: function (res) {
          console.log(res.data);
        }
      });
    } else {
      wx.request({
        url: api.addUrl,
        method: "POST",
        data: {
          type: "countDown",
          data: {
            uid: app.globalData.userInfo.uid,
            event: event,
            endTime: enddate,
          }
        },
        success: function (res) {
          console.log(res.data);
        }
      });
    }
    wx.navigateBack({
      delta: 1
    })
  },


})