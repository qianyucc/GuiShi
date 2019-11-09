import * as echarts from '../ec-canvas/echarts.js';
const api = require('../../config/config.js');
const app = getApp();

function initChart(canvas, width, height) {
  const chart = echarts.init(canvas, null, {
    width: width,
    height: height
  });
  canvas.setChart(chart);

  let completeNum = 0;
  let unConpleteNum = 0;

  wx.request({
    url: api.getPlanCountUrl,
    method: 'GET',
    data: {
      uid: app.globalData.userInfo.uid
    },
    success: res => {
      console.log(res);
      completeNum = res.data.completeNum;
      unConpleteNum = res.data.unConpleteNum;
      var option = {
        title: {
          text: '计划完成情况统计图',
          x: 'center',
          y: 'top',
          // 主标题文本样式设置
          textStyle: {
            fontSize: '26rpx',
            fontWeight: 'bolder',
            color: '#000080'
          }
        },
        series: [{
          name: '访问来源',
          type: 'pie',
          radius: '50%',
          itemStyle: {
            normal: {
              label: {
                show: true,
                formatter: '{b} : \n{c} ({d}%)'
              },
              labelLine: {
                show: true
              }
            }
          },
          data: [{
              value: completeNum,
              name: '已完成',
              itemStyle: {
                normal: {
                  color: 'rgba(32,178,170,0.5)',
                  shadowBlur: '90',
                  shadowColor: 'rgba(0,0,0,0.8)',
                  shadowOffsetY: '30'
                }
              }
            },
            {
              value: unConpleteNum,
              name: '未完成',
              itemStyle: {
                normal: {
                  color: 'rgba(178,34,34,0.5)',
                  shadowBlur: '90',
                  shadowColor: 'rgba(0,0,0,0.8)',
                  shadowOffsetY: '30'
                }
              }
            }
          ]
        }],
        // itemStyle 设置饼状图扇形区域样式
        itemStyle: {
          // emphasis：英文意思是 强调;着重;（轮廓、图形等的）鲜明;突出，重读
          // emphasis：设置鼠标放到哪一块扇形上面的时候，扇形样式、阴影
          emphasis: {
            shadowBlur: 10,
            shadowOffsetX: 0,
            shadowColor: 'rgba(30, 144, 255，0.5)'
          }
        }
      };
      chart.setOption(option);
      return chart;
    },
    fail: err => {
      console.log(err);
    }
  })
}

Page({

  /**
   * 页面的初始数据
   */
  data: {
    ec: {
      onInit: initChart
    }
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function(options) {
    let that = this;
    wx.request({
      url: api.getPlanGroupByDate,
      data: {
        uid: app.globalData.userInfo.uid
      },
      success: res => {
        console.log(res);
        let info = res.data;
        let dateList = [];
        for (let i in info) {
          dateList.push(i);
        }
        this.setData({
          dateList: dateList,
          planList: res.data
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
  onReady: function() {

  },

  /**
   * 生命周期函数--监听页面显示
   */
  onShow: function() {

  },

  /**
   * 生命周期函数--监听页面隐藏
   */
  onHide: function() {

  },

  /**
   * 生命周期函数--监听页面卸载
   */
  onUnload: function() {

  },

  /**
   * 页面相关事件处理函数--监听用户下拉动作
   */
  onPullDownRefresh: function() {

  },

  /**
   * 页面上拉触底事件的处理函数
   */
  onReachBottom: function() {

  },

  /**
   * 用户点击右上角分享
   */
  onShareAppMessage: function() {

  }
})