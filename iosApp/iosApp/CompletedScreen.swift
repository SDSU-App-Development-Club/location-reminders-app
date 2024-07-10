//
//  CompletedScreen.swift
//  iosApp
//
//  Created by Deanna O on 7/9/24.
//  Copyright © 2024 orgName. All rights reserved.
//

import SwiftUI

struct CompletedScreen: View {
    var body: some View {
        
        ZStack {
            
            Image("Reminder Screen")
                .resizable()
                .edgesIgnoringSafeArea(.all)
            
            Image("Reminder Squiggly 1")
                .offset(y: 100)
            
            VStack {
                VStack(alignment: .leading, spacing: 4) {
                    Text("Completed")
                        .fontWeight(.semibold)
                        .font(.system(size: 32))
                        .foregroundColor(Color.white)
                    
                    Rectangle()
                        .frame(width: 340, height: 2)
                        .foregroundColor(Color.white)
                }
                .frame(maxWidth: .infinity, alignment: .leading)
                .padding(.leading, 20) // Adjust padding to match the spacing in the screenshot
                
                Spacer()
                    .frame(height: 40) // Adjust the spacing below the title and underline
                
                Spacer()
            }
            .frame(maxHeight: 670)
        }
    }
}

#Preview {
    CompletedScreen()
}
