# StudyCommunity
班级圈后端Project
## 数据库表字段
### 学校信息
| 字段 | 类型 | 主键 |
| --- | --- | --- |
| school_id | int | P |
| school_name | varchar(100) | |
### 班级信息
| 字段 | 类型 | 主键 | 说明 |
| --- | --- | --- | --- |
| clazz_id | varchar(100) | P | |
| clazz_name | varchar(100) | | |
| school_id | int | | |
| clazz_desc | varchar(255) | | 班级描述 |
### 教师信息
| 字段 | 类型 | 主键 | 说明 |
| --- | --- | --- | --- |
| userid | varchar(100) | P | |
| username | varchar(100) | | |
| sex | boolean | | Y：男 |
| role | boolean | | Y：班主任 |
| subj | varchar(100) | | 学科 |
| clazz_id | varchar(100) | | |
| phone | varchar(100) | | |
| pwd | varchar(100) | | |
| avatar | varchar(100) | | 头像路径 |
| cover | varchar(100) | | 封面图路径 |
### 学生信息
| 字段 | 类型 | 主键 | 说明 |
| --- | --- | --- | --- |
| userid | varchar(100) | P | |
| username | varchar(100) | | |
| sex | boolean | | |
| clazz_id | varchar(100) | | |
### 家长信息
| 字段 | 类型 | 主键 | 说明 |
| --- | --- | --- | --- |
| userid | varchar(100) | P | |
| username | varchar(100) | | |
| sex | boolean | | |
| child_id | varchar(100) | | |
| phone | varchar(100) | | |
| pwd | varchar(100) | | |
| avatar | varchar(100) | | 头像路径 |
| cover | varchar(100) | | 封面图路径 |
### 帖子信息
| 字段 | 类型 | 主键 | 说明 |
| --- | --- | --- | --- |
| post_id | varchar(100) | P | |
| userid | varchar(100) | | |
| clazz_id | varchar(100) | | |
| title | varchar(100) | | |
| detail | varchar(255) | | 帖子正文 |
| image | varchar(255) | | 图片路径 |
| video | varchar(255) | | 视频路径 |
| comments | varchar(65535) | | 评论id，分号隔开 |
| likes | int | | 点赞数 |
### 评论信息
| 字段 | 类型 | 主键 | 说明 |
| --- | --- | --- | --- |
| comment_id | varchar(100) | P | |
| post_id | varchar(100) | | |
| userid | varchar(100) | | |
| detail | varchar(255) | | 帖子正文 |
