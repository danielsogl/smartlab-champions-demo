import { afterEach, describe, expect, it, vi } from 'vitest'
import { flushPromises, mount } from '@vue/test-utils'
import ChargePointDetail from '../ChargePointDetail.vue'
import type { ChargePoint } from '@/api'

function mockApi(chargePoint: ChargePoint) {
  const fetchMock = vi.fn(async (_url: string, init?: RequestInit) =>
    init?.method === 'POST'
      ? new Response(JSON.stringify({ sessionId: 'session-1' }), { status: 202 })
      : new Response(JSON.stringify(chargePoint), { status: 200 }),
  )
  vi.stubGlobal('fetch', fetchMock)
  return fetchMock
}

async function mountDetail(chargePoint: ChargePoint) {
  mockApi(chargePoint)
  const wrapper = mount(ChargePointDetail, { props: { id: chargePoint.id } })
  await flushPromises()
  return wrapper
}

const available: ChargePoint = { id: 'CP-1', location: 'Hamburg', status: 'AVAILABLE' }

describe('ChargePointDetail', () => {
  afterEach(() => vi.unstubAllGlobals())

  it('shows id, location and status', async () => {
    const wrapper = await mountDetail(available)

    expect(wrapper.text()).toContain('CP-1')
    expect(wrapper.text()).toContain('Hamburg')
    expect(wrapper.text()).toContain('AVAILABLE')
  })

  it('starts a session on click', async () => {
    const wrapper = await mountDetail(available)

    await wrapper.get('button').trigger('click')
    await flushPromises()

    expect(fetch).toHaveBeenCalledWith('/api/charge-points/CP-1/remote-start', { method: 'POST' })
    expect(wrapper.get('[role=status]').text()).toContain('Session started')
  })

  it('AC3: remote start is disabled in maintenance', async () => {
    const wrapper = await mountDetail({ id: 'CP-7', location: 'München', status: 'IN_MAINTENANCE' })

    expect(wrapper.get('button').attributes()).toHaveProperty('disabled')
    expect(wrapper.text()).toContain('In maintenance')
  })
})
