const app = getApp();
Page({
  data: {
    imgUrl:  app.globalData.imgUrl,
    isNormal: false,
    postType: 0,
    isAnonymous: false,
    isMoreInfo: false,
    picUrls: [],
    customBg: ['DA4453','E9573F','8CC152','39b54a','48CFAD','37BCC9','4FC1E9','3BAFDA','4A89DC','0081ff','967ADC','6739b6','D770AD','9c26b0','a5673f','8799a3','656D78','434A54'],
    pickedCustomBg: "0081ff",
    title: "",
    content: "",
    placeHolders: ["真知灼见", "今日趣事", "优质资源"],
    showNotify: false,
    notifyTitle: "",
    notifyDetail: "",
    showLoading: false,
    defaultbg: ['nbga.jpg', 'nbgb.jpg', 'nbgc.jpg', 'nbgd.jpg', 'nbge.jpg']
  },
  onShow: function() {
    this.setData({
      isNormal: wx.getStorageSync('isNormal')
    })
  },

  // 显示一个提示消息，没有关闭按钮，一定时间后自动消失，使用时直接调用并传递一个数组
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

  showAnonymousInfo: function() {
    this.setData({
      isMoreInfo: true
    })
  },

  onLoad: function(e) {
    this.setData({
      postType: e.postType
    })
  },

  closeSelect: function() {
    this.setData({
      isMoreInfo: false
    })
  },

  changeCheckboxStatus: function(e) {
    this.setData({
      isAnonymous: this.data.isAnonymous?false:true
    })
  },

  selectPic: function() {
    var picUrl = []
    var self = this
    wx.chooseImage({
      count: 1,
      sizeType: ['compressed'],
      sourceType: ['album', 'camera'],
      success: function (res) {
        let picsize = res.tempFiles[0].size;
        let path = res.tempFiles[0].path
        let formatImage = path.split(".")[(path.split(".")).length - 1];
        if (formatImage!="png"&&formatImage!="jpg"&&formatImage!="jpeg"&&formatImage!="gif") {
          let e = ['提示', '仅支持png, jpg, jpeg, gif格式图片']
          self.showNotify(e)
          return
        }
        if (picsize > 3000000) {
          let e = ['提示', '图片大小限制在3M以内']
          self.showNotify(e)
          return
        }
        picUrl.push(res.tempFilePaths[0])
        self.setData({
          picUrls: picUrl,
          pickedCustomBg: "0081ff"
        })
      }
    })
  },

  previewPic: function() {
    wx.previewImage({
      urls: this.data.picUrls,
    })
  },

  deletePic: function() {
    var picUrl = []
    this.setData({
      picUrls: picUrl
    })
  },

  getInput: function(e) {
    var inputid = e.currentTarget.dataset.inputid
    this.setData({
      [inputid]: e.detail.value,
    })
  },
  
  pickDefaultBg(e) {
    // this.deletePic()
    let bgindex = e.currentTarget.dataset.bgindex
    this.setData({
      'picUrls[0]': this.data.imgUrl + '/wxbg/' + this.data.defaultbg[bgindex]
    })
  },

  pickCurrentColor: function(e) {
    this.deletePic()
    this.setData({
      pickedCustomBg: e.currentTarget.dataset.custombg
    })
  },

  addPicturePost: function() {
    this.setData({
      showLoading: true
    })
    var that = this
    wx.uploadFile({
      filePath: that.data.picUrls[0],
      header: {
        'content-type': 'application/x-www-form-urlencoded',
        'cookie': 'JSESSIONID=' + app.globalData.SESSIONID
      },
      method: "POST",
      name: 'postpicture',
      formData: {
        'type': that.data.postType,
        'title': that.data.title,
        'content': that.data.content,
        'imgtype': 0,
        'imgurl': "",
        'anonymous': that.data.isAnonymous?1:0
      },
      url: app.globalData.baseUrl + 'addPicturePost',
      success: function(res) {
        let resp = JSON.parse(res.data)
        if (resp.code===0) {
          var e = ["发布成功", "发布成功，即将跳转到首页"]
          that.showNotify(e)
          setTimeout(function() {
            wx.switchTab({
              url: '/pages/bbs/bbs',
            })
          }, 2000)
        } else {
          var e = ["发布失败",resp.message]
          that.showNotify(e)
        }
      },
      error: function() {
        var e = ["提示", "出了点儿错，稍后再试吧"]
        that.showNotify(e)
      },
      complete: function() {
        that.setData({
          showLoading: false
        })
      }
    })
  },

  addSimplePost: function(e) {
    let action = e
    this.setData({
      showLoading: true
    })
    var that = this
    wx.request({
      url: app.globalData.baseUrl + 'addSimplePost',
      data: {
        type: that.data.postType,
        title: that.data.title,
        content: that.data.content,
        anonymous: that.data.isAnonymous?1:0,
        imgtype: action[0],
        imgurl: action[1]
      },
      method: "POST",
      header: {
        'content-type': 'application/x-www-form-urlencoded',
        'cookie': 'JSESSIONID=' + app.globalData.SESSIONID
      },
      success: function(res) {
        if (res.data.code===0) {
          var e = ["发布成功", "发布成功，即将跳转到首页"]
          that.showNotify(e)
          setTimeout(function() {
            wx.switchTab({
              url: '/pages/bbs/bbs',
            })
          }, 2000)
        } else {
          var e = ["提示", res.data.message]
          that.showNotify(e)
        }
      },
      error: function() {
        var e = ["提示", "出了点儿错，稍后再试吧"]
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
  },

  toPost: function() {
    let title = this.data.title
    let content = this.data.content
    let picUrlsObj = this.data.picUrls
    if (title.trim().length===0 || content.trim().length===0) {
      var e = ["提示","标题不能为空~"]
      this.showNotify(e)
      return
    }
    // if (title.replace(/\s+/g, '').length===0 || content.replace(/\s+/g, '').length===0) {
    //   var e = ["提示","标题或内容不能全为空"]
    //   this.showNotify(e)
    //   return
    // }
    if (title.length > 40 || content.length > 1000) {
      var e = ["提示","标题或正文字数超过限制"]
      this.showNotify(e)
      return
    }
    // 此方法中，simplepost是指使用预设图片以及纯色背景两种情况
    // picturepost是指上传了自定义的图片的情况
    // 后端可通过imgurl是否包含wxbg字符串来判断是预设图片还是纯色背景
    // 代码0表示添加了图片（包括预设图片），代码1表示只有纯色背景
    // 后端会进行处理，最终0表示自定义图片，1表示预设图片，2表示纯色背景
    if (picUrlsObj.length!==0) {
      if (picUrlsObj[0].indexOf(this.data.imgUrl) !== -1) {
        var e = [0, this.data.picUrls]
        this.addSimplePost(e)
      } else {
        this.addPicturePost()
      }
    } else {
      var e = [1, this.data.pickedCustomBg]
      this.addSimplePost(e)
    }
  },

  closeResultMsg: function() {
    this.setData({
      isResultMsg: false
    })
  }
})