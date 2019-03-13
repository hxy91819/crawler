# 简易爬虫框架-项目结构

## common

基础组件

## beautyleg

- 爬虫框架
- 框架爬取代码

## service

- 客户端，用于调用爬虫框架爬取数据，并持久化到数据库
- 提供web服务，供前端调用显示图片

## fe-beautyleg

- 前端代码，React实现

# todo list

- [x] 修复bug：分页查询到底了之后，仍旧继续进行查询
- [x] 为历史图片增加排序号
- [x] 详情页里面按照排序号进行排序
- [ ] 爬虫跑出来数据的时候，根据文件名生成排序号
- [ ] 列表页增加搜索功能
- [ ] 列表页增加图片数量的显示
- [ ] 增量定时跑批新的图片的逻辑
- [ ] 服务启动慢的问题
- [ ] 部署优化，内部走内网请求
- [ ] 优化防盗链