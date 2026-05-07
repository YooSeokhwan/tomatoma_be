import React from 'react'
import { formatPrice, formatRating } from '../utils/formatUtils'
import '../styles/PlaceDetailPopup.css'

function PlaceDetailPopup({ place }) {
  const handleCall = () => {
    if (place.phone) {
      window.location.href = `tel:${place.phone}`
    }
  }

  const handleWebsite = () => {
    if (place.websiteUrl) {
      try {
        const url = new URL(place.websiteUrl)
        const isAllowedProtocol = url.protocol === 'http:' || url.protocol === 'https:'
        const isInternalHost = /^(localhost|127\.|10\.|192\.168\.|172\.(1[6-9]|2\d|3[01])\.)/.test(url.hostname)
        if (isAllowedProtocol && !isInternalHost) {
          window.open(place.websiteUrl, '_blank', 'noopener,noreferrer')
        }
      } catch {
        // 유효하지 않은 URL
      }
    }
  }

  const handleDirections = () => {
    const mapsUrl = `https://www.google.com/maps/search/?api=1&query=${place.latitude},${place.longitude}`
    window.open(mapsUrl, '_blank', 'noopener,noreferrer')
  }

  return (
    <div className="place-detail-popup">
      <h3 className="place-name">{place.name}</h3>

      <div className="place-info">
        {place.latitude && place.longitude && (
          <div className="info-row">
            <span className="label">📍</span>
            <span className="value">{place.address}</span>
          </div>
        )}

        {place.phone && (
          <div className="info-row">
            <span className="label">☎️</span>
            <span className="value">{place.phone}</span>
          </div>
        )}

        {place.websiteUrl && (
          <div className="info-row">
            <span className="label">🌐</span>
            <span className="value">{place.websiteUrl}</span>
          </div>
        )}

        {place.rating && (
          <div className="info-row">
            <span className="label">⭐</span>
            <span className="value">{place.rating.toFixed(1)}</span>
          </div>
        )}

        {place.priceApprox && (
          <div className="info-row">
            <span className="label">💰</span>
            <span className="value">{formatPrice(place.priceApprox)}</span>
          </div>
        )}

        {place.operatingHours && (
          <div className="info-row">
            <span className="label">🕒</span>
            <span className="value">{place.operatingHours}</span>
          </div>
        )}
      </div>

      <div className="place-actions">
        {place.phone && (
          <button className="btn btn-small" onClick={handleCall} title="전화 걸기">
            전화
          </button>
        )}
        {place.websiteUrl && (
          <button className="btn btn-small" onClick={handleWebsite} title="웹사이트 방문">
            웹사이트
          </button>
        )}
        <button className="btn btn-small" onClick={handleDirections} title="경로 안내">
          경로안내
        </button>
      </div>
    </div>
  )
}

export default PlaceDetailPopup
