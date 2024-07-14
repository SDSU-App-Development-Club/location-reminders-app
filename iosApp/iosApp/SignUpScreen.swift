//
//  SignUpScreen.swift
//  iosApp
//
//  Created by Deanna O on 6/9/24.
//  Copyright © 2024 orgName. All rights reserved.
//




import SwiftUI
import Shared


struct SignUpScreen: View {
    @State private var email: String = ""
    @State private var password: String = ""
    @State private var signInSuccess = false
    @State private var needLogIn = false
    
    
    var body: some View {
        Group {
            
            ZStack { //background
            
                BackgroundWallpaper()
                
                BackgroundDetails()
                
                if signInSuccess {
                    LogInScreen()
                } else {
                    //LoginView()
                    if needLogIn {
                        LogInScreen()
                    } else {
                        VStack {
                            SignUpText()
                                .offset(y: -20)
                               
                            
                            VStack { //input email and password to sign up
                                
                                Email(email: $email)
                                
                                Password(password: $password)
                               
                                CreateAccountButton(email: $email, password: $password, signInSuccess: $signInSuccess)
                                
                                //turn into a button
                                AlreadyHaveAnAccount(needLogIn: $needLogIn)

                            }
                            
                        }
                        .offset(y: -40)
                    }
                
                }

            }
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

struct  BackgroundDetails: View {
    var body: some View {
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
    }
}

struct BackgroundWallpaper: View {
    var body: some View {
        Image("Sign Up Background")
            .resizable()
            .edgesIgnoringSafeArea(.all)
    }
}

struct Email: View {
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

struct Password: View {
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

struct CreateAccountButton: View {
    @Binding var email: String
    @Binding var password: String
    @Binding var signInSuccess: Bool
    var body: some View {
        Button(action: {
            RestAPIAccess().attemptSignup(username: email, password: password) {
                response, error in
                if let response = response {
                    signInSuccess = true
                }
                
            }
        }){
            
            ZStack {
                RoundedRectangle(cornerRadius: 18)
                    .frame(width: 300, height: 50) // Adjust the frame size as needed
                    .foregroundColor(.white)
                
                Text("Create Account")
                //fix coloring and add a drop shadow
            }
            .offset(y: -10)
        }
    }
}

struct AlreadyHaveAnAccount: View {
    @Binding var needLogIn: Bool
    var body: some View {
        Button(action: {
            needLogIn = true
        }){
            Text ("Already have an account?")
                .foregroundColor(.white)
            + Text(" Log in.")
                .foregroundColor(.white)
                .bold()
        }
    }
}
