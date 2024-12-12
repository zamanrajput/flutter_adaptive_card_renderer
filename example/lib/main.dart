import 'package:adaptive_card_renderer/adaptive_card_renderer.dart';
import 'package:flutter/material.dart';

void main() {
  runApp(const MyApp());
}

class MyApp extends StatefulWidget {
  const MyApp({Key? key}) : super(key: key);

  @override
  State<MyApp> createState() => _MyAppState();
}

class _MyAppState extends State<MyApp> {
  TextEditingController jsonController = TextEditingController();
  String? jsonText;

  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      home: Scaffold(
        appBar: AppBar(title: const Text('Adaptive Card Renderer')),
        body: Column(
          children: [
            // TextField for JSON input
            Expanded(
              child: Padding(
                padding: const EdgeInsets.all(8.0),
                child: TextField(
                  controller: jsonController,
                  maxLines: null,
                  // Allows multiline input
                  decoration: InputDecoration(
                    hintText: "Enter or paste your JSON here",
                    border: OutlineInputBorder(),
                  ),
                ),
              ),
            ),
            // Buttons
            Row(
              mainAxisAlignment: MainAxisAlignment.spaceEvenly,
              children: [
                ElevatedButton(
                  onPressed: () {
                    setState(() {
                      jsonText = jsonController.text;
                    });
                  },
                  child: const Text('Render'),
                ),
                ElevatedButton(
                  onPressed: () {
                    setState(() {
                      jsonController.clear();
                      jsonText = null;
                    });
                  },
                  child: const Text('Clear'),
                ),
              ],
            ),
            // Rendered Adaptive Card
            if (jsonText != null)
              Expanded(
                child: AdaptiveCardView(jsonText: jsonText!),
              ),
          ],
        ),
      ),
    );
  }
}
