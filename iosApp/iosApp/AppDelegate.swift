//
//  AppDelegate.swift
//  iosApp
//
//  Created by Deanna O on 8/1/24.
//  Copyright © 2024 orgName. All rights reserved.
//

import Foundation
import SwiftUI
import GoogleMaps
//import GooglePlaces

class AppDelegate: NSObject, UIApplicationDelegate {
    
    func application(_ application: UIApplication, didFinishLaunchingWithOptions launchOptions: [UIApplication.LaunchOptionsKey : Any]? = nil) -> Bool {
        
        GMSServices.provideAPIKey("AIzaSyD03IWaMTOrmnwfFzXptGz2Rhrl2NoQ4XM")
        //GMSPlacesClient.provideAPIKey("AIzaSyD03IWaMTOrmnwfFzXptGz2Rhrl2NoQ4XM")
        
        return true
    }
    
    //YOU CAN ADD OTHER UIApplicationDelegate here
    
}
