import { Routes, Route, Link, useLocation, useNavigate } from 'react-router-dom'
import { useState, useEffect } from 'react'
import heroVisual from './assets/hero-visual.jpeg'
import { fetchProducts } from './api/apiClient'
import InquiryForm from './components/InquiryForm.jsx'
import Cart from './components/Cart.jsx'
import { formatUSD } from './utils/moneyUtils'

function App() {
  const location = useLocation()
  const navigate = useNavigate()

  // only send people to "/" if they refreshed on another route (for demo)
  // NOTE: temporarily disabled so clicking between routes doesn't get pulled back to "/"
  /*
  useEffect(() => {
    const navEntries = performance.getEntriesByType('navigation')
    const navType = navEntries[0]?.type
    const legacyType = performance.navigation?.type // 1 == reload

    const didReload = navType === 'reload' || legacyType === 1

    if (didReload && location.pathname !== '/') {
      navigate('/', { replace: true })
    }
  }, [location.pathname, navigate])
  */

  // simple in-memory cart for services the user wants to scope
  const [cartItems, setCartItems] = useState([])

  // add a service to the cart, but avoid duplicates
  const handleAddToCart = service => {
    setCartItems(prev => {
      const alreadyIn = prev.some(item => item.id === service.id)
      if (alreadyIn) return prev
      return [...prev, service]
    })
  }

  // remove a single service from the cart
  const handleRemoveFromCart = serviceId => {
    setCartItems(prev => prev.filter(item => item.id !== serviceId))
  }

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
          <Route
            path="/services"
            element={<Services onAddToCart={handleAddToCart} />}
          />
          <Route
            path="/cart"
            element={<Cart items={cartItems} onRemove={handleRemoveFromCart} />}
          />
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
        {/* brand goes home */}
        <Link
          to="/"
          style={{
            fontWeight: 700,
            letterSpacing: '0.08em',
            fontSize: '1.03rem',
            textDecoration: 'none',
            color: '#213E60',
          }}
        >
          Heshima Studio
        </Link>

        {/* nav links */}
        <nav style={{ display: 'flex', gap: '1.4rem' }}>
          <NavLink to="/">Home</NavLink>
          <NavLink to="/services">Services</NavLink>
          <NavLink to="/cart">Cart</NavLink>
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
          This frontend will talk to the Spring Boot API: products, inquiries, and admin tools.
          Then it will be wired up with real data using Axios.
        </p>

        <div style={{ display: 'flex', gap: '1rem', flexWrap: 'wrap' }}>
          <Link to="/services" style={primaryButton}>
            View Services
          </Link>
          <Link to="/admin" style={ghostButton}>
            Admin
          </Link>
        </div>
      </div>

      {/* hero visual */}
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
function Services({ onAddToCart }) {
  console.log('Loading services…')

  const [products, setProducts] = useState([])
  const [loading, setLoading] = useState(true)
  const [error, setError] = useState('')
  const [showInquiry, setShowInquiry] = useState(false)
  const [selectedService, setSelectedService] = useState('')

  useEffect(() => {
    async function load() {
      console.log('Loading services...')
      try {
        const data = await fetchProducts()
        console.log('✅ API data from /api/products:', data)
        setProducts(data)
      } catch (err) {
        console.error('❌ Error loading services:', err)
        setError('Could not load services from the API. Showing placeholder services instead.')
      } finally {
        setLoading(false)
        console.log('✅ Done loading services.')
      }
    }
    load()
  }, [])

  const fallbackServices = [
    { id: '1', name: 'Branding', basePrice: 750, description: 'Visual identity and brand kit.' },
    { id: '2', name: 'Web Design', basePrice: 1200, description: 'Responsive marketing website.' },
    { id: '3', name: 'UX / UI', basePrice: 950, description: 'Interface design for dashboards.' },
  ]

  const listToRender = products.length > 0 ? products : fallbackServices

  const handleInquireClick = serviceName => {
    setSelectedService(serviceName)
    setShowInquiry(true)
  }

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
              price={service.basePrice}
              desc={service.description || 'Studio service.'}
              onInquire={() => handleInquireClick(service.name)}
              onAdd={() =>
                onAddToCart({
                  id: service.id,
                  name: service.name,
                  basePrice: service.basePrice ?? 0,
                })
              }
            />
          ))}
        </div>
      )}

      {showInquiry && (
        <div
          style={{
            marginTop: '2.25rem',
            maxWidth: '520px',
            marginLeft: 'auto',
            marginRight: 'auto',
          }}
        >
          <InquiryForm products={listToRender} selectedService={selectedService} />
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

function ServiceCard({ title, price, desc, onInquire, onAdd }) {
  return (
    <div
      style={{
        background: '#fff',
        borderRadius: '1rem',
        padding: '1.25rem 1.1rem',
        border: '1px solid rgba(33,62,96,0.03)',
        boxShadow: '0 10px 28px rgba(0,0,0,0.025)',
        transition: 'transform 140ms ease-out, box-shadow 140ms ease-out',
        display: 'flex',
        flexDirection: 'column',
        minHeight: '240px',
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
      <p style={{ marginBottom: '0.6rem', color: '#E68C3A', fontWeight: 600 }}>
        {formatUSD(price ?? 0)}
      </p>
      <p style={{ fontSize: '0.82rem', lineHeight: 1.4, marginBottom: '0.85rem' }}>{desc}</p>
      <div style={{ display: 'flex', gap: '0.5rem', marginTop: 'auto' }}>
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
          onClick={onInquire}
        >
          Inquire
        </button>
        <button
          style={{
            background: 'transparent',
            border: '1px solid rgba(33,62,96,0.15)',
            borderRadius: '999px',
            fontSize: '0.72rem',
            padding: '0.45rem 0.9rem',
            color: '#213E60',
            cursor: 'pointer',
            lineHeight: 1,
            marginLeft: '0.4rem',
          }}
          onClick={onAdd}
        >
          Add to scope
        </button>
      </div>
    </div>
  )
}

/* ================= ADMIN ================= */
/**
 * Admin view to see all inquiries that were submitted from the public form
 * These come from the Spring Boot API at /api/inquiries
 * already verfied this route in Postman with Basic Auth
 * @returns 
 */
function Admin() {
  // this will hold the inquiries coming from the Spring Boot API
  const [inquiries, setInquiries] = useState([])
  // simple loading / error state so the UI isn’t blank
  const [loading, setLoading] = useState(true)
  const [error, setError] = useState('')

  useEffect(() => {
    async function loadInquiries() {
      // hitting the same endpoint I tested in Postman
      const url = 'http://localhost:8080/api/inquiries'

      // same creds I used in Postman → Basic Auth
      const username = 'admin@heshima.studio'
      const password = 'password123'
      const basic = btoa(`${username}:${password}`)

      try {
        const res = await fetch(url, {
          headers: {
            Authorization: `Basic ${basic}`,
            'Content-Type': 'application/json',
          },
        })

        if (!res.ok) {
          // if Spring Boot sends 401 / 403 / 500, surface it
          throw new Error(`API responded with ${res.status}`)
        }

        const data = await res.json()
        // data should already be the InquiryResponse list from the backend
        setInquiries(data)
      } catch (err) {
        console.error('❌ Error loading admin inquiries:', err)
        // friendly msg for the UI
        setError(
          'Could not load inquiries. Make sure the Spring Boot app is running and you are using the admin credentials.'
        )
      } finally {
        setLoading(false)
      }
    }

    loadInquiries()
  }, [])

  return (
    <section style={{ maxWidth: '760px' }}>
      <h2 style={{ fontSize: '1.7rem', marginBottom: '0.8rem' }}>Admin Dashboard</h2>
      <p style={{ lineHeight: 1.5, marginBottom: '1.3rem' }}>
        These come from <code>/api/inquiries</code> on the Spring Boot backend. This is the same route
        I verified in Postman with Basic Auth.
      </p>

      {loading && <p style={{ color: 'rgba(33,62,96,0.65)' }}>Loading inquiries…</p>}

      {!loading && error && (
        <p style={{ color: '#C05621', background: 'rgba(192,86,33,0.08)', padding: '0.5rem 0.75rem', borderRadius: '0.5rem' }}>
          {error}
        </p>
      )}

      {!loading && !error && inquiries.length === 0 && (
        <p style={{ color: 'rgba(33,62,96,0.6)' }}>
          No inquiries yet. Submit one from the Services page and refresh this admin view.
        </p>
      )}

      {!loading && !error && inquiries.length > 0 && (
        <div style={{ display: 'flex', flexDirection: 'column', gap: '0.8rem' }}>
          {inquiries.map(inquiry => (
            <div
              key={inquiry.id}
              style={{
                background: '#fff',
                borderRadius: '0.75rem',
                padding: '0.75rem 0.9rem 0.65rem',
                border: '1px solid rgba(33,62,96,0.035)',
                boxShadow: '0 8px 20px rgba(0,0,0,0.015)',
              }}
            >
              <div style={{ display: 'flex', justifyContent: 'space-between', gap: '1rem' }}>
                <div>
                  <div style={{ fontWeight: 600 }}>{inquiry.customerName}</div>
                  <div style={{ fontSize: '0.75rem', color: 'rgba(33,62,96,0.55)' }}>
                    {inquiry.customerEmail}
                  </div>
                </div>
                <div style={{ fontSize: '0.68rem', color: 'rgba(33,62,96,0.4)' }}>
                  {/* createdAt is ISO from backend */}
                  {inquiry.createdAt ? new Date(inquiry.createdAt).toLocaleString() : '—'}
                </div>
              </div>

              {inquiry.notes && (
                <p style={{ marginTop: '0.45rem', fontSize: '0.78rem', lineHeight: 1.4 }}>
                  {inquiry.notes}
                </p>
              )}

              {/* show how many items (products) were attached to this inquiry */}
              <p
                style={{
                  marginTop: '0.35rem',
                  fontSize: '0.7rem',
                  color: 'rgba(33,62,96,0.5)',
                }}
              >
                Items in request: {Array.isArray(inquiry.items) ? inquiry.items.length : 0}
              </p>
            </div>
          ))}
        </div>
      )}
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