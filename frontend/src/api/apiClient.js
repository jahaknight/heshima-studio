import axios from 'axios';

const apiClient = axios.create({
    baseURL: 'http://localhost:8080',
    headers: {
        'Content-Type': 'application/json',
    },
});

export async function fetchProducts() {
    const response = await apiClient.get('/api/products');
    return response.data;
}

// POST /api.inquiries
// this sends the public inquiry form data to the backend
export async function submitInquiry(inquiry) {
    const response = await apiClient.post('/api/inquiries', inquiry)
    return response.data;
}

