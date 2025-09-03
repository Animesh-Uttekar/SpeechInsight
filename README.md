# SpeechInsight 🎤

An Android application that combines speech-to-text recognition with AI-powered text analysis using OpenAI's GPT models.

## Features ✨

- **Speech-to-Text Recognition**: Convert spoken words to text using Android's built-in speech recognition
- **AI-Powered Analysis**: Automatically analyze recognized speech with ChatGPT for word simplification and effectiveness suggestions  
- **Firebase Authentication**: Secure user login and registration system
- **Interactive Chat Interface**: Chat with AI to get suggestions and improvements for your speech
- **Real-time Processing**: Immediate analysis of speech input with AI feedback

## Screenshots 📱

![Login Screen](signin_page.png)

## Architecture 🏗️

The app follows a multi-activity architecture with these main components:

### Activities
- **LoginActivity**: Firebase authentication and user sign-in (Entry point)
- **SignUpActivity**: User registration with Firebase Auth
- **MainActivity**: Speech recording and initial text display
- **ChatGPTActivity**: AI chat interface and text analysis
- **AnalyticsActivity**: (Placeholder for future analytics features)

### Key Classes
- **Message**: Data model for chat messages
- **MessageAdapter**: RecyclerView adapter for displaying chat conversations

## Technology Stack 🛠️

- **Language**: Java
- **Platform**: Android (API 26+)
- **Authentication**: Firebase Authentication
- **AI Integration**: OpenAI GPT-3.5 Turbo API
- **HTTP Client**: OkHttp3
- **UI Components**: 
  - RecyclerView for chat interface
  - Material Design components
  - Custom EditText styling

## Setup & Installation 🚀

### Prerequisites
- Android Studio
- Android device/emulator with API level 26 or higher
- Firebase project with Authentication enabled
- OpenAI API key

### Installation Steps

1. **Clone the repository**
   ```bash
   git clone https://github.com/Animesh-Uttekar/SpeechInsight.git
   cd SpeechInsight
   ```

2. **Firebase Setup**
   - Create a new Firebase project at [Firebase Console](https://console.firebase.google.com/)
   - Enable Authentication with Email/Password sign-in method
   - Download `google-services.json` and place it in the `app/` directory
   - Replace the existing `google-services.json` file

3. **OpenAI API Configuration**
   - Get your API key from [OpenAI Platform](https://platform.openai.com/api-keys)
   - In `ChatGPTActivity.java`, line 95, replace `"Bearer sk-"` with your actual API key:
     ```java
     .header("Authorization","Bearer YOUR_OPENAI_API_KEY_HERE")
     ```

4. **Build and Run**
   - Open the project in Android Studio
   - Sync the project with Gradle files
   - Run the app on your device/emulator

## Permissions 🔐

The app requires the following permissions:
- `RECORD_AUDIO`: For speech-to-text functionality

## Usage Flow 📋

1. **Authentication**: Users must sign up or log in using email/password
2. **Speech Input**: Tap the microphone icon to start speech recognition
3. **AI Analysis**: The app automatically sends recognized text to ChatGPT with the prompt: "Analyze the following text and provide a list of simpler, more effective words suitable for speech"
4. **Interactive Chat**: Users can continue chatting with the AI for further suggestions and improvements

## Configuration ⚙️

### Gradle Dependencies
```gradle
implementation 'androidx.appcompat:appcompat:1.6.1'
implementation 'com.google.android.material:material:1.11.0'
implementation 'com.squareup.okhttp3:okhttp:4.10.0'
implementation 'com.google.firebase:firebase-auth:22.3.1'
```

### API Configuration
- **OpenAI Model**: GPT-3.5 Turbo
- **API Endpoint**: `https://api.openai.com/v1/completions`
- **Temperature**: 0 (for consistent responses)

## Security Notes ⚠️

- **API Key Security**: The OpenAI API key is currently hardcoded. For production, consider:
  - Using Android Keystore for secure storage
  - Implementing a backend proxy to hide API keys
  - Using environment variables or secure configuration files

## Contributing 🤝

1. Fork the repository
2. Create a feature branch (`git checkout -b feature/amazing-feature`)
3. Commit your changes (`git commit -m 'Add some amazing feature'`)
4. Push to the branch (`git push origin feature/amazing-feature`)
5. Open a Pull Request

## Future Enhancements 🚀

- [ ] Analytics dashboard implementation
- [ ] Offline speech recognition
- [ ] Multiple language support
- [ ] Voice response from AI
- [ ] Conversation history storage
- [ ] Custom prompt templates
- [ ] Speech pattern analysis
- [ ] Export conversation transcripts

## Known Issues 🐛

- API key is exposed in source code (security concern)
- Limited error handling for network failures
- No offline functionality
- Deprecated `onActivityResult` method usage

## License 📄

This project is open source. Please check the repository for license details.

## Contact 📧

**Animesh Uttekar**
- GitHub: [@Animesh-Uttekar](https://github.com/Animesh-Uttekar)

---

**Note**: This app requires active internet connection for AI features and proper Firebase/OpenAI API configuration to function correctly.