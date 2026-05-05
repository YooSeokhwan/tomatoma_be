import React, { useState } from 'react'
import SearchInput from './SearchInput'
import CategoryFilter from './CategoryFilter'
import SortDropdown from './SortDropdown'
import TrendFoodList from './TrendFoodList'
import '../styles/LeftPanel.css'

function LeftPanel({
  selectedFood,
  onSelectFood,
  selectedCategories,
  onCategoryToggle,
  onSelectAllCategories,
  searchQuery,
  onSearchChange,
  sortBy,
  onSortChange,
  refreshKey,
}) {
  return (
    <div className="left-panel">
      <SearchInput value={searchQuery} onChange={onSearchChange} />

      <div className="filter-section">
        <h3>필터</h3>
        <CategoryFilter
          selectedCategories={selectedCategories}
          onCategoryToggle={onCategoryToggle}
          onSelectAllCategories={onSelectAllCategories}
        />
      </div>

      <div className="sort-section">
        <label htmlFor="sort-select">정렬:</label>
        <SortDropdown value={sortBy} onChange={onSortChange} />
      </div>

      <TrendFoodList
        selectedFood={selectedFood}
        onSelectFood={onSelectFood}
        selectedCategories={selectedCategories}
        searchQuery={searchQuery}
        sortBy={sortBy}
        refreshKey={refreshKey}
      />
    </div>
  )
}

export default LeftPanel
