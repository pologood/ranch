# 阅读JSON

请求
- Service Key - ranch.doc.read-json
- URI - /doc/read-json

参数

|名称|类型|必须|说明|
|---|---|---|---|
|id|char(36)|是|ID值。|

返回值
```json
{
    "id": "ID值",
    "classifies": ["分类集"],
    "author": {},
    "category": "类别",
    "sort": "顺序",
    "subject": "标题",
    "image": "主图URI地址",
    "thumbnail": "缩略图URI地址",
    "summary": "摘要",
    "label": "标签",
    "type": "类型",
    "source": "内容源",
    "content": "内容",
    "read": "阅读次数",
    "favorite": "收藏次数",
    "comment": "评论次数",
    "praise": "点赞数",
    "score": "得分",
    "time": "更新时间",
    "relation": {
        "previous": {},
        "next": {}
        "relates": [{}]
    }
}
```
