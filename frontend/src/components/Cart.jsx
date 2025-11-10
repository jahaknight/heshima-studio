// Cart component
// Displays the services the visitor has "scoped" from the Services page.
// This is not a real e-commerce cart — it's just a way for the user to
// collect Heshima Studio services before sending an inquiry.
export default function Cart({ items, onRemove }) {
  // calculate total using basePrice from each service
  const total = items.reduce((sum, item) => sum + (item.basePrice || 0), 0)

  return (
    <section>
      <h2 style={{ fontSize: '1.8rem', marginBottom: '1rem' }}>Project plan (cart)</h2>
      <p style={{ marginBottom: '1.4rem', maxWidth: '520px' }}>
        These are the Heshima Studio services you're scoping. Remove anything you don't need before
        you submit an inquiry.
      </p>

      {items.length === 0 ? (
        // empty state so the page doesn't look broken
        <p style={{ color: 'rgba(33,62,96,0.6)' }}>
          No services added yet. Go to <strong>Services</strong> and click “Add to plan.”
        </p>
      ) : (
        <div style={{ display: 'flex', flexDirection: 'column', gap: '0.75rem' }}>
          {items.map(item => (
            <div
              key={item.id}
              style={{
                background: '#fff',
                borderRadius: '0.75rem',
                padding: '0.85rem 1rem',
                display: 'flex',
                justifyContent: 'space-between',
                alignItems: 'center',
                border: '1px solid rgba(33,62,96,0.04)',
              }}
            >
              <div>
                <div style={{ fontWeight: 600 }}>{item.name}</div>
                <div style={{ fontSize: '0.78rem', color: 'rgba(33,62,96,0.55)' }}>
                  ${Number(item.basePrice || 0).toFixed(2)}
                </div>
              </div>

              {/* remove button for this service */}
              <button
                onClick={() => onRemove(item.id)}
                style={{
                    background: 'transparent',
                    border: 'none',
                    color: '#C05621',
                    cursor: 'pointer',
                    fontSize: '0.75rem',
                }}
              >
                remove
              </button>
            </div>
          ))}

          {/* summary line at the bottom */}
          <div style={{ marginTop: '0.95rem', fontWeight: 600 }}>
            Estimated total: ${total.toFixed(2)}
          </div>
        </div>
      )}
    </section>
  )
}