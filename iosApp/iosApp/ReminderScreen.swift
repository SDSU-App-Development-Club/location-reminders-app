//
//  ReminderScreen.swift
//  iosApp
//
//  Created by Deanna O on 7/5/24.
//  Copyright © 2024 orgName. All rights reserved.
//



import SwiftUI
import Shared

struct ReminderScreen: View {
    //    @State private var isClicked = false
    @State private var isNewTaskVisible = false
    @State private var slideOffset: CGFloat = UIScreen.main.bounds.height
    @State private var isThereAList = true
    @State private var reminderList: [String] = []
    @State private var list: [String] = [] // Add this line
    
    
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
                    if (!isThereAList) {
                        Text("No new reminders")
                            .font(.system(size: 20, weight: .bold))
                            .foregroundColor(.white)
                        
                        Text("Create a new task to get started")
                            .font(.system(size: 16))
                            .foregroundColor(.white.opacity(0.8))
                    } else {
                        //add parsing to get list
                        //for each thing in the list create a rounded rectangle with the correct attributes then add a spacer above it
                        VStack {
                            ZStack {
                                HStack {
                                    Image(systemName: "circle")
                                        .fontWeight(.semibold)
                                        .font(.title)
                                    
                                    VStack {
                                        ForEach(Array(list.enumerated()), id: \.offset) { index, element in
                                            Text(LocalizedStringKey(element))
                                        }
                                    }
                                }
                            }
                            .background(
                                RoundedRectangle(cornerRadius: 25.0)
                                    .foregroundStyle(Color(hex: "BFFDF2"))
                                    .frame(width: UIScreen.main.bounds.width / 1.1, height:  UIScreen.main.bounds.height / 8 )
                                    .shadow(color: Color.black.opacity(0.3), radius: 3, x: 5, y: 6)
                            )
                        }
                        
                    }
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
        .onAppear {
            fetchAlerts()
        }
        
    }
    
    private func fetchAlerts() {
        let info = UserDefaults.standard.object(forKey: "jwt") as? String ?? ""
        RestAPIAccess().attemptGetAlerts(jwt: info) { response, error in
            if let response = response, response.ok, let value = response.value() {
                DispatchQueue.main.async {
                    self.list = value as? [String] ?? []
                    self.isThereAList = !self.list.isEmpty
                }
            }
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

//went where else is on line 49
//                        let info = UserDefaults.standard.object(forKey: "jwt") as? String ?? String()
//
//                        VStack {
//                            ZStack {
//
//
//                                HStack {
//                                    Image(systemName: "circle")
//                                        .fontWeight(.semibold)
//                                        .font(/*@START_MENU_TOKEN@*/.title/*@END_MENU_TOKEN@*/)
//
//                                    var list: Array<Any>? = nil
//
//                                    RestAPIAccess().attemptGetAlerts(jwt: info) { response, error in
//                                        // assign nullable var to non null if it is not null
//                                        if let response = response {
//                                            // check if result is ok (has value)
//                                            if response.ok {
//                                                let list = response.value()!
//
//
//                                            }
//                                        }
//                                    }
//                                    VStack {
//                                        ForEach(Array(list.enumerated()), id: \.offset) { index, element in
//                                            Text(LocalizedStringKey(element as! String))
//                                        }
//                                    }
//                                }
//                            }
//                            .background(
//                                RoundedRectangle(cornerRadius: /*@START_MENU_TOKEN@*/25.0/*@END_MENU_TOKEN@*/)
//                                    .foregroundStyle(Color(hex: "BFFDF2"))
//                                    .frame(width: UIScreen.main.bounds.width / 1.1, height:  UIScreen.main.bounds.height / 8 )
//                                .shadow(color: Color.black.opacity(0.3), radius: 3, x: 5, y: 6)
//                            )
//                        }
//                        //.background() //add later to help with spacing
