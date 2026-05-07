import { describe, it, expect } from 'vitest'
import { formatPrice, formatRating, formatFrequencyBar, truncateText, formatDate } from '../utils/formatUtils'

describe('formatPrice', () => {
  it('null/0이면 가격 정보 없음 반환', () => {
    expect(formatPrice(null)).toBe('가격 정보 없음')
    expect(formatPrice(0)).toBe('가격 정보 없음')
  })

  it('숫자를 원화 형식으로 포맷', () => {
    expect(formatPrice(12000)).toBe('₩12,000')
    expect(formatPrice(1000000)).toBe('₩1,000,000')
  })
})

describe('formatRating', () => {
  it('null/0이면 별점 없음 반환', () => {
    expect(formatRating(null)).toBe('별점 없음')
    expect(formatRating(0)).toBe('별점 없음')
  })

  it('소수점 1자리로 포맷', () => {
    expect(formatRating(4.5)).toBe('⭐ 4.5')
    expect(formatRating(3)).toBe('⭐ 3.0')
  })
})

describe('formatFrequencyBar', () => {
  it('빈도를 퍼센트 너비로 변환', () => {
    expect(formatFrequencyBar(5000, 10000)).toEqual({ width: '50%', percentage: 50 })
    expect(formatFrequencyBar(1000, 10000)).toEqual({ width: '10%', percentage: 10 })
  })

  it('100%를 초과하지 않음', () => {
    expect(formatFrequencyBar(20000, 10000).width).toBe('100%')
  })

  it('기본 maxFrequency는 10000', () => {
    expect(formatFrequencyBar(10000)).toEqual({ width: '100%', percentage: 100 })
  })
})

describe('truncateText', () => {
  it('null/빈 문자열이면 빈 문자열 반환', () => {
    expect(truncateText(null)).toBe('')
    expect(truncateText('')).toBe('')
  })

  it('최대 길이 이하면 그대로 반환', () => {
    expect(truncateText('짧은 텍스트', 50)).toBe('짧은 텍스트')
  })

  it('최대 길이 초과하면 ... 추가', () => {
    const long = 'a'.repeat(60)
    const result = truncateText(long, 50)
    expect(result).toBe('a'.repeat(50) + '...')
    expect(result.length).toBe(53)
  })
})

describe('formatDate', () => {
  it('null/빈 값이면 빈 문자열 반환', () => {
    expect(formatDate(null)).toBe('')
    expect(formatDate('')).toBe('')
  })

  it('날짜 문자열을 한국어 형식으로 포맷', () => {
    const result = formatDate('2026-05-07T14:00:00')
    expect(result).toContain('2026')
    expect(result).toContain('05')
    expect(result).toContain('07')
  })
})
