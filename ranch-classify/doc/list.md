# 检索分类信息集

请求
- Service Key - ranch.classify.list
- URI - /classify/list

参数

|名称|类型|说明|
|---|---|---|
|code|char(100)|编码前缀，会自动匹配【code+%】，必须。|
|key|char(100)|包含的键值。|
|name|char(100)|包含的名称。|

返回值
```json
[
    {
        "id": "ID值",
        "code": "编码",
        "key": "键",
        "value": "值",
        "name": "名称"
    }
]
```

> [扩展属性](json.md)

> [刷新缓存](refresh.md)
