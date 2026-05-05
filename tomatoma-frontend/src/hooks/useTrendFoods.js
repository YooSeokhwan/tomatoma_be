import { useState, useEffect } from 'react'
import { trendService } from '../services/trendService'

export const useTrendFoods = (page = 0, size = 20, category = null, sortBy = 'search_frequency') => {
  const [trends, setTrends] = useState([])
  const [loading, setLoading] = useState(true)
  const [error, setError] = useState(null)
  const [totalElements, setTotalElements] = useState(0)

  useEffect(() => {
    const fetchTrends = async () => {
      setLoading(true)
      setError(null)

      try {
        const response = await trendService.getTrendingFoods(page, size, category, sortBy)
        if (response.status === 'success') {
          setTrends(response.data.content || [])
          setTotalElements(response.data.totalElements || 0)
        } else {
          setError(response.message || 'Failed to fetch trends')
        }
      } catch (err) {
        setError(err.message || 'Error fetching trends')
      } finally {
        setLoading(false)
      }
    }

    fetchTrends()
  }, [page, size, category, sortBy])

  return { trends, loading, error, totalElements }
}

export const useSearchTrends = (keyword) => {
  const [results, setResults] = useState([])
  const [loading, setLoading] = useState(false)
  const [error, setError] = useState(null)

  useEffect(() => {
    if (!keyword || keyword.trim() === '') {
      setResults([])
      return
    }

    const search = async () => {
      setLoading(true)
      setError(null)

      try {
        const response = await trendService.searchTrendingFoods(keyword)
        if (response.status === 'success') {
          setResults(response.data.content || [])
        } else {
          setError(response.message || 'Search failed')
        }
      } catch (err) {
        setError(err.message || 'Error searching')
      } finally {
        setLoading(false)
      }
    }

    const debounceTimer = setTimeout(search, 300)
    return () => clearTimeout(debounceTimer)
  }, [keyword])

  return { results, loading, error }
}
