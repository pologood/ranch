# 检索

请求
- Service Key - ranch.stripe.query
- URI - /stripe/query

参数

无

返回值
```json
[{
    "key": "引用key",
    "name": "名称",
    "email": "Email",
    "publishable": "发行号",
    "secret": "密钥"
}]
```

> 后台管理接口，需验证[请求参数签名](https://github.com/heisedebaise/tephra/blob/master/tephra-ctrl/doc/sign.md)。
