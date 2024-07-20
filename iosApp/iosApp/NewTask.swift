//
//  NewTask.swift
//  iosApp
//
//  Created by Deanna O on 7/16/24.
//  Copyright © 2024 orgName. All rights reserved.
//

import SwiftUI

struct NewTask: View {
    @State private var newTask: String = ""
    @State private var notes: String = ""
    
    var body: some View {
        
        //New task title with add button & toggling
        ZStack {
            RoundedRectangle(cornerRadius: 25)
                .fill(Color.white)
                .shadow(color: Color.black.opacity(0.5), radius: 10)
                .offset(y: UIScreen.main.bounds.height / 25)
            
            NewTaskTitle()
            
            //new task information
            VStack {
                //emoji + new task __
                EmojiAndTitle(newTask: $newTask)
                
                //Notes text box
                Notes(notes: $notes)
                
                //location box that holds filling in address for task
                ZStack {
                    //background box
                    RoundedRectangle(cornerRadius: 20)
                        .frame(height: 200)
                        .foregroundStyle(.white)
                    
                    VStack {
                        //location title and icon
                        LocationTitle()
                        
                        //quick find address icons
                        HStack{ //turn these into buttons that change color when clicked on
                            
                            //current location button
                            CurrentLocation()
                            //house address button
                            HouseAddress()
                            //work address button
                            WorkAddress()
                            //custom address
                            CustomAddress()
                        }
                      
                        
                        //divider rectangle
                        Rectangle()
                            .frame(height: 1)
                            .padding(.horizontal)
                            .foregroundStyle(Color(hex: "#cbcbc9"))
                        
                        //Button/thingy for changing/setting the location data
                        //actually implement when backend is up
                        Text("Arriving")
                        
                    }
                    
                }.padding(.horizontal)
                
                Spacer()
                    .frame(height: UIScreen.main.bounds.height / 3.8)
                
            }
        }
    }
}

#Preview {
    NewTask()
}


struct NewTaskTitle: View {
    var body: some View {
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

struct EmojiAndTitle: View {
    @Binding var newTask: String
    
    var body: some View {
        HStack {
            //adds an emoji to the task
            Button(action: {
                //back end stuff to remember the emoji & add a new emoji
            }) {
                ZStack {
                    Circle()
                        .foregroundColor(.white)
                        .frame(width: 48)
                    
                    Image("add emoji")
                        .offset(x: 2)
                }
            }
            //typing the title of the task
            ZStack {
                RoundedRectangle(cornerRadius: 22)
                    .frame(height: 48)
                    .foregroundColor(.white)
                
                TextField("New Task __", text: $newTask)
                    .padding(.leading, 16)
            }
        }.padding(.horizontal)
    }
}

struct Notes: View {
    @Binding var notes: String
    
    var body: some View {
        ZStack {
            RoundedRectangle(cornerRadius: 22)
                .frame(height: 48)
                .foregroundStyle(.white)
            
            
            TextField("Notes", text: $notes)
                .padding(.leading, 16)
        }.padding(.horizontal)
    }
}

struct LocationTitle: View {
    var body: some View {
        HStack {
            Image(systemName: "location.square.fill")
                .foregroundStyle(.green)
                .font(.system(size: 28))
            
            Text("Location")
                .font(.system(size: 16))
                .fontWeight(.semibold)
            
            Spacer()
                .frame(width: UIScreen.main.bounds.height / 3.7)
        }
    }
}

struct CurrentLocation: View {
    var body: some View {
        VStack{
            ZStack {
                RoundedRectangle(cornerRadius: 10)
                    .frame(width: 70, height: 70)
                    .padding(5)
                    .foregroundStyle(Color(hex: "#cbcbc9"))
                
                Image(systemName: "location.fill")
                    .font(.system(size: 45))
                
            }
            Text("Current")
                .font(.system(size: 12))
                .fontWeight(.semibold)
        }
    }
}

struct HouseAddress: View {
    var body: some View {
        VStack{
            ZStack{
                RoundedRectangle(cornerRadius: 10)
                    .frame(width: 70, height: 70)
                    .padding(5)
                    .foregroundStyle(Color(hex: "#cbcbc9"))
                
                Image(systemName: "house")
                    .font(.system(size: 45))
            }
            Text("Home")
                .font(.system(size: 12))
                .fontWeight(.semibold)
        }
    }
}

struct WorkAddress: View {
    var body: some View {
        VStack{
            ZStack {
                RoundedRectangle(cornerRadius: 10)
                    .frame(width: 70, height: 70)
                    .padding(5)
                    .foregroundStyle(Color(hex: "#cbcbc9"))
                
                Image(systemName: "building.fill")
                    .font(.system(size: 45))
            }
            Text("Work")
                .font(.system(size: 12))
                .fontWeight(.semibold)
        }
    }
}

struct CustomAddress: View {
    var body: some View {
        VStack{
            ZStack {
                RoundedRectangle(cornerRadius: 10)
                    .frame(width: 70, height: 70)
                    .padding(5)
                    .foregroundStyle(Color(hex: "#cbcbc9"))
                
                Image(systemName: "plus")
                    .font(.system(size: 25))
                    .fontWeight(.bold)
            }
            Text("Custom")
                .font(.system(size: 12))
                .fontWeight(.semibold)
        }
    }
}
