import axios from 'axios';

const Comparer_URL = 'http://localhost:8080/compare/json';
const Transformer_URL = 'http://localhost:8080/transform/json';

const jsonConfig = {
    headers: {
        'Content-Type': 'application/json'
    },
    responseType: 'text'
};

export const transformJsonMinify = async (json1: any) => {
    const response = await axios.post(`${Transformer_URL}?transforms=minify`, json1, jsonConfig);
    return response.data;
};

export const transformJsonFilter = async (keys: string[], json1: any) => {
    const response = await axios.post(`${Transformer_URL}?transforms=filter&keys=${keys.toString()}`, json1, jsonConfig);
    return response.data;
};

export const transformJsonBeautify = async (data: any) => {
    const response = await axios.post(`${Transformer_URL}?transforms=beautify`, data, jsonConfig);
    return response.data;
}

export const compareJson = async (json1: any, json2: any) => {
    let obj1, obj2;
    try {
        obj1 = JSON.parse(json1);
        obj2 = JSON.parse(json2);
    } catch (error) {
        console.error(error);
    }
    const response = await axios.post(`${Comparer_URL}`, { "firstJson": obj1, "secondJson": obj2 }, jsonConfig);
    return response.data;
}




