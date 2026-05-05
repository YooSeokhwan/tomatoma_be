import apiClient from './api'

export const trendService = {
  /**
   * Get trending foods
   */
  getTrendingFoods: async (page = 0, size = 20, category = null, sortBy = 'search_frequency', direction = 'DESC') => {
    try {
      const params = { page, size, sortBy, direction }
      if (category) params.category = category

      const response = await apiClient.get('/trends', { params })
      return response.data
    } catch (error) {
      console.error('Error fetching trending foods:', error)
      throw error
    }
  },

  /**
   * Search trending foods by keyword
   */
  searchTrendingFoods: async (keyword, page = 0, size = 20) => {
    try {
      const response = await apiClient.get('/trends/search', {
        params: { q: keyword, page, size }
      })
      return response.data
    } catch (error) {
      console.error('Error searching trending foods:', error)
      throw error
    }
  },

  /**
   * Get a specific trending food
   */
  getTrendingFoodById: async (trendFoodId) => {
    try {
      const response = await apiClient.get(`/trends/${trendFoodId}`)
      return response.data
    } catch (error) {
      console.error('Error fetching trending food:', error)
      throw error
    }
  },
}
