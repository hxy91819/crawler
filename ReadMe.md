# 简易爬虫框架-项目结构

## 后端

在根目录下执行以下命令：

打包：
```
mvn clean package
```

启动Spring Boot服务：
```
java -jar service/target/service-0.0.1-SNAPSHOT.jar
```

### common

基础组件

### beautyleg

- 爬虫框架
- 框架爬取代码

### service

- 客户端，用于调用爬虫框架爬取数据，并持久化到数据库
- 提供web服务，供前端调用显示图片

## 前端

### fe-beautyleg

- 前端代码，React实现

安装依赖：
```
npm install
```

调试启动：
```
npm run start
```

打包：
```
npm run build
```

# todo list

- [x] 修复bug：分页查询到底了之后，仍旧继续进行查询
- [x] 为历史图片增加排序号
- [x] 详情页里面按照排序号进行排序
- [x] 爬虫跑出来数据的时候，根据文件名生成排序号
- [ ] 爬虫跑批和数据入库使用MQ加速处理
- [x] 列表页增加搜索功能
- [x] 列表页增加图片数量的显示
- [ ] 增量定时跑批新的图片的逻辑

## 难题求助

- [ ] 服务启动慢的问题
- [ ] Google浏览器支持防盗链导致图片无法查看的问题


# cmd

## 执行爬虫任务

内网中执行以下命令，参数需要进行URL Encode：

```
curl http://localhost:8080/manage/fetch/fetchAndSaveBeautyList?url=https%3a%2f%2fwww.meituri.com%2fx%2f74%2f
curl http://localhost:8080/manage/fetch/fetchAndSaveBeautyPics?org=%e7%a7%80%e4%ba%ba%e7%bd%91MyGirl%e7%be%8e%e5%aa%9b%e9%a6%86%e5%86%99%e7%9c%9f%e9%9b%86%e5%a4%a7%e5%85%a8

curl http://localhost:8080/manage/fetch/fetchAndSaveBeautyList?url=https%3a%2f%2fwww.meituri.com%2fx%2f63%2f
curl http://localhost:8080/manage/fetch/fetchAndSaveBeautyPics?org=%e5%b0%a4%e6%9e%9c%e7%bd%91Ugirls%e5%86%99%e7%9c%9f%e5%85%a8%e9%9b%86

curl http://localhost:8080/manage/fetch/fetchAndSaveBeautyList?url=https%3a%2f%2fwww.meituri.com%2fx%2f78%2f
curl http://localhost:8080/manage/fetch/fetchAndSaveBeautyList?url=https%3a%2f%2fwww.meituri.com%2fx%2f75%2f

curl http://localhost:8080/manage/fetch/fetchAndSaveBeautyList?url=https%3a%2f%2fwww.meituri.com%2fx%2f73%2f \
 && curl http://localhost:8080/manage/fetch/fetchAndSaveBeautyList?url=https%3a%2f%2fwww.meituri.com%2fx%2f71%2f \
 && curl http://localhost:8080/manage/fetch/fetchAndSaveBeautyList?url=https%3a%2f%2fwww.meituri.com%2fx%2f70%2f \
 && curl http://localhost:8080/manage/fetch/fetchAndSaveBeautyList?url=https%3a%2f%2fwww.meituri.com%2fx%2f68%2f \
 && curl http://localhost:8080/manage/fetch/fetchAndSaveBeautyList?url=https%3a%2f%2fwww.meituri.com%2fx%2f67%2f \
 && curl http://localhost:8080/manage/fetch/fetchAndSaveBeautyList?url=https%3a%2f%2fwww.meituri.com%2fx%2f62%2f

curl http://localhost:8080/manage/fetch/fetchAndSaveBeautyPics?org=FEILIN%e5%97%b2%e5%9b%a1%e5%9b%a1%e5%86%99%e7%9c%9f%e9%9b%86%e5%9b%be%e7%89%87 \
&& curl http://localhost:8080/manage/fetch/fetchAndSaveBeautyPics?org=IMiss%e7%88%b1%e8%9c%9c%e7%a4%be%e5%86%99%e7%9c%9f%e9%9b%86%e5%9b%be%e7%89%87 \
&& curl http://localhost:8080/manage/fetch/fetchAndSaveBeautyPics?org=MFStar%e6%a8%a1%e8%8c%83%e5%ad%a6%e9%99%a2%e5%86%99%e7%9c%9f%e9%9b%86%e5%9b%be%e7%89%87 \
&& curl http://localhost:8080/manage/fetch/fetchAndSaveBeautyPics?org=MiiTao%e8%9c%9c%e6%a1%83%e7%a4%be%e5%86%99%e7%9c%9f%e9%9b%86 \
&& curl http://localhost:8080/manage/fetch/fetchAndSaveBeautyPics?org=TuiGirl%e6%8e%a8%e5%a5%b3%e9%83%8e%e5%86%99%e7%9c%9f%e5%85%a8%e9%9b%86 \
&& curl http://localhost:8080/manage/fetch/fetchAndSaveBeautyPics?org=YouWu%e5%b0%a4%e7%89%a9%e9%a6%86%e5%86%99%e7%9c%9f%e9%9b%86 \
&& curl http://localhost:8080/manage/fetch/fetchAndSaveBeautyPics?org=%e5%8f%b0%e6%b9%be%e6%ad%a3%e5%a6%b9%2f%e5%8f%b0%e6%b9%be%e5%a5%b3%e7%a5%9e%2f%e5%8f%b0%e6%b9%be%e7%bd%91%e7%ba%a2%e7%be%8e%e5%a5%b3 \
&& curl http://localhost:8080/manage/fetch/fetchAndSaveBeautyPics?org=Ugirls%e7%88%b1%e5%b0%a4%e7%89%a9 
 


curl http://localhost:8080/manage/fetch/fetchAndSaveBeautyList?url=https%3a%2f%2fwww.meituri.com%2fx%2f66%2f

curl http://localhost:8080/manage/fetch/fetchAndSaveBeautyPics?org=%e6%b3%a2%e8%90%9d%e7%a4%beBoLoli%e5%86%99%e7%9c%9f%e5%a5%97%e5%9b%be%e5%85%a8%e9%9b%86
```
