# 检索账户集

请求
- Service Key - ranch.account.query
- URI - /account/query

参数

|名称|类型|说明|
|---|---|---|
|uid|string|用户ID或UID，为空表示全部。|
|owner|char(100)|所有者ID，null或all表示所有。|
|type|int|类型，-1表示全部。|
|minBalance|int|最小余额，-1表示不限制。|
|maxBalance|int|最大余额，-1表示不限制。|

返回值
```json
[
    {
        "id": "ID值",
        "user": "用户",
        "owner": "所有者",
        "type": "类型",
        "balance": "余额",
        "deposit": "存入总额",
        "withdraw": "取出总额",
        "reward": "奖励总额",
        "profit": "盈利总额",
        "consume": "消费总额",
        "remitIn": "汇入总额",
        "remitOut": "汇出总额"
    }
]
```

> 后台管理接口，需验证[请求参数签名](https://github.com/heisedebaise/tephra/blob/master/tephra-ctrl/doc/sign.md)。
