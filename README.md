# JYMultipleAdapter
这是一个Recyclerview Adapter的封装，使用于Adapter中有多种不同Item。
- 方便定义多种类型的Item
- 支持Section
- 支持滑动删除，拖动等
- 添加头部尾部
- 头部固定
- 后续将添加更多功能

#### 版本 ```0.1.0```
#### 用法
在```build.graldle```添加：
````
allprojects {
    repositories {
        jcenter()
        maven {
            url 'https://dl.bintray.com/tmwin/maven'
        }
    }
}
````


````
compile 'com.xjy.widget:adapter:0.1.0'
````

## 自定义Provider
````
public class HomeItemProvider extends ItemProvider<Model> implements ItemProviderActionHelper{
    @Override
    public int onInflateLayout() {
        return R.layout.item_home_item;
    }

    @Override
    public void onBindViewHolder(MultipleViewHolder viewHolder, int position, Model item) {
        viewHolder.setText(R.id.textView, item.name);
    }

    //以下必须implements ItemProviderActionHelper

    //是否可以滑动删除
    @Override
    public boolean isItemCanSwipe(int position) {
        return false;
    }

    //是否可以拖动
    @Override
    public boolean isItemCanMove(int position) {
        return true;
    }

    @Override
    public boolean onItemSwipe(int position) {
        //返回true表示自己处理这个事件
        return false;//返回false表示不处理，MutilpleAdapter自动删除对应数据，更新界面
    }

    @Override
    public boolean onItemMove(int oldPosition, int newPosition) {
        //返回true表示自己处理这个事件
        return false;//返回false表示MutilpleAdapter处理，自动交换数据和更新界面
    }
}
````

## 自定义HeaderFooter

````
    public class MyHeader extends AbsHeaderFooterProvider<String>{

        @Override
        public int onInflateLayout() {
            return 0;
        }

        @Override
        public void onBindViewHolder(MultipleViewHolder viewHolder, int position, String item) {

        }

        @Override
        public boolean isKeep() {
        //设置滑动到顶部时固定，暂时只支持垂直方向
             return true;
        }
    }
````

## 使用

````
    RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
    MultipleAdapter multipleAdapter = new MultipleAdapter(this);
    recyclerview.setAdapter(multipleAdapter);

    HomeItemProvider provider = new HomeItemProvider();
    //将Provider注册到Adapter
    multipleAdapter.registerProvider(provider);
    //设置每行显示4列，此处不需要设置Layoutanager
    provider.setSpanSize(4);
    MyHeader header = MyHeader();
    provider.registerHeaderProvider("header", header);
    //如需使用自己的LayoutManager，请设置setUseDefaultSetting(false);
    //展开或收起一个Provider, section代表第几个Provider
    multipleAdapter.toggleExpand(section);

````

## 各种事件监听
- setOnProviderLongClickListener 长按事件
- setOnProviderClickListener 点击事件
- setOnClickViewListener 监听item中某个View的点击事件
- setOnLongClickViewListener 监听item中某个View的长按事件

### 更多用法请参考demo
