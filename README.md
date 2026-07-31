<p align="center">
  <img src="./assets/banner.svg" alt="Star Wars Explorer banner" width="100%" />
</p>

<p align="center">
  <img src="https://img.shields.io/badge/Kotlin-7F52FF?style=for-the-badge&logo=kotlin&logoColor=white" />
  <img src="https://img.shields.io/badge/Jetpack%20Compose-4285F4?style=for-the-badge&logo=jetpackcompose&logoColor=white" />
  <img src="https://img.shields.io/badge/Android-3DDC84?style=for-the-badge&logo=android&logoColor=white" />
</p>

Android app built with **Kotlin** and **Jetpack Compose** for the Axians "Jedi Council" technical challenge. Lets users search, filter, sort, and view details about Star Wars characters using the [SWAPI](https://swapi.dev/).

---

## ✨ Features

- 🔍 Search characters by name
- 🚻 Filter by gender
- 🔤 Sort alphabetically or by birth year
- ♾️ Infinite scroll pagination
- 📋 Character detail screen with full stats (height, mass, hair/skin/eye color, birth year, gender)
- 🖼️ Character photos (via [akabab/starwars-api](https://github.com/akabab/starwars-api), since SWAPI doesn't provide images)
- 🌗 Light/dark mode support (follows system preference)

---

## 🛠️ Tech stack

| Layer | Technology |
|---|---|
| UI | Kotlin + Jetpack Compose |
| Networking | Retrofit + kotlinx.serialization |
| Async / state | Coroutines + StateFlow |
| Navigation | Navigation Compose |
| Images | Coil |

---

## 🏗️ Architecture

The project follows a layered architecture:

```
data/
 ├── remote/        → Retrofit services and API clients
 ├── model/         → Data models (Person, SwapiResponse, CharacterImage)
 └── repository/    → Abstracts data sources from ViewModels
ui/
 ├── list/          → Character list screen (search, filter, sort, pagination)
 ├── detail/        → Character detail screen
 └── theme/         → Material3 theming (Star Wars inspired yellow/black palette)
navigation/         → App-wide navigation graph
```

Each screen follows a **stateful/stateless split**: a `Screen` composable owns the ViewModel, and a `View` composable receives plain state and callbacks — making the UI previewable and testable independent of business logic.

---

## 📝 Notes

- **API mirror:** `swapi.dev` has known DNS/uptime issues, so this project uses `swapi.py4e.com`, a maintained mirror with an identical API schema, as the base URL.
- **Photos:** SWAPI doesn't provide character images. Photos are fetched separately from `akabab/starwars-api` and matched by character name.

---

## 🚀 Setup

1. Clone the repository
2. Open in Android Studio
3. Sync Gradle
4. Run on emulator or device (minSdk 24+)

<p align="center">
  <em>May the Force be with your build.</em>
</p>
