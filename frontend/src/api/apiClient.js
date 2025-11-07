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

export default apiClient;