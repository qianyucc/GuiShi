const api = require('../../../config/config.js')
const app = getApp();

Page({
  data: {
    update: false,
    currDate: app.globalData.currentDate,
    title: '',
    input: ''
  },

  /**
   * 如果是从“我的”页面跳转过来的，就加载数据
   */
  onLoad: function (options) {
    let that = this;
    // 判断是从哪个页面跳转过来的
    let pages = getCurrentPages();
    let prevpage = pages[pages.length - 2];
    if (prevpage.route == 'pages/myLetter/myLetter') {
      let letter = {};
      for (let key in options) {
        letter[key] = decodeURIComponent(options[key]);
      }
      that.setData({
        update:true,
        currDate: letter.noticetime,
        letter: letter
      });
      console.log(that.data);
    }
  },

  bindDateChange: function (e) {
    console.log('picker发送选择改变，携带值为', e.detail.value)
    this.setData({
      currDate: e.detail.value
    });
  },

  formSubmit: function (e) {
    let that = this;
    console.log('form发生了submit事件，携带数据为：', e.detail.value);

    let reqUrl;
    let reqData;
    if (that.data.update) {
      reqUrl = api.updateUrl;
      reqData = {
        type: "letter",
        id: that.data.letter.id,
        data: {
          title: e.detail.value.title,
          content: e.detail.value.input,
          mail: e.detail.value.mail,
          noticeTime: e.detail.value.date
        }
      }
    } else {
      reqUrl = api.addUrl;
      reqData = {
        type: "letter",
        data: {
          uid: app.globalData.userInfo.uid,
          title: e.detail.value.title,
          content: e.detail.value.input,
          mail: e.detail.value.mail,
          noticeTime: e.detail.value.date
        }
      };
    }

    console.log(reqData);

    wx.request({
      url: reqUrl,
      method: "POST",
      data: reqData,
      success: function (res) {
        console.log(res.data);
        // 弹出提示框
        wx.showToast({
          title: '添加成功！',
          icon: 'success',
          duration: 1500,
          mask: true
        });
      }
    });

    wx.navigateBack({
      delta: 1
    })
  },


})