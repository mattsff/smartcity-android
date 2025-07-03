# SmartCity Map Search App

This Android app provides a map-based city search experience with a familiar, intuitive layout inspired by Google Maps.

## Project Scope

Current implementation status:
- [x] City data loading from API
- [x] City search functionality
- [x] Results display in BottomSheet
- [x] City visualization on map
- [x] Jetpack Compose UI implementation
- [ ] Favorites management
- [ ] Smooth camera transitions on map
- [ ] City detail view
- [ ] Unit and UI tests

## Other identified issues & improvements

- Improve Keyboard handling
- Clear markers when search is cleared
- Improve UI
- Improve UX for error and loading states
- Add Unit Tests, UI, and E2E
- Set up CI/CD pipelines
- Add localization support
- Integrate a linter


## Overview

- Search for cities using a search bar at the top of the screen.
- View a map in the background.
- See a bottom sheet list of city results (expanded by default).
- The list updates in real time as you type.
- The map only updates when:
    - You select a city from the list, or You press the search (keyboard "return") button.
- When the map updates:
    - The list collapses.
    - The map displays either the selected city or all search results.
- The number of results is limited for clarity and performance.
- No clustering is used, as the result set is controlled.
- Each city result indicates if it is marked as a favorite.

## Functional Flow

- **Search bar** at the top for city queries.
- **Map** as the main background.
- **Bottom sheet** with real-time updating city results.
- **Map updates** only on selection or search confirmation.
- **Favorites** status shown for each city.

## Prerequisites

- Android Studio Meerkat or higher
- Android SDK 26 or higher
- JAVA 17
- Kotlin 2.0.21+

## Setup & Usage

1. Clone the repository
2. Open the project in Android Studio
3. Configure Google Maps API Key (see Configuration section below)
4. Build and run on a device or emulator

## Running Unit Tests

You can run the unit tests from Android Studio:

- Right-click the `test` directory inside any module and select **Run Tests**.
- Or, from the terminal, run:

```sh
./gradlew test
```

## Technical Details

- **Architecture:** MVVM with Clean Architecture (modularized by layers)
- **UI:** Jetpack Compose
- **Dependency Injection:** Dagger Hilt
- **Navigation:** Jetpack Navigation Component
- **Networking:** Retrofit
- **Local Database:** Room
- **Serialization:** Kotlinx Serialization
- **Map:** Google Maps Compose
- **Testing:** JUnit, MockK

## Configuration

- API `BASE_URL` is set in the `data` module `build.gradle` file.
- **Google Maps API Key**: Add your Google Maps API Key in the `local.properties` file:
  ```
  MAPS_API_KEY=your_api_key_here
  ```
  If you don't have an API key, please contact the project maintainer to request one.


