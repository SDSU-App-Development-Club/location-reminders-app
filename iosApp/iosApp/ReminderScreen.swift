//
//  ReminderScreen.swift
//  iosApp
//
//  Created by Deanna O on 7/5/24.
//  Copyright © 2024 orgName. All rights reserved.
//

import SwiftUI

struct ReminderScreen: View {
    var body: some View {
        ZStack {
            
            Image("Reminder Screen")
                .resizable()
                .edgesIgnoringSafeArea(.all)
            
            Image("Reminder Squiggly 1")
                .offset(y: 100)
            
            VStack {
                VStack(alignment: .leading, spacing: 4) {
                    Text("Reminders")
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
                
                // New task button
                Button(action: {
                    // Action to pull up the bottom screen to add a new task
                }) {
                    ZStack {
                        Rectangle()
                            .foregroundColor(Color.white)
                            .frame(maxWidth: 128, maxHeight: 39, alignment: .leading)
                            .cornerRadius(17.0)
                        
                        Text("+ New Task")
                            .fontWeight(.semibold)
                            .font(.system(size: 16))
                            .foregroundColor(Color.black)
                    }
                }
                
                Spacer()
            }
            .frame(maxHeight: 670)
        }
    }
}

#Preview {
    ReminderScreen()
}
