import { Routes, Route, Link } from 'react-router-dom'
import { useEffect, useState } from 'react'
import heroVisual from './assets/hero-visual.jpeg'
import { fetchProducts } from './api/apiClient'

function App() {
  return (
    <div
      style={{
        minHeight: '100vh',
        background: '#F4F2EF',
        color: '#213E60',
        display: 'flex',
        flexDirection: 'column',
      }}
    >
      <Header />

      <main
        style={{
          maxWidth: '1180px',
          margin: '0 auto',
          padding: '2.5rem 1.25rem 4rem',
          width: '100%',
          flex: 1,
        }}
      >
        <Routes>
          <Route path="/" element={<Home />} />
          <Route path="/services" element={<Services />} />
          <Route path="/admin" element={<Admin />} />
        </Routes>
      </main>

      <Footer />
    </div>
  )
}

/* ================= HEADER ================= */
function Header() {
  return (
    <header
      style={{
        width: '100%',
        background: '#F4F2EF',
        borderBottom: '1px solid rgba(33,62,96,0.05)',
        position: 'sticky',
        top: 0,
        zIndex: 10,
      }}
    >
      <div
        style={{
          maxWidth: '1180px',
          margin: '0 auto',
          padding: '1.05rem 1.25rem',
          display: 'flex',
          justifyContent: 'space-between',
          alignItems: 'center',
        }}
      >
        <div style={{ fontWeight: 700, letterSpacing: '0.08em', fontSize: '1.03rem' }}>
          Heshima Studio
        </div>

        {/* nav links */}
        <nav style={{ display: 'flex', gap: '1.4rem' }}>
          <NavLink to="/">Home</NavLink>
          <NavLink to="/services">Services</NavLink>
          <NavLink to="/admin">Admin</NavLink>
        </nav>
      </div>
    </header>
  )
}

function NavLink({ to, children }) {
  return (
    <Link
      to={to}
      style={{
        textDecoration: 'none',
        color: '#213E60',
        fontSize: '0.9rem',
        fontWeight: 500,
        paddingBottom: '0.35rem',
        borderBottom: '2px solid transparent',
      }}
      onMouseEnter={e => (e.currentTarget.style.borderBottom = '2px solid #213E60')}
      onMouseLeave={e => (e.currentTarget.style.borderBottom = '2px solid transparent')}
    >
      {children}
    </Link>
  )
}

/* ================= HOME ================= */
function Home() {
  return (
    // hero wrapper
    <section
      style={{
        display: 'flex',
        gap: '3.25rem',
        alignItems: 'center',
        justifyContent: 'space-between',
        minHeight: '420px',
      }}
    >
      {/* left column */}
      <div style={{ maxWidth: '470px' }}>
        <p
          style={{
            textTransform: 'uppercase',
            fontSize: '0.68rem',
            letterSpacing: '0.28em',
            marginBottom: '0.8rem',
            color: 'rgba(33,62,96,0.6)',
          }}
        >
          Design with Dignity
        </p>

        <h1
          style={{
            fontSize: '2.7rem',
            lineHeight: 1.02,
            marginBottom: '1.2rem',
            fontWeight: 700,
          }}
        >
          Modern digital experiences for creative brands.
        </h1>

        <p
          style={{
            lineHeight: 1.6,
            marginBottom: '1.8rem',
            color: 'rgba(33,62,96,0.83)',
          }}
        >
          This frontend will talk to the Spring Boot API — products, inquiries, and admin tools.
          Then it will be wired up with real data using Axios.
        </p>

        {/* buttons */}
        <div style={{ display: 'flex', gap: '1rem', flexWrap: 'wrap' }}>
          <Link to="/services" style={primaryButton}>
            View Services
          </Link>
          <Link to="/admin" style={ghostButton}>
            Admin
          </Link>
        </div>
      </div>

      {/* right column — hero visual */}
      <div
        style={{
          width: '400px',
          background: '#E68C3A',
          borderRadius: '1.6rem',
          padding: '1.1rem',
          boxShadow: '0 22px 60px rgba(0,0,0,0.06)',
        }}
      >
        <img
          src={heroVisual}
          alt="Heshima Studio mobile UI mockups"
          style={{
            width: '100%',
            display: 'block',
            borderRadius: '1.2rem',
            background: '#fff',
          }}
        />
      </div>
    </section>
  )
}

const primaryButton = {
  background: '#E68C3A',
  color: '#fff',
  padding: '0.6rem 1.3rem',
  borderRadius: '999px',
  textDecoration: 'none',
  fontWeight: 600,
  border: 'none',
  display: 'inline-flex',
  alignItems: 'center',
  gap: '0.35rem',
}

const ghostButton = {
  border: '1px solid #213E60',
  color: '#213E60',
  padding: '0.6rem 1.3rem',
  borderRadius: '999px',
  textDecoration: 'none',
  fontWeight: 500,
  background: 'transparent',
}

/* ================= SERVICES ================= */
function Services() {
  console.log('Loading services…')

  // load products from the Spring Boot API
  const [products, setProducts] = useState([])
  const [loading, setLoading] = useState(true)
  const [error, setError] = useState('')

  useEffect(() => {
    async function load() {
      console.log('Loading services...')
      try {
        const data = await fetchProducts()
        console.log('✅ API data from /api/products:', data)
        setProducts(data)
      } catch (err) {
        console.error('❌ Error loading services:', err)
        // show a friendly message if the backend is not running
        setError('Could not load services from the API. Showing placeholder services instead.')
      } finally {
        setLoading(false)
        console.log('✅ Done loading services.')
      }
    }

    load()
  }, [])

  // placeholder services so the page never looks empty
  const fallbackServices = [
    { id: '1', name: 'Branding', basePrice: 750, description: 'Visual identity and brand kit.' },
    {
      id: '2',
      name: 'Web Design',
      basePrice: 1200,
      description: 'Responsive marketing website.',
    },
    {
      id: '3',
      name: 'UX / UI',
      basePrice: 950,
      description: 'Interface design for dashboards.',
    },
  ]

  // Prefer real API products when they exist.
  // If API returned nothing (or failed), show the static placeholder services
  // so the UI still explains what the studio offers.
  const listToRender = products.length > 0 ? products : fallbackServices

  return (
    <section style={{ paddingTop: '2.5rem' }}>
      <h2 style={{ fontSize: '1.8rem', marginBottom: '1rem' }}>Studio Services</h2>
      <p style={{ marginBottom: '1.5rem', maxWidth: '540px' }}>
        These come from <code>/api/products</code> on the Spring Boot backend. If the backend is not
        running, the page will show placeholder services.
      </p>

      {loading ? (
        <p style={{ color: 'rgba(33,62,96,0.6)' }}>Loading services…</p>
      ) : (
        <div
          style={{
            display: 'grid',
            gridTemplateColumns: 'repeat(auto-fit, minmax(260px, 1fr))',
            gap: '1.25rem',
          }}
        >
          {listToRender.map(service => (
            <ServiceCard
              key={service.id}
              title={service.name}
              price={
                service.basePrice !== undefined
                  ? `$${Number(service.basePrice).toFixed(2)}`
                  : '$0.00'
              }
              desc={service.description || 'Studio service.'}
            />
          ))}
        </div>
      )}

      {error && (
        <p style={{ marginTop: '1rem', fontSize: '0.75rem', color: '#C05621' }}>
          {error}
        </p>
      )}
    </section>
  )
}

function ServiceCard({ title, price, desc }) {
  return (
    <div
      style={{
        background: '#fff',
        borderRadius: '1rem',
        padding: '1.25rem 1.1rem',
        border: '1px solid rgba(33,62,96,0.03)',
        boxShadow: '0 10px 28px rgba(0,0,0,0.025)',
        transition: 'transform 140ms ease-out, box-shadow 140ms ease-out',
      }}
      onMouseEnter={e => {
        e.currentTarget.style.transform = 'translateY(-3px)'
        e.currentTarget.style.boxShadow = '0 16px 38px rgba(0,0,0,0.04)'
      }}
      onMouseLeave={e => {
        e.currentTarget.style.transform = 'translateY(0)'
        e.currentTarget.style.boxShadow = '0 10px 28px rgba(0,0,0,0.025)'
      }}
    >
      <h3 style={{ marginBottom: '0.3rem', fontSize: '1.05rem' }}>{title}</h3>
      <p style={{ marginBottom: '0.6rem', color: '#E68C3A', fontWeight: 600 }}>{price}</p>
      <p style={{ fontSize: '0.82rem', lineHeight: 1.4, marginBottom: '0.85rem' }}>{desc}</p>
      <button
        style={{
          background: '#213E60',
          color: '#fff',
          border: 'none',
          padding: '0.45rem 0.9rem',
          borderRadius: '999px',
          fontSize: '0.75rem',
          cursor: 'pointer',
          transition: 'transform 120ms ease-out',
        }}
        onMouseEnter={e => (e.currentTarget.style.transform = 'scale(1.03)')}
        onMouseLeave={e => (e.currentTarget.style.transform = 'scale(1)')}
      >
        Inquire
      </button>
    </div>
  )
}

/* ================= ADMIN ================= */
function Admin() {
  return (
    <section style={{ maxWidth: '540px' }}>
      <h2 style={{ fontSize: '1.7rem', marginBottom: '0.75rem' }}>Admin Dashboard</h2>
      <p style={{ lineHeight: 1.5 }}>
        This will display inquiries from <code>/api/inquiries</code> same endpoint tested in Postman
        with Basic Auth. Later the frontend will add Axios + auth header and render a table here.
      </p>
    </section>
  )
}

/* ================= FOOTER ================= */
function Footer() {
  return (
    <footer
      style={{
        borderTop: '1px solid rgba(33,62,96,0.05)',
        padding: '1.25rem',
        textAlign: 'center',
        fontSize: '0.7rem',
        color: 'rgba(33,62,96,0.6)',
      }}
    >
      © {new Date().getFullYear()} Heshima Studio — Design with Dignity.
    </footer>
  )
}

export default App