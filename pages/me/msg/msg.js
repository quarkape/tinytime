const app = getApp()
Page({
  data: {
    imgUrl:  app.globalData.imgUrl,
    message: [],
    isLogin: false,
    showNotify: false,
    notifyTitle: "",
    notifyDetail: ""
  },
  onLoad: function (options) {
    this.getAllMessage()
  },

  getAllMessage() {
    let that = this
    wx.request({
      url: app.globalData.baseUrl + "getAllMessage",
      method: "POST",
      header: {
        'content-type': 'application/x-www-form-urlencoded',
        'cookie': 'JSESSIONID=' + app.globalData.SESSIONID
      },
      success(res) {
        if (res.data.code===0) {
          that.setData({
            message: res.data.data,
          })
        } else {
          let e = ['提示', res.data.message]
          that.showNotify(e)
        }
      },
      error() {
        let e = ['提示', '出了点儿错，稍后再试吧']
        that.showNotify(e)
      }
    })
  },

  toMessage(e) {
    let title = e.currentTarget.dataset.title
    let postid = e.currentTarget.dataset.postid
    if (e.currentTarget.dataset.isread===0) {
      let messageid = e.currentTarget.dataset.messageid
      let that = this
      wx.request({
        url: app.globalData.baseUrl + "changeMessageStatus",
        method: "POST",
        header: {
          'content-type': 'application/x-www-form-urlencoded',
          'cookie': 'JSESSIONID=' + app.globalData.SESSIONID
        },
        data: {
          messageid: messageid
        },
        success(res) {
          if (res.data.code===0) {
            let index = e.currentTarget.dataset.index
            that.setData({
              ['message['+index+'].status']: 1
            })
            wx.navigateTo({
              url: '/pages/bbs/passage/passage?title='+title+'&postid='+postid,
            })
          }
        }
      })
    } else {
      wx.navigateTo({
        url: '/pages/bbs/passage/passage?title='+title+'&postid='+postid,
      })
    }
  }
})