// src/components/InquiryForm.jsx
import { useState } from 'react'
import axios from 'axios'

function InquiryForm({ products = [], selectedService = '' }) {
    // form state
    const [name, setName] = useState('')
    const [email, setEmail] = useState('')
    // default to what the user clicked; otherwise empty
    const [service, setService] = useState(selectedService)
    const [details, setDetails] = useState('')
    const [message, setMessage] = useState('')

    // submit handler posts to Spring Boot
    async function handleSubmit(e) {
        e.preventDefault()
        setMessage('')

        try {
            await axios.post('http://localhost:8080/api/inquiries', {
                name,
                email,
                serviceInterestedIn: service,
                message: details,
            })
            setMessage('✅ Inquiry sent to the studio.')
            setName('')
            setEmail('')
            setService(selectedService || '')
            setDetails('')
        } catch (err) {
            console.error('Error submitting inquiry:', err)
            // message for when backend rejects due to auth/CORS
            setMessage('Could not send inquiry right now. Please try again.')
        }
    }

    return (
        <form
            onSubmit={handleSubmit}
            style={{
                background: '#fff',
                borderRadius: '1.4rem',
                padding: '1.9rem 1.6rem 1.6rem',
                maxWidth: '540px',
                border: '1px solid rgba(33,62,96,0.03)',
                boxShadow: '0 18px 42px rgba(0,0,0,0.025)',
            }}
        >
            <h3 style={{ marginBottom: '0.25rem' }}>Start a project inquiry</h3>
            <p style={{ marginBottom: '1.3rem', fontSize: '0.75rem', color: 'rgba(33,62,96,0.6)' }}>
                This posts to the Spring Boot API at <code>/api/inquiries</code>.
            </p>

            {/* name */}
            <label style={labelStyle}>
                Name
                <input
                    style={inputStyle}
                    value={name}
                    onChange={e => setName(e.target.value)}
                    required
                    placeholder="Jaha Knight"
                />
            </label>

            {/* email */}
            <label style={labelStyle}>
                Email
                <input
                    type="email"
                    style={inputStyle}
                    value={email}
                    onChange={e => setEmail(e.target.value)}
                    required
                    placeholder="you@example.com"
                />
            </label>

            {/* service interested in — dropdown from products */}
            <label style={labelStyle}>
                Service interested in
                <select
                    style={inputStyle}
                    value={service}
                    onChange={e => setService(e.target.value)}
                    required
                >
                    <option value="">Select a service…</option>
                    {products.map(p => (
                        <option key={p.id} value={p.name}>
                            {p.name}
                        </option>
                    ))}
                </select>
            </label>

            {/* project details */}
            <label style={labelStyle}>
                Project details
                <textarea
                    style={{ ...inputStyle, minHeight: '110px', resize: 'vertical' }}
                    value={details}
                    onChange={e => setDetails(e.target.value)}
                    placeholder="need help with logo work"
                />
            </label>

            <button
                type="submit"
                style={{
                    background: '#213E60',
                    color: '#fff',
                    border: 'none',
                    padding: '0.6rem 1.35rem',
                    borderRadius: '999px',
                    fontWeight: 500,
                    cursor: 'pointer',
                }}
            >
                Submit inquiry
            </button>

            {message && (
                <p style={{ marginTop: '0.6rem', fontSize: '0.7rem', color: '#C05621' }}>{message}</p>
            )}
        </form>
    )
}

const labelStyle = {
    display: 'block',
    fontSize: '0.73rem',
    marginBottom: '0.65rem',
    color: '#213E60',
}

const inputStyle = {
    width: '100%',
    marginTop: '0.35rem',
    border: '1px solid rgba(33,62,96,0.1)',
    borderRadius: '0.8rem',
    padding: '0.5rem 0.65rem',
    fontSize: '0.78rem',
    outline: 'none',
}

export default InquiryForm





