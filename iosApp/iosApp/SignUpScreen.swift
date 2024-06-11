//
//  SignUpScreen.swift
//  iosApp
//
//  Created by Deanna O on 6/9/24.
//  Copyright © 2024 orgName. All rights reserved.
//


import SwiftUI

struct SignUpScreen: View {
    @State private var email: String = ""
    @State private var password: String = ""
    var body: some View {
        ZStack { //background
        
            Image("Sign Up Background")
                .resizable()
                .edgesIgnoringSafeArea(.all)
            
            VStack {
                Image("Sign Up Page Squiggly 1")
                    .resizable()
                    .scaledToFit()
                    .frame(maxHeight: .infinity, alignment: .top)
                    .offset(y: -90)

                Spacer()

                Image("Sign Up Page Squiggly 2")
                    .resizable()
                    .scaledToFit()
                    .frame(maxHeight: .infinity, alignment: .bottom)
                    .offset(x: 20, y: 60)
            }
            .frame(maxWidth: .infinity, maxHeight: .infinity)
            .edgesIgnoringSafeArea(.all)
            
            VStack {
                SignUpText()
                    .offset(y: -20)
                   
                
                VStack { //input email and password to sign up
                    TextField("Email Address", text: $email)
                        .offset(x: 15)
                        .frame(maxWidth: 300, maxHeight: 50, alignment: .trailing)
                        .background(Color.white)
                        .cornerRadius(18.0)
                        .offset(y: -30)

                    
                    TextField("Password", text: $password)
                        .offset(x: 15)
                        .frame(maxWidth: 300, maxHeight: 50, alignment: .trailing)
                        .background(Color.white)
                        .cornerRadius(18.0)
                        .offset(y: -20)
                    
                    Button (action: {}) { //create account button
                        ZStack {
                            RoundedRectangle(cornerRadius: 18)
                                .frame(width: 300, height: 50) // Adjust the frame size as needed
                                .foregroundColor(.white)
                            
                            Text("Create Account")
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
                   
                }
               
            }
            .offset(y: -40)
            
        }
    }
}

struct SignUpText: View {
    var body: some View {
        VStack {
            Text("Sign Up")
                .fontWeight(.bold)
                .font(.system(size: 48))
                .foregroundColor(.white)
                .frame(maxWidth: 300, alignment: .leading)

        }
    }
}

struct SignUpScreen_Previews: PreviewProvider {
    static var previews: some View {
        SignUpScreen()
    }
}
