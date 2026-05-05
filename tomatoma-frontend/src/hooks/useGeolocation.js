import { useState, useEffect } from 'react'

export const useGeolocation = () => {
  const [latitude, setLatitude] = useState(null)
  const [longitude, setLongitude] = useState(null)
  const [error, setError] = useState(null)
  const [loading, setLoading] = useState(true)

  useEffect(() => {
    if (!navigator.geolocation) {
      setError('Geolocation is not supported by your browser')
      setLoading(false)
      return
    }

    const geoSuccess = (position) => {
      setLatitude(position.coords.latitude)
      setLongitude(position.coords.longitude)
      setError(null)
      setLoading(false)
    }

    const geoError = (error) => {
      setError(error.message)
      // Default to Seoul if geolocation fails
      setLatitude(37.5665)
      setLongitude(126.9780)
      setLoading(false)
    }

    navigator.geolocation.getCurrentPosition(geoSuccess, geoError, {
      timeout: 5000,
      enableHighAccuracy: false,
    })
  }, [])

  return { latitude, longitude, error, loading }
}
