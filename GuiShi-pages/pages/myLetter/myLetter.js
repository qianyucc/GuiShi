const api = require('../../config/config.js');

const app = getApp();

Page({

  /**
   * 页面的初始数据
   */
  data: {
    actionSheetHidden: true,
    actionSheetItems: [
      { bindtap: 'Modify', txt: '修改' },
      { bindtap: 'Delete', txt: '删除' }
    ]
  },
  /**
   * 点击时弹出上拉菜单
   */
  selectAction: function (ev) {
    this.setData({
      actionSheetHidden: !this.data.actionSheetHidden,
      // 将该条信件的信息取出来放在data里面
      selectedLetter:ev.currentTarget.dataset
    })
  },
  actionSheetbindchange: function () {
    this.setData({
      actionSheetHidden: !this.data.actionSheetHidden
    })
  },
  /**
   * 点击修改的回调函数
   */
  bindModify: function () {
    let that = this;
    this.setData({
      actionSheetHidden: !this.data.actionSheetHidden
    });
    let info = that.data.selectedLetter;
    let navigateUrl = '../add/mail/mail?';
    for (let key in info) {
      info[key] = encodeURIComponent(info[key]);
      navigateUrl += key + '=' + info[key] + '&';
    }
    navigateUrl = navigateUrl.substring(0, navigateUrl.length - 1);
    wx.navigateTo({
      url: navigateUrl
    });
  },
  /**
   * 点击删除的回调函数
   */
  bindDelete: function () {
    this.setData({
      actionSheetHidden: !this.data.actionSheetHidden
    });
    this.deleteItem();
  },


  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    var that = this;
    console.log('myMail is loading...');
    wx.request({
      url: api.getLetterUrl,
      data: {
        uid: app.globalData.userInfo.uid,
        is_all: 1
      },
      success: res => {
        console.log(res);
        res = res.data;
        if (res.error_code == 0) {
          let letterList = res.data;
          that.setData({
            letterList: letterList
          });
        } else {
          wx.showToast({
            title: '服务器忙，请稍后再试',
            icon: 'none',
            duration: 1500,
            mask: true
          });
        }
      },
      fail: err => {
        console.log(err);
        wx.showToast({
          title: '服务器忙，请稍后再试',
          icon: 'none',
          duration: 1500,
          mask: true
        });
      }
    })
  },
  // 跳转到详细信息
  goDetial: function (ev) {
    let info = ev.currentTarget.dataset;
    let navigateUrl = '../myLetterDetail/myLetterDetail?';
    for (let key in info) {
      info[key] = encodeURIComponent(info[key]);
      navigateUrl += key + '=' + info[key] + '&';
    }
    navigateUrl = navigateUrl.substring(0, navigateUrl.length - 1);
    wx.navigateTo({
      url: navigateUrl
    });
  },
  // 删除信件
  deleteItem: function () {
    var that = this;
    let id = that.data.selectedLetter.id;
    wx.showModal({
      title: '提示',
      content: '确定要删除这篇日记吗？',
      success: res => {
        if (res.confirm) {
          console.log('点击确定了');
          wx.request({
            url: api.deleteUrl,
            data: {
              type: 'letter',
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