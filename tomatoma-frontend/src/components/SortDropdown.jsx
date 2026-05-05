import React from 'react'
import '../styles/SortDropdown.css'

function SortDropdown({ value, onChange }) {
  const sortOptions = [
    { value: 'search_frequency', label: '인기순' },
    { value: 'trend_rank', label: '신규순' },
    { value: 'created_at', label: '최신순' },
  ]

  return (
    <select
      className="sort-dropdown"
      value={value}
      onChange={(e) => onChange(e.target.value)}
      aria-label="Sort by"
    >
      {sortOptions.map((option) => (
        <option key={option.value} value={option.value}>
          {option.label}
        </option>
      ))}
    </select>
  )
}

export default SortDropdown
