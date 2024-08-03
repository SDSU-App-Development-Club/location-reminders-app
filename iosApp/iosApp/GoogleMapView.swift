//
//  GoogleMapView.swift
//  iosApp
//
//  Created by Deanna O on 8/1/24.
//  Copyright © 2024 orgName. All rights reserved.
//

import SwiftUI
import GoogleMaps

struct GoogleMapView: UIViewRepresentable {
    @Binding var center: CLLocationCoordinate2D
    @Binding var selectedLocation: CLLocationCoordinate2D?
    
    func makeUIView(context: Context) -> GMSMapView {
        let camera = GMSCameraPosition.camera(withLatitude: center.latitude, longitude: center.longitude, zoom: 12.0)
        let mapView = GMSMapView(frame: .zero, camera: camera)
        mapView.delegate = context.coordinator
        return mapView
    }

    func updateUIView(_ mapView: GMSMapView, context: Context) {
        mapView.animate(toLocation: center)
        
        mapView.clear()
        if let location = selectedLocation {
            let marker = GMSMarker(position: location)
            marker.map = mapView
        }
    }
    
    func makeCoordinator() -> Coordinator {
        Coordinator(self)
    }
    
    class Coordinator: NSObject, GMSMapViewDelegate {
        var parent: GoogleMapView
        
        init(_ parent: GoogleMapView) {
            self.parent = parent
        }
        
        func mapView(_ mapView: GMSMapView, didTapAt coordinate: CLLocationCoordinate2D) {
            parent.selectedLocation = coordinate
        }
    }
}

//struct GoogleMapView: UIViewRepresentable {
//    func makeUIView(context: Context) -> GMSMapView {
//        let camera = GMSCameraPosition.camera(withLatitude: -33.86, longitude: 151.20, zoom: 6.0)
//        let mapView = GMSMapView(frame: .zero, camera: camera)
//        
//        // Creates a marker in the center of the map.
//        let marker = GMSMarker()
//        marker.position = CLLocationCoordinate2D(latitude: -33.86, longitude: 151.20)
//        marker.title = "Sydney"
//        marker.snippet = "Australia"
//        marker.map = mapView
//        
//        return mapView
//    }
//
//    func updateUIView(_ mapView: GMSMapView, context: Context) {
//        // Update the map view if needed
//    }
//}

//#Preview {
//    GoogleMapView()
//}
