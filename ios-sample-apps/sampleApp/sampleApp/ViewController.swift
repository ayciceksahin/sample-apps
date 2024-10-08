//
//  ViewController.swift
//  sampleApp
//
//  Created by Mehmet Şahin Ayçiçek on 27.07.2022.
//

import UIKit
import Storyly


class ViewController: UIViewController {
    let storylyViewProgrammatic = StorylyView()
    let STORYLY_INSTANCE_TOKEN = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJhY2NfaWQiOjEyMDQ5LCJhcHBfaWQiOjE4MjI0LCJpbnNfaWQiOjIwNTIwfQ.vvj09IAC4iNeO3RI6JHO3qg7QRLAhVabB7faPqEk76s"
    
    
    
    let externalData = [[
        "{name}": "Sahin",
        "{user_id}": "sahin"
    ],
    [
        "{name}": "Alper",
        "{user_id}": "alper"
    ]]
    
    let userPropertiesData = [
          "user_id": "John",
          "country_count": "7",
          "mile_count": "2751",
          "cover_image": "https://w7.pngwing.com/pngs/114/579/png-transparent-pink-cross-stroke-ink-brush-pen-red-ink-brush-ink-leave-the-material-text.png"
        ]
    
    let storylyFlag = true
    
    let font = UIFont(name:"Roboto-BoldItalic", size: 32)
    
    private var lastStorylyView: StorylyView?

    @IBOutlet weak var storlyView: StorylyView!
       
    override func viewDidLoad() {
        super.viewDidLoad()
        let openStoryURL = "cianurl://cian.ru/storyly?g=38178&instance=9386&play=sg"
        let lang =  Locale.autoupdatingCurrent.languageCode
        print("lang ======== \(String(describing: lang!))")
        if (storylyFlag == true) {
            self.storlyView.storylyInit = StorylyInit(
                storylyId: STORYLY_INSTANCE_TOKEN,
                segmentation:StorylySegmentation(segments: Set(arrayLiteral: lang!, "диплинк", "french", "germany", "staging", "gender-f","active", "de")),
                customParameter: "2"
                //userData: userPropertiesData
            )
        }
        // Do any additional setup after loading the view.
        self.storlyView.rootViewController = self
        self.storlyView.delegate = self
        
        //self.storlyView.setExternalData(externalData: externalData)
        self.storlyView.storylyInit.userData = userPropertiesData
        self.storlyView.storyInteractiveFont = UIFont(name: "Thonburi-Light", size: 10)!
        self.storlyView.openStory(payload: URL(string: openStoryURL)!)
        //self.storlyView.openStory(storyGroupId: "51360", storyId: "418174", play: .Story)
        if (storylyFlag == false){
            self.storlyView.isHidden = true
        }
        //self.storlyView.storyGroupSize = "custom"
        //self.storlyView.storyGroupIconStyling = StoryGroupIconStyling(height: 100, width: 150, cornerRadius: 20)
    }


}


extension ViewController: StorylyDelegate {
    func storylyActionClicked(_ storylyView: StorylyView, rootViewController: UIViewController, story: Story) {
        
        print("[storyly] IntegrationViewController:storylyActionClicked - story action_url {\(story.media.actionUrl ?? "")}")
                
        guard let url = URL(string: story.media.actionUrl! ) else {
            return
                }
        UIApplication.shared.openURL(url)
        
        
        
        /*self.lastStorylyView = storylyView
        self.lastStorylyView?.pause()
        let  bottomsheet = RedBottomSheet()
        
        if #available(iOS 15.0, *) {
            if let sheet = bottomsheet.sheetPresentationController {
                sheet.detents = [.medium()]
            }
        }
        topMostController()?.present(bottomsheet, animated: true,completion: nil)
        
        bottomsheet.onDismiss = {
            self.lastStorylyView?.resume()
        }*/
    }
    func storylyEvent(_ storylyView: Storyly.StorylyView,
                      event: Storyly.StorylyEvent,
                      storyGroup: Storyly.StoryGroup?,
                      story: Storyly.Story?,
                      storyComponent: Storyly.StoryComponent?) {
        print("seen ======== \(story!.seen)")
        print("StoryNextClicked ======== \(Storyly.StorylyEvent.StoryNextClicked.stringValue)")
        //print("StoryComponent ======== \(storyComponent?.type.stringValue)")
        print("Event Type ===== \(event.stringValue)")

        if (event == Storyly.StorylyEvent.StoryViewed ){
                        
            if( story?.media.type == StoryType.Video){
                
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
            print("EVENT --> \(story?.media.actionUrl)")
        }
        
        /*if ( event == Storyly.StorylyEvent.StoryViewed) {
            print("Story Type ======== \(story?.media.type.rawValue.description)")
        }*/
        
    }
    
    func storylyStoryDismissed(_ storylyView: Storyly.StorylyView) {
        print("StoryComponent ======== Play Music")
    }
    
    func storylyLoaded(_ storylyView: StorylyView, storyGroupList: [StoryGroup], dataSource: StorylyDataSource) {
        print("storylyLoaded ==========>\(storyGroupList.capacity)-\(dataSource.rawValue.description)")
        
        if (dataSource == StorylyDataSource.API) {
            print("apii ======== \(StorylyDataSource.API)")
            print("storylyLoadedAPIIIII ==========>\(storyGroupList.count)")
                }
        
        
        /*if (dataSource == StorylyDataSource.API) {
            if(storyGroupList.count==0){
                self.storlyView.isHidden = true
            }
            else {
                self.storlyView.isHidden = false
            }
        }*/
        
        
        /*DispatchQueue.main.asyncAfter(deadline: .now() + 5.0) {
            print("5 saniye delayyyy")
            self.storlyView.openStory(storyGroupId: "51360", storyId: "418174", play: .Story)
         }*/
        
    }
    
    func storylyLoadFailed(_ storylyView: StorylyView, errorMessage: String) {
            print("[storyly] IntegrationViewController:storylyLoadFailed - error {\(errorMessage)}")
        }
    
    func storylyUserInteracted(_ storylyView: Storyly.StorylyView,
                               storyGroup: Storyly.StoryGroup,
                               story: Storyly.Story,
                               storyComponent: Storyly.StoryComponent) {
        
        print("storyComponent ======== \(storyComponent.type)")
        
        let promoCodeComponent = storyComponent as? StoryPromoCodeComponent
             
            print("storyComponent ======== \(promoCodeComponent?.text)")

    }
}
 
func topMostController() -> UIViewController? {
    guard let window = UIApplication.shared.keyWindow, let rootViewController = window.rootViewController else {
        return nil
    }
    var topController = rootViewController
    
    while let newTopController = topController.presentedViewController {
        topController = newTopController
    }
    return topController
}



    




