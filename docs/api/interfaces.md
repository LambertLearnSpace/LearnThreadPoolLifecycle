# API接口文档

## 通信模块

### 发送消息
- **接口描述**: 用于发送消息到指定用户。
- **请求方法**: POST
- **URL**: `/api/communication/send`
- **请求参数**:
  - `userId` (String): 用户ID
  - `message` (String): 消息内容
- **返回值**:
  - `status` (String): 发送状态
  - `messageId` (String): 消息ID
- **异常**:
  - `400 Bad Request`: 参数不正确
  - `500 Internal Server Error`: 服务器错误

### 接收消息
- **接口描述**: 用于接收用户消息。
- **请求方法**: GET
- **URL**: `/api/communication/receive/{userId}`
- **路径参数**:
  - `userId` (String): 用户ID
- **返回值**:
  - `messages` (List<String>): 用户收到的消息列表
- **异常**:
  - `404 Not Found`: 用户不存在
  - `500 Internal Server Error`: 服务器错误

## 同步模块

### 数据同步
- **接口描述**: 同步外部系统数据。
- **请求方法**: POST
- **URL**: `/api/synchronization/sync`
- **请求参数**:
  - `syncType` (String): 同步类型，指定同步的数据类型。
    - 可选值:
      - `FULL`: 全量数据同步
      - `INCREMENTAL`: 增量数据同步
      - `REFRESH`: 刷新现有数据
- **返回值**:
  - `status` (String): 同步状态
  - `details` (String): 同步详情
- **异常**:
  - `400 Bad Request`: 参数不正确
  - `500 Internal Server Error`: 服务器错误

### 检查同步状态
- **接口描述**: 检查数据同步的状态。
- **请求方法**: GET
- **URL**: `/api/synchronization/status/{syncId}`
- **路径参数**:
  - `syncId` (String): 同步任务的ID
- **返回值**:
  - `status` (String): 同步任务的当前状态
- **异常**:
  - `404 Not Found`: 同步任务不存在
  - `500 Internal Server Error`: 服务器错误
