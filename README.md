# SysuInfoServicePlatform
A login interface
Add_StudentFrame Branch最新版本，添加了学生用户端社团活动界面的壳子。
1 从登陆界面按下“SIGN IN OR REGISTER"按键可跳转到学生端（即StudentPage.java）
2 学生端activity（StudentPage）包括四个Fragment选项卡，默认显示“个人主页”（对应的是StudentMain.java）
3 主界面右划唤出drawer菜单，包括四个选项卡，目前只做了"社团活动"（对应的是StudentActivity.java）
4 社团活动Fragment包括三个Tab，分别为“公益活动”，“体育活动”，“我的活动”，（分别对应serviceContent.java，sportContent.java，myactContent.java）
5 在“公益活动”，“体育活动”两个tab中显示的活动卡片均可点击，点击后跳转到该活动的详情介绍页（对应DetailActivity.java）
