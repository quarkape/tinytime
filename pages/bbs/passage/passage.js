const app = getApp()
// const imgUrl = app.globalData.imgUrl;
Page({
  data: {
    imgUrl:  app.globalData.imgUrl,
    picHeight: 0.63*wx.getSystemInfoSync().windowWidth,
    isInput: false,
    floor: -1,
    inputBoxTxt: "留言",
    postid: "",
    postMain: [],
    postInfo: [],
    postComment: [],
    notifyTitle: "",
    notifyDetail: "",
    showLoading: false,
    loadingTxt: "",
    isQuote: false,
    comment: "",
    quote: "",
    isReport: false,
    report: "",
    isNormal: false
  },
  onLoad: function(e) {
    wx.setNavigationBarTitle({
      title: e.title
    })
    this.setData({
      postid: e.postid,
      showLoading: true,
      loadingTxt: "玩命加载中"
    })
    // wx.setNavigationBarTitle({
    //   title: 'test'
    // })
    // this.setData({
    //   postid: '16688778108971668932042672',
    //   showLoading: true,
    //   loadingTxt: "玩命加载中"
    // })
    this.getCertainPost()
  },

  onShow: function() {
    this.setData({
      isNormal: wx.getStorageSync('isNormal')
    })
  },

  // 预览头像
  previewAvatar(e) {
    console.log(e.currentTarget.dataset.avatarurl)
    let avatar = [this.data.imgUrl+e.currentTarget.dataset.avatarurl]
    wx.previewImage({
      urls: avatar,
    })
  },

  // show a notify window without close button and will disapear automatically
  // an array should be given while using, array includs notifyTitle and notifyDetail
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

  getCertainPost: function() {
    var that = this
    wx.request({
      url: app.globalData.baseUrl + "getCertainPost",
      method: "POST",
      header: {
        'content-type': 'application/x-www-form-urlencoded',
        'cookie': 'JSESSIONID=' + app.globalData.SESSIONID
      },
      data: {
        postid: that.data.postid
      },
      success: function(res) {
        if (res.data.code===0) {
          that.setData({
            postMain: res.data.data.postMain,
            postInfo: res.data.data.postInfo,
            postComment: res.data.data.postComment
          })
        } else {
          var e = ['提示', res.data.message]
          that.showNotify(e)
        }
      },
      error: function() {
        var e = ['提示', '出了点儿错，稍后再试吧']
        that.showNotify(e)
      },
      complete: function() {
        that.setData({
          showLoading: false,
          loadingTxt: ""
        })
      }
    })
  },

  //图片方法预览
  picPreview: function(e) {
    let pics = []
    pics.push(this.data.imgUrl+e.currentTarget.dataset.url)
    wx.previewImage({
      urls: pics
    })
  },

  //点击引用评论
  toQuote: function(e) {
    var floorr = e.currentTarget.dataset.floor
    this.setData({
      isInput: true,
      inputBoxTxt: "正在引用 "+(floorr+1)+" 楼的评论",
      floor: floorr,
      isQuote: true,
      comment: ""
    })
    wx.pageScrollTo({
      selector: "#inputBox",
      duration: 300
    })
  },

  cancelQuote: function() {
    this.setData({
      isInput: false,
      inputBoxTxt: "点击发表回复",
      isQuote: false,
      // if user click quote button and entered some words but lastly cancel quote
      comment: "",
      floor: -1
    })
  },

  getInput: function(e) {
    var inputid = e.currentTarget.dataset.inputid
    this.setData({
      [inputid]: e.detail.value
    })
  },

  toComment: function() {
    var comment = this.data.comment
    if (comment==="" || comment.replace(/\s+/g, '').length===0) {
      var e = ["提示", "评论内容是空的~"]
      this.showNotify(e)
      return
    }
    if (this.data.isQuote) {
      this.setData({
        quote: "回复内容：" + this.data.postComment[this.data.floor].detail
      })
    }
    var that = this
    wx.request({
      url: app.globalData.baseUrl + "addComment",
      method: "POST",
      header: {
        'content-type': 'application/x-www-form-urlencoded',
        'cookie': 'JSESSIONID=' + app.globalData.SESSIONID
      },
      data: {
        type: that.data.isQuote?1:0,
        postid: that.data.postid,
        comment: that.data.comment,
        quote: that.data.quote,
        roleb: that.data.isQuote?that.data.postComment[that.data.floor].id:"",
        authorid: that.data.postMain.authorid
      },
      success: function(res) {
        if (res.data.code===0) {
          // if comment succeed, update the post
          that.setData({
            postComment: res.data.data,
            isQuote: false,
            quote: "",
            comment: ""
          })
        } else {
          var e = ["提示", res.data.message]
          that.showNotify(e)
        }
      },
      error: function() {
        var e = ['提示', '出了点儿错，稍后再试吧']
        that.showNotify(e)
      }
    })
  },

  toAgree: function() {
    var that = this
    wx.request({
      url: app.globalData.baseUrl + 'changeAgreeStatus',
      method: "POST",
      header: {
        'content-type': 'application/x-www-form-urlencoded',
        'cookie': 'JSESSIONID=' + app.globalData.SESSIONID
      },
      data: {
        postid: that.data.postid
      },
      success: function(res) {
        if (res.data.code===0) {
          that.setData({
            'postMain.agree': res.data.data===0?that.data.postMain.agree-1:that.data.postMain.agree+1,
            'postInfo.agree': res.data.data
          })
        } else {
          var e = ['点赞失败', res.data.message]
          that.showNotify(e)
        }
      },
      error: function() {
        var e = ['提示', '出了点儿错，稍后再试吧']
        that.showNotify(e)
      }
    })
  },

  toCollect: function() {
    var that = this
    wx.request({
      url: app.globalData.baseUrl + 'changeCollectStatus',
      method: "POST",
      header: {
        'content-type': 'application/x-www-form-urlencoded',
        'cookie': 'JSESSIONID=' + app.globalData.SESSIONID
      },
      data: {
        postid: that.data.postid
      },
      success: function(res) {
        if (res.data.code===0) {
          that.setData({
            'postMain.collect': res.data.data===0?that.data.postMain.collect-1:that.data.postMain.collect+1,
            'postInfo.collect': res.data.data
          })
        } else {
          var e = ['收藏失败', res.data.message]
          that.showNotify(e)
        }
      },
      error: function() {
        var e = ['提示', '出了点儿错，稍后再试吧']
        that.showNotify(e)
      }
    })
  },

  toReport: function() {
    this.setData({
      isReport: this.data.isReport?false:true
    })
  },

  cancelbtn() {
    this.setData({
      isReport: false
    })
  },

  reportbtn: function() {
    if (this.data.report==="" || this.data.report.replace(/\s+/g, '').length===0) {
      let e = ['提示', '举报信息空空如也']
      this.showNotify(e)
      return
    }
    this.setData({
      showLoading: true
    })
    var that = this
    wx.request({
      url: app.globalData.baseUrl + "toReport",
      method: "POST",
      header: {
        'content-type': 'application/x-www-form-urlencoded',
        'cookie': 'JSESSIONID=' + app.globalData.SESSIONID
      },
      data: {
        postid: that.data.postid,
        report: that.data.report
      },
      success: function(res) {
        if (res.data.code===0) {
          that.setData({
            report: "",
            isReport: false
          })
          var e = ['提示', '举报信息已收到，我们将尽快核实并通知与您。感谢您为健康网络环境做出的贡献']
          that.showNotify(e)
        } else {
          var e = ['提示', res.data.message]
          that.showNotify(e)
        }
      },
      error: function() {
        var e = ['提示', '出了点儿错，稍后再试吧']
        that.showNotify(e)
      },
      complete: function() {
        that.setData({
          showLoading: false
        })
      },
      fail: () => {
        that.setData({
          showLoading: false
        })
      }
    })
  }

})