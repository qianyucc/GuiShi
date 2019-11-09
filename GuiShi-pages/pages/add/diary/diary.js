var util = require('../../../utils/util.js')
var bmap = require('../../libs/bmap-wx.min.js')
const api = require('../../../config/config.js')

const app = getApp();


Page({

  data: {
    intput: '',
    weatherData: '',
    weekData: '',
    dayData: '',
    timedata: '',
  },
  formSubmit: function(e, ) {
    console.log('form发生了submit事件，携带数据为：', e.detail.value),
      console.log('timedata:', this.data.timedata),
      console.log('时间，天气，记录：', this.data.weatherData)
    wx.request({
      url: api.addUrl,
      method: "POST",
      data: {
        type: "diary",
        data: {
          uid: app.globalData.userInfo.uid,
          time: this.data.timedata,
          weather: this.data.weatherData.weatherDesc,
          week: this.data.weekData,
          content: e.detail.value.input,
        }
      },
      success: function(res) {
        console.log(res.data);
        wx.showToast({
          title: '添加成功！',
          icon: 'success',
          duration: 1500,
          mask: true
        });
      },
    });
    wx.navigateBack({
      delta: 1
    })
  },


  onLoad: function() {
    var data = util.formatData(new Date());
    this.setData({
      timedata: data,
    });
    var that = this;
    var BMap = new bmap.BMapWX({
      ak: 'HFTil24AyGBWDrCzbKsxOoKl2OmL2T4O'
    });
    var fail = function(data) {
      console.log('fail!!!!')
      console.log(data);
    };
    var success = function(data) {
      console.log('success!!!');
      var weatherData = data.currentWeather[0];
      that.setData({
        weatherData: weatherData,
        weekData: weatherData.date.substring(0, 2),
        dayData: weatherData.date.substring(3, 9),
      });
    }
    BMap.weather({
      fail: fail,
      success: success
    });
  }
})