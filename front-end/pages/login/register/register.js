const app = getApp()
Page({
  data: {
    imgUrl:  app.globalData.imgUrl,
    isNormal: false,
    // avatarUrl: "",
    isAgree: false,
    isSla: false,
    name: "",
    password: "",
    ensurepassword: "",
    email: "",
    authcode: "",
    codeText: "获取验证码",
    isInfoConfirmed: false,
    isGetAuthCode: false,
    restTime: 60,
    setInter: "",
    showLoading: false,
    loadingTxt: "",
    hasAuthcode: false,
    showNotify: false,
    notifyTitle: "",
    notifyDetail: ""
  },
  onShow: function() {
    this.setData({
      isNormal: wx.getStorageSync('isNormal')
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

  Sla: function() {
    this.setData({
      isSla: true
    })
  },

  closeSla: function() {
    this.setData({
      isSla: false
    })
  },

  changeCheckboxStatus: function(e) {
    this.setData({
      isAgree: this.data.isAgree?false:true
    })
  },

  // 快捷的获取到input值而不需要每一个输入框都写一个方法，通过data-inputid知道是哪一个输入框
  getInput: function(e) {
    var inputid = e.currentTarget.dataset.inputid
    this.setData({
      [inputid]: e.detail.value
    })
  },

  // click get authcode button
  toGetAuthCode: function() {
    let name = this.data.name;
    let password = this.data.password;
    let email = this.data.email;
    let ensurepassword = this.data.ensurepassword
    if (name==="" || password==="" || email==="") {
      var e = ['提示', '用户名或密码或邮箱空空如也']
      this.showNotify(e)
      return;
    }
    if (!(/^[A-Za-z0-9\u4e00-\u9fa5]+$/).test(name)) {
      var e = ['提示', '用户名中含有非法字符']
      this.showNotify(e)
      return;
    }
    if (!(/^\w+$/).test(password)) {
      var e = ['提示', '密码中含有非法字符']
      this.showNotify(e)
      return;
    }
    if (password!==ensurepassword) {
      var e = ['提示', '密码前后不一致']
      this.showNotify(e)
      return;
    }
    if (!(/^[a-zA-Z0-9_.-]+@[a-zA-Z0-9-]+(\.[a-zA-Z0-9-]+)*\.[a-zA-Z0-9]{2,6}$/.test(email))) {
      var e = ['提示', '邮箱格式有误']
      this.showNotify(e)
      return;
    }
    // 一旦格式正确，输入框将不可再输入并显示加载动画
    this.setData({
      isInfoConfirmed: true,
      showLoading: true,
      loadingTxt: "获取验证码"
    })
    // 发送请求，验证并获取验证码
    var that = this
    wx.request({
      url: app.globalData.baseUrl + "getAuthCodeAtRegister",
      // url: 'http://localhost:8080/getAuthCodeAtRegister',
      data: {
        name: name,
        email: email
      },
      method: "POST",
      header: {
        'content-type': 'application/x-www-form-urlencoded'
      },
      success: function(res) {
        if (res.data.code===0) {
          that.setData({
            hasAuthcode: true,
          })
          var e = ['提示', '验证码已发送到邮箱中，30分钟内填写有效']
          that.showNotify(e)
          // 保存当前session，用于验证码的验证
          app.globalData.SESSIONID = res.data.data.SESSIONID
          // 设置计时器
          that.data.setInter = setInterval(function() {
            if (that.data.restTime>0) {
              that.setData({
                restTime: that.data.restTime-1,
                codeText: that.data.restTime-1 + " s后可以重发",
                isGetAuthCode: true
              })
            } else {
              clearInterval(that.data.setInter)
              that.setData({
                isGetAuthCode: false,
                codeText: "获取验证码",
                restTime: 60
              })
            }
          }, 1000)
        } else {
          that.setData({
            isInfoConfirmed: false,
          })
          var e = ['提示', res.data.message]
          that.showNotify(e)
        }
      },
      error: function() {
        that.setData({
          isInfoConfirmed: false,
        })
        var e = ['提示', '出了点儿错，稍后再试吧']
        that.showNotify(e)
      },
      complete: function() {
        //关闭加载动画
        that.setData({
          showLoading: false,
          loadingTxt: ""
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

  // click register button and has select an avatar
  toRegister: function() {
    var name = this.data.name;
    var password = this.data.password;
    var email = this.data.email;
    var authcode = this.data.authcode;
    if (!this.data.isAgree) {
      var e = ['提示', '请先阅读并同意服务协议']
      this.showNotify(e)
      return
    }
    if (!this.data.hasAuthcode) {
      var e = ['提示', '请先获取验证码并填写']
      this.showNotify(e)
      return
    }
    if (authcode==="") {
      var e = ['提示', '请前往邮箱查看验证码并填写']
      this.showNotify(e)
      return
    }
    // show loading animation
    this.setData({
      showLoading: true,
      loadingTxt: "正在注册..."
    })
    var that = this
    wx.request({
      url: app.globalData.baseUrl + "register",
      // url: 'http://localhost:8080/register',
      header: {
        'content-type': 'application/x-www-form-urlencoded',
        'cookie': 'JSESSIONID=' + app.globalData.SESSIONID
      },
      method: "POST",
      data: {
        'name': name,
        'password': password,
        'email': email,
        'authcode': authcode
      },
      success (res) {
        if (res.data.code===0) {
          var e = ['提示', '注册成功!即将跳转到登录页面']
          that.showNotify(e)
          setTimeout(function() {
            wx.redirectTo({
              url: '/pages/login/login',
            })
          }, 2000)
        } else {
          var e = ['提示', res.data.message]
          that.showNotify(e)
        }
      },
      error() {
        var e = ['提示', '出了一点儿错，稍后再试吧']
        that.showNotify(e)
      },
      complete() {
        that.setData({
          showLoading: false,
          loadingTxt: ""
        })
      },
      fail: () => {
        that.setData({
          showLoading: false,
          loadingTxt: ""
        })
      }
    })
    // wx.uploadFile({
    //   url: 'http://localhost:8080/register',
    //   filePath: that.data.avatarUrl,
    //   name: 'avatar',
    //   header: {
    //     'content-type': 'application/x-www-form-urlencoded',
    //     'cookie': 'JSESSIONID=' + app.globalData.SESSIONID
    //   },
    //   method: "POST",
    //   formData: {
    //     'name': name,
    //     'password': password,
    //     'email': email,
    //     'authcode': authcode
    //   },
    //   success (res) {
    //     let resp = JSON.parse(res.data)
    //     if (resp.code===0) {
    //       var e = ['提示', '注册成功!即将跳转到登录页面']
    //       that.showNotify(e)
    //       setTimeout(function() {
    //         wx.redirectTo({
    //           url: '/pages/login/login',
    //         })
    //       }, 3000)
    //     } else {
    //       var e = ['提示', resp.message]
    //       that.showNotify(e)
    //     }
    //   },
    //   fail () {
    //     var e = ['提示', '出了一点儿错，稍后再试吧']
    //     that.showNotify(e)
    //   },
    //   complete () {
    //     that.setData({
    //       showLoading: false,
    //       loadingTxt: ""
    //     })
    //   }
    // })
  },
})