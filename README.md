# 鹰眼酒店评价生成器 (Hawk Hotel Reviewer)

![Version](https://img.shields.io/badge/version-1.0.8-gold)
![Platform](https://img.shields.io/badge/platform-Android-navy)
![Aesthetics](https://img.shields.io/badge/design-Imperial_Nocturne-blue)

**鹰眼酒店评价生成器** 是一款专为高端商旅人士打造的智能点评工具。它结合了“静谧奢华”的设计理念与最前沿的多模态 AI 技术，旨在为用户提供仪式感十足的酒店评价生成体验。

## 🌟 核心亮点

- **静谧奢华 UI (Quiet Luxury)**：采用 “Imperial Nocturne” 设计风格，深蓝底色配以金属金点缀，辅以磨砂玻璃（Glassmorphism）特效，营造 concierge 级别的交互体验。
- **多模态视觉识别 (Vision AI)**：内置 **LongCat-Flash-Lite** 多模态引擎。只需上传酒店照片，AI 即可自动识别客房备品品牌（如 Dyson, Le Labo）、装修材质及窗外景观，并将其自然融入文案。
- **位置感知搜索 (Location-Aware)**：深度集成高德地图 REST API。支持实时定位与附近酒店权重搜索，确保选店精准无误。
- **嵌入式交互设计**：自研嵌入式搜索结果列表，解决移动端下拉框漂移痛点，交互流畅自然。

## 🛠️ 技术栈

- **UI 框架**：Jetpack Compose (声明式 UI)
- **依赖注入**：Dagger Hilt
- **异步处理**：Kotlin Coroutines & Flow
- **网络层**：Retrofit 2 & OkHttp
- **位置服务**：Google FusedLocationProvider & 高德 REST API
- **AI 引擎**：OpenAI 兼容协议 (LongCat / Gemini)

## 🚀 快速开始

1. **环境要求**：
   - Android Studio Jellyfish 或更高版本。
   - Android SDK 28+ (Android 9.0+)。

2. **配置密钥**：
   在项目根目录的 `local.properties` 中添加以下配置：
   ```properties
   AMAP_API_KEY=您的高德API服务Key
   LONGCAT_API_KEY=您的AI服务Key
   LONGCAT_BASE_URL=https://api.longcat.chat/openai/v1
   LONGCAT_MODEL=LongCat-Flash-Lite
   ```

3. **编译运行**：
   直接点击运行即可。应用已配置好所有 Dagger Hilt 和权限逻辑。

## 📸 版本记录

### v1.0.8 (Current)
- [New] 增强多模态视觉识别提示词，支持品牌与材质深度分析。
- [Fix] 补全核心代码 KDoc 注释，提升工程质量。
- [UI] 优化设置页面，保持 LongCat 默认配置的纯净感。

---
*Created by Antigravity AI for Hawk.*
