import apiClient from './api'

export const placeService = {
  /**
   * Get places for a specific food
   */
  getPlacesForFood: async (trendFoodId) => {
    try {
      const response = await apiClient.get(`/places/${trendFoodId}`)
      return response.data
    } catch (error) {
      console.error('Error fetching places:', error)
      throw error
    }
  },

  /**
   * Get places sorted by rating
   */
  getPlacesByRating: async (trendFoodId) => {
    try {
      const response = await apiClient.get(`/places/${trendFoodId}/rated`)
      return response.data
    } catch (error) {
      console.error('Error fetching places by rating:', error)
      throw error
    }
  },

  /**
   * Get nearest places
   */
  getNearestPlaces: async (trendFoodId, latitude, longitude) => {
    try {
      const response = await apiClient.get(`/places/${trendFoodId}/nearest`, {
        params: { latitude, longitude }
      })
      return response.data
    } catch (error) {
      console.error('Error fetching nearest places:', error)
      throw error
    }
  },
}
