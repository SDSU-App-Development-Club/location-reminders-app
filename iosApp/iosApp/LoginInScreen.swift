//
//  LoginInScreen.swift
//  iosApp
//
//  Created by Deanna O on 6/9/24.
//  Copyright © 2024 orgName. All rights reserved.
//

import SwiftUI

struct LogInScreen: View {
    @State private var email: String = ""
    @State private var password: String = ""
    var body: some View {
        ZStack { //background
            
            Image("Sign Up Background")
                .resizable()
                .edgesIgnoringSafeArea(.all)
            
            VStack {
                Image("Login Squiggly2")
                    .resizable()
                    .scaledToFit()
                    .frame(maxHeight: .infinity, alignment: .top)
                    .offset(y: -0)
                
                Spacer()
                
                Image("Login Squiggly1")
                    .resizable()
                    .scaledToFit()
                    .frame(maxHeight: .infinity, alignment: .bottom)
                    .offset(x: 0, y: -20)
            }
            .frame(maxWidth: .infinity, maxHeight: .infinity)
            .edgesIgnoringSafeArea(.all)
            
            VStack {
                VStack {
                    Text("Log In")
                        .fontWeight(.bold)
                        .font(.system(size: 36))
                        .foregroundColor(.white)
                        .frame(maxWidth: 300, alignment: .leading)

                }
                    .offset(y: -20)
                
                
                VStack { //input email and password to sign up
                    TextField("Email Address", text: $email)
                        .offset(x: 15)
                        .frame(maxWidth: 300, maxHeight: 40, alignment: .trailing)
                        .background(Color.white)
                        .cornerRadius(15.0)
                        .offset(y: -30)
                        .font(.system(size: 16))
                        
                    
                    
                    TextField("Password", text: $password)
                        .offset(x: 15)
                        .frame(maxWidth: 300, maxHeight: 40, alignment: .trailing)
                        .background(Color.white)
                        .cornerRadius(15.0)
                        .offset(y: -20)
                        .font(.system(size: 16))
                    
                    Button (action: {}) { //create account button
                        ZStack {
                            RoundedRectangle(cornerRadius: 15)
                                .frame(width: 300, height: 40) // Adjust the frame size as needed
                                .foregroundColor(.white)
                            
                            Text("Log In")
                                .font(.system(size: 20))
                                .fontWeight(.bold)
                            //fix coloring and add a drop shadow
                            
                        }
                        .offset(y: -10)
                    }
                    
                    Button (action: {}) {
                        Text ("Already have an account?")
                            .foregroundColor(.white)
                        + Text(" Log in.")
                            .foregroundColor(.white)
                            .bold()
                    }
                    .frame(width: 290, height: 20, alignment: .leading)
                    .font(.system(size: 16))
                    
                }
                
            }
            .offset(y: -40)
            
        }
    }
    
}


struct LogInScreen_Previews: PreviewProvider {
    static var previews: some View {
        LogInScreen()
    }
}
