const app = getApp()
Page({
  data: {
    baseUrl: '',
    isNormal: false
  },
  onShow: function() {
    this.setData({
      isNormal: wx.getStorageSync('isNormal'),
      imgUrl:  app.globalData.imgUrl
    })
  }
})