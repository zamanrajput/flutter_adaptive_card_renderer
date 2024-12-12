# adaptive_card_renderer

`adaptive_card_renderer` is a Flutter plugin that enables rendering [Adaptive Cards](https://adaptivecards.io/) directly in your Flutter applications. Adaptive Cards are a way to exchange UI content in a cross-platform, human-readable format.

This plugin supports rendering Adaptive Cards on both Android and iOS, leveraging platform-specific views for an optimized and native experience.

## Features

- **Dynamic Adaptive Card Rendering**: Pass JSON data to render interactive cards.
- **Cross-Platform Support**: Works seamlessly on Android and iOS.
- **Customizable Input**: Accepts JSON directly from the user to render cards on the fly.

## Getting Started

### Installation

Add the following dependency to your `pubspec.yaml` file:

```yaml
dependencies:
  adaptive_card_renderer: ^0.0.1
```

### Android Configuration
```xml

<style name="LaunchTheme" parent="@style/Theme.MaterialComponents.DayNight">
    <!-- Your theme configurations -->
</style>


```

### Example Usage
```dart
import 'package:adaptive_card_renderer/adaptive_card_renderer.dart';
import 'package:flutter/material.dart';

void main() {
  runApp(const MyApp());
}

class MyApp extends StatelessWidget {
  const MyApp({Key? key}) : super(key: key);

  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      home: Scaffold(
        appBar: AppBar(title: const Text('Adaptive Card Renderer')),
        body: Center(
          child: AdaptiveCardView(
            jsonText: '''
{
  "type": "AdaptiveCard",
  "$schema": "http://adaptivecards.io/schemas/adaptive-card.json",
  "version": "1.6",
  "body": [
    {
      "type": "TextBlock",
      "text": "Hello Adaptive Cards!",
      "size": "Medium",
      "weight": "Bolder"
    }
  ]
}
            ''',
          ),
        ),
      ),
    );
  }
}
```

### Dynamic JSON Input Example
```dart
import 'package:flutter/material.dart';
import 'package:adaptive_card_renderer/adaptive_card_renderer.dart';

void main() => runApp(const MyApp());

class MyApp extends StatelessWidget {
  const MyApp({Key? key}) : super(key: key);

  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      home: AdaptiveCardScreen(),
    );
  }
}

class AdaptiveCardScreen extends StatefulWidget {
  @override
  _AdaptiveCardScreenState createState() => _AdaptiveCardScreenState();
}

class _AdaptiveCardScreenState extends State<AdaptiveCardScreen> {
  final TextEditingController _controller = TextEditingController();

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(title: const Text('Dynamic Adaptive Card')),
      body: Column(
        children: [
          TextField(
            controller: _controller,
            maxLines: 10,
            decoration: const InputDecoration(
              labelText: 'Enter Adaptive Card JSON',
              border: OutlineInputBorder(),
            ),
          ),
          ElevatedButton(
            onPressed: () {
              setState(() {});
            },
            child: const Text('Render Card'),
          ),
          if (_controller.text.isNotEmpty)
            Expanded(
              child: AdaptiveCardView(
                jsonText: _controller.text,
              ),
            ),
        ],
      ),
    );
  }
}
```

### Contributing
Contributions are welcome! Feel free to open issues or submit pull requests to improve the plugin.

### License
This project is licensed under the MIT License - see the LICENSE file for details.




