import apiClient from './api'

export const categoryService = {
  /**
   * Get all categories
   */
  getCategories: async () => {
    try {
      const response = await apiClient.get('/categories')
      return response.data
    } catch (error) {
      console.error('Error fetching categories:', error)
      throw error
    }
  },
}
