const api = require('../../config/config.js')

var app = getApp();

Page({
  data: {
    logs: [],
    selectPerson: true,
    selectArea: false,
  },

  load: function () {
    var that = this;

    wx.request({

      url: api.getCountDownUrl,
      data: {
        is_all: '0',
        uid: app.globalData.userInfo.uid+""
      },
      header: {
        'content-type': 'application/json'
      },
      success: function (res) {
        var data = res.data.data;
        var send = [];

        for (var i in data) {
          var dic = {};
          //添加
          dic["id"] = data[i].id;
          dic["event"] = data[i].event;
          dic["time"] = data[i].endTime;
          dic["color"] = data[i].color;
          dic["pointtime"] = data[i].endTime.substring(5, 10);
          dic["restDays"] = data[i].restDays
          
          send.push(dic);
        }
        console.log(send);
        that.setData({
          send: send
        })
      }
    })
  },

  inputChangeHandle: function (e) {
    this.setData({ input: e.detail.value })
  },
  // 长按删除倒计时
  deleteItem: function (ev) {
    var that = this;
    let id = ev.currentTarget.dataset.id;
    wx.showModal({
      title: '提示',
      content: '确定要删除这条倒计时吗？',
      success: res => {
        if (res.confirm) {
          console.log('点击确定了');
          wx.request({
            url: api.deleteUrl,
            data: {
              type: 'countDown',
              id: id
            },
            success: res => {
              console.log(res);
              if (res.data.error_code === 0) {
                wx.showToast({
                  title: '删除成功！',
                  icon: 'success',
                  duration: 1500,
                  mask: true
                });
                that.onLoad();
              } else {
                wx.showToast({
                  title: '服务器忙，请稍后再试！',
                  icon: 'none',
                  duration: 1500,
                  mask: true
                });
              }
            },
            fail: err => {
              wx.showToast({
                title: '服务器忙，请稍后再试！',
                icon: 'none',
                duration: 1500,
                mask: true
              });
            }
          })
        } else if (res.cancel) {
          console.log('点击取消了');
          return;
        }
      }
    })
  },

  // 修改倒计时
  modifyItem:function(ev){
    let info = ev.currentTarget.dataset;
    let navigateUrl = '../add/countdown/countdown?';
    for (let key in info) {
      info[key] = encodeURIComponent(info[key]);
      navigateUrl += key + '=' + info[key] + '&';
    }
    navigateUrl = navigateUrl.substring(0, navigateUrl.length - 1);
    wx.navigateTo({
      url: navigateUrl
    });
  },

  hideMenu:function(e){
    console.log(e);
    this.data.selectPerson = false;
    this.setData({
      selectArea: false,
      selectPerson: true,
    })
  },

  //点击选择类型
  clickPerson: function () {
    var selectPerson = this.data.selectPerson;
    if (selectPerson == true) {
      this.setData({
        selectArea: true,
        selectPerson: false,
      })
    } else {
      this.setData({
        selectArea: false,
        selectPerson: true,
      })
    }
  },
  //点击切换
  mySelect: function (e) {
    var yemian = e.target.dataset.me;
    wx.navigateTo({
      url: '/pages/add/' + yemian + "/" + yemian,
    })

  },
  onLoad: function (options) {
    // 页面初始化 options为页面跳转所带来的参数
    this.load()
  },
  onReady: function () {
    // 页面渲染完成
  },
  onShow: function () {
    this.onLoad();
    // 页面显示
  },
  onHide: function () {
    // 页面隐藏
  },
  onUnload: function () {
    // 页面关闭
  }
})