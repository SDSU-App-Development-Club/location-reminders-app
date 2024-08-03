import SwiftUI
import GoogleMaps

@main
struct iOSApp: App {
    
    
    @UIApplicationDelegateAdaptor(AppDelegate.self) private var appDelegate
    
//    init() {
//            GMSServices.provideAPIKey("AIzaSyD03IWaMTOrmnwfFzXptGz2Rhrl2NoQ4XM")
//    }

    
	var body: some Scene {
		WindowGroup {
			ContentView()
		}

	}   
}


