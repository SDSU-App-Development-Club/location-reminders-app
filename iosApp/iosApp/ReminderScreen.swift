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
        ZStack{
            
            Image("Reminder Screen")
                .resizable()
                .edgesIgnoringSafeArea(.all)
            
            Image("Reminder Squiggly 1")
                .offset(y: 100)
            
            VStack (){
                Text("Reminders")
                    .fontWeight(.semibold)
                    .font(.system(size: 32))
                    .frame(maxWidth: 340, maxHeight: 75, alignment: .leading)
                    .foregroundColor(Color.white)
                
                Rectangle()
                    .frame(maxWidth: 359, maxHeight: 2, alignment: .center)
                    .foregroundColor(Color.white)
                
               
                
            }
            .frame(maxHeight: 670)
            
            Spacer()
            
        }
        
    }
}

#Preview {
    ReminderScreen()
}
