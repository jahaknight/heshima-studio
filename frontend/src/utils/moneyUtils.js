/**
 * Format a number (like 1200) into USD with commas ("$1,200.00")
 * This keeps pricing consistent with the rest of the app
 */

const usdFormatter = new Intl.NumberFormat('en-US', {
    style: 'currency',
    currency: 'USD',
    minimumFractionDigits: 2,
})

export function formatUSD(amount) {
    const safeNumber = Number(amount) || 0
    return usdFormatter.format(safeNumber)
}


