import React from 'react'
import '../styles/LoadingSpinner.css'

function LoadingSpinner({ message = '로딩 중...' }) {
  return (
    <div className="loading-spinner-wrapper">
      <div className="loading-spinner"></div>
      <p>{message}</p>
    </div>
  )
}

export default LoadingSpinner
