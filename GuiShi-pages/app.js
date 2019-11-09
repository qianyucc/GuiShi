//app.js
App({
  onLaunch: function () {
    console.log('app onlaunch');
    var myDate = new Date();
    var currentDate = myDate.getFullYear() + "-" + (myDate.getMonth() + 1) + "-" + myDate.getDate();
    var currentTime = myDate.getHours() + ":" + myDate.getMinutes();     //获取当前分钟数(0-59)
    console.log(currentDate);
    console.log(currentTime);
    this.globalData.currentDate = currentDate;
    this.globalData.currentTime = currentTime;
  },

  globalData: {}
})