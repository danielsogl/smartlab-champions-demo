export type ChargePointStatus = 'AVAILABLE' | 'CHARGING' | 'OFFLINE' | 'IN_MAINTENANCE' | 'UNKNOWN'

export interface ChargePoint {
  id: string
  location: string
  status: ChargePointStatus
}

export class ApiError extends Error {
  constructor(readonly code: string) {
    super(code)
  }
}

async function request<T>(path: string, init?: RequestInit): Promise<T> {
  const res = await fetch(`/api${path}`, init)
  const body = await res.json()
  if (!res.ok) throw new ApiError(body.code ?? `HTTP_${res.status}`)
  return body as T
}

export const listChargePoints = () => request<ChargePoint[]>('/charge-points')

export const getChargePoint = (id: string) => request<ChargePoint>(`/charge-points/${id}`)

export const requestRemoteStart = (id: string) =>
  request<{ sessionId: string }>(`/charge-points/${id}/remote-start`, { method: 'POST' })
