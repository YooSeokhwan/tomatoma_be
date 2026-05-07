import React, { useState, useEffect } from 'react'
import { APIProvider, Map, AdvancedMarker, Pin, InfoWindow, useMap } from '@vis.gl/react-google-maps'
import { useFoodPlaces } from '../hooks/useFoodPlaces'
import { useGeolocation } from '../hooks/useGeolocation'
import PlaceDetailPopup from './PlaceDetailPopup'
import LoadingSpinner from './LoadingSpinner'
import '../styles/GoogleMapsComponent.css'

const DEFAULT_CENTER = { lat: 37.5665, lng: 126.9780 }

function MapCameraController({ target }) {
  const map = useMap()
  useEffect(() => {
    if (map && target) map.panTo(target)
  }, [map, target])
  return null
}

function GoogleMapsComponent({ selectedFood, refreshKey }) {
  const apiKey = import.meta.env.VITE_REACT_APP_GOOGLE_MAPS_API_KEY
  const { latitude, longitude } = useGeolocation()
  const { places, loading: placesLoading } = useFoodPlaces(selectedFood?.id)
  const [selectedPlace, setSelectedPlace] = useState(null)
  const [visibleMarkers, setVisibleMarkers] = useState([])

  useEffect(() => {
    setVisibleMarkers(selectedFood ? places : [])
    setSelectedPlace(null)
  }, [places, selectedFood, refreshKey])

  if (!apiKey) {
    return (
      <div className="google-maps-component error">
        <p>Google Maps API Key가 설정되지 않았습니다.</p>
      </div>
    )
  }

  const userLocation = latitude && longitude ? { lat: latitude, lng: longitude } : null

  return (
    <div className="google-maps-component">
      <APIProvider apiKey={apiKey}>
        <Map
          defaultCenter={DEFAULT_CENTER}
          defaultZoom={13}
          mapId="DEMO_MAP_ID"
          fullscreenControl={false}
          streetViewControl={false}
          style={{ width: '100%', height: '100%' }}
        >
          <MapCameraController target={userLocation} />

          {userLocation && (
            <AdvancedMarker position={userLocation} title="현재 위치">
              <div style={{
                width: 16,
                height: 16,
                borderRadius: '50%',
                backgroundColor: '#4285F4',
                border: '2px solid white',
                boxShadow: '0 2px 4px rgba(0,0,0,0.3)',
              }} />
            </AdvancedMarker>
          )}

          {visibleMarkers.map((place) => (
            <AdvancedMarker
              key={place.id}
              position={{ lat: place.latitude, lng: place.longitude }}
              title={place.name}
              onClick={() => setSelectedPlace(place)}
            >
              <Pin
                background={selectedFood?.color || '#FF5252'}
                glyphColor="#FFFFFF"
                borderColor="#FFFFFF"
              />
            </AdvancedMarker>
          ))}

          {selectedPlace && (
            <InfoWindow
              position={{ lat: selectedPlace.latitude, lng: selectedPlace.longitude }}
              onClose={() => setSelectedPlace(null)}
            >
              <PlaceDetailPopup place={selectedPlace} />
            </InfoWindow>
          )}
        </Map>
      </APIProvider>

      {!selectedFood && (
        <div className="map-empty-state">
          <p>좌측에서 음식을 선택하여 판매처를 확인하세요.</p>
        </div>
      )}

      {selectedFood && placesLoading && (
        <div className="map-loading">
          <LoadingSpinner />
        </div>
      )}
    </div>
  )
}

export default GoogleMapsComponent
