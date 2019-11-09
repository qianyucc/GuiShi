## 规时

> 一款基于uni-app、ColorUI、SpringBoot开发的用于时间管理的微信小程序

### 主要功能

#### 倒计时

用户可以制定比较长远的计划，比如还有一月考六级、还有三个月放寒假等等

#### 日计划

这里主要用于合理规划自己的一天，用户可以添加不同的计划，可以给每个计划设置开始时间和结束时间。

- 未完成计划

未完成的计划显示在列表的最上面，完成计划之后需要手动将计划打上勾，这时候该计划就会移入已完成区

- 已完成计划

已完成计划放在未完成计划的下面

- 未完成计划

当前时间超过了结束时间的计划被认为是未完成的计划，放在最下面。

> 这里提供给用户“反悔”的操作，比如已经完成计划，但是忘记勾选计划。这个时候可以在未完成区将计划勾选，这是该计划会自动被归为已完成计划。

#### 日记

日记功能类似一个仅自己可见的朋友圈，每天可以在这里发布自己的想法，支持上传图片。

#### 未来信件

此功能是该小程序的辅助功能，只提供写信的功能，完成之后的信件不能修改，不能查看，只会在未来的一天（这个时间是用户指定的）通过邮箱的形式发送给用户，可以写给一年后的自己，或者一个月之后的自己等等。
