import SwiftUI
import Shared

struct ContentView: View {
    @State private var nextPage = false
    
    var body: some View {
        Group {
            if nextPage {
                LogInScreen()
            } else {
                Button(action: { nextPage = true }){ //if the person touches the screen go to the login page
                    ZStack {
                        Background()
                        
                        // holds decorative squiggly lines
                        BackgroundDetails()
                        
                        WelcomePeaceSignandText()
                    }
                }
            }
        }
    }
}

struct ContentView_Previews: PreviewProvider {
    static var previews: some View {
        ContentView()
    }
}

//background details extracted subview
struct ExtractedView: View {
    var body: some View {
        VStack {
            Image("Welcome Page Squiggly 1")
                .offset(y: -180)
            
            Image("Welcome Page Squiggly 2")
                .offset(x: -180, y: 30)
            
            Image("Welcome Page Squiggly 3")
                .offset(y: 100)
        }
    }
}

struct Background: View {
    var body: some View {
        Image("Welcome Page")
            .resizable()
            .edgesIgnoringSafeArea(.all)
    }
}

struct WelcomePeaceSignandText: View {
    var body: some View {
        VStack {
            
            Spacer()
            
            Text("Welcome!")
                .fontWeight(.black)
                .font(.system(size: 48))
                .frame(maxWidth: 300, maxHeight: 75, alignment: .center)
                .foregroundColor(Color(hex: "#FFFFFF"))
            
            Image("PeaceSign")
                .padding()
            
            
            Text("Tap anywhere to begin")
                .frame(maxWidth: 300, alignment: .center)
                .foregroundColor(Color(hex: "#FFFFFF"))
                .font(.title2)
            
            Spacer()
        }
    }
}
