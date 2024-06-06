import SwiftUI
import Shared

struct ContentView: View {
    @State private var showContent = false
    var body: some View {

        // background of the welcome page
       ZStack  {
            Image ("Welcome Page")


            // holds decorative squiggly lines
             VStack {
                Image ("Welcome Page Squiggly 1.png")
                    .offset(y: -110)

                Image ("Welcome Page Squiggly 2.png")
                    .offset(x: -170, y: 30)

                Image ("Welcome Page Squiggly 3.png")
                    .offset(y: 50)

            }


            // holds text and icon welcoming people to the app
            VStack {
                //text needs to be arial
                //fix spacing
                //fix font weight
                Text("Welcome!")
                    .fontWeight(.black)
                    .font(.system(size: 48))
                    .padding()
                    .frame(maxWidth: 300, maxHeight: 75, alignment: .center)
                    .foregroundColor(Color(hex: "#FFFFFF"))

                Image ("PeaceSign")

                Button(action: {}) {
                    Text("Tap anywhere to begin")
                        .frame(maxWidth: 270, alignment: .center)
                        //.fontWeight(.black)
                        .font(.title2)
                        .padding()
                        .foregroundColor(Color(hex: "#FFFFFF"))
                }

            }
        }

    }

//    var body: some View {
//        VStack {
//            Button("Click me!") {
//                withAnimation {
//                    showContent = !showContent
//                }
//            }
//
//            if showContent {
//                VStack(spacing: 16) {
//                    Image(systemName: "swift")
//                        .font(.system(size: 200))
//                        .foregroundColor(.accentColor)
//                    Text("SwiftUI:")
//                }
//                .transition(.move(edge: .top).combined(with: .opacity))
//            }
//        }
//        .frame(maxWidth: .infinity, maxHeight: .infinity, alignment: .top)
//        .padding()
//    }
}

struct ContentView_Previews: PreviewProvider {
    static var previews: some View {
        ContentView()
    }
}
