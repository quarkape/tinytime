const app = getApp()
Page({
  data: {
    imgUrl:  app.globalData.imgUrl,
    mycollect: [],
    showNotify: false,
    notifyTitle: "",
    notifyDetail: ""

  },
  onLoad: function (options) {
    this.getMyCollects()
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

  getMyCollects() {
    let that = this
    wx.request({
      url: app.globalData.baseUrl + "getMyCollects",
      method: "POST",
      header: {
        'cookie': 'JSESSIONID=' + app.globalData.SESSIONID
      },
      success(res) {
        if (res.data.code===0) {
          that.setData({
            mycollect: res.data.data
          })
        } else {
          let e = ['获取失败', res.data.message]
          that.showNotify(e)
        }
      },
      error() {
        let e = ['出错', '出了点儿错，稍后再试吧']
        that.showNotify(e)
      }
    })
  },

  toCollect(e) {
    let title = e.currentTarget.dataset.title
    let postid = e.currentTarget.dataset.postid
    wx.navigateTo({
      url: '/pages/bbs/passage/passage?title=' + title + "&postid=" + postid,
    })
  }
})