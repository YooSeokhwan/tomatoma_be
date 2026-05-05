import React from 'react'
import { formatPrice, formatFrequencyBar } from '../utils/formatUtils'
import '../styles/TrendFoodCard.css'

function TrendFoodCard({ food, isSelected, onSelect }) {
  const frequencyBar = formatFrequencyBar(food.searchFrequency, 10000)

  return (
    <div
      className={`trend-food-card ${isSelected ? 'selected' : ''}`}
      onClick={onSelect}
      role="button"
      tabIndex={0}
      onKeyPress={(e) => {
        if (e.key === 'Enter' || e.key === ' ') {
          onSelect()
        }
      }}
      aria-label={`${food.name}, ${food.category}, ${food.searchFrequency} searches`}
    >
      <div className="card-header">
        <div className="category-badge" style={{ backgroundColor: food.color }}>
          {food.category}
        </div>
        <span className="rank">#{food.trendRank}</span>
      </div>

      <h4 className="food-name">{food.name}</h4>

      <div className="frequency-section">
        <div className="label">검색 빈도</div>
        <div className="frequency-bar">
          <div className="bar-fill" style={{ width: frequencyBar.width }}></div>
        </div>
        <span className="frequency-value">{food.searchFrequency?.toLocaleString() || 0}</span>
      </div>

      <div className="card-footer">
        <div className="place-count">판매처: 미로딩</div>
        <button className="btn btn-small" onClick={(e) => e.stopPropagation()}>
          상세보기
        </button>
      </div>
    </div>
  )
}

export default TrendFoodCard
