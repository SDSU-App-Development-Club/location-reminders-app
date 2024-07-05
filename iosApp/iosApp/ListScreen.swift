//
//  ListScreen.swift
//  iosApp
//
//  Created by Deanna O on 7/4/24.
//  Copyright © 2024 orgName. All rights reserved.
//

import SwiftUI

struct ListScreen: View {
    var body: some View {
        ZStack{
            Image("Lists screen")
                .resizable()
                .edgesIgnoringSafeArea(.all)
            
            VStack{
                Image("Lists screen squiggly 1")
                    .offset(y: 160)
                
                Image("Lists screen squiggly 2")
                    .offset(y: -190)
            }
            
            VStack{
                Text("Lists")
                    .fontWeight(.semibold)
                    .font(.system(size: 32))
                    .frame(maxWidth: 340, maxHeight: 75, alignment: .leading)
                    .foregroundColor(Color(hex: "#FFFFFF"))
                
                Rectangle()
                    .frame(maxWidth: 359, maxHeight: 2, alignment: .center)
                    .foregroundColor(Color(hex: "#FFFFFF"))
                
                HStack{
                    Rectangle()
                        .frame(maxWidth: 166, maxHeight: 129, alignment: .trailing)
                        .cornerRadius(15.0)
                        .foregroundColor(Color(hex: "#b0fcf4"))
                    
                    Spacer()
                        .frame(width: 28)
                    
                    Rectangle()
                        .frame(maxWidth: 166, maxHeight: 129, alignment: .trailing)
                        .cornerRadius(15.0)
                        .foregroundColor(Color(hex: "#b0fcf4"))
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
