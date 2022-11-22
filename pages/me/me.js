const app = getApp();
Page({
  data: {
    imgUrl:  app.globalData.imgUrl,
    isMark: "",
    isLogin: false,
    showNotify: false,
    notifyTitle: "",
    notifyDetail: "",
    basicInfo: {"mark":"NONE"},
    showAuth: false,
    showFeedback: false,
    feedback: "",
    showLoading: false,
    loadingTxt: "",
    showModifyName: false,
    newname: "",
    showClear: false,
    isNormal: false
  },
  
  onShow: function() {
    this.setData({
      isLogin: app.globalData.isLogin,
      isNormal: wx.getStorageSync('isNormal')
    })
    if (app.globalData.isLogin) {
      this.setData({
        showLoading: true,
        loadingTxt: "获取资料..."
      })
      this.getBasicInfo()
    }
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

  showAuthentication() {
    this.setData({
      showAuth: true
    })
  },
  
  closeWindow(e) {
    var windowid = e.currentTarget.dataset.windowid
    this.setData({
      [windowid]: false,
    })
    if (windowid==="showFeedback") {
      this.setData({
        feedback: ""
      })
    }
    if (windowid==="showModifyName") {
      this.setData({
        newname: ""
      })
    }
  },

  getBasicInfo: function() {
    var that = this
    wx.request({
      url: app.globalData.baseUrl + "getBasicInfo",
      // url: "http://localhost:8080/getBasicInfo",
      header: {
        'content-type': 'application/x-www-form-urlencoded',
        'cookie': 'JSESSIONID='+ app.globalData.SESSIONID
      },
      method: "POST",
      success(res) {
        if (res.data.code===0) {
          that.setData({
            basicInfo: res.data.data,
            showLoading: false,
            loadingTxt: ""
          })
        } else {
          var e = ['个人资料获取失败', res.data.message]
          that.showNotify(e)
        }
      },
      error() {
        var e = ['提示', '出了点儿错，稍后再试吧']
        that.showNotify(e)
      }
    })
  },

  toLogin: function() {
    wx.navigateTo({
      url: '/pages/login/login',
    })
  },

  previewImg: function(e) {
    let avatarurl = e.currentTarget.dataset.avatarurl
    wx.previewImage({
      urls: [avatarurl],
    })
  },

  toNext: function(e) {
    let id = e.currentTarget.dataset.id 
    if (id!=="about" && id!=="history" && id!=="instruc") {
      if (!this.data.isLogin) {
        var e = ['提示', '请先登录']
        this.showNotify(e)
        return
      }
    }
    wx.navigateTo({
      url: id + '/' + id,
    })
  },

  toFeedBack: function() {
    this.setData({
      showFeedback: true
    })
  },

  getInput(e) {
    let inputid = e.currentTarget.dataset.inputid
    this.setData({
      [inputid]: e.detail.value
    })
  },

  goFeedback: function() {
    if (this.data.feedback==="" || this.data.feedback.replace(/\s+/g, '').length===0) {
      var e = ['提示', '总得写点儿什么吧']
      this.showNotify(e)
      return
    }
    this.setData({
      showLoading: true,
      loadingTxt: "反馈中..."
    })
    var that = this
    wx.request({
      url: app.globalData.baseUrl + "addFeedback",
      method: "POST",
      header: {
        'content-type': 'application/x-www-form-urlencoded',
        'cookie': 'JSESSIONID=' + app.globalData.SESSIONID
      },
      data: {
        detail: that.data.feedback
      },
      success(res) {
        if (res.data.code===0) {
          var e = ['反馈成功', '你的建议我已经收到啦，我会认真阅读并尽快回复你哒']
          that.showNotify(e)
          that.setData({
            showFeedback: false
          })
        } else {
          var e = ['反馈失败', res.data.message]
          that.showNotify(e)
        }
      },
      error() {
        var e = ['提示', '出了点儿错，稍后再试吧']
        that.showNotify(e)
      },
      complete() {
        that.setData({
          showLoading: false,
          loadingTxt: ""
        })
      }
    })
  },

  changeAvatar() {
    if (!app.globalData.isLogin) {
      let e = ['提示', '请先登录']
      this.showNotify(e)
      return
    }
    let that = this
    wx.chooseImage({
      count: 1,
      sizeType: ['compressed'],
      sourceType: ['album', 'camera'],
      success (res) {
        let picsize = res.tempFiles[0].size;
        let path = res.tempFiles[0].path
        let formatImage = path.split(".")[(path.split(".")).length - 1];
        if (formatImage!="png"&&formatImage!="jpg"&&formatImage!="jpeg"&&formatImage!="gif") {
          let e = ['提示', '仅支持png, jpg, jpeg, gif格式图片']
          self.showNotify(e)
          return
        }
        if (picsize > 1000000) {
          let e = ['提示', '图片大小限制在1M以内']
          self.showNotify(e)
          return
        }
        that.setData({
          showLoading: true,
          loadingTxt: "光速修改中..."
        })
        that.toChangeAvatar(res.tempFilePaths[0])
      }
    })
  },

  toChangeAvatar(e) {
    let that = this
    let newavatar = e
    wx.uploadFile({
      filePath: newavatar,
      header: {
        'cookie': 'JSESSIONID=' + app.globalData.SESSIONID
      },
      name: 'newavatar',
      url: app.globalData.baseUrl + "changeAvatar",
      // url: 'http://localhost:8080/changeAvatar',
      success(res) {
        let resp = JSON.parse(res.data)
        if (resp.code===0) {
          that.setData({
            'basicInfo.user.avatarurl': resp.data
          })
          let e = ['提示', '头像更换成功，如果没有刷新请重启小程序']
          that.showNotify(e)
        } else {
          let e = ['提示', resp.message]
          that.showNotify(e)
        }
      },
      error() {
        let e = ['提示', '出了点儿错，稍后再试吧']
        that.showNotify(e)
      }
    })
  },

  changeName() {
    this.setData({
      showModifyName: true
    })
  },

  goModifyName() {
    let that = this
    wx.request({
      url: app.globalData.baseUrl + "changeName",
      // url: "http://localhost:8080/changeName",
      header: {
        'content-type': 'application/x-www-form-urlencoded',
        'cookie': 'JSESSIONID=' + app.globalData.SESSIONID
      },
      data: {
        newname: that.data.newname
      },
      success(res) {
        if (res.data.code===0) {
          that.setData({
            'basicInfo.user.name': that.data.newname,
            'showModifyName': false
          })
          let e = ['提示', '用户名修改成功']
          that.showNotify(e)
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

  clearCache() {
    this.setData({
      showClear: true
    })
  },

  toClearCache() {
    let that = this
    wx.clearStorage({
      success: (res) => {
        that.setData({
          showClear: false
        })
        wx.showToast({
          title: '操作成功!',
        })
      },
    })
    // wx.clearStorageSync()
    // this.setData({
    //   showClear: false
    // })
  }
})