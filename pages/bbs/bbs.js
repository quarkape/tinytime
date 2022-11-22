const app = getApp()
const util = require('../../utils/util')
Page({
  data: {
    imgUrl: app.globalData.imgUrl,
    curItem: 0,
    // navs: ["热度", "吐槽", "趣闻", "分享"],
    navs: ["热点", "趣闻", "分享"],
    distance: 5,
    picHeight: 0.27*wx.getSystemInfoSync().windowWidth,
    isSelect: false,
    isShowLogin: false,
    showLoading: false,
    showNotify: false,
    notifyTitle: "",
    notifyDetail: "",
    keyword: "",
    isLoading: false,
    isSearch: false,
    postLists: [],
    isNormal: false
  },

  onShow: function() {
    this.setData({
      isNormal: wx.getStorageSync('isNormal')
    })
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

  onLoad: function() {
    // 如果用户之前登陆过，自动登录
    if (app.globalData.name && !app.globalData.isLogin) {
      this.goLogin()
    }
    // get all posts, 0 represents update action instead of loading
    this.setData({
      showLoading: true
    })
    this.getPosts(0);
  },

  onPullDownRefresh() {
    this.setData({
      isSearch: false
    })
    this.getPosts(0);
  },

  onReachBottom() {
    // show the loading gif
    this.setData({
      isLoading: true
    })
    // load more posts, 1 represents load action instead of updating
    this.data.isSearch?this.getSearchPosts(1):this.getPosts(1)
  },

  //main function, e is an array which contains action
  getPosts: function(e) {
    var that = this
    var postlen = this.data.postLists.length
    var resPosts = this.data.postLists
    var currentDate = util.formatTime(new Date)
    var action = e
    wx.request({
      url: app.globalData.baseUrl+"getPosts",
      method: "POST",
      data: {
        type: that.data.curItem,
        // lastdate is mainlly used when load posts and avoid repeat posts while doing paged query
        lastdate: action===0?currentDate:that.data.postLists[postlen-1].date
      },
      header: {
        // because the query is public, so cookie is not needed
        'content-type': 'application/x-www-form-urlencoded',
        // 'cookie': 'JSESSIONID=' + app.globalData.SESSIONID
      },
      success: function(res) {
        if (res.data.code===0) {
          // while updating, directlly replace the original posts array
          if (action===0) {
            that.setData({
              postLists: res.data.data,
            })
          } else {
            // while loading more
            if (!res.data===null) {
              resPosts.push(res.data.data)
              that.setData({
                postLists: resPosts
              })
            }
          }
        } else {
          // show notify window
          var e = ["刷新失败", res.data.message]
          this.showNotify(e)
        }
      },
      error: function() {
        // hide the refresh animation
        wx.stopPullDownRefresh()
        var e = ["提示", "出了点儿错，稍后再试吧"]
        this.showNotify(e)
      },
      complete: function() {
        that.setData({
          showLoading: false,
          isLoading: false
        })
        // hide the refresh animation
        wx.stopPullDownRefresh()
      }
    })
  },

  changeNav: function(e) {
    var pickItem = e.currentTarget.dataset.id
    this.setData({
      curItem: pickItem,
      distance: (30*pickItem + 5),
      isSearch: false
    })
    this.getPosts(0)
  },

  toPassage: function(e) {
    if (!app.globalData.isLogin) {
      this.setData({
        isShowLogin: true
      })
      return
    }
    wx.navigateTo({
      url: 'passage/passage?title='+e.currentTarget.dataset.title+'&postid='+e.currentTarget.dataset.postid,
    })
  },

  toSelect: function() {
    this.setData({
      isSelect: true
    })
  },

  toNew: function(e) {
    this.setData({
      isSelect: false
    })
    if (!app.globalData.isLogin) {
      this.setData({
        isShowLogin: true
      })
      return
    }
    wx.navigateTo({
      url: 'new/new?postType=' + e.currentTarget.dataset.id,
    })
  },

  // close the window according to response window type
  closeWindow: function(e) {
    var modelid = e.currentTarget.dataset.modelid
    this.setData({
      [modelid]: false
    })
  },

  goLogin: function() {
    this.setData({
      isShowLogin: false
    })
    wx.navigateTo({
      url: '../login/login',
    })
  },

  getKeyword: function(e) {
    this.setData({
      keyword: e.detail.value
    })
  },

  getSearchPosts: function() {
    this.setData({
      showLoading: true
    })
    this.goSearch(0)
  },

  goSearch: function(e) {
    var that = this
    var resPosts = this.data.postLists
    var currentDate = util.formatTime(new Date())
    var postlen = this.data.postLists.length
    var action = e
    wx.request({
      url: app.globalData.baseUrl+"getSearchPosts",
      method: "POST",
      data: {
        keyword: that.data.keyword,
        lastdate: action===0?currentDate:that.data.postLists[postlen - 1].date
      },
      header: {
        'content-type': 'application/x-www-form-urlencoded',
        'cookie': 'JSESSIONID=' + app.globalData.SESSIONID
      },
      success: function(res) {
        that.setData({
          showLoading: false
        })
        if (res.data.code===0) {
          if (action===0) {
            that.setData({
              postLists: res.data.data
            })
          } else {
            resPosts.push(resPosts)
            that.setData({
              postLists: resPosts
            })
          }
        } else {
          var e = ["刷新失败", res.data.message]
          that.showNotify(e)
        }
      },
      error: function() {
        that.setData({
          showLoading: false,
        })
        var e = ["提示", "出了点儿错，稍后再试吧"]
        this.showNotify(e)
      }
    })
  }
})