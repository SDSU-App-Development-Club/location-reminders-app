import SwiftUI
import Shared

struct ContentView: View {
    @State private var showContent = false
    var body: some View {
        NavigationView {
            NavigationLink {
                // destination view to navigate to
                LogInScreen()
            } label: {
                ZStack {
                    Image("Welcome Page")
                        .resizable()
                        .edgesIgnoringSafeArea(.all)
                    
                    // holds decorative squiggly lines
                    VStack {
                        Image("Welcome Page Squiggly 1")
                            .offset(y: -110)
                        
                        Image("Welcome Page Squiggly 2")
                            .offset(x: -170, y: 30)
                        
                        Image("Welcome Page Squiggly 3")
                            .offset(y: 50)
                    }
                    
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
        }
    }
}

struct ContentView_Previews: PreviewProvider {
    static var previews: some View {
        ContentView()
    }
}

