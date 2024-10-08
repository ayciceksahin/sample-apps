//
//  ViewController.swift
//  myStoryly
//
//  Created by Mehmet Şahin Ayçiçek on 7.11.2022.
//

import UIKit
import Storyly

class ViewController: UIViewController {
    internal var openUrl: URL?
    internal var openUrlPayload: [AnyHashable : Any]?
    
    var cartItems: [STRCartItem] = []
    
    let STORYLY_INSTANCE_TOKEN =             "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJhY2NfaWQiOjcxMzcsImFwcF9pZCI6MTcwNTcsImluc19pZCI6MTg5MTl9.6c3Soj-Lnkh6jch0-h8VGZ1S6w3keS5BRtNQ4nwMAPM"
    
    let userPropertiesData = [
        "name" : "Sahin",
        "image_product_1" : "https://contents.mediadecathlon.com/p2058435/ec1165ccbcdc2abc0ef41b98f8c53e77/p2058435.jpg?format=auto&quality=70&f=650x0",
        "image_product_2" : "https://contents.mediadecathlon.com/p2431723/bea97805143565ce7ab8e0e3680ce3e5/p2431723.jpg?format=auto&quality=70&f=650x0",
        "image_product_3" : "https://contents.mediadecathlon.com/p2427007/6547648c60fc3c1d643fe2968cec84a7/p2427007.jpg?format=auto&quality=70&f=650x0",
        "name_product_1" : "Umbrella High Resistance - Camouflage Grey",
        "name_product_2" : "Rain Cover for Hiking Backpack - 10/20 L",
        "name_product_3" : "Waterproof Mobile Phone Pouch Large IPX8",
        "number_of_items_left_in_cart": "2",
        "username" : "John",
        "gift_amount" : "35%",
        "cover_image" : "https://www.ros.net.pl/GalleryImages/product_photos/1280_720/264055_357671_1280_720_86849.png"
    ]
    
    @IBOutlet weak var storylyView: StorylyView!
    
    let openStoryURL = "freshapp://storyly?g=146402&s=1617771&instance=18919&play=s"
    
    
    func openStory2(url:URL) {
        print("Open Story")
        self.storylyView.openStory(payload: url)
      }
    
    override func viewDidLoad() {
        super.viewDidLoad()
        
        
        self.storylyView.storylyInit = StorylyInit(
            storylyId: STORYLY_INSTANCE_TOKEN,
            config: StorylyConfig.Builder()
                           .setStoryGroupStyling(
                               styling: StorylyStoryGroupStyling.Builder()
                                   .setSize(size: .Large)
                                   .setIconHeight(height: 110)
                                   .setIconWidth(width: 300)
                                   .setIconCornerRadius(radius: 0)
                                   //.setIconThematicImageLabel(label: "dark")
                                   .build()
                            )
                           .setStoryStyling(
                            styling: StorylyStoryStyling.Builder()
                                //.setInteractiveFont(font: UIFont.monospacedDigitSystemFont(ofSize: 10, weight: UIFont.Weight.bold))
                                .build()
                            )
                           .setProductConfig(
                            config: StorylyProductConfig.Builder()
                                //.setProductFeedCountry(country: "")
                                //.setProductFeedLanguage(language: "")
                                .setFallbackAvailability(isEnabled: true)
                                .setCartEnabled(isEnabled: true)
                                .build()
                           )
                           .setLabels(labels: Set(arrayLiteral: "tr", "english", "french", "uk", "en", "homepage","active", "bulgaria_accounts_screen" ))
                           .setTestMode(isTest: true)
                           .setLocale(locale: "tr-TR")
                           .setCustomParameter(parameter: "customid")
                           .build()
        )
        
        self.storylyView.rootViewController = self
        // the class(indicated with self) extends StorylyDelegate
        self.storylyView.delegate = self // Override event functions
        self.storylyView.productDelegate = self
        // Do any additional setup after loading the view.
        self.storylyView.storylyInit.config.userData = userPropertiesData
        //self.storylyView.openStory(payload: URL(string: openStoryURL)!)
        //self.storylyView.openStory(storyGroupId: "115711", play: PlayMode.StoryGroup)
        //self.storylyView.languageCode = "TR"
    }

}


extension ViewController : StorylyDelegate {
    
    func storylyLoaded(_ storylyView: Storyly.StorylyView,
                       storyGroupList: [Storyly.StoryGroup],
                       dataSource: StorylyDataSource) {
        print("StorylyLoaded ===>\(storyGroupList.capacity)")
        if (dataSource == StorylyDataSource.API) {
            print("apii ======== \(StorylyDataSource.API)")
            print("storylyLoadedAPIIIII ==========>\(storyGroupList.capacity)")
                }
    }
    
    func storylyLoadFailed(_ storylyView: Storyly.StorylyView,
                           errorMessage: String) {
        print("StorylyFailed ===>\(errorMessage)")
    }
    
    func storylyActionClicked(_ storylyView: Storyly.StorylyView,
                                rootViewController: UIViewController,
                                story: Storyly.Story) {
        // story.media.actionUrl is important field
        print("[storyly] IntegrationViewController:storylyActionClicked - story action_url {\(story.actionUrl ?? "")}")
        
        //self.storylyView.closeStory(animated: true)
        //self.storylyView.openStory(storyGroupId: "146402", storyId: "1617771", play: PlayMode.StoryGroup)
                
        guard let url = URL(string: story.actionUrl! ) else {
            return
                }
        UIApplication.shared.openURL(url)
    }
    
    func storylyEvent(_ storylyView: Storyly.StorylyView,
                      event: Storyly.StorylyEvent,
                      storyGroup: Storyly.StoryGroup?,
                      story: Storyly.Story?,
                      storyComponent: Storyly.StoryComponent?) {
        print("seen ======== \(story!.name)")
        print("StoryNextClicked ======== \(Storyly.StorylyEvent.StoryNextClicked.stringValue)")
        //print("StoryComponent ======== \(storyComponent?.type.stringValue)")
        print("Event Type ===== \(event.stringValue)")

        if (event == Storyly.StorylyEvent.StoryViewed ){
                        
            if( storyComponent?.type == StoryComponentType.Image){
                
                //print("StoryComponent ======== \(storyComponent?.type.stringValue)")
                print("StoryComponent ======== Stop Music")
            }
            
            else{
                print("StoryComponent ======== Play Music")
            }
            
        }
        
        
        if (event == Storyly.StorylyEvent.StoryPromoCodeCopied ){
            
            print("EVENT --> \(Storyly.StorylyEvent.StoryPromoCodeCopied)")
            
        }
        if (event == Storyly.StorylyEvent.StoryCTAClicked){
            print("EVENT --> \(story?.actionUrl)")
        }
        
        /*if ( event == Storyly.StorylyEvent.StoryViewed) {
            print("Story Type ======== \(story?.media.type.rawValue.description)")
        }*/
        
    }
}


extension ViewController: StorylyProductDelegate {
    
    func storylyHydration(_ storylyView: StorylyView, products: [STRProductInformation]) {
        //* Data class that represents the storyly product information
        }
        
        func storylyEvent(_ storylyView: StorylyView,
                                         event: StorylyEvent) {
            //print("Shopping: \(event.stringValue)")
            if (event == StorylyEvent.StoryCheckoutButtonClicked ) {
                print("Shopping: StoryCheckoutButtonClicked")
            }
            if (event == StorylyEvent.StoryCartButtonClicked ) {
                print("Shopping: StoryCartButtonClicked")
            }
            if (event == StorylyEvent.StoryCartViewClicked ) {
                print("Shopping: StoryCartViewClicked")
            }
            if (event == StorylyEvent.StoryProductSelected ) {
                print("Shopping: StoryProductSelected")
            }
            
        }
         
        func storylyUpdateCartEvent(storylyView: StorylyView, event: StorylyEvent, cart: STRCart?, change: STRCartItem?, onSuccess: ((STRCart?) -> Void)?, onFail: ((STRCartEventResult) -> Void)?) {
            cartItems.removeAll { $0.item.productId == change?.item.productId && $0.item.variants == change?.item.variants }
            
            guard let change = change else { return }
                    
            let totalPrice = Int(truncating: change.item.salesPrice ?? 0) * change.quantity
            let oldTotalPrice = change.item.price * Float(change.quantity)
     
            let item = STRCartItem(item: change.item, quantity: change.quantity, totalPrice: totalPrice as NSNumber, oldTotalPrice: oldTotalPrice as NSNumber)
                
            if change.quantity > 0 {
                cartItems.append(item)
            }
               
            let carTotalPrice = self.cartItems.compactMap { Int(truncating: $0.totalPrice ?? 0) }.reduce(0, +)
            let carOldTotalPrice = self.cartItems.compactMap { Int(truncating: $0.oldTotalPrice ?? 0)  }.reduce(0, +)
            
            DispatchQueue.main.asyncAfter(deadline: .now() + .milliseconds(1_000), execute: {
                onSuccess?(STRCart(items: self.cartItems, totalPrice: Float(carTotalPrice), oldTotalPrice: carOldTotalPrice as NSNumber, currency: "USD"))
            })
            
            if (event == StorylyEvent.StoryProductAdded) {
                print("Shopping: StoryProductAdded")
            }
            if (event == StorylyEvent.StoryProductUpdated) {
                print("Shopping: StoryProductUpdated")
            }
            if (event == StorylyEvent.StoryProductRemoved) {
                print("Shopping: StoryProductRemoved")
            }
        }
    
}










/*
 let vc = storyboard?.instantiateViewController(withIdentifier: "second_vc") as! SecondViewController
 present(vc, animated:true)
 */
