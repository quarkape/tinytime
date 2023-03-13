const app = getApp()
Page({
  data: {
    imgUrl:  app.globalData.imgUrl,
    myposts: [],
    showNotify: false,
    notifyTitle: "",
    notifyDetail: "",
    showDel: false,
    delpostid: "",
    showLoading: false,
    loadingTxt: "",
    isNormal: false
  },
  onShow: function() {
    this.setData({
      isNormal: wx.getStorageSync('isNormal')
    })
  },
  onLoad: function () {
    this.getMyPosts()
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

  getMyPosts() {
    let that = this
    wx.request({
      url: app.globalData.baseUrl + "getMyPosts",
      method: "POST",
      header: {
        'content-type': 'application/x-www-form-urlencoded',
        'cookie': 'JSESSIONID=' + app.globalData.SESSIONID
      },
      success(res) {
        if (res.data.code===0) {
          that.setData({
            myposts: res.data.data
          })
        } else {
          let e = ['获取发表失败', res.data.message]
          that.showNotify(e)
        }
      },
      error() {
        let e = ['出错', '出了点儿错，稍后再试吧']
        that.showNotify(e)
      }
    })
  },

  toMyPost(e) {
    let title = e.currentTarget.dataset.title
    let postid = e.currentTarget.dataset.postid
    wx.navigateTo({
      url: '/pages/bbs/passage/passage?title=' + title + "&postid=" + postid,
    })
  },

  deletePost(e) {
    let postid = e.currentTarget.dataset.postid
    let delid = e.currentTarget.dataset.delid
    this.setData({
      showDel: true,
      delpostid: postid,
      delid: delid
    })
  },

  toDeletePost() {
    this.setData({
      showLoading: true,
      loadingTxt: "删除中"
    })
    let that = this
    let delpostid = this.data.delpostid
    let postss = this.data.myposts
    wx.request({
      url: app.globalData.baseUrl + "deletePost",
      // url: 'http://localhost:8080/deletePost',
      header: {
        'content-type': 'application/x-www-form-urlencoded',
        'cookie': 'JSESSIONID=' + app.globalData.SESSIONID
      },
      data: {
        delpostid: delpostid
      },
      success(res) {
        if (res.data.code===0) {
          postss.splice(that.data.delid, 1)
          that.setData({
            myposts: postss
          })
          let e = ['提示', '话题已成功删除啦']
          that.showNotify(e)
        } else {
          let e = ['提示', res.data.message]
          that.showNotify(e)
        }
      },
      error() {
        let e = ['提示', '出了点儿错，稍后再试吧']
        that.showNotify(e)
      },
      complete() {
        that.setData({
          showDel: false,
          showLoading: false
        })
      },
      fail: () => {
        that.setData({
          showLoading: false,
          loadingTxt: ""
        })
      }
    })
  },

  closeWindow() {
    this.setData({
      showDel: false
    })
  }
})