import React, { useState, useEffect } from 'react'
import { categoryService } from '../services/categoryService'
import '../styles/CategoryFilter.css'

function CategoryFilter({
  selectedCategories,
  onCategoryToggle,
  onSelectAllCategories,
}) {
  const [categories, setCategories] = useState([])
  const [loading, setLoading] = useState(true)

  useEffect(() => {
    const fetchCategories = async () => {
      try {
        const response = await categoryService.getCategories()
        if (response.status === 'success') {
          setCategories(response.data || [])
        }
      } catch (error) {
        console.error('Error fetching categories:', error)
      } finally {
        setLoading(false)
      }
    }

    fetchCategories()
  }, [])

  if (loading) {
    return <div className="category-filter">카테고리 로딩 중...</div>
  }

  return (
    <div className="category-filter">
      <div className="category-buttons">
        {categories.map((category) => (
          <button
            key={category.id}
            className={`category-btn ${selectedCategories.has(category.name) ? 'active' : ''}`}
            style={{
              borderColor: selectedCategories.has(category.name) ? category.color : '#BDBDBD',
              backgroundColor: selectedCategories.has(category.name) ? category.color + '20' : 'transparent',
            }}
            onClick={() => onCategoryToggle(category.name)}
            aria-label={`Filter by ${category.name}`}
            title={category.name}
          >
            <span className="category-icon">{category.icon_emoji}</span>
            <span className="category-name">{category.name}</span>
          </button>
        ))}
      </div>

      <div className="category-actions">
        <button
          className="btn btn-small"
          onClick={onSelectAllCategories}
          aria-label={selectedCategories.size > 0 ? 'Deselect all' : 'Select all'}
        >
          {selectedCategories.size > 0 ? '전체 해제' : '전체 선택'}
        </button>
      </div>
    </div>
  )
}

export default CategoryFilter
