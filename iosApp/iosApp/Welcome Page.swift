//
//  Welcome Page.swift
//  iosApp
//
//  Created by Deanna O on 6/4/24.
//  Copyright © 2024 orgName. All rights reserved.
//

import SwiftUI


struct WelcomePage: View {
    var body: some View {
        // background of the welcome page
        ZStack {
            Image ("Welcome Page")
           
            
            // holds decorative squiggly lines
            VStack {
                Image ("Squiggly3")
                    .offset(y: -110)
                
                Image ("Squiggly1")
                    .offset(x: -170, y: 30)
                
                Image ("Squiggly2")
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
                
                Button(action: {}){
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

    
}




#Preview {
    WelcomePage()
}

