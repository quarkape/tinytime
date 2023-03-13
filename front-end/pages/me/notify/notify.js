const app = getApp()
Page({
  data: {
    imgUrl:  app.globalData.imgUrl,
    notify: [],
    showNotify: false,
    notifyTitle: "",
    notifyDetail: ""
  },
  onLoad: function () {
    this.getAllNotify()
  },

  showNotify: function(e) {
    this.setData({
      showNotify: true,
      notifyTitle: e[0],
      notifyDetail: e[1]
    })
    var that = this
    setTimeout(function() {
      that.setData({
        showNotify: false
      })
    }, 2000)
  },
  
  getAllNotify: function() {
    var that = this
    wx.request({
      url: app.globalData.baseUrl + "getAllNotify",
      method: "POST",
      header: {
        'content-type': 'application/x-www-form-urlencoded',
        'cookie': 'JSESSIONID=' + app.globalData.SESSIONID
      },
      success(res) {
        if (res.data.code===0) {
          that.setData({
            notify: res.data.data,
          })
        } else {
          var e = ['提示', res.data.message]
          that.showNotify(e)
        }
      },
      error() {
        var e = ['提示', '出了点儿错，稍后再试吧']
        that.showNotify(e)
      }
    })
  },

  toNotify(e) {
    if (e.currentTarget.dataset.isread===0) {
      var notifyid = e.currentTarget.dataset.notifyid
      var that = this
      wx.request({
        url: app.globalData.baseUrl + "changeNotifyStatus",
        method: "POST",
        header: {
          'content-type': 'application/x-www-form-urlencoded',
          'cookie': 'JSESSIONID=' + app.globalData.SESSIONID
        },
        data: {
          notifyid: notifyid
        },
        success(res) {
          if (res.data.code===0) {
            let index = e.currentTarget.dataset.index
            that.setData({
              ['notify['+index+'].status']: 1
            })
          }
        }
      })
    }
  }
})