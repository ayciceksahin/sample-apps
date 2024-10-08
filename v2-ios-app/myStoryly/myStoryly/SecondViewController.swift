//
//  SecondViewController.swift
//  myStoryly
//
//  Created by Mehmet Şahin Ayçiçek on 26.12.2022.
//

import UIKit
import Storyly

class SecondViewController: UIViewController {
    
    let labels = Set(arrayLiteral: "es", "turkey", "french", "germany", "country-uk", "country-us","active", "de" )
    
    @IBOutlet weak var storylyView: StorylyView!
    
    override func viewDidLoad() {
        super.viewDidLoad()
        self.storylyView.storylyInit = StorylyInit(storylyId: "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJhY2NfaWQiOjkzNDcsImFwcF9pZCI6MTQyMTMsImluc19pZCI6MTU0ODJ9.lxOQ1X7HzhMWP4ulh5UyMpUhhC0CpE4er2wEwpYWFGo")
        self.storylyView.rootViewController = self
        self.storylyView.delegate = self
        // Do any additional setup after loading the view.
    }
    
    @IBAction func openStoryButton() {
        //self.storylyView.storylyInit.config.labels = labels
        self.storylyView.storylyInit =  StorylyInit(storylyId: "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJhY2NfaWQiOjcxMzcsImFwcF9pZCI6MTE3NDYsImluc19pZCI6MTI1ODJ9.k7IVUbx4b23WTobh7u-ZIAYMdjN1xIDyA8z5WWncWbU")
        /*DispatchQueue.main.asyncAfter(deadline: .now() + 2.0) {
            print("3 second delay")
         //self.storylyView.openStory(storyGroupId: "51351", play: PlayMode.StoryGroup)
         }*/
    }

}

extension SecondViewController : StorylyDelegate {
    
    func storylyLoaded(_ storylyView: Storyly.StorylyView,
                       storyGroupList: [Storyly.StoryGroup],
                       dataSource: StorylyDataSource) {
        //print("StorylyLoaded ===>\(storyGroupList.capacity)")
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
                
        guard let url = URL(string: story.actionUrl! ) else {
            return
                }
        UIApplication.shared.openURL(url)
        
        //self.storylyView.pauseStory()
    }
}
