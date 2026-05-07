import { describe, it, expect, vi, beforeEach } from 'vitest'
import { render, screen, fireEvent } from '@testing-library/react'
import PlaceDetailPopup from '../components/PlaceDetailPopup'

const mockPlace = {
  name: '마라탕 맛집',
  address: '서울특별시 성동구 뚝섬로 1길 25',
  latitude: 37.553,
  longitude: 127.070,
  phone: '02-1234-5678',
  websiteUrl: 'https://example.com',
  rating: 4.5,
  priceApprox: 15000,
  operatingHours: '11:00 - 22:00',
}

describe('PlaceDetailPopup', () => {
  it('장소 이름을 표시', () => {
    render(<PlaceDetailPopup place={mockPlace} />)
    expect(screen.getByText('마라탕 맛집')).toBeInTheDocument()
  })

  it('주소를 표시', () => {
    render(<PlaceDetailPopup place={mockPlace} />)
    expect(screen.getByText('서울특별시 성동구 뚝섬로 1길 25')).toBeInTheDocument()
  })

  it('전화번호가 있으면 전화 버튼 표시', () => {
    render(<PlaceDetailPopup place={mockPlace} />)
    expect(screen.getByRole('button', { name: /전화/ })).toBeInTheDocument()
  })

  it('전화번호가 없으면 전화 버튼 미표시', () => {
    render(<PlaceDetailPopup place={{ ...mockPlace, phone: null }} />)
    expect(screen.queryByRole('button', { name: /전화/ })).toBeNull()
  })

  it('웹사이트 URL이 있으면 웹사이트 버튼 표시', () => {
    render(<PlaceDetailPopup place={mockPlace} />)
    expect(screen.getByRole('button', { name: /웹사이트/ })).toBeInTheDocument()
  })

  it('경로안내 버튼은 항상 표시', () => {
    render(<PlaceDetailPopup place={mockPlace} />)
    expect(screen.getByRole('button', { name: /경로안내/ })).toBeInTheDocument()
  })

  describe('웹사이트 URL 보안', () => {
    beforeEach(() => {
      vi.stubGlobal('open', vi.fn())
    })

    it('https URL은 새 탭에서 열림', () => {
      render(<PlaceDetailPopup place={mockPlace} />)
      fireEvent.click(screen.getByRole('button', { name: /웹사이트/ }))
      expect(window.open).toHaveBeenCalledWith('https://example.com', '_blank', 'noopener,noreferrer')
    })

    it('localhost URL은 차단', () => {
      const place = { ...mockPlace, websiteUrl: 'http://localhost/admin' }
      render(<PlaceDetailPopup place={place} />)
      fireEvent.click(screen.getByRole('button', { name: /웹사이트/ }))
      expect(window.open).not.toHaveBeenCalled()
    })

    it('내부 IP(127.x)는 차단', () => {
      const place = { ...mockPlace, websiteUrl: 'http://127.0.0.1/secret' }
      render(<PlaceDetailPopup place={place} />)
      fireEvent.click(screen.getByRole('button', { name: /웹사이트/ }))
      expect(window.open).not.toHaveBeenCalled()
    })

    it('내부 IP(192.168.x)는 차단', () => {
      const place = { ...mockPlace, websiteUrl: 'http://192.168.1.1/' }
      render(<PlaceDetailPopup place={place} />)
      fireEvent.click(screen.getByRole('button', { name: /웹사이트/ }))
      expect(window.open).not.toHaveBeenCalled()
    })

    it('javascript: 프로토콜은 차단', () => {
      const place = { ...mockPlace, websiteUrl: 'javascript:alert(1)' }
      render(<PlaceDetailPopup place={place} />)
      fireEvent.click(screen.getByRole('button', { name: /웹사이트/ }))
      expect(window.open).not.toHaveBeenCalled()
    })

    it('유효하지 않은 URL은 차단', () => {
      const place = { ...mockPlace, websiteUrl: 'not-a-url' }
      render(<PlaceDetailPopup place={place} />)
      fireEvent.click(screen.getByRole('button', { name: /웹사이트/ }))
      expect(window.open).not.toHaveBeenCalled()
    })
  })
})
