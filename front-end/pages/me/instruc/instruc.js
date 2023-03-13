const app = getApp()
const imgUrl= app.globalData.imgUrl;
Page({
  data: {
    isNormal: false,
    guides: [
      {
        title: '发帖',
        content: '你可以随时发布帖子。登陆后进入宇宙页面，点击右下角纸飞机图标，选择帖子类型即可进入编辑帖子页面。你可以选择使用纯色、系统默认图片、自定义图片三种模式作为帖子背景或者附件。单击选择图片按钮可以选择本地图片；单击系统默认图片图标后，小程序将自动将其设置为背景；单击纯色色块后，小程序会将纯色设置为帖子背景。注意，选择图片后如果要切换回纯色请手动点击图片区域右下角叉号以清除当前图片。',
        img: imgUrl+'wxbg/guide/g0.png'
      },
      {
        title: '评论/引用',
        content: '你可以对评论区的某条评论进行引用并回复。点击想要回复的评论右上角的引号图标，将开启回复模式，此时只需在评论区输入框中输入回复内容并点击评论即可。开启回复模式后，你可以通过点击取消按钮取消该操作。',
        img: imgUrl+'wxbg/guide/g1.png'
      },
      {
        title: '复制每日一句',
        content: '你可以自由复制每日一句的内容。点击想要复制的句子(区分中英文)，小程序将会提示复制结果。',
        img: imgUrl+'wxbg/guide/g2.png'
      },
      {
        title: '制作表情包',
        content: '你可以用每日一句来制作表情包。长按每日一句的句子区域，将会跳转到表情包制作页面。表情包制作页面下方左右滑动可以更改模板，点击右上角保存按钮可以将表情包保存至本地。目前仅支持默认的表情包模板，后续会考虑增加更多模板。欢迎反馈更多有趣的模板至quarkape@qq.com。',
        img: imgUrl+'wxbg/guide/g3.png'
      },
      {
        title: '修改头像/昵称',
        content: '你可以随时修改自己的头像。进入我的页面，点击头像可以查看大图，长按则可以选择本地图片并上传作为头像。小程序不提供头像裁剪功能，请提前将头像图片裁剪完成。建议裁剪为正方形，图片大小不能操作1M。你可以随时修改自己的昵称。进入我的页面，点击昵称，小程序将跳出修改昵称的输入框，在输入框内输入想要修改的昵称并点击确定即可完成修改。',
        img: imgUrl+'wxbg/guide/g4.png'
      },
      {
        title: '管理帖子/消息/通知',
        content: '你可以对发布、收藏的帖子，收到的消息、通知进行管理。单击发布或收藏的帖子，可以进入该贴页面。长按发布的帖子，可以对帖子执行删除操作；长按收藏的帖子，可以取消收藏该贴。未读的消息或通知在右上角有标识，单击未读消息或通知可以将其标识为已读；长按通知或消息，可以对其执行删除操作。',
        img: imgUrl+'wxbg/guide/g5.png'
      }
    ]
  },
  onShow: function() {
    this.setData({
      isNormal: wx.getStorageSync('isNormal')
    })
  },
  previewImg: function(e) {
    let imgurl = e.currentTarget.dataset.imgurl
    wx.previewImage({
      urls: [imgurl],
    })
  }
})