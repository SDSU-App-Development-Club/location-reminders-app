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
        ZStack{
            
//            Color.blue
//                .ignoresSafeArea(.all)
            
            RoundedRectangle(cornerRadius: 25)
                .fill(Color.white)
                .shadow(color: Color.black.opacity(0.5), radius: 10)
                .offset(y: UIScreen.main.bounds.height / 25)
            
            VStack{
                
                HStack {
                    
                    Image(systemName: "chevron.down")
                        .foregroundColor(.teal) //change to correct teal later
                        .fontWeight(.bold)
                        .font(.system(size: 25))
                    
                    Spacer()
                    
                    Text("New Task __") //fix to show whatever task you are on
                        .font(.headline)
                    
                    Spacer()
                    
                    Image(systemName: "plus")
                        .foregroundColor(.white)
                        .padding(8)
                        .background(Color.teal) // fix to be correct color
                        .fontWeight(.bold)
                        .clipShape(Circle())
                    
                    
                }.frame(maxHeight: 100)
                .padding()
                
                Spacer()
            } 
            
        }
        
        
        
    }
}


#Preview {
    NewTask()
}
