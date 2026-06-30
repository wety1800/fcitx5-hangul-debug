# Fcitx5 for Android - Hangul Edition

基于 [fcitx5-android](https://github.com/fcitx5-android/fcitx5-android) 的自定义版本，主要修改：

- 移除 debug 构建的 `applicationIdSuffix`，使 debug 版本与官方 release 使用相同的 `applicationId`
- 修复第三方插件（如 Fcitx5-SyncClipboard）无法识别的问题
- 支持韩语（Hangul）键盘显示

## 文件说明

| 目录/文件 | 说明 |
|-----------|------|
| `app/` | 主应用模块 |
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
