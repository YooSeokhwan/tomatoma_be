/**
 * Create custom marker icon for Google Maps
 */
export const createMarkerIcon = (color = '#FF5252') => {
  return {
    path: 'M 0,-25 Q 25,-52 25,-25 Q 25,0 0,25 Q -25,0 -25,-25 Q -25,-52 0,-25 Z',
    fillColor: color,
    fillOpacity: 1,
    strokeColor: '#FFFFFF',
    strokeWeight: 2,
    scale: 1,
  }
}

/**
 * Get color for category
 */
export const getCategoryColor = (category, categoryMap) => {
  return categoryMap?.[category]?.color || '#999999'
}

/**
 * Calculate bounds for all markers
 */
export const calculateBounds = (places) => {
  if (!places || places.length === 0) return null

  let north = places[0].latitude
  let south = places[0].latitude
  let east = places[0].longitude
  let west = places[0].longitude

  places.forEach((place) => {
    north = Math.max(north, place.latitude)
    south = Math.min(south, place.latitude)
    east = Math.max(east, place.longitude)
    west = Math.min(west, place.longitude)
  })

  return {
    north,
    south,
    east,
    west,
  }
}

/**
 * Format distance in meters/km
 */
export const formatDistance = (meters) => {
  if (meters < 1000) {
    return Math.round(meters) + 'm'
  }
  return (meters / 1000).toFixed(1) + 'km'
}

/**
 * Calculate distance between two points (Haversine formula)
 */
export const calculateDistance = (lat1, lon1, lat2, lon2) => {
  const R = 6371000 // Earth's radius in meters
  const dLat = ((lat2 - lat1) * Math.PI) / 180
  const dLon = ((lon2 - lon1) * Math.PI) / 180
  const a =
    Math.sin(dLat / 2) * Math.sin(dLat / 2) +
    Math.cos((lat1 * Math.PI) / 180) *
      Math.cos((lat2 * Math.PI) / 180) *
      Math.sin(dLon / 2) *
      Math.sin(dLon / 2)
  const c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a))
  return R * c
}
