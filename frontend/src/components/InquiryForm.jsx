import { useState } from 'react'
import { submitInquiry } from '../api/apiClient'

function InquiryForm({ products = [], selectedService = '' }) {
  const [name, setName] = useState('')
  const [email, setEmail] = useState('')
  // start empty so placeholder shows
  const [message, setMessage] = useState('')
  const [serviceId, setServiceId] = useState(() => {
    if (selectedService && products.length > 0) {
      const match = products.find(p => p.name === selectedService)
      return match ? match.id : ''
    }
    return ''
  })

  const [submitting, setSubmitting] = useState(false)
  const [success, setSuccess] = useState('')
  const [error, setError] = useState('')

  async function handleSubmit(e) {
    e.preventDefault()
    setSubmitting(true)
    setSuccess('')
    setError('')

    try {
      const payload = {
        productId: Number(serviceId),
        name,
        email,
        message,
      }

      const response = await submitInquiry(payload)
      console.log('✅ Inquiry created:', response)

      setSuccess('Inquiry received — Heshima Studio will follow up shortly.')
      setName('')
      setEmail('')
      setMessage('')       
      setServiceId('')
    } catch (err) {
      console.error('❌ Error submitting inquiry:', err)
      setError('Something went wrong sending your inquiry. Please try again or contact the studio directly.')
    } finally {
      setSubmitting(false)
    }
  }

  return (
    <div
      style={{
        background: '#fff',
        borderRadius: '1rem',
        padding: '1.5rem 1.25rem 1.75rem',
        boxShadow: '0 10px 26px rgba(0,0,0,0.03)',
        border: '1px solid rgba(33,62,96,0.03)',
      }}
    >
      <h3 style={{ marginBottom: '0.4rem', fontSize: '1.15rem', fontWeight: 700, color: '#213E60' }}>
        Let's dignify your brand.
      </h3>
      <p style={{ marginBottom: '1.2rem', fontSize: '0.8rem', color: 'rgba(33,62,96,0.6)' }}>
        Share what you need and we'll match it with the right Heshima Studio service.
      </p>

      <form onSubmit={handleSubmit} style={{ display: 'flex', flexDirection: 'column', gap: '0.75rem' }}>
        <label style={labelStyle}>
          Service you're interested in
          <select
            value={serviceId}
            onChange={e => setServiceId(e.target.value)}
            style={inputStyle}
            required
          >
            <option value="">Select a service</option>
            {products.map(prod => (
              <option key={prod.id} value={prod.id}>
                {prod.name}
              </option>
            ))}
          </select>
        </label>

        <label style={labelStyle}>
          First name, Last name
          <input
            type="text"
            value={name}
            onChange={e => setName(e.target.value)}
            placeholder="e.g. Jaha Knight"
            style={inputStyle}
            required
          />
        </label>

        <label style={labelStyle}>
          Contact email
          <input
            type="email"
            value={email}
            onChange={e => setEmail(e.target.value)}
            placeholder="you@example.com"
            style={inputStyle}
            required
          />
        </label>

        <label style={labelStyle}>
          Project details
          <textarea
            value={message}
            onChange={e => setMessage(e.target.value)}
            rows={4}
            style={{ ...inputStyle, resize: 'vertical' }}
            placeholder="Tell us what you’re building, timeline, and any links."
          />
        </label>

        <div style={{ textAlign: 'center', marginTop: '0.4rem' }}>
          <button
            type="submit"
            disabled={submitting}
            style={{
              background: '#213E60',
              color: '#fff',
              border: 'none',
              borderRadius: '999px',
              padding: '0.55rem 1.4rem',
              fontWeight: 600,
              cursor: submitting ? 'not-allowed' : 'pointer',
              opacity: submitting ? 0.65 : 1,
            }}
          >
            {submitting ? 'Sending…' : 'Submit inquiry'}
          </button>
        </div>
      </form>

      {success && (
        <p style={{ marginTop: '1rem', fontSize: '0.75rem', color: '#21725a' }}>
          {success}
        </p>
      )}
      {error && (
        <p style={{ marginTop: '1rem', fontSize: '0.75rem', color: '#C05621' }}>
          {error}
        </p>
      )}
    </div>
  )
}

const labelStyle = {
  display: 'flex',
  flexDirection: 'column',
  gap: '0.35rem',
  fontSize: '0.75rem',
  color: '#213E60',
  fontWeight: 500,
}

const inputStyle = {
  border: '1px solid rgba(33,62,96,0.15)',
  borderRadius: '0.5rem',
  padding: '0.5rem 0.55rem',
  fontSize: '0.78rem',
  outline: 'none',
}

export default InquiryForm





