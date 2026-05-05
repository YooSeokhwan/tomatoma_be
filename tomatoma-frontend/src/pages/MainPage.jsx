import React, { useState, useEffect } from 'react'
import Header from '../components/Header'
import LeftPanel from '../components/LeftPanel'
import GoogleMapsComponent from '../components/GoogleMapsComponent'
import Footer from '../components/Footer'
import '../styles/MainPage.css'

function MainPage() {
  const [selectedFood, setSelectedFood] = useState(null)
  const [selectedCategories, setSelectedCategories] = useState(new Set())
  const [searchQuery, setSearchQuery] = useState('')
  const [sortBy, setSortBy] = useState('search_frequency')
  const [refreshKey, setRefreshKey] = useState(0)

  const handleCategoryToggle = (categoryName) => {
    const newCategories = new Set(selectedCategories)
    if (newCategories.has(categoryName)) {
      newCategories.delete(categoryName)
    } else {
      newCategories.add(categoryName)
    }
    setSelectedCategories(newCategories)
  }

  const handleSelectAllCategories = () => {
    if (selectedCategories.size > 0) {
      setSelectedCategories(new Set())
    }
  }

  const handleRefresh = () => {
    setRefreshKey((prev) => prev + 1)
  }

  return (
    <div className="main-page">
      <Header onRefresh={handleRefresh} />

      <div className="container">
        <LeftPanel
          selectedFood={selectedFood}
          onSelectFood={setSelectedFood}
          selectedCategories={selectedCategories}
          onCategoryToggle={handleCategoryToggle}
          onSelectAllCategories={handleSelectAllCategories}
          searchQuery={searchQuery}
          onSearchChange={setSearchQuery}
          sortBy={sortBy}
          onSortChange={setSortBy}
          refreshKey={refreshKey}
        />

        <GoogleMapsComponent
          selectedFood={selectedFood}
          selectedCategories={selectedCategories}
          refreshKey={refreshKey}
        />
      </div>

      <Footer />
    </div>
  )
}

export default MainPage
