# fcitx5-hangul-debug

基于 [fcitx5-android](https://github.com/fcitx5-android/fcitx5-android) 的韩语输入法键盘显示修复版本。

## 问题

原版 fcitx5-android 的韩语(hangul)输入插件可以正常输入韩语文字，但虚拟键盘界面上始终显示英文字母，无法直观地展示韩语字符布局，对韩语初学者不友好。

## 修复内容

### 1. 韩语键盘显示 (`TextKeyboard.kt`)

启用韩语输入法后，键盘自动切换为韩语二式(Dubeolsik)布局显示：

```
英语模式:                韩语模式:
Q  W  E  R  T  Y  U  I  O  P     ㅂ  ㅈ  ㄷ  ㄱ  ㅅ  ㅛ  ㅕ  ㅑ  ㅐ  ㅔ
  A  S  D  F  G  H  J  K  L        ㅁ  ㄴ  ㅇ  ㄹ  ㅎ  ㅗ  ㅓ  ㅏ  ㅣ
Z  X  C  V  B  N  M                ㅋ  ㅌ  ㅊ  ㅍ  ㅠ  ㅜ  ㅡ
```

- 通过 `ime.languageCode == "ko"` 自动检测韩语输入法
- 切换回其他输入法时自动恢复英文键盘显示
- 韩语模式下按键始终发送小写英文字母（韩语引擎需要 QWERTY 键码）

### 2. 长按弹出紧辅音 (`PopupPreset.kt`)

支持长按弹出韩语紧辅音(双声母)和复合元音：

| 按键 | 显示 | 长按弹出 | 说明 |
|------|------|----------|------|
| R | ㄱ | ㄲ | 双kiyeok |
| E | ㄷ | ㄸ | 双digeut |
| Q | ㅂ | ㅃ | 双bieup |
| T | ㅅ | ㅆ | 双siot |
| W | ㅈ | ㅉ | 双jieut |
| O | ㅐ | ㅒ | 复合元音 |
| P | ㅔ | ㅖ | 复合元音 |

### 3. 插件识别修复 (`plugin-base/AndroidManifest.xml`)

修复 debug 版本无法识别插件的问题：将 intent-filter action 从硬编码改为使用 `${mainApplicationId}` 占位符，适配 debug 构建的应用 ID 后缀。

## 构建环境

- JDK 17
- Android SDK (compileSdk 36)
- NDK 28.0.13004108
- CMake 3.31.6
- MSYS2 (用于 Gettext)

## 构建方法

```bash
git clone https://github.com/wety1800/fcitx5-hangul-debug.git
cd fcitx5-hangul-debug
git submodule update --init --recursive

# 设置环境变量
export JAVA_HOME=/path/to/jdk17
export ANDROID_HOME=/path/to/android-sdk

# 编译
./gradlew assembleDebug
```

## 安装

需要安装两个 APK：

```bash
# 安装主应用
adb install app/build/outputs/apk/debug/*-arm64-v8a-debug.apk

# 安装韩语插件
adb install plugin/hangul/build/outputs/apk/debug/*-arm64-v8a-debug.apk
```

## 文件修改说明

| 文件 | 说明 |
|------|------|
| `app/.../keyboard/TextKeyboard.kt` | 韩语键盘显示核心逻辑 |
| `app/.../popup/PopupPreset.kt` | 韩语紧辅音长按弹出 |
| `lib/plugin-base/.../AndroidManifest.xml` | 插件 intent 占位符修复 |

## 许可证

基于 fcitx5-android 项目，遵循 LGPL-2.1-or-later 许可证。
