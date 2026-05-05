import React, { useState, useEffect, useCallback } from 'react'
import { GoogleMap, useJsApiLoader, Marker, InfoWindow } from '@react-google-maps/api'
import { useFoodPlaces } from '../hooks/useFoodPlaces'
import { useGeolocation } from '../hooks/useGeolocation'
import { categoryService } from '../services/categoryService'
import { createMarkerIcon } from '../utils/mapUtils'
import PlaceDetailPopup from './PlaceDetailPopup'
import LoadingSpinner from './LoadingSpinner'
import '../styles/GoogleMapsComponent.css'

const mapContainerStyle = {
  width: '100%',
  height: '100%',
}

const defaultMapCenter = {
  lat: 37.5665,
  lng: 126.9780,
}

function GoogleMapsComponent({ selectedFood, selectedCategories, refreshKey }) {
  const apiKey = import.meta.env.VITE_REACT_APP_GOOGLE_MAPS_API_KEY
  const { isLoaded, loadError } = useJsApiLoader({ googleMapsApiKey: apiKey || '' })

  const { latitude, longitude } = useGeolocation()
  const { places, loading: placesLoading } = useFoodPlaces(selectedFood?.id)
  const [map, setMap] = useState(null)
  const [selectedPlace, setSelectedPlace] = useState(null)
  const [categories, setCategories] = useState({})
  const [mapCenter, setMapCenter] = useState(defaultMapCenter)
  const [visibleMarkers, setVisibleMarkers] = useState([])

  useEffect(() => {
    const fetchCategories = async () => {
      try {
        const response = await categoryService.getCategories()
        if (response.status === 'success') {
          const categoryMap = {}
          response.data.forEach((cat) => { categoryMap[cat.name] = cat })
          setCategories(categoryMap)
        }
      } catch (error) {
        console.error('Error fetching categories:', error)
      }
    }
    fetchCategories()
  }, [])

  useEffect(() => {
    if (latitude && longitude) {
      setMapCenter({ lat: latitude, lng: longitude })
    }
  }, [latitude, longitude])

  useEffect(() => {
    if (selectedFood) {
      const filtered = selectedCategories.size > 0
        ? places.filter(() => selectedCategories.has(selectedFood.category))
        : places
      setVisibleMarkers(filtered)
    } else {
      setVisibleMarkers([])
    }
  }, [places, selectedFood, selectedCategories, refreshKey])

  const onLoad = useCallback((mapInstance) => setMap(mapInstance), [])
  const onUnmount = useCallback(() => setMap(null), [])

  if (!apiKey) {
    return (
      <div className="google-maps-component error">
        <p>Google Maps API Key가 설정되지 않았습니다.</p>
      </div>
    )
  }

  if (loadError) {
    return (
      <div className="google-maps-component error">
        <p>Google Maps 로드 실패: {loadError.message}</p>
      </div>
    )
  }

  if (!isLoaded) {
    return (
      <div className="google-maps-component">
        <LoadingSpinner />
      </div>
    )
  }

  return (
    <div className="google-maps-component">
      <GoogleMap
        mapContainerStyle={mapContainerStyle}
        center={mapCenter}
        zoom={13}
        onLoad={onLoad}
        onUnmount={onUnmount}
        options={{ fullscreenControl: false, streetViewControl: false }}
      >
        {latitude && longitude && (
          <Marker
            position={{ lat: latitude, lng: longitude }}
            icon={{
              path: window.google.maps.SymbolPath.CIRCLE,
              scale: 8,
              fillColor: '#4285F4',
              fillOpacity: 1,
              strokeColor: '#FFFFFF',
              strokeWeight: 2,
            }}
            title="현재 위치"
          />
        )}

        {visibleMarkers.map((place) => (
          <Marker
            key={place.id}
            position={{ lat: place.latitude, lng: place.longitude }}
            icon={createMarkerIcon(selectedFood?.color || '#FF5252')}
            onClick={() => setSelectedPlace(place)}
            title={place.name}
          />
        ))}

        {selectedPlace && (
          <InfoWindow
            position={{ lat: selectedPlace.latitude, lng: selectedPlace.longitude }}
            onCloseClick={() => setSelectedPlace(null)}
          >
            <PlaceDetailPopup place={selectedPlace} />
          </InfoWindow>
        )}
      </GoogleMap>

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
