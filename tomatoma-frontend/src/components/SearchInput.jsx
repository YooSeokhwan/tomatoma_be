import React from 'react'
import '../styles/SearchInput.css'

function SearchInput({ value, onChange }) {
  return (
    <div className="search-input-wrapper">
      <input
        type="text"
        className="search-input"
        placeholder="🔍 음식명을 입력하세요..."
        value={value}
        onChange={(e) => onChange(e.target.value)}
        aria-label="Search foods"
      />
    </div>
  )
}

export default SearchInput
