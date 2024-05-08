import 'package:flutter/material.dart';
import 'package:storyly_flutter/storyly_flutter.dart';

import 'scroll_example.dart';

void main() {
  runApp(const MyApp());
}

class MyApp extends StatelessWidget {
  const MyApp({Key? key}) : super(key: key);

  // This widget is the root of your application.
  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      title: 'StorylyDemo',
      theme: ThemeData(
        primarySwatch: Colors.blue,
      ),
      home: const HomePage(title: 'Storyly Demo Page'),
    );
  }
}

class HomePage extends StatefulWidget {
  const HomePage({
    Key? key,
    required this.title,
  }) : super(key: key);

  final String title;

  @override
  _HomePageState createState() => _HomePageState();
}

class _HomePageState extends State<HomePage> {
  static const storylyInstanceToken =
      "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJhY2NfaWQiOjc5MjQsImFwcF9pZCI6MTU0MTYsImluc19pZCI6MTY4ODZ9.djck1SGjzhm5DoevmaBy989qIasJ4mCGIZiops1Lh8U";

  //myApp token - Classical Token
  static const storylyToken =
      "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJhY2NfaWQiOjc5MjQsImFwcF9pZCI6MTU0MTYsImluc19pZCI6MTY4ODZ9.djck1SGjzhm5DoevmaBy989qIasJ4mCGIZiops1Lh8U";

  late StorylyViewController storylyViewController;

  void onStorylyViewCreated(StorylyViewController storylyViewController) {
    this.storylyViewController = storylyViewController;
    //this.storylyViewController.setExternalData(externalData);
    //this.storylyViewController.hydrateProducts(products);
  }

  final userProp = {
    "name": "sahin",
    "product_name": "product_name_url",
    "cta_button": "Buy Now",
    "cover_image": "https://pro-fra-s3-productsassets.rossmann.pl/product_1_large/334525_1280_720.png",
  };

  final externalData = [
    {
      "{user_name}": "user_name_url",
      "{picture}": "https://ichef.bbci.co.uk/news/999/cpsprodpb/15951/production/_117310488_16.jpg",
      "{media_1}": "media_url_1",
      "{product_name}": "product_name_url",
      "{price}": "price_url",
      "{description}": "description_url",
      "{cta_button}": "Buy Now",
      "{cta_url}": "https://www.storyly.io/",
    },
    {
      "{user_name}": "user_name_url",
      "{fav_picture}": "https://ichef.bbci.co.uk/news/999/cpsprodpb/15951/production/_117310488_16.jpg",
      "{media_1}": "media_url_1",
      "{fav_product_name}": "product_name_url",
      "{price}": "price_url",
      "{description}": "description_url",
      "{fav_cta_button}": "Buy Now",
      "{cta_url}": "https://www.storyly.io/",
    }
  ];

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: Text(widget.title),
      ),
      body: Center(
        child: Column(
          children: <Widget>[
            Container(
              margin: const EdgeInsets.only(top: 10),
              height: 120,
              child: StorylyView(
                onStorylyViewCreated: onStorylyViewCreated,
                storylyProductEvent: (event) {
                  if (event == "StoryCartViewClicked") {
                    debugPrint("StoryCartViewClicked -> event: $event");
                  }
                  if (event == "StoryProductSelected") {
                    debugPrint("StoryProductSelected -> event: $event");
                  }
                },
                storylyOnProductHydration: (products){
                  debugPrint("products -> products Ids: $products");
                },
                storylyOnProductCartUpdated: (event, cart, change, responseId) {
                  storylyViewController.approveCartChange(responseId, {
                    "items": [
                      {
                        "item": {
                          "productId": "1",
                          "productGroupId": "1",
                          "title": "High-waist midi skirt",
                          "url": "https://www.storyly.io/",
                          "desc": "High-waist midi skirt made of a viscose blend. Featuring a slit at the hem and invisible zip fastening.",
                          "price": 25.99,
                          "imageUrls": ["https://random-feed-generator.vercel.app/images/clothes/group-1/1-6D7868.jpg","https://random-feed-generator.vercel.app/images/clothes/group-1/2-6D7868.jpg","https://random-feed-generator.vercel.app/images/clothes/group-1/3-6D7868.jpg","https://random-feed-generator.vercel.app/images/clothes/group-1/4-6D7868.jpg"],
                          "variants": [
                            {"name":"color","value":"#6D7868"},
                            {"name":"size","value":"XS"}
                          ]
                        },
                        "totalPrice": 12,
                        "oldTotalPrice": 15,
                        "quantity": 2
                      }
                    ],
                    "totalPrice": 15,

                    "currency": "EUR"
                  });
                },
                androidParam: StorylyParam()
                  ..storylyId = "Storyly_Token"
                //..storyGroupTextTypeface= 'Lobster1.4.otf'
                //..storyInteractiveTextTypeface= 'Lobster1.4.otf'
                  ..storyGroupListHorizontalEdgePadding = 20
                  ..storylyUserProperty = userProp
                  ..isProductCartEnabled = true
                  ..storylyTestMode = true
                  ..storylyLayoutDirection = "rtl"
                  ..storylyCustomParameters = "bfc76a3a23@nana.sa"
                  //..storylySegments = ["ar", "seg00002"]
                  ..storyGroupListHorizontalPaddingBetweenItems = 20,
                iosParam: StorylyParam()
                  ..storylyId = "Storyly_Token"
                  ..storyGroupListHorizontalEdgePadding = 20
                  ..isProductCartEnabled = true
                  ..isProductFallbackEnabled = true
                  ..storylyLayoutDirection = "rtl"
                  ..storylyUserProperty = userProp
                  ..storyGroupListHorizontalPaddingBetweenItems = 20,
                storylyLoaded: (storyGroups, dataSource) {
                  debugPrint(
                    "storylyLoaded -> storyGroups: ${storyGroups.length}",
                  );
                  debugPrint("storylyLoaded -> dataSource: $dataSource");
                },
                storylyLoadFailed: (errorMessage) =>
                    debugPrint("storylyLoadFailed"),
                storylyActionClicked: (story) {
                  debugPrint("storylyActionClicked -> ${story.media.actionUrl}");
                },
                storylyEvent: (event, storyGroup, story, storyComponent) {
                  debugPrint("storylyEvent -> event: $event");
                  debugPrint(
                    "storylyEvent -> storyGroup: ${storyGroup?.title}",
                  );
                  debugPrint("storylyEvent -> story: ${story?.title}");
                  debugPrint("storylyEvent -> storyComponent: $storyComponent");
                },
                storylyStoryShown: () => debugPrint("storylyStoryShown"),
                storylyStoryDismissed: () => debugPrint(
                  "storylyStoryDismissed",
                ),
                storylyUserInteracted: (storyGroup, story, storyComponent) {
                  debugPrint(
                    "userInteracted -> storyGroup: ${storyGroup.title}",
                  );
                  debugPrint("userInteracted -> story: ${story.title}");
                  debugPrint(
                    "userInteracted -> storyComponent: $storyComponent",
                  );
                },
              ),
            ),
            TextButton(
              onPressed: () {
                Navigator.push(
                  context,
                  MaterialPageRoute(
                    builder: (context) => const ScrollExample(),
                  ),
                );
              },
              child: const Text("Scroll Example"),
            ),
          ],
        ),
      ),
    );
  }
}