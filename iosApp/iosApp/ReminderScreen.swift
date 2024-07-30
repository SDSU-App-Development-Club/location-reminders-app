//
//  ReminderScreen.swift
//  iosApp
//
//  Created by Deanna O on 7/5/24.
//  Copyright © 2024 orgName. All rights reserved.
//

import SwiftUI

struct ReminderScreen: View {
//    @State private var isClicked = false
    @State private var isNewTaskVisible = false
    @State private var slideOffset: CGFloat = UIScreen.main.bounds.height
    
    var body: some View {
        ZStack {
            
            Image("Reminder Screen")
                .resizable()
                .edgesIgnoringSafeArea(.all)
            
            Image("Reminder Squiggly 1")
                .offset(y: 100)
            
            VStack {
                RemindersTitle() // TODO: Adjust padding to match the spacing in the screenshot
                    .padding(.top, 30)
                
                Spacer()
                    .frame(height: 50)
                
                VStack(spacing: 10) {
                    Text("No new reminders")
                        .font(.system(size: 20, weight: .bold))
                        .foregroundColor(.white)
                    
                    Text("Create a new task to get started")
                        .font(.system(size: 16))
                        .foregroundColor(.white.opacity(0.8))
                }
                
                Spacer()
                
            }
            //.padding(.top, 20)
            .offset(y: isNewTaskVisible ? -slideOffset : 0)
            
            
            NewTask(isVisible: $isNewTaskVisible)
            .offset(y: isNewTaskVisible ? 0 : slideOffset)
            .animation(.easeInOut(duration: 0.5), value: isNewTaskVisible)
            
            
            ZStack {
                
                Circle()
                    .foregroundStyle(.white)
                    .frame(width: 90)
                    .offset(x: UIScreen.main.bounds.width / 2.5, y: UIScreen.main.bounds.height / 2.55)
                    .shadow(color: Color.black.opacity(0.5), radius: 10)
                
                
                RoundedRectangle(cornerRadius: 30)
                    .foregroundColor(.white)
                    .offset(y: UIScreen.main.bounds.height / 1.2)
                    .shadow(color: Color.black.opacity(0.5), radius: 10)
                       
                ZStack {
                    Circle()
                        .foregroundStyle(.white)
                        .frame(width: 90)
                    
                    
                    Circle()
                        .foregroundStyle(Color(hex: "FF009a88"))
                        .frame(width: 70)
                    
                    Image(systemName: "plus")
                        .foregroundStyle(.white)
                        .font(.system(size: 40))
                        .fontWeight(.bold)
                    
                }
                .offset(x: UIScreen.main.bounds.width / 2.5, y: UIScreen.main.bounds.height / 2.55)
                .onTapGesture {
                    withAnimation(.easeInOut(duration: 0.5)){
                        isNewTaskVisible.toggle()
                    }
                }

                
            }
            .opacity(isNewTaskVisible ? 0 : 1)
   
        }
        
    }
}

#Preview {
    ReminderScreen()
}

struct RemindersTitle: View {
    var body: some View {
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
        .padding(.leading, 20)
    }
}
