import { describe, it, expect, vi } from 'vitest'
import { render, screen, fireEvent } from '@testing-library/react'
import TrendFoodCard from '../components/TrendFoodCard'

const mockFood = {
  id: 1,
  name: '마라탕',
  category: '중식',
  color: '#FF5252',
  searchFrequency: 500,
  trendRank: 1,
  placeCount: 21,
}

describe('TrendFoodCard', () => {
  it('food가 null이면 아무것도 렌더링하지 않음', () => {
    const { container } = render(<TrendFoodCard food={null} onSelect={() => {}} />)
    expect(container.firstChild).toBeNull()
  })

  it('음식 이름, 카테고리, 랭크를 표시', () => {
    render(<TrendFoodCard food={mockFood} onSelect={() => {}} />)
    expect(screen.getByText('마라탕')).toBeInTheDocument()
    expect(screen.getByText('중식')).toBeInTheDocument()
    expect(screen.getByText('#1')).toBeInTheDocument()
  })

  it('판매처 수를 표시', () => {
    render(<TrendFoodCard food={mockFood} onSelect={() => {}} />)
    expect(screen.getByText('판매처: 21곳')).toBeInTheDocument()
  })

  it('카드 클릭 시 onSelect 호출', () => {
    const onSelect = vi.fn()
    render(<TrendFoodCard food={mockFood} onSelect={onSelect} />)
    fireEvent.click(screen.getByRole('button', { name: /마라탕/ }))
    expect(onSelect).toHaveBeenCalledTimes(1)
  })

  it('선택된 상태일 때 selected 클래스 적용', () => {
    render(<TrendFoodCard food={mockFood} isSelected={true} onSelect={() => {}} />)
    expect(screen.getByRole('button', { name: /마라탕/ })).toHaveClass('selected')
  })

  it('선택되지 않은 상태일 때 selected 클래스 없음', () => {
    render(<TrendFoodCard food={mockFood} isSelected={false} onSelect={() => {}} />)
    expect(screen.getByRole('button', { name: /마라탕/ })).not.toHaveClass('selected')
  })

  it('placeCount가 없을 때 0곳으로 표시', () => {
    const foodWithoutCount = { ...mockFood, placeCount: undefined }
    render(<TrendFoodCard food={foodWithoutCount} onSelect={() => {}} />)
    expect(screen.getByText('판매처: 0곳')).toBeInTheDocument()
  })

  it('Enter 키로 onSelect 호출', () => {
    const onSelect = vi.fn()
    render(<TrendFoodCard food={mockFood} onSelect={onSelect} />)
    fireEvent.keyPress(screen.getByRole('button', { name: /마라탕/ }), { key: 'Enter', code: 'Enter', charCode: 13 })
    expect(onSelect).toHaveBeenCalledTimes(1)
  })
})
