##【ONE · 一个】说明

本软件是ONE的第三方客户端，分为四个模块，分别是文章模块、音乐模块、影视模块、图文模块。

- **文章模块**：对应了ONE的文章模块，在主界面就可以看到，用一个列表显示文章的基本信息，在列表中可以下拉刷新，上拉加载更多文章。点击文章后进入详情界面，里面有文章的具体内容、作者等各种信息。已经阅读过的文章会被缓存到本地，不需要再次加载。

- **音乐模块**：对应了ONE的音乐模块，在主界面就可以看到，用列表显示了乐评文章的信息，在列表中可以下拉刷新，上拉加载更多。点击进入后可以看到音乐的歌词、歌手等信息，同时也可以看到乐评的具体内容。已经看过的乐评会被缓存到本地，不需要再次加载。

- **影视模块**：对应了ONE的影视模块，在主界面就可以看到，用列表显示了影评的信息，在列表中能下拉刷新，也可以下拉加载更多影评。点击进入后可以看到电影的信息以及内容概括，同时也能看到文章作者对这个电影的影评。已经看过的影评会被缓存到本地，不需再次加载。

- **图文模块**：对应了ONE的图文模块，需要从侧边栏进入，用轮播图的形式显示了十张图片，图片随ONE中的图片模块的更新而更新。图片的下面是图片的期数以及与图片有关的文字。

---

## 项目设计及学习心得

### 整体设计

本项目基于MVP架构，View层只做了界面相关的处理，网络请求有关及数据库有关的操作均由Model层来处理，调用形式为**View=>Presenter=>Model**，而Model层的数据反馈则由接口来实现。

本次学习MVP架构的心得博客：[【Android】第三方ONE开发之MVP架构的学习以及思考](http://193.112.164.83/index.php/2018/05/13/%E3%80%90android%E3%80%91%E7%AC%AC%E4%B8%89%E6%96%B9one%E5%BC%80%E5%8F%91%E4%B9%8Bmvp%E6%9E%B6%E6%9E%84%E7%9A%84%E5%AD%A6%E4%B9%A0%E4%BB%A5%E5%8F%8A%E6%80%9D%E8%80%83/ "【Android】第三方ONE开发之MVP架构的学习以及思考")

### 界面相关

界面设计尽量贴近Material Design。主界面用了侧边栏+ViewPager+TabLayout的形式，三个Pager存放了三个Fragment，这些Fragment分别对应了文章、音乐、影视板块。三个板块都是用一个自定义的支持上拉加载更多的ListView来展示对应内容的列表。其中ListView的Adapter经过封装成了一个通用的Adapter。比较特殊的是图文板块，它和其他板块不同的是需要从侧边栏进入。是一个用ViewPager来实现的轮播图，里面包括了图片的期数以及相关文字，会自动滚动。

本次学习自定义上拉加载更多的ListView的心得博客：[【Android】第三方ONE开发之上拉加载更多的ListView](http://193.112.164.83/index.php/2018/05/17/%E3%80%90android%E3%80%91%E7%AC%AC%E4%B8%89%E6%96%B9one%E5%BC%80%E5%8F%91%E4%B9%8B%E4%B8%8A%E6%8B%89%E5%8A%A0%E8%BD%BD%E6%9B%B4%E5%A4%9A%E7%9A%84listview/ "【Android】第三方ONE开发之上拉加载更多的ListView")

本次学习封装ListView的万能Adapter的心得博客：[【Android】第三方ONE开发之ListView的学习以及万能Adapter的打造](http://193.112.164.83/index.php/2018/05/10/%E3%80%90android%E3%80%91listview%E7%9A%84%E5%AD%A6%E4%B9%A0%E4%BB%A5%E5%8F%8A%E4%B8%87%E8%83%BDadapter%E7%9A%84%E6%89%93%E9%80%A0/ "【Android】第三方ONE开发之ListView的学习以及万能Adapter的打造")

### 数据相关

#### 网络相关

本项目网络请求从model层发出，获取到的数据通过接口反馈给presenter层，然后返回给view层进行UI处理，图片做了三级缓存的机制，先从内存中找寻图片，找不到再从本地缓存中找寻图片，仍然找寻不到的情况下再从网络中获取，加快了图片加载的速度并且减小了流量的消耗

#### 数据库相关

本项目中，已经阅览过的文章/音乐/影视详细信息都会通过数据库的方式保存到本地，当要打开的文章已经被阅览过，就不用再进行网络请求json文本，而是从本地数据库获取相应信息，建立为对象，再反馈到View层显示在界面上。