////
////  ListScreen.swift
////  iosApp
////
////  Created by Deanna O on 7/4/24.
////  Copyright © 2024 orgName. All rights reserved.
///
///
///
////


//TODO: fix spacing for lists title and 
import SwiftUI

struct ListScreen: View {
    @State private var reminderScreen = false
    @State private var completedScreen = false
    
    var body: some View {
        
        NavigationStack {
            ZStack {
                
                BackgroundView()
                
                BackgroundDetailsView()
                
                VStack {
                    ListTitle()
                    
                    Spacer()
                        .frame(height: 40)
                    
                    //three buttons: reminders, completed, & new tasks
                    HStack {
                        // reminder button
                        ReminderButton()
                        
                        Spacer()
                            .frame(width: 28)
                        
                        // completed button
                        CompleteButton()
                    }
                    
                    Spacer()
                }
                .frame(maxHeight: 670)
                
                
                //animation begins here
//                NewTask()
//                    .offset(y: UIScreen.main.bounds.height * 0.25) //y = 200 is at perfect spot
//                
            }
        }
    }
}

#Preview {
    ListScreen()
}


struct CompleteButton: View {
    var body: some View {
        NavigationLink{
            CompletedScreen()
        } label: {
            ZStack {
                Rectangle()
                    .cornerRadius(15.0)
                    .foregroundColor(Color(hex: "#b0fcf4"))
                    .frame(width: 166, height: 129, alignment: .trailing)
                
                VStack {
                    HStack {
                        Image(systemName: "checkmark")
                            .foregroundColor(.black)
                            .font(.system(size: 40))
                            .padding([.top, .leading], 10)
                        Spacer()
                        // Add any other elements here if needed
                    }
                    Spacer()
                    Text("Completed")
                        .foregroundColor(.black)
                        .font(.system(size: 28, weight: .semibold))
                        .padding(.bottom, 10)
                }
                .frame(width: 166, height: 129, alignment: .leading)
            }
            
        }
    }
}

struct ReminderButton: View {
    var body: some View {
        NavigationLink{
            ReminderScreen()
        } label: {
            ZStack {
                Rectangle()
                    .frame(width: 166, height: 129, alignment: .trailing)
                    .cornerRadius(15.0)
                    .foregroundColor(Color(hex: "#b0fcf4"))
                
                VStack {
                    HStack {
                        Image(systemName: "mappin.and.ellipse")
                            .foregroundColor(.black)
                            .font(.system(size: 36))
                            .padding([.top, .leading], 10)
                        Spacer()
                        Text("2")
                            .foregroundColor(.black)
                            .font(.system(size: 42, weight: .bold))
                            .padding([.top, .trailing], 10)
                    }
                    Spacer()
                    Text("Reminders")
                        .foregroundColor(.black)
                        .font(.system(size: 28, weight: .semibold))
                        .padding(.bottom, 10)
                }
                .frame(width: 166, height: 129, alignment: .leading)
            }
        }
    }
}

struct BackgroundView: View {
    var body: some View {
        Image("Lists screen")
            .resizable()
            .edgesIgnoringSafeArea(.all)
    }
}

struct BackgroundDetailsView: View {
    var body: some View {
        VStack {
            Image("Lists screen squiggly 1")
                .offset(y: 160)
            
            Image("Lists screen squiggly 2")
                .offset(y: -190)
        }
    }
}

struct ListTitle: View {
    var body: some View {
        VStack(alignment: .leading, spacing: 4) {
            Text("Lists")
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
