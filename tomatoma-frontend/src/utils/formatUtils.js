/**
 * Format price to Korean Won
 */
export const formatPrice = (price) => {
  if (!price) return '가격 정보 없음'
  return '₩' + new Intl.NumberFormat('ko-KR').format(price)
}

/**
 * Format rating (0.0 - 5.0)
 */
export const formatRating = (rating) => {
  if (!rating) return '별점 없음'
  return `⭐ ${rating.toFixed(1)}`
}

/**
 * Format date
 */
export const formatDate = (dateString) => {
  if (!dateString) return ''
  const date = new Date(dateString)
  return new Intl.DateTimeFormat('ko-KR', {
    year: 'numeric',
    month: '2-digit',
    day: '2-digit',
    hour: '2-digit',
    minute: '2-digit',
  }).format(date)
}

/**
 * Format frequency as bar chart
 */
export const formatFrequencyBar = (frequency, maxFrequency = 10000) => {
  const percentage = (frequency / maxFrequency) * 100
  return {
    width: Math.min(percentage, 100) + '%',
    percentage: Math.round(percentage),
  }
}

/**
 * Truncate text
 */
export const truncateText = (text, maxLength = 50) => {
  if (!text) return ''
  return text.length > maxLength ? text.substring(0, maxLength) + '...' : text
}
