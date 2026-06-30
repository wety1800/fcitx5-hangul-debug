# fcitx5-hangul-debug

基于 [fcitx5-android](https://github.com/fcitx5-android/fcitx5-android) 的韩文（Hangul）输入法调试版本。

## 项目说明

本项目在 fcitx5-android 基础上，专注于韩文输入法的开发与调试，主要修改包括：

- 韩文键盘显示优化（TextKeyboard）
- ECM CMake 模块重构
- CI 构建流程修复

## 支持的输入法

- 韩文（Hangul）— 通过 [fcitx5-hangul](https://github.com/fcitx/fcitx5-hangul) 插件
- 中文（拼音、双拼、五笔等）
- 英文（含拼写检查）
- 其他语言（Rime、Anthy 等可选插件）

## 构建

### 环境要求

- Android SDK Platform & Build-Tools 35
- Android NDK 28.0.13004108
- CMake 3.31.6
- [KDE/extra-cmake-modules](https://github.com/KDE/extra-cmake-modules)
- GNU Gettext >= 0.20

### 快速开始

```bash
git clone https://github.com/wety1800/fcitx5-hangul-debug.git
cd fcitx5-hangul-debug
git submodule update --init --recursive

# Linux/macOS
sudo apt install extra-cmake-modules gettext  # Debian/Ubuntu

# 构建 APK
./gradlew :app:assembleRelease
```

### CI

GitHub Actions 自动构建 arm64-v8a APK，每次推送到 master 分支触发。

## 上游同步

```bash
git remote add upstream https://github.com/fcitx5-android/fcitx5-android.git
git fetch upstream
git rebase upstream/master
git submodule update --init --recursive
git push
```

## 已知问题

- `libime` 的 `kenlm` 子模块在 CI 上无法正确初始化（已通过禁用 tools 构建绕过）
- OpenCC 预编译库的 Marisa 依赖路径大小写问题（已修复）

## 许可证

本项目继承上游 [fcitx5-android](https://github.com/fcitx5-android/fcitx5-android) 的 GPL-2.0 许可证。
