import 'package:bubble/bubble.dart';
import 'package:flutter/material.dart';
import 'package:flutter/services.dart';
import 'package:http/http.dart' as http;
import 'dart:convert';
import 'dart:async';

const rasaIP = '0.0.0.0:5005';
const localIP = 'localhost';
const IP = localIP;
const CHANNEL = "edu.upc.gessi.broadcast.CHANNEL";
const REVERSE_CHANNEL = "edu.upc.gessi.broadcast.REVERSE_CHANNEL";


void main() {
  runApp(MaterialApp(
    home: MyApp(),
    debugShowCheckedModeBanner: false,
  ));

}

class MyApp extends StatefulWidget {
  @override
  _MyAppState createState() => _MyAppState();
}

class _MyAppState extends State<MyApp> {

  final messageInsert = TextEditingController();
  List<Map> messsages = List();

  /**
   * Build channel to register broadcasts
   */
  static const platform = MethodChannel(CHANNEL);
  static const reversePlatform = MethodChannel(REVERSE_CHANNEL);


  Future<void> _registerBroadcasts() async {
    try {
      await platform.invokeMethod('registerBroadcasts');
    } on PlatformException catch (e) {
      print(e);
    }
  }

  @override
  void initState() {
    super.initState();
    //Register broadcasts when building the app
    _registerBroadcasts();

    MethodChannel(REVERSE_CHANNEL).setMethodCallHandler((call) async {
      if (call.method == "fromPlanRouteToCreateEvent") {

        //TODO trigger RASA communication
        print("TODO:\t Trigger RASA communication. Still to be done... :-)");

      }
      return null;
    });
  }

  /**
   * End of broadcast register
   */


  Future<String> sendToRasa (String text) async {
    print("started send to rasa");
    try {
      dynamic response = await http.post(
          Uri.parse('http://' + IP + ':5005' + '/webhooks/rest/webhook'),
          headers: {"Content-Type": "application/json"},
          body: jsonEncode({
            "sender": "me",
            "message": text
          })).timeout(
          const Duration(seconds: 10)
      );
      List<dynamic> r = json.decode(response.body);
      setState(() {
        messsages.insert(0,
            {"data": 0, "message": r[0]["text"]});
      });
      print('Request with statusCode : ${response.statusCode} and body: ${response.body}');
      
    } on TimeoutException catch (e) {}
    return "error";
  }

  @override
  Widget build(BuildContext context) {

    return Scaffold(
      appBar: AppBar(
        title: Text(
          "Mobile Catalogue Chatbot",
        ),
        backgroundColor: Colors.deepOrange,
      ),
      body: Container(
        child: Column(
          children: <Widget>[
            Flexible(
                child: ListView.builder(
                    reverse: true,
                    itemCount: messsages.length,
                    itemBuilder: (context, index) => chat(
                        messsages[index]["message"].toString(),
                        messsages[index]["data"]))),
            Divider(
              height: 5.0,
              color: Colors.deepOrange,
            ),
            Container(
              padding: EdgeInsets.only(left: 15.0, right: 15.0),
              margin: const EdgeInsets.symmetric(horizontal: 8.0),
              child: Row(
                children: <Widget>[
                  Flexible(
                      child: TextField(
                    controller: messageInsert,
                    decoration: InputDecoration.collapsed(
                        hintText: "Send your message",
                        hintStyle: TextStyle(
                            fontWeight: FontWeight.bold, fontSize: 18.0)),
                  )),
                  Container(
                    margin: EdgeInsets.symmetric(horizontal: 4.0),
                    child: IconButton(
                      
                        icon: Icon(
                          
                          Icons.send,
                          size: 30.0,
                          color: Colors.deepOrange,
                        ),
                        onPressed: () {
                          if (messageInsert.text.isEmpty) {
                            print("empty message");
                          } else {
                            setState(() {
                              messsages.insert(0,
                                  {"data": 1, "message": messageInsert.text});
                            });
                            sendToRasa(messageInsert.text);
                            //response(messageInsert.text);
                            messageInsert.clear();
                          }
                        }),
                  )
                ],
              ),
            ),
            SizedBox(
              height: 15.0,
            )
          ],
        ),
      ),
    );
  }

  //for better one i have use the bubble package check out the pubspec.yaml

  Widget chat(String message, int data) {
    return Padding(
      padding: EdgeInsets.all(10.0),
      child: Bubble(
          radius: Radius.circular(15.0),
          color: data == 0 ? Colors.deepOrange : Colors.orangeAccent,
          elevation: 0.0,
          alignment: data == 0 ? Alignment.topLeft : Alignment.topRight,
          nip: data == 0 ? BubbleNip.leftBottom : BubbleNip.rightTop,
          child: Padding(
            padding: EdgeInsets.all(2.0),
            child: Row(
              mainAxisSize: MainAxisSize.min,
              children: <Widget>[
                CircleAvatar(
                  backgroundImage: AssetImage(
                      data == 0 ? "assets/bot.png" : "assets/user.png"),
                ),
                SizedBox(
                  width: 10.0,
                ),
                Flexible(
                    child: Text(
                  message,
                  style: TextStyle(
                      color: Colors.white, fontWeight: FontWeight.bold),
                ))
              ],
            ),
          )),
    );
  }
}
