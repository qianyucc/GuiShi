//index.js

const api = require('../../config/config.js')

//获取应用实例
const app = getApp();

Page({
  data: {},
  doLogin: function () {
    // 获取code
    wx.login({
      success: codeInfo => {
        console.log(codeInfo);
        app.globalData.code = codeInfo.code;
      }
    });
  },
  onLoad: function () {
    console.log('login onlaunch');
    var that = this;
    let loginFlag = wx.getStorageSync('loginFlag');
    if (loginFlag) {
      // 检查 session_key 是否过期
      wx.checkSession({
        success: function () {
          // 直接从Storage中获取用户信息
          let userStorageInfo = wx.getStorageSync('userInfo');
          if (userStorageInfo) {
            app.globalData.userInfo = JSON.parse(userStorageInfo);
            // 直接到主界面
            setTimeout(function () {
              wx.switchTab({
                url: '../timeline/timeline'
              });
            }, 500);
            console.log(app.globalData);
          } else {
            // 弹出提示框
            wx.showToast({
              title: '缓存信息缺失，请重新授权登录！',
              icon: 'none',
              duration: 1500,
              mask: true
            });
            that.doLogin();
          }
        },
        // session_key 过期
        fail: function (error) {
          that.doLogin();
        }
      });
    } else {
      // 无登录态
      that.doLogin();
    }
  },
  getUserInfo: function (e) {
    console.log(e);
    if (e.detail.errMsg === "getUserInfo:ok") {
      let that = this;
      // 显示登录中框
      wx.showLoading({
        title: '登录中，请稍后',
        mask: 'true'
      });
      // 请求服务端的登录接口
      wx.request({
        url: api.loginUrl,
        method: 'POST',
        data: {
          code: app.globalData.code, // 临时登录凭证
          rawData: e.detail.rawData, // 用户非敏感信息
          signature: e.detail.signature, // 签名
          encryptedData: e.detail.encryptedData, // 用户敏感信息
          iv: e.detail.iv // 解密算法的向量
        },
        success: function (res) {
          // 用户信息
          var userData = {};

          console.log(res);
          wx.hideLoading();
          res = res.data;
          if (res.error_code == 0) {
            // 只有用户授权之后才能调用这个函数
            wx.getUserInfo({
              withCredentials: false,
              lang: 'zh_CN',
              success: result => {
                console.log(result);
                userData.info = result.userInfo;
                // 将用户信息储存到缓存里面
                wx.setStorageSync('userInfo', JSON.stringify(userData));
                app.globalData.userInfo = userData;
                console.log(userData);
              },
              fail: err => {
                console.log(err);
              }
            });
            userData.uid = res.data.uid;
            // 将用skey储存到缓存里面
            wx.setStorageSync('loginFlag', res.data.skey);
            // 转到主界面
            wx.switchTab({
              url: '../timeline/timeline'
            });
          } else {
            wx.showToast({
              title: String(res.error_msg),
              icon: 'none',
              duration: 1500,
              mask: true
            });
          }
        },
        fail: function (error) {
          wx.hideLoading();
          // 调用服务端登录接口失败
          console.log(error);
          wx.showToast({
            title: '服务器忙，请稍后再试',
            icon: 'none',
            duration: 1500,
            mask: true
          });
        }
      });
    }
  }
})