import { useState, useEffect } from 'react'
import { placeService } from '../services/placeService'

export const useFoodPlaces = (trendFoodId) => {
  const [places, setPlaces] = useState([])
  const [loading, setLoading] = useState(false)
  const [error, setError] = useState(null)

  useEffect(() => {
    if (!trendFoodId) {
      setPlaces([])
      return
    }

    const fetchPlaces = async () => {
      setLoading(true)
      setError(null)

      try {
        const response = await placeService.getPlacesForFood(trendFoodId)
        if (response.status === 'success') {
          setPlaces(response.data || [])
        } else {
          setError(response.message || 'Failed to fetch places')
        }
      } catch (err) {
        setError(err.message || 'Error fetching places')
      } finally {
        setLoading(false)
      }
    }

    fetchPlaces()
  }, [trendFoodId])

  return { places, loading, error }
}

export const useNearestPlaces = (trendFoodId, latitude, longitude) => {
  const [places, setPlaces] = useState([])
  const [loading, setLoading] = useState(false)
  const [error, setError] = useState(null)

  useEffect(() => {
    if (!trendFoodId || latitude === null || longitude === null) {
      setPlaces([])
      return
    }

    const fetchNearest = async () => {
      setLoading(true)
      setError(null)

      try {
        const response = await placeService.getNearestPlaces(trendFoodId, latitude, longitude)
        if (response.status === 'success') {
          setPlaces(response.data || [])
        } else {
          setError(response.message || 'Failed to fetch nearest places')
        }
      } catch (err) {
        setError(err.message || 'Error fetching places')
      } finally {
        setLoading(false)
      }
    }

    fetchNearest()
  }, [trendFoodId, latitude, longitude])

  return { places, loading, error }
}
