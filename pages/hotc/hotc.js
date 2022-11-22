const app = getApp()
Page({
  data: {
    imgUrl:  app.globalData.imgUrl,
    isNormal: false,
    navs: ["晴圆", "阴缺"],
    curItem: 0,
    curIndex: 0,
    curDIndex: 0,
    aniBar: "",
    ww: wx.getSystemInfoSync().windowWidth,
    distance: 15,
    isSelect: false,
    sitem: [],
    ditem: [],
    picH: 0.558 * wx.getSystemInfoSync().windowWidth,
    cdate: [],
    scdate: [],
    showNotify: false,
    notifyTitle: "",
    notifyDetail: "",
    showLoading: false,
    canvas: null,
    _context: null,
    zpostarray: [0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14],
    dpostarray: [0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14],
    isCreatePost: false,
    pw: wx.getSystemInfoSync().windowWidth,
    ph: wx.getSystemInfoSync().windowHeight,
    isZ: true,
    postword: '',
    isShowFunc: false,
    voteDetail: [],
    isLogin: false
  },
  onLoad() {
    this.setData({
      isLogin: app.globalData.isLogin,
      showLoading: true,
      'cdate[0]': this.getEnDateStr(0),
      'cdate[1]': this.getEnDateStr(-1),
      'cdate[2]': this.getEnDateStr(-2),
      'cdate[3]': this.getEnDateStr(-3),
    })
    let cdate = this.data.cdate
    this.setData({
      'scdate[0][1]': cdate[0][1].substr(0, cdate[0][1].length - 2),
      'scdate[1][1]': cdate[1][1].substr(0, cdate[1][1].length - 2),
      'scdate[2][1]': cdate[2][1].substr(0, cdate[2][1].length - 2),
      'scdate[3][1]': cdate[3][1].substr(0, cdate[3][1].length - 2),
    })
    this.getSentence()
    this.getEnDateStr(0)
    this.getVoteDetail()
    wx.createSelectorQuery()
      .select('#canvas')
      .node()
      .exec((res) => {
          this.init(res[0].node);
      });
      this.gePicCount()
  },
  
  // 把监听用户登陆的函数放到onshow里面来，保证能够实时更新用户的登录态
  onShow() {
    this.setData({
      isLogin: app.globalData.isLogin,
      isNormal: wx.getStorageSync('isNormal')
    })
  },

  // 获取表情包模板个数
  gePicCount() {
    let that = this
    wx.request({
      url: app.globalData.baseUrl + '/getPicCount',
      header: {
        'content-type': 'application/www-x-form-urlencoded'
      },
      success: (res) => {
        if (res.data.code == 0) {
          let ztemp = [], dtemp = [];
          for (let i=0;i<res.data.data[0];i++) {
            ztemp.push(i);
          }
          for (let i=0;i<res.data.data[1];i++) {
            dtemp.push(i);
          }
          that.setData({
            zpostarray: ztemp,
            dpostarray: dtemp
          })
        } else {
          return;
        }
      }
    })
  },

  // 初始化函数
  init(canvas) {
    this.setData({
      canvas: canvas
    })
    canvas.width =  canvas._width
    canvas.height =  canvas._height
    this.setData({
      _context: canvas.getContext('2d')
    })
  },

  changeSentence(e) {
    this.setData({
      curIndex: e.detail.current
    })
    this.getVoteDetail()
  },

  changeDSentence(e) {
    this.setData({
      curDIndex: e.detail.current
    })
    this.getVoteDetail()
  },

  //图片方法预览
  picPreview: function(e) {
    var picUrl = []
    picUrl.push(e.currentTarget.dataset.url)
    wx.previewImage({
      urls: picUrl
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

  //复制文字
  copySentence(e) {
    let type = e.currentTarget.dataset.type
    let that = this
    wx.setClipboardData({
      data: type==='0'?that.data.sitem[that.data.curIndex].content:that.data.sitem[that.data.curIndex].note,
      success(res) {
        wx.showToast({
          title: '',
        })
      }
    })
  },

  copydSentence(e) {
    let dindex = e.currentTarget.dataset.dindex
    let that = this
    wx.setClipboardData({
      data: that.data.ditem[that.data.curDIndex][dindex],
      success(res) {
        wx.showToast({
          title: '内容已复制',
        })
      }
    })
  },

  getSentence() {
    let that = this
    wx.request({
      url: app.globalData.baseUrl + "getDailySentence",
      method: "GET",
      data: {
        type: 0,
        titlea: that.getDateStr(0),
        titleb: that.getDateStr(-1),
        titlec: that.getDateStr(-2),
        titled: that.getDateStr(-3),
      },
      success(res) {
        that.setData({
          showLoading: false,
          sitem: res.data.data
        })
      }
    })
  },

  changeNav: function(e) {
    let curItem = e.currentTarget.dataset.id
    this.setData({
      curItem: curItem,
      distance: 50*curItem + 15,
      showLoading: true
    })
    if (curItem===0) {
      this.getSentence()
    } else {
      this.getDuSentence()
    }
    this.getVoteDetail()
  },

  getDateStr(addDayCount) {
    let dd = new Date();
    dd.setDate(dd.getDate() + addDayCount);//获取AddDayCount天后的日期 
    let y = dd.getFullYear();
    let m = dd.getMonth() + 1;//获取当前月份的日期 
    let d = dd.getDate();
    if (m < 10) {
      m = '0' + m;
    };
    if (d < 10) {
      d = '0' + d;
    };
    return y + "-" + m + "-" + d;
  },

  getEnDateStr(addDayCount) {
    var dt = new Date();
    dt.setDate(dt.getDate() + addDayCount);//获取AddDayCount天后的日期
    let m=new Array("Jan","Feb","Mar","Apr","May","Jun","Jul","Aug","Spt","Oct","Nov","Dec");
    let w=new Array("Monday","Tuseday","Wednesday","Thursday","Friday","Saturday","Sunday");
    let d=new Array("st","nd","rd","th");
    let mn=dt.getMonth();
    let wn=dt.getDay();
    let dn=dt.getDate();
    var dns;
    if(((dn%10)<1) ||((dn%10)>3)){
      dns=d[3];
    } else {
      dns=d[(dn%10)-1];
      if((dn==11)||(dn==12)){
        dns=d[3];
      }
    }
    let res = []
    res[0] = m[mn];
    res[1] = dn + dns;
    if (wn===0) {
      res[2] = w[6]
    } else {
      res[2] = w[wn-1]
    }
    return res
  },

  getDuSentence() {
    let that = this
    wx.request({
      url: app.globalData.baseUrl + "getDailySentence",
      method: "GET",
      data: {
        type: 1,
        titlea: that.getDateStr(0),
        titleb: that.getDateStr(-1),
        titlec: that.getDateStr(-2),
        titled: that.getDateStr(-3),
      },
      success(res) {
        that.setData({
          showLoading: false,
          ditem: res.data.data
        })
      }
    })
  },

  // 将制作的海报保存到相册中
  saveImageToAlbum() {
    let that = this
    let w = 0.9*this.data.pw
    wx.canvasToTempFilePath({
      x: 0,
      y: 0,
      width: w,
      height: w,
      destWidth: w-1,
      destHeight: w-1,
      fileType: 'jpg',
      quality: 1,
      canvas: that.data.canvas,
      success: function (res) {
        that.setData({
          tempCanvasPath: res.tempFilePath
        })
        //将图片保存到相册
        wx.saveImageToPhotosAlbum({
          filePath: that.data.tempCanvasPath,
          success(res) {
            console.log(res);
          }
        })
      },
      fail: function (res) {
        console.log(res)
      }
    })
  },

  // 关闭post
  closePost() {
    this.setData({
      isCreatePost: false,
      isShowFunc: false
    })
    let _context = this.data._context
    let pw = this.data.pw
    _context.clearRect(0, 0, 0.9*pw, 0.9*pw)
  },

  createZPost(e) {
    let posttype = e.currentTarget.dataset.posttype
    let word = e.currentTarget.dataset.word
    this.setData({
      isCreatePost: true,
      isShowFunc: true,
      isZ: posttype==="0"?true:false,
      postword: word
    })
    this.toCreatePost(0, word)
  },

  // 绘制多行文本
  drawText: function (ctx, str) {
    let lineWidth = 0;
    let lastSubStrIndex = 0; // 每次开始截取的字符串的索引
    let pw = this.data.pw
    let leftWidth = 0.1*pw; // 从左开始绘制文本的位置
    let initHeight = 0.7*pw; // 开始绘制的文本距离画布顶部的距离
    let canvasWidth = 0.9*pw
    for (let i = 0; i < str.length; i++) {
      lineWidth += ctx.measureText(str[i]).width;
      if (lineWidth > 0.7*pw) {
        ctx.fillText(str.substring(lastSubStrIndex, i), leftWidth, initHeight); // 绘制截取部分
        initHeight += 30; // 16为字体的高度
        lineWidth = 0;
        lastSubStrIndex = i;
      }
      if (i == str.length - 1) { // 绘制剩余部分,此时i是最后一个字
        let reststr = str.substring(lastSubStrIndex, i + 1)
        ctx.fillText(reststr, 0.5*(canvasWidth - ctx.measureText(reststr).width), initHeight);
      }
    }
  },

  pickPostStyle(e) {
    let postid = e.currentTarget.dataset.postid
    this.toCreatePost(postid, this.data.postword)
  },

  // 选择海报样式
  toCreatePost(e, word) {
    let postid = e
    let isZ = this.data.isZ?'zpost':'dpost'
    let pw = this.data.pw
    let canvas = this.data.canvas
    let _context = this.data._context
    _context.clearRect(0, 0, 0.9*pw, 0.9*pw)
    _context.rect(0, 0, 0.9*pw, 0.9*pw)
    _context.fillStyle = '#ffffff'
    _context.fill()
    let img = canvas.createImage()
    img.src = this.data.imgUrl + 'wxbg/' + isZ + '/p' + postid + '.png'
    img.onload = () =>{
      _context.drawImage(img, 0.15*pw, 0, 0.6*pw, 0.6*pw)
    }
    _context.fillStyle = '#000000'
    _context.font = 'normal bold 24px sans-serif'
    this.drawText(_context, word, pw)
    _context.fillStyle = '#BBBBBB'
    _context.font = 'normal bold 15px sans-serif'
    _context.fillText('话题起源说', 0.7*pw, 0.85*pw, 0.7*pw);
  },

  // 获取正能量上面的相关投票数据，包含对句子的投票和使用者是否对这个点赞了
  getVoteDetail() {
    let that = this
    wx.request({
      url: app.globalData.baseUrl + "getVoteDetail",
      // url: "http://localhost:8080/getVoteDetail",
      header: {
        'cookie': 'JSESSIONID=' + app.globalData.SESSIONID,
        'content-type': 'application/www-x-form-urlencoded'
      },
      data: {
        type: that.data.curItem,
        date: that.data.curItem==0?that.getDateStr(-that.data.curIndex):that.getDateStr(-that.data.curDIndex)
      },
      success(res) {
        if (res.data.code===0) {
          that.setData({
            voteDetail: res.data.data
          })
        }
      },
    })
  },

  changeVoteState() {
    let that = this
    wx.request({
      url: app.globalData.baseUrl + "changeVoteState",
      // url: "http://localhost:8080/changeVoteState",
      header: {
        'content-type': "application/www-x-form-urlencoded",
        'cookie': 'JSESSIONID=' + app.globalData.SESSIONID
      },
      data: {
        type: that.data.curItem,
        date: that.data.curItem==0?that.getDateStr(-that.data.curIndex):that.getDateStr(-that.data.curDIndex)
      },
      success(res) {
        if (res.data.code===0) {
          that.setData({
            voteDetail: res.data.data
          })
        }
      }
    })
  },
})