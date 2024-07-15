//
//  LoginInScreen.swift
//  iosApp
//
//  Created by Deanna O on 6/9/24.
//  Copyright © 2024 orgName. All rights reserved.
//

import SwiftUI
import Shared

struct LogInScreen: View {
    @State private var email: String = ""
    @State private var password: String = ""
    @State private var logInButton = false
    @State private var signUpButton = false
    @State private var showingAlert = false
    
    var body: some View {
        Group {
            ZStack { //background
                
                //BackgroundPage extracted subview
                BackgroundPage()
                
                //BackgroundDetail extracted subview
                BackgroundDetails()
                
                if logInButton {
                   ListScreen()
                } else {
                    
                    if signUpButton {
                        SignUpScreen()
                    } else {
                        
                        VStack {
                            
                            LoginText()
                            
                            VStack { //input email and password to sign up
                                EmailFields(email: $email)
                                
                                PasswordField(password: $password)
                                
                                Button(action: {
                                    RestAPIAccess().attemptLogin(username: email, password: password) { response, error in
                                        if let response = response {
                                            logInButton = true
                                        } else {
                                            //alert button
                                            showingAlert = true
                                        }
                                    }
                                    
                                }){
                                    ZStack {
                                        RoundedRectangle(cornerRadius: 18)
                                            .frame(width: 300, height: 50) // Adjust the frame size as needed
                                            .foregroundColor(.white)
                                        
                                        Text("Log In")
//                                            .font(.system(size: 20))
//                                            .fontWeight(.bold)
                                        //fix coloring and add a drop shadow
                                        
                                    }
                                    .offset(y: -10)
                                }
                                .alert("Log in Failed", isPresented: $showingAlert) {
                                    Button("OK", role: .cancel) { }
                                } message: {
                                    Text(email.isEmpty || password.isEmpty ? "Please enter both email and password." : "Login failed. Please try again.")
                                }
                                
                                
                                Button(action: { signUpButton = true }){
                                    //turn into a button
                                    Text ("Don't have an account?")
                                        .foregroundColor(.white)
                                    + Text(" Sign Up.")
                                        .foregroundColor(.white)
                                        .bold()
                                }
                            }
                            
                        }
                        .offset(y: -40)
                    }
                    
                }
                
            }
            
            
            
            
        }
    }
    
    
}




struct LogInScreen_Previews: PreviewProvider {
    static var previews: some View {
        LogInScreen()
    }
}

struct BackgroundDetail: View {
    var body: some View {
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
    }
}

struct BackgroundPage: View {
    var body: some View {
        Image("Sign Up Background")
            .resizable()
            .edgesIgnoringSafeArea(.all)
    }
}

struct LoginText: View {
    var body: some View {
        VStack {
            Text("Log In")
                .fontWeight(.bold)
                .font(.system(size: 48))
                .foregroundColor(.white)
                .frame(maxWidth: 300, alignment: .leading)
            
        }
        .offset(y: -20)
    }
}

struct EmailFields: View {
    @Binding var email: String
    
    var body: some View {
        TextField("Email Address", text: $email)
            .offset(x: 15)
            .frame(maxWidth: 300, maxHeight: 50, alignment: .trailing)
            .background(Color.white)
            .cornerRadius(18.0)
            .offset(y: -30)
            .autocapitalization(.none)
    }
}

struct PasswordField: View {
    @Binding var password: String
    
    var body: some View {
        SecureField("Password", text: $password)
            .offset(x: 15)
            .frame(maxWidth: 300, maxHeight: 50, alignment: .trailing)
            .background(Color.white)
            .cornerRadius(18.0)
            .offset(y: -20)
    }
}
