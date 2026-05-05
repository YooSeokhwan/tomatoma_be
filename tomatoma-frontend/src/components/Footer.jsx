import React, { useState, useEffect } from 'react'
import '../styles/Footer.css'

function Footer() {
  const [lastUpdate, setLastUpdate] = useState(new Date())

  useEffect(() => {
    setLastUpdate(new Date())
  }, [])

  const formattedDate = lastUpdate.toLocaleDateString('ko-KR', {
    year: 'numeric',
    month: '2-digit',
    day: '2-digit',
    hour: '2-digit',
    minute: '2-digit',
  })

  return (
    <footer className="footer">
      <div className="footer-content">
        <p>© 2026 토마토마 | 트렌드 업데이트: {formattedDate}</p>
      </div>
    </footer>
  )
}

export default Footer
