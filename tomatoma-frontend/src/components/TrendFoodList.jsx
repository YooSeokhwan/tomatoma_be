import React, { useMemo, useRef } from 'react'
import TrendFoodCard from './TrendFoodCard'
import LoadingSpinner from './LoadingSpinner'
import { useTrendFoods, useSearchTrends } from '../hooks/useTrendFoods'
import '../styles/TrendFoodList.css'

function TrendFoodList({
  selectedFood,
  onSelectFood,
  selectedCategories,
  searchQuery,
  sortBy,
  refreshKey,
}) {
  // Set → 안정적인 문자열 키 (참조 비교 문제 방지)
  const categoryKey = Array.from(selectedCategories).sort().join(',')

  // 카테고리 필터는 클라이언트에서 처리 → API엔 항상 null (다중 선택 지원)
  const { trends, loading: trendsLoading, error: trendsError } = useTrendFoods(
    0,
    50,
    null,
    sortBy,
    refreshKey
  )

  // Search functionality
  const { results: searchResults, loading: searchLoading } = useSearchTrends(searchQuery)

  // Determine which data to show
  const isSearching = searchQuery.trim() !== ''
  const foodList = isSearching ? searchResults : trends
  const loading = isSearching ? searchLoading : trendsLoading
  const error = isSearching ? null : trendsError

  // Filter by categories if needed
  const filteredList = useMemo(() => {
    let filtered = foodList

    if (selectedCategories.size > 0 && !isSearching) {
      filtered = foodList.filter((food) => selectedCategories.has(food.category))
    }

    // Sort
    if (sortBy === 'search_frequency') {
      filtered = [...filtered].sort((a, b) => (b.searchFrequency || 0) - (a.searchFrequency || 0))
    } else if (sortBy === 'trend_rank') {
      filtered = [...filtered].sort((a, b) => (a.trendRank || 0) - (b.trendRank || 0))
    }

    return filtered
  }, [foodList, categoryKey, sortBy, isSearching])

  if (loading) {
    return (
      <div className="trend-food-list">
        <LoadingSpinner />
      </div>
    )
  }

  if (error) {
    return (
      <div className="trend-food-list error">
        <div className="error-message">
          <p>⚠️ 오류 발생</p>
          <p>{error}</p>
          <button className="btn btn-small" onClick={() => window.location.reload()}>
            새로고침
          </button>
        </div>
      </div>
    )
  }

  if (filteredList.length === 0) {
    return (
      <div className="trend-food-list empty">
        <div className="empty-message">
          <p>🔍 결과가 없습니다</p>
          <p>필터를 조정하거나 다른 검색어를 시도해주세요.</p>
        </div>
      </div>
    )
  }

  return (
    <div className="trend-food-list">
      {filteredList.map((food) => (
        <TrendFoodCard
          key={food.id}
          food={food}
          isSelected={selectedFood?.id === food.id}
          onSelect={() => onSelectFood(food)}
        />
      ))}
    </div>
  )
}

export default TrendFoodList
