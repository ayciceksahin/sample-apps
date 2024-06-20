/**
 * Sample React Native App
 *
 * adapted from App.js generated by the following command:
 *
 * react-native init example
 *
 * https://github.com/facebook/react-native
 */

import React, { Component } from 'react';
import { View, Button, Image, Text, PixelRatio, Linking, Platform, SafeAreaView } from 'react-native';
import { Storyly } from 'storyly-react-native';

const PIN_ICON = require('./assets/pin_icon.png'); 
const HOVER_IMG = require('./assets/watch.jpg'); 


const STORYLY_TOKEN = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJhY2NfaWQiOjc2MCwiYXBwX2lkIjo0MDUsImluc19pZCI6NDA0fQ.1AkqOy_lsiownTBNhVOUKc91uc9fDcAxfQZtpm3nj40"

const convertToNative = (size: number) => {
   return Platform.OS === 'android' ? PixelRatio.getPixelSizeForLayoutSize(size) : size
}


const CustomPortraitView = ({ storyGroup }) => {
    return (
        <>
            {(storyGroup ? (
                <View style={{ width: 100, height: 178 }}>
                    <Image style={{
                        width: "100%",
                        height: "100%",
                        borderRadius: 8 }}
                        source={{ uri: storyGroup.iconUrl}} />
                    <View style={{ width: 100, height: 178, borderRadius: 8, position: 'absolute', backgroundColor:  storyGroup.seen ? "#16ad055f" : "#1905ad5f" }}>
                        <View style={{ flexDirection:'column', width: 90, marginLeft: 5, height: "20%", alignItems: 'center', justifyContent: 'flex-start'}}>
                        { storyGroup.pinned ?
                            <Image style={{ width: 20, height: 20, marginTop:10, marginBottom: 10, borderRadius: 10 }} source={ PIN_ICON } /> 
                             : 
                             <View style={{  width: 20, height: 1, marginTop:5, marginBottom: 10 }} />}
                        </View> 

                        <View style={{ flexDirection:'column', width: 90, marginLeft: 5, height: "80%", alignItems: 'flex-end', justifyContent: 'flex-end'}}>
                        <View style={{ width: "100%", marginLeft: 5, height: "70%", alignItems: 'center', justifyContent: 'flex-end'}}>
                              <Image style={{ width: 60, height: 60, marginTop:5, borderRadius: 30 }} source={ HOVER_IMG } />
                          </View>
                          <View style={{ width: "100%", marginLeft: 5, height: "30%", alignItems: 'center', justifyContent: 'flex-end'}}>
                              <Text style={{ marginBottom: 5,flexWrap: 'wrap', width: "90%", textAlign: 'center', fontWeight: 'bold', fontSize: 12, color: "white" }}>{storyGroup.title}</Text>
                          </View>
                        </View> 
                    </View>
                </View>
            ) : (
                <View style={{width: "100%", height: "100%",  borderRadius: 8 }}></View>
            ))}
        </>
    )
}

var productOnCarts: any[] = [];

export default class App extends Component {

    componentDidMount() {
        Linking.getInitialURL().then((url) => {
          if (url) {
            console.log('Initial url is: ' + url);
            this.storyly.openStory(url)
          }
        }).catch(err => console.error('An error occurred', err));
      }

    storyly: any;
    render() {
        return (
            <SafeAreaView>
                 <Storyly
                    ref={ref =>
                         { this.storyly = ref 
                            if (this.storyly != null) {
                                this.storyly.hydrateProducts([
                                     {
                                        "productId": "58",
                                        "productGroupId": "6",
                                        "title": "High-waist midi skirt",
                                        "url": "https://www.storyly.io/",
                                        "desc": "High-waist midi skirt made of a viscose blend. Featuring a slit at the hem and invisible zip fastening.",
                                        "price": 25.99,
                                                        
                                         "currency": "USD",
                                         "imageUrls": ["https://random-feed-generator.vercel.app/images/clothes/group-1/1-6D7868.jpg","https://random-feed-generator.vercel.app/images/clothes/group-1/2-6D7868.jpg","https://random-feed-generator.vercel.app/images/clothes/group-1/3-6D7868.jpg","https://random-feed-generator.vercel.app/images/clothes/group-1/4-6D7868.jpg"],
                                         "variants": [
                                                       {"name":"color","value":"#6D7868"},
                                                       {"name":"size","value":"XS"}
                                                     ]
                                      },
                                      {
                                         "productId": "58",
                                         "productGroupId": "1",
                                         "title": "High-waist midi skirt",
                                         "url": "https://www.storyly.io/",
                                         "desc": "High-waist midi skirt made of a viscose blend. Featuring a slit at the hem and invisible zip fastening.",
                                         "price": 25.99,
                                                        
                                          "currency": "USD",               
                                          "imageUrls": ["https://random-feed-generator.vercel.app/images/clothes/group-1/1-6D7868.jpg","https://random-feed-generator.vercel.app/images/clothes/group-1/2-6D7868.jpg","https://random-feed-generator.vercel.app/images/clothes/group-1/3-6D7868.jpg","https://random-feed-generator.vercel.app/images/clothes/group-1/4-6D7868.jpg"],
                                          "variants": [
                                                        {"name":"color","value":"#6D7868"},
                                                        {"name":"size","value":"M"}
                                                       ]
                                        },])
                            }
                        }}
                    style={{ width: '100%', height: convertToNative(178), marginTop: 10, marginBottom: 10}}
                    storyGroupViewFactory={{
                        width: convertToNative(100),
                        height: convertToNative(178),
                        customView: CustomPortraitView
                    }}
                    storylyId={"eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJhY2NfaWQiOjcxMzcsImFwcF9pZCI6MTE3NDYsImluc19pZCI6MTI1ODJ9.k7IVUbx4b23WTobh7u-ZIAYMdjN1xIDyA8z5WWncWbU"}
                    //storyGroupSize="small"
                    //storyGroupTextTypeface={"Lobster1.4.otf"}
                   storylySegments={["en", "french", "english"]} 
                    customParameter='97386670'
                    storylyTestMode = {true}
                    /*storylyUserProperty={{
                        "dugout_amt_won" : "123",
                        "dugout_cj" : "123",
                        "dugout_perc_player_in_dt" : "123",
                        "dugout_roundplyd" : "123",
                    }}*/
                    storyFallbackIsEnabled = {true}
                    storyCartIsEnabled = {true}
                    onCartUpdate={ eventPayload => {
                        if (eventPayload.event == "StoryProductAdded" ) {
                            console.log(`[Storyly] default - StoryProductAdded ${JSON.stringify(eventPayload.event)} `)
                            //This event sent when a product is added.
                            productOnCarts = [...productOnCarts, {
                                "item": {
                                    "productId": eventPayload.change.item.productId,
                                    "productGroupId": eventPayload.change.item.productGroupId,
                                    "title": eventPayload.change.item.title,
                                    "url": "eventPayload.change.item",
                                    "desc": eventPayload.change.item.desc,
                                    "price":eventPayload.change.item.price,
                                    "imageUrls": eventPayload.change.item.imageUrls,
                                    "variants": eventPayload.change.item.variants,
                                    "salesPrice": eventPayload.change.item.salesPrice,
                                },
                                "totalPrice": eventPayload.change.item.price*eventPayload.change.quantity,
                                "oldTotalPrice": eventPayload.change.item.price*eventPayload.change.quantity,
                                "quantity": eventPayload.change.quantity
                            }]
                        }
                        if (eventPayload.event == "StoryProductUpdated" ) {
                            console.log(`[Storyly] default - StoryProductUpdated ${JSON.stringify(eventPayload.event)} `)
                            //This event sent when a product is updated.
                            // Update your cart
                        }
                        if (eventPayload.event == "StoryProductRemoved" ) {
                            console.log(`[Storyly] default - StoryProductRemoved ${JSON.stringify(eventPayload.event)} `)
                            //This event sent when a product is removed.
                            // Remove items from your cart
                        }  
                        
                    this.storyly.approveCartChange(eventPayload.responseId, {
                        "items": productOnCarts,
                        "totalPrice": eventPayload.change.item.price*eventPayload.change.quantity,
                        "oldTotalPrice": eventPayload.change.item.price*eventPayload.change.quantity,
                        "currency": "USD"
                       });
                    }}
                    onLoad={loadEvent => {
                        console.log(`[Storyly] default - onLoad`);
                    }}
                    onFail={errorMessage => {
                        console.log(`[Storyly] default - onFail ${errorMessage}`);
                    }}
                     onPress={event => { 
                        //this.storyly.closeStory()
                        Linking.openURL(event.story.actionUrl)
                        console.log(`[Storyly] default - onPress`, event.story.actionUrl);
                     }}
                    /*onEvent={event => {{
                        "event" == "StoryCompleted" ? 
                            console.log( `[Storyly] default - StoryCompleted`)
                        :
                            console.log( `[Storyly] default - not Complated`)
                        }
                    }}*/
                    onEvent={eventPayload => {
                       
                        console.log(`[Storyly] default - onEvent ${JSON.stringify(eventPayload.event)} StoryID:${JSON.stringify(eventPayload.story.id)} `)
                        console.log(`[Storyly] StoryType:${eventPayload.storyComponent} `)

                        
                    }}
                    /*onEvent={event => {
                        console.log(`[Storyly] StoryType:${event.storyComponent?.type} `)          
                    }}*/

                    onStoryOpen={() => {
                        console.log("[Storyly] default - onStoryOpen");
                    }}
                    onStoryClose={() => {
                        console.log("[Storyly] default - onStoryClose");
                    }}
                    onUserInteracted={interactionEvent => {
                        console.log(`[Storyly] default - onStoryUserInteracted:${JSON.stringify(interactionEvent.storyComponent)}`);
                    }}/>
                <Button
                    onPress={() => { this.storyly.refresh(); }}
                    title="Refresh"
                />
            </SafeAreaView>
        );
    }
}