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
    var body: some View {
        
        ZStack {
            Image("Lists screen")
                .resizable()
                .edgesIgnoringSafeArea(.all)
            
            VStack {
                Image("Lists screen squiggly 1")
                    .offset(y: 160)
                
                Image("Lists screen squiggly 2")
                    .offset(y: -190)
            }
            
            VStack {
                Text("Lists")
                    .fontWeight(.semibold)
                    .font(.system(size: 32))
                    .frame(maxWidth: 340, maxHeight: 75, alignment: .leading)
                    .foregroundColor(Color.white)
                
                Rectangle()
                    .frame(maxWidth: 359, maxHeight: 2, alignment: .center)
                    .foregroundColor(Color.white)
                
                HStack {
                    
                    // reminder button TODO: need to make into actual button
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
                                        .font(.system(size: 24))
                                        .padding([.top, .leading], 10)
                                    Spacer()
                                    Text("2")
                                        .foregroundColor(.black)
                                        .font(.system(size: 24, weight: .bold))
                                        .padding([.top, .trailing], 10)
                                }
                                Spacer()
                                Text("Reminders")
                                    .foregroundColor(.black)
                                    .font(.system(size: 16, weight: .semibold))
                                    .padding(.bottom, 10)
                            }
                            .frame(width: 166, height: 129, alignment: .leading)
                        }
                    }
                    
                    Spacer()
                        .frame(width: 28)
                    
                    // completed button TODO: need to make into actual button
                    ZStack {
                        Rectangle()
                            .frame(width: 166, height: 129, alignment: .trailing)
                            .cornerRadius(15.0)
                            .foregroundColor(Color(hex: "#b0fcf4"))
                        
                        VStack {
                            HStack {
                                Image(systemName: "checkmark")
                                    .foregroundColor(.black)
                                    .font(.system(size: 24))
                                    .padding([.top, .leading], 10)
                                Spacer()
                                // Add any other elements here if needed
                            }
                            Spacer()
                            Text("Completed")
                                .foregroundColor(.black)
                                .font(.system(size: 16, weight: .semibold))
                                .padding(.bottom, 10)
                        }
                        .frame(width: 166, height: 129, alignment: .leading)
                    }
                }
                
                Spacer()
            }
            .frame(maxHeight: 670)
        }
        
    }
}

#Preview {
    ListScreen()
}

