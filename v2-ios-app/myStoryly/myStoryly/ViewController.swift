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
    
    let STORYLY_INSTANCE_TOKEN = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJhY2NfaWQiOjcxMzcsImFwcF9pZCI6MTE3NDYsImluc19pZCI6MTI1ODJ9.k7IVUbx4b23WTobh7u-ZIAYMdjN1xIDyA8z5WWncWbU"
    
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
        "cover_img" : "https://cdn.pixabay.com/photo/2022/10/15/21/23/cat-7523894_1280.jpg"
    ]
    
    @IBOutlet weak var storylyView: StorylyView!
    
    let openStoryURL = "gssapp://storyly?g=115711&s=1105275&instance=12582&play=sg"
    
    
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
                                   .setIconWidth(width: 160)
                                   .setIconCornerRadius(radius: 12)
                                   .build()
                            )
                           .setProductConfig(
                            config: StorylyProductConfig.Builder()
                                //.setProductFeedCountry(country: "")
                                //.setProductFeedLanguage(language: "")
                                .build()
                           )
                           .setLabels(labels: Set(arrayLiteral: "es", "country_russia", "french", "germany", "country-uk", "country-us","active", "14week" ))
                           .setTestMode(isTest: true)
                           //.setCustomParameter(parameter: "")
                           .build()
        )
        
        self.storylyView.rootViewController = self
        // the class(indicated with self) extends StorylyDelegate
        self.storylyView.delegate = self // Override event functions
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
        print("[storyly] IntegrationViewController:storylyActionClicked - story action_url {\(story.media.actionUrl ?? "")}")
                
        guard let url = URL(string: story.media.actionUrl! ) else {
            return
                }
        UIApplication.shared.openURL(url)
    }
}










/*
 let vc = storyboard?.instantiateViewController(withIdentifier: "second_vc") as! SecondViewController
 present(vc, animated:true)
 */
