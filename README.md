# JYMultipleAdapter
这是一个Recyclerview Adapter的封装，使用于Adapter中有多种不同Item。
- 方便定义多种类型的Item。
- 支持滑动删除，拖动等。
- 后续将添加更多功能

#### 版本 ```0.02```
#### 用法
在```build.graldle```添加：
```
allprojects {
    repositories {
        jcenter()
        maven {
            url 'https://dl.bintray.com/tmwin/maven'
        }
    }
}
```
```
compile 'com.xjy.jymultipleadapter:jymultipleadapter:0.0.2'
```
在```Activity```中:
````
MyItemProvider myItemProvider = new MyItemProvider(this);
MultipleAdapter mMultipleAdapter = new MultipleAdapter();
mMultipleAdapter.registerCreator(Model.class, myItemProvider);
recyclerView.setLayoutManager(new LinearLayoutManager(this));
recyclerView.setAdapter(mMultipleAdapter);
myItemProvider.add(new Model());
mMultipleAdapter.notifyDataSetChanged();
````
### 更多用法请参考demo
