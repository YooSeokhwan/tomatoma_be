import React from 'react'
import '../styles/Header.css'

function Header({ onRefresh }) {
  return (
    <header className="header">
      <div className="header-content">
        <div className="logo-section">
          <span className="logo">🍅</span>
          <h1>토마토마</h1>
        </div>

        <h2 className="subtitle">트렌드 음식 판매처 찾기</h2>

        <div className="header-actions">
          <button
            className="btn btn-icon"
            onClick={onRefresh}
            title="새로고침"
            aria-label="Refresh trending foods"
          >
            ↻
          </button>
          <button
            className="btn btn-icon"
            title="도움말"
            aria-label="Help"
          >
            ?
          </button>
          <button
            className="btn btn-icon"
            title="설정"
            aria-label="Settings"
          >
            ⚙️
          </button>
        </div>
      </div>
    </header>
  )
}

export default Header
