//
//  NewTask.swift
//  iosApp
//
//  Created by Deanna O on 7/16/24.
//  Copyright © 2024 orgName. All rights reserved.
//

import SwiftUI

struct NewTask: View {
    var body: some View {
        ZStack {
            RoundedRectangle(cornerRadius: 25)
                .fill(Color.white)
                .shadow(color: Color.black.opacity(0.5), radius: 10)
                .offset(y: UIScreen.main.bounds.height / 25)
            
            VStack(spacing: 0) { // Set spacing to 0 to remove gaps
                HStack {
                    Image(systemName: "chevron.down")
                        .foregroundColor(Color(hex: "FF009a88"))
                        .fontWeight(.bold)
                        .font(.system(size: 25))
                    
                    Spacer()
                    
                    Text("New Task __")
                        .font(.headline)
                    
                    Spacer()
                    
                    Image(systemName: "plus")
                        .foregroundColor(.white)
                        .padding(8)
                        .background(Color(hex: "FF009a88"))
                        .fontWeight(.bold)
                        .clipShape(Circle())
                }
                .offset(y: UIScreen.main.bounds.height / 30)
                .padding()
                
                Spacer()
                    .frame(height: 20)
                
                
                Rectangle()
                    .foregroundColor(Color(hex: "#D9D9D9"))
                    .frame(height: 3)
                
                Rectangle()
                    .foregroundColor(Color(hex: "EEEEEE"))
                    .ignoresSafeArea(.all)
                
               
            }
        }
    }
}

#Preview {
    NewTask()
}


//import SwiftUI
//
//struct NewTask: View {
//    var body: some View {
//        ZStack{
//            
////            Color.blue
////                .ignoresSafeArea(.all)
//            
//            RoundedRectangle(cornerRadius: 25)
//                .fill(Color.white)
//                .shadow(color: Color.black.opacity(0.5), radius: 10)
//                .offset(y: UIScreen.main.bounds.height / 25)
//            
//            VStack{
//                
//                HStack {
//                    
//                    Image(systemName: "chevron.down")
//                        .foregroundColor(Color(hex: "FF009a88")) //change to correct teal later
//                        .fontWeight(.bold)
//                        .font(.system(size: 25))
//                    
//                    Spacer()
//                    
//                    Text("New Task __") //fix to show whatever task you are on
//                        .font(.headline)
//                    
//                    Spacer()
//                    
//                    Image(systemName: "plus")
//                        .foregroundColor(.white)
//                        .padding(8)
//                        .background(Color(hex: "FF009a88")) // fix to be correct color
//                        .fontWeight(.bold)
//                        .clipShape(Circle())
//                
//                    
//                    
//                }
//                .offset(y: UIScreen.main.bounds.height / 30)
//                .padding()
//                
//                Spacer()
//                    .frame(maxHeight: 20)
//                
//                Rectangle()
//                    .foregroundStyle(Color(hex: "#D9D9D9"))
//                    .frame(maxWidth: 500, maxHeight: 3)
//                
////                Rectangle()
////                    .foregroundStyle(Color(hex: "EEEEEE"))
//                
//                GeometryReader { geometry in
//                    Rectangle()
//                        .foregroundStyle(Color(hex: "EEEEEE"))
//                        .frame(width: geometry.size.width, height: geometry.size.height * 2)
//                }
//                
//                
//                      
//                
//                Spacer()
//            } 
//            
//        }
//        
//        
//        
//    }
//}
//
//
//#Preview {
//    NewTask()
//}
