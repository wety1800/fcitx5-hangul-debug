# Fcitx5 for Android - Hangul Edition

基于 [fcitx5-android](https://github.com/fcitx5-android/fcitx5-android) 的自定义版本，主要修改：

- 移除 debug 构建的 `applicationIdSuffix`，使 debug 版本与官方 release 使用相同的 `applicationId`
- 修复第三方插件（如 Fcitx5-SyncClipboard）无法识别的问题
- 支持韩语（Hangul）键盘显示
- 集成外部语音输入（AIDL）支持，基于 [fcitx5-android-bibi-keyboard](https://github.com/BryceWG/fcitx5-android-bibi-keyboard)

## 两个版本

| 版本 | 说明 | 下载 |
|------|------|------|
| 标准版 | 基于官方项目，包含所有插件 | `fcitx5-hangul.apk` |
| 含语音插件版 | 基于修改版本，加入语音模块 | `fcitx5-hangul-voice.apk` |

## 文件说明

| 目录/文件 | 说明 |
|-----------|------|
| `app/` | 主应用模块 |
| `app/src/main/aidl/` | AIDL 接口（外部语音服务通信） |
| `app/src/main/java/.../link/` | 语音客户端代码 |
| `app/src/main/java/.../input/voice/` | 语音波形视图 |
| `build-logic/` | 构建逻辑插件（已修改 applicationId 相关配置） |
| `lib/common/` | 公共库（IPC 通信等） |
| `lib/fcitx5/` | Fcitx5 核心库 |
| `lib/fcitx5-lua/` | Lua 插件支持 |
| `lib/fcitx5-chinese-addons/` | 中文输入法附加组件 |
| `lib/libime/` | IME 核心库 |
| `lib/plugin-base/` | 插件基础库 |
| `plugin/anthy/` | 日语输入法插件 |
| `plugin/chewing/` | 注音输入法插件 |
| `plugin/clipboard-filter/` | 剪贴板过滤插件 |
| `plugin/jyutping/` | 粤语拼音插件 |
| `plugin/rime/` | Rime 输入法插件 |
| `plugin/sayura/` | 僧伽罗语输入法插件 |
| `plugin/thai/` | 泰语输入法插件 |
| `plugin/unikey/` | 越南语输入法插件 |

## 韩语插件

韩语（Hangul）插件请从官方版本下载：

**官方韩语插件下载地址：**
https://github.com/fcitx5-android/fcitx5-android/releases/latest

选择对应架构的 `org.fcitx.fcitx5.android.plugin.hangul-*-release.apk` 安装即可。

## 语音输入功能

语音输入功能基于 [fcitx5-android-bibi-keyboard](https://github.com/BryceWG/fcitx5-android-bibi-keyboard)，通过 AIDL 与外部语音服务（言犀/asr-keyboard）通信。

### 使用方法

1. 安装言犀（asr-keyboard）应用：[com.brycewg.asrkb](https://github.com/BryceWG/asr-keyboard) 或 [com.brycewg.asrkb.pro](https://github.com/BryceWG/asr-keyboard)
2. 在 Fcitx5 设置中，将空格键长按行为设置为"语音输入（AIDL）"
3. 长按空格键启动语音输入，再次点击空格键结束输入

### 权限说明

- `RECORD_AUDIO`：用于录制语音输入
- `com.brycewg.asrkb.EXTERNAL_SPEECH`：访问言犀外部语音服务
- `com.brycewg.asrkb.pro.EXTERNAL_SPEECH`：访问言犀 Pro 版外部语音服务

## 构建说明

```bash
# 设置 Android SDK 路径
export ANDROID_HOME=/path/to/android/sdk

# 编译 debug 版本（仅 arm64-v8a）
./gradlew :app:assembleDebug
```

## 更新日志

### 2026-06-30
- 移除 debug 构建的 `applicationIdSuffix = ".debug"`，修复第三方插件无法识别的问题
- 更新 `MAIN_APPLICATION_ID` 配置，使 debug 版本使用 `org.fcitx.fcitx5.android`（与官方一致）
- 添加 `NativeBuildTasks.kt` 的异常处理，容忍 Windows 环境下缺少 Gettext 工具的构建错误
- 集成外部语音输入（AIDL）支持，基于 fcitx5-android-bibi-keyboard
- 添加 `WaveLineView` 依赖用于语音波形显示
