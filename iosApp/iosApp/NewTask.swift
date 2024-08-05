//
//  NewTask.swift
//  iosApp
//
//  Created by Deanna O on 7/16/24.
//  Copyright © 2024 orgName. All rights reserved.
//

import SwiftUI
import GoogleMaps

struct NewTask: View {
    @State private var newTask: String = ""
    @State private var notes: String = ""
    @State private var toggle = false
    @State private var location: String = ""
    @Binding var isVisible: Bool
    @State private var selectedLocation: CLLocationCoordinate2D?
        @State private var mapCenter: CLLocationCoordinate2D = CLLocationCoordinate2D(latitude: -33.86, longitude: 151.20)
    @State private var savedLocation: CLLocationCoordinate2D?
    
    
    var body: some View {
        
        //New task title with add button & toggling
        ZStack {
            RoundedRectangle(cornerRadius: 25)
                .fill(Color.white)
                .shadow(color: Color.black.opacity(0.5), radius: 10)
                .offset(y: UIScreen.main.bounds.height / 25)
            
            NewTaskTitle(toggle: $toggle, isVisible: $isVisible, newTask: $newTask)
            
            //new task information
            VStack (spacing: 10) {
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
                    
                    VStack(spacing: 10) {
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
                        
                        Divider()
                        
                        HStack {
                            TextField("Search for a Place", text: $location, onCommit: {
                                searchLocation()
                            })
                            .textFieldStyle(RoundedBorderTextFieldStyle())
                            
                            Button(action: searchLocation) {
                                Image(systemName: "magnifyingglass")
                            }
                        }
                        .padding(.horizontal)
                        
                        GoogleMapView(center: $mapCenter, selectedLocation: $selectedLocation)
                            .frame(height: 150)
                            .cornerRadius(10)
                        
                        Button(action: saveLocation) {
                            Text("Save Location")
                                .padding()
                                .background(Color.blue)
                                .foregroundColor(.white)
                                .cornerRadius(10)
                        }
                        .disabled(selectedLocation == nil)
                        
                        
                    }
                    .padding(.top, 10)
                    .padding(.bottom, 10)
                    
                }
                .padding(.horizontal)
                .background(
                    RoundedRectangle(cornerRadius: 20)
                        .fill(Color.white)
                        .shadow(color: Color.black.opacity(0.1), radius: 5, x: 0, y: 2)
                )
                .padding(.horizontal)
                
            }
            .padding(.top, 20)
            
            
            if toggle {
                ReminderScreen()
            }
            
            
        }
    }
    
    func searchLocation() {
        let geocoder = CLGeocoder()
        geocoder.geocodeAddressString(location) { placemarks, error in
            if let placemark = placemarks?.first, let location = placemark.location {
                self.mapCenter = location.coordinate
                self.selectedLocation = location.coordinate
            }
        }
    }
    
    func saveLocation() {
        if let location = selectedLocation {
            savedLocation = location
            // Here you can also save the location to your app's data storage
            print("Location saved: \(location.latitude), \(location.longitude)")
        }
    }
}

#Preview {
    @State var isVisible = true
        return NewTask(isVisible: $isVisible)
}


struct NewTaskTitle: View {
    @Binding var toggle: Bool
    @Binding var isVisible: Bool
    @Binding var newTask: String
    
    var body: some View {
        ZStack {
            VStack(spacing: 0) { // Set spacing to 0 to remove gaps
                HStack {
                    Image(systemName: "chevron.down")
                        .foregroundColor(Color(hex: "FF009a88"))
                        .fontWeight(.bold)
                        .font(.system(size: 25))
                        .onTapGesture {
                            withAnimation(.easeInOut(duration: 0.5)) {
                                isVisible = false
                            }
                        }
                    Spacer()
                    
                    TextField("New Task __", text: $newTask)
                        .font(.headline)
                    
                    Spacer()
                    
                    
                    // TODO: turn this into a button that saves the new task
                    Button(action: {
                        
                        //add function to add reminder
                        
                        
                    }){
                        Image(systemName: "plus")
                            .foregroundColor(.white)
                            .padding(8)
                            .background(Color(hex: "FF009a88"))
                            .fontWeight(.bold)
                            .clipShape(Circle())
                    }
                }
                .offset(y: UIScreen.main.bounds.height / 30)
                .padding(20)
                
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
                .frame(width: UIScreen.main.bounds.width / 1.8)
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
