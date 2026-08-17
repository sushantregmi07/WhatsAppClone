# WhatsApp UI Clone

A pixel-accurate recreation of WhatsApp's iOS interface built natively for Android using **Kotlin**, **Jetpack Compose**, and **Material Design 3**. This project demonstrates modern Android development practices by translating high-fidelity Figma designs into a fully functional UI prototype.

## Screens

| Screen | Description |
|---|---|
| **Phone Authorization** | Country picker, phone number input with dynamic separator, form validation |
| **Chats List** | Conversation list with avatars, message previews, timestamps, unread badges, swipe actions (More/Archive), edit mode with multi-select, action sheet |
| **Conversation** | Message bubbles (sent/received), text/document/voice message types, date separators, inline timestamps with read receipts, chat wallpaper, message composer |

## Architecture

```
com.example.whatsappclone
├── data
│   ├── repository          # InMemoryChatRepository
│   └── seed                # Deterministic seed data (8 conversations, varied message types)
├── di                      # Hilt dependency injection modules
├── domain
│   ├── model               # AvatarKey, Message, MessageContent, ConversationSummary, DeliveryStatus
│   └── repository          # ChatRepository interface
├── feature
│   ├── authorization       # Phone auth screen, ViewModel, country picker
│   ├── chats               # Chats list screen, ViewModel, swipeable rows, action sheet
│   └── conversation        # Conversation screen, ViewModel, message bubbles, composer
├── navigation              # Type-safe routes (Serializable), NavHost
└── ui
    ├── components           # AvatarImage, ReadReceiptIcon, DocumentFileIcon
    └── theme                # Color, Typography, Dimens, Shape tokens
```

**Pattern:** Feature-first MVVM with unidirectional data flow (`StateFlow` for UI state, `SharedFlow` for one-shot events).

## Tech Stack

| Category | Library | Version |
|---|---|---|
| Language | Kotlin | 2.2.10 |
| UI | Jetpack Compose (BOM) | 2026.02.01 |
| Design System | Material Design 3 | via Compose BOM |
| Navigation | Navigation Compose | 2.9.8 |
| DI | Hilt + KSP | 2.60.1 |
| Serialization | kotlinx-serialization | 1.7.3 |
| Async | Coroutines | 1.10.2 |
| Build | AGP (Gradle Kotlin DSL) | 9.3.1 |
| Min SDK | Android 8.0 (API 26) | — |
| Target SDK | API 37 | — |

## Getting Started

### Prerequisites

- **Android Studio** Ladybug or newer
- **JDK 11+**
- Android SDK with API 37 installed

### Build & Run

```bash
# Clone the repository
git clone <repository-url>
cd WhatsAppClone

# Build the debug APK
./gradlew assembleDebug

# Run unit tests
./gradlew testDebugUnitTest

# Install on a connected device/emulator
./gradlew installDebug
```

## Key Features

### Phone Authorization
- Country picker dialog with search
- Dynamic country code display (adapts to codes like +1 through +977)
- Phone number validation with formatted input
- iOS-style top bar with centered title

### Chats List
- Shimmer loading skeleton
- Empty state with illustration
- Swipe-to-reveal actions (More, Archive)
- Edit mode with circular checkboxes and bulk operations (Archive, Read All, Delete)
- iOS-style action sheet (Mute, Contact Info, Export, Clear, Delete)
- Real-time preview sync — sending a message updates the chat preview and list order
- Bottom tab bar (Status, Calls, Camera, Chats, Settings)

### Conversation
- WhatsApp-style message bubbles with tail shapes
- Inline timestamp and read receipts (double-check marks) embedded in message text
- Document message cards with file icon, name, size, and extension
- Voice message placeholders (received and sent variants)
- Date separator chips
- Chat wallpaper background
- Composer with emoji, camera, and mic/send toggle
- Edge-to-edge display with proper `WindowInsets` handling

### Design System
- Complete color token set matching WhatsApp's palette
- Typography scale with consistent `FontFamily.Default` across all screens
- Dimension constants for spacing, sizing, and corner radii
- Custom bubble shapes (sent vs. received with tail corners)

## Testing

Unit tests cover ViewModels and the repository layer:

| Test File | Coverage |
|---|---|
| `InMemoryChatRepositoryTest` | Seed data integrity, message ordering, preview sync, send/delete/mute/archive/clear operations |
| `PhoneAuthorizationViewModelTest` | Phone input validation, country selection |
| `ChatsViewModelTest` | Edit mode, selection state, stale selection cleanup, action sheet |
| `ConversationViewModelTest` | Message loading, sending, preview updates, multi-conversation isolation |

```bash
./gradlew testDebugUnitTest
```

## Seed Data

The app ships with 8 pre-seeded conversations containing deterministic messages across all content types:

- **Text** — single-line and multi-line messages
- **Document** — file cards (PDF, PNG, DOCX) with size labels
- **Voice** — duration-labeled voice messages
- **Photo** — camera icon previews on the chats list

Each conversation has incoming and outgoing messages with explicit timestamps and delivery statuses (Sent, Delivered, Read), ensuring the Chats list preview always matches the actual latest message.