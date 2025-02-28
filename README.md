# Peak App

Peak is a **focus timer application** built with **Jetpack Compose** using **MVI architecture** and **Koin for dependency injection**. The app helps users stay focused by tracking time spent on tasks.

## Features

- 📌 **Focus Timer**: Start, stop, and update a countdown timer.
- 🎨 **Jetpack Compose UI**: A modern UI with animations and material design components.
- 🏛 **MVI Architecture**: Uses a unidirectional data flow for state management.
- 🔗 **Koin DI**: Dependency injection using Koin.
- 🗓 **Jalali Date Support**: Displays the current date in the Persian calendar.
- 🕰 **Progress Animation**: Circular progress bar for visualizing focus time.

## Tech Stack

- **Kotlin**
- **Jetpack Compose**
- **Koin (Dependency Injection)**
- **State Management (MVI)**
- **Coroutines & Flow**
- **Material 3 Components**

## Project Structure

```
ir.halion.peak
│── MainActivity.kt
│── PeakApplication.kt
│
├── peresenter
│   ├── di
│   │   └── ViewModelModule.kt
│   ├── focus
│   │   ├── FocusScreen.kt
│   │   ├── FocusViewModel.kt
│   │   ├── FocusState.kt
│   │   ├── FocusEvent.kt
```

## Installation & Running

1. Clone the repository:
   ```sh
   git clone https://github.com/your-username/peak.git
   ```
2. Open the project in **Android Studio**.
3. Sync Gradle and install dependencies.
4. Run the app on an emulator or physical device.

## How to Use

1. Open the app.
2. Set a focus task and time.
3. Start the timer and stay focused!
4. Stop or reset the timer as needed.

## Contributing

Contributions are welcome! Feel free to fork the repo and submit a pull request.

## License

This project is licensed under the **MIT License**.

---

🚀 Built with love using **Jetpack Compose**!

