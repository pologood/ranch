# 上架

请求
- Service Key - ranch.editor.sale
- URI - /editor/sale

参数

|名称|类型|说明|
|---|---|---|
|id|char(36)|ID值。|

返回值
```json
{
  "type": "类型",
  "sort": "顺序",
  "name": "名称",
  "keyword": "关键词",
  "width": "宽度",
  "height": "高度",
  "image": "预览图",
  "state": "状态：0-待审核；1-审核通过；2-审核拒绝；3-已上架；4-已下架",
  "create": "创建时间",
  "modify": "修改时间"
}
```

> 后台管理接口，需验证[请求参数签名](https://github.com/heisedebaise/tephra/blob/master/tephra-ctrl/doc/sign.md)。