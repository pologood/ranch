# 上传图片

上传文件可使用以下任一接口：
- [上传文件](https://github.com/heisedebaise/tephra/blob/master/tephra-ctrl/doc/upload.md)
- [上传多文件](https://github.com/heisedebaise/tephra/blob/master/tephra-ctrl/doc/uploads.md)
- [HTTP上传文件](https://github.com/heisedebaise/tephra/blob/master/tephra-ctrl-http/doc/upload.md)

上传时需将`name`或`key`设置为`ranch.classify`，支持的图片类型如下：

|类型|后缀|
|---|---|
|image/jpeg|.jpg or .jpeg|
|image/png|.png|
|image/gif|.gif|
|image/svg+xml|.svg|
