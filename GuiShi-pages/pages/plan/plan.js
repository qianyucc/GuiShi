// pages/list/list.js
const api = require('../../config/config.js');
const app = getApp();

Page({

  /**
   * 页面的初始数据
   */
  data: {
  },
  add: function () {
    wx.navigateTo({
      url: '/pages/addPlan/addPlan'
    })
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    let that = this;
    wx.request({
      url: api.getPlanUrl,
      data: {
        uid: app.globalData.userInfo.uid,
        isToday: 1
      },
      success: res => {
        console.log(res);
        let info = res.data;
        this.setData({
          planList: info.data
        });
      },
      fail: err => {
        console.log(err);
      }
    })
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
    this.onLoad(null);
  },
  // 点击修改计划
  modifyItem:function(ev){
    let info = ev.currentTarget.dataset;
    let navigateUrl = '../addPlan/addPlan?';
    for (let key in info) {
      info[key] = encodeURIComponent(info[key]);
      navigateUrl += key + '=' + info[key] + '&';
    }
    navigateUrl = navigateUrl.substring(0, navigateUrl.length - 1);
    wx.navigateTo({
      url: navigateUrl
    });
  },
  // 长按删除计划
  deleteItem: function (ev) {
    var that = this;
    let id = ev.currentTarget.dataset.id;
    wx.showModal({
      title: '提示',
      content: '确定要删除这个计划吗？',
      success: res => {
        if (res.confirm) {
          console.log('点击确定了');
          wx.request({
            url: api.deleteUrl,
            data: {
              type: 'plan',
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
  //将状态转换为已完成
  changeToComplete: function (ev) {
    let that = this;
    let id = ev.currentTarget.dataset.id;
    console.log(id);
    wx.request({
      url: api.updateUrl,
      method: "POST",
      data: {
        id: id,
        type: "plan",
        data: {
          status:1
        }
      },
      success: function (res) {
        console.log(res.data);
        that.onLoad(null);
      },
      fail: err => {
        console.log(err);
      }
    });
  },
  // 将状态转换为未知
  changeToLoading:function(ev){
    let that = this;
    let id = ev.currentTarget.dataset.id;
    console.log(id);
    wx.request({
      url: api.updateUrl,
      method: "POST",
      data: {
        id: id,
        type: "plan",
        data: {
          status: 0
        }
      },
      success: function (res) {
        console.log(res.data);
        that.onLoad(null);
      },
      fail: err => {
        console.log(err);
      }
    });
  },
  // 将状态转换为未知
  changeToRight: function (ev) {
    let that = this;
    let id = ev.currentTarget.dataset.id;
    console.log(id);
    wx.request({
      url: api.updateUrl,
      method: "POST",
      data: {
        id: id,
        type: "plan",
        data: {
          status: 1
        }
      },
      success: function (res) {
        console.log(res.data);
        that.onLoad(null);
      },
      fail: err => {
        console.log(err);
      }
    });
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
